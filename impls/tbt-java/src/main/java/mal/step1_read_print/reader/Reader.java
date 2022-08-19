package mal.step1_read_print.reader;

import mal.step1_read_print.token.Token;
import mal.step1_read_print.token.TokenType;
import mal.step1_read_print.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Reader {
    private final Logger logger = LoggerFactory.getLogger(Reader.class);

    private static final String REGEX =
            "[\\s,]*(~@|[\\[\\]{}()'`~^@]|\"(?:\\\\.|[^\\\\\"])*\"?|;.*|[^\\s\\[\\]{}('\"`,;)]*)";
    private static final Pattern pattern = Pattern.compile(REGEX);

    private final Queue<Token> tokens = new LinkedList<>();

    // parse string into AST
    public static MalType readStr(String s) {
        Reader r = new Reader(s);
        return r.readForm();
    }

    private Reader(String s) {
        tokenize(s);
        checkBrackets();
    }

    // return the token at current position and increments the position
    private Token next() {
        return tokens.poll();
    }

    // return the token at current position
    private Token peek() {
        return tokens.peek();
    }


    // parse & save all tokens in s to `tokens'
    private void tokenize(String input) {
        Matcher m = pattern.matcher(input);
        assert (m.groupCount() == 1);
        while (m.find()) {
            String target = m.group(1);
            if (target.isEmpty())
                continue;
            if (target.startsWith(";")) // skip comments
                break;
            tokens.add(new Token(target));
        }
        logger.debug("original tokens: {}", tokens);
        for (Token token : tokens)
            token.processString();
        logger.debug("after processing: {}", tokens);
    }

    private void checkBrackets() {
        Deque<TokenType> stack = new LinkedList<>();
        for (Token token : tokens) {
            TokenType type = token.getType();
            if (!type.isBracket())
                continue;

            if (type.isLeftBracket()) {
                // push left bracket to stack
                stack.push(type);
            } else {
                // try to match right bracket
                if (!stack.isEmpty()) {
                    TokenType leftBracket = stack.pop();
                    if (leftBracket.getRightBracketType() == type) {
                        // bracket matches
                        continue;
                    }
                }
                // bracket doesn't match
                throw new RuntimeException("unbalanced brackets");
            }
        }
        if (!stack.isEmpty())   // bracket doesn't match
            throw new RuntimeException("unbalanced brackets");
    }

    // return either a list or an atom (int, symbol, string, ...)
    private MalType readForm() {
        if (peek() == null)
            return null;

        TokenType type = peek().getType();
        if (type.isLeftBracket()) {
            next();             // discard left parenthesis
            return readList(type);
        } else if (type.isSpecialFunction()) {
            next();

            List<MalType> l = new LinkedList<>();
            l.add(new MalSymbol(type.getSpecialName()));

            if (type == TokenType.WITH_META) {
                MalType arg1 = readForm();
                MalType arg2 = readForm();
                l.add(arg2);
                l.add(arg1);
            } else {
                l.add(readForm());
            }

            return new MalList(l, TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN);
        } else {
            return readAtom();
        }
    }

    private MalList readList(TokenType leftBracket) {
        List<MalType> l = new ArrayList<>();
        TokenType rightBracket = leftBracket.getRightBracketType();
        while (true) {
            if (peek().getType() == rightBracket) {
                next();         // discard right parenthesis
                return new MalList(l, leftBracket, rightBracket);
            }
            l.add(readForm());
        }
    }

    private MalType readAtom() {
        Token token = next();  // get & discard current token
        String s = token.getValue();
        switch (token.getType()) {
            case NUMBER: return new MalInt(Integer.parseInt(s));
            case STRING: return new MalString(s);
            case SYMBOL: return new MalSymbol(s);
        }
        return null;
    }
}
