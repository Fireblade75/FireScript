package nl.firepy.firescript.component;

import java.util.ArrayList;

import nl.firepy.firescript.compiler.scope.Scope;

public class BlockComponent implements FireScriptComponent {

    private ArrayList<FireScriptComponent> fireScriptComponents = new ArrayList<>();
    private Scope scope;

    public BlockComponent(Scope scope) {
        this.scope = scope;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        for(FireScriptComponent fireScriptComponent : fireScriptComponents) {
            asm.addAll(fireScriptComponent.generateCode());
        }
        return asm;
    }

    public void add(FireScriptComponent component) {
        fireScriptComponents.add(component);
    }

    public Scope getScope() {
        return scope;
    }
}
