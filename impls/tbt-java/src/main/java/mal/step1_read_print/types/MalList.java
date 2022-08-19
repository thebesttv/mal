package mal.step1_read_print.types;

import mal.step1_read_print.token.TokenType;

import java.util.List;

public class MalList extends MalType {
    private final MalType[] value;
    private final TokenType leftBracket, rightBracket;

    public MalList(List<MalType> l, TokenType leftBracket, TokenType rightBracket) {
        value = new MalType[l.size()];
        for (int i = 0; i < l.size(); i++)
            value[i] = l.get(i);
        this.leftBracket = leftBracket;
        this.rightBracket = rightBracket;
    }

    @Override public MalType[] value() {
        return value;
    }

    @Override public String toString() {
        // returns: (x1 x2 ... xn)
        StringBuilder res = new StringBuilder();
        res.append(leftBracket.getSpecialName());
        boolean first = true;
        for (MalType x : value) {
            if (first) first = false;
            else res.append(" ");
            res.append(x.toString());
        }
        res.append(rightBracket.getSpecialName());
        return res.toString();
    }
}
