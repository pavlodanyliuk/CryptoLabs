package net.cryptodanyliuk.main;

import net.cryptodanyliuk.frequency.FrequencyAnalyzer;
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

        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer(alph, path);
        frequencyAnalyzer.analyze();
        frequencyAnalyzer.printResults();

    }
}
