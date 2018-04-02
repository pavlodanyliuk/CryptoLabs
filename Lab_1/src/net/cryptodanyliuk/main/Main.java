/**
*Practicum No. 1
*Danyliuk&Trifonov
*FB-51
**/


package net.cryptodanyliuk.main;

import net.cryptodanyliuk.entropy.EntropyCalculator;
import net.cryptodanyliuk.frequency.BigramFrequencyAnalyzer;
import net.cryptodanyliuk.frequency.CharacterFrequencyAnalyzer;
import net.cryptodanyliuk.utils.FileCleaner;

import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        String alph = "абвгдежзийклмнопрстуфхцчшщъыьэюя ";

        String path = "resourses/text";

        FileCleaner fileCleaner = new FileCleaner(alph);


        //Filtering
        fileCleaner.cleanFile(path);
        String clearPath = fileCleaner.getEditedFilePath();

        //Characters
        System.out.println("Calculating character frequency...");
        CharacterFrequencyAnalyzer frequencyAnalyzer = new CharacterFrequencyAnalyzer(alph, clearPath);
        frequencyAnalyzer.analyze();
        frequencyAnalyzer.printResults();

        System.out.print("Entropy H1 = ");
        System.out.println(new EntropyCalculator(alph).calculateEntropyH1(frequencyAnalyzer.getResults()));


        //Bigrams
        BigramFrequencyAnalyzer bigramFrequencyAnalyzer = new BigramFrequencyAnalyzer(alph, clearPath);

        bigramFrequencyAnalyzer.analyze();

        bigramFrequencyAnalyzer.printCrossingResult();
        System.out.println();
        System.out.print("Entropy H2 = ");
        System.out.println(new EntropyCalculator(alph).calculateEntropyH2(bigramFrequencyAnalyzer.getResultForCrossBiagram()));
        System.out.println("---------------------------------");


        bigramFrequencyAnalyzer.printNonCrossResult();

        System.out.print("Entropy H2 = ");
        System.out.println(new EntropyCalculator(alph).calculateEntropyH2(bigramFrequencyAnalyzer.getResultForNonCrossBiagram()));
        System.out.println("----------------------------------");

    }
}
