/*
Game display that runs all StdDraw-related functions.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

// StdDraw docs: https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html

public class GameDisplay {
    private Button real; // "Real" option button
    private Button fake; // "Onion" option button
    private double textMargin; // Vertical distance between the bottoms of
    // two lines of text
    private double canvasWidth; // Width of StdDraw canvas (it is square)

    // Constructor
    public GameDisplay(double textMargin, double canvasWidth) {
        real = new Button("reel.png", 0.25 * canvasWidth, 0.4 * canvasWidth,
                          0.15 * canvasWidth);
        fake = new Button("onion.png", 0.75 * canvasWidth, 0.4 * canvasWidth,
                          0.15 * canvasWidth);
        StdDraw.setFont(new Font("Georgia", Font.PLAIN, 20));
        this.textMargin = textMargin;
        this.canvasWidth = canvasWidth;
        StdDraw.setScale(0, canvasWidth);
    }

    // Square button class
    private class Button {
        private String image; // Button image filepath
        private double xPos; // x-coordinate of button's center
        private double yPos; // y-coordinate of button's center
        private double radius; // radius of button

        // Constructor
        public Button(String image, double xPos, double yPos, double radius) {
            this.image = image;
            this.xPos = xPos;
            this.yPos = yPos;
            this.radius = radius;
        }

        // Draws button on screen
        public void draw() {
            StdDraw.picture(xPos, yPos, image, radius * 2,
                            radius * 2);
        }

        // Returns whether the button is clicked
        public boolean clicked() {
            if (StdDraw.isMousePressed()) {
                if (StdDraw.mouseX() >= xPos - radius && StdDraw.mouseX() <= xPos + radius) {
                    return (StdDraw.mouseY() >= yPos - radius && StdDraw.mouseY() <= yPos + radius);
                }
            }
            return false;
        }
    }

    // Draws lavender background and game title on top
    public void drawBackground() {
        StdDraw.setPenColor(new Color(250, 214, 255)); // Lavender
        StdDraw.filledSquare(canvasWidth / 2, canvasWidth / 2,
                             canvasWidth / 2); // Fills whole screen
        StdDraw.setPenColor(new Color(63, 11, 84)); // Dark purple
        // Thin rectangle on top for title
        double titleHeight = 0.94 * canvasWidth;
        StdDraw.filledRectangle(canvasWidth / 2, titleHeight, canvasWidth / 2,
                                (canvasWidth - titleHeight));
        StdDraw.setPenColor(new Color(235, 179, 255)); // Light lavender
        StdDraw.text(canvasWidth / 2, titleHeight, "Determining Absurdity"); // Write title on
        // aforementioned rectangle
    }

    // Displays given text
    public void drawHeadline(String text) {
        StdDraw.setPenColor(StdDraw.BLACK);
        final int maxLineLength = 40; // Line length threshold after which newlines
        // will be inserted after first space

        // Split headline into chunks of >= 40 characters and draw
        StringBuilder currentLine = new StringBuilder(); // Text of current line
        int lineNumber = 0;
        for (int i = 0; i < text.length(); i++) {
            currentLine.append(text.charAt(i));
            // If over maxLineLength characters, wait until space to split
            if (currentLine.length() > maxLineLength && text.charAt(i) == ' ') {
                // Draw current line below previous, and then reset current line
                StdDraw.text(canvasWidth / 2, canvasWidth * 0.8 - textMargin * lineNumber,
                             currentLine.toString());
                lineNumber++;
                currentLine = new StringBuilder();
            }
        }
        // Write the leftover line
        StdDraw.text(canvasWidth / 2, canvasWidth * 0.8 - textMargin * lineNumber,
                     currentLine.toString());
    }

    // Renders the two buttons
    public void drawButtons() {
        real.draw();
        fake.draw();
    }

    // Waits for user to guess and returns true for real, false for fake
    public boolean getGuess() {
        boolean noGuess = true;
        boolean guess = false;
        while (noGuess) {
            if (real.clicked()) {
                guess = true;
                noGuess = false;
            }
            if (fake.clicked()) {
                noGuess = false;
            }
        }
        return guess;
    }

    // Writes whether user was right or wrong, and current statistics
    public void drawResult(boolean correct, int right, int wrong) {
        StdDraw.setPenColor(StdDraw.BLACK);
        String message = "You got it ";
        if (correct) {
            message += "right!";
        }
        else {
            message += "wrong!";
        }
        // 0.5 centers the text, while the second number (height) decreases
        StdDraw.text(canvasWidth / 2, 0.2 * canvasWidth, message);
        StdDraw.text(canvasWidth / 2, 0.2 * canvasWidth - textMargin,
                     "Correct guesses: " + right + " Incorrect guesses: " + wrong);
        StdDraw.text(canvasWidth / 2, 0.2 * canvasWidth - textMargin * 3,
                     "Press ENTER to continue or BACKSPACE to quit");
    }

    // Waits for user to hit enter (returns true) or backspace (returns false)
    public boolean continueGame() {
        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                return true;
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_BACK_SPACE)) {
                return false;
            }
        }
    }

    // Clears display
    public void clear() {
        StdDraw.clear();
    }

    // Outputs ending message
    public void drawEndScreen() {
        StdDraw.clear();
        StdDraw.setPenColor(new Color(250, 214, 255)); // Lavender
        StdDraw.filledSquare(0.5 * canvasWidth, 0.5 * canvasWidth, 0.5 *
                canvasWidth); // Fills screen
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.5 * canvasWidth, 0.4 * canvasWidth,
                     "Congrats! We hope you enjoyed playing.");
    }

    public static void main(String[] args) {
        // Test every function of game display
        GameDisplay display = new GameDisplay(0.05, 1.0);
        display.drawBackground();
        display.drawHeadline("Test very much a really long headline, a headline"
                                     + "so long that it goes over to the next"
                                     + "line because it's over 40 characters");
        display.drawButtons();
        boolean guess = display.getGuess();
        display.drawResult(guess, 5, 2);
        if (!display.continueGame()) {
            display.clear();
            display.drawBackground();
        }
        else {
            display.clear();
            display.drawEndScreen();
        }
    }
}
