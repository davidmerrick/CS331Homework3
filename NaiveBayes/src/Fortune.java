import java.util.ArrayList;

/*
 * This is an abstract view of a fortune.  
 * It contains a feature vector of the words present and the classification.  
 */
public class Fortune {

    public ArrayList<Boolean> featureVector; //holds the feature vector for this fortune

    public Fortune(Integer vocabSize){
        //Initialize the featureVector to the same size as the vocabulary + 1
        Integer vectorSize = vocabSize + 1;
        this.featureVector = new ArrayList<Boolean>(vectorSize);
        for(int i = 0; i < vectorSize; i++){
            this.featureVector.set(i, false);
        }
    }

}
