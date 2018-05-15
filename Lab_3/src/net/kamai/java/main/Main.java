package net.kamai.java.main;

import net.kamai.java.utils.BigramFrequencyAnalyzer;
import net.kamai.java.utils.FileCleaner;
import net.kamai.java.utils.MathSuperMan;

public class Main {

    public static void main(String[] args) {

        String alph = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
        String path = "resources/crypt";

        FileCleaner fileCleaner = new FileCleaner(alph);

        //Filtering
        fileCleaner.cleanFile(path);
        String clearPath = fileCleaner.getEditedFilePath();

        /**
         *  1 rst
         */
        MathSuperMan.resolveFkProblemLineSrav(2, 5, 6);

        /**
         * 2nd
         */

        BigramFrequencyAnalyzer bigramFrequencyAnalyzer = new BigramFrequencyAnalyzer(alph, clearPath);

        bigramFrequencyAnalyzer.analyze();

        bigramFrequencyAnalyzer.printMap(bigramFrequencyAnalyzer.getSortedMap(bigramFrequencyAnalyzer.getResultForNonCrossBiagram()));
    }
}
