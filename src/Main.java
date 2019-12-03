import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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
            int argsNum = args.length;
            if (argsNum == 0) {
                badInput();
            } else {
                String functionName = args[0];
                switch (argsNum) {
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
                        break;
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

    /**
     * The class that has function that does manipulations on names.
     * In order that class works, the project must contain a names.txt file in the resources that has a list of english names.
     */
    public static class NamesMaster {
        private BufferedReader bufferedReader;

        /**
         * Initialize for the class.
         * Calls for a function that opens the stream reader.
         */
        public NamesMaster() {
            startReading();
        }

        /**
         * The function gets a string and counts how many names contain that particular string.
         *
         * @param string, the string the user want to count.
         * @return The amount of appearances .
         */
        public int CountSpecificString(String string) {
            int quantity = 0;
            String nextName;
            while ((nextName = nextName()) != null) {
                if (nextName.contains(string)) {
                    quantity++;
                }
            }
            return quantity;
        }

        /**
         * The function gets a number of length and returns all the sub-names with the given length.
         *
         * @param length, The length that the user wants.
         * @return A dictionary with string ( at the given length ) as key and the number of appearances.
         */
        public Map<String, Integer> CountAllStrings(int length) {
            //Calls for a function that return the dictionary, send a false argument because we do want the first uppercase letter.
            Map<String, Integer> dictionary = getDictByLength(length, false);
            return dictionary;
        }

        /**
         * The function gets a number of length and a boolean, and returns all the sub-names with the given length.
         *
         * @param length,  The length that the user wants.
         * @param toLower, Boolean that determines if throw the capital letters or to pay attention to them.
         * @return A dictionary with string ( at the given length ) as key and the number of appearances.
         */
        private Map<String, Integer> getDictByLength(int length, boolean toLower) {
            Map<String, Integer> dictionary = new HashMap<>();
            String nextName;
            while ((nextName = nextName()) != null) {
                //Throws capital letters if needed
                int br = 0;
                if (toLower) {
                    nextName = nextName.toLowerCase();
                }
                for (int i = 0; i + length < nextName.length() + 1; i++) {
                    String subByLength = nextName.substring(i, i + length);//check if add 1
                    addOneToQuantity(dictionary, subByLength);
                }
            }
            return dictionary;
        }

        /**
         * Function that return the string with certain length that appearance the most, return few if there is a tie.
         *
         * @param length, The length that the user wants.
         * @return A list of string that appear the most by some length.
         */
        public List<String> CountMaxString(int length) {
            //Calls for a function that return the dictionary, send a false argument because we do not want the first uppercase letter.
            Map<String, Integer> dictionary = getDictByLength(length, true);
            // get max appearances.
            List<String> maxStrings = getArgMaxList(dictionary);
            return maxStrings;
        }

        /**
         * A function that returns all the names that contain some string
         *
         * @param string, the string the user want to search in names.
         * @return A list of all the names that contain the string.
         */
        public List<String> AllIncludesString(String string) {
            string = string.toLowerCase();
            String nextName;
            List<String> allIncludesString = new LinkedList<>();
            while ((nextName = nextName()) != null) {
                nextName = nextName.toLowerCase();
                if (string.contains(nextName)) {
                    allIncludesString.add(nextName);
                }
            }
            return allIncludesString;
        }

        /**
         * A function that generate a random name.
         *
         * @return A name.
         */
        public String GenerateName() {
            //Initialize data structures for generating the name:
            Map<Character, Integer> firstLettersAppearances = new HashMap<>();
            Map<Character, Map<Character, Integer>> pairsCounting = new HashMap<>();
            Map<Integer, Integer> namesLength = new HashMap<>();
            InitializeDictionaries(firstLettersAppearances, pairsCounting, namesLength);
            //Generate the name:
            String generatedName = GenerateName(firstLettersAppearances, pairsCounting, namesLength);
            return generatedName;
        }

        /**
         * A function that generates a name by an existing data on characters appearances.
         *
         * @param firstLettersAppearances, details about names first character.
         * @param pairsCounting,           details about what character follows what character by statistics.
         * @return a name.
         */
        private String GenerateName(Map<Character, Integer> firstLettersAppearances, Map<Character, Map<Character, Integer>> pairsCounting, Map<Integer, Integer> namesLength) {
            String name = "";
            //Get the first letter for a name.
            Character prevLetter = createFirstLetter(firstLettersAppearances);
            name += prevLetter;
            int nameLength = getRandomLength(namesLength);
            //Add letters until gets to the length.
            for (int i = 1; i < nameLength; i++) {
                prevLetter = createNextLetter(pairsCounting, prevLetter);
                name += prevLetter;
            }
            return name;
        }

        /**
         * A function that uses existing data to return random length.
         *
         * @param namesLength, the lengths of existing names.
         * @return a length.
         */
        private int getRandomLength(Map<Integer, Integer> namesLength) {
            int totalNames = 0;
            int length = 6;
            //Count the number of all existing names.
            for (Integer quantity : namesLength.values()) {
                totalNames += quantity;
            }
            //Choose random number.
            int random = (int) (Math.random() * totalNames);
            //Choose the length by distribution.
            for (Map.Entry<Integer, Integer> pair : namesLength.entrySet()) {
                if (pair.getValue() >= random) {
                    length = pair.getKey();
                    return length;
                } else {
                    random -= pair.getValue();
                }
            }
            return length;
        }

        /**
         * A function that fills data to all needed dictionaries.
         *
         * @param firstLettersAppearances, dictionary the holds data for how many time each letter is a first letter of a name.
         * @param pairsCounting,           dictionary the holds data for how many each character follow each character.
         * @param namesLength,             dictionary the holds data for how many names exists in different lengths.
         */
        private void InitializeDictionaries(Map<Character, Integer> firstLettersAppearances, Map<Character, Map<Character, Integer>> pairsCounting, Map<Integer, Integer> namesLength) {
            String nextName;
            while ((nextName = nextName()) != null) {
                addOneToQuantity(namesLength, nextName.length());
                //Handle first characters dictionary:
                char first = nextName.charAt(0);
                addOneToQuantity(firstLettersAppearances, first);

                //Handle pairs dictionary:
                for (int i = 1; i < nextName.length(); i++) {
                    char curr = nextName.charAt(i);
                    char prev = nextName.charAt(i - 1);
                    Map<Character, Integer> prevDict;
                    if (pairsCounting.containsKey(prev)) {
                        prevDict = pairsCounting.get(prev);
                    } else {
                        prevDict = new HashMap<>();
                        pairsCounting.put(prev, prevDict);
                    }
                    addOneToQuantity(prevDict, curr);
                }
            }
        }

        /**
         * A function that returns a letter to be the first letter for a name.
         *
         * @param firstLettersAppearances, dictionary the holds data for how many time each letter is a first letter of a name.
         * @return a letter.
         */
        private Character createFirstLetter(Map<Character, Integer> firstLettersAppearances) {
            return getRandomArgMax(firstLettersAppearances);
        }

        /**
         * A function that returns a letter to be the next letter for a name.
         *
         * @param pairsCounting, dictionary the holds data for how many each character follow each character.
         * @param prevLetter,    The previous letter.
         * @return a letter.
         */
        private Character createNextLetter(Map<Character, Map<Character, Integer>> pairsCounting, Character prevLetter) {
            Map<Character, Integer> nextLetters = pairsCounting.get(prevLetter);
            return getRandomArgMax(nextLetters);
        }

        /**
         * A function that returns the object that appear the most times, return random if there is a tie.
         *
         * @param nextLetters, dictionary the holds data for a object and number of appearances.
         * @param <T>,         the object type.
         * @return the object the appear the maximum.
         */
        private static <T> T getRandomArgMax(Map<T, Integer> nextLetters) {
            List<T> argMax = getArgMaxList(nextLetters);
            int r = (int) (Math.random() * argMax.size());
            return argMax.get(r);
        }

        /**
         * A function that returns an object that has most appearances in certain dictionary.
         *
         * @param dictionary, the dictionary to get the data from.
         * @param <T>,        the object type.
         * @return List of object that appear the most.
         */
        private static <T> List<T> getArgMaxList(Map<T, Integer> dictionary) {
            int maxAmount = -1;
            List<T> maxList = new ArrayList<>();
            for (Map.Entry<T, Integer> entry : dictionary.entrySet()) {
                if (entry.getValue() >= maxAmount) {
                    if (entry.getValue() > maxAmount) {
                        maxAmount = entry.getValue();
                        maxList = new ArrayList<>();
                    }
                    maxList.add(entry.getKey());
                }
            }
            return maxList;
        }

        /**
         * A function that adds 1 to value to a certain value in certain dictionary.
         *
         * @param dictionary, the dictionary to manipulate.
         * @param key,        the key to add 1 too.
         * @param <T>,        the type of the object.
         */
        private static <T> void addOneToQuantity(Map<T, Integer> dictionary, T key) {
            int quantity = 0;
            if (dictionary.containsKey(key)) {
                quantity = dictionary.get(key);
            }
            dictionary.put(key, quantity + 1);
        }

        /**
         * A function the performs a classic stream opening for reading from a file.
         */
        private void startReading() {
            try {
                InputStream in = getClass().getResourceAsStream("/names.txt");
                bufferedReader = new BufferedReader(new InputStreamReader(in));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * A function that closes the reading stream.
         */
        public void finished() {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * A function that iterates over a stream from a file a returns the next line.
         *
         * @return a name.
         */
        private String nextName() {
            String name = null;
            try {
                name = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return name;
        }
    }
}
