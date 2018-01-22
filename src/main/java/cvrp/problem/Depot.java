package cvrp.problem;

/**
 * Sandelis
 */
public class Depot {

    //eilės numeris naudojamas genuose
    private final int number;
    //koordinatės
    private final Point point;

    public Depot(int number, Point point) {
        this.number = number;
        this.point = point;
    }

    public int getNumber() {
        return number;
    }

    public Point getPoint() {
        return point;
    }
}
