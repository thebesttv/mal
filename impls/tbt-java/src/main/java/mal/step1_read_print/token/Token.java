package mal.step1_read_print.token;

public class Token {
    private TokenType type;
    private String value;

    public Token(String v) {
        type = TokenType.getTokenType(v);
        value = v;
    }

    public void processString() {
        if (type != TokenType.STRING)
            return;

        if (value.length() < 2 || !value.endsWith("\""))
            throw new RuntimeException("unbalanced string (not surrounded with quotes)");

        // discard surrounding quotes
        value = value.substring(1, value.length() - 1);

        StringBuilder builder = new StringBuilder();
        char[] array = value.toCharArray();
        for (int i = 0; i < array.length; ) {
            char ch = array[i++];
            if (ch == '\\') {
                if (i >= array.length)
                    throw new RuntimeException("unbalanced string (error due to escape sequences)");
                // Only escape according to MAL.  For a full list of escape sequences, see
                // https://docs.oracle.com/javase/tutorial/java/data/characters.html
                char target = array[i++];
                char escaped;
                switch (target) {
//                    case 't':  escaped = '\t'; break;
//                    case 'b':  escaped = '\b'; break;
                    case 'n':  escaped = '\n'; break;
//                    case 'r':  escaped = '\r'; break;
//                    case 'f':  escaped = '\f'; break;
//                    case '\'': escaped = '\''; break;
                    case '"':  escaped = '\"'; break;
                    case '\\': escaped = '\\'; break;
                    default:   escaped = target; break;
                }
                System.out.println("target " + target + " get " + escaped);
                builder.append(escaped);
            } else {
                builder.append(ch);
            }
        }
        value = builder.toString();
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (type.isBracket())
            return "'" + value + "'";
        else
            return type.toString() + "(" + value + ")";
    }
}
