package nl.firepy.firescript.component.expr;

import java.util.ArrayList;

import nl.firepy.firescript.component.FireScriptComponent;

public class EmptyComponent implements FireScriptComponent {
    @Override
    public ArrayList<String> generateCode() {
        return new ArrayList<>();
    }
}
