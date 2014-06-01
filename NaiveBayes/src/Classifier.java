import java.util.List;

public class Classifier {

    private List<Fortune> trainData;

	public Classifier(){
		
	}
	
	public Classifier(List<Fortune> trainData) {
        this.trainData = trainData;
	}

	public Boolean classify(List<Boolean> featureVector) {

		return null;
	}
}
