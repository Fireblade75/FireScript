package nl.firepy.firescript.component;

import java.util.List;
import java.util.stream.Collectors;

public class DeclareStatement implements FireScriptComponent {

    private String variableName;
    private FireScriptComponent exprComponent;

    public DeclareStatement(String variableName, FireScriptComponent exprComponent) {
        this.variableName = variableName;
        this.exprComponent = exprComponent;
    }

    @Override
    public List<String> generateCode() {
        String line = "local " + variableName;
        if (exprComponent != null ) {
            line += " = " + exprComponent.generateCode().stream().collect(Collectors.joining("\n"));
        }
        return List.of(line);
    }
    
}
