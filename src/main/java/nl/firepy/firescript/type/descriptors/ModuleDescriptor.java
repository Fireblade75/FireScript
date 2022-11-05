package nl.firepy.firescript.type.descriptors;

import java.util.HashMap;

import nl.firepy.firescript.compiler.LegacyFunctionDescriptor;
import nl.firepy.firescript.component.expr.RootVariable;
import nl.firepy.firescript.component.expr.Variable;

public class ModuleDescriptor {
    
    private ModuleName moduleName;
    private HashMap<String, FunctionDescriptor> functions = new HashMap<>();
    private HashMap<String, ClassDescriptor> classes = new HashMap<>();
    private HashMap<String, RootVariable> variables = new HashMap<>();

    private ClassDescriptor activeClass = null;
    private FunctionDescriptor activeFunction = null;

    public ModuleDescriptor(String sourceName) {
        this.moduleName = new ModuleName(sourceName);
    }

    public void openFunction(String functionName) {
        if(activeFunction != null) {
            throw new RuntimeException("Subfunctions are not supported");
        }
        activeFunction = new FunctionDescriptor(functionName);
        functions.put(functionName, activeFunction);
    }

    public void closeFunction() {
        if(activeFunction == null) {
            throw new RuntimeException("No function has been openned");
        }
        activeFunction = null;
    }

    public FunctionDescriptor getFunction(String name) {
        return functions.get(name);
    }

    public void addVariable(String name, RootVariable variable) {
        variables.put(name, variable);
    }

    public Variable getField(String label) {
        return variables.get(label);
    }
}
