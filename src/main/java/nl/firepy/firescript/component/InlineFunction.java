package nl.firepy.firescript.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.firepy.firescript.component.internal.FireScriptExpression;
import nl.firepy.firescript.type.FSFunctionType;
import nl.firepy.firescript.type.FSType;
import nl.firepy.firescript.type.Param;
import nl.firepy.firescript.util.ParamUtil;

public class InlineFunction implements FireScriptExpression {

    private List<Param> params;
    private FSType returnType;
    private BlockComponent functionBlock;

    public InlineFunction(List<Param> params, FSType returnType, BlockComponent functionBlock) {
        this.params = params;
        this.returnType = returnType;
        this.functionBlock = functionBlock;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public FSType getType() {
        return new FSFunctionType(params, returnType);
    }

    @Override
    public String generateCode() {
        ArrayList<String> code = new ArrayList<>();
        code.add("function" + ParamUtil.getParamList(params));
        code.addAll(functionBlock.generateCode());
        code.add("end");
        
        return code.stream().collect(Collectors.joining("\n"));
    }

    
    
}
