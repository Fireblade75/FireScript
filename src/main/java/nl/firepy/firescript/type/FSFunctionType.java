package nl.firepy.firescript.type;

import java.util.List;

import nl.firepy.firescript.util.ParamUtil;

public class FSFunctionType extends FSType {

    public FSFunctionType(List<Param> params, FSType returnType) {
        super(generateFunctionType(params, returnType), FSType.FUNCTION);
    }
    
    private static String generateFunctionType(List<Param> params, FSType returnType) {
        return ParamUtil.getParamList(params) + ": " + returnType;
    }
}
