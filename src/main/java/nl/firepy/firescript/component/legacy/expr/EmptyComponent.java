package nl.firepy.firescript.component.legacy.expr;

import java.util.ArrayList;

import nl.firepy.firescript.component.FireScriptComponent;

public class EmptyComponent implements FireScriptComponent {
    @Override
    public ArrayList<String> generateCode() {
        return new ArrayList<>();
    }
}
