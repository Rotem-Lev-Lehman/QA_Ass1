
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

    public void CountSpecificString(String name){
        int counter = 0;
        String nextName;
        while((nextName=nextName())!=null){
            if(nextName.contains(name)){
                counter++;
            }
        }
        System.out.println(counter);
    }

    public void CountAllStrings(int length){
        Map<String,Integer> dictionary = getDictByLength(length,false);
        for (Map.Entry<String,Integer> pair:dictionary.entrySet()) {
            System.out.println(pair.getKey()+":"+pair.getValue());
        }
    }

    private Map<String, Integer> getDictByLength(int length,boolean toLower) {
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        String nextName;
        while((nextName=nextName())!=null){
            if (toLower) {
                nextName = nextName.toLowerCase();
            }
            for(int i=0;i+length<nextName.length();i++){
                String str = nextName.substring(i,i+length);//check if add 1
                int quantity = 0;
                if(dictionary.containsKey(str)){
                    quantity = dictionary.get(str);
                }
                dictionary.put(str,quantity+1);
            }
        }
        return dictionary;
    }

    public void CountMaxString(int length){
        Map<String,Integer> dictionary = getDictByLength(length,true);
        List<String> maxStrings = new LinkedList<>();
        int maxQuantity = -1;
        for (Map.Entry<String,Integer> pair:dictionary.entrySet()) {
            if (pair.getValue() >= maxQuantity){
                if (pair.getValue() > maxQuantity){
                    maxQuantity = pair.getValue();
                    maxStrings = new LinkedList<>();
                }
                maxStrings.add(pair.getKey());
            }
        }
        for (String str:maxStrings) {
            System.out.println(str);
        }
    }

    public void AllIncludesString(String name){
        String nextName;
        while((nextName=nextName())!=null){
            if(name.contains(nextName)){
                System.out.println(nextName);
            }
        }
    }

    public void GenerateName(){

    }



    private void startReading(){
        try {
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void finished(){
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String nextName(){
        String name = null;
        try {
            name = bufferedReader.readLine();
            if (name!=null){
                name = formatName(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String formatName(String name) {
        name = name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase();
        return name;
    }
}
