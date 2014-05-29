import java.util.*;

/*
 * This is an abstract view of a fortune.  
 * It contains a feature vector of the words present and the classification.  
 */
public class Fortune {
	public static ArrayList<String> feature_descriptor;//the corresponding word for each element of our feature vector 
	public ArrayList<Boolean> feature_vector; //holds the feature vector for this fortune
	public Boolean isPredictive;//holds whether this is a fortune
}
