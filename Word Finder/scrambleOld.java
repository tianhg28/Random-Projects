public class scrambleOld {
    public static void main(String[] args) throws FileNotFoundException {
        
        Scanner input = new Scanner(new File("dictionary.txt"));
        List<String> dictionary = new ArrayList<>();
        while (input.hasNextLine()) {
            dictionary.add(input.nextLine());
        }
        input.close();

        Frame f = new Frame(dictionary);

    }
    public static void playGame(List<String> dictionary, Scanner console) {
        boolean playMore = true;
        System.out.println("");
        System.out.println("Type your next word here (press enter if none): ");
        String response = console.nextLine();
        if (response.length() == 0) {
            playMore = false;
        }
        while(playMore) {
            // Collects user input
            //System.out.print("Enter the given letters: ");
            //String response = console.next();
            // Starts computing
            List<String> answers = new ArrayList<>();
            LetterInventory userWord = new LetterInventory(response);
            for (String word: dictionary) {
                LetterInventory dicWord = new LetterInventory(word);
                if (userWord.contains(dicWord)) {
                    answers.add(word);
                }
            }
            // Outputs to user
            System.out.println("");
            System.out.println("Here is the list of all possible words you can use!");
            int length = 0;
            while (!answers.isEmpty()) {
                List<String> temp = new ArrayList<>();
                for (int i = 0; i < answers.size(); i++) {
                    String word = answers.get(i);
                    if (word.length() == length) {
                        temp.add(answers.remove(i));
                        i -= 1;
                    }
                }
                if (!temp.isEmpty()) {
                    System.out.println(length + " character words:");
                    System.out.println(temp);
                }
                length += 1;
            }
            // Asks if user if they have another word
            System.out.println("");
            System.out.println("Type your next word here (press enter if none): ");
            response = console.nextLine();
            if (response.length() == 0) {
                playMore = false;
            }
        }
    }
}
