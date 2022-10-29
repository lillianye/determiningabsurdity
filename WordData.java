/* Creates storage for how many times headlines with a given word are guessed
   correctly or incorrectly. Accounts for percent that a headline with the given
   word is guessed right and for the frequency that the word appears in a headline
*/

import java.util.ArrayList;

public class WordData {

    private String word; // word of headline
    private int correct; // number of correct guesses of headlines with this word
    private int wrong; // number of incorrect guesses of headlines with this word

    // value that accounts for percentage of correct guesses and frequency of the word
    private double bayesianAvg;

    // Takes in a word and creates un-updated WordData
    public WordData(String word) {
        this.word = word;
        correct = 0;
        wrong = 0;
        bayesianAvg = 0.0;
    }

    // Returns the word
    public String getWord() {
        return word;
    }

    // Returns number of times headlines with word was correctly guessed
    public int numCorrect() {
        return correct;
    }

    // Returns number of times headlines with word was incorrectly guessed
    public int numWrong() {
        return wrong;
    }

    // Increases number of times headlines with word was correctly guessed by 1
    public void incrementCorrect() {
        correct++;
    }

    // Increases number of times headlines with word was incorrectly guessed by 1
    public void incrementIncorrect() {
        wrong++;
    }

    // Calculates and returns percent that headlines with word was correct guessed
    public double percentCorrect() {
        if (correct > 0) {
            return (correct * 100.0) / (correct + wrong);
        }
        else {
            return 0.0;
        }
    }

    // Takes in tracker of all words that the word is in and sets the Bayesian
    // average value of the word using following formula
    // (# times asked) / (# times asked + avg times each word asked) * % right of word
    // + (avg # times each word asked) / (# times asked + avg times each word asked) *
    // avg % correct of all words
    // Source:
    // www.codementor.io/@arpitbhayani/solving-an-age-old-problem-using-bayesian
    // -average-15fy4ww08p
    public void setBayesianAvg(WordPredictor tracker) {
        int timesAsked = correct + wrong;
        double avgTimesAsked = tracker.averageTimesAsked();
        double avgPercent = tracker.averagePercentCorrect();
        if (timesAsked + avgTimesAsked == 0) {
            bayesianAvg = 0.0;
        }
        else {
            bayesianAvg = ((timesAsked / (timesAsked + avgTimesAsked))
                    * percentCorrect()) +
                    ((avgTimesAsked / (timesAsked + avgTimesAsked)) * avgPercent);
        }
    }

    // Returns Bayesian average value for the word
    public double getBayesianAvg() {
        return bayesianAvg;
    }

    // Takes in a word and a list of filler words and returns whether the word
    // is a filler word
    public static boolean isFiller(ArrayList<String> fillers, String word) {
        for (String filler : fillers) {
            if (word.compareToIgnoreCase(filler) == 0) {
                return true;
            }
        }
        return false;
    }

    // Tests methods in WordData class
    public static void main(String[] args) {

        // Creates test WordData that initially has 0 percent correct
        WordData test1 = new WordData("Exam1");
        StdOut.println("Initial Percent Correct (Should be 0.0): "
                               + test1.percentCorrect() + "%");

        // Tests incrementCorrect() and incrementIncorrect()
        test1.incrementCorrect();
        test1.incrementIncorrect();
        StdOut.print("Percent Correct Now (Should be 50.00): ");
        StdOut.printf("%.2f", test1.percentCorrect());
        StdOut.println();

        // Creates a test list of filler words and tests isFiller()
        ArrayList<String> testFillers = new ArrayList<String>();
        testFillers.add("the");
        testFillers.add("And");
        testFillers.add("they");
        StdOut.println("Is test1 a filler word (Should be false)? "
                               + isFiller(testFillers, test1.getWord()));

        // Tests isFiller() when the word is a filler but is capitalized different
        WordData test2 = new WordData("The");
        StdOut.println("Is test2 a filler word (Should be true)? "
                               + isFiller(testFillers, test2.getWord()));
    }

}
