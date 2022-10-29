/*
Main game class that contains main game loop, stores player data, and runs
statistics.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DeterminingAbsurdity {
    // Create an array of headlines from a given file and length
    // Source for using Scanner to read files:
    // www.java67.com/2012/11/how-to-read-file-in-java-using-scanner-example.html
    private static ArrayList<Headline> readHeadlines(String filename) {
        boolean isReal = false;
        if (filename.equals("Real.txt")) isReal = true;
        ArrayList<Headline> headlines = new ArrayList<Headline>();

        // Read headlines from text file and append to headlines ArrayList
        File text = new File(filename);
        try {
            Scanner readText = new Scanner(text);  // Create a Scanner object
            while (readText.hasNextLine()) {
                headlines.add(new Headline(readText.nextLine(), isReal));
            }
            readText.close();
            return headlines;
        }
        catch (FileNotFoundException exception) {
            throw new IllegalArgumentException("File " + filename + " not found");
        }
    }

    // Select a random element of headline array
    private static Headline randomHeadline(ArrayList<Headline> headlines) {
        int rand = StdRandom.uniform(0, headlines.size());
        return headlines.get(rand);
    }

    // Merge sort the WordData array based on their Bayesian average values
    public static void sort(WordData[] unsorted, WordData[] hold, int low, int high) {
        // Code adapted from COS126 sorting lecture
        int n = high - low;
        if (n <= 1) {
            return;
        }
        int mid = low + (n / 2);
        sort(unsorted, hold, low, mid);
        sort(unsorted, hold, mid, high);
        merge(unsorted, hold, low, mid, high);
    }

    // Merges two WordData arrays for mergesort
    public static void merge(WordData[] unsorted, WordData[] hold, int low, int mid, int high) {
        // Code adapted from COS126 sorting lecture
        int i = low, j = mid, n = high - low;
        for (int k = 0; k < n; k++) {
            if (i == mid) {
                hold[k] = unsorted[j++];
            }
            else if (j == high) {
                hold[k] = unsorted[i++];
            }
            else if (unsorted[j].getBayesianAvg() < unsorted[i].getBayesianAvg()) {
                hold[k] = unsorted[j++];
            }
            else {
                hold[k] = unsorted[i++];
            }
        }
        for (int k = 0; k < n; k++) {
            unsorted[low + k] = hold[k];
        }
    }

    // Parses .txt list of filler words into an ArrayList of fillerwords
    // Source for using Scanner to read files:
    // www.java67.com/2012/11/how-to-read-file-in-java-using-scanner-example.html
    public static ArrayList<String> getFillerWords(String fillerFile) {
        ArrayList<String> fillerWords = new ArrayList<String>();
        File fillerText = new File(fillerFile);
        try {
            Scanner readFiller = new Scanner(fillerText);
            while (readFiller.hasNext()) {
                fillerWords.add(readFiller.next());
            }
            readFiller.close();
            return fillerWords;
        }
        catch (FileNotFoundException exception) {
            throw new IllegalArgumentException("File " + fillerFile + " not found");
        }
    }

    public static void main(String[] args) {
        // Initialize headline count
        int headlinesAsked = 0;

        // Initialize ArrayLists of headlines
        ArrayList<Headline> realHeadlines = readHeadlines("Real.txt");
        ArrayList<Headline> fakeHeadlines = readHeadlines("Onion.txt");

        // Creates a trackero analyze on correctly/incorrectly guessed headlines
        WordPredictor data = new WordPredictor();
        ArrayList<String> fillerWords = getFillerWords("fillerwords.txt");
        int correct = 0;
        int wrong = 0;

        // Initialize display and music
        StdAudio.loopInBackground("summer.wav");
        GameDisplay display = new GameDisplay(0.05, 1.0);

        // Main game loop
        while (!(headlinesAsked == realHeadlines.size() + fakeHeadlines.size())) {
            // Draw background
            display.drawBackground();

            // Randomly choose if headline will be real or fake
            boolean headlineReal = false;
            if (StdRandom.uniform(0, 2) % 2 == 0) {
                headlineReal = true;
            }

            // Pick a random headline that wasn't asked before
            Headline headline;
            do {
                if (headlineReal) {
                    headline = randomHeadline(realHeadlines);
                }
                else {
                    headline = randomHeadline(fakeHeadlines);
                }
            } while (headline.askedBefore());
            headlinesAsked++;

            // Capitalize all words in headline
            headline.uppercaseFirstLetter();

            // Display headlines and buttons
            display.drawHeadline(headline.getHeadline());
            display.drawButtons();

            // Check for user guess
            boolean guess = display.getGuess();

            // Output result
            boolean userCorrect = headline.isReal() == guess;
            if (userCorrect) {
                correct++;
            }
            else {
                wrong++;
            }
            display.drawResult(userCorrect, correct, wrong);

            // Update wordpredictor stats
            data.updateTracker(fillerWords, headline.getHeadline(), userCorrect);

            // Wait for user to hit enter or quit
            if (!display.continueGame()) {
                break;
            }

            // Clear display
            display.clear();
        }

        // Game's main loop has ended

        // Show end screen
        display.drawEndScreen();

        // Stores all WordData in the analytics tracker in an array
        // and calculates Bayesian average of each word to use to sort words later
        WordData[] analytics = new WordData[data.getAnalytics().size()];
        int count = 0;
        for (String word : data.getAnalytics().keys()) {
            analytics[count] = data.getAnalytics().get(word);
            analytics[count].setBayesianAvg(data);
            count++;
        }

        // Sorts words in analytic tracker in order of lowest to highest Bayesian avg
        sort(analytics, new WordData[analytics.length], 0, analytics.length);

        // Prints out analytics of the game in order of highest to lowest Bayesian
        // avg, also prints out % guessed correctly for headlines with each word
        for (int i = analytics.length - 1; i >= 0; i--) {
            StdOut.print(analytics[i].getWord() + ": Bayesian Average = ");
            StdOut.printf("%.2f", analytics[i].getBayesianAvg());
            StdOut.print("%, # of times it appeared " + (analytics[i].numCorrect()
                    + analytics[i].numWrong()));
            StdOut.print(", Raw Percent Guessed Correctly = ");
            StdOut.printf("%.2f", analytics[i].percentCorrect());
            StdOut.println("%!");
        }
    }
}


