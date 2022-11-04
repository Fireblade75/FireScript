package nl.firepy.firescript.component;

import java.util.List;

import nl.firepy.firescript.component.internal.FireScriptBlock;
import nl.firepy.firescript.component.internal.FireScriptExpression;

public class DeclareStatement implements FireScriptBlock {

    private String variableName;
    private FireScriptExpression exprComponent;

    public DeclareStatement(String variableName, FireScriptExpression exprComponent) {
        this.variableName = variableName;
        this.exprComponent = exprComponent;
    }

    @Override
    public List<String> generateCode() {
        String line = "local " + variableName;
        if (exprComponent != null ) {
            line += " = " + exprComponent.generateCode();
        }
        return List.of(line);
    }
    
}
