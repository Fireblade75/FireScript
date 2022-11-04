package nl.firepy.firescript.compiler.scope;

import java.util.HashMap;

import nl.firepy.firescript.component.expr.BlockVariable;
import nl.firepy.firescript.component.expr.RootVariable;
import nl.firepy.firescript.component.expr.Variable;
import nl.firepy.firescript.type.FSType;

public class Scope {
    private Scope parent = null;
    private HashMap<String, Variable> valueMap = new HashMap<>();

    public Scope() {
    }

    public Scope(Scope scope) {
        parent = scope;
    }

    public void addValue(String label, FSType type, boolean isConstant) {
        if(!hasValueDirect(label)) {
            Variable variable;
            if(isRoot()) {
                variable = new RootVariable(label, type, isConstant);
            } else {
                variable = new BlockVariable(label, type, isConstant);
            }

            valueMap.put(label, variable);
        } else {
            throw new RuntimeException("Cannot add '"+label+"' to this scope, variable already defined");
        }
    }

    public Variable getVariable(String label) {
        if (valueMap.containsKey(label)) {
            return valueMap.get(label);
        } else if (parent != null) {
            return parent.getVariable(label);
        } else {
            return null;
        }
    }

    /**
     * Checks whether the label is already defined in this scope
     * If true the variable can not be added
     * @param label the label of the variable
     * @return true if the variable already exists
     */
    public boolean hasValueDirect(String label) {
        return valueMap.containsKey(label);
    }

    public Scope getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean isRoot() {
        return parent == null;
    }
}
