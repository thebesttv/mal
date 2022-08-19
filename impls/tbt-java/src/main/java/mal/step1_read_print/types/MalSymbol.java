package mal.step1_read_print.types;

public class MalSymbol extends MalType {
    private String value;

    public MalSymbol(String s) {
        value = s;
    }

    @Override public String value() {
        return value;
    }

    @Override public String toString() {
        // return "MalSymbol: " + value;
        return value;
    }
}
