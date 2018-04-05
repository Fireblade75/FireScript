package nl.saxion.viglo;


import nl.saxion.viglo.component.*;
import nl.saxion.viglo.component.expr.EmptyComponent;
import nl.saxion.viglo.component.expr.ExprComponent;
import nl.saxion.viglo.component.expr.NotExprComponent;

public class VigloCodeVisitor extends VigloBaseVisitor<VigloComponent> {

    private ImportContext importContext = new ImportContext();
    private Scope scope = new Scope();

    @Override
    public VigloComponent visitProgram(VigloParser.ProgramContext ctx) {
        ProgramComponent programComponent = new ProgramComponent();

        for(VigloParser.ImportStatementContext importStatement : ctx.importStatement()) {
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
    public VigloComponent visitImportStatement(VigloParser.ImportStatementContext ctx) {
        importContext.addImport(ctx.IMPORT_LITERAL().getText());
        return null;
    }

    @Override
    public VigloComponent visitClassBlock(VigloParser.ClassBlockContext ctx) {
        String className = ctx.NAME().getText();
        return new ClassComponent(className);
    }

    @Override
    public VigloComponent visitStructBlock(VigloParser.StructBlockContext ctx) {
        String className = ctx.NAME().getText();
        BlockComponent blockComponent = visitBlock(ctx.block());
        return new StructComponent(className, blockComponent);
    }

    @Override
    public VigloComponent visitDeclareStatement(VigloParser.DeclareStatementContext ctx) {
        String label = ctx.NAME().getText();
        boolean constant = ctx.varKey.getText().equals("const");
        if(ctx.type()!=null) {
            Value value = new Value(ctx.type().getText(), constant);
            scope.addValue(label, value);
            return new EmptyComponent();
        } else {
            ExprComponent expr = (ExprComponent) visit(ctx.exp());
            Value value = expr.getValue();
            scope.addValue(label, value);
            return new AssignComponent(expr);
        }
    }

    @Override
    public BlockComponent visitBlock(VigloParser.BlockContext ctx) {
        scope = new Scope(scope);
        BlockComponent blockComponent = new BlockComponent();
        for(VigloParser.StatementContext statement : ctx.statement()) {
            blockComponent.add(visit(statement));
        }
        scope = scope.getParent();
        return blockComponent;
    }

    @Override
    public NotExprComponent visitNotExpression(VigloParser.NotExpressionContext ctx) {
        ExprComponent childComponent = (ExprComponent) visit(ctx.exp());
        return new NotExprComponent(childComponent);
    }

}
