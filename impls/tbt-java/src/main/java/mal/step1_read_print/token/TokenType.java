package mal.step1_read_print.token;

public enum TokenType {
    LEFT_PAREN, RIGHT_PAREN,      // ( )
    LEFT_SQUARE, RIGHT_SQUARE,    // [ ]
    LEFT_CURLY, RIGHT_CURLY,      // { }
    QUOTE,              // '
    QUASIQUOTE,         // `
    UNQUITE,            // ~
    SPLICE_UNQUOTE,     // ~@
    DEREF,              // @
    WITH_META,          // ^
    NUMBER,
    SYMBOL,
    STRING,
    ILLEGAL;

    // return type of token
    public static TokenType getTokenType(String token) {
        if (token == null || token.isEmpty())
            return ILLEGAL;
        switch (token) {
            case "(": return LEFT_PAREN;
            case ")": return RIGHT_PAREN;
            case "[": return LEFT_SQUARE;
            case "]": return RIGHT_SQUARE;
            case "{": return LEFT_CURLY;
            case "}": return RIGHT_CURLY;

            case "'": return QUOTE;
            case "`": return QUASIQUOTE;
            case "~": return UNQUITE;
            case "~@": return SPLICE_UNQUOTE;
            case "@": return DEREF;
            case "^": return WITH_META;
        }

        char ch = token.charAt(0);
        if (ch == '"')
            return STRING;
        if (Character.isDigit(ch))
            return NUMBER;
        return SYMBOL;
    }

    public boolean isSpecialFunction() {
        switch (this) {
            case QUOTE:
            case QUASIQUOTE:
            case UNQUITE:
            case SPLICE_UNQUOTE:
            case DEREF:
            case WITH_META:
                return true;
        }
        return false;
    }

    public boolean isBracket() {
        switch (this) {
            case LEFT_PAREN:
            case LEFT_SQUARE:
            case LEFT_CURLY:
            case RIGHT_PAREN:
            case RIGHT_SQUARE:
            case RIGHT_CURLY:
                return true;
        }
        return false;
    }

    public boolean isLeftBracket() {
        switch (this) {
            case LEFT_PAREN:
            case LEFT_SQUARE:
            case LEFT_CURLY:
                return true;
        }
        return false;
    }

    public TokenType getRightBracketType() {
        switch (this) {
            case LEFT_PAREN: return RIGHT_PAREN;
            case LEFT_CURLY: return RIGHT_CURLY;
            case LEFT_SQUARE: return RIGHT_SQUARE;
            default: return ILLEGAL;
        }
    }

    // return name of brackets or special functions
    public String getSpecialName() {
        switch (this) {
            // brackets
            case LEFT_PAREN:   return "(";
            case RIGHT_PAREN:  return ")";
            case LEFT_CURLY:   return "{";
            case RIGHT_CURLY:  return "}";
            case LEFT_SQUARE:  return "[";
            case RIGHT_SQUARE: return "]";
            // special functions
            case QUOTE:         return "quote";
            case QUASIQUOTE:    return "quasiquote";
            case UNQUITE:       return "unquote";
            case SPLICE_UNQUOTE:return "splice-unquote";
            case DEREF:         return "deref";
            case WITH_META:     return "with-meta";
            default:            return null;
        }
    }
}
