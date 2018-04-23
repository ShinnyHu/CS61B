package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Set;

public class ReachableSpace {
    public Set<Position> floorSet = new HashSet<>();
    public Position p;
    TETile[][] world;

    public ReachableSpace(Position p, TETile[][] world) {
        this.p = p;
        this.world = world;
        floorSet.add(p);
    }

    public Set<Position> getFloorSet() {
        return floorSet;
    }

    public void addAllFloor(int count) {
        if(count == 1000) {
            return;
        }

        Set<Position> temp = new HashSet<>();
        for (Position p : floorSet) {
            Position[] surround  = p.surrounding();
            for (int i = 1; i < surround.length; i += 2) {
                if(valid(surround[i]) && world[surround[i].x][surround[i].y] == Tileset.FLOOR) {
                    temp.add(surround[i]);
                }
            }
        }
        floorSet.addAll(temp);
        count += 1;
        addAllFloor(count);
    }

    public boolean valid(Position p) {
        return (p.x >= 0 && p.x < world.length) && (p.y >= 0 && p.y < world[0].length);
    }


}
