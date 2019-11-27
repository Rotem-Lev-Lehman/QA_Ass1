import java.io.*;
import java.net.URL;
import java.util.*;

public class NamesMaster {
    private String fileName = "names.txt";
    private File file;
    private BufferedReader bufferedReader;

    public NamesMaster() {
        ClassLoader classLoader = Main.class.getClassLoader();
        URL resource = classLoader.getResource("names.txt");
        file = new File(resource.getFile());
        startReading();
    }

    public void CountSpecificString(String name) {
        int counter = 0;
        String nextName;
        while ((nextName = nextName()) != null) {
            if (nextName.contains(name)) {
                counter++;
            }
        }
        System.out.println(counter);
    }

    public void CountAllStrings(int length) {
        Map<String, Integer> dictionary = getDictByLength(length, false);
        for (Map.Entry<String, Integer> pair : dictionary.entrySet()) {
            System.out.println(pair.getKey() + ":" + pair.getValue());
        }
    }

    private Map<String, Integer> getDictByLength(int length, boolean toLower) {
        Map<String, Integer> dictionary = new HashMap<>();
        String nextName;
        while ((nextName = nextName()) != null) {
            if (toLower) {
                nextName = nextName.toLowerCase();
            }
            for (int i = 0; i + length < nextName.length(); i++) {
                String str = nextName.substring(i, i + length);//check if add 1
                addOneToQuantity(dictionary, str);
            }
        }
        return dictionary;
    }

    public void CountMaxString(int length) {
        Map<String, Integer> dictionary = getDictByLength(length, true);
        List<String> maxStrings = getArgMaxList(dictionary);
        for (String str : maxStrings) {
            System.out.println(str);
        }
    }

    public void AllIncludesString(String name) {
        String nextName;
        while ((nextName = nextName()) != null) {
            if (name.contains(nextName)) {
                System.out.println(nextName);
            }
        }
    }

    public void GenerateName() {
        //Initialize data structures for generating the name:
        Map<Character, Integer> firstLettersAppearances = new HashMap<>();
        Map<Character, Map<Character, Integer>> pairsCounting = new HashMap<>();
        InitializeDictionaries(firstLettersAppearances, pairsCounting);

        //Generate the name:
        String generatedName = GenerateName(firstLettersAppearances, pairsCounting);

        //Print the name:
        System.out.println(generatedName);
    }

    private String GenerateName(Map<Character, Integer> firstLettersAppearances, Map<Character, Map<Character, Integer>> pairsCounting) {
        String name = "";
        Character prevLetter = createFirstLetter(firstLettersAppearances);
        name += prevLetter;
        for (int i = 0; i < 5; i++) {
            prevLetter = createNextLetter(pairsCounting, prevLetter);
            name += prevLetter;
        }
        return name;
    }

    private void InitializeDictionaries(Map<Character, Integer> firstLettersAppearances, Map<Character, Map<Character, Integer>> pairsCounting) {
        String nextName;
        while ((nextName = nextName()) != null) {
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

    private Character createFirstLetter(Map<Character, Integer> firstLettersAppearances) {
        return getRandomArgMax(firstLettersAppearances);
    }

    private Character createNextLetter(Map<Character, Map<Character, Integer>> pairsCounting, Character prevLetter) {
        Map<Character, Integer> prevDict = pairsCounting.get(prevLetter);
        return getRandomArgMax(prevDict);
    }

    private static <T> T getRandomArgMax(Map<T, Integer> dictionary) {
        List<T> argMax = getArgMaxList(dictionary);
        int r = (int) (Math.random() * argMax.size());
        return argMax.get(r);
    }

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

    private static <T> void addOneToQuantity(Map<T, Integer> dictionary, T key) {
        int quantity = 0;
        if (dictionary.containsKey(key)) {
            quantity = dictionary.get(key);
        }
        dictionary.put(key, quantity + 1);
    }

    private void startReading() {
        try {
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void finished() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String nextName() {
        String name = null;
        try {
            name = bufferedReader.readLine();
            if (name != null) {
                name = formatName(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String formatName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return name;
    }
}
