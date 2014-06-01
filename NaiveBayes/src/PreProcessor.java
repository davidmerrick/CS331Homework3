import java.util.*;
import java.io.*;

public class PreProcessor {

    List<String> stopWords;
    List<String> vocabulary;
    //Array of fortunes in training data
    List<Fortune> trainingFortuneArray;

    public PreProcessor()
    {
        //Initialize the lists
        this.vocabulary = new ArrayList<String>();
        this.stopWords = new ArrayList<String>();
    }

    private void initStopWords(String filename){
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
        //Create a set. Later, convert this to an array
        Set<String> vocabSet = new HashSet();

        this.initStopWords(stopWordsFile);

        try {
            BufferedReader br = new BufferedReader(new FileReader(trainingDataFile));
            //Read entire line at a time into a string
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    String [] lineWords = line.split(" ");
                    //Add the words to the activeWords and fortune words
                    for(String word : lineWords){
                        vocabSet.add(word);
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
        vocabSet.removeAll(stopWords);

        //Convert set to array then to list
        this.vocabulary = new ArrayList(vocabSet);

        //Sort it alphabetically
        Collections.sort(this.vocabulary);
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
                    Fortune fortune = this.fortuneFromString(line);

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


    //Pretty much the same as trainData, but doesn't classify the fortunes. Just returns a list of the test ones
    public List<Fortune> testData(String testDataFile) throws IOException {
        List<Fortune> testFortunes = new ArrayList<Fortune>();

        try {
            BufferedReader brData = new BufferedReader(new FileReader(testDataFile));
            //Read entire line at a time into a string
            String line;
            try {
                while ((line = brData.readLine()) != null) {
                    Fortune fortune = this.fortuneFromString(line);
                    testFortunes.add(fortune);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    brData.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return testFortunes;
    }

    //Returns a list of the labels associated with the test data
    public List<Boolean> testLabels(String testLabelFile){
        List<Boolean> testLabels = new ArrayList<Boolean>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(testLabelFile));
            //Read entire line at a time into a string
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    if(line.trim().contains("1")) {
                        testLabels.add(true);
                    } else {
                        testLabels.add(false);
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

        return testLabels;
    }

    //Takes a line as input and converts to a fortune with a feature vector. Doesn't set the label, though.
    public Fortune fortuneFromString(String line){
        Fortune fortune = new Fortune(this.vocabulary.size());
        fortune.fortuneString = line;
        String [] lineWords = line.split(" ");

        for (String word : lineWords){
            //Ignore the word if it's not in the vocabulary
            if(this.vocabulary.contains(word)){
                //Set the feature vector to true at the index of the word
                fortune.featureVector.set(this.vocabulary.indexOf(word), true);
            }
        }
        return fortune;
    }

    //Outputs the training data to a file
    public void outputTrainingData(String outputFile, List<Fortune> trainFortuneList){
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "utf-8"));
            for(String word : this.vocabulary) {
                writer.write(word + ", ");
            }
            writer.write('\n');
            for(Fortune fortune : trainFortuneList){
                for(Boolean wordVector : fortune.featureVector){
                    if(wordVector){
                        writer.write("1");
                    } else {
                        writer.write("0");
                    }
                    writer.write(", ");
                }
                writer.write('\n');
            }
        } catch (IOException ex) {
            // report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                //Do something
            }
        }

    }
}
