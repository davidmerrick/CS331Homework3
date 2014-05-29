import org.junit.Test;

import java.io.IOException;
import java.util.*;

//http://stackoverflow.com/questions/2697182/how-to-use-an-array-list

public class PreProcessorTest extends PreProcessor{

	@Test
	public void test() {
        //fail("Not yet implemented");
	}
	
	@Test
	public void testPreProcess(){
		//load in the stop words
		//load in the messages 
		//get a set of all words in messages that aren't stop words
		//count the length and make a vector description 
		//loop over all the messages and create their respective feature vectors
        PreProcessor p = new PreProcessor();
        p.initStopWords("data/stoplist.txt");
        try {
            ArrayList<Fortune> trainingData = p.trainData("training_messages","training_classifications");
        } catch (IOException e) {
            e.printStackTrace();
        }

        p = new PreProcessor();
        p.initStopWords("NaiveBayes/data/stoplist.txt");

		//load in the test messages 
		//convert each of the messages to a feature of "activeWords"
        ArrayList<Fortune> testData = p.testData("test_fortunes","test_classifications");
	}

}
