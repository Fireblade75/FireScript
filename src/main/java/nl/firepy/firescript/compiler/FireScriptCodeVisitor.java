package nl.firepy.firescript.compiler;


import nl.firepy.firescript.compiler.scope.GlobalScope;
import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.*;
import nl.firepy.firescript.component.branching.IfComponent;
import nl.firepy.firescript.component.branching.WhileComponent;
import nl.firepy.firescript.component.expr.*;
import nl.firepy.firescript.type.ClassHeader;
import nl.firepy.firescript.type.Value;
import java.util.ArrayList;

public class FireScriptCodeVisitor extends FireScriptBaseVisitor<FireScriptComponent> {

    private ImportContext importContext = new ImportContext();
    private Scope scope;
    private GlobalScope globalScope;
    private String className;
    private ClassHeader classHeader;

    public void setClassHeader(ClassHeader classHeader) {
        this.classHeader = classHeader;
    }

    @Override
    public FireScriptComponent visitProgram(FireScriptParser.ProgramContext ctx) {
        ProgramComponent programComponent = new ProgramComponent();

        for(FireScriptParser.ImportStatementContext importStatement : ctx.importStatement()) {
            visitImportStatement(importStatement);
        }

        if(ctx.classBlock() != null) {
            programComponent.setClassComponent(visitClassBlock(ctx.classBlock()));
        } else {
            programComponent.setClassComponent(visitStructBlock(ctx.structBlock()));
        }

        return programComponent;
    }

    @Override
    public FireScriptComponent visitImportStatement(FireScriptParser.ImportStatementContext ctx) {
        importContext.addImport(ctx.IMPORT_LITERAL().getText());
        return null;
    }

    @Override
    public FireScriptComponent visitClassBlock(FireScriptParser.ClassBlockContext ctx) {
        className = "viglo/" + ctx.NAME().getText();
        RootBlockComponent block = visitRootBlock(ctx.rootBlock());
        return new ClassComponent(className, block, classHeader);
    }

    @Override
    public FireScriptComponent visitStructBlock(FireScriptParser.StructBlockContext ctx) {
        globalScope = new GlobalScope(className, classHeader);
        scope = new Scope(globalScope, false);
        className = "viglo/" +ctx.NAME().getText();
        BlockComponent blockComponent = visitBlock(ctx.block());
        return new StructComponent(className, blockComponent);
    }

    /*
     * This block is the root block of a class,
     */
    @Override
    public RootBlockComponent visitRootBlock(FireScriptParser.RootBlockContext ctx) {
        globalScope = new GlobalScope(className, classHeader);
        RootBlockComponent blockComponent = new RootBlockComponent(scope, className);
        for(FireScriptParser.RootStatementContext statement : ctx.rootStatement()) {
            if(statement.declareFunction() != null) {
                blockComponent.add(visit(statement));
            }
        }
        return blockComponent;
    }

    @Override
    public FireScriptComponent visitDeclareFunction(FireScriptParser.DeclareFunctionContext ctx) {
        String name = ctx.NAME().getText();
        boolean isStatic =ctx.STATIC() != null;
        scope = new Scope(globalScope, isStatic);
        FunctionExpression functionExpression = visitFunctionStatement(ctx.functionStatement());
        FunctionComponent functionComponent = new FunctionComponent(name, functionExpression, scope, isStatic);
        scope.close();
        return functionComponent;
    }

    @Override
    public FunctionExpression visitFunctionStatement(FireScriptParser.FunctionStatementContext ctx) {
        ParamList paramList = visitParamList(ctx.paramList());
        String returnType = ctx.type().getText();
        scope.setReturnType(returnType);
        FunctionExpression functionExpression = new FunctionExpression(paramList, returnType, scope);
        functionExpression.setBlock(visitBlock(ctx.block()));
        return functionExpression;
    }

    @Override
    public ParamList visitParamList(FireScriptParser.ParamListContext ctx) {
        return new ParamList(ctx.paramItem());
    }

    @Override
    public BlockComponent visitBlock(FireScriptParser.BlockContext ctx) {
        scope = new Scope(scope);
        BlockComponent blockComponent = new BlockComponent(scope);
        for(FireScriptParser.StatementContext statement : ctx.statement()) {
            blockComponent.add(visit(statement));
        }
        scope.close();
        scope = scope.getParent();
        return blockComponent;
    }

    @Override
    public FireScriptComponent visitDeclareInferStatement(FireScriptParser.DeclareInferStatementContext ctx) {
        String label = ctx.NAME().getText();
        boolean constant = ctx.varKey.getText().equals("const");
        ExprComponent expr = (ExprComponent) visit(ctx.exp());
        Value value = expr.getValue();
        scope.addValue(label, value);
        return new AssignStatement(expr, scope, label);
    }

    @Override
    public FireScriptComponent visitDeclareOnlyStatement(FireScriptParser.DeclareOnlyStatementContext ctx) {
        String label = ctx.NAME().getText();
        boolean constant = ctx.varKey.getText().equals("const");
        Value value = new Value(ctx.type().getText(), constant);
        scope.addValue(label, value);
        return new EmptyComponent();
    }

    @Override
    public FireScriptComponent visitChainExp(FireScriptParser.ChainExpContext ctx) {
        if(ctx.chainPart().size()>0) {
            throw new UnsupportedOperationException();
        } else {
            return visit(ctx.chainTail());
        }
    }

    @Override
    public VariableComponent visitVariable(FireScriptParser.VariableContext ctx) {
        String label = ctx.NAME().getText();
        return new VariableComponent(scope.getValue(label), scope, scope.getIndex(label));
    }

    @Override
    public FireScriptComponent visitFunctionCall(FireScriptParser.FunctionCallContext ctx) {
        String functionName = ctx.NAME().getText();
        ArrayList<ExprComponent> exprComponents = new ArrayList<>();
        for(FireScriptParser.ExpContext exp : ctx.exp()) {
            exprComponents.add((ExprComponent) visit(exp));
        }
        return new FunctionCall(scope, functionName, exprComponents);
    }

    @Override
    public FireScriptComponent visitAssignStatement(FireScriptParser.AssignStatementContext ctx) {
        ExprComponent expr = (ExprComponent) visit(ctx.exp());
        String label = ctx.variable().getText();
        return new AssignStatement(expr, scope, label);
    }

    @Override
    public FireScriptComponent visitEchoStatement(FireScriptParser.EchoStatementContext ctx) {
        ExprComponent expr = (ExprComponent) visit(ctx.exp());
        return new EchoStatement(expr, scope);
    }

    @Override
    public NotExprComponent visitNotExpression(FireScriptParser.NotExpressionContext ctx) {
        ExprComponent childComponent = (ExprComponent) visit(ctx.exp());
        return new NotExprComponent(childComponent, scope);
    }

    @Override
    public FireScriptComponent visitLogicExpression(FireScriptParser.LogicExpressionContext ctx) {
        ExprComponent leftExpr = (ExprComponent) visit(ctx.left);
        ExprComponent rightExpr = (ExprComponent) visit(ctx.right);
        String op = ctx.logic_operator().getText();
        return new LogicExpression(leftExpr, rightExpr, op, scope);
    }

    @Override
    public FireScriptComponent visitMathExpression(FireScriptParser.MathExpressionContext ctx) {
        ExprComponent leftExpr = (ExprComponent) visit(ctx.left);
        ExprComponent rightExpr = (ExprComponent) visit(ctx.right);
        String op = ctx.math_operator().getText();
        return new MathExpression(leftExpr, rightExpr, op, scope);
    }

    @Override
    public FireScriptComponent visitEqualityExpression(FireScriptParser.EqualityExpressionContext ctx) {
        ExprComponent leftExpr = (ExprComponent) visit(ctx.left);
        ExprComponent rightExpr = (ExprComponent) visit(ctx.right);
        boolean inverted = ctx.eq_operator().getText().equals("!=");
        return new EqualsExpression(leftExpr, rightExpr, inverted, scope);
    }

    @Override
    public FireScriptComponent visitCompareExpression(FireScriptParser.CompareExpressionContext ctx) {
        ExprComponent leftExpr = (ExprComponent) visit(ctx.left);
        ExprComponent rightExpr = (ExprComponent) visit(ctx.right);
        String op = ctx.comp_operator().getText();
        return new CompareExpression(leftExpr, rightExpr, op, scope);
    }

    @Override
    public IfComponent visitIfStatement(FireScriptParser.IfStatementContext ctx) {
        ExprComponent ifExpr = (ExprComponent) visit(ctx.exp());
        BlockComponent ifBlock = visitBlock(ctx.block());
        IfComponent ifComponent = new IfComponent(ifExpr, ifBlock, scope);
        if(ctx.elseStatement() != null) {
            ifComponent.addElseBlock(visitElseStatement(ctx.elseStatement()));
        }
        for(FireScriptParser.ElseifStatementContext elseIfBlock : ctx.elseifStatement()) {
            ifComponent.addElseIfBlock(visitElseifStatement(elseIfBlock));
        }
        return ifComponent;
    }

    @Override
    public WhileComponent visitWhileStatement(FireScriptParser.WhileStatementContext ctx) {
        ExprComponent exprComponent = (ExprComponent) visit(ctx.exp());
        BlockComponent blockComponent = visitBlock(ctx.block());
        return new WhileComponent(exprComponent, blockComponent, scope);
    }

    @Override
    public BlockComponent visitElseStatement(FireScriptParser.ElseStatementContext ctx) {
        return visitBlock(ctx.block());
    }

    @Override
    public IfComponent.ElseIfBlock visitElseifStatement(FireScriptParser.ElseifStatementContext ctx) {
        BlockComponent block = visitBlock(ctx.block());
        ExprComponent expr  = (ExprComponent) visit(ctx.exp());
        return new IfComponent.ElseIfBlock(expr, block);
    }

    @Override
    public FireScriptComponent visitBracketExpression(FireScriptParser.BracketExpressionContext ctx) {
        return visit(ctx.exp());
    }

    @Override
    public IntLiteral visitIntLiteral(FireScriptParser.IntLiteralContext ctx) {
        int val = Integer.parseInt(ctx.INT_LITERAL().getText());
        return new IntLiteral(val);
    }

    @Override
    public BoolLiteral visitBoolLiteral(FireScriptParser.BoolLiteralContext ctx) {
        boolean val = Boolean.parseBoolean(ctx.BOOL_LITERAL().getText());
        return new BoolLiteral(val);
    }

    @Override
    public CharLiteral visitCharLiteral(FireScriptParser.CharLiteralContext ctx) {
        char val = ctx.CHAR_STRING().getText().charAt(1);
        return new CharLiteral(val);
    }

    @Override
    public FloatLiteral visitFloatLiteral(FireScriptParser.FloatLiteralContext ctx) {
        float val = Float.parseFloat(ctx.FLOAT_LITERAL().getText());
        return new FloatLiteral(val);
    }

    @Override
    public DoubleLiteral visitDoubleLiteral(FireScriptParser.DoubleLiteralContext ctx) {
        double val = Float.parseFloat(ctx.DOUBLE_LITERAL().getText());
        return new DoubleLiteral(val);
    }

    @Override
    public FireScriptComponent visitLongLiteral(FireScriptParser.LongLiteralContext ctx) {
        String longStr = ctx.LONG_LITERAL().getText();
        longStr = longStr.substring(0, longStr.length() - 1);
        long val = Long.parseLong(longStr);
        return new LongLiteral(val);
    }

    @Override
    public ReturnStatement visitReturnStatement(FireScriptParser.ReturnStatementContext ctx) {
        return new ReturnStatement((ExprComponent) visit(ctx.exp()), scope, scope.getReturnType());
    }
}
