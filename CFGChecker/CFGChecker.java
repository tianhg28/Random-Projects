//Work on validating input at the end and adding epsilon function

import java.util.*;

public class CFGChecker {
    public static List<String> results = new ArrayList<>();
    public static void main(String args[]) {
        
        Map<String, List<String>> cfgInfo = new TreeMap<>(); // see if can make global later
        
        Scanner console = new Scanner(System.in);
        Random rand = new Random();

        //Displays Intro
        System.out.println("Start symbol is S. Use [->] for arrow, [|] for or, and space for epsilon.");
        System.out.println("Input is case-senstive and ignores whitespace.");
        System.out.println("Example: S -> 01 | 11 | e");

        //Collects the CFG
        String response = console.nextLine();
        while(!response.contains("S ->") && !response.contains("S->") ) {
            System.out.println("Please enter using the proper format described earlier.");
            response = console.nextLine();
        }
        while(!response.isEmpty()) {
            String[] line = response.split("->");
            line[0] = line[0].trim();
            String[] options = line[1].split("[|]");
            List<String> optionsList = new ArrayList<>();
            for (String option : options) {
                option = option.trim();
                optionsList.add(option);
            }
            cfgInfo.put(line[0], optionsList);
            response = console.nextLine();
        }

        //Generate Random Strings
        System.out.println("Generate Random String? (y/n)");
        String yesNo = console.next();
        System.out.println("");
        if (yesNo.toLowerCase().substring(0, 1).equals("y")) {
            System.out.println("How many strings to generate?");
            int times = console.nextInt();
            System.out.println("");

            for (int t = 0; t < times; t ++) {
                int num = rand.nextInt(cfgInfo.get("S").size());
                String chosen = cfgInfo.get("S").get(num);  //Randomsly chooses one of options from S
                String result = "";
                for(int i = 0; i < chosen.length(); i++) { //iterates over chosen option
                    String ch = chosen.charAt(i) + "";
                    if (!containsNT(cfgInfo.keySet(), ch)) {
                        result = result + ch;
                    } else {
                        result = result + getString(cfgInfo, ch, rand);
                    }
                }
                System.out.println(result);
            }
        }

        //Verify String
        System.out.println("Checker grammer? (y/n)");
        yesNo = console.next();
        if (yesNo.substring(0,1).toLowerCase().equals("y")) {
            System.out.println("Enter string to check if it is in CFG.");
            String input = console.next();
            System.out.println("");

            for(String symbol: cfgInfo.get("S")) {
                testString(cfgInfo, "", symbol, input);
            }
            
            if(results.isEmpty()) {
                System.out.println("The string does not match the CFG language.");
            } else {
                System.out.println("The string does match the CFG language!");
            }
            
        }

    }
    
    //Psuedo-code for cfg checker part 
    //asks user for cfg and a string - already have map with nt and terminals
    //count the number of characters in string
    //start with S, for each option, generate all possble string paths
    //check if number of chars in string is at or over limit, if yes
    // - if generated string matches input stirng, return true
    // - if no match, dont do anything, stop current recursive call and move onto other possbilities
    //also if string generation ends and no match, terminate call
    //at end when all possible string combination has been exhausted and no string match, return false

    public static void testString(Map<String, List<String>> cfgInfo, String result, String remaining, String input) {
        //make sure under char limit

        //System.out.println("(" + result + ") (" + remaining + ")");
        
        if (remaining.isEmpty()) {
            if (result.equals(input)){
                results.add(result);
            }
        } else if (result.length() < input.length()) {
            String ch = remaining.substring(0, 1);
            remaining = remaining.substring(1);
            if (!containsNT(cfgInfo.keySet(), ch)) {
                testString(cfgInfo, result + ch, remaining, input);
            } else {
                for(String symbol: cfgInfo.get(ch)) {
                    testString(cfgInfo, result, symbol + remaining, input);
                }
            }
        }
    }

    //Reucursive function to help build the string
    public static String getString(Map<String, List<String>> cfgInfo, String symbol, Random rand) {
        // symbol (ch) is a variable

        int num = rand.nextInt(cfgInfo.get(symbol).size());
        String chosenSymbol = cfgInfo.get(symbol).get(num); //chooses a symbol at random 
        String result = "";
        for(int i = 0; i < chosenSymbol.length(); i++) { //iterates over chosen option
            String ch = chosenSymbol.charAt(i) + "";
            if (!containsNT(cfgInfo.keySet(), ch)) {
                result = result + ch;
            } else {
                result = result + getString(cfgInfo, ch, rand);
            }
        }

        return result;
    }
    

    //Check if passed string contain any of the non-terminals
    public static boolean containsNT (Set<String> nts, String str) {
        for (String nt: nts) {
            if (str.contains(nt)) {
                return true;
            }
        }
        return false;
    }
}

//start from S, and then choose a random string option 
    //check if option contains a left-hand non terminal (is replacable)
    // - if does not great, stop (base case)
    // - if does, then for each variable
    //construct an answer string, start at left and go to the right
    //if character then add to string, if variable:

    //1 given a variable, pick a random option
    //2 check if option contains a left-hand non terminal (is replacable)
    //3 - if does not great, stop (base case)
    //4 - if does, then for each variable, start again from step 1 (recursive)
    //print out compiled string
