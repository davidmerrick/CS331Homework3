import java.util.List;

public class Classifier {

    private List<Fortune> trainData;

	public Classifier(){
		
	}
	
	public Classifier(List<Fortune> trainData) {
        this.trainData = trainData;
	}

	public Boolean classify(Fortune fortune) {
        List<Boolean> featureVector = fortune.featureVector;
        // 1. Estimate P(Y=v) as fraction of records with Y=v
        Double overallProbabilityTrue = (Math.log(this.computeProbabilityOfY(true) + Math.E) - 1.0);
        Double overallProbabilityFalse = (Math.log(this.computeProbabilityOfY(false) + Math.E) - 1.0);

        // 2. Estimate P(X_i=u | Y=v) as fraction of "Y=v" records that also have X=u
        //Check the probability that the fortune is predictive:
        int index = 0; //Index in the featureVector
        Double predictiveSum = overallProbabilityTrue;
        Double notPredictiveSum = overallProbabilityFalse;
        for(Boolean feature : featureVector){
            predictiveSum += (Math.log(this.computeProbabilityOfXGivenY(feature, true, index) + Math.E) - 1.0);
            notPredictiveSum += (Math.log(this.computeProbabilityOfXGivenY(feature, false, index) + Math.E) - 1.0);
            index++;
        }

        if(predictiveSum > notPredictiveSum){
            return true;
        } else {
            return false;
        }
	}

    private Double computeProbabilityOfY(Boolean v){
        Integer count = 0;
        for(Fortune fortune : trainData){
            if(fortune.isPredictive() == v){
                count++;
            }
        }

        double probability = (double) count/(double) trainData.size();
        return probability;
    }

    //Estimate P(X_i=u | Y=v) as fraction of "Y=v" records that also have X=u
    private Double computeProbabilityOfXGivenY(Boolean u, Boolean v, int index){
        //Y is overall probability of being predictive
        //X is the probability in the feature vector
        //u is value for X, v is value for Y, index is index in feature vector

        Integer count = 0;
        Integer yCount = 0; //Total number of Y = v records
        for(Fortune fortune : trainData){
            if(fortune.isPredictive() == v){
                yCount++;
                if(fortune.featureVector.get(index) == u){
                    count++;
                }
            }
        }

        double probability = (double) count/(double) yCount;
        return probability;
    }
}
