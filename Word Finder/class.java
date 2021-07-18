import java.util.*;

class Solution {
    public int numDifferentIntegers(String word) {
        char[] charArray = word.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if ((int)c >= 97 && (int)c < 123) {
                charArray[i] = ' ';
            }
        }
        word = "";
        for (int i = 0; i < charArray.length; i++) {
            word = word + charArray[i];
        }
        
        System.out.println(Arrays.toString(charArray));
        System.out.println(word);
        
        String[] wordSplit = word.split("[ ]+");
        
        System.out.println(Arrays.toString(wordSplit));
        
        Set<Integer> unique = new TreeSet<>();
        for (int i = 0; i < wordSplit.length; i++) {
            if (!wordSplit[i].isEmpty()) {
                System.out.println(wordSplit[i]);
                int num = Integer.valueOf(wordSplit[i]);
                unique.add(num);
            }
        }
        
        
        
        return unique.size();
    }
}