package net.kamai.java.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCleaner {

    private String alphabet;

    private File currentFile;
    private File editedFile;

    public FileCleaner(String alphabet){
        this.alphabet = alphabet;
    }

    public void cleanFile (String path){
        currentFile = new File(path);
        createEditedFile();


        try(
                FileReader fileReader = new FileReader(currentFile);
                FileWriter fileWriter = new FileWriter(editedFile)
        )
        {

            int symb;

            if(alphabet.contains(" ")){
                boolean isPrevSpace = false;

                char chSymb;

                while((symb = fileReader.read()) != -1){
                    chSymb = Character.toLowerCase((char)symb);

                    if(!alphabet.contains(Character.toString(chSymb)))
                        chSymb = ' ';

                    if(chSymb == ' '){
                        if(isPrevSpace) continue;
                        isPrevSpace = true;
                    } else isPrevSpace = false;

                    writeClearSymbol(fileWriter, chSymb);
                }

            } else {

                while ((symb = fileReader.read()) != -1) {
                    writeClearSymbol(fileWriter, Character.toLowerCase((char) symb));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public String getEditedFilePath(){
        if(editedFile == null) return "";

        return editedFile.getPath();
    }

    public File getEditedFile(){
        return editedFile;
    }

    private void createEditedFile(){
        editedFile = new File(currentFile.getPath() + "_edited.txt");
        System.out.println(editedFile.getPath());
    }

    private void writeClearSymbol(FileWriter writer, char symbol) throws IOException{
        if(alphabet.contains(Character.toString(symbol)))
            writer.write(symbol);
    }
}
