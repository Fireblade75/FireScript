package nl.firepy.firescript.type.descriptors;

import java.util.HashMap;

import nl.firepy.firescript.component.expr.BlockVariable;
import nl.firepy.firescript.component.expr.Variable;

public class FunctionDescriptor {
    
    private String functionName;
    private HashMap<String, BlockVariable> variables = new HashMap<>();

    public FunctionDescriptor(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void addVariable(BlockVariable variable) {
        this.variables.put(variable.getVariableName(), variable);
    }

    public Variable getVariable(String variableName) {
        return this.variables.get(variableName);
    }
}
