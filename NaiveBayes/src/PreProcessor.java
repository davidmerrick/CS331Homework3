import java.util.*;
import java.io.*;

public class PreProcessor {

	Set<String> stopWords,activeWords;

    public void PreProcessor()
    {

    }

    public void initStopWords(String filename){
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

	public ArrayList<Fortune> trainData(String trainingDataFile, String trainingClassificationFile) throws IOException {
		// TODO Auto-generated method stub
		
		//read through the whole file to get the words 
		BufferedReader br = new BufferedReader(new FileReader(trainingDataFile));
		String line = br.readLine();
		this.activeWords = new HashSet<String>();
		while(line != null){
			String [] FortuneWords = line.split(" ");//http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
			for (String word : FortuneWords){
				this.activeWords.add(word);
			}
		}
		//AllWords - StopWords = ActiveWords
		activeWords.removeAll(stopWords);
		
		
		//read through the file for each fortune 
			//split each message up into words
				//check if each word is in the Active words
					//set bit active if it's an Active word
			//add the fortune's feature vector 
		return null;
	}

	public ArrayList<Fortune> testData(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}

    public void initMessages(String filename){

    }
}
