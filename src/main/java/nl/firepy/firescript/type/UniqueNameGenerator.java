package nl.firepy.firescript.type;

public class UniqueNameGenerator {
    private static int nextInt = 1;

    private String getUniqueName(char symbol) {
        return "fpyi_"+symbol+"_" + nextInt++;
    }

    String getFunctionName() {
        return getUniqueName('f');
    }

    String getVariableName() {
        return getUniqueName('v');
    }

    String getConstantName() {
        return getUniqueName('r');
    }

    String getClassName() {
        return getUniqueName('c');
    }

    String getObjectName() {
        return getUniqueName('o');
    }
}
