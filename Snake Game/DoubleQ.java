import java.util.*;

public class DoubleQ {
    private List<int[]> coords;
    // end of list is front of snake - ADD TO END OF LIST
    // index 0 of list is back of snake - REMOVE FROM INDEX 0

    public DoubleQ() {
        coords = new ArrayList<>();
    }

    public void addFront(int x, int y) {
        coords.add(new int[] {x, y});
    }

    public int[] getFront() {
        return coords.get(coords.size() - 1);
    }

    public int[] remove() {
        return coords.remove(0);
    }
    
    public boolean contains(int[] other) {
        for (int[] a: coords) {
            if (a[0] == other[0] && a[1] == other[1]) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String s = "";
        for (int[] a: coords) {
            s += Arrays.toString(a) + " ";
        }
        return s;
    }
}
