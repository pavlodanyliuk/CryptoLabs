package shiffre;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BlockReader {
    private ArrayList<FileReader> readers;
    private int cursor;

    private final int COUNT_READERS;

    public BlockReader(ArrayList<FileReader> readers){
        this.readers = readers;
        cursor = 0;
        COUNT_READERS = readers.size();
    }

    public int read() throws IOException {
        FileReader reader = readers.get(cursor % COUNT_READERS);
        cursor++;

        return reader.read();
    }

    public void close() throws IOException {
        for(FileReader reader : readers){
            reader.close();
        }
    }

}
