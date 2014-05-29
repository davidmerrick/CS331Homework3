import java.util.list;
import java.io.BufferedReader;
import java.io.IOException;

public class PreProcessor {

    private List<String> stopWords;

    public PreProcessor()
    {

    }

    public initStopWords(string filename){
        ReadWriteTextFileJDK7 text = new ReadWriteTextFileJDK7();
        this.stopWords = new ArrayList<String>();

        //treat as a small file
        this.stopWords = text.readSmallTextFile(filename);
    }

    public initMessages(string filename){

    }

}
