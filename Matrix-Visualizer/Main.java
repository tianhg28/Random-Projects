import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class Main {
    public static final int w = 1120;
    public static final int h = 640;
    public static final int n = 40;
    public static final int xMax = w/n/2; //max x in cartesian coordinates (14)
    public static final int yMax = h/n/2; //max y in cartesian coordinates (8)
    public static final int xO = w/2;
    public static final int yO = h/2;
    public static final int frames = 80; //ENTER: The frames in the transformation
    public static final int time = 2000; //ENTER: How long the transformation will be in milliseconds
    public static int currFrame;
    public static Timer timer = new Timer();

    public static void main(String args[]) {
        Input inp = new Input(2);
    }

    public static void drawPanel(double[][] matrix) throws InterruptedException {

        DrawingPanel panel = new DrawingPanel(w, h);
        panel.setBackground(Color.BLACK);
        Graphics g = panel.getGraphics();

        drawBasis(g);
        drawAxis(g);
        
        //2d Arrays used to connect points top to bottom and left to right to create a coordinate grid
        //IN FUTURE NOTE: possibly make into 3d array to make code simplifer and less space
        double[][] top = new double[xMax*2 + 1][2];
        double[][] bottom = new double[xMax*2 + 1][2];
        double[][] left = new double[yMax*2 + 1][2];
        double[][] right = new double[yMax*2 + 1][2];
        
        //creates the matrices, 0 is x coord, 1 is y coord
        for (int i = 0; i < xMax * 2 + 1; i++) { 
            top[i][0] = -xMax + i;
            bottom[i][0] = -xMax + i;

            top[i][1] = yMax;
            bottom[i][1] = -yMax;
        }
        for (int i = 0; i < yMax * 2 + 1; i++) {
            left[i][1] = yMax - i;
            right[i][1] = yMax - i;

            left[i][0] = -xMax;
            right[i][0] = xMax;
        }

        double[][] topIntv = new double[xMax*2 + 1][2];
        double[][] botIntv = new double[xMax*2 + 1][2];
        double[][] leftIntv = new double[yMax*2 + 1][2];
        double[][] rightIntv = new double[yMax*2 + 1][2];

        //Begin Linear Transformation, transforms the points
        int index = 0;
        for(double[] pair: top) {
            List<Double> newCoord = multiply(matrix, pair);
            topIntv[index][0] = (newCoord.get(0) - pair[0]) / frames; //x intv
            topIntv[index][1] = (newCoord.get(1) - pair[1]) / frames; //y intv
            index++;
        }
        index = 0;
        for(double[] pair: bottom) {
            List<Double> newCoord = multiply(matrix, pair);
            botIntv[index][0] = (newCoord.get(0) - pair[0]) / frames; //x intv
            botIntv[index][1] = (newCoord.get(1) - pair[1]) / frames; //y intv
            index++;
        }
        index = 0;
        for(double[] pair: left) {
            List<Double> newCoord = multiply(matrix, pair);
            leftIntv[index][0] = (newCoord.get(0) - pair[0]) / frames; //x intv
            leftIntv[index][1] = (newCoord.get(1) - pair[1]) / frames; //y intv
            index++;
        }
        index = 0;
        for(double[] pair: right) {
            List<Double> newCoord = multiply(matrix, pair);
            rightIntv[index][0] = (newCoord.get(0) - pair[0]) / frames; //x intv
            rightIntv[index][1] = (newCoord.get(1) - pair[1]) / frames; //y intv
            index++;
        }
        drawPlane(g, top, bottom, left, right);

        //Begins the animation
        currFrame = 0;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currFrame++;
                if (currFrame >= frames) {
                    timer.cancel();
                }
                int index = 0;
                for(double[] pair: top) {
                    pair[0] = pair[0] + topIntv[index][0];
                    pair[1] = pair[1] + topIntv[index][1];
                    index++;
                }

                index = 0;
                for(double[] pair: bottom) {
                    pair[0] = pair[0] + botIntv[index][0];
                    pair[1] = pair[1] + botIntv[index][1];
                    index++;
                }

                index = 0;
                for(double[] pair: left) {
                    pair[0] = pair[0] + leftIntv[index][0];
                    pair[1] = pair[1] + leftIntv[index][1];
                    index++;
                }

                index = 0;
                for(double[] pair: right) {
                    pair[0] = pair[0] + rightIntv[index][0];
                    pair[1] = pair[1] + rightIntv[index][1];
                    index++;
                }
                panel.clear();
                drawBasis(g);
                drawAxis(g);
                drawPlane(g, top, bottom, left, right);
                
            }
        }, 1000, time/frames);

        /*
        for(int[] pair: right) {
            System.out.print("(" + pair[0] + ", " + pair[1] + ") ");
        } */
    }

    // Connects top & bottom points and left & right points to create a grid
    // Passes the array pairs to drawSeg (cartesian coordinates smul num) 
    public static void drawPlane(Graphics g, double[][] top, double[][] bottom, double[][] left, double[][] right) {
        g.setColor(new Color(35, 111, 136)); 
        for(int i = 0; i < top.length; i++) {
            drawSeg(g, top[i][0], top[i][1], bottom[i][0], bottom[i][1]);
        }
        for(int i = 0; i < left.length; i++) {
            drawSeg(g, left[i][0], left[i][1], right[i][0], right[i][1]);
        }
    }

    //Takes in input in cartesian coordinate, converts it to panel coordinates, then draw segment
    public static void drawSeg(Graphics g, double xI, double yI, double xF, double yF) {
        g.drawLine((int)(xO + xI*n), (int)(yO - yI*n), (int)(xO + xF*n), (int)(yO - yF*n));
    }

    public static List<Double> multiply(double[][] matrix1, double[] matrix2) {
        List<Double> solution = new ArrayList<>();
        for(int row = 0; row < 2; row++) {
            double sum = 0;
            for(int nums = 0; nums < 2; nums++) {
                sum+= matrix1[row][nums] * matrix2[nums];
            }
            solution.add(sum);
        }
        return solution;
    }

    //Draws Coordinate Plane Before Transformation
    public static void drawBasis(Graphics g) {
        g.setColor(new Color(55, 54, 55));
        for(int x = 0; x < 30; x++) {
            g.drawLine(x * n, 0, x * n, h);
        }
        for(int y = 0; y < 20; y++) {
            g.drawLine(0, y * n, w, y * n);
        }
    }

    //Draws Axis
    public static void drawAxis(Graphics g) {
        g.setColor(new Color(215, 214, 216));  //Horizontal
        g.drawLine(0, h/2 - 1, w, h/2 - 1);
        g.drawLine(0, h/2, w, h/2);
        g.drawLine(0, h/2 + 1, w, h/2 + 1); 
        g.drawLine(w/2 - 1, 0, w/2 - 1, h); //Vertical
        g.drawLine(w/2, 0, w/2, h);
        g.drawLine(w/2 + 1, 0, w/2 + 1, h);
    }
}
