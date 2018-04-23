package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.stddraw.StdDraw;

import java.util.Random;

public class MoveAround {
    public TETile[][] world;
    public Position player;
    public Position door;
    public Random random;
    public String allInput;

    public MoveAround(TETile[][] w) {
        world = w;
    }

    public void set() {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (world[i][j].equals(Tileset.PLAYER)) {
                    player = new Position(i, j);
                }
                if (world[i][j].equals(Tileset.LOCKED_DOOR)) {
                    door = new Position(i, j);
                }
            }
        }
    }

    public void setRandom(Random random) {
        player = new Position(random.nextInt(world.length), random.nextInt(world[0].length));
        door = new Position(random.nextInt(world.length), random.nextInt(world[0].length));
        this.random = random;
    }

    public void init() {
        while(world[player.x][player.y] != Tileset.FLOOR) {
            player = new Position(random.nextInt(world.length), random.nextInt(world[0].length));
        }
        while(!(world[door.x][door.y] == Tileset.WALL && connected(door))) {
            door = new Position(random.nextInt(world.length), random.nextInt(world[0].length));
        }
        world[player.x][player.y] = Tileset.PLAYER;
        world[door.x][door.y] = Tileset.LOCKED_DOOR;
    }

    public TETile[][] getWorld() {
        return world;
    }

    public Boolean connected(Position p) {
        Position[] surround = p.surrounding();
        for(int i = 1; i < surround.length; i += 2) {
            if(valid(surround[i]) && world[surround[i].x][surround[i].y] == Tileset.FLOOR) {
                return true;
            }
        }
        return false;
    }

    public boolean valid(Position p) {
        return (p.x >= 0 && p.x < world.length) && (p.y >= 0 && p.y < world[0].length);
    }

    public TETile[][] move(char c) {
        if(c == 'w') {
            if(world[player.x][player.y + 1].equals(Tileset.FLOOR)) {
                world[player.x][player.y + 1] = Tileset.PLAYER;
                world[player.x][player.y] = Tileset.FLOOR;
                player.y += 1;
            }
        } else if(c == 'a') {
            if(world[player.x - 1][player.y].equals(Tileset.FLOOR)) {
                world[player.x - 1][player.y] = Tileset.PLAYER;
                world[player.x][player.y] = Tileset.FLOOR;
                player.x -= 1;
            }
        } else if(c == 's') {
            if(world[player.x][player.y - 1].equals(Tileset.FLOOR)) {
                world[player.x][player.y - 1] = Tileset.PLAYER;
                world[player.x][player.y] = Tileset.FLOOR;
                player.y -= 1;
            }
        } else if(c == 'd') {
            if(world[player.x + 1][player.y].equals(Tileset.FLOOR)) {
                world[player.x + 1][player.y] = Tileset.PLAYER;
                world[player.x][player.y] = Tileset.FLOOR;
                player.x += 1;
            }
        }
        return world;
    }

    public char getIns() {
        String input = "";
        int i = 0;
        while(i < 1) {
            if(StdDraw.hasNextKeyTyped()) {
                input += StdDraw.nextKeyTyped();
                i += 1;
            }

        }
        char c = input.charAt(0);
        if(c == 'q' && allInput.charAt(allInput.length() - 1) == ':') {
            StdDraw.pause(1000);
            SaveAndLoad.saveWorld(world);
            System.exit(0);
        }
        allInput += c;
        return c;
    }
}
