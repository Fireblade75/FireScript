package nl.firepy.firescript.compiler;

import nl.firepy.firescript.compiler.FireScriptParser.AssignStatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.DeclareInferStatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.DeclareOnlyStatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.RootStatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.StatementContext;
import nl.firepy.firescript.compiler.FireScriptParser.StringLiteralContext;
import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.expr.Variable;
import nl.firepy.firescript.type.FSType;
import nl.firepy.firescript.type.TypeConverter;
import nl.firepy.firescript.type.Value;
import nl.firepy.firescript.type.descriptors.ModuleDescriptor;

import java.util.ArrayList;

public class FireScriptTypeVisitor extends FireScriptBaseVisitor<FSType> {

    private ModuleDescriptor moduleDescriptor;

    public ModuleDescriptor visitCodeFile(String sourceName, FireScriptParser.ProgramContext ctx) {
        this.moduleDescriptor = new ModuleDescriptor(null);
        visitProgram(ctx);
        return moduleDescriptor;
    }

    @Override
    public FSType visitProgram(FireScriptParser.ProgramContext ctx) {
        for(var statement : ctx.rootStatement()) {
            visit(statement);
        }
        return null;
    }

    @Override
    public FSType visitRootStatement(RootStatementContext ctx) {
        return visit(ctx.statement());
    }

    // @Override
    // public String visitStatement(StatementContext ctx) {
    //     if (ctx.assignStatement() != null) {
    //         return visit(ctx.assignStatement());
    //     } else if(ctx.declareStatement() != null) {
    //         return visit(ctx.declareStatement());
    //     }
    //     throw new UnsupportedOperationException("Method not implemented yet");
    // }


    // @Override
    // public String visitDeclareFunction(FireScriptParser.DeclareFunctionContext ctx) {
    //     scope = new Scope(globalScope, false);
    //     visit(ctx.functionStatement());
    //     scope.close();
    //     return "function";
    // }

    // @Override
    // public String visitFunctionStatement(FireScriptParser.FunctionStatementContext ctx) {
    //     scope.setReturnType(ctx.type().getText());
    //     for(FireScriptParser.ParamItemContext paramItem : ctx.paramList().paramItem()) {
    //         visit(paramItem);
    //     }

    //     visit(ctx.block());
    //     return "function";
    // }

    // @Override
    // public String visitParamItem(FireScriptParser.ParamItemContext ctx) {
    //     Value value = new Value(ctx.type().getText(), false);
    //     scope.addValue(ctx.NAME().getText(), value);
    //     return ctx.type().getText();
    // }

    // @Override
    // public String visitBlock(FireScriptParser.BlockContext ctx) {
    //     boolean functionScope = !scope.hasParent();
    //     String functionType = scope.getReturnType();
    //     for(FireScriptParser.BlockStatementContext statment : ctx.blockStatement()) {
    //         visit(statment);
    //     }
    //     for (int i = 0; i < ctx.blockStatement().size(); i++) {
    //         if(!functionType.equals("void") && i == ctx.blockStatement().size() - 1 && functionScope) {
    //             if(ctx.blockStatement(i).returnStatement() == null) {
    //                 throw new CompilerException(ctx.blockStatement(i), "Expected return statement");
    //             }
    //         }
    //     }
    //     return "block";
    // }

    // @Override
    // public String visitReturnStatement(FireScriptParser.ReturnStatementContext ctx) {
    //     String expType = visit(ctx.exp());
    //     String functionType = scope.getReturnType();
    //     if(!expType.equals(functionType) && !TypeConverter.canFreelyCast(expType, functionType)) {
    //         throw new CompilerException(ctx.exp(), "Cannot return " + expType +" expected a " + functionType);
    //     }
    //     return super.visitReturnStatement(ctx);
    // }

    // @Override
    // public String visitDeclareInferStatement(FireScriptParser.DeclareInferStatementContext ctx) {
    //     String type = visit(ctx.exp());
    //     boolean constant = ctx.CONST() != null;
    //     scope.addValue(ctx.NAME().getText(), new Value(type, constant));
    //     return visit(ctx.exp());
    // }

    // @Override
    // public String visitAssignStatement(FireScriptParser.AssignStatementContext ctx) {
    //     String label = ctx.variable().getText();
    //     Value value = scope.getValue(label);
    //     if(value == null) {
    //         throw new CompilerException(ctx, "Variable '" + label + "' is not defined");
    //     }

    //     String valueType = value.getType(scope);
    //     String expType = visit(ctx.exp());
    //     if (!valueType.equals(expType)) {
    //         if(!TypeConverter.canFreelyCast(expType, valueType)) {
    //             throw new CompilerException(ctx, "Cannot assign " + expType + " to variable '" + label + "' of type " + valueType);
    //         }
    //     }
    //     return expType;
    // }

    // @Override
    // public String visitMathExpression(FireScriptParser.MathExpressionContext ctx) {
    //     return visitTwoNumberExpression(ctx.left, ctx.right);
    // }

    // @Override
    // public String visitCompareExpression(FireScriptParser.CompareExpressionContext ctx) {
    //     return visitTwoNumberExpression(ctx.left, ctx.right);
    // }

    // private String visitTwoNumberExpression(FireScriptParser.ExpContext left, FireScriptParser.ExpContext right) {
    //     String leftType = visit(left);
    //     String rightType = visit(right);
    //     if(!TypeConverter.isNumber(leftType)) {
    //         throw new CompilerException(left, "Expected a number, found "+leftType);
    //     }
    //     if(!TypeConverter.isNumber(rightType)) {
    //         throw new CompilerException(right, "Expected a number, found "+rightType);
    //     }
    //     return TypeConverter.combineNumbers(leftType, rightType);
    // }

    // @Override
    // public String visitNotExpression(FireScriptParser.NotExpressionContext ctx) {
    //     String expType = visit(ctx.exp());
    //     if(!expType.equals("bool")) {
    //         throw new CompilerException(ctx.exp(), "Expected a bool, found "+ expType);
    //     }
    //     return "bool";
    // }

    // @Override
    // public String visitLogicExpression(FireScriptParser.LogicExpressionContext ctx) {
    //     String leftType = visit(ctx.left);
    //     String rightType = visit(ctx.right);
    //     if(!leftType.equals("bool")) {
    //         throw new CompilerException(ctx.left, "Expected a bool, found "+leftType);
    //     }
    //     if(!rightType.equals("bool")) {
    //         throw new CompilerException(ctx.right, "Expected a bool, found "+rightType);
    //     }
    //     return "bool";
    // }

    // @Override
    // public String visitChainExp(FireScriptParser.ChainExpContext ctx) {
    //     if(ctx.chainPart().size()>0) {
    //         throw new CompilerException(ctx, "Chained expressions are not supported, for now.");
    //     } else {
    //         return visit(ctx.chainTail());
    //     }
    // }

    // @Override
    // public String visitVariable(FireScriptParser.VariableContext ctx) {
    //     Value value = scope.getValue(ctx.NAME().getText());
    //     if(value == null) {
    //         throw new CompilerException(ctx, "Variable '" + ctx.NAME().getText() + "' is not defined");
    //     }
    //     return value.getType(scope);
    // }

    // @Override
    // public String visitFunctionCall(FireScriptParser.FunctionCallContext ctx) {
    //     FunctionDescriptor function = scope.getFunction(ctx.NAME().getText());
    //     ArrayList<String> functionParams = function.getParamTypes();
    //     if(ctx.exp().size() != functionParams.size()) {
    //         throw new CompilerException(ctx, "Parameters don't match " + function.toString());
    //     } else {
    //         for (int i = 0; i < functionParams.size(); i++) {
    //             String callParam = visit(ctx.exp(i));
    //             if(!callParam.equals(functionParams.get(i))) {
    //                 if(!TypeConverter.canFreelyCast(callParam, functionParams.get(i))) {
    //                     String message = "Expected " + functionParams.get(i) + " found " + callParam;
    //                     throw new CompilerException(ctx.exp(i), message);
    //                 }
    //             }
    //         }
    //     }
    //     return function.getReturnType();
    // }

    // @Override
    // public String visitBracketExpression(FireScriptParser.BracketExpressionContext ctx) {
    //     return visit(ctx.exp());
    // }

    @Override
    public FSType visitAssignStatement(AssignStatementContext ctx) {
        FSType assigmentType = visit(ctx.exp());
        Variable variable  = moduleDescriptor.getVariable(ctx.variable().getText().toString());
        // Check if autoconvert is possible
        if(!assigmentType.canConvertTo(variable.getType())) {
            throw new RuntimeException("Type conversion not possible");
        }
        return FSType.VOID;
    }

    @Override
    public FSType visitDeclareInferStatement(DeclareInferStatementContext ctx) {
        FSType type = visit(ctx.exp());
        boolean isConstant = ctx.CONST() != null;
        moduleDescriptor.addVariable(ctx.NAME().toString(), type, isConstant);
        return FSType.VOID;
    }

    @Override
    public FSType visitDeclareOnlyStatement(DeclareOnlyStatementContext ctx) {
        FSType type = moduleDescriptor.parseType(ctx.type().getText().toString());
        moduleDescriptor.addVariable(ctx.NAME().toString(), type, false);
        return FSType.VOID;
    }

    @Override
    public FSType visitStringLiteral(StringLiteralContext ctx) {
        return FSType.STRING;
    }

    @Override
    public FSType visitIntLiteral(FireScriptParser.IntLiteralContext ctx) {
        return FSType.INT;
    }

    @Override
    public FSType visitFloatLiteral(FireScriptParser.FloatLiteralContext ctx) {
        return FSType.FLOAT;
    }

    @Override
    public FSType visitBoolLiteral(FireScriptParser.BoolLiteralContext ctx) {
        return FSType.BOOL;
    }
}
