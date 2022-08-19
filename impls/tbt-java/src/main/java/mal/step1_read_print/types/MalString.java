package mal.step1_read_print.types;

public class MalString extends MalType {
    private String value;

    public MalString(String s) {
        value = s;
    }

    @Override public String value() {
        return value;
    }

    @Override public String toString() {
        // return "MalString: " + value;
        return '"' + escape() + '"';
    }

    private String escape() {
        return value
                .replace("\\", "\\\\")
//                .replace("\t", "\\t")
//                .replace("\b", "\\b")
                .replace("\n", "\\n")
//                .replace("\r", "\\r")
//                .replace("\f", "\\f")
//                .replace("\'", "\\'")
                .replace("\"", "\\\"");
    }
}
