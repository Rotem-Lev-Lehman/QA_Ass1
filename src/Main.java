import java.util.List;
import java.util.Map;

/**
 * Class that runs the program.
 */
public class Main {
    /**
     * A program that complete manipulations on a set of names and prints result the user depends on his request.
     * @param args, Should get 1 or 2 arguments, run the user request and finish program.
     */
    public static void main(String[] args) {
        //Initialize the names manipulation class.
        NamesMaster namesMaster = new NamesMaster();
        try {
            //Bad input because the program must receive 1 or 2 arguments.
            int argsNum = args.length;
            if(argsNum == 0){
                badInput();
            }
            else{
                String functionName = args[0];
                switch (argsNum){
                    case 1:
                        if (functionName.equals("GenerateName")) {
                            System.out.println(namesMaster.GenerateName());
                        } else { // Bad input, there no other function that called without arguments.
                            badInput();
                        }
                        break;
                    case 2:
                        String argument = args[1];
                        //Start to check what function the user want to use.
                        int length;
                        switch (functionName) {
                            case "CountSpecificString":
                                System.out.println(namesMaster.CountSpecificString(argument));
                                break;
                            case "CountAllStrings":
                                length = Integer.parseInt(argument);
                                if (length <= 0) {
                                    badInput();
                                } else {
                                    Map<String, Integer> dictionary = namesMaster.CountAllStrings(length);
                                    for (Map.Entry<String, Integer> pair : dictionary.entrySet()) {
                                        System.out.println(pair.getKey() + ":" + pair.getValue());
                                    }
                                }
                                break;
                            case "CountMaxString":
                                length = Integer.parseInt(argument);
                                if (length <= 0) {
                                    badInput();
                                } else {
                                    List<String> maxStrings = namesMaster.CountMaxString(length);
                                    for (String str : maxStrings) {
                                        System.out.println(str);
                                    }
                                }
                                break;
                            case "AllIncludesString":
                                List<String> allIncludesString = namesMaster.AllIncludesString(argument);
                                for (String str : allIncludesString) {
                                    System.out.println(str);
                                }
                                break;
                            default:
                                badInput();
                        }
                    default:
                        badInput();
                }
            }
        //Print alert to user, if program crashed it because of bad input because the program is perfect (: .
        } catch (Exception e) {
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
