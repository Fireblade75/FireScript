package nl.firepy.firescript.type;

public class Param {
    private String parameterName;
    private FSType parameterType;

    public Param(String parameterName, FSType parameterType) {
        this.parameterName = parameterName;
        this.parameterType = parameterType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public FSType getParameterType() {
        return parameterType;
    }
}
