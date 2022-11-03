package nl.firepy.firescript.compiler.scope;

import nl.firepy.firescript.type.CodeFileDescriptor;

public class GlobalScope {

    private String className;
    private CodeFileDescriptor classHeader;


    public GlobalScope(String className, CodeFileDescriptor classHeader) {
        super();
        this.className = className;
        this.classHeader = classHeader;
    }

    public CodeFileDescriptor getClassHeader() {
        return classHeader;
    }
}
