import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain;
        int score = 0;

        System.out.println("Welcome to the Number Guessing Game!");

        do {
            int numberToGuess = random.nextInt(100) + 1;
            int attempts = 5;
            boolean guessedCorrectly = false;
            System.out.println("\nA new number has been generated. Try to guess it!");

            while (attempts > 0 && !guessedCorrectly) {
                System.out.print("Enter your guess (1-100): ");
                int userGuess = scanner.nextInt();

                if (userGuess == numberToGuess) {
                    System.out.println("Congratulations! You guessed the correct number.");
                    guessedCorrectly = true;
                    score += attempts;
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low!");
                } else {
                    System.out.println("Too high!");
                }

                attempts--;

                if (!guessedCorrectly && attempts > 0) {
                    System.out.println("You have " + attempts + " attempts left.");
                } else if (attempts == 0) {
                    System.out.println("Sorry, you've run out of attempts. The correct number was " + numberToGuess + ".");
                }
            }

            System.out.print("Would you like to play another round? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");

        } while (playAgain);

        System.out.println("\nGame Over! Your final score is: " + score);
        scanner.close();
    }
}
