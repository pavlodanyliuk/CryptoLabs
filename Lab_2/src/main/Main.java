package main;

import shiffre.ChiffreDeVigenere;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        /**
         * 1st TASK
         */

        String[] keys = {
                "по", // 2
                "три", // 3
                "клас", // 4
                "пьить", // 5
                "суперпуперключ" // 14
        };

        String fileNameIn = "resources/opentext";
        String fileNameOut = "resources/outtext";

        String alphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
        String freqAlphabet = "оеантилсрвкмдпуяьызгбчйжшхюцщэфъ";

        ChiffreDeVigenere.initAlphabet(alphabet);
        ChiffreDeVigenere.initFreqAlphabet(freqAlphabet);

        /**
         * 2nd TASK
         */

        System.out.println("Open text : " + ChiffreDeVigenere.calcMappingIndex(fileNameIn));

        for(int i = 0; i <keys.length; i++) {
            ChiffreDeVigenere.encrypt(keys[i], fileNameIn, fileNameOut + i);
            System.out.println( "Length of key = " + keys[i].length() + " : " + ChiffreDeVigenere.calcMappingIndex(fileNameOut + i));

        }

        /**
         * 3rd TASK
         */

        ChiffreDeVigenere.findKey("resources/crypt");

        //Key founded: возвращениеджинна
        String key = "возвращениеджинна";

        ChiffreDeVigenere.decrypt(key, "resources/crypt", "resources/decrypt");


    }



}
