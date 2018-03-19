package shiffre;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiffreDeVigenere {

    private static ArrayList<Character> alphabet = new ArrayList<>();


    private ChiffreDeVigenere(){
        super();
    }


    public static void encrypt(String keyVigenere, String fileNameIn, String fileNameOut){

        char[] key = keyVigenere.toCharArray();

        try(
                FileReader reader = new FileReader(fileNameIn);
                FileWriter writer = new FileWriter(fileNameOut)
                )
        {
            int cursor = 0;
            int module = alphabet.size();

            int symbInt;
            char symbol;

            int keyIdex;

            while ((symbInt = reader.read()) != -1){

                symbol = (char)symbInt;
                keyIdex = alphabet.indexOf(key[mod(cursor, key.length)]);
                cursor++;

                int result = mod(alphabet.indexOf(symbol) + keyIdex, module);

                writer.write(alphabet.get(result));

            }


        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void decrypt(String fileNameIn, String fileNameOut){
    }


    public static double calcMappingIndex(String fileName){
        Map<Character, MutableInt> frequency = initMap();

        try(
                FileReader reader = new FileReader(fileName)
                )
        {

            int symbInt;
            char symb;

            while ((symbInt = reader.read()) != -1){
                symb = (char)symbInt;
                frequency.get(symb).increment();
            }


        }catch (IOException e){
            e.printStackTrace();
        }

        int n = calcCountOfCharacter(frequency);

        System.out.println(n);

        double sum = 0;
        for(Map.Entry<Character, MutableInt> map : frequency.entrySet()){
            sum += map.getValue().getValue() * (map.getValue().getValue() - 1);

        }

        double i =  sum / (n*(n-1));

        return i;
    }

    /**
     * Utils
     */

    private static int mod(int num, int module){
        return num%module;
    }

    private static Map<Character, MutableInt> initMap(){
        Map<Character, MutableInt> map = new HashMap<>();

        for(char lit : alphabet){
            map.put(lit, new MutableInt());
        }

        return map;
    }

    private static int calcCountOfCharacter(Map<Character, MutableInt> maps){
        int count = 0;
        for(Map.Entry<Character, MutableInt> map : maps.entrySet()){
            count += map.getValue().getValue();
        }

        return count;
    }


    /**
     * Geters, setters, inits methods
     */
    public static void initAlphabet(String alphabetStr){
        char[] alph = alphabetStr.toCharArray();
        alphabet = new ArrayList<>(alph.length);

        for(int i = 0; i < alph.length; i++){
            alphabet.add(alph[i]);
        }
    }

    public static void setAlphabet(ArrayList alphabet) {
        ChiffreDeVigenere.alphabet = alphabet;
    }

    public static ArrayList<Character> getAlphabet() {
        return alphabet;
    }

}
