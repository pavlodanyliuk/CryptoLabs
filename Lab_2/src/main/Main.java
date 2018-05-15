

/**
 *Practicum No. 1
 *Danyliuk&Trifonov
 *FB-51
 **/

package main;

import shiffre.ChiffreDeVigenere;


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
                "суперпуперключдужедовгий" // 14
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

        System.out.println("Length of key | Index");
        System.out.println("            - | " + ChiffreDeVigenere.calcMappingIndex(fileNameIn));

        for(int i = 0; i <keys.length; i++) {
            ChiffreDeVigenere.encrypt(keys[i], fileNameIn, fileNameOut + i);
            System.out.println( "            "+ keys[i].length() + " | " + ChiffreDeVigenere.calcMappingIndex(fileNameOut + i));

        }

        /**
         * 3rd TASK
         */

        String cryptFileName = "resources/cryptV_edited";

        ChiffreDeVigenere.findKey(cryptFileName);

        //Key founded: возвращениеджинна
        String key = "вшекспирбуря";

        ChiffreDeVigenere.decrypt(key, cryptFileName, cryptFileName + "_decrypt");


    }



}
