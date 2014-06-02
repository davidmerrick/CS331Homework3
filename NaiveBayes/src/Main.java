import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {


    //Your code will be required to take the following command line arguments (in order):
    //<stoplist file> <training data file> <training label file> <testing data file> <testing label file>

	public static void main(String[] args) {
        String stopWordsFile;
        String trainingDataFile;
        String trainingLabelFile;
        String testDataFile;
        String testLabelFile;
        String resultsFile = "results.txt";


        //Parse the command line arguments
        if(args.length < 5){
            System.out.println("No command line arguments specified or they weren't in this order: ");
            System.out.println("<stoplist file> <training data file> <training label file> <testing data file> <testing label file>");
            System.out.println("Setting these to defaults and proceeding.");

            //Set the defaults
            stopWordsFile = "NaiveBayes/data/stoplist.txt";
            trainingDataFile = "NaiveBayes/data/traindata.txt";
            trainingLabelFile = "NaiveBayes/data/trainlabels.txt";
            testDataFile = "NaiveBayes/data/testdata.txt";
            testLabelFile = "NaiveBayes/data/testlabels.txt";
        } else {
            //Set the defaults
            stopWordsFile = args[0];
            trainingDataFile = args[1];
            trainingLabelFile = args[2];
            testDataFile = args[3];
            testLabelFile = args[4];
        }

        // 1. Form the vocabulary. The vocabulary consists of the set of all the words that are in the training data with stop words removed.

        PreProcessor p = new PreProcessor();
        p.formVocabulary(trainingDataFile, stopWordsFile);

        try {
            // 2. Convert the training data into a set of features. Vector size is M+1. First M values correspond to the words and the (M+1)th slot corresponds to the class label.
            List<Fortune> trainingDataList = p.trainData(trainingDataFile, trainingLabelFile);

            // 3. Output the pre-processed training data to a file called preprocessed.txt
            String preprocessedFile = "NaiveBayes/data/preprocessed.txt";
            p.outputTrainingData(preprocessedFile, trainingDataList);


            //Now, for the Classifier:
            // 1. Read in the training data along with the training labels and learns the parameters used by the classifier.
            Classifier c = new Classifier(trainingDataList);

            // 2. In the testing phase, the trained naive Bayes classifier classifies the data in the testing data file.
            // You will need to convert the fortune cookie messages in the testing data into a feature vector, just
            // like in the training data where a 1 in the ith slot indicates the presence of the ith word in the vocabulary while a 0 indicates the absence.
            // If you encounter a word in the testing data that is not present in your vocabulary, ignore that word.
            // Note that the feature vector is only of size M because the class labels are not part of the testing data.


            //First, run the classifier on the training data
            List<Fortune> testFortunes = p.testData(trainingDataFile);
            List<Boolean> testLabels = p.testLabels(trainingLabelFile);

            Integer correctCount = 0; //Number of fortunes correctly classified
            int fortuneIndex = 0;

            //For debugging: fortunes it got wrong
            List<Fortune> wrongFortunes = new ArrayList<Fortune>();

            for(Fortune fortune : testFortunes){
                Boolean classification = c.classify(fortune);
                //If it got it right, increment the correctCount
                if(classification == testLabels.get(fortuneIndex)){
                    correctCount++;
                } else {
                    wrongFortunes.add(fortune);
                }
                fortuneIndex++;
            }

            Float trainAccuracy = (float) correctCount/(float) testFortunes.size();

            //Next, run the classifier on the test data
            testFortunes = p.testData(testDataFile);
            testLabels = p.testLabels(testLabelFile);

            correctCount = 0; //Number of fortunes correctly classified
            fortuneIndex = 0;

            //For debugging: fortunes it got wrong
            wrongFortunes = new ArrayList<Fortune>();

            for(Fortune fortune : testFortunes){
                Boolean classification = c.classify(fortune);
                //If it got it right, increment the correctCount
                if(classification == testLabels.get(fortuneIndex)){
                    correctCount++;
                } else {
                    wrongFortunes.add(fortune);
                }
                fortuneIndex++;
            }

            Float testAccuracy = (float) correctCount/(float) testFortunes.size();

            // 3. Output the accuracy of the naive Bayes classifier by
            // comparing the predicted class label of each message in the testing data to the actual class label.
            // The accuracy is the number of correct predictions divided by the total number of predictions.

            System.out.println("Printing results to results.txt in current directory.");
            Writer writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultsFile), "utf-8"));
                writer.write("Results:");
                writer.write('\n');
                writer.write("Training data file: " + trainingDataFile);
                writer.write('\n');
                writer.write("Training labels file: " + trainingLabelFile);
                writer.write('\n');
                writer.write("Accuracy of running classifier on training data file: " + trainAccuracy);
                writer.write('\n');
                writer.write("Test data file: " + testDataFile);
                writer.write('\n');
                writer.write("Test labels file: " + testLabelFile);
                writer.write('\n');
                writer.write("Accuracy of running classifier on test data file: " + testAccuracy);
                writer.write('\n');
            } catch (IOException ex) {
                // report
            } finally {
                try {
                    writer.close();
                } catch (Exception ex) {
                    //Do something
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
