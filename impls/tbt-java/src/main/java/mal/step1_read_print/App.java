/*
 * TODO:
 * - Step 0: Add full line editing and command history support
 * - Step 1:
 *   - add printReadably to prStr
 *   - add type: nil, true, false, keyword, vector, hash-map
 * FIXME:
 * - in Reader, when no input is given (or input has only comment), return nil
 */

package mal.step1_read_print;

import mal.step1_read_print.reader.Reader;
import mal.step1_read_print.types.MalType;

import java.util.Scanner;

public class App {
    private static final Scanner sc = new Scanner(System.in);

    public static MalType READ(String s) {
        return Reader.readStr(s);
    }

    public static MalType EVAL(MalType ast) {
        return ast;
    }

    public static String PRINT(MalType ast) {
        return ast.toString();
    }

    public static String rep(String s) {
        return PRINT(EVAL(READ(s)));
    }

    public static void main(String[] args) {
        while (true) {
            System.out.print("user> ");
            if (!sc.hasNextLine())
                break;
            try {
                System.out.println(rep(sc.nextLine()));
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
