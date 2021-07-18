import java.util.*;

public class LetterInventory {
    private Map<Character, Integer> letterInv;

    public LetterInventory(String word) {
        letterInv = new TreeMap<>();
        for (int i = 97; i < 123; i++) {
            char currChar = (char)i;
            letterInv.put(currChar, 0);
            for (int j = 0; j < word.length(); j++) {
                if (word.toLowerCase().charAt(j) == currChar) {
                    letterInv.put(currChar, letterInv.get(currChar) + 1);
                }
            }
        }
    }

    public boolean contains(LetterInventory other) {
        for (int i = 97; i < 123; i++) {
            if (this.letterInv.get((char)i) < other.letterInv.get((char)i)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        String result = "";
        for (char c: letterInv.keySet()) {
            result += repeat(String.valueOf(c), letterInv.get(c));
        }
        return result;
    }

    private String repeat(String text, int times) {
        String result = "";
        for (int i = 0; i < times; i++) {
            result = result + text;
        }
        return result;
    }
}
