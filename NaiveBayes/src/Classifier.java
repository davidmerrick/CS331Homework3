import java.util.List;

public class Classifier {

    private List<Fortune> trainData;
    private Boolean ignoreAbsentWords = false;

	public Classifier(){
		
	}
	
	public Classifier(List<Fortune> trainData) {
        this.trainData = trainData;
	}

	public Boolean classify(Fortune fortune) {
        List<Boolean> featureVector = fortune.featureVector;
        // 1. Estimate P(Y=v) as fraction of records with Y=v
        Double overallProbabilityTrue = this.probabilityLog(this.computeProbabilityOfY(true));
        Double overallProbabilityFalse = this.probabilityLog(this.computeProbabilityOfY(false));

        // 2. Estimate P(X_i=u | Y=v) as fraction of "Y=v" records that also have X=u
        //Check the probability that the fortune is predictive:
        int index = 0; //Index in the featureVector
        Double predictiveSum = overallProbabilityTrue;
        Double notPredictiveSum = overallProbabilityFalse;
        for(Boolean feature : featureVector){
            //Decide whether to ignore the word if it's absent. Otherwise compute probability based on its absence.
            if(!(this.ignoreAbsentWords && !feature)) {
                predictiveSum += this.probabilityLog(this.computeProbabilityOfXGivenY(feature, true, index));
                notPredictiveSum += this.probabilityLog(this.computeProbabilityOfXGivenY(feature, false, index));
            }
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

    //Returns the log of a probability
    private Double probabilityLog(Double probability){
        return Math.log(probability + Math.E) - 1.0;
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

        double probability;
        // Dirichlet Prior: if no records found, probability is 1/n
        // (where n is the number of possible values, which, in this case is True and False so 2) or 50%
        if(count == 0){
            probability = 0.5;
        } else {
            probability = (double) count/(double) yCount;
        }
        return probability;
    }

    //For debugging
    //Estimate P(X_i=u | Y=v) as fraction of "Y=v" records that also have X=u
    public Double computeProbabilityOfWordGivenY(Boolean u, Boolean v, String word, List<String> vocabulary){
        //Y is overall probability of being predictive
        //X is the probability in the feature vector
        //u is value for X, v is value for Y, word determines index in feature vector

        int index = vocabulary.indexOf(word);

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
