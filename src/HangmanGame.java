import java.util.*;

public class HangmanGame {
    private static final int MAX_LIVES = 6;
    private List<String> words = Arrays.asList("apple", "banana", "cherry", "grape", "orange");
    private String selectedWord;
    private char[] guessedWord;
    private int lives;
    private Set<Character> guessedLetters;
    private int wins;
    private int losses;

    public HangmanGame() {
        guessedLetters = new HashSet<>();
        wins = 0;
        losses = 0;
    }

    private void selectRandomWord() {
        Random random = new Random();
        selectedWord = words.get(random.nextInt(words.size()));
        guessedWord = new char[selectedWord.length()];
        Arrays.fill(guessedWord, '_');
        lives = MAX_LIVES;
        guessedLetters.clear();
    }

    private void displayWord() {
        for (char c : guessedWord) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    private void play() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        System.out.println("Welcome to Hangman!");

        while (playAgain) {
            selectRandomWord();
            while (lives > 0) {
                displayWord();
                System.out.println("Lives remaining: " + lives);
                System.out.print("Guess a letter or the whole word: ");
                String guess = scanner.nextLine().toLowerCase();

                if (guess.length() == 1) {
                    char letter = guess.charAt(0);
                    if (guessedLetters.contains(letter)) {
                        System.out.println("You already guessed that letter.");
                        continue;
                    }
                    guessedLetters.add(letter);
                    if (selectedWord.indexOf(letter) >= 0) {
                        revealLetter(letter);
                        System.out.println("Correct guess!");
                        if (isWordComplete()) {
                            System.out.println("Congratulations! You guessed the word: " + selectedWord);
                            wins++;
                            break;
                        }
                    } else {
                        lives--;
                        System.out.println("Incorrect guess.");
                    }
                } else if (guess.length() == selectedWord.length()) {
                    if (guess.equals(selectedWord)) {
                        System.out.println("Congratulations! You guessed the word: " + selectedWord);
                        wins++;
                        break;
                    } else {
                        lives--;
                        System.out.println("Incorrect guess.");
                    }
                } else {
                    System.out.println("Invalid input. Please guess a letter or the whole word.");
                }
            }

            if (lives == 0) {
                System.out.println("Game Over! The word was: " + selectedWord);
                losses++;
            }

            System.out.println("Wins: " + wins + " | Losses: " + losses);
            System.out.print("Do you want to play again? (yes/no): ");
            String response = scanner.nextLine().toLowerCase();
            if (!response.equals("yes")) {
                playAgain = false;
                System.out.println("Thanks for playing! Final Score -> Wins: " + wins + " | Losses: " + losses);
            }
        }
    }

    private void revealLetter(char letter) {
        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == letter) {
                guessedWord[i] = letter;
            }
        }
    }

    private boolean isWordComplete() {
        for (char c : guessedWord) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        HangmanGame game = new HangmanGame();
        game.play();
    }
}
