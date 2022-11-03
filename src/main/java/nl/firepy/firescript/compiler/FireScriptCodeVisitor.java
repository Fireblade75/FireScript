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
import nl.firepy.firescript.compiler.scope.GlobalScope;
import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.*;
import nl.firepy.firescript.component.expr.ExprComponent;
import nl.firepy.firescript.component.expr.IntLiteral;
import nl.firepy.firescript.component.legacy.AssignStatement;
import nl.firepy.firescript.type.CodeFileDescriptor;

public class FireScriptCodeVisitor extends FireScriptBaseVisitor<FireScriptComponent> {

    private ImportContext importContext = new ImportContext();
    private Scope scope;
    private GlobalScope globalScope;
    private String className;
    private CodeFileDescriptor classHeader;

    public FireScriptComponent visitCodeFile(CodeFileDescriptor classHeader, ProgramContext ctx) {
        globalScope = new GlobalScope(classHeader.getSourceName(), classHeader);
        scope = new Scope(globalScope, false);
        return visitProgram(ctx);
    }

    @Override
    public FireScriptComponent visitProgram(ProgramContext ctx) {
        BlockComponent blockComponent = new BlockComponent(scope);

        var stats = ctx.rootStatement();
        var text = ctx.getText();

        for (var statement : ctx.rootStatement()) {
            blockComponent.add(visit(statement));
        }

        return blockComponent;
    }

    @Override
    public FireScriptComponent visitStatement(StatementContext ctx) {
        /*
            |declareStatement
            | assignStatement
            | declareFunction
            | ifStatement
            | chainExp
         */
        if (ctx.assignStatement() != null) {
            return visit(ctx.assignStatement());
        } else if(ctx.declareStatement() != null) {
            return visit(ctx.declareStatement());
        }
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public FireScriptComponent visitRootStatement(RootStatementContext ctx) { 
        // if(ctx.declareClass() != null) {
        //     return visit(ctx.declareClass());
        // } else if(ctx.declareFunction() != null) {
        //     return visit(ctx.declareFunction());
        // } else {
            return visit(ctx.statement());
        // }
    }

    @Override
    public FireScriptComponent visitAssignStatement(AssignStatementContext ctx) {
        // return new AssignStatement(null, scope, className)
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public FireScriptComponent visitDeclareStatement(DeclareStatementContext ctx) {
        if(ctx.declareInferStatement() != null) {
            return visit(ctx.declareInferStatement());
        } else {
            return visit(ctx.declareOnlyStatement());
        }
    }

    @Override
    public FireScriptComponent visitDeclareInferStatement(DeclareInferStatementContext ctx) {
        return new DeclareStatement(ctx.NAME().toString(), visit(ctx.exp()));
    }

    @Override
    public FireScriptComponent visitDeclareOnlyStatement(DeclareOnlyStatementContext ctx) {
        return new DeclareStatement(ctx.NAME().toString(), null);
    }

    @Override
    public ExprComponent visitIntLiteral(IntLiteralContext ctx) {
        long value = Long.parseLong(ctx.getText());
        return new IntLiteral(value);
    }
}
