import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;

public class Snake {
    public static final int HEIGHT = 25; 
    public static final int WIDTH = 25;
    public static final int n = 20;
    private static JLabel score;
    public static int scoreNum = 0;
    Panel p;

    public static void main(String[] args) {
        Panel p = new Panel();

        JFrame f = new JFrame();
        f.setTitle("Snake");
        Image img = new ImageIcon("snake.png").getImage();
        f.setIconImage(img);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        f.add(p);
        
        // add panel on right side - use flowlayout - that shows the stats
        JPanel menu = new JPanel();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));
        menu.setPreferredSize(new Dimension(150, HEIGHT * n));
        menu.setBackground(new Color(43,43,43));
        menu.setBorder(BorderFactory.createLineBorder(new Color(145, 138, 60), 5));
        f.add(menu);

        JLabel tit = new JLabel("Sssnake");
        tit.setForeground(new Color(171, 170, 91));
        tit.setFont(new Font("Comic Sans", Font.ITALIC, 30));
        tit.setBounds(10, 40, 130, 50);
        tit.setBorder(BorderFactory.createLineBorder(new Color(145, 138, 60), 5));
        menu.add(tit);

        score = new JLabel("Score: " + scoreNum);
        score.setForeground(new Color(171, 170, 91));
        score.setFont(new Font("Comic Sans", Font.ITALIC, 20));
        score.setBounds(10, 160, 130, 50);
        menu.add(score);
        
        f.pack();
        f.setLocationRelativeTo(null); // Sets frame in middle of screen
        f.setVisible(true);

        p.startGame();
    }

    public static void updateScore() {
        System.out.println("success");
        scoreNum = scoreNum + 1;
        System.out.println("new score: " + scoreNum);
        score.setText("Score: " + scoreNum);
    }

}