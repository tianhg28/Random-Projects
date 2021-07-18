import javax.swing.*;
import java.util.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;

public class DetCalc extends JFrame implements ActionListener {
    JButton submit;
    JButton setZeros;
    JButton clear;
    JLabel answer;
    int n;
    List<JTextField> fieldList;

    public DetCalc(int n) {
        this.n = n;
        int n2 = Math.max(n, 5);
        int x = Math.max(350, n * 45 + 170);
        int y = Math.max(205, n * 30 + 110);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(x, y);
        this.setLayout(null);
        this.setTitle("Determinant Calculator");

        //Constructs Matrix of text fields
        JPanel matrix = new JPanel();
        matrix.setLayout(new GridLayout(n, n, 5, 5));
        matrix.setBounds(10, 10, n * 40, n * 30);
        this.add(matrix);
        fieldList = new ArrayList<>();
        
        //Adds textfields to panel
        for (int i = 0; i < n * n; i++) {
            fieldList.add(new JTextField());
        }
        for(JTextField field: fieldList) {
            matrix.add(field);
        }

        //Buttons
        setZeros = new JButton("Fill blanks w/ 0");
        setZeros.setBounds(15, y - 80, 117, 20);
        setZeros.addActionListener(this);
        this.add(setZeros);

        clear = new JButton("Clear");
        clear.setBounds(143, y - 80, 65, 20);
        clear.addActionListener(this);
        this.add(clear);

        submit = new JButton("Submit");
        submit.addActionListener(this);
        submit.setBounds((x - (n2 * 40)) / 2 + n2 * 40 - 40, y/4 - 30 , 80, 30);
        this.add(submit);

        //JLabel for answer
        JLabel answerLabel = new JLabel("The Determinant is: ");
        answerLabel.setBounds((x - (n2 * 40)) / 2 + n2 * 40 - 60, y/4 + 30 , 120, 30);
        this.add(answerLabel);

        answer = new JLabel("");
        answer.setBounds((x - (n2 * 40)) / 2 + n2 * 40 - 60, y/4 + 65 , 115, 30);
        answer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        answer.setHorizontalAlignment(JLabel.CENTER);
        this.add(answer);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == setZeros) {
            for (JTextField field: fieldList) {
                if (field.getText().length() == 0) {
                    field.setText("0");
                }
            }
        } 
        if (e.getSource() == clear) {
            for (JTextField field: fieldList) {
                field.setText("");
            }
        }
        if (e.getSource() == submit) {
            List<Integer> values = new ArrayList<>();
            for (JTextField field: fieldList) {
                try {
                    values.add(Integer.valueOf(field.getText()));
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, "Please enter valid values for each input!", "Error", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
            calculateDet(values, n);
        }
        
    }

    public void calculateDet(List<Integer> values, int n) {
        int[][] matrix = new int[n][n]; // [row][column]
        for (int i = 0; i < n * n; i++) {
            matrix[i / n][i % n] = values.get(i);;
        }
        /*
        //Debug
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }*/
        int determinant = 0;
        for (int i = 0; i < n; i++) {
            determinant += matrix[0][i] * Math.pow(-1, i) * getThatD(n, 0, i, matrix);
        }
        if (values.size() == 1) {
            determinant = values.get(0);
        }
        answer.setText("" + determinant);
    }

    private int getThatD(int n, int row, int column, int[][] matrix) {
        int[][] newMatrix = new int[n-1][n-1];
        int newR = 0;
        for (int r = 0; r < n; r++) {
            int newC = 0;
            for (int c = 0; c < n; c++) {
                if (r == row) {
                newR--;
                break;
                } else if (c != column) {
                newMatrix[newR][newC] = matrix[r][c];
                newC++;
                }
            }
            newR++;
        }
        if (newMatrix.length == 1) {
            return newMatrix[0][0];
        } else {
            int determinant = 0;
            for (int i = 0; i < n - 1; i++) {
                determinant += newMatrix[0][i] * Math.pow(-1, i) * getThatD(n - 1, 0, i, newMatrix);
            }
            return determinant;
        }
    }
}
