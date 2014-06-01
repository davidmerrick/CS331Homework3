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

        //Files for output
        String preprocessedFile = "NaiveBayes/data/preprocessed.txt";

        PreProcessor p = new PreProcessor();
        p.formVocabulary(trainingDataFile, stopWordsFile);

        // 2. Convert the training data into a set of features. Vector size is M+1. First M values correspond to the words and the (M+1)th slot corresponds to the class label.

        try {
            List<Fortune> trainingDataList = p.trainData(trainingDataFile, trainingLabelFile);
            p.outputTrainingData(preprocessedFile, trainingDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. Output the pre-processed training data to a file called preprocessed.txt

        ArrayList<Fortune> testData = p.testData("NaiveBayes/data/testdata.txt","NaiveBayes/data/testlabels.txt");
	}

}
