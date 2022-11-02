package nl.firepy.firescript.component;

import java.util.ArrayList;

public class ProgramComponent implements FireScriptComponent {

    private FireScriptComponent classComponent;

    public void setClassComponent(FireScriptComponent classComponent) {
        this.classComponent = classComponent;
    }

    @Override
    public ArrayList<String> generateCode() {
        return new ArrayList<>(classComponent.generateCode());
    }
}
