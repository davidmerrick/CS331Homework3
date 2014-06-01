import java.util.*;
import java.io.*;

public class PreProcessor {

    List<String> stopWords,activeWords;
    List<String> vocabulary;
    //Array of fortunes in training data
    List<Fortune> trainingFortuneArray;

    public void PreProcessor()
    {

    }

    private void initStopWords(String filename){
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

    //Forms a list of all the words used in the test data
    public void formVocabulary(String trainingDataFile, String stopWordsFile){
        this.initStopWords(stopWordsFile);
        try {
            BufferedReader br = new BufferedReader(new FileReader(trainingDataFile));
            //Read entire line at a time into a string
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    String [] lineWords = line.split(" ");
                    //Add the words to the activeWords and fortune words
                    for (String word : lineWords){
                        this.vocabulary.add(word);
                    }
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
        this.vocabulary.removeAll(stopWords);
    }

    //Parses a line of words into a Fortune vector
    private Fortune parseLine(String line){
        Fortune fortune = new Fortune(this.vocabulary.size());

        List<String> fortuneWords = new ArrayList<String>();
        String [] lineWords = line.split(" ");
        //Add the words to the activeWords and fortune words


        //Subtract out the stop words
        fortuneWords.removeAll(this.stopWords);

        return fortune;
    }

	public List<Fortune> trainData(String trainingDataFile, String trainingLabelsFile) throws IOException {
        this.trainingFortuneArray = new ArrayList<Fortune>();

        try {
            BufferedReader brData = new BufferedReader(new FileReader(trainingDataFile));
            BufferedReader brLabels = new BufferedReader(new FileReader(trainingLabelsFile));
            //Read entire line at a time into a string
            String line, label;
            try {
                while ((line = brData.readLine()) != null && (label = brLabels.readLine()) != null) {
                    Fortune fortune = new Fortune(this.vocabulary.size());
                    String [] lineWords = line.split(" ");

                    //Add the words to a list so it's easily searchable
                    for (String word : lineWords){
                        if(this.vocabulary.contains(word)){
                            //Set the feature vector to true at the index of the word
                            fortune.featureVector.set(this.vocabulary.indexOf(word), true);
                        }
                    }

                    //Set the classification index of the feature vector
                    if(label.trim() == "1"){
                        fortune.featureVector.set(this.vocabulary.size(), true);
                    }

                    this.trainingFortuneArray.add(fortune);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    brData.close();
                    brLabels.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

		return this.trainingFortuneArray;
	}

	public ArrayList<Fortune> testData(String string, String string2) {
		return null;
	}

    public void initMessages(String filename){

    }
}
