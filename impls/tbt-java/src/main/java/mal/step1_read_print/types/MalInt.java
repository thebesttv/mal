package mal.step1_read_print.types;

public class MalInt extends MalType {
    private int value;

    public MalInt(int i) {
        value = i;
    }

    @Override public Integer value() {
        return value;
    }

    @Override public String toString() {
        // return "MalInt: " + value;
        return "" + value;
    }
}

