package byog.Core;


public class Position{
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object pos) {
        Position p = (Position) pos;
        return this.x == p.x && this.y == p.y;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + x;
        hash = hash * 31 + y;
        return hash;
    }

    public Position[] surrounding() {
        Position[] p = new Position[8];
        p[0] = new Position(x - 1, y - 1);
        p[1] = new Position(x - 1, y);
        p[2] = new Position(x - 1, y + 1);
        p[3] = new Position(x, y + 1);
        p[4] = new Position(x + 1, y + 1);
        p[5] = new Position(x + 1, y);
        p[6] = new Position(x + 1, y - 1);
        p[7] = new Position(x, y - 1);
        return p;
    }

    public boolean near(Position p) {
        if((Math.abs(this.x - p.x) == 1 && this.y == p.y) ||
                (Math.abs(this.y - p.y) == 1 && this.x == p.x)){
            return true;
        }
        return false;
    }

}