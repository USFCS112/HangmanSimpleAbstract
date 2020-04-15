import java.util.Scanner;

public class HangmanUser extends Hangman {
    
    /**
     * get a guess from user. Ignore all but first char. keep asking until get valid guess
     * @return the guess
     */
    @Override
    public char getGuess() {
        System.out.print("Please enter a guess:");
        Scanner scanner = new Scanner(System.in);
        String guessS = scanner.next();
        char guess = guessS.charAt(0);  // should error check and make sure at least one letter
        while (previousGuesses.contains(guessS)) {
            System.out.println("you have already guessed that, please try a different letter");
            guessS= scanner.next();
            guess = guessS.charAt(0);
        }
        return guess;
    }

    public static void main(String [] args) {
        Hangman hangman = new HangmanUser();
        hangman.play();

    }
}
