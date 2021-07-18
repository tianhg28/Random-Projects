import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.imageio.ImageIO;

public class MainFrame extends JFrame implements ActionListener {
    JButton det;
    JButton gaussJordan;
    JButton power;
    JButton inverse;
    JButton transpose;
    JButton multiplication;
    JButton addition;
    JButton subtraction;
    /*
    JTextField row1;
    JTextField column1;
    JTextField row2;
    JTextField column2; */

    JSpinner row1;
    JSpinner column1;
    JSpinner row2;
    JSpinner column2;

    public static void main(String[] args) {
        MainFrame main = new MainFrame();
    }

    public MainFrame() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);   
        this.setSize(750, 500);
        this.setResizable(false);
        this.setFocusable(false);
        this.setTitle("Matrix Calculator");
        
        //Set Background
        try {
            this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("graybg.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Title
        JLabel title1 = new JLabel("Matrix");
        title1.setFont(new Font("Comic Sans", Font.ITALIC, 40));
        title1.setForeground(Color.BLACK);
        title1.setBounds(30, 10, 200, 50);
        this.add(title1);
        JLabel title2 = new JLabel("Calculator");
        title2.setFont(new Font("Comic Sans", Font.ITALIC, 40));
        title2.setForeground(Color.BLACK);
        title2.setBounds(80, 60, 200, 50);
        this.add(title2);

        //Panel
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(4, 2, 10, 35));
        bPanel.setBounds(350, 60, 340, 350);
        bPanel.setOpaque(false);
        this.add(bPanel);

        //Buttons
        det = new JButton("Determinant");
        det.addActionListener(this);
        det.setFocusable(false);
        bPanel.add(det);

        gaussJordan = new JButton("Gauss Jordan");
        gaussJordan.addActionListener(this);
        gaussJordan.setFocusable(false);
        bPanel.add(gaussJordan);
        
        power = new JButton("Power");
        power.addActionListener(this);
        power.setFocusable(false);
        bPanel.add(power);

        inverse = new JButton("Inverse");
        inverse.addActionListener(this);
        inverse.setFocusable(false);
        bPanel.add(inverse);

        transpose = new JButton("Transpose");
        transpose.addActionListener(this);
        transpose.setFocusable(false);
        bPanel.add(transpose);

        multiplication = new JButton("Multiplication");
        multiplication.addActionListener(this);
        multiplication.setFocusable(false);
        bPanel.add(multiplication);

        addition= new JButton("Addition");
        addition.addActionListener(this);
        addition.setFocusable(false);
        bPanel.add(addition);

        subtraction = new JButton("Subtraction");
        subtraction.addActionListener(this);
        subtraction.setFocusable(false);
        bPanel.add(subtraction);

        // Textfields & User Interface
        //Matrix 1 Label
        JLabel label1= new JLabel("Dimensions of 1st Matrix:");
        label1.setBounds(20, 140, 250, 50);
        label1.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(label1);
        JLabel x1 = new JLabel("X");
        x1.setBounds(115, 210, 40, 25);
        x1.setFont(new Font("Arial", Font.PLAIN, 15));
        this.add(x1);
        JLabel extra = new JLabel("(rows)                   (columns)");
        extra.setBounds(50, 230, 180, 25);
        this.add(extra);

        //Matrix 1 Input
        row1 = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        row1.setBounds(50, 210, 40, 25);
        this.add(row1);
        column1 = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        column1.setBounds(150, 210, 40, 25);
        this.add(column1);

        //Matrix 2 Label
        JLabel label2 = new JLabel("Dimensions of 2nd Matrix:");
        label2.setBounds(20, 275, 250, 50);
        label2.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(label2);
        JLabel label3 = new JLabel("(if applicable)");
        label3.setBounds(20, 300, 100, 40);
        label3.setFont(new Font("Arial", Font.PLAIN, 14));
        this.add(label3);
        JLabel x2 = new JLabel("X");
        x2.setBounds(115, 360, 40, 25);
        x2.setFont(new Font("Arial", Font.PLAIN, 15));
        this.add(x2);
        JLabel extra2 = new JLabel("(rows)                   (columns)");
        extra2.setBounds(50, 380, 180, 25);
        this.add(extra2);

        //Matrix 2 Input
        row2 = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        row2.setBounds(50, 360, 40, 25);
        this.add(row2);
        column2 = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        column2.setBounds(150, 360, 40, 25);;
        this.add(column2);

        this.setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            row1.commitEdit();
        } catch (ParseException e1) {
        }
        int r1 = (Integer) row1.getValue(); 
        int c1 = (Integer) column1.getValue();
        int r2 = (Integer) row2.getValue();
        int c2 = (Integer) column2.getValue();

        if (e.getSource() == det ) {
            DetCalc detcalc = new DetCalc(r1);
        }
        if (e.getSource() == gaussJordan) {
            TwoMatrixFrame gFrame = new TwoMatrixFrame(r1, c1, "Echelon");
        }
        if (e.getSource() == transpose) {
            TwoMatrixFrame tFrame = new TwoMatrixFrame(r1, c1, "Transpose");
        }
        if (e.getSource() == power) {
            if (r1 != c1) {
                JOptionPane.showMessageDialog(null, "Rows must equal columns!", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                TwoMatrixFrame pFrame = new TwoMatrixFrame(r1, c1, "Power");
            }
        }
        if (e.getSource() == inverse) {
            if (r1 != c1) {
                JOptionPane.showMessageDialog(null, "Rows must equal columns!", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                TwoMatrixFrame iFrame = new TwoMatrixFrame(r1, c1, "Inverse");
            }
        }

        if (e.getSource() == multiplication) {
            if (c1 != r2) {
                JOptionPane.showMessageDialog(null, "Number of columns in matrix 1 must equal rows in matrix 2!", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                ThreeMatrixFrame mFrame = new ThreeMatrixFrame(r1, c1, r2, c2, "Multiplication");
            }
        }
        if (e.getSource() == addition) {
            ThreeMatrixFrame mFrame = new ThreeMatrixFrame(r1, c1, r2, c2, "Addition");
        }
        if (e.getSource() == subtraction) {
            ThreeMatrixFrame mFrame = new ThreeMatrixFrame(r1, c1, r2, c2, "Subtraction");
        }
        
    }
}
