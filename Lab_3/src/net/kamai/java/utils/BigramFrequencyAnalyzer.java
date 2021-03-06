package net.kamai.java.utils;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class BigramFrequencyAnalyzer {
    private String doubleFormat = "#0.00";

    private String fileName;
    private String alphabet;

    private Map<String, MutableInt> quntCross;
    private Map<String, MutableInt> quntNon;

    private Map<String, Double> resultCross;
    private Map<String, Double> resultNon;

    public BigramFrequencyAnalyzer(String alphabet, String fileName){

        this.alphabet = alphabet;
        this.fileName = fileName;

        quntCross = new HashMap<>();
        quntNon = new HashMap<>();

        resultCross = new HashMap<>();
        resultNon = new HashMap<>();
    }

    public Map<String, Double> getResultForCrossBiagram() {
        return resultCross;
    }

    public Map<String, Double> getResultForNonCrossBiagram() {
        return resultNon;
    }

    public void analyze(){
        init();

        analyzeCrossingBigram();
        generCrossResult();

        analyzeNonCrossBigram();
        generNonResult();
    }

    private void analyzeCrossingBigram(){
        try(
                FileReader reader = new FileReader(fileName);
                )
        {

            char symb1 = 0;
            char symb2;

            int num = reader.read();
            if (num != -1) symb1 = (char)num;

            while ((num = reader.read()) != -1 ){
                symb2 = (char)num;
                quntCross.get(Character.toString(symb1) + symb2).increment();
                symb1 = symb2;
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void analyzeNonCrossBigram(){
        try(
                FileReader reader = new FileReader(fileName);
        )
        {

            char symb1;
            char symb2;

            int num1;
            int num2;

            while ((num1 = reader.read()) != -1 && (num2 = reader.read()) != -1){
                symb1 = (char)num1;
                symb2 = (char)num2;
                quntNon.get(Character.toString(symb1) + symb2).increment();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void init(){
        char[] alph = alphabet.toCharArray();

        String key;

        for(int i = 0; i < alph.length; i++){
            for(int j = 0; j < alph.length; j++){

                key = Character.toString(alph[i]) + alph[j];
                quntCross.put(key, new MutableInt());
                quntNon.put(key, new MutableInt());

            }
        }


    }

    private void generCrossResult(){
        int count = getCountOfCrossBigrams();

        for(Map.Entry<String, MutableInt> qun : quntCross.entrySet()){
            resultCross.put(qun.getKey(), ((double)qun.getValue().getValue())/count);
        }
    }

    private void generNonResult(){
        int count = getCountOfNonBigrams();

        for(Map.Entry<String, MutableInt> qun : quntNon.entrySet()){
            resultNon.put(qun.getKey(), ((double)qun.getValue().getValue())/count);
        }
    }

    private int getCountOfCrossBigrams(){
        int count = 0;
        for(Map.Entry<String, MutableInt> qun : quntCross.entrySet()){
            count += qun.getValue().getValue();
        }

        return count;
    }

    private int getCountOfNonBigrams(){
        int count = 0;
        for(Map.Entry<String, MutableInt> qun : quntNon.entrySet()){
            count += qun.getValue().getValue();
        }

        return count;
    }

    public void printCrossingResult(){
        System.out.println("Crossing Bigram");

        printPattern(resultCross);


    }

    public void printNonCrossResult(){
        System.out.println("Non Crossing Bigram");

        printPattern(resultNon);
    }

    public void printPattern(Map<String, Double> result){
        System.out.print("\\ ");

        char[] alph = alphabet.toCharArray();

        for(int i = 0; i < alphabet.length(); i++){
            System.out.print(alph[i] + "    ");
        }
        System.out.println();

        for(int i = 0; i < alph.length; i++){
            System.out.print(alph[i] + " ");

            for (int j = 0; j < alph.length; j++){
                String formattedDouble = new DecimalFormat(doubleFormat).format(result.get(Character.toString(alph[i]) + alph[j]) * 100);
                System.out.print(formattedDouble + " ") ;
            }
            System.out.println();
        }
    }

    public static Map<String, Double> getSortedMap(Map<String, Double> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
        }
    }

}
