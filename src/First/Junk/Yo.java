/*package First;

import javafx.scene.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Yo {

    private JFrame screen;
    private JPanel p;

    public Yo(int x, int y, int xSize, int ySize, int howMany){
        screen = new JFrame();
        this.p = new JPanel();
        lots(x,y, xSize, ySize, howMany);
    }

    public void lots(int xLots, int yLots, int xSizeLots, int ySizeLots, int howManyLots){
        for(int i = 0; i < howManyLots + 1; i ++){
            new Tile(xLots + (i * xSizeLots ), yLots + (i * ySizeLots), xSizeLots, ySizeLots);
            new Tile(xLots + (i * xSizeLots * 2), yLots, xSizeLots, ySizeLots);
            new Tile(xLots + (i * xSizeLots), yLots - (i * ySizeLots), xSizeLots, ySizeLots);
            new Tile(xLots - (i * xSizeLots), yLots + (i * ySizeLots), xSizeLots, ySizeLots);
            new Tile(xLots - (i * xSizeLots * 2), yLots + (i * ySizeLots * 2), xSizeLots, ySizeLots);
            new Tile(xLots - (i * xSizeLots), yLots + (i * ySizeLots), xSizeLots, ySizeLots);
        }
    }


    public class Tile {

        public Tile (int xTile, int yTile, int xSizeTile, int ySizeTile) {

            int xpoly[] = {xTile, xTile - xSizeTile, xTile - xSizeTile, xTile, xTile + xSizeTile, xTile + xSizeTile,};
            int ypoly[] = {yTile - ySizeTile, yTile - ySizeTile / 2, yTile + ySizeTile / 2,
                    yTile + ySizeTile, yTile + ySizeTile / 2, yTile - ySizeTile / 2,};

            Polygon poly = new Polygon(xpoly, ypoly, xpoly.length);

            p.add(poly);


                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.BLUE);
                    g.drawPolygon(poly);
                }

                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(800, 600);
                }

            };
            MouseAdapter ma = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    super.mouseClicked(me);

                    if (poly.contains(me.getPoint())) {
                        System.out.println("Clicked polygon");
                    }

                }
            };

            p.addMouseListener(ma);//add listener to panel
            screen.add(p);
            screen.pack();
            screen.setVisible(true);

        }

    }





    public static void main(String[] args) {
        //new Yo(400, 300, 20, 20, 2);


    }
}

*/