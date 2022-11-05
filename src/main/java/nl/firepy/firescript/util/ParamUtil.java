package nl.firepy.firescript.util;

import java.util.List;
import java.util.stream.Collectors;

import nl.firepy.firescript.type.Param;

public class ParamUtil {
    
    public static String getParamList(List<Param> params) {
        String paramList = params.stream()
            .map(param -> param.getParameterName())
            .collect(Collectors.joining(", "));
        
        return "(" + paramList + ")";
    }
}
