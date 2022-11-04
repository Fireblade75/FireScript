package nl.firepy.firescript.compiler;


import nl.firepy.firescript.compiler.FireScriptParser.AssignStatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.DeclareInferStatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.DeclareOnlyStatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.DeclareStatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.ExpContext;
import nl.firepy.firescript.compiler.FireScriptParser.IntLiteralContext;
import nl.firepy.firescript.compiler.FireScriptParser.ProgramContext;
import nl.firepy.firescript.compiler.FireScriptParser.RootStatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.StatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.StringLiteralContext;
import nl.firepy.firescript.compiler.FireScriptParser.TypeContext;
import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.*;
import nl.firepy.firescript.component.expr.ExprComponent;
import nl.firepy.firescript.component.expr.IntLiteral;
import nl.firepy.firescript.component.expr.StringLiteral;
import nl.firepy.firescript.component.expr.TypeExpression;
import nl.firepy.firescript.component.internal.FireScriptBlock;
import nl.firepy.firescript.component.internal.FireScriptComponent;
import nl.firepy.firescript.component.internal.FireScriptExpression;
import nl.firepy.firescript.component.legacy.AssignStatement;
import nl.firepy.firescript.type.CodeFileDescriptor;

public class FireScriptCodeVisitor extends FireScriptBaseVisitor<FireScriptComponent> {

    private ImportContext importContext = new ImportContext();
    private Scope scope;
    private Scope rootScope;
    private String className;
    private CodeFileDescriptor classHeader;

    public FireScriptBlock visitCodeFile(CodeFileDescriptor classHeader, ProgramContext ctx) {
        rootScope = new Scope();
        scope = rootScope;
        return visitProgram(ctx);
    }

    @Override
    public FireScriptBlock visitProgram(ProgramContext ctx) {
        BlockComponent blockComponent = new BlockComponent(scope);

        for (RootStatementContext statement : ctx.rootStatement()) {
            blockComponent.add(visitRootStatement(statement));
        }

        return blockComponent;
    }

    @Override
    public FireScriptBlock visitStatement(StatementContext ctx) {
        /*
            |declareStatement
            | assignStatement
            | declareFunction
            | ifStatement
            | chainExp
         */
        if (ctx.assignStatement() != null) {
            return visitAssignStatement(ctx.assignStatement());
        } else if(ctx.declareStatement() != null) {
            return visitDeclareStatement(ctx.declareStatement());
        }
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public FireScriptBlock visitRootStatement(RootStatementContext ctx) { 
        // if(ctx.declareClass() != null) {
        //     return visit(ctx.declareClass());
        // } else if(ctx.declareFunction() != null) {
        //     return visit(ctx.declareFunction());
        // } else {
            return visitStatement(ctx.statement());
        // }
    }

    @Override
    public FireScriptBlock visitAssignStatement(AssignStatementContext ctx) {
        String variableName = ctx.variable().getText();
        var rawExpression = visit(ctx.exp());
        if(rawExpression instanceof FireScriptExpression) {
            FireScriptExpression expression = (FireScriptExpression) rawExpression;
            return new nl.firepy.firescript.component.AssignStatement(variableName, scope, expression);
        } else {
            throw new UnsupportedOperationException("Invalid Expression Type: " + rawExpression.getClass().getName());
        }
    }

    @Override
    public FireScriptBlock visitDeclareStatement(DeclareStatementContext ctx) {
        if(ctx.declareInferStatement() != null) {
            return visitDeclareInferStatement(ctx.declareInferStatement());
        } else {
            return visitDeclareOnlyStatement(ctx.declareOnlyStatement());
        }
    }

    @Override
    public FireScriptBlock visitDeclareInferStatement(DeclareInferStatementContext ctx) {
        var rawExpression = visit(ctx.exp());
        if(rawExpression instanceof FireScriptExpression) {
            FireScriptExpression expression = (FireScriptExpression) rawExpression;
            boolean isConstant = ctx.CONST() != null;
            scope.addValue(ctx.NAME().toString(), expression.getType(), isConstant);
            return new DeclareStatement(ctx.NAME().toString(), expression);
        } else {
            throw new UnsupportedOperationException("Invalid Expression Type: " + rawExpression.getClass().getName());
        }
    }

    @Override
    public FireScriptBlock visitDeclareOnlyStatement(DeclareOnlyStatementContext ctx) {
        TypeExpression typeExpression = visitType(ctx.type());
        scope.addValue(ctx.NAME().toString(), typeExpression.getType(), false);
        return new DeclareStatement(ctx.NAME().toString(), null);
    }

    @Override
    public StringLiteral visitStringLiteral(StringLiteralContext ctx) {
        String rawStringText = ctx.STRING_STRING().getText();
        if(rawStringText.length() < 2) throw new RuntimeException("Invalid String: " + rawStringText);
        String stringText = rawStringText.substring(1, rawStringText.length() -1);
        
        return new StringLiteral(stringText);
    }

    @Override
    public IntLiteral visitIntLiteral(IntLiteralContext ctx) {
        long value = Long.parseLong(ctx.getText());
        return new IntLiteral(value);
    }

    @Override
    public TypeExpression visitType(TypeContext ctx) {
        return new TypeExpression(ctx);
    }
}
