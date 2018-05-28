package net.kamai.java.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LomatorM3000 {

    private final List<Character> alphabet;
    private String fileName;

    private List<String> trueFreq;
    private List<String> shipFreq;
    private List<String> badBigrams;

    private String Y;
    private String X;
    private String Y1;
    private String X1;


    public LomatorM3000(String alphabet, String fileName, List<String> trueFreq, List<String> shipFreq, List<String> badBigrams){

        char[] alph = alphabet.toCharArray();
        this.alphabet = new ArrayList<>(alph.length);

        for(int i = 0; i < alph.length; i++){
            this.alphabet.add(alph[i]);
        }

        this.fileName = fileName;

        this.trueFreq = trueFreq;
        this.shipFreq = shipFreq;

        this.badBigrams = badBigrams;

    }

    public void lomatNeStroit(){
       for ( int i = 0; i < 4; i++ ) {
           for (int j = 0; j< 5; j++ ) {
               for (int k = 0; k<5; k++) {
                   if(j == k) continue;
                   setPossibleXandY(i, j, k);

                   if( lomaaat() ) {
                       return;
                   }

               }
           }
       }


    }

    public boolean lomaaat () {
        //a * (x* - x**) = y* - y** mod m*m
        List<Integer> aAll = findA();

        if( aAll == null) return false;
        for( int a : aAll ) {
            // b = (Y* - aX*) mod m*m
            if(a % alphabet.size() == 0) continue;
            int b = findB(a);

            System.out.println("(a,b) : (" + a + "," + b + ")" );

            if (decrypt(a, b)) {
                System.out.println("MESSAGE FROM LomatorM300: YEAH, BABY. WE DID IT!");
                return true;
            }

        }

        return false;
    }

    private void setPossibleXandY (int i, int j, int k) {
        this.Y = shipFreq.get(j);
        this.X = trueFreq.get(i);

        this.Y1 = shipFreq.get(k);
        this.X1 = trueFreq.get(i+1);

        System.out.println("X* -> Y* (" + X + " -> "  + Y + ")");
        System.out.println("X** -> Y** (" + X1 + " -> "  + Y1 + ")");
    }

    private List<Integer> findA () {
        System.out.println("Finding a...");
        return MathSuperMan.resolveFkProblemLineSrav(
                mapBigramToNumber(X) -  mapBigramToNumber(X1),
                mapBigramToNumber(Y) - mapBigramToNumber(Y1),
                alphabet.size() * alphabet.size()
                );
    }

    private int findB ( int a ) {
        int b = (mapBigramToNumber( Y ) - a * mapBigramToNumber( X )) % (alphabet.size() * alphabet.size());

        b = MathSuperMan.onlyPositive(b, alphabet.size() * alphabet.size());

        return b;
    }

    public int mapBigramToNumber ( String bigram ) {
        char[] bigramChar = bigram.toCharArray();
        if(bigramChar.length != 2) throw new IllegalArgumentException();

        int num1 = alphabet.indexOf(bigramChar[0]);
        int num2 = alphabet.indexOf(bigramChar[1]);

        return num1 * alphabet.size() + num2;
    }

    public String mapNumberToBigram ( int number ) {
        int num2 = number % alphabet.size();
        int num1 = number / alphabet.size();

        return "" + alphabet.get(num1) + alphabet.get(num2);
    }

    public boolean decrypt ( int a, int b ) {

        try(
                FileReader reader = new FileReader( fileName );
                FileWriter writer = new FileWriter("resources/decrypt/" +  "decrypt" + "_" + a + "_" + b)
        )
        {

            char symb1;
            char symb2;

            int num1;
            int num2;

            while ((num1 = reader.read()) != -1 && (num2 = reader.read()) != -1){
                symb1 = (char)num1;
                symb2 = (char)num2;
                String bigram = transform("" + symb1 + symb2, a, b);
                if(badBigrams.contains(bigram)){
                    writer.close();
                    new File("resources/decrypt/" +  "decrypt" + "_" + a + "_" + b).delete();
                    return false;
                }
                writer.write(bigram);
            }

            return true;

        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public String transform ( String bigram, int a, int b ) {
        List<Integer> result  = MathSuperMan.resolveFkProblemLineSrav(a, mapBigramToNumber(bigram) - b, alphabet.size()*alphabet.size());
        return mapNumberToBigram(result.get(0));
    }

}
