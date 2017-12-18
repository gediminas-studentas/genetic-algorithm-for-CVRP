package cvrp.problem;

public class Depot {

    private final int number;
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
