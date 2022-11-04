package nl.firepy.firescript.component;

import java.util.ArrayList;
import java.util.List;

import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.internal.FireScriptBlock;
import nl.firepy.firescript.component.internal.FireScriptComponent;
import nl.firepy.firescript.component.internal.FireScriptExpression;

public class BlockComponent implements FireScriptBlock {

    private ArrayList<FireScriptBlock> fireScriptComponents = new ArrayList<>();
    private Scope scope;

    public BlockComponent(Scope scope) {
        this.scope = scope;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        for(FireScriptBlock fireScriptComponent : fireScriptComponents) {
            asm.addAll(fireScriptComponent.generateCode());
        }
        return asm;
    }

    public void add(FireScriptBlock component) {
        fireScriptComponents.add(component);
    }

    public void add(FireScriptExpression expression) {
        fireScriptComponents.add(() -> List.of(expression.generateCode()));
    }

    public Scope getScope() {
        return scope;
    }
}
