import java.util.*;

public class calculator {
    public static void main(String args[]) {
        //Gathers information about input
        Scanner console = new Scanner(System.in);
        System.out.println("Seperate Differential Equations (Enter 1)");
        System.out.println("1st Order Linear Differntial Equations (Enter 2)");
        System.out.println("2nd Order Linear Differntial Equations (Enter 3)");
        String num = console.next();
        System.out.println("");
        System.out.println("Enter the equation (use y' and y''' and * for multiplication) in standard form.");
        if (num.equals("1")) {
            System.out.println("Example: dy/dt=-4y");
        } else if(num.equals("2")) {
            System.out.println("Example: y'+4y=3cos(4t)");
        } else {
            System.out.println("Example: y''+3y'+2y=3e(5t)");
        }
        String equation = console.next();

        //Proccess equation
        String[] sides = equation.split("=");
        String left = sides[0]; String right = sides[1];
        //System.out.println(Arrays.toString(sides));

        if (num.equals("1")) { //Seperable
            String numb = right.substring(0, right.length() - 1);
            double k = Double.parseDouble(numb);
            System.out.println("Solution: y = Ae^(" + k + "t)");
        } else if (num.equals("2")) { //1st Order
            String[] terms = left.split("\\+");
            int[] coeffs = new int[terms.length];
            int index = 0;
            for (String term: terms) {
                term.trim();
                String temp = removeLetters(term);
                if (temp.isEmpty()) {
                    coeffs[index] = 1;
                } else {
                    coeffs[index] = Integer.parseInt(temp);
                }
                index++;
                
            }
            double p = coeffs[1];
            if (right.contains("e")) {
                String[] rightSplit = right.split("e");
                double k1 = Double.parseDouble(rightSplit[0]);
                double k2 = Double.parseDouble(removeLetters(rightSplit[1]));
                System.out.println("Solution: y = " + rightSplit[0] + "*e^(" + (k2-p) + "t) + Ce^(" + -p + "t)");
            } else { 
                System.out.println("Solution: y = " + right + "*e^(" + -p + "t) + Ce^(" + -p + "t)");
            }

        } else { //2nd Order
            String[] terms = left.split("\\+");
            int[] coeffs = new int[terms.length];
            int index = 0;
            for (String term: terms) {
                term.trim();
                String temp = removeLetters(term);
                if (temp.isEmpty()) {
                    coeffs[index] = 1;
                } else {
                    coeffs[index] = Integer.parseInt(temp);
                }
                index++;
            }
            //Add code later to account for cases: less than 3 terms, one of the coefficients is not present

            //Proccesses left side
            double a = coeffs[0];   double b = coeffs[1];   double c = coeffs[2];
            String result = "";
            double det = b*b-4*a*c;
            if (det > 0) {// 2 real roots:
                double r1 = round2(-b + Math.sqrt(det)) / (2*a);
                double r2 = round2(-b - Math.sqrt(det)) / (2*a);
                result = "y = C1 * e^(" + r1 + "t) + C2 * e^(" + r2 + "t)";
            } else if(det<0) { //2 imaginary roots
                double A = round2(-b  / (2*a));
                double B = round2(Math.sqrt(-det) / (2*a));
                result = "y = C1*cos(" + B + "t)*e^(" + A + "t) + C2*sin(" + B + "t)*e^(" + A + "t)";
            } else { // 1 Real Root
                double r = -b / (2*a);
                result = "y = C1*e^(" + r + "t) + C2*t*e^(" + r + "t)";
            }

            //Proccesses right side
            if (right.contains("e")) {
                String[] rightSplit = right.split("e");
                double k1 = Double.parseDouble(rightSplit[0]);
                double k2 = Double.parseDouble(removeLetters(rightSplit[1])); //Add method that removes all non-numerals in future
                double bigA = k1 / (a*k2*k2 + b*k2 + c);
                result = result + " + " + round2(bigA) + "e^(" + k2 + "t)";
            } else if (right.contains("cos") || right.contains("sin")) {
                String[] rightSplit = right.split("cos");
                String s = rightSplit[1];
                String temp = "";
                for (int i = 0; i < s.length(); i++) {
                    if (Character.isDigit(s.charAt(i))) {
                        temp = temp + s.charAt(i);
                    }
                }
                double k1 = Double.parseDouble(rightSplit[0]);
                double k2 = Double.parseDouble(temp);
                double m = c - a*k2*k2;
                double n = b*k2;
                double bigA;
                double bigB;
                if (right.contains("cos")) {
                    bigA = m*k1 / (m*m + n*n);
                    bigB = n*k1 / (m*m + n*n);
                } else {
                    bigA = -n*k1 / (m*m + n*n);
                    bigB = m*k1 / (m*m + n*n);
                }
                
                result = result + " + " + round2(bigA) + "cos(" + k2 + "t) + " + round2(bigB) + "sin(" + k2 + "t)";
            }

            System.out.println("Solution: " + result);
        }
    }

    public static double round2(double n) {
        return Math.round(n * 100.0) / 100.0;
    }

    public static String removeLetters(String str) {
        String result = "";
        for(int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                result = result + str.charAt(i);
            }
        }
        return result;
    }
}