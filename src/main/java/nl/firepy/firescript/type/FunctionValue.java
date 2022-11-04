package nl.firepy.firescript.type;

import nl.firepy.firescript.compiler.scope.Scope;

public class FunctionValue extends Value {
    private String functionName;

    public FunctionValue(String functionName) {
        super("FUNCTION", false);
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    @Override
    public String getType(Scope scope) {
        // return scope.getFunction(functionName).getReturnType();
        return "";
    }
}
