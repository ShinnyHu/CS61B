package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.stddraw.StdDraw;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

public class MapVisual {

    TERenderer ter;
    public static int width;
    public static int height;
    private TETile[][] finalWorld;
    public long seed;
    public Random random;
    public Set<Position> largestFloor;

    //TODO: implement the seed generation of world
    public MapVisual(String s, TERenderer t, int w, int h) {
        long temp = 0;
        for(int i = 1; i < s.length() - 1; i++) {
            if(s.charAt(i) == 'S' && s.charAt(i) == 's') {
                break;
            }
            temp = temp * 10 + Character.getNumericValue(s.charAt(i));
        }
        seed = temp;

        this.ter = t;
        this.width = w;
        this.height = h;
        ter.initialize(width, height + 3);
        finalWorld = new TETile[width][height + 3];
        random = new Random(seed);

    }

    public MapVisual(TETile[][] world, TERenderer ter) {
        finalWorld = world;
        this.ter = ter;
        this.width = world.length;
        this.height = world[0].length - 3;
        ter.initialize(width, height + 3);
    }

    /** Return the generated final world. */
    public TETile[][] getWorld() {
        return finalWorld;
    }

    /** Initiation of the world using Nothing everywhere.
     *  So that nullPointerException is being avoid.
     */
    public void initWorld() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height + 3; j++) {
                finalWorld[i][j] = Tileset.NOTHING;
            }
        }
    }

    /** Create one rectangular room.
     *  Position p -> bottom left of the room;
     *  int w -> room width + 2(left and right walls);
     *  int l -> room length + 2(upper and lower walls). */
    public void newRoom(Position p, int w, int l) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < l; j++) {
                int newX = i + p.x;
                int newY = j + p.y;
                if(i == 0 || i == w - 1 || j == 0 || j == l - 1) {
                    if (!intersect(newX, newY)) {
                        finalWorld[newX][newY] = Tileset.WALL;
                    }
                } else {
                    finalWorld[newX][newY] = Tileset.FLOOR;
                }
            }
        }
    }

    public void newHallway(Position p, int len, char c) {
        if (c == 'v') {
            newRoom(p, 3, len);
        } else if (c =='h') {
            newRoom(p, len, 3);
        } else {
            throw new NoSuchElementException();
        }
    }

    public boolean intersect(int x, int y) {
        return finalWorld[x][y] == Tileset.FLOOR;
    }

    public void multipleRooms(int count) {
        for(int i = 0; i < count; i++) {
            Position p = generateRandomPos(4);
            newRoom(p, generateRandomLenForRoom(p.x, width),
                    generateRandomLenForRoom(p.y, height));
        }
    }

    /** Generate random width and height for rooms.
     *  pos: x or y coordinate
     *  available: the space that can be placed
     */
    public int generateRandomLenForRoom(int pos, int available) {
        int r = random.nextInt(available - pos);

        // Make sure the room is not too small or too large.
        while(r < 4 || r > width / 8) {
            r = random.nextInt(available - pos);
        }
        return r;
    }

    public void multipleHallways(int nVertical ,int nHorizon) {
        for(int i = 0; i < nVertical; i++) {
            Position p = generateRandomPos(3);
            newHallway(p, generateRandomLenForHallway(p.y, height), 'v');
        }
        for(int i = 0; i < nHorizon; i++) {
            Position p = generateRandomPos(3);
            newHallway(p, generateRandomLenForHallway(p.x, width), 'h');
        }
    }

    public int generateRandomLenForHallway(int pos, int available) {
        int r = random.nextInt(available - pos);
        while(r < 3 || r > width / 2) {
            r = random.nextInt(available - pos);
        }
        return r;
    }

    public Position generateRandomPos(int spaceNeeded) {
        return new Position(random.nextInt(width - spaceNeeded),
                        random.nextInt(height - spaceNeeded));
    }


    public Set<Position> findReachableSpace() {
        Position p = generateRandomPos(0);
        while(finalWorld[p.x][p.y] != Tileset.FLOOR) {
            p = generateRandomPos(0);
        }
        ReachableSpace s = new ReachableSpace(p, finalWorld);
        s.addAllFloor(0);
        return s.getFloorSet();

    }

    public int findLargestSpace() {
        Set<Position> floorSet = findReachableSpace();
        int r = floorSet.size();
        int count = 0;
        while(r < width * height * 0.1 && count < 10) {
            floorSet = findReachableSpace();
            r = floorSet.size();
            count += 1;
        }
        if(count == 10) {
            throw new NoSuchElementException("Very bad luck");
        }
        for(int i = 0; i < 10; i++) {
            Set<Position> temp = findReachableSpace();
            if(temp.size() > r) {
                floorSet = temp;
                r = floorSet.size();
            }
        }
        largestFloor = floorSet;
        return r;
    }

    public void deleteFragment() {
        DeleteFragment d = new DeleteFragment(largestFloor, finalWorld);
        d.findFloorFrag();
        d.findWallFrag();
        d.deleteFloor();
        d.deleteWall();
        d.enclosure();
    }

    public void moverAround(boolean isLoad) {
        MoveAround mov = new MoveAround(finalWorld);
        if(!isLoad) {
            mov.setRandom(random);
            mov.init();
            finalWorld = mov.world;
        } else {
            mov.set();
        }
        updateImg();


        while (!mov.player.near(mov.door)){
            mouseListen();
            char c = mov.getIns();
            finalWorld = mov.move(c);
            System.out.print(c);
            updateImg();
        }
        if(mov.player.near(mov.door)) {
            StdDraw.pause(2000);
        }
    }

    public void mouseListen() {
        updateImg();
        while (true) {
            Position mouse = listen();
            if(inImg(mouse)) {
                String s = finalWorld[mouse.x][mouse.y].description();
                drawGUI(s);
            } else {
                drawGUI("");
            }

            if(StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
    }

    public boolean inImg(Position p) {
        return (p.x >= 0 && p.x < width) && (p.y >= 0 && p.y < height);
    }

    public void updateImg() {
        ter.renderFrame(finalWorld);
//        drawLine();
    }

    public void startGame() {
        initWorld();
        multipleRooms(30);
        multipleHallways(20, 20);
        findLargestSpace();
        deleteFragment();
        moverAround(false);

        WinGame win = new WinGame(40, 40);
        win.drawFrame();
    }

    public void loadGame() {
        moverAround(true);

        WinGame win = new WinGame(40, 40);
        win.drawFrame();
    }

    public Position listen() {
        int x = (int)StdDraw.mouseX();
        int y = (int)StdDraw.mouseY();
        return new Position(x,y);
    }

    public void drawLine() {
        StdDraw.setPenColor(Color.white);
        StdDraw.line(0, height, width, height);
        StdDraw.show();
    }

    public void drawGUI(String s) {
        for (int x = 0; x < finalWorld.length; x += 1) {
            for (int y = height; y < height + 3; y += 1) {
                finalWorld[x][y].draw(x, y);
            }
        }
        StdDraw.setPenColor(Color.white);
        StdDraw.textLeft(1, height + 1, s);
        StdDraw.show();
    }

    public static void main(String[] args) {


        TERenderer ter = new TERenderer();
        MapVisual m = new MapVisual(args[0], ter, 60, 30);

        ter.initialize(m.getWorld().length, m.getWorld()[0].length);

        m.initWorld();
        m.multipleRooms(30);
        m.multipleHallways(20, 20);

        m.findLargestSpace();

        m.deleteFragment();

        m.moverAround(false);
    }


}
