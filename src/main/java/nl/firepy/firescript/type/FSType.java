package nl.firepy.firescript.type;

public class FSType {
    public static final FSType INT = new FSType("int");
    public static final FSType  FLOAT = new FSType("float");
    public static final FSType  BOOL = new FSType("bool");
    public static final FSType STRING = new FSType("string");
    public static final FSType CLASS = new FSType("class");
    public static final FSType OBJECT = new FSType("object");
    public static final FSType ARRAY = new FSType("array");
    public static final FSType FUNCTION = new FSType("function");

    private String internalType;
    private FSType parentType;
    private String externalType;

    private FSType(String internalType) {
        this.internalType = internalType;
        this.externalType = internalType;
        this.parentType = null;
    }

    public FSType(String typeName, FSType parentType) {
        this.internalType = parentType.internalType;
        this.parentType = parentType;
        this.externalType = typeName;
    }

    public String getTypeName() {
        return externalType;
    }
    
    public FSType getParentType() {
        return parentType;
    }

    public boolean isBaseType() {
        return internalType.equals(externalType);
    }
}
