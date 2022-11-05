package nl.firepy.firescript.type.descriptors;

public class ModuleName {
    private String sourceName;
    private String namespace;

    public ModuleName(String sourceName) {
        this.sourceName = sourceName;
        this.namespace = "";
    }

    public ModuleName(String sourceName, String namespace) {
        this.sourceName = sourceName;
        this.namespace = namespace;
    }

    public String getFullModuleName() {
        if(namespace.length() > 1) {
        return namespace + "." + sourceName;
        } else {
            return "default." + sourceName;
        }
    }
}
