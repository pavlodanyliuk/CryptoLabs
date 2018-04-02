package shiffre;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChiffreDeVigenere {

    private static ArrayList<Character> alphabet = new ArrayList<>();

    private static ArrayList<Character> freqAlphabet = new ArrayList<>();


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


    public static void decrypt (String keyVigenere, String fileNameIn, String fileNameOut){

        char[] key = keyVigenere.toCharArray();
        String fullPath = fileNameIn + "_blocks"  + "/" + "step_" + keyVigenere.length() + "/";

        ArrayList<FileReader> readers = new ArrayList<>(keyVigenere.length());


        try (
                FileWriter writer = new FileWriter(fileNameOut)
        ) {
            BlockReader reader = new BlockReader(initReader(fullPath, key.length));

            int symbInt;
            int keyIndex;
            int cursor = 0;

            while((symbInt = reader.read()) != -1) {
                char symb = (char) symbInt;

                keyIndex = alphabet.indexOf( key[mod(cursor, key.length)] );
                cursor++;

                int result = mod(alphabet.indexOf(symb) - keyIndex, alphabet.size());

                writer.write(alphabet.get(result));
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void findKey (String fileNameIn){

        //1st Split into blocks for different keys
        //2nd Calculate indexes for different keys
        //3rd Chose the index which is closer to theoretical value

        ArrayList<Double> indexes = new ArrayList<>();

        for (int i = 1; i < 31; i++){

            indexes.add(calculateIndexForKey(fileNameIn, i));

        }

        int keyLen = getKeyLength(indexes);

        System.out.println("--------------\nResult key length : " + keyLen);
        System.out.println("--------------");

        String fullPath = fileNameIn + "_blocks"  + "/" + "step_" + keyLen + "/";

        ArrayList<Character> freqCharInBlocks = getTheMostFreqCharactersInBlocks(fullPath, keyLen);

        for(int i = 0; i < freqCharInBlocks.size(); i++){
            System.out.println("New letter founded! : " + caesar(freqCharInBlocks.get(i), alphabet.indexOf(freqAlphabet.get(0))));
        }

        System.out.println("--------------");


    }


    public static double calcMappingIndex(String fileName){
        Map<Character, MutableInt> frequency = getFreqCharacterInFile(fileName);

        int n = calcCountOfCharacter(frequency);

        double sum = 0;
        for(Map.Entry<Character, MutableInt> map : frequency.entrySet()){
            sum += map.getValue().getValue() * (map.getValue().getValue() - 1);

        }

        return  sum / (n*(n-1));
    }

    /**
     * Utils
     */

    private static int mod(int num, int module){
        while(num < 0){
            num += module;
        }
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

    private static String splitIntoBlocks (String fileNameIn, int step){
        ArrayList<FileWriter> writers = new ArrayList<>();
        String basicDirectory = fileNameIn + "_blocks"  + "/" + "step_" + step + "/";
        try(
                FileReader reader = new FileReader(fileNameIn)
        ) {


            for(int i = 0; i < step; i++) {
                File file = new File(basicDirectory+ "block_" + i);
                file.getParentFile().mkdirs();

                writers.add(new FileWriter(file));
            }


            int symbInt;
            char symb;
            int cursor = 0;

            while ((symbInt = reader.read()) != -1){
                symb = (char)symbInt;
                writers.get(cursor % step).write(symb);

                cursor++;
            }



            for(FileWriter writer : writers){
                writer.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return basicDirectory;
    }

    private static double calculateIndexForKey (String fileNameIn, int keyLen ) {
        String dir = splitIntoBlocks(fileNameIn, keyLen);

        double avgIndex = 0;
        for(int j = 0; j < keyLen; j++ ) {
            avgIndex += calcMappingIndex(dir + "block_" + j);
        }

        System.out.println(keyLen + " | " + avgIndex / keyLen);

        return avgIndex / keyLen;
    }

    private static int getKeyLength (ArrayList<Double> indexes){
        double maxIndex = Collections.max(indexes);

        int keyWithMaxValue = indexes.indexOf(maxIndex) + 1;

        //find length a least of keys, which are divisors of key

        int resultKeyLen = keyWithMaxValue;

        for(int i = 2; i < keyWithMaxValue; i++) {

            if (keyWithMaxValue % i == 0) {
                if (indexes.get(i - 1) * 1.06 > indexes.get(keyWithMaxValue - 1)) {
                    resultKeyLen = i;
                    break;
                }
            }
        }

        return resultKeyLen;
    }

    private static ArrayList<Character> getTheMostFreqCharactersInBlocks(String fullPath, int keyLen){
        ArrayList<Character> result = new ArrayList<>(keyLen);

        for(int i = 0; i < keyLen; i++){
            String fileName = fullPath + "block_" + i;

            result.add(getMostFreqCharacter(getFreqCharacterInFile(fileName)));
        }

        return result;
    }

    private static char getMostFreqCharacter (Map<Character, MutableInt> freq){
        double value = 0;
        Character result = null;
        for(Map.Entry<Character, MutableInt> frequency : freq.entrySet()){
            if (frequency.getValue().getValue() > value){
                value = frequency.getValue().getValue();
                result = frequency.getKey();
            }
        }
        return result;
    }

    private static Map<Character, MutableInt> getFreqCharacterInFile (String fileName){
        Map<Character, MutableInt> frequency = initMap();

        try(
                FileReader reader = new FileReader(fileName)
        ) {

            int symbInt;
            char symb;

            while ((symbInt = reader.read()) != -1){
                symb = (char)symbInt;
                frequency.get(symb).increment();
            }


        }catch (IOException e){
            e.printStackTrace();
        }
        return frequency;
    }

    private static char caesar (char mostFreqChar, int x){
        int k = mod(alphabet.indexOf(mostFreqChar) - x, alphabet.size());
        return alphabet.get(k);
    }

    private static ArrayList<FileReader> initReader(String fullPath, int countBlock) throws IOException {
        ArrayList<FileReader> readers = new ArrayList<>(countBlock);

        for (int i = 0; i < countBlock; i++){
            readers.add(new FileReader(fullPath + "block_" + i));
        }

        return readers;
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

    public static void initFreqAlphabet(String alphabetStr){
        char[] alph = alphabetStr.toCharArray();
        freqAlphabet = new ArrayList<>(alph.length);

        for(int i = 0; i < alph.length; i++){
            freqAlphabet.add(alph[i]);
        }
    }


    public static void setAlphabet(ArrayList alphabet) {
        ChiffreDeVigenere.alphabet = alphabet;
    }

    public static ArrayList<Character> getAlphabet() {
        return alphabet;
    }

    public static ArrayList<Character> getFreqAlphabet() {
        return freqAlphabet;
    }

    public static void setFreqAlphabet(ArrayList<Character> freqAlphabet) {
        ChiffreDeVigenere.freqAlphabet = freqAlphabet;
    }
}
