import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class ClassifierTest extends Classifier{
	
	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test 
	public void AprioriTest(){
		
	}
	
	@Test 
	public void LogTest(){
		
	}
	
	@Test
	public void classificationTest(){
		ArrayList<Fortune> trainData = new ArrayList<Fortune>();
		Classifier classifier = new Classifier(trainData);
		Fortune toClassify = new Fortune();
		Boolean isFortune = classifier.classify(toClassify.feature_vector);
	}

}
