/*
 * TODO: Add full line editing and command history support
 */

import java.util.Scanner;

public class step0_repl {
    private static Scanner sc = new Scanner(System.in);

    public static String READ(String s) {
        return s;
    }

    public static String EVAL(String s) {
        return s;
    }

    public static String PRINT(String s) {
        return s;
    }

    public static String rep(String s0) {
        String s1 = READ(s0);
        String s2 = EVAL(s1);
        String s3 = PRINT(s2);
        return s3;
    }

    public static void main(String[] args) {
        while(true) {
            System.out.print("user> ");
            if(!sc.hasNextLine())
                break;
            String input = sc.nextLine();
            String res = rep(input);
            System.out.println(res);
        }
    }
}
