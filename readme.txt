COS126 Final Project: Implementation

Please complete the following questions and upload this readme.txt to the
TigerFile assignment for the "Final Project Implementation".


/**********************************************************************
 * Basic Information                                                  *
 **********************************************************************/

Name 1: Michelle Liu

NetID 1: ml7415

Name 2: Lillian Ye

NetID 2: ly5103

Project preceptor name: Mona Singh 

Project title: Determining Absurdity

CodePost link for proposal feedback: https://codepost.io/code/488233

Link to project video: https://youtu.be/04KaBNcEPrw

Approximate number of hours to complete the final project
Number of hours: 15

/**********************************************************************
 * Required Questions                                                 *
 **********************************************************************/

Describe your project in a few sentences.

Our project will be a game where users guess whether news headlines are 
real or satirical (from The Onion), and the program will run analyses on 
what words are more likely to result in correct/incorrect guesses.


Describe in detail your three features and where in your program
they are implemented (e.g. starting and ending line numbers,
function names, etc.).
1. The first feature will be a core gameplay functionality that stores 
real and fake headlines, selects one randomly that has not already
been guessed before, and allows the user to guess
whether it is real or fake (DeterminingAbsurdity.java lines 111-163). It
does so by storing Headline objects (Headline.java) in an ArrayList of 
headlines that it reads from a file (DeterminingAbsurdity lines 13-31). 
It will also have a counter that keeps track of how many correct and 
incorrect guesses the user
has made (DeterminingAbsurdity.java lines 103-104).


2. The second feature is a GUI created using StdDraw (GameDisplay.java).
A display object is instantiated (DeterminingAbsurdity line 108). Then,
the API includes drawing the background, headline, buttons, result of
each guess, and end screen. The interactivity is contained in two
functions: 1) getGuess(), which waits for a user
to click on the REAL or FAKE button and returns a boolean, and
2) continueGame(), which waits for a user to hit enter or backspace in
order to continue playing or end the game, respectively.


3. The third feature will run analytics; for each headline, the program
will store words from the headlines in a Symbol Table (WordPredictor.java)
and keep track of how often headlines with that word in it were guessed
correctly/incorrectly. Words from a predetermined list of closed class
(non-content) words are not added to the symbol table (WordData.java isFiller()).
At the end of the game, all words from the symbol table are merge sorted
(DeterminingAbsurdity.java lines 40-73) according to their Bayesian weights,
which is a ranking system that takes into account both the # of times 
the word appeared as well as how often it was guessed correctly). This
sorted list of words is output to the terminal in decreasing order of 
Bayesian weight. The output also lists each word's raw correct percentage and
the number of times it appeared (DeterminingAbsurdity lines 185-193).



Describe in detail how to compile and run your program. Include a few example
run commands and the expected results of running your program. For non-textual
outputs (e.g. graphical or auditory), feel free to describe in words what the
output should be or reference output files (e.g. images, audio files) of the
expected output.

Run the following commands:
javac-introcs DeterminingAbsurdity.java
java-introcs DeterminingAbsurdity

No command line arguments are taken.

Upon running these commands, an StdDraw window should pop up with a headline.
Music will also play in the background. Upon ending the game, the program will 
output statistics about each word to the terminal.

If the user desires to have the statistics to be output into a file, they can run 
the following commands instead:
javac-introcs DeterminingAbsurdity.java
java-introcs DeterminingAbsurdity > nameoffile.txt

nameoffile.txt can be replaced with the name of the file you would like your 
results to be output to (this file cannot already exist).





Describe how your program accepts user input and mention the line number(s) at
which your program accepts user input.

The user can click REAL or ONION depending on whether they think the given 
headline is from a news website or 
from The Onion (GameDisplay.java lines 107-120). After each guess, the user can 
then hit enter to continue with another headline,
or backspace to quit (GameDisplay.java lines 141-150). 





Describe how your program produces output based on user input (mention line
numbers).

Depending on whether the user guessed correctly or incorrectly, 
the game will let them know if they were right or wrong & their current number of
right/wrong guesses. (GameDisplay.java lines 123-138)


Describe the data structure your program uses and how it supports your program's
functionality (include the variable name and the line number(s) at which it is
declared and initialized).

The program uses a Symbol Table (WordPredictor.java line 11) that stores each 
non-filler word from news headlines as
the key and associates a WordData object containing how many times headlines 
with that word were guessed right/wrong as the value.
This allows our program to run the analytics at the end, telling the user what 
words resulted in more right/wrong guesses and what words
appeared more frequently. 

The program also uses an ArrayList to store all the Headline objects 
(Determining Absurdity lines 15-33) that are read from Scanner, and it allows
the game to repeatedly grab Headline objects from the ArrayList to give to the user. 



List the two custom functions written by your project group, including function
signatures and line numbers; if your project group wrote more than two custom
functions, choose the two functions that were most extensively tested.
1. isFiller() in WordData.java lines 86-93

2. upperCaseFirstLetter() in Headline.java lines 33-51

List the line numbers where you test each of your two custom functions twice.
For each of the four tests (two for each function), explain what was being
tested and the expected result. For non-textual results (e.g. graphical or
auditory), you may describe in your own words what the expected result
should be or reference output files (e.g. images, audio files).
1. The first test for isFiller() is in WordData.java lines 111-116. A WordData
was created for “Exam1” and a sample
filler words list was used to test. “Exam1” is not a filler word, so the
expected result of isFiller() is false, and that is
what the function correctly returned.

2. The second test for isFiller() is in WordData.java lines 119-121. A
WordData was created for “The” and the same
sample filler words list was used to test. “the” is in the filler words
list, so this test was to make sure that isFiller() can
recognize filler words even when they are capitalized differently than
the word is in the list. The expected result of 
isFiller() is true, and that is what the function correctly returned.

3. Headline, uppercaseFirstLetter() on line 59. This first test is for
a basic headline just to make sure that the function works and has visible
results. Since the function returns void, and its output is via modifying
the headline field of that object, I tested it by printing getHeadline()
for that object before and after running uppercaseFirstLetter(). Sure
enough, “Michelle and Lillian pass COS126” has every character after
a space converted into uppercase, so as to result in “Michelle And
Lillian Pass COS126.”

4. Headline, uppercaseFirstLetter() on line 63. This second test is
for weirder cases such as punctuation, and numbers. I wanted to make
sure that the function didn’t break when attempting to uppercase
non-alphabetic characters like ‘ ‘, ‘.’, and ‘1’, and that it could
work with single-character words. The result was as expected, where
"prince-ton line-numbers  . 1tests  a" returned “Prince-ton Line-numbers  . 1tests  A”.


/**********************************************************************
 * Citing Resources                                                   *
 **********************************************************************/

List below *EVERY* resource your project group looked at (bullet lists and
links suffice).

COS 126 Lecture on Sorting and Searching Slides 33 & 34 https://www.cs.princeton.edu/courses/archive/fall21/cos126/static/lectures/CS.11.SearchSort.pdf 
Bayesian Average Formula https://www.codementor.io/@arpitbhayani/solving-an-age-old-problem-using-bayesian-average-15fy4ww08p 
PRAW documentation (Python Reddit scraper) https://praw.readthedocs.io/en/stable/ 
Data source for “real” headlines: https://www.reddit.com/r/nottheonion/
Data source for “onion” headlines: https://www.reddit.com/r/TheOnion/ 
All StdDraw information https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
Using Scanner to read from files https://www.java67.com/2012/11/how-to-read-file-in-java-using-scanner-example.html 
Closed class words: https://mailman.uib.no/public/corpora/2011-November/014318.htmlt
Audio file: Summer 1st Movement, Antonio Vivaldi. https://www.youtube.com/watch?v=e0eljruR8Lk
Jar file creation: https://docs.oracle.com/javase/tutorial/deployment/jar/build.html



Remember that you should *ALSO* be citing every resource that informed your
code at/near the line(s) of code that it informed.

Did you receive help from classmates, past COS 126 students, or anyone else?
If so, please list their names.  ("A Sunday lab TA" or "Office hours on
Thursday" is ok if you don't know their name.)
Yes or no?

No.


Did you encounter any serious problems? If so, please describe.
Yes or no?

No.



List any other comments here.




/**********************************************************************
 * Submission Checklist                                               *
 **********************************************************************/

Please mark that you’ve done all of the following steps:
[X] Created a project.zip file, unzipped its contents, and checked that our
    compile and run commands work on the unzipped contents. Ensure that the .zip
    file is under 50MB in size.
[X] Created and uploaded a Loom or YouTube video, set its thumbnail/starting
    frame to be an image of your program or a title slide, and checked that
    the video is viewable in an incognito browser.
[X] Uploaded all .java files to TigerFile.
[X] Uploaded project.zip file to TigerFile.

After you’ve submitted the above on TigerFile, remember to do the following:
[X] Complete and upload readme.txt to TigerFile.
[X] Complete and submit Google Form
    (https://forms.cs50.io/de2ccd26-d643-4b8a-8eaa-417487ba29ab).


/**********************************************************************
 * Partial Credit: Bug Report(s)                                      *
 **********************************************************************/

For partial credit for buggy features, you may include a bug report for at
most 4 bugs that your project group was not able to fix before the submission
deadline. For each bug report, copy and paste the following questions and
answer them in full. Your bug report should be detailed enough for the grader
to reproduce the bug. Note: if your code appears bug-free, you should not
submit any bug reports.

BUG REPORT #1:
1. Describe in a sentence or two the bug.




2. Describe in detail how to reproduce the bug (e.g. run commands, user input,
   etc.).




3. Describe the resulting effect of bug and provide evidence (e.g.
   copy-and-paste the buggy output, reference screenshot files and/or buggy
   output files, include a Loom video of reproducing and showing the effects of
   the bug, etc.).




4. Describe where in your program code you believe the bug occurs (e.g. line
   numbers).




5. Please describe what steps you tried to fix the bug.





/**********************************************************************
 * Extra Credit: Runtime Analysis                                     *
 **********************************************************************/

You may earn a small amount of extra credit by analyzing the efficiency of one
substantial component of your project. Please answer the following questions if
you would like to be considered for the extra credit for program analysis
(remember to copy and paste your answers to the following questions into the
Google form as well for credit).

Specify the scope of the component you are analyzing (e.g. function name,
starting and ending lines of specific .java file).




What is the estimated runtime (e.g. big-O complexity) of this component?
Provide justification for this runtime (i.e. explain in your own words why
you expect this component to have this runtime performance).




Provide experimental evidence in the form of timed analysis supporting this
runtime estimate. (Hint: you may find it helpful to use command-line
arguments/flags to run just the specified component being analyzed).





/**********************************************************************
 * Extra Credit: Packaging project as an executable .jar file         *
 **********************************************************************/

You may earn a small amount of extra credit by packaging your project as an
executable .jar file. Please answer the following question if you would like to
be considered for this extra credit opportunity (remember to copy and paste your
answers to the following questions into the Google form as well for credit).

Describe in detail how to execute your .jar application (e.g. what execution
command to use on the terminal). Include a few example execution commands and
the expected results of running your program. For non-textual outputs
(e.g. graphical or auditory), feel free to describe in words what the output
should be or reference output files (e.g. images, audio files) of the expected
output.

The .jar file was too large to attach in TigerFile, being 98mb, but it
runs when the user types:
java -jar project.jar
The input and output is the same as above. I couldn't get the IntelliJ artifact
tool to work because my compiled DeterminingAbsurdity and other custom classes
were not appearing in the .jar, so I created it by extracting that .jar using 
this command:
jar xvf COS\ 126.jar 
Then I manually added the DeterminingAbsurdity and other compiled classes, and then 
recreating the jar using this command:
jar cvmf MANIFEST.MF project.jar *.*
Although I am unable to attach my .jar file I hope this clarifies my process and
shows that we did actually do the process of creating one, and it runs on
my computer using the above commands.