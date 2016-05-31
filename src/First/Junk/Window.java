package First.Junk;

import First.Board.*;
import First.Piece.Fighter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import static java.awt.font.TextAttribute.FONT;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Window {

    private JFrame frame;
    private JPanel panel;
    private Board engineBoard;
    private BufferedImage background;
    private JTextArea general;
    private JTextArea ruleCard;
    private JTextArea rules;
    private JButton go;
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    private int taskBarHeight;
    private Rectangle winSize;
    private int wideMiddle;
    private int tallMiddle;
    private int circle;
    private int mapWidth;
    private int mapRadius;
    final java.util.List<TileButton> boardTiles;
    public Icon rook;




    public Window() {
        frame = new JFrame("Carrion Window");
        boardTiles = new ArrayList<>();
        engineBoard = new Board();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        taskBarHeight = screenSize.height - winSize.height;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - taskBarHeight;
        panel = new JPanel();
        panel.setLayout(null);
        background = null;
        wideMiddle = (((int) (screenHeight * (2 / (Math.sqrt(3)))))/2);
        tallMiddle =  (screenHeight / 2);
        mapWidth =  ((int) (screenHeight * (2 / (Math.sqrt(3)))));
        circle = (mapWidth / 45);
        mapRadius = 21;//includes the center


        try {
            background = ImageIO.read(new File("C:/Users/Dan/IdeaProjects/Learning/art/background/javamap.jpg"));
        } catch (IOException e) {
            System.out.println("Can't Find Image");
        }
        Image dimg = background.getScaledInstance((mapWidth-(mapWidth%45)), (screenHeight), Image.SCALE_SMOOTH);

        try {
            background = ImageIO.read(new File("C:/Users/Dan/IdeaProjects/Learning/art/background/javamap.jpg"));
        } catch (IOException e) {
            System.out.println("Can't Find Image");
        }
        Image cornerIcon = background.getScaledInstance(60, 50, Image.SCALE_SMOOTH);

        frame.setIconImage(cornerIcon);

        JLabel map = new JLabel(new ImageIcon(dimg));
        map.setBounds(0, 0, (int) (screenHeight * (2 / (Math.sqrt(3)))), screenHeight);
        general = new JTextArea("Info all about them generals gggggggggggg gggggggggggggggggggggggg ggggggggggggggggggggg ggggggggggggg gggggggggggg gggggggggggggggggggggggggggggggg gggggggggggggggggggggggggg gggggggggggggggggg ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");
        //general.setLocation((screenWidth - ((screenWidth - mapWidth)) +((screenWidth - mapWidth)/9)),
                //((screenHeight/5)));
        general.setSize(((screenWidth - mapWidth)/3), (screenHeight/3));
        general.setEditable(false);
        general.setLineWrap(true);
        general.setWrapStyleWord(true);

        ruleCard = new JTextArea("A nice summary of the rules here");
        ruleCard.setLocation((screenWidth - ((screenWidth - mapWidth)) +((5 * (screenWidth - mapWidth))/9)),
                ((screenHeight/5)));
        ruleCard.setSize(((screenWidth - mapWidth)/3), (screenHeight/3));
        ruleCard.setEditable(false);
        ruleCard.setLineWrap(true);
        ruleCard.setWrapStyleWord(true);

        rules = new JTextArea();
        Reader reader = null;
        try {
            reader = new FileReader(new File("C:/Users/Dan/IdeaProjects/Learning/art/Text/Rulesv3.txt"));
            rules.read(reader, "The force is strong with this one");
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception exp) {
            }
        }
        rules.setLocation((screenWidth - ((screenWidth - mapWidth)) +((screenWidth - mapWidth)/9)),
                (3 * (screenHeight/5)));
        rules.setSize((7*((screenWidth - mapWidth)/9)), (screenHeight/3));
        rules.setEditable(false);
        rules.setLineWrap(true);
        rules.setWrapStyleWord(true);//may want to use a scrollpane

        go = new JButton("Next Phase?");
        go.setLocation((screenWidth - ((screenWidth - mapWidth)/2) -((screenWidth - mapWidth)/3)/2),
                ((screenHeight/9)/3));
        go.setSize(((screenWidth - mapWidth)/3), (screenHeight/9));

        //rook = new ImageIcon("C:/Users/Dan/IdeaProjects/Chess/art/Chess Pieces/Plain/WR.gif");
        rook = new ImageIcon("C:/Users/Dan/IdeaProjects/Learning/art/RedSupply.png");
        TileButton monster = new TileButton(0, 0, 0, wideMiddle,
                tallMiddle, circle);
        monster.addIcon(rook);
        TileButton someButton = new TileButton(0, 0, 0, wideMiddle + 30, tallMiddle, circle);
        someButton.addIcon(rook);
        //panel.add(someButton);
        //System.out.println(wideMiddle);
        //System.out.println(tallMiddle);
        //System.out.println((((int) (screenHeight * (2 / (Math.sqrt(3))))) / 2));
        //System.out.println( (screenHeight / 2));
        System.out.println(circle);
        System.out.println((mapWidth-(mapWidth%45)));
        System.out.println(screenHeight);

        populateBoard();

        JScrollPane generalPane = new JScrollPane(general);
        generalPane.setBounds(((screenWidth - ((screenWidth - mapWidth)) +((screenWidth - mapWidth)/9))),
                ((screenHeight/5)),((screenWidth - mapWidth)/3), (screenHeight/3));
        //generalPane.setHorizontalScrollBarPolicy(31);
        //generalPane.setVerticalScrollBarPolicy(22);
        //DefaultCaret caret = (DefaultCaret)general.getCaret();
        //caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        panel.add(generalPane);

        JScrollPane ruleCardPane = new JScrollPane(ruleCard);
        ruleCardPane.setBounds((screenWidth - ((screenWidth - mapWidth)) +((5 * (screenWidth - mapWidth))/9)),
                ((screenHeight/5)),((screenWidth - mapWidth)/3), (screenHeight/3));
        //ruleCardPane.setHorizontalScrollBarPolicy(31);
        //ruleCardPane.setVerticalScrollBarPolicy(22);
        panel.add(ruleCardPane);

        JScrollPane rulesPane = new JScrollPane(rules);
        rulesPane.setBounds((screenWidth - ((screenWidth - mapWidth)) +((screenWidth - mapWidth)/9)),
                (3 * (screenHeight/5)),(7*((screenWidth - mapWidth)/9)), (screenHeight/3));
        //rulesPane.setHorizontalScrollBarPolicy(31);
        //rulesPane.setVerticalScrollBarPolicy(22);
        panel.add(rulesPane);


        //panel.add(monster);
        //panel.add(general);
        //panel.add(ruleCard);
        //panel.add(rules);
        panel.add(go);
        panel.add(map);
        panel.setLocation(0,0);

        frame.add(panel);
        frame.setResizable(true);
        //frame.setSize(900, 800);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(screenSize);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }


    public void populateBoard(){
        TileButton middleButton = new TileButton(rook,0, 0, 0, wideMiddle, tallMiddle, circle);
        //middleButton.setText("20");
        //middleButton.setFont(new Font("Arial", Font.BOLD, 11));
        //middleButton.setForeground(Color.BLACK);
        //middleButton.setHorizontalTextPosition(JButton.CENTER);
        //middleButton.setVerticalTextPosition(JButton.CENTER);
        //middleButton.setMargin(new Insets(0, 0, 0, 0));
        //middleButton.addIcon(rook);
        //middleButton.supplyLine = true;
        panel.add(middleButton);
        panel.validate();
        panel.repaint();
        boardTiles.add(middleButton);
        for (int i = 1; i < mapRadius; i++) {
            TileButton cornerButton1 = new TileButton(i, -i, 0,
                    wideMiddle + (i * (circle / 2)) - ((-i) * (circle / 2)),
                    tallMiddle + ((i) * (screenHeight / 45)) + ((-i) * (screenHeight / 45)), circle);
            TileButton cornerButton2 = new TileButton(-i, i, 0,
                    wideMiddle + ((-i) * (circle / 2)) - ((i) * (circle / 2)),
                    tallMiddle + ((-i) * (screenHeight / 45)) + ((i) * (screenHeight / 45)), circle);
            TileButton cornerButton3 = new TileButton(i, 0, -i,
                    wideMiddle + (i * (circle / 2)) - ((0) * (circle / 2)),
                    tallMiddle + ((i) * (screenHeight / 45)) + ((0) * (screenHeight / 45)), circle);
            TileButton cornerButton4 = new TileButton(-i, 0, i,
                    wideMiddle + ((-i) * (circle / 2)) - ((0) * (circle / 2)),
                    tallMiddle + ((-i) * (screenHeight / 45)) + ((0) * (screenHeight / 45)), circle);
            TileButton cornerButton5 = new TileButton(0, i, -i,
                    wideMiddle + ((0) * (circle / 2)) - ((i) * (circle / 2)),
                    tallMiddle + ((0) * (screenHeight / 45)) + ((i) * (screenHeight / 45)), circle);
            TileButton cornerButton6 = new TileButton(0, -i, i,
                    wideMiddle + ((0) * (circle / 2)) - ((-i) * (circle / 2)),
                    tallMiddle + ((0) * (screenHeight / 45)) + ((-i) * (screenHeight / 45)), circle);
            panel.add(cornerButton1);
            panel.add(cornerButton2);
            panel.add(cornerButton3);
            panel.add(cornerButton4);
            panel.add(cornerButton5);
            panel.add(cornerButton6);
            boardTiles.add(cornerButton1);
            boardTiles.add(cornerButton2);
            boardTiles.add(cornerButton3);
            boardTiles.add(cornerButton4);
            boardTiles.add(cornerButton5);
            boardTiles.add(cornerButton6);
            int k;
            for (k = 1; k < i; k++) {
                TileButton fillButton1 = new TileButton(-i, k, (i - k),
                        wideMiddle + ((-i) * ((circle / 2))) - ((k) * ((circle / 2))),
                        tallMiddle + ((-i) * (screenHeight / 45)) + ((k) * (screenHeight / 45)), circle);
                TileButton fillButton2 = new TileButton(i, -k, -(i - k),
                        wideMiddle + ((i) * (circle / 2)) - ((-k) * (circle / 2)),
                        tallMiddle + ((i) * (screenHeight / 45)) + ((-k) * (screenHeight / 45)), circle);
                TileButton fillButton3 = new TileButton(k, -i, (i - k),
                        wideMiddle + ((k) * (circle / 2)) - ((-i) * (circle / 2)),
                        tallMiddle + ((k) * (screenHeight / 45)) + ((-i) * (screenHeight / 45)), circle);
                TileButton fillButton4 = new TileButton(-k, i, -(i - k),
                        wideMiddle + ((-k) * (circle / 2)) - ((i) * (circle / 2)),
                        tallMiddle + ((-k) * (screenHeight / 45)) + ((i) * (screenHeight / 45)), circle);
                TileButton fillButton5 = new TileButton(-k, -(i - k), i,
                        wideMiddle + ((-k) * (circle / 2)) - ((-(i - k)) * (circle / 2)),
                        tallMiddle + ((-k) * (screenHeight / 45)) + ((-(i - k)) * (screenHeight / 45)), circle);
                TileButton fillButton6 = new TileButton(k, (i - k), -i,
                        wideMiddle + ((k) * (circle / 2)) - ((i - k) * (circle / 2)),
                        tallMiddle + ((k) * (screenHeight / 45)) + ((i - k) * (screenHeight / 45)), circle);
                //fillButton1.addIcon(rook);
                panel.add(fillButton1);
                panel.add(fillButton2);
                panel.add(fillButton3);
                panel.add(fillButton4);
                panel.add(fillButton5);
                panel.add(fillButton6);
                boardTiles.add(fillButton1);
                boardTiles.add(fillButton2);
                boardTiles.add(fillButton3);
                boardTiles.add(fillButton4);
                boardTiles.add(fillButton5);
                boardTiles.add(fillButton6);
            }
        }
        //middleButton.addIcon(rook);
    }

    private class TileButton extends JButton {
        //private Icon rook = new ImageIcon("C:/Users/Dan/IdeaProjects/Chess/art/Chess Pieces/Plain/WR.gif");
        private Icon icon;
        private int qcoord;
        private int rcoord;
        private int scoord;
        private int diameter;
        private int xposition;
        private int yposition;
        private boolean supplyLine;

        public TileButton(int qcoord, int rcoord, int scoord, int xposition, int yposition, int diameter) {
            this(null, null, qcoord, rcoord, scoord, xposition, yposition, diameter);
        }
        public TileButton(Icon icon, int qcoord, int rcoord, int scoord, int xposition, int yposition, int diameter) {
            this(null, icon, qcoord, rcoord, scoord, xposition, yposition, diameter);
        }
        public TileButton(String text, Icon icon, int qcoord, int rcoord, int scoord, int xpositioncenter, int ypositioncenter, int diameter) {
            this.qcoord = qcoord;
            this.rcoord = rcoord;
            this.scoord = scoord;
            this.icon = icon;
            this.diameter = diameter;
            this.xposition = xpositioncenter - ((diameter)/2);
            this.yposition = ypositioncenter - ((diameter)/2);
            setModel(new DefaultButtonModel());
            init(text, icon);
            this.supplyLine = false;
            setBorderPainted(false);
            this.setSize((diameter),(diameter));
            this.setLocation(this.xposition, this.yposition);
            Color playerColor = new Color(190,18,12);
            this.setBackground(playerColor);
            //this.setBackground(Color.RED);//color of the circle
            this.setIcon(icon);
            this.setOpaque(true);
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            this.setFont(new Font("Arial", Font.BOLD, 11));
            this.setForeground(Color.BLACK);//color of the text
            this.setHorizontalTextPosition(JButton.CENTER);
            this.setVerticalTextPosition(JButton.CENTER);
            this.setMargin(new Insets(0, 0, 0, 0));
        /*if(icon==null) {
            return;
        }*/
            //setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            //setBackground(Color.BLUE);
            setFocusPainted(false);
            //setVerticalAlignment(SwingConstants.TOP);
            //setAlignmentY(Component.TOP_ALIGNMENT);
            initShape();
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ///System.out.println("qcoord = " + qcoord);
                    //System.out.println("rcoord = " + rcoord);
                    //System.out.println("scoord = " + scoord);
                    general.append("\nqcoord = " + qcoord + "\nrcoord = " + rcoord + "\nscoord = " + scoord + "\n");
                    general.setCaretPosition(general.getDocument().getLength());
                    if (getIcon()== null) {
                        addIcon(rook);
                        supplyLine = true;
                        setText(Integer.toString(qcoord));
                        setForeground(Color.BLACK);
                        System.out.println("no icon");
                        for (TileButton tile : boardTiles){
                            if (isNextTo(tile)){
                                if(tile.getIcon() == null){
                                    tile.setText(Integer.toString(tile.getQCoord()));
                                    tile.setForeground(Color.BLACK);
                                    tile.addIcon(rook);
                                    tile.supplyLine = true;
                                    panel.validate();
                                    panel.repaint();
                                }else{
                                    tile.addIcon(null);
                                    tile.supplyLine = false;
                                    tile.setText(Integer.toString(tile.getRCoord()));
                                    tile.setForeground(playerColor);
                                    panel.validate();
                                    panel.repaint();
                                }
                            }
                        }
                    } else {
                        addIcon(null);
                        supplyLine = false;
                        setText(Integer.toString(rcoord));
                        setForeground(playerColor);
                        System.out.println("icon");
                        for (TileButton tile : boardTiles){
                            if (isNextTo(tile)){
                                if(tile.getIcon() == null){
                                    tile.addIcon(rook);
                                    tile.supplyLine = true;
                                    tile.setText(Integer.toString(tile.getQCoord()));
                                    tile.setForeground(Color.BLACK);
                                    panel.validate();
                                    panel.repaint();
                                }else{
                                    tile.addIcon(null);
                                    tile.supplyLine = false;
                                    tile.setText(Integer.toString(tile.getRCoord()));
                                    tile.setForeground(playerColor);
                                    panel.validate();
                                    panel.repaint();
                                }
                            }
                        }
                    }

                    //general.setText("Don't touch me there");

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {


                }

                @Override
                public void mouseExited(MouseEvent e) {

                }

            });
        }
        protected Shape shape, base;
        protected void initShape() {
            if(!getBounds().equals(base)) {
                Dimension s = new Dimension((this.diameter), (this.diameter));
                base = getBounds();
                shape = new Ellipse2D.Float(0, 0, s.width-1, s.height-1);
            }
        }


        public Icon getIcon(){
            return this.icon;
        }
        public void addIcon(Icon icon){
            this.icon = icon;
        }
        public int getQCoord(){
            return this.qcoord;
        }
        public int getRCoord(){
            return this.rcoord;
        }
        public int getSCoord(){
            return this.scoord;
        }

        public boolean isNextTo(TileButton tile){
            if (((tile.getQCoord() == this.getQCoord()) &&
                    ((tile.getRCoord() + 1) == this.getRCoord()) &&
                    ((tile.getSCoord() - 1) == this.getSCoord())) ||
                    ((tile.getQCoord() == this.getQCoord()) &&
                    ((tile.getRCoord() - 1) == this.getRCoord()) &&
                    ((tile.getSCoord() + 1) == this.getSCoord())) ||
                    (((tile.getQCoord() + 1) == this.getQCoord()) &&
                    ((tile.getRCoord() - 1) == this.getRCoord()) &&
                    ((tile.getSCoord()) == this.getSCoord())) ||
                    (((tile.getQCoord() - 1) == this.getQCoord()) &&
                    ((tile.getRCoord() + 1) == this.getRCoord()) &&
                    ((tile.getSCoord()) == this.getSCoord())) ||
                    (((tile.getQCoord() - 1) == this.getQCoord()) &&
                    ((tile.getRCoord()) == this.getRCoord()) &&
                    ((tile.getSCoord() + 1) == this.getSCoord())) ||
                    (((tile.getQCoord() + 1) == this.getQCoord()) &&
                    ((tile.getRCoord()) == this.getRCoord()) &&
                    ((tile.getSCoord() - 1) == this.getSCoord()))){
                return true;
            }else {
                return false;
            }
        }

        public void talkToEngineFighters(){
            if (engineBoard.getTile(this.getQCoord(), this.getRCoord(), this.getSCoord()).getFighters() != null) {
                setText(Integer.toString(((Fighter) engineBoard.getTile(this.getQCoord(), this.getRCoord(), this.getSCoord()).getFighters().get(0)).getRegiments()));
                panel.validate();
                panel.repaint();
            }
        }


        @Override public Dimension getPreferredSize() {
            Icon icon = getIcon();
            Insets i = getInsets();
            int iw = Math.max(icon.getIconWidth(), icon.getIconHeight());
            return new Dimension(iw+i.right+i.left, iw+i.top+i.bottom);
        }
        @Override protected void paintBorder(Graphics g) {
            //if (supplyLine) {
                initShape();
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.setStroke(new BasicStroke(2.0f));
                if (engineBoard.getTile(this.getQCoord(), this.getRCoord(), this.getSCoord()).getSupplyLine() != null) {
                g2.draw(shape);
                }
                if (engineBoard.getTile(this.getQCoord(), this.getRCoord(), this.getSCoord()).getTown() != null) {
                    g2.fill(shape);
                }
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_OFF);
            //}
        }
        @Override public boolean contains(int x, int y) {
            initShape();
            return shape.contains(x, y);
            //or return super.contains(x, y) && ((image.getRGB(x, y) >> 24) & 0xff) > 0;
        }
    }

}
