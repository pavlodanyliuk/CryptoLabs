package net.cryptodanyliuk.entropy;

import java.util.Map;

public class EntropyCalculator {

    private char[] alphabet;

    public EntropyCalculator(String alphabet){
        this.alphabet = alphabet.toCharArray();
    }

    public double calculateEntropyH1(Map<Character, Double> frequency){
        double result = 0;

        for(int i = 0; i < alphabet.length; i++){
            double propability = frequency.get(alphabet[i]);
            result -=  propability * (Math.log(propability)/Math.log(2));
        }

        return result;
    }

    public double calculateEntropyH2(Map<String, Double> frequency){
        double result = 0;

        for(int i = 0; i < alphabet.length; i++){
            for(int j = 0; j < alphabet.length; j++) {
                double propability = frequency.get(Character.toString(alphabet[i]) + alphabet[j]);
                if(propability == 0) continue;
                result -= propability* (Math.log(propability)/Math.log(2));
            }
        }

        return result/2;
    }
}
