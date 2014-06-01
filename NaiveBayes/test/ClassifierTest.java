import org.junit.Test;

import java.util.ArrayList;

public class ClassifierTest extends Classifier{

	@Test
	public void AprioriTest(){
		
	}
	
	@Test
	public void classificationTest(){
		ArrayList<Fortune> trainData = new ArrayList<Fortune>();
		Classifier classifier = new Classifier(trainData);
		//Fortune toClassify = new Fortune();
		//Boolean isFortune = classifier.classify(toClassify.feature_vector);
	}
}
