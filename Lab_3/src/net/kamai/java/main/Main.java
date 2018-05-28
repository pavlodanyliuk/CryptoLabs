package net.kamai.java.main;

import net.kamai.java.utils.BigramFrequencyAnalyzer;
import net.kamai.java.utils.FileCleaner;
import net.kamai.java.utils.LomatorM3000;
import net.kamai.java.utils.MathSuperMan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        String alph = "абвгдежзийклмнопрстуфхцчшщьыэюя";
        String path = "resources/crypt_20";
        List<String> topFreq = Arrays.asList("ст", "то", "но", "ен", "на");
        List<String> badBigrams = Arrays.asList(
                "аы", "аь", "гы", "гь", "гю", "еы", "еь", "жы", "жю", "иы", "иь", "йы", "йь", "кы",
                "кь", "оы", "оь", "уы", "уь", "фь", "хы", "ць", "чы", "шы", "щы", "ыь", "ьы", "ьь",
                "эе", "эж", "эу", "эч", "эщ", "эы", "эь", "ээ", "эю", "эя", "юы", "юь", "яы"
        );


        FileCleaner fileCleaner = new FileCleaner(alph);

        //Filtering
        fileCleaner.cleanFile(path);
        String clearPath = fileCleaner.getEditedFilePath();

        /**
         * 1st
         */

//        MathSuperMan.resolveFkProblemLineSrav(2, 4, 6);

        /**
         * 2nd
         */

        BigramFrequencyAnalyzer bigramFrequencyAnalyzer = new BigramFrequencyAnalyzer(alph, clearPath);

        bigramFrequencyAnalyzer.analyze();

        //bigramFrequencyAnalyzer.printMap(bigramFrequencyAnalyzer.getSortedMap(bigramFrequencyAnalyzer.getResultForNonCrossBiagram()));

        List<String> topFive = BigramFrequencyAnalyzer.getSortedMap(bigramFrequencyAnalyzer.getResultForNonCrossBiagram()).keySet()
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        System.out.print("Top five bigrams into text: [ ");
        topFive.forEach( b -> System.out.print(" " + b + " ") );
        System.out.println("]");


        /**
         * 3rd
         */


        LomatorM3000 lomator = new LomatorM3000(alph, clearPath, topFreq, topFive, badBigrams);
        lomator.lomatNeStroit();


    }
}
