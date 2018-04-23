package byog.Core;

import byog.stddraw.StdDraw;

import java.awt.*;

public class WinGame {
    public static int width;
    public static int height;
    public Font font;

    public WinGame(int w, int h) {
        this.width = w;
        this.height = h;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void drawFrame() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2, "You win!");
        StdDraw.show();
    }

}
