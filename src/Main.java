import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static final int MAX_GUESSES = 10;
    private static  int POINTS = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("movies.txt"));
        Scanner userInput = new Scanner(System.in);
        List<String> movies = new ArrayList<>();

        while (scanner.hasNext()) {
            movies.add(scanner.nextLine());
        }
        Random random = new Random();
        String movie = movies.get(random.nextInt(movies.size()));
 //rA       System.out.println(movie);

        List<Character> playerGuesses = new ArrayList<>();
        List<Character> wrongGuesses = new ArrayList<>();
        theMovieToBeGuessed(movie, playerGuesses, wrongGuesses);
        checkWinnerState(movie, playerGuesses);

        while (true) {
            getPlayerGuess(userInput, movie, playerGuesses, wrongGuesses);
            if (wrongGuesses.size() >= MAX_GUESSES) {
                System.out.println("\nGame over!");
                break;
            }
            if (checkWinnerState(movie, playerGuesses)) {
                System.out.println("\nCongratulations! You've guessed the movie correctly: " + movie);
                break;
            }
        }
    }
    private static boolean checkWinnerState(String movie, List<Character> playerGuesses) {
        for (int i = 0; i < movie.length() ; i++) {
            char character = movie.charAt(i);
            if (character != '_' && character != ' '  && !playerGuesses.contains(movie.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    private static void getPlayerGuess(Scanner userInput, String movie, List<Character> playerGuesses, List<Character> wrongGuesses) {
        System.out.println("\nPlease guess a letter: ");
        String letterGuess = userInput.nextLine();


        if (letterGuess.isEmpty()){
            System.out.println("Please enter valid character");
            theMovieToBeGuessed(movie, playerGuesses, wrongGuesses);
            getPlayerGuess(userInput, movie, playerGuesses, wrongGuesses);
            return;
        }
        char guessedLetter = letterGuess.charAt(0);
        if (letterGuess.length() != 1){
            System.out.println("Please enter ONE character ONLY");
            theMovieToBeGuessed(movie, playerGuesses, wrongGuesses);
            getPlayerGuess(userInput, movie, playerGuesses, wrongGuesses);
            return;
        }
        if (playerGuesses.contains(guessedLetter) || wrongGuesses.contains(guessedLetter)) {
            System.out.println("You have already guessed that letter. Try again.");
        } else {
            if (movie.contains(String.valueOf(guessedLetter))) {
                playerGuesses.add(guessedLetter);
            } else {
                wrongGuesses.add(guessedLetter);
                POINTS++;
            }
        }
        theMovieToBeGuessed(movie, playerGuesses, wrongGuesses);
    }
    private static void theMovieToBeGuessed(String movie, List<Character> playerGuesses, List<Character> wrongGuesses) {
        System.out.print("You are guessing: ");
        for (int i = 0; i < movie.length(); i++) {
            char character = movie.charAt(i);
            if (character == ' ') {
                System.out.print(" ");
            } else if (playerGuesses.contains(character)) {
                System.out.print(character);
            } else {
                System.out.print("_");
            }
        }
        System.out.print("\nYou have guessed (" + POINTS + ") wrong letters:"+ wrongGuesses);
    }
}


