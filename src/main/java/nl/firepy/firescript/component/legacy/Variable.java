package nl.firepy.firescript.component.legacy;

import java.util.ArrayList;

import nl.firepy.firescript.component.FireScriptComponent;

public class Variable implements FireScriptComponent {
    private String label;
    private String type;

    public Variable(String label, String type) {
        this.label = label;
        this.type = type;
    }

    @Override
    public ArrayList<String> generateCode() {
        return null;
    }
}
