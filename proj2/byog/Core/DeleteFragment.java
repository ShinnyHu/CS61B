package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Set;

public class DeleteFragment {
    public Set<Position> largestFloorSet;
    public Set<Position> floorFrag = new HashSet<>();
    public Set<Position> wallFrag = new HashSet<>();
    TETile[][] world;

    public DeleteFragment(Set<Position> largestFloorSet, TETile[][] world) {
        this.largestFloorSet = largestFloorSet;
        this.world = world;
    }

    public void findFloorFrag() {
        for(int i = 0; i < world.length; i++) {
            for(int j = 0; j < world[0].length; j++) {
                Position p = new Position(i, j);
                if(world[i][j] == Tileset.FLOOR && (!(largestFloorSet.contains(p)))) {
                    floorFrag.add(p);
                }
            }
        }
    }

    public void findWallFrag() {
        for(Position p: floorFrag) {
            Position[] surround  = p.surrounding();
            for (int i = 0; i < surround.length; i++) {
                if(valid(surround[i]) && world[surround[i].x][surround[i].y] == Tileset.WALL) {
                    wallFrag.add(surround[i]);
                }
            }
        }
    }

    public void deleteFloor() {
        for (Position p: floorFrag) {
            world[p.x][p.y] = Tileset.NOTHING;
        }
    }

    public void deleteWall() {
        // delete walls for empty(nothing) rooms:
        for (Position p: wallFrag) {
            world[p.x][p.y] = Tileset.NOTHING;
        }

        // delete isolate walls:
        for(int i = 0; i < world.length; i++) {
            for(int j = 0; j < world[0].length; j++) {
                Position p = new Position(i, j);

                if(world[i][j] == Tileset.WALL) {
                    Boolean del = true;
                    Position[] surround = p.surrounding();
                    for (int k = 0; k < surround.length; k++) {
                        if(valid(surround[k])) {
                            if(world[surround[k].x][surround[k].y] != Tileset.NOTHING) {
                                del = false;
                            }
                        }
                    }
                    if(del) {
                        world[i][j] = Tileset.NOTHING;
                    }
                }
            }
        }
    }

    public void enclosure() {
        for (Position p: largestFloorSet) {
            Position[] surround  = p.surrounding();
            for (int i = 0; i < surround.length; i++) {
                if(valid(surround[i]) && world[surround[i].x][surround[i].y] == Tileset.NOTHING) {
                    world[surround[i].x][surround[i].y] = Tileset.WALL;
                }
            }
        }
    }

    public boolean valid(Position p) {
        return (p.x >= 0 && p.x < world.length) && (p.y >= 0 && p.y < world[0].length);
    }
}
