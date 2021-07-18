import javax.swing.*;
import javax.swing.border.Border;

import java.util.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;

public class TwoMatrixFrame extends JFrame implements ActionListener {
    JButton submit;
    JButton setZeros;
    JButton clear;
    JLabel answer;
    int r;
    int c;
    String name;
    List<JTextField> fieldList;
    List<JLabel> labelList;

    public TwoMatrixFrame(int r, int c, String name) {
        this.r = r;
        this.c = c;
        this.name = name;
        int r2 = Math.max(r, 5);
        int c2 = Math.max(c, 5);
        int n = Math.max(r, c);
        n = Math.max(n, 5);
        int x;
        int y;
        if (name.equals("Transpose")) {
            x = Math.max(300 * 2, n * 45  * 2 + 120);
            y = Math.max(205, n * 30 + 110);
        } else {
            x = Math.max(300 * 2, c * 45  * 2 + 120);
            y = Math.max(205, r * 30 + 110);
        }
       

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(x, y);
        this.setTitle("Matrix " + name + " Calculator");
        this.setLayout(null);
        this.setFocusable(false);

        //Constructs Matrix of text fields
        JPanel matrix = new JPanel();
        matrix.setLayout(new GridLayout(r, c, 5, 5));
        matrix.setBounds(20, 20, c * 40, r * 30);
        this.add(matrix);
        fieldList = new ArrayList<>();
        //Adds textfields to panel
        for (int i = 0; i < r * c; i++) {
            fieldList.add(new JTextField());
        }
        for(JTextField field: fieldList) {
            matrix.add(field);
        }

        //Constructs matrix to output answer
        JPanel matrix2 = new JPanel();
        if (name.equals("Transpose")) {
            matrix2.setLayout(new GridLayout(c, r, 5, 5));
            matrix2.setBounds(x - n * 40 - 35, 20, r * 40, c * 30);
        } else {
            matrix2.setLayout(new GridLayout(r, c, 5, 5));
            matrix2.setBounds(x - c2 * 40 - 35, 20, c * 40, r * 30);
        }
        this.add(matrix2);
        //Add labels to panel
        labelList= new ArrayList<>();
        for (int i = 0; i < r * c; i++) {
            labelList.add(new JLabel());
        }
        for(JLabel label : labelList) {
            Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
            label.setBorder(border);
            label.setHorizontalAlignment(JLabel.CENTER);
            matrix2.add(label);
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
        if (name.equals("Transpose")) {
            submit.setBounds(n * 40  + 55 , y/2 - 30 , 80, 30);
        } else {
            submit.setBounds(c2 * 40  + 55 , y/2 - 30 , 80, 30);
        }
        this.add(submit);

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
            int[][] matrix = new int[r][c]; // [row][column]
            for (int i = 0; i < r * c; i++) {
                matrix[i / c][i % c] = values.get(i);;
            }
            /*for (int i = 0; i < matrix.length; i++) {
                System.out.println(Arrays.toString(matrix[i]));
            } */
            if (name.equals("Echelon")) {
                calcEchelon(matrix);
            } else if (name.equals("Power")) {
                calcPower(matrix);
            } else if(name.equals("Inverse")) {
                calcInverse(matrix);
            } else if (name.equals("Transpose")) {
                calcTranspose(matrix);
            }
        }
    }

    //Bug discovered, currently working on fix
    private void calcEchelon(int[][] matrixInt) {
        double[][] matrix = new double[r][c];
        //switches matrix from int to double
        for (int row = 0; row < r; row++) {
            for(int col = 0; col < c; col++) {
                matrix[row][col] = matrixInt[row][col];
            }
        }

        for (int i = 1; i < r; i++) {
            if (matrix[i][0] != 0) {
                
            }
        }
        setTextDouble(matrix);
    }

    //Elementary matrix row operations
    private void rowSwitch(double[][] matrix, int row1, int row2) {
        for (int i = 0; i < c; i++) {
            double temp = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = temp;
        }
    }
    private void rowMultiply(double[][] matrix, int row, int factor) {
        for(int i = 0; i < c; i++) {
            matrix[row][i] = matrix[row][i] * factor;
        }
    }
    private void rowAdd(double[][] matrix, int row, int addedRow, int factor) { // row1 + (f * row2)
        for(int i = 0; i < c; i++) {
            matrix[row][i] = matrix[row][i] + matrix[addedRow][i] * factor;
        }
    }

    //Very inefficient method, fix later
    private void calcPower(int[][] matrix) {
        String input= JOptionPane.showInputDialog(null, "Enter the power the matrix will be raised to (integer). ", "Enter the Power", JOptionPane.QUESTION_MESSAGE);
        int power = Integer.parseInt(input);
        
        int[][] changedMatrix = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                changedMatrix[i][j] = matrix[i][j];
            }
        }
        
        for(int t = 0; t < power - 1; t++) {
            List<Integer> solution = new ArrayList<>();
            for(int row = 0; row < r; row++) {
                for (int column = 0; column < c; column++) {
                    int sum = 0;
                    for(int nums = 0; nums < c; nums++) {
                        sum+= changedMatrix[row][nums] * matrix[nums][column];
                    }
                    solution.add(sum);
                }
            }

            for (int i = 0; i < r * c; i++) {
                changedMatrix[i / c][i % c] = solution.get(i);;
            }
        }
        setText(changedMatrix);
    }

    //Bug discovered, currently working on fix
    private void calcInverse(int[][] matrix) {
        setText(matrix);
    }

    private void calcTranspose(int[][] matrix) {
        int[][] newMatrix = new int[c][r];
        for(int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                newMatrix[j][i] = matrix[i][j];
            }
        }
        setText(newMatrix);
    }
    
    private void setText(int[][] matrix) {
        int i = 0;;
        for (JLabel label: labelList) {
            int newC = matrix[0].length;
            label.setText("" + matrix[i / newC][i % newC]);
            i++;
        }
    }

    //combine two methods later
    private void setTextDouble(double[][] matrix) {
        int i = 0;;
        for (JLabel label: labelList) {
            int newC = matrix[0].length;
            label.setText("" + matrix[i / newC][i % newC]);
            i++;
        }
    }

}
