package nl.firepy.firescript.component.legacy;

import java.util.ArrayList;

import nl.firepy.firescript.component.FireScriptComponent;

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
