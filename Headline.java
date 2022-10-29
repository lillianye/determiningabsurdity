/* Stores the headline's text, whether it is from the Onion or not, and whether
it has been asked before.
 */

public class Headline {
    private String headline; // Text of the headline
    private boolean real; // If headline is real or not
    private boolean askedBefore; // If headline was previously asked

    // Constructor
    public Headline(String headline, boolean real) {
        this.headline = headline;
        this.real = real;
        askedBefore = false;
    }

    // Returns the headline
    public String getHeadline() {
        return headline;
    }

    // Returns isReal
    public boolean isReal() {
        return real;
    }

    // Returns askedBefore
    public boolean askedBefore() {
        return askedBefore;
    }

    // Uppercases the first letter of each word in the headline, mutating it
    public void uppercaseFirstLetter() {
        StringBuilder result = new StringBuilder();
        // input validation
        if (headline.length() == 0) {
            return;
        }
        // Always uppercase first letter
        result.append(Character.toUpperCase(headline.charAt(0)));
        // Uppercases the given character if preceded by a space
        for (int i = 1; i < headline.length(); i++) {
            if (headline.charAt(i - 1) == ' ') {
                result.append(Character.toUpperCase(headline.charAt(i)));
            }
            else {
                result.append(headline.charAt(i));
            }
        }
        headline = result.toString();
    }

    public static void main(String[] args) {
        // Test every function of Headline
        Headline test = new Headline("Michelle and Lillian pass COS126", true);
        StdOut.println(test.getHeadline());
        StdOut.println(test.isReal());
        StdOut.println(test.askedBefore());
        test.uppercaseFirstLetter();
        StdOut.println(test.getHeadline());
        Headline test2 = new Headline("prince-ton line-numbers  . 1tests  a", true);
        StdOut.println(test2.getHeadline());
        test2.uppercaseFirstLetter();
        StdOut.println(test2.getHeadline());
    }
}
