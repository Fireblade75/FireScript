package nl.firepy.firescript.component;

import java.util.ArrayList;

import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.expr.ExprComponent;
import nl.firepy.firescript.component.expr.NumberConverter;
import nl.firepy.firescript.type.TypeConverter;

public class ReturnStatement implements FireScriptComponent {

    private ExprComponent expr;
    private Scope scope;
    private String functionType;

    public ReturnStatement(ExprComponent expr, Scope scope, String functionType) {
        this.expr = expr;
        this.scope = scope;
        this.functionType = functionType;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        String expType = expr.getValue().getType(scope);
        asm.addAll(NumberConverter.loadExpr(expr, expType, functionType));
        switch (TypeConverter.toRawType(functionType)) {
            case "int":
                asm.add("\tireturn");
                break;
            case "long":
                asm.add("\tlreturn");
                break;
            case "float":
                asm.add("\tfreturn");
                break;
            case "double":
                asm.add("\tdreturn");
                break;
            default:
                asm.add("\treturn");
        }
        return asm;
    }
}
