import org.junit.Test;

import java.util.ArrayList;

public class ClassifierTest extends Classifier{
	
	@Test
	public void classificationTest(){
        // 1. Read in the training data along with the training labels and learns the parameters used by the classifier.

        ArrayList<Fortune> trainData = new ArrayList<Fortune>();
		Classifier classifier = new Classifier(trainData);



		//Fortune toClassify = new Fortune();
		//Boolean isFortune = classifier.classify(toClassify.feature_vector);
	}
}
