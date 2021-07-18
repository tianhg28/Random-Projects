import javax.swing.*;
import javax.swing.border.Border;

import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;

public class ThreeMatrixFrame extends JFrame implements ActionListener {
    JButton submit;
    JButton setZeros;
    JButton clear;
    JLabel answer;
    int r;
    int c;
    int r2;
    int c2;
    String name;
    List<JTextField> fieldList;
    List<JTextField> fieldList2;
    List<JLabel> labelList;

    public ThreeMatrixFrame(int r1, int c1, int r2, int c2, String name) {
        this.r = r1;
        this.c = c1;
        this.r2 = r2;
        this.c2 = c2;
        this.name = name;
        int x;
        int y;
        if (name.equals("Multiplication")) {
            x = (c + c2 * 2) * 40 + 105 + 100;
            y = Math.max(r, r2) * 30 + 110;
        } else {
            x = c * 40 * 3 + 105 + 100;
            y = r * 30 + 110;
        }
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(x, y);
        this.setTitle("Matrix " + name + " Calculator");
        this.setLayout(null);
        this.setFocusable(false);

        //Constructs Matrix 1
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

        //Constructs Matrix 2
        JPanel matrix2 = new JPanel();
        if (name.equals("Multiplication")) {
            matrix2.setLayout(new GridLayout(r2, c2, 5, 5));
            matrix2.setBounds(c * 40 + 40, 20, c2 * 40, r2 * 30);
        } else {
            matrix2.setLayout(new GridLayout(r, c, 5, 5));
            matrix2.setBounds(c * 40 + 40, 20, c * 40, r * 30);
        }
        this.add(matrix2);
        fieldList2 = new ArrayList<>();
        //Adds textfields to panel
        int tot;
        if (name.equals("Multiplication")) {
            tot = r2 * c2;
        } else {
            tot = r * c;
        }
        for (int i = 0; i < tot; i++) {
            fieldList2.add(new JTextField());
        }
        for(JTextField field: fieldList2) {
            matrix2.add(field);
        }

        //Adds operation sign label
        String operation;
        if (name.equals("Multiplication")) {
            operation = "X";
        } else if (name.equals("Subtration")) {
            operation = "-";
        } else {
            operation = "+";
        }
        JLabel sign = new JLabel(operation);
        sign.setBounds(c * 40 + 23, r * 30 / 2 , 40, 40);
        sign.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(sign);
        JLabel equals = new JLabel("=");
        if (name.equals("Multiplication")) {
            equals.setBounds((c + c2) * 40 + 45, r * 30 / 2, 40, 40);
        } else {
            equals.setBounds(c * 80 + 45, r * 30 / 2 , 40, 40);
        }
        equals.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(equals);

        //Constructs output matrix
        JPanel matrix3 = new JPanel();
        if (name.equals("Multiplication")) {
            matrix3.setLayout(new GridLayout(r, c2, 5, 5));
            matrix3.setBounds((c + c2) * 40 + 65, 20, c2 * 40, r * 30);
        } else {        
            matrix3.setLayout(new GridLayout(r, c, 5, 5));
            matrix3.setBounds(c * 40 * 2 + 65, 20, c * 40, r * 30);
        }
        this.add(matrix3);
        //Add labels to panel
        labelList= new ArrayList<>();
        if (name.equals("Multiplication")) {
            tot = r * c2;
        } else {
            tot = r * c;
        }
        for (int i = 0; i < tot; i++) {
            labelList.add(new JLabel());
        }
        for(JLabel label : labelList) {
            Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
            label.setBorder(border);
            label.setHorizontalAlignment(JLabel.CENTER);
            matrix3.add(label);
        }

        //Buttons
        setZeros = new JButton("Fill blanks w/ 0");
        setZeros.setBounds(13, y - 80, 117, 20);
        setZeros.addActionListener(this);
        this.add(setZeros);

        clear = new JButton("Clear");
        clear.setBounds(138, y - 80, 65, 20);
        clear.addActionListener(this);
        this.add(clear);

        submit = new JButton("Submit");
        submit.addActionListener(this);
        if (name.equals("Multiplication")) {
            submit.setBounds((c + c2 * 2) * 40 + 90, r * 30 / 2 ,80, 30);
        } else {
            submit.setBounds((c * 3) * 40 + 90, r * 30 / 2 ,80, 30);
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
            for (JTextField field: fieldList2) {
                if (field.getText().length() == 0) {
                    field.setText("0");
                }
            }
        } 
        if (e.getSource() == clear) {
            for (JTextField field: fieldList) {
                field.setText("");
            }
            for (JTextField field: fieldList2) {
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
            List<Integer> values2 = new ArrayList<>();
            for (JTextField field: fieldList2) {
                try {
                    values2.add(Integer.valueOf(field.getText()));
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, "Please enter valid values for each input!", "Error", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
            if (name.equals("Multiplication")) {
                calcMult(values, values2);
            } else if (name.equals("Subtraction")) {
                calcSub(values, values2);
            } else if(name.equals("Addition")) {
                calcAdd(values, values2);
            }
        }
    }

    //Figure out multiplication algorithm later
    private void calcMult(List<Integer> values, List<Integer> values2) {
        int[][] matrix = new int[r][c]; // [row][column]
        for (int i = 0; i < r * c; i++) {
            //System.out.println(i / r + " " + i % c);
            matrix[i / c][i % c] = values.get(i);;
        }

        int[][] matrix2 = new int[r2][c2]; // [row][column]
        for (int i = 0; i < r2 * c2; i++) {
            matrix2[i / c2][i % c2] = values2.get(i);;
        } 

        List<Integer> solution = new ArrayList<>();
        for(int row = 0; row < r; row++) {
            for (int column = 0; column < c2; column++) {
                int sum = 0;
                for(int nums = 0; nums < c; nums++) {
                    sum+= matrix[row][nums] * matrix2[nums][column];
                }
                solution.add(sum);
            }
        }
        int i = 0;
        for (JLabel label: labelList) {
            label.setText("" + solution.get(i));
            i++;
        }
    }

    private void calcAdd(List<Integer> values,List<Integer> values2) {
        int i = 0;
        for (JLabel label: labelList) {
            label.setText("" + (values.get(i) + values2.get(i)));
            i++;
        }
    }

    private void calcSub(List<Integer> values,List<Integer> values2) {
        int i = 0;
        for (JLabel label: labelList) {
            label.setText("" + (values.get(i) - values2.get(i)));
            i++;
        }
    }

}
