import java.util.*;
import java.io.*;

public class PreProcessor {

    List<String> stopWords,activeWords;
    //Array of fortunes in test data
    List<Fortune> testFortuneArray;

    public void PreProcessor()
    {

    }

    public void initStopWords(String filename){
        this.stopWords = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            //Read entire line at a time into a string
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    this.stopWords.add(line.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Parses a line of words into a Fortune vector
    private Fortune parseLine(String line){
        Fortune fortune = new Fortune();

        List<String> fortuneWords = new ArrayList<String>();
        String [] lineWords = line.split(" ");
        //Add the words to the activeWords and fortune words
        for (String word : lineWords){
            fortuneWords.add(word);
            this.activeWords.add(word);
        }

        //Subtract out the stop words
        fortuneWords.removeAll(this.stopWords);

        return fortune;
    }

	public List<Fortune> trainData(String trainingDataFile, String trainingClassificationFile) throws IOException {
        this.testFortuneArray = new ArrayList<Fortune>();
        this.activeWords = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(trainingDataFile));
            //Read entire line at a time into a string
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    Fortune lineFortune = this.parseLine(line);
                    this.testFortuneArray.add(lineFortune);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Remove all the stop words from the active words
		this.activeWords.removeAll(stopWords);
		
		
		//read through the file for each fortune 
			//split each message up into words
				//check if each word is in the Active words
					//set bit active if it's an Active word
			//add the fortune's feature vector 
		return null;
	}

	public ArrayList<Fortune> testData(String string, String string2) {
		return null;
	}

    public void initMessages(String filename){

    }
}
