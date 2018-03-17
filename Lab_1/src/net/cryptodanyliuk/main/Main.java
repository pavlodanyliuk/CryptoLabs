package net.cryptodanyliuk.main;

import net.cryptodanyliuk.frequency.BigramFrequencyAnalyzer;
import net.cryptodanyliuk.frequency.CharacterFrequencyAnalyzer;
import net.cryptodanyliuk.utils.FileCleaner;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String alph = "абвгдежзийклмнопрстуфхцчшщъыьэюя ";

        String path = "resourses/text_edited";

//        FileCleaner fileCleaner = new FileCleaner(alph);
//
//        fileCleaner.cleanFile(path);

//        CharacterFrequencyAnalyzer frequencyAnalyzer = new CharacterFrequencyAnalyzer(alph, path);
//        frequencyAnalyzer.analyze();
//        frequencyAnalyzer.printResults();

        BigramFrequencyAnalyzer bigramFrequencyAnalyzer = new BigramFrequencyAnalyzer(alph, path);
        bigramFrequencyAnalyzer.analyze();
        bigramFrequencyAnalyzer.printCrossingResult();
        bigramFrequencyAnalyzer.printNonCrossResult();

    }
}
