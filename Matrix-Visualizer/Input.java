import javax.swing.*;
import java.util.*;
import java.awt.GridLayout;
import java.awt.event.*;

public class Input extends JFrame implements ActionListener {
    JButton submit;
    JButton setZeros;
    JButton clear;
    JLabel answer;
    int n;
    List<JTextField> fieldList;
    private final int frames = 20;

    public Input(int n) {
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
            List<Double> values = new ArrayList<>();
            for (JTextField field: fieldList) {
                try {
                    values.add(Double.valueOf(field.getText()));
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, "Please enter valid values for each input!", "Error", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
            double[][] matrix = new double[2][2];
            for (int i = 0; i < 4; i++) {
                matrix[i/2][i%2] = values.get(i);
            }

            double x1Dif = (matrix[0][0] - 1) / frames;
            double y1Dif = matrix[1][0] / frames;
            double x2Dif = matrix[0][1] / frames;
            double y2Dif = (matrix[1][1] - 1) / frames;

            //matrix[0][0] = 1; matrix[1][0] = 0; matrix[0][1] = 0; matrix[1][1] = 1;

            //Main.drawPanel(matrix, x1Dif, y1Dif, x2Dif, y2Dif, frames);

            try {
                Main.drawPanel(matrix);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
        }
    }
}
