/*package First;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Hola {

    private JFrame screen;

    public Hola(int x, int y, int xSize, int ySize, int howMany){
        screen = new JFrame();
        lots(x,y, xSize, ySize, howMany);
    }

    public void lots(int xLots, int yLots, int xSizeLots, int ySizeLots, int howManyLots){
        for(int i = 0; i < howManyLots + 1; i ++){
            tile(xLots + (i * xSizeLots * 2), yLots + (i * ySizeLots * 2), xSizeLots, ySizeLots);
        }
    }


    public void tile(int xTile, int yTile, int xSizeTile, int ySizeTile) {

        int xpoly[] = {xTile, xTile - xSizeTile, xTile - xSizeTile, xTile, xTile + xSizeTile, xTile + xSizeTile,};
        int ypoly[] = {yTile - ySizeTile, yTile - ySizeTile/2, yTile + ySizeTile/2,
                        yTile + ySizeTile, yTile + ySizeTile/2, yTile - ySizeTile/2,};

        Polygon poly = new Polygon(xpoly, ypoly, xpoly.length);

        JPanel p = new JPanel() {

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

    public static void main(String[] args) {
        new Hola(400, 300, 20, 20, 1);
    }
}*/
