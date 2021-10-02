import java.util.*;
import java.io.*;

public class HangmanMain {

	public static void main(String[] args) throws FileNotFoundException{
		
		// Prints intro to get game
		System.out.println("Need help solving guessing letters in hangman? Well, you've come to ");
		System.out.println("the right place! Just enter the number of letters in the word and the ");
		System.out.println("Hangman solver will automatically tell you the best letter to guess! ");
		System.out.println("");
		
		// Opens the dictionary and reads the file into an arrayList
		Scanner input = new Scanner(new File("dictionary.txt"));
		List<String> dictionary = new ArrayList<>();
		while (input.hasNext()) {
			dictionary.add(input.next().toLowerCase());
		}
		input.close();
		
		// Sets basic parameters
		Scanner console = new Scanner(System.in);
		System.out.print("Enter the number of letters in the word you are guesses: ");
		int letters = console.nextInt();
		System.out.println("");
		HangmanSolver solver = new HangmanSolver(dictionary, letters);
		playGame(solver, console);
		console.close();
		
		// Word is guessed and game program finishes
		System.out.println("");
		System.out.println("");
		System.out.println("You guessed the word! It was " + solver.possibleWords() + "! Congrats on cheating!");
	}
	
	public static void playGame(HangmanSolver solver, Scanner console) {
		while (!solver.gameOver()) {
			System.out.println("Current word: " + solver.getWord());
			String guess = solver.bestGuess();
			System.out.println("Past guesses: " + solver.getGuesses());
			System.out.println("Your best guess right now would be: " + guess);
			System.out.print("Was there a \"" + guess + "\"? (y/n) ");
			String response = console.next().toLowerCase();
			if (response.charAt(0) == 'n') {
				String[] dummy = new String[0];
				solver.changeDict(false, dummy, guess);
			} else {
				System.out.println("Enter the index number(s) (1 - " + solver.getLetters() + ") the letter appear in. ");
				System.out.print("If are multiple indexes, seperated them by commas: ");
				String index = console.next(); 
				String[] indexes = index.split("[,]+"); // fix so allows space with comma
				solver.changeDict(true, indexes, guess); 
			}
			System.out.println("");
			if (solver.possibleWords().size() > 1) {
				System.out.println("There are currently " + solver.words() + " possible words to guess.");
				if (solver.words() <= 40) {
					System.out.println("You are close! Here are the list of possible answers:");
					System.out.println(solver.possibleWords());
				} else {
					System.out.println("(shows possible words when there are less than 40)");
				}
				System.out.println("");
			}
		}
	}
}
