/* Creates the analytic tracker for all meaningful words that appear in news
   headlines. Updates tracker with every non-filler word in a headline and
   calculates average frequency and correct percentage of the words.
*/

import java.util.ArrayList;

public class WordPredictor {

    // tracks correct/wrong guesses for each word
    private ST<String, WordData> wordTracker;

    // creates empty tracker
    public WordPredictor() {
        wordTracker = new ST<String, WordData>();
    }

    // Takes in headline, filler words to filter out, and whether user guessed right
    // or wrong. Updates symbol table for each non-filler word in headline, either
    // adding the word to symbol table or updating correct/wrong guesses for that word
    public void updateTracker(ArrayList<String> fillers, String headline, boolean guess) {
        String[] holder = headline.split(" ");
        for (int i = 0; i < holder.length; i++) {
            if (!WordData.isFiller(fillers, holder[i])) {
                if (!wordTracker.contains(holder[i])) {
                    wordTracker.put(holder[i], new WordData(holder[i]));
                }
                if (guess) {
                    wordTracker.get(holder[i]).incrementCorrect();
                }
                else {
                    wordTracker.get(holder[i]).incrementIncorrect();
                }
            }
        }
    }

    // Calculates and returns average number of times each word in the Symbol
    // Table appeared in an asked headline
    public double averageTimesAsked() {
        int averageNumberAsked = 0;
        for (String word : wordTracker.keys()) {
            averageNumberAsked += (wordTracker.get(word).numCorrect()
                    + wordTracker.get(word).numWrong());
        }
        if (averageNumberAsked == 0 || wordTracker.size() == 0) {
            return 0.0;
        }
        else {
            return ((averageNumberAsked * 1.0) / wordTracker.size());
        }
    }

    // Calculates and returns the average correct guess rate for headlines containing
    // the words in Symbol Table
    public double averagePercentCorrect() {
        double averagePercent = 0.0;
        for (String word : wordTracker.keys()) {
            averagePercent += wordTracker.get(word).percentCorrect();
        }
        if (averagePercent == 0.0 || wordTracker.size() == 0) {
            return 0.0;
        }
        else {
            return (averagePercent / wordTracker.size());
        }
    }

    // Returns Symbol Table tracker of non-filler words that appeared in headlines
    public ST<String, WordData> getAnalytics() {
        return wordTracker;
    }

    public String toString() {
        StringBuilder trackerResults = new StringBuilder();
        for (String word : wordTracker) {
            trackerResults.append(word + ": Correct = "
                                          + wordTracker.get(word).numCorrect()
                                          + ", Incorrect = "
                                          + wordTracker.get(word).numWrong());
            trackerResults.append("\n");
        }
        return trackerResults.toString();
    }

    // Tests all instance methods of WordPredictor class
    public static void main(String[] args) {

        // Creates a test WordPredictor and sets "and" as only filler word
        WordPredictor testTracker = new WordPredictor();
        ArrayList<String> testfillers = new ArrayList<String>();
        testfillers.add("and");

        // Tests updateTracker(), averageTimesAsked(), and averagePercentCorrect()
        testTracker.updateTracker(testfillers, "Michelle loves COS126 and", true);
        StdOut.println(testTracker);
        StdOut.println("Average times each word asked: "
                               + testTracker.averageTimesAsked());
        StdOut.println("Average percent correct for headlines with each word: "
                               + testTracker.averagePercentCorrect());
    }
}
