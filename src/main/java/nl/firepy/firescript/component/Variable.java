package nl.firepy.firescript.component;

import java.util.ArrayList;

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
