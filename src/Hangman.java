import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public abstract class Hangman {
    protected String phrase="";
    protected StringBuilder hiddenPhrase=null;
    protected String previousGuesses="";

    public static int MAXGUESSES=6;

    public abstract char getGuess();

    /**
     * play a game of hangman, this version uses methods and data members
     */
    public void play() {

        this.phrase = this.randomPhrase();
        // generate the hidden phrase by replacing all non-space with *
        this.hiddenPhrase = this.getHiddenPhrase();

        System.out.println(this.phrase);
        System.out.println(this.hiddenPhrase);

        // guess
        boolean gameOver=false;
        int wrongGuesses=0;
        while (!gameOver) {
            System.out.println("previous guesses:"+this.previousGuesses);
            char guess = this.getGuess();
            this.previousGuesses= this.previousGuesses+guess;
            boolean match= this.processGuess(guess);
            if (match==false) {
                wrongGuesses++;
                if (wrongGuesses==MAXGUESSES) {
                    System.out.println("YOU LOSE");
                    gameOver=true;
                }
                System.out.println(guess+" is not in phrase");

            } else {
                if (this.phrase.equals(this.hiddenPhrase.toString())) {
                    System.out.println("YOU WIN");
                    gameOver = true;
                }

            }
            System.out.println("Wrong Guesses:"+wrongGuesses);
            System.out.println(this.hiddenPhrase);
        }

    }

    /**
     * opens a file, and chooses one of the lines as phrase
     * @return the random phrase
     */
    public String randomPhrase() {
        // Read phrases from file
        // Get random phrase from list
        List<String> phraseList= null;
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        Random random = new Random();
        int r = random.nextInt(2);
        String phrase = phraseList.get(r);
        return phrase;
    }

    /**
     * replace all letters with *, leave all other chars same
     * @return stringbuilder version, so we can later replace * when guess right
     */
    public StringBuilder getHiddenPhrase() {
        StringBuilder hiddenPhrase = new StringBuilder(phrase);

        // hide letters with asterisks in hiddenPhrase
        int i=0;
        while (i<phrase.length()) {
            if (Character.isLetter(phrase.charAt(i))) {
                hiddenPhrase.setCharAt(i,'*');
            }
            i=i+1;
        }
        return hiddenPhrase;
    }


    /**
     * find occurrences of guess in phrase, replace * in corresponding position of hidden
     * @param guess the letter guess
     * @return
     */
    public boolean processGuess(char guess) {
        boolean match=false;
        int i = 0;
        while (i < phrase.length()) {
            String guessS = ""+guess;
            String phraseCharS = ""+phrase.charAt(i);
            if (guessS.equalsIgnoreCase(phraseCharS)) {
                match=true;
                hiddenPhrase.setCharAt(i, phrase.charAt(i));

            }
            i = i + 1;
        }
        return match;


    }

}
