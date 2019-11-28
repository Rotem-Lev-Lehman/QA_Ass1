import java.util.List;
import java.util.Map;

/**
 * Class that runs the program.
 */
public class Main {
    /**
     * A program that complete manipulations on a set of names and prints result the user depends on his request.
     *
     * @param args, Should get 1 or 2 arguments, run the user request and finish program.
     */
    public static void main(String[] args) {
        //Initialize the names manipulation class.
        NamesMaster namesMaster = new NamesMaster();
        try {
            //Bad input because the program must receive 1 or 2 arguments.
            if (args.length == 0) {
                badInput();
            } else {
                String functionName = args[0];
                if (args.length == 1) {
                    if (functionName.equals("GenerateName")) {
                        System.out.println(namesMaster.GenerateName());
                    } else { // Bad input, there no other function that called without arguments.
                        badInput();
                    }
                } else if (args.length == 2) {
                    String argument = args[1];
                    //Start to check what function the user want to use.
                    if (functionName.equals("CountSpecificString")) {
                        System.out.println(namesMaster.CountSpecificString(argument));
                    } else if (functionName.equals("CountAllStrings")) {
                        int length = Integer.parseInt(argument);
                        if (length <= 0) {
                            badInput();
                        } else {
                            Map<String, Integer> dictionary = namesMaster.CountAllStrings(length);
                            for (Map.Entry<String, Integer> pair : dictionary.entrySet()) {
                                System.out.println(pair.getKey() + ":" + pair.getValue());
                            }
                        }
                    } else if (functionName.equals("CountMaxString")) {
                        int length = Integer.parseInt(argument);
                        if (length <= 0) {
                            badInput();
                        } else {
                            List<String> maxStrings = namesMaster.CountMaxString(length);
                            for (String str : maxStrings) {
                                System.out.println(str);
                            }
                        }
                    } else if (argument.equals("AllIncludesString")) {
                        List<String> allIncludesString = namesMaster.AllIncludesString(argument);
                        for (String str : allIncludesString) {
                            System.out.println(str);
                        }
                    } else { // Bad input, there no other function that called with one argument.
                        badInput();
                    }
                    // Bad input because there cant be more than 2 arguments
                } else {
                    badInput();
                }
            }

        } catch (Exception e) {
            //Print alert to user, if program crashed it because of bad input because the program is perfect (: .
            badInput();
        }
        //Informers the namesMaster that the program finished using it.
        namesMaster.finished();
    }

    /**
     * A function that prints to the user that he input bad parameters.
     */
    private static void badInput() {
        System.out.println("Bad parameters");
    }
}
