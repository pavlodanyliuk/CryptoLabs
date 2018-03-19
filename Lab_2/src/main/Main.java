package main;

import shiffre.ChiffreDeVigenere;

public class Main {

    public static void main(String[] args) {
        String[] keys = {
                "по",
                "три",
                "клас",
                "пьить",
                "суперпуперключ"
        };

        String fileNameIn = "resources/opentext";
        String fileNameOut = "resources/outtext";

        String alphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюя";

        ChiffreDeVigenere.initAlphabet(alphabet);

        for(int i = 0; i <1; i++) {
            ChiffreDeVigenere.encrypt(keys[i], fileNameIn, fileNameOut + i);
            System.out.println(fileNameOut + i + " : " + ChiffreDeVigenere.calcMappingIndex(fileNameOut + i));

        }


    }
}
