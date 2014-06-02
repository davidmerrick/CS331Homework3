import org.junit.Test;

import java.io.IOException;
import java.util.*;

//http://stackoverflow.com/questions/2697182/how-to-use-an-array-list

public class PreProcessorTest extends PreProcessor{
	
	@Test
	public void testPreProcess(){
        // 1. Form the vocabulary. The vocabulary consists of the set of all the words that are in the training data with stop words removed.

        //Files that were given
        String stopWordsFile = "NaiveBayes/data/stoplist.txt";
        String trainingDataFile = "NaiveBayes/data/traindata.txt";
        String trainingLabelFile = "NaiveBayes/data/trainlabels.txt";
        String testDataFile = "NaiveBayes/data/testdata.txt";
        String testLabelFile = "NaiveBayes/data/testlabels.txt";

        PreProcessor p = new PreProcessor();
        p.formVocabulary(trainingDataFile, stopWordsFile);

        try {
            // 2. Convert the training data into a set of features. Vector size is M+1. First M values correspond to the words and the (M+1)th slot corresponds to the class label.
            List<Fortune> trainingDataList = p.trainData(trainingDataFile, trainingLabelFile);

            // 3. Output the pre-processed training data to a file called preprocessed.txt
            String preprocessedFile = "NaiveBayes/data/preprocessed.txt";
            p.outputTrainingData(preprocessedFile, trainingDataList);


            //Now, for the Classifier:
            // 1. Read in the training data along with the training labels and learns the parameters used by the classifier.
            Classifier c = new Classifier(trainingDataList);

            // 2. In the testing phase, the trained naive Bayes classifier classifies the data in the testing data file.
            // You will need to convert the fortune cookie messages in the testing data into a feature vector, just
            // like in the training data where a 1 in the ith slot indicates the presence of the ith word in the vocabulary while a 0 indicates the absence.
            // If you encounter a word in the testing data that is not present in your vocabulary, ignore that word.
            // Note that the feature vector is only of size M because the class labels are not part of the testing data.
            List<Fortune> testFortunes = p.testData(testDataFile);
            List<Boolean> testLabels = p.testLabels(testLabelFile);

            Integer correctCount = 0; //Number of fortunes correctly classified
            int fortuneIndex = 0;

            //For debugging: fortunes it got wrong
            List<Fortune> wrongFortunes = new ArrayList<Fortune>();

            for(Fortune fortune : testFortunes){
                Boolean classification = c.classify(fortune);
                //If it got it right, increment the correctCount
                if(classification == testLabels.get(fortuneIndex)){
                    correctCount++;
                } else {
                    wrongFortunes.add(fortune);
                }
                fortuneIndex++;
            }

            // 3. Output the accuracy of the naive Bayes classifier by
            // comparing the predicted class label of each message in the testing data to the actual class label.
            // The accuracy is the number of correct predictions divided by the total number of predictions.

            Float accuracy = (float) correctCount/(float) testFortunes.size();
            System.out.print(accuracy);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
