package nl.firepy.firescript.component.expr;

import nl.firepy.firescript.compiler.FireScriptParser.TypeContext;
import nl.firepy.firescript.component.internal.FireScriptExpression;
import nl.firepy.firescript.type.FSType;

public class TypeExpression implements FireScriptExpression {

    private FSType fsType;

    public TypeExpression(TypeContext ctx) {
        if (ctx.BOOL() != null) {
            this.fsType = FSType.BOOL;
        } else if (ctx.INT() != null) {
            this.fsType = FSType.BOOL;
        } else if (ctx.FLOAT() != null) {
            this.fsType = FSType.BOOL;
        } else if (ctx.STRING() != null) {
            this.fsType = FSType.BOOL;
        } else {
            throw new UnsupportedOperationException("Type " + ctx.getText() + " is currently not supported.");
        }
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public FSType getType() {
        return fsType;
    }
    
    @Override
    public String generateCode() {
        return fsType.getTypeName();
    }
}
