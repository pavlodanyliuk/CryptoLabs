package net.cryptodanyliuk.frequency;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CharacterFrequencyAnalyzer {

    private String doubleFormat = "#0.00";

    private String fileName;
    private String alphabet;

    private Map<Character, MutableInt> quantity;
    private Map<Character, Double> results;

    public CharacterFrequencyAnalyzer ( String alphabet, String fileName ){

        this.alphabet = alphabet;
        this.fileName = fileName;

        results = new HashMap<>();

        quantity = new HashMap<>();

    }

    public void analyze(){
        init();

        try(
                FileReader reader = new FileReader(fileName)
        )
        {

            int symbol;
            char lit;

            while ((symbol = reader.read()) != -1){
                lit = (char)symbol;
                quantity.get(lit).increment();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        genResults();
    }


    public void printResults(){

        Map<Character, Double> map = getSortedMap();

        System.out.println("Character | %");

        for(Map.Entry<Character, Double> result : map.entrySet()){
            String formattedDouble = new DecimalFormat(doubleFormat).format(result.getValue()*100);
            System.out.println(result.getKey() + "         | " + formattedDouble);
        }
    }


    public Map<Character, Double> getSortedMap(){
        ValueComparator comparator = new ValueComparator(results);
        TreeMap<Character, Double> sortedMap = new TreeMap<>(comparator);

        sortedMap.putAll(results);

        return sortedMap;
    }

    public Map<Character, Double> getResults(){
        return results;
    }


    private void init(){
        for(char lit : alphabet.toCharArray()){
            quantity.put(lit, new MutableInt());
        }
    }

    private void genResults(){
        int count = getCountOfCharacters();

        for(Map.Entry<Character, MutableInt> qun : quantity.entrySet()){
            results.put(qun.getKey(), ((double)qun.getValue().getValue())/count);
        }
    }

    private int getCountOfCharacters(){
        int count = 0;
        for(Map.Entry<Character, MutableInt> qun : quantity.entrySet()){
            count += qun.getValue().getValue();
        }

        return count;
    }

}

class ValueComparator implements Comparator<Character> {
    Map<Character, Double> base;

    public ValueComparator(Map<Character, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(Character a, Character b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
