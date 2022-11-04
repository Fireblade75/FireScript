package nl.firepy.firescript.component;

import java.util.List;

import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.expr.RootVariable;
import nl.firepy.firescript.component.expr.Variable;
import nl.firepy.firescript.component.internal.FireScriptBlock;
import nl.firepy.firescript.component.internal.FireScriptExpression;

public class AssignStatement implements FireScriptBlock {
    
    private FireScriptExpression expression;
    private Variable variable;

    public AssignStatement(String variableName, Scope scope, FireScriptExpression expression) {
        this.expression = expression;
        this.variable = scope.getVariable(variableName);
        
        if (this.variable == null) {
            throw new RuntimeException("Variable " + variableName + " is not defined in this scope");
        }
    }

    @Override
    public List<String> generateCode() {
        if(variable instanceof RootVariable) {
            String code = variable.getVariableName() + " = " + expression.generateCode();
            return List.of(code);
        } else {
            throw new UnsupportedOperationException("Block variables are currently not supported");
        }
    }
}
