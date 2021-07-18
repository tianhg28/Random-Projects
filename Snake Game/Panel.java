import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
//import java.util.Timer;


public class Panel extends JPanel implements ActionListener, KeyListener{
    public final int HEIGHT = 25; 
    public final int WIDTH = 25;
    public final int n = 20;
    public final int initLen= 5;
    DoubleQ coords;
    int[] fruit;
    int direction;
    boolean started;
    Timer timer;

    public Panel() {
        this.setPreferredSize(new Dimension (WIDTH * n, HEIGHT * n));
        this.setFocusable(true);
        this.addKeyListener((this));
        
        coords = new DoubleQ();
        fruit = new int[2];
        genFruit();
        started = true;
        direction = 2;
        timer = new Timer(100, this);
    }

    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        //Initializes the Game Background
        if (started) {
            for (int h = 0; h < HEIGHT; h++) { // paints background
                for (int w = 0; w < WIDTH; w++) {
                    if ((h + w) % 2 == 0) {
                        g2.setPaint(new Color(138, 212, 106));
                    } else {
                        g2.setPaint(new Color(111, 199, 74));
                    }
                    g2.fillRect(w * n, h * n, n, n);
                }
            }
            /*
            g2.setPaint(new Color(43,43,43));
            g2.fillRect(WIDTH * n, 0, 150, HEIGHT * n);
            g2.setPaint(new Color(145, 138, 60));
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(WIDTH * n  + 2, 2, 145, HEIGHT * n  - 4);
*/
            //Paints snake
            int initY = (HEIGHT / 2);
            int initX = (WIDTH / 2);
            g2.setPaint(new Color(14, 131, 204));    // body color
            g2.fillRect((initX - initLen - 1) * n, initY * n, n, n);   // draw back
            coords.addFront(initX - initLen - 1, initY);       // Add to queue
            for (int i = initLen - 2; i > 0; i--) {           // paints snake
                g2.fillRect((initX - i) * n, initY * n, n, n);
                coords.addFront(initX - i, initY);
            }
            g2.setPaint(new Color(12, n, 117));      // nHead color
            g2.fillRect(initX * n, initY * n, n, n); // draw front
            coords.addFront(initX, initY);           // Add to queue
            started = false;

            //Paints fruit
            g2.setPaint(Color.RED);
            g2.fillOval(fruit[0] * n, fruit[1] * n, n, n);
        }

        int[] nHead = coords.getFront().clone();
        switch(direction) { // Determines nHead coords
            case 1:
                nHead[1] = nHead[1] - 1;
                break;
            case 2:
                nHead[0] = nHead[0] + 1;
                break;
            case 3:
                nHead[1] = nHead[1] + 1;
                break;
            case 4:
                nHead[0] = nHead[0] - 1;
                break;
        }
        if(coords.contains(nHead) || nHead[0] < 0 || nHead [1] < 0 || nHead[0] >= WIDTH || nHead[1] >= HEIGHT) {
            timer.stop();
        } else {
            g2.setPaint(new Color(14, 131, 204));    // body color
            g2.fillRect(coords.getFront()[0] * n, coords.getFront()[1] * n, n, n); // fills in current nHead
            g2.setPaint(new Color(12, n, 117)); //nHead color
            g2.fillRect(nHead[0] * n, nHead[1] * n, n, n);// colors nHead
            coords.addFront(nHead[0], nHead[1]);          // add coords
            //If fruit not eaten
            if (!Arrays.equals(fruit, nHead)) {                 
                int[] back = coords.remove(); // gets tail coord
                if ((back[0] + back[1]) % 2 == 0) { // chooses color
                    g2.setPaint(new Color(138, 212, 106));
                } else {
                    g2.setPaint(new Color(111, 199, 74));
                }
                g2.fillRect(back[0] * n, back[1] * n, n, n); // recolors background
            //If fruit is eaten
            } else {              
                Snake.updateScore();
                System.out.println("update");                                  
                genFruit();
                g2.setPaint(Color.RED);
                g2.fillOval(fruit[0] * n, fruit[1] * n, n, n);
            }
        }
    }

    public void startGame() {
        timer.start();;
    }

    private void genFruit() {
        Random rand = new Random();
        int x = rand.nextInt(WIDTH); int y = rand.nextInt(HEIGHT);
        if (!coords.contains(new int[] {x , y})) {
            fruit[0] = x; fruit[1] = y;
        } else {
            genFruit();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer)
            repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar()) {
			case 'w': 
                if (direction != 3)
                    direction = 1;
				break;
			case 'd': 
                if (direction != 4)
                    direction = 2;
				break;
			case 's': 
                if (direction != 1)
                    direction = 3;
				break;
			case 'a': 
                if (direction != 2)
                    direction = 4;
				break;
		}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
