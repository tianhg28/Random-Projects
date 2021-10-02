import java.util.*;

public class HangmanSolver {
	private List<String> dictionary;
	private List<String> guesses;
	private String answer;
	private int letters;
	
	public HangmanSolver(List<String> dictionary, int letters) {
		this.dictionary = new ArrayList<>();
		guesses = new ArrayList<>();
		for (String word: dictionary) {
			if (word.length() == letters) { 
				this.dictionary.add(word);
			}
		} 
		this.letters = letters;
		answer = repeat("-", letters);
	}
	
	// Returns the letter that appears the most
	public String bestGuess() {
		Map<Character, Integer> freq = new TreeMap<>();
		for (int val = 97; val < 123; val++) {
			char letter = (char)val;
			for (String word: dictionary) {
				if (word.contains(String.valueOf(letter))) {
					if (!freq.keySet().contains(letter)) {
						freq.put(letter, 0);
					}
					freq.put(letter, freq.get(letter) + 1);
				}
			}
		}
		int maxFreq = 0;
		char maxChar = '\0';
		for (char c: freq.keySet()) {
			int num = freq.get(c);
			if (num > maxFreq && !guesses.contains(String.valueOf(c))) {
				maxFreq = num;
				maxChar = c;
			}
		}
		return String.valueOf(maxChar);
	}
	
	// Adjust the dictionary accordingly based on user input
	public void changeDict(boolean correctGuess, String[] indexes, String guess) {
		
		if (correctGuess) {
			for (String sIndex: indexes) {
				int index = Integer.parseInt(sIndex) - 1;
				answer = replace(answer, index, guess);
				Iterator<String> itr = dictionary.iterator();
				while (itr.hasNext()) {
					String word = itr.next();
					if (word.charAt(index) != guess.charAt(0)) {
						itr.remove();
					}
				}
			}
		} else {
			Iterator<String> itr = dictionary.iterator();
			while (itr.hasNext()) {
				String word = itr.next();
				if (word.contains(guess)) {
					itr.remove();
				}
			}
		}
		guesses.add(guess);
	}
	
	public List<String> possibleWords() {
		return dictionary;
	}
	
	public List<String> getGuesses() {
		return guesses;
	}
	
	public String getWord() {
		return answer;
	}
	
	public int words() {
		return dictionary.size();
	}
	
	public int getLetters() {
		return letters;
	}
	
	public boolean gameOver() {
		if (dictionary.size() <= 1) {
			return true;
		} 
		return false;
	}
	
	private static String repeat(String text, int times) {
        String result = "";
        for (int i = 0; i < times; i++) {
            result = result + text;
        }
        return result;
    }
	
	private static String replace(String s, int index, String replacement) {
        return s.substring(0, index) + replacement + s.substring(index + 1);
    }
}
 