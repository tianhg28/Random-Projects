import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.util.*;


public class Frame extends JFrame implements ActionListener {
    private JButton enter;
    private JTextField letters;
    private List<String> dictionary;
    private JPanel panel;

    public Frame(List<String> dictionary) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("Word Finder");
        this.dictionary = dictionary;
        this.setSize(900, 600);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(116, 199, 247));

        Font titleFont = new Font("Comic Sans", Font.ITALIC, 60);
        Font inputFont = new Font("Arial", Font.PLAIN, 35);

        // Adds the Title
        JLabel title1 = new JLabel("Word");
        title1.setFont(titleFont);
        title1.setForeground(new Color(1, 117, 15));
        title1.setBounds(110, 40, 200, 50);
        this.add(title1);
        JLabel title2 = new JLabel("Finder");
        title2.setFont(titleFont);
        title2.setForeground(Color.BLUE);
        title2.setBounds(100, 120, 200, 50);
        this.add(title2);

        // Adds Input and Description
        JLabel desc = new JLabel("Type your letters below!");
        desc.setFont(new Font("Arial", Font.PLAIN, 25));
        desc.setBounds(60, 200, 300, 100);
        this.add(desc);

        letters = new JTextField();
        letters.setBounds(80, 300, 220, 45);
        letters.setFont(inputFont);
        letters.setAlignmentY(JTextField.BOTTOM_ALIGNMENT);
        this.add(letters);

        enter = new JButton("Enter");
        enter.setBounds(130, 400, 120, 40);
        enter.addActionListener(this);
        this.add(enter);

        // Creates the output scroll panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 247, 207));
        panel.setPreferredSize(new Dimension(400, 500));

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBounds(410, 30, 420, 500);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scroll);


        this.setVisible(true);
    }

    private void findWords(String response) {
        panel.removeAll();
        List<JTextArea> texts = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        LetterInventory userWord = new LetterInventory(response);
        // Adds relevant words of all length to answers.
        for (String word: dictionary) {
            LetterInventory dicWord = new LetterInventory(word);
            if (userWord.contains(dicWord)) {
                answers.add(word);
            }
        }
        int length = 0;
        // Removes words from answer and adds them to the text area in order by length
        while (!answers.isEmpty()) {
            List<String> temp = new ArrayList<>();  // List of words with current length
            // Removes word from answers and adds to temp (with appropriate length)
            for (int i = 0; i < answers.size(); i++) {
                String word = answers.get(i);
                if (word.length() == length) {
                    temp.add(answers.remove(i));
                    i -= 1;
                }
            }
            // Creates a new text area with the words w/ current length
            if (!temp.isEmpty()) {
                texts.add(new JTextArea(length + " Letter Words: " + temp.toString()));
            }
            length += 1;
        }
        panel.setPreferredSize(new Dimension(400, 5 + texts.size() * 105)); //x = 400, y = boxes * 100 + margin
        int n = 0;
        // change font, background color
        for (JTextArea text: texts) {
            text.setLineWrap(true);
            text.setWrapStyleWord(true);
            text.setEditable(false);
            text.setBackground(new Color(231, 255, 207));
            text.setFont(new Font("Times New Roman,", Font.PLAIN, 18));
            text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JScrollPane scroll = new JScrollPane(text);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setBounds(5, 5 + 105 * n, 390, 100);
            panel.add(scroll);
            n++;
        }
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enter) {
            findWords(letters.getText());
        }
        
    }
}
