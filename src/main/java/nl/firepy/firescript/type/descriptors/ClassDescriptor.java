package nl.firepy.firescript.type.descriptors;

import java.util.HashMap;

import nl.firepy.firescript.component.expr.BlockVariable;

public class ClassDescriptor {
    private String className;
    private HashMap<String, FunctionDescriptor> functions = new HashMap<>();
    private HashMap<String, BlockVariable> variables = new HashMap<>();

    public ClassDescriptor(String className) {
        this.className = className;
    }
}
