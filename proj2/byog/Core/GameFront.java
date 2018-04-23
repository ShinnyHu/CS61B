package byog.Core;

import byog.TileEngine.TERenderer;
import byog.stddraw.StdDraw;

import java.util.NoSuchElementException;

import java.awt.*;

public class GameFront {
    public static int width;
    public static int height;
    public Font font;
    public TERenderer ter;

    public GameFront(int w, int h, TERenderer ter) {
        this.width = w;
        this.height = h;
        this.ter = ter;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        font = new Font("Times", Font.PLAIN, 20);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height * 4 / 5, "CS61B: The Game");
        StdDraw.text(width / 2, height * 3 / 5, "New Game (N)");
        StdDraw.text(width / 2, height * 3 / 5 - 2, "Load Game (L)");
        StdDraw.text(width / 2, height * 3 / 5 - 4, "Quit (Q)");
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(width / 2, height * 2 / 5, s);
        StdDraw.show();
    }




    public String Selection() {
        String input = "";
        int i = 0;
        while(true) {
            if(StdDraw.hasNextKeyTyped()) {
                input += StdDraw.nextKeyTyped();
                i += 1;
                drawFrame(input);
            }
            if(i != 0 && input.charAt(i - 1) == 's') {
                break;
            }
            if(i == 1) {
                char c = input.charAt(0);
                if(c == 'n') {
                    StdDraw.pause(1000);
                    drawFrame("Enter seed and end with an 's'");
                } else if(c == 'l') {
                    StdDraw.pause(1000);
                    drawFrame("loading...");
                    StdDraw.pause(1000);
                    break;
                } else if(c == 'q') {
                    StdDraw.pause(1000);
                    drawFrame("exiting...");
                    StdDraw.pause(1000);
                    System.exit(0);
                } else {
                    throw new NoSuchElementException();
                }
            }

        }
        StdDraw.pause(1000);
        return input;
    }

}
