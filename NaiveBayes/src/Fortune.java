import java.util.ArrayList;
import java.util.List;

/*
 * This is an abstract view of a fortune.  
 * It contains a feature vector of the words present and the classification.  
 */
public class Fortune {

    public List<Boolean> featureVector; //holds the feature vector for this fortune
    public String fortuneString; //Holds the fortune words for debugging

    public Fortune(int vocabSize){
        //Initialize the featureVector to the same size as the vocabulary + 1
        int vectorSize = vocabSize + 1;
        this.featureVector = new ArrayList<Boolean>();

        //Set all values to false initially
        for(int i = 0; i < vectorSize; i++){
            this.featureVector.add(false);
        }
    }

    //Returns the end of the featureVector to indicate if fortune is predictive or not
    public Boolean isPredictive(){
        return this.featureVector.get(this.featureVector.size()-1);
    }
}
