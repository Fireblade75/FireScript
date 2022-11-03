package nl.firepy.firescript.type;

import java.util.HashMap;

import nl.firepy.firescript.compiler.FunctionDescriptor;

public class CodeFileDescriptor {
    private String sourceName;
    private HashMap<String, FunctionDescriptor> functions = new HashMap<>();
    private HashMap<String, String> fields = new HashMap<>();

    public CodeFileDescriptor(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void addFunction(FunctionDescriptor function) {
        functions.put(function.getName(), function);
    }

    public FunctionDescriptor getFunction(String name) {
        return functions.get(name);
    }

    public void addField(String name, String type) {
        fields.put(name, type);
    }

    public HashMap<String, String> getFields() {
        return fields;
    }

    public int countFields() {
        return fields.size();
    }

    public String getField(String label) {
        return fields.get(label);
    }
}
