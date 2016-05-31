package First.GUI;

import First.Board.*;
import First.EmbeddedResources;
import First.Piece.*;
import First.Piece.Fighters.Askia;
import First.Player.Alliance;
import First.Player.Player;
import First.Stage;
import com.sun.xml.internal.ws.api.ResourceLoader;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.awt.BorderLayout;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Interface {

    private JFrame frame;
    private JPanel panel;
    private JPanel boardPanel;
    private JPanel rightSidePanel;
    private JPanel buttonPanel;
    private Board engineBoard;
    private BufferedImage background;
    //private Image background;
    private JTextArea general;
    private JTextArea ruleCard;
    private JTextArea rules;
    private JScrollPane boardPane;
    private JButton go;
    private JButton zoomButton;
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    private int taskBarHeight;
    private Rectangle winSize;
    private int wideMiddle;
    private int tallMiddle;
    private int boardPaneHeight;
    private int boardPaneWidth;
    private int circle;
    private int mapHeight;
    private int mapWidth;
    private int zoom;
    private int mapRadius;
    private List<TileButton> boardTiles;
    private Icon battleimage;
    private Icon chiefofstaff;
    private boolean movementPhase;
    private Tile clickedTile;
    private Tile activeTile;
    private Tile helpingTile;
    private Fighter activeFighter;
    private Fighter enemyFighter;
    private Fighter helpingFighter;
    private Fighter assistedFighter;
    private Town assistedTown;
    private Capitol assistedCapitol;
    private JLabel map;
    private Alliance activeAlliance;
    private boolean askia;
    private EmbeddedResources resources;
    private MoveList moveList;




    public Interface() {
        frame = new JFrame("Carrion");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        frame.setJMenuBar(tableMenuBar);
        boardTiles = new ArrayList<>();
        engineBoard = new Board();
        moveList = new MoveList();
        moveList.addMove(engineBoard, false);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        taskBarHeight = screenSize.height - winSize.height;
        screenWidth = winSize.width;
        screenHeight = winSize.height - 15;
        panel = new JPanel(new BorderLayout());
        //panel.setLayout(null);
        rightSidePanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout());
        wideMiddle = (((int) (screenHeight * (2 / (Math.sqrt(3)))))/2);
        tallMiddle =  (screenHeight / 2);
        boardPaneHeight = (screenHeight - (screenHeight%90));
        boardPaneWidth =  (((int) (screenHeight * (2 / (Math.sqrt(3)))))- ((int) (screenHeight * (2 / (Math.sqrt(3)))))%90);
        zoom = 1;
        mapHeight = (boardPaneHeight);
        mapWidth = (boardPaneWidth);
        circle = (mapWidth / 45);
        mapRadius = 21;//includes the center
        movementPhase = false;
        askia = false;
        resources = new EmbeddedResources();

        /*background = null;
        try {
            background = ImageIO.read(new File("C:/Users/Dan/IdeaProjects/Learning/art/background/noTowns.jpg"));
        } catch (IOException e) {
            System.out.println("Can't Find Image");
        }*/
        background = null;
        try {
            background = ImageIO.read((resources.getNoTownsURL()));
        } catch (IOException e) {
            System.out.println("Can't Find Image");
        }
        System.out.println(resources.getNoTownsURL());

        //background = First.ResourceLoader.getImage("javamap.jpg");
        Image cornerIcon = background.getScaledInstance(60, 50, Image.SCALE_SMOOTH);
        frame.setIconImage(cornerIcon);

        Font textAreasFont = new Font("Sanserif", Font.BOLD, 18);


        general = new JTextArea("Carrion is the coolest");
        //general.setSize(((screenWidth - boardPaneWidth)/3), (boardPaneHeight /3));
        //general.setPreferredSize(new Dimension(((screenWidth - boardPaneWidth)/3), (boardPaneHeight /3)));
        general.setEditable(false);
        general.setLineWrap(true);
        general.setWrapStyleWord(true);
        general.setFont(textAreasFont);
        JScrollPane generalPane = new JScrollPane(general);
        //generalPane.setBounds(((screenWidth - ((screenWidth - boardPaneWidth)) +((screenWidth - boardPaneWidth)/9))),
                //((boardPaneHeight /5)),((screenWidth - boardPaneWidth)/3), (boardPaneHeight /3));
        generalPane.setPreferredSize(new Dimension(((screenWidth - boardPaneWidth)/3), (boardPaneHeight /3)));
        rightSidePanel.add(generalPane, BorderLayout.CENTER);

        ruleCard = new JTextArea("A nice summary of the rules here");
        //ruleCard.setSize(((screenWidth - boardPaneWidth)/3), (boardPaneHeight /3));
        //ruleCard.setPreferredSize(new Dimension(((screenWidth - boardPaneWidth)/3), (boardPaneHeight /3)));
        ruleCard.setEditable(false);
        ruleCard.setLineWrap(true);
        ruleCard.setWrapStyleWord(true);
        ruleCard.setFont(textAreasFont);
        JScrollPane ruleCardPane = new JScrollPane(ruleCard);
        //ruleCardPane.setBounds((screenWidth - ((screenWidth - boardPaneWidth)) +((5 * (screenWidth - boardPaneWidth))/9)),
                //((boardPaneHeight /5)),((screenWidth - boardPaneWidth)/3), (boardPaneHeight /3));
        ruleCardPane.setPreferredSize(new Dimension(((screenWidth - boardPaneWidth)/3), (boardPaneHeight /3)));
        rightSidePanel.add(ruleCardPane, BorderLayout.LINE_END);

        rules = new JTextArea();
        /*Reader reader = null;
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
        }*/

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(resources.getRulesv3URL().openStream()));
            rules.read(in, "Long Rules");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //rules.setSize((7*((screenWidth - boardPaneWidth)/9)), (boardPaneHeight /3));
        //rules.setPreferredSize(new Dimension((7*((screenWidth - boardPaneWidth)/9)), (boardPaneHeight /3)));
        rules.setEditable(false);
        rules.setLineWrap(true);
        rules.setWrapStyleWord(true);
        rules.setFont(textAreasFont);
        JScrollPane rulesPane = new JScrollPane(rules);
        //rulesPane.setBounds((screenWidth - ((screenWidth - boardPaneWidth)) +((screenWidth - boardPaneWidth)/9)),
                //(3 * (boardPaneHeight /5)),(7*((screenWidth - boardPaneWidth)/9)), (boardPaneHeight /3));
        rulesPane.setPreferredSize(new Dimension((7*((screenWidth - boardPaneWidth)/9)), (boardPaneHeight /3)));
        rightSidePanel.add(rulesPane, BorderLayout.PAGE_END);

        go = new JButton(engineBoard.getActivePlayer().getPlayerAlliance().toString() + " Player Expose Generals");
        go.setFont(textAreasFont);
        go.setLocation((screenWidth - ((screenWidth - boardPaneWidth)/2) -((screenWidth - boardPaneWidth)/3)/2),
                ((boardPaneHeight /9)/3));
        go.setSize(((screenWidth - boardPaneWidth)/3), (boardPaneHeight /9));
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player activePlayer = engineBoard.getActivePlayer();
                if (activePlayer.getTurnStage() == Stage.MOVECOS){
                    if (activePlayer.exposedAskia()){
                        activePlayer.exposeFighters();
                    }
                    activePlayer.progressTurnStage(engineBoard);
                    engineBoard.progressActivePlayer();
                    Player newActivePlayer = engineBoard.getActivePlayer();
                    int turnCode = newActivePlayer.progressTurnStage(engineBoard);
                    if (turnCode == 0){
                        allButtonsTalkToFighters();
                        go.setText(newActivePlayer.getPlayerAlliance().toString() +" Player Expose Generals");

                    }else if (turnCode == 1){
                        allButtonsTalkToFighters();
                        go.setText(newActivePlayer.getPlayerAlliance().toString() +" Player Allocate Regiments");
                    }
                    moveList.addMove(engineBoard.getDeepClone(engineBoard), true);
                    general.setText("Carrion is so cool!");
                    ruleCard.setText("Just the coolest!");


                }else if (activePlayer.getTurnStage() == Stage.EXPOSE){
                    activePlayer.progressTurnStage(engineBoard);
                    allButtonsTalkToFighters();
                    askia = false;
                    go.setText(activePlayer.getPlayerAlliance().toString() +" Player Allocate Regiments");
                    moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                }else if (activePlayer.getTurnStage() == Stage.ALLOCATE){
                    activePlayer.progressTurnStage(engineBoard);
                    activeFighter = null;
                    activeTile = null;
                    go.setText(activePlayer.getPlayerAlliance().toString() +" Player Move Generals");
                    moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                }else if (activePlayer.getTurnStage() == Stage.SETASSIST){
                    int turnCode = activePlayer.progressTurnStage(engineBoard);
                    if (turnCode == 0){
                        go.setText(activePlayer.getPlayerAlliance().toString() +" Player Move Chief of Staff");
                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                    }else if (turnCode == 1){
                        engineBoard.progressActivePlayer();
                        Player newActivePlayer = engineBoard.getActivePlayer();
                        int turnCode2 = newActivePlayer.progressTurnStage(engineBoard);
                        if (turnCode2 == 0){
                            allButtonsTalkToFighters();
                            go.setText(newActivePlayer.getPlayerAlliance().toString() +" Player Expose Generals");

                        }else if (turnCode2 == 1){
                            allButtonsTalkToFighters();
                            go.setText(newActivePlayer.getPlayerAlliance().toString() +" Player Allocate Regiments");
                        }
                        moveList.addMove(engineBoard.getDeepClone(engineBoard), true);
                        general.setText("Carrion is so cool!");
                        ruleCard.setText("Just the coolest!");


                    }

                }else if (activePlayer.getTurnStage() == Stage.MOVEFIGHTERS){
                    int turnCode = activePlayer.progressTurnStage(engineBoard);
                        if (turnCode == 0) {
                            general.append("\nFight");
                            go.setText(activePlayer.getPlayerAlliance().toString() +" Player Fight");
                            general.setCaretPosition(general.getDocument().getLength());
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), true);

                        }else if (turnCode == 1){
                            general.append("\nNot a legal position");
                            general.setCaretPosition(general.getDocument().getLength());
                        }else if (turnCode == 2){
                            go.setText(activePlayer.getPlayerAlliance().toString() +" Player Set Defense");
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), false);
                        }

                }else if (activePlayer.getTurnStage() == Stage.BATTLE) {
                    int turnCode = activePlayer.progressTurnStage(engineBoard);
                    if (turnCode == 0) {
                        go.setText(activePlayer.getPlayerAlliance().toString() +" Player Set Defense");
                        general.setCaretPosition(general.getDocument().getLength());
                    }else if (turnCode == 1){
                        general.append("\nNot Done Fighting");
                        general.setCaretPosition(general.getDocument().getLength());
                    }else if (turnCode == 2){
                        general.append("\nNot a legal position");
                        general.setCaretPosition(general.getDocument().getLength());
                    }else if (turnCode == 3){
                        go.setText("Avenge the old boar");
                    }
                    moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                }
                activeTile = null;
                activeFighter = null;
            }
        });
        buttonPanel.add(go);


        zoomButton = new JButton("Zoom In");
        zoomButton.setFont(textAreasFont);
        //zoomButton.setLocation((screenWidth - ((screenWidth - boardPaneWidth)) +((screenWidth - boardPaneWidth)/9)),
                //((boardPaneHeight /9)/3));
        //zoomButton.setSize(((screenWidth - boardPaneWidth)/7), (boardPaneHeight /9));
        zoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (zoom == 1){
                    zoom = 2;
                    System.out.println(zoom);
                    zoomMap();
                    boardPanel.revalidate();
                    boardPanel.repaint();
                    boardPane.getHorizontalScrollBar().setUnitIncrement(15);
                    boardPane.getVerticalScrollBar().setUnitIncrement(15);
                    panel.revalidate();
                    panel.repaint();
                    Rectangle bounds = boardPane.getViewport().getViewRect();
                    Dimension size = boardPane.getViewport().getViewSize();
                    int x = (size.width - bounds.width) / 2;
                    int y = (size.height - bounds.height) / 2;
                    boardPane.getViewport().setViewPosition(new Point(x, y));
                    zoomButton.setText("Zoom In");


                }else if (zoom == 2){
                    zoom = 3;
                    System.out.println(zoom);
                    zoomMap();
                    boardPanel.revalidate();
                    boardPanel.repaint();
                    boardPane.getHorizontalScrollBar().setUnitIncrement(15);
                    boardPane.getVerticalScrollBar().setUnitIncrement(15);
                    panel.revalidate();
                    panel.repaint();
                    Rectangle bounds = boardPane.getViewport().getViewRect();
                    Dimension size = boardPane.getViewport().getViewSize();
                    int x = (size.width - bounds.width) / 2;
                    int y = (size.height - bounds.height) / 2;
                    boardPane.getViewport().setViewPosition(new Point(x, y));
                    zoomButton.setText("Zoom Out");

                }else{
                    zoom = 1;
                    System.out.println(zoom);
                    zoomMap();
                    boardPanel.revalidate();
                    boardPanel.repaint();
                    panel.revalidate();
                    panel.repaint();
                    zoomButton.setText("Zoom In");
                }
            }
        });
        buttonPanel.add(zoomButton);
        rightSidePanel.add(buttonPanel, BorderLayout.PAGE_START);

        ImageIcon imageIcon = new ImageIcon(resources.getBattleURL()); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(circle, circle,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        battleimage = new ImageIcon(newimg);  // transform it back

        ImageIcon imageIcon1 = new ImageIcon(resources.getCrownURL()); // load the image to a imageIcon
        Image image1 = imageIcon1.getImage(); // transform it
        Image newimg1 = image1.getScaledInstance(circle, circle,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        chiefofstaff = new ImageIcon(newimg1);  // transform it back


        //battleimage = new ImageIcon("C:/Users/Dan/IdeaProjects/Learning/art/background/WR.gif");
        //chiefofstaff = new ImageIcon("C:/Users/Dan/IdeaProjects/Learning/art/background/WP.gif");
        System.out.println("circle = " + circle);
        System.out.println("boardPaneHeight = " + boardPaneHeight);
        System.out.println("boardPaneWidth = " + boardPaneWidth);

        panel.add(rightSidePanel, BorderLayout.LINE_END);
        panel.setLocation(0,0);
        boardPanel = new JPanel();
        boardPane = new JScrollPane(boardPanel);
        boardPane.getViewport().setLayout(null);
        zoomMap();
        //boardPanel.setPreferredSize(new Dimension(mapWidth, mapHeight));
        boardPanel.setSize(new Dimension(mapWidth, mapHeight));
        boardPanel.setLocation(0,0);


        //boardPane.setPreferredSize(new Dimension(boardPaneWidth, boardPaneHeight));
        //boardPane.setLayout(null);
        boardPane.setBounds(0, 0, mapWidth + boardPane.getVerticalScrollBar().getWidth(),
                mapHeight + boardPane.getHorizontalScrollBar().getHeight());
        panel.add(boardPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setResizable(true);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(screenWidth, screenHeight + 30);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
    }

    private void populateMenuBar(JMenuBar tableMenuBar){
        tableMenuBar.add(createFileMenu());
    }

    private JMenu createFileMenu(){
        final JMenu filesMenu = new JMenu("File");
        filesMenu.setMnemonic(KeyEvent.VK_F);

        final JMenuItem new2Game = new JMenuItem("New 2 Player Game", KeyEvent.VK_2);
        new2Game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                engineBoard = new Board(2);
                allButtonsTalkToFighters();
                go.setText("Red Player Expose Generals");
            }
        });
        filesMenu.add(new2Game);

        final JMenuItem new3Game = new JMenuItem("New 3 Player Game", KeyEvent.VK_3);
        new3Game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                engineBoard = new Board(3);
                allButtonsTalkToFighters();
                go.setText("Red Player Expose Generals");
            }
        });
        filesMenu.add(new3Game);

        final JMenuItem new4Game = new JMenuItem("New 4 Player Game", KeyEvent.VK_4);
        new4Game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                engineBoard = new Board(4);
                allButtonsTalkToFighters();
                go.setText("Yellow Player Expose Generals");

            }
        });
        filesMenu.add(new4Game);

        final JMenuItem new5Game = new JMenuItem("New 5 Player Game", KeyEvent.VK_5);
        new5Game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                engineBoard = new Board(5);
                allButtonsTalkToFighters();
                go.setText("Red Player Expose Generals");

            }
        });
        filesMenu.add(new5Game);

        final JMenuItem new6Game = new JMenuItem("New 6 Player Game", KeyEvent.VK_6);
        new6Game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                engineBoard = new Board(6);
                allButtonsTalkToFighters();
                go.setText("Red Player Expose Generals");

            }
        });
        filesMenu.add(new6Game);

        final JMenuItem new7Game = new JMenuItem("Neighbors 2 Player ", KeyEvent.VK_N);
        new7Game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                engineBoard = new Board(7);
                allButtonsTalkToFighters();
                go.setText("Red Player Expose Generals");

            }
        });
        filesMenu.add(new7Game);

        final JMenuItem new8Game = new JMenuItem("Angle 2 Player ", KeyEvent.VK_A);
        new8Game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                engineBoard = new Board(8);
                allButtonsTalkToFighters();
                go.setText("Red Player Expose Generals");

            }
        });
        filesMenu.add(new8Game);

        final JMenuItem undoMove = new JMenuItem("Undo Last Move", KeyEvent.VK_U);
        KeyStroke ctrlLeftKeyStroke = KeyStroke.getKeyStroke("control Z");
        undoMove.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        //undoMove.setAccelerator(ctrlLeftKeyStroke);
        undoMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                engineBoard = undo(engineBoard);
                allButtonsTalkToFighters();
                updateGoButton();
                activeTile = null;
                activeFighter = null;

            }
        });
        filesMenu.add(undoMove);

        final JMenuItem redoMove = new JMenuItem("Redo Last Move", KeyEvent.VK_R);
        //KeyStroke ctrlRightKeyStroke = KeyStroke.getKeyStroke("control R");
        redoMove.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, InputEvent.CTRL_MASK));
        redoMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                engineBoard = redo(engineBoard);
                allButtonsTalkToFighters();
                updateGoButton();
                activeTile = null;
                activeFighter = null;
            }
        });
        filesMenu.add(redoMove);

        return filesMenu;

    }

    public Board undo(Board engineBoard){
        return moveList.undo();

    }

    public Board redo(Board engineBoard){
        return moveList.redo();
    }

    public void updateGoButton(){
        go.setText(engineBoard.getActivePlayer().getPlayerAlliance().toString() +
                " Player " +  engineBoard.getActivePlayer().getTurnStage().toString());

    }


    public void zoomMap(){

        boardTiles.clear();
        Image dimg = background.getScaledInstance((zoom * mapWidth), ( zoom * mapHeight), Image.SCALE_SMOOTH);
        JLabel map = new JLabel(new ImageIcon(dimg));
        map.setBounds(0, 0, (zoom * mapWidth), (zoom * mapHeight));

        ImageIcon imageIcon = new ImageIcon(resources.getBattleURL()); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(zoom * circle, zoom * circle,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        battleimage = new ImageIcon(newimg);  // transform it back

        ImageIcon imageIcon1 = new ImageIcon(resources.getCrownURL()); // load the image to a imageIcon
        Image image1 = imageIcon1.getImage(); // transform it
        Image newimg1 = image1.getScaledInstance(zoom * circle, zoom * circle,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        chiefofstaff = new ImageIcon(newimg1);  // transform it back

        boardPanel.removeAll();
        boardPanel.setLayout(null);
        populateBoard();
        boardPanel.add(map);
        boardPanel.setSize(zoom * mapWidth, zoom * mapHeight);
        boardPanel.setPreferredSize(new Dimension(zoom * mapWidth, zoom * mapHeight));
        //boardPane.getHorizontalScrollBar().setValue((boardPane.getHorizontalScrollBar().getMaximum())/2);
        //System.out.println("zoomMap is doing something");
    }

    public void populateBoard(){
        TileButton middleButton = new TileButton(0, 0, 0, circle);
        boardPanel.add(middleButton);
        boardPanel.validate();
        boardPanel.repaint();
        boardTiles.add(middleButton);
        for (int i = 1; i < mapRadius; i++) {
            TileButton cornerButton1 = new TileButton(i, -i, 0, circle);
            TileButton cornerButton2 = new TileButton(-i, i, 0, circle);
            TileButton cornerButton3 = new TileButton(i, 0, -i, circle);
            TileButton cornerButton4 = new TileButton(-i, 0, i, circle);
            TileButton cornerButton5 = new TileButton(0, i, -i, circle);
            TileButton cornerButton6 = new TileButton(0, -i, i, circle);
            boardPanel.add(cornerButton1);
            boardPanel.add(cornerButton2);
            boardPanel.add(cornerButton3);
            boardPanel.add(cornerButton4);
            boardPanel.add(cornerButton5);
            boardPanel.add(cornerButton6);
            boardTiles.add(cornerButton1);
            boardTiles.add(cornerButton2);
            boardTiles.add(cornerButton3);
            boardTiles.add(cornerButton4);
            boardTiles.add(cornerButton5);
            boardTiles.add(cornerButton6);
            int k;
            for (k = 1; k < i; k++) {
                TileButton fillButton1 = new TileButton(-i, k, (i - k), circle);
                TileButton fillButton2 = new TileButton(i, -k, -(i - k), circle);
                TileButton fillButton3 = new TileButton(k, -i, (i - k), circle);
                TileButton fillButton4 = new TileButton(-k, i, -(i - k), circle);
                TileButton fillButton5 = new TileButton(-k, -(i - k), i, circle);
                TileButton fillButton6 = new TileButton(k, (i - k), -i, circle);
                boardPanel.add(fillButton1);
                boardPanel.add(fillButton2);
                boardPanel.add(fillButton3);
                boardPanel.add(fillButton4);
                boardPanel.add(fillButton5);
                boardPanel.add(fillButton6);
                boardTiles.add(fillButton1);
                boardTiles.add(fillButton2);
                boardTiles.add(fillButton3);
                boardTiles.add(fillButton4);
                boardTiles.add(fillButton5);
                boardTiles.add(fillButton6);
            }
        }
    }

    public void allButtonsTalkToFighters(){
        for (TileButton button : boardTiles){
            button.talkToEngineFighters();
        }
    }

    private class TileButton extends JButton {
        private Icon icon;
        private int qcoord;
        private int rcoord;
        private int scoord;
        private int diameter;
        private int xPositionCenter;
        private int yPositionCenter;
        private boolean supplyLine;

        public TileButton(int qcoord, int rcoord, int scoord, int diameter) {
            this(null, null, qcoord, rcoord, scoord, diameter);
        }
        public TileButton(Icon icon, int qcoord, int rcoord, int scoord, int diameter) {
            this(null, icon, qcoord, rcoord, scoord, diameter);
        }

        public TileButton(String text, Icon icon, int qcoord, int rcoord, int scoord, int diameter) {
            this.qcoord = qcoord;
            this.rcoord = rcoord;
            this.scoord = scoord;
            this.icon = icon;
            this.diameter = zoom * diameter;
            this.xPositionCenter = (((zoom * mapWidth /2) - ((this.diameter)/2)) + ((this.qcoord) * (this.diameter/2)) - ((this.rcoord) * (this.diameter/2)));
            this.yPositionCenter = (((zoom * mapHeight /2) - ((this.diameter)/2)) + ((this.qcoord)*((zoom * mapHeight)/45)) + ((this.rcoord)*((zoom * mapHeight)/45)));
            setModel(new DefaultButtonModel());
            init(text, icon);
            this.supplyLine = false;
            setBorderPainted(false);
            this.setSize((zoom * diameter),(zoom * diameter));
            this.setLocation(this.xPositionCenter, this.yPositionCenter);
            this.setIcon(icon);
            this.setOpaque(true);
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            this.setFont(new Font("Arial", Font.BOLD, ((zoom) * 11)));
            this.setHorizontalTextPosition(JButton.CENTER);
            this.setVerticalTextPosition(JButton.CENTER);
            this.setMargin(new Insets(0, 0, 0, 0));
            setFocusPainted(false);
            initShape();
            talkToEngineFighters();
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("qcoord = " + qcoord);
                    System.out.println("rcoord = " + rcoord);
                    System.out.println("scoord = " + scoord);
                    Player activePlayer = engineBoard.getActivePlayer();
                    clickedTile =  engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());

                    if (activePlayer.getTurnStage() == Stage.ALLOCATE) {
                        if (isLeftMouseButton(e)) {
                            if ((!clickedTile.getFighters().isEmpty()) &&
                                    ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance() == activePlayer.getPlayerAlliance()  &&
                                    (((Fighter) clickedTile.getFighters().get(0)).getRegiments() <= ((Fighter) clickedTile.getFighters().get(0)).getMaxRegiments()) &&
                                    activePlayer.isFighterConnected(((Fighter) clickedTile.getFighters().get(0)))) {
                                ((Fighter) clickedTile.getFighters().get(0)).addRegiments(1);
                                activePlayer.takeRegiment(((Fighter) clickedTile.getFighters().get(0)));
                                talkToEngineFighters();
                                general.append("\n" + clickedTile.getFighters().get(0).toString() + " has " + activePlayer.getRegimentsLeft(((Fighter) clickedTile.getFighters().get(0))) + " regiments left to get.");
                                general.setCaretPosition(general.getDocument().getLength());
                                ruleCard.setText(((Fighter) clickedTile.getFighters().get(0)).toString() + ((Fighter) clickedTile.getFighters().get(0)).fightsAndKills());
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);
                            }
                        } else if (isRightMouseButton(e)) {
                            if ((!clickedTile.getFighters().isEmpty()) &&
                                    ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance() == activePlayer.getPlayerAlliance() &&
                                    (((Fighter) clickedTile.getFighters().get(0)).getRegiments() >= 2) &&
                                    activePlayer.isFighterConnectedDrop(((Fighter) clickedTile.getFighters().get(0)))) {
                                ((Fighter) clickedTile.getFighters().get(0)).dropRegiment();
                                talkToEngineFighters();
                                general.append("\n" + clickedTile.getFighters().get(0).toString() + " has " + activePlayer.getRegimentsLeft(((Fighter) clickedTile.getFighters().get(0))) + " regiments left to get.");
                                general.setCaretPosition(general.getDocument().getLength());
                                ruleCard.setText(((Fighter) clickedTile.getFighters().get(0)).toString() + ((Fighter) clickedTile.getFighters().get(0)).fightsAndKills());
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                            }
                        }

                    }else if (activePlayer.getTurnStage() == Stage.EXPOSE){
                        if ((isLeftMouseButton(e))&& (!clickedTile.getFighters().isEmpty())){
                            if (((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance() != activePlayer.getPlayerAlliance()){
                                if (((Fighter) clickedTile.getFighters().get(0)).getIsExposed()){
                                    general.append("\n" + clickedTile.getFighters().get(0).toString());
                                    general.setCaretPosition(general.getDocument().getLength());
                                    moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                                }else if (!((Fighter) clickedTile.getFighters().get(0)).getIsExposed()){
                                    general.append("\nUnknown General");
                                    general.setCaretPosition(general.getDocument().getLength());
                                    moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                                }
                            }
                        }else if ((isRightMouseButton(e)) && (!clickedTile.getFighters().isEmpty()) &&
                                (((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance() == activePlayer.getPlayerAlliance())){

                            if (clickedTile.getFighters().get(0).getClass() == Askia.class){
                                ((Fighter) clickedTile.getFighters().get(0)).exposeFighter();
                                askia = true;
                                general.append("\n" + clickedTile.getFighters().get(0).toString() + " exposed\nclick on player capitol to expose generals");
                                general.setCaretPosition(general.getDocument().getLength());
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                                if (!((Fighter) clickedTile.getFighters().get(0)).hasChiefOfStaff()){
                                    activePlayer.exposeFighters();
                                    moveList.addMove(engineBoard.getDeepClone(engineBoard), false);
                                }
                                talkToEngineFighters();

                            }else {
                                ((Fighter) clickedTile.getFighters().get(0)).exposeFighter();
                                general.append("\n" + clickedTile.getFighters().get(0).toString() + " exposed");
                                general.setCaretPosition(general.getDocument().getLength());
                                talkToEngineFighters();
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                            }
                        }else if (clickedTile.getCapitol() != null &&
                                clickedTile.getCapitol().getPieceAlliance() != activePlayer.getPlayerAlliance() &&
                                askia){
                            clickedTile.getCapitol().getPiecePlayer().exposeFighters();
                            allButtonsTalkToFighters();
                            askia = false;
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), true);

                        }

                    }else if (activePlayer.getTurnStage() == Stage.MOVEFIGHTERS) {

                        if ((!clickedTile.getFighters().isEmpty()) &&
                                ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance() ==  activePlayer.getPlayerAlliance() &&
                                (!clickedTile.isTileBattlefield())){

                            activeTile = clickedTile;
                            activeFighter = ((Fighter) activeTile.getFighters().get(0));

                            if (isLeftMouseButton(e)) {

                                System.out.println("new active tile left click \nmps = " + activeFighter.getMovementPoints() + "\nregs = " + activeFighter.getRegiments());
                                general.append("\nmps = " + activeFighter.getMovementPoints() + "\nregs = " + activeFighter.getRegiments());

                            } else if (isRightMouseButton(e) && clickedTile.getTown() == null &&
                                    (activeFighter.getRegiments() >=2)) {

                                if (clickedTile.getSupplyLine() == null &&
                                        (!activeFighter.getInteractedWithLines() &&
                                        (activeFighter.getMovementPoints() >= 1))) {

                                    engineBoard.setPiece(qcoord, rcoord, scoord, (activeFighter.getPieceAlliance()), Piece.PieceType.SUPPLYLINE);
                                    activeFighter.releaseRegiments(1);
                                    activeFighter.trueDroppedLine(true);
                                    talkToEngineFighters();
                                    getTileButton(activeTile.getTileQCoord(),activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                    setForeground(clickedTile.getSupplyLine().getPieceColor());
                                    moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                }else if (clickedTile.getSupplyLine() == null  &&
                                        activeFighter.getInteractedWithLines()){
                                    engineBoard.setPiece(qcoord, rcoord, scoord, (activeFighter.getPieceAlliance()), Piece.PieceType.SUPPLYLINE);
                                    activeFighter.releaseRegiments(1);
                                    activeFighter.trueDroppedLine(false);
                                    talkToEngineFighters();
                                    getTileButton(activeTile.getTileQCoord(),activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                    setForeground(clickedTile.getSupplyLine().getPieceColor());
                                    moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                                }
                            }else if ((isRightMouseButton(e)) && (clickedTile.getTown() != null) &&
                                    (activeFighter.getRegiments()>=2) && (!activeFighter.getHasDroppedTown()) &&
                                    clickedTile.getTown().getPieceAlliance() == Alliance.UNOCCUPIED &&
                                    activeFighter.shermanCheck()){

                                activeTile = clickedTile;
                                activeFighter = ((Fighter) activeTile.getFighters().get(0));
                                Town oldTown  = clickedTile.getTown();
                                engineBoard.removePiece(clickedTile.getTown());
                                engineBoard.setPiece(qcoord, rcoord, scoord, (activeFighter.getPieceAlliance()), Piece.PieceType.TOWN);
                                clickedTile.getTown().transferMutiny(oldTown);
                                activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                activeFighter.dropTown();
                                activeFighter.setAfterBattleMovementPoints();
                                activeFighter.genghisMethod();
                                activeFighter.sargonMethod();
                                getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                talkToEngineFighters();
                                setForeground(clickedTile.getTown().getPieceColor());
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                            }
                        }else if (clickedTile.getFighters().isEmpty() &&
                                (clickedTile.getTown() == null)  &&
                                (clickedTile.getCapitol() == null) &&
                                activeFighter.canNapoleonMove(engineBoard, clickedTile)) {

                            if ((!activeTile.getFighters().isEmpty()) &&
                                    (isNextTo(getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord())))){

                                if (isLeftMouseButton(e) &&(activeFighter.getMovementPoints() >= 1)){

                                    if (((clickedTile.getSupplyLine() == null))) {

                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(false, false);
                                        clickedTile.addFighter(activeFighter);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                    } else if((clickedTile.getSupplyLine() != null) &&
                                            (clickedTile.getSupplyLine().getPieceAlliance() == activePlayer.getPlayerAlliance())) {

                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(false, false);
                                        clickedTile.addFighter(activeFighter);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                    } else if((clickedTile.getSupplyLine() != null) &&
                                            (clickedTile.getSupplyLine().getPieceAlliance() != activePlayer.getPlayerAlliance())  &&
                                            ((activeFighter.getInteractedWithLines()) ||
                                            ((activeFighter.getMovementPoints() >= 2) && !activeFighter.getInteractedWithLines()) ||
                                            activeFighter.shermanCut())) {

                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(true, false);
                                        clickedTile.addFighter(activeFighter);
                                        engineBoard.removePiece(clickedTile.getSupplyLine());
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                                    }
                                }else if (activeFighter.sunTzuMethod() &&( (clickedTile.getSupplyLine() == null) ||
                                        ((clickedTile.getSupplyLine() != null) && (clickedTile.getSupplyLine().getPieceAlliance() == activePlayer.getPlayerAlliance()))) ){
                                    activeFighter.haveGhosted();
                                    Fighter ghost = engineBoard.setGhost(clickedTile.getTileQCoord(),clickedTile.getTileRCoord(), clickedTile.getTileSCoord(), activeFighter.getPieceAlliance());
                                    ghost.exposeFighter();
                                    ghost.addRegiments(activeFighter.getRegiments() - 1);
                                    activeFighter.setGhost(ghost);
                                    talkToEngineFighters();
                                    moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                } else if ((isRightMouseButton(e))  && (activeFighter.getRegiments() >= 2) &&
                                        (activeFighter.getMovementPoints() >= 1)) {

                                    if ((clickedTile.getSupplyLine() == null) &&
                                            (activeFighter.getInteractedWithLines() ||
                                            activeFighter.qinDrop())) {

                                        engineBoard.setPiece(qcoord, rcoord, scoord, (activeFighter.getPieceAlliance()), Piece.PieceType.SUPPLYLINE);
                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(false, true);
                                        clickedTile.addFighter(activeFighter);
                                        activeFighter.releaseRegiments(1);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        setForeground(clickedTile.getSupplyLine().getPieceColor());
                                        boardPanel.validate();
                                        boardPanel.repaint();
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                    }else if ((clickedTile.getSupplyLine() == null) &&
                                            activeFighter.getMovementPoints() >= 2 &&
                                            !activeFighter.getInteractedWithLines()) {

                                        engineBoard.setPiece(qcoord, rcoord, scoord, (activeFighter.getPieceAlliance()), Piece.PieceType.SUPPLYLINE);
                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(false, true);
                                        clickedTile.addFighter(activeFighter);
                                        activeFighter.releaseRegiments(1);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        setForeground(clickedTile.getSupplyLine().getPieceColor());
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                    }else if ((clickedTile.getSupplyLine() != null) &&
                                            (clickedTile.getSupplyLine().getPieceAlliance() == activePlayer.getPlayerAlliance())) {

                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(false, false);
                                        clickedTile.addFighter(activeFighter);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        setForeground(clickedTile.getSupplyLine().getPieceColor());
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                    }else if ((clickedTile.getSupplyLine() != null) &&
                                            (clickedTile.getSupplyLine().getPieceAlliance() != activePlayer.getPlayerAlliance()) &&
                                            activeFighter.getMovementPoints() >= 2 &&
                                            !activeFighter.getInteractedWithLines()){

                                        engineBoard.removePiece(clickedTile.getSupplyLine());
                                        engineBoard.setPiece(qcoord, rcoord, scoord, (activeFighter.getPieceAlliance()), Piece.PieceType.SUPPLYLINE);
                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(true, true);
                                        activeFighter.releaseRegiments(1);
                                        clickedTile.addFighter(activeFighter);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        setForeground(clickedTile.getSupplyLine().getPieceColor());
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                    }else if ((clickedTile.getSupplyLine() != null) &&
                                            (clickedTile.getSupplyLine().getPieceAlliance() != activePlayer.getPlayerAlliance()) &&
                                            activeFighter.getInteractedWithLines()){

                                        engineBoard.removePiece(clickedTile.getSupplyLine());
                                        engineBoard.setPiece(qcoord, rcoord, scoord, (activeFighter.getPieceAlliance()), Piece.PieceType.SUPPLYLINE);
                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(true, true);
                                        activeFighter.releaseRegiments(1);
                                        clickedTile.addFighter(activeFighter);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        setForeground(clickedTile.getSupplyLine().getPieceColor());
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                    }
                                }
                                activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                            }

                        }else if ((!activeTile.getFighters().isEmpty()) && (clickedTile.getTown() != null) &&
                                (clickedTile.getTown().getPieceAlliance() == Alliance.UNOCCUPIED) &&
                                (isNextTo(getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()))) &&
                                activeFighter.getMovementPoints() >=1 && clickedTile.getFighters().isEmpty() &&
                                activeFighter.canNapoleonMove(engineBoard, clickedTile)){

                            activeTile.getFighters().remove(activeFighter);
                            activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                            activeFighter.attackUnoccupiedTown();
                            clickedTile.getTown().removeMutiny();
                            clickedTile.getTown().setMutiny(Alliance.UNOCCUPIED, activeFighter);
                            clickedTile.addFighter(activeFighter);
                            getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                            talkToEngineFighters();
                            setForeground(clickedTile.getTown().getPieceColor());
                            activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                        }else if ((!activeTile.getFighters().isEmpty()) && (clickedTile.getTown() != null) &&
                                (((clickedTile.getTown().getPieceAlliance() != activePlayer.getPlayerAlliance()))) &&
                                (isNextTo(getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()))) &&
                                (activeFighter.getMovementPoints() >= 1) && clickedTile.getFighters().isEmpty() &&
                                activeFighter.canAshokaAttack(null) &&
                                activeFighter.canNapoleonMove(engineBoard, clickedTile)){

                            activeFighter.attack(activeTile);
                            activeTile.getFighters().remove(activeFighter);
                            activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                            clickedTile.addFighter(activeFighter);
                            getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                            talkToEngineFighters();
                            activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                        }else if ((!activeTile.getFighters().isEmpty()) && (clickedTile.getFighters().size() == 1) &&
                                ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance() !=  activePlayer.getPlayerAlliance() &&
                                (isNextTo(getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()))) &&
                                (activeFighter.getMovementPoints() >= 1) && activeFighter.getPieceAlliance() == activePlayer.getPlayerAlliance() &&
                                (clickedTile.getSupplyLine() == null) &&
                                activeFighter.canAshokaAttack((Fighter) clickedTile.getFighters().get(0)) &&
                                activeFighter.canNapoleonMove(engineBoard, clickedTile)){

                            if (((Fighter) clickedTile.getFighters().get(0)).isGhost()){
                                engineBoard.removePiece((Fighter)clickedTile.getFighters().get(0));
                                general.append("\nGhost!");
                                activeTile.getFighters().remove(activeFighter);
                                activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                activeFighter.lowerMovementPoints(false, false);
                                clickedTile.addFighter(activeFighter);
                                getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                talkToEngineFighters();
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                            }else if (((Fighter) clickedTile.getFighters().get(0)).hasGhost()){
                                activeFighter.attack(activeTile);
                                activeTile.getFighters().remove(activeFighter);
                                activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                ((Fighter) clickedTile.getFighters().get(0)).fightIsFighting();
                                engineBoard.removePiece(((Fighter) clickedTile.getFighters().get(0)).getGhost());
                                clickedTile.addFighter(activeFighter);
                                clickedTile.removeSupplyLine();
                                getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                allButtonsTalkToFighters();
                                activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                            }else {
                                activeFighter.attack(activeTile);
                                activeTile.getFighters().remove(activeFighter);
                                activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                ((Fighter) clickedTile.getFighters().get(0)).fightIsFighting();
                                clickedTile.addFighter(activeFighter);
                                getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                talkToEngineFighters();
                                activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                            }

                        }else if ((!activeTile.getFighters().isEmpty()) && (clickedTile.getFighters().size() == 1) &&
                                ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance() !=  activePlayer.getPlayerAlliance() &&
                                (isNextTo(getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()))) && activeFighter.getPieceAlliance() == activePlayer.getPlayerAlliance() &&
                                (clickedTile.getSupplyLine() != null) && (((activeFighter.getMovementPoints() >= 1) && ((activeFighter.getInteractedWithLines()) || activeFighter.shermanCut())) ||
                                ((activeFighter.getMovementPoints() >= 2) && (!activeFighter.getInteractedWithLines()))) &&
                                activeFighter.canAshokaAttack((Fighter) clickedTile.getFighters().get(0)) &&
                                activeFighter.canNapoleonMove(engineBoard, clickedTile)){

                            if (((Fighter) clickedTile.getFighters().get(0)).isGhost()){
                                engineBoard.removePiece((Fighter)clickedTile.getFighters().get(0));
                                general.append("\nGhost!");
                                activeTile.getFighters().remove(activeFighter);
                                activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                activeFighter.lowerMovementPoints(true, false);
                                clickedTile.addFighter(activeFighter);
                                engineBoard.removePiece(clickedTile.getSupplyLine());
                                getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                talkToEngineFighters();
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                            }else if (((Fighter) clickedTile.getFighters().get(0)).hasGhost()){
                                activeFighter.attack(activeTile);
                                activeTile.getFighters().remove(activeFighter);
                                activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                ((Fighter) clickedTile.getFighters().get(0)).fightIsFighting();
                                engineBoard.removePiece(((Fighter) clickedTile.getFighters().get(0)).getGhost());
                                clickedTile.addFighter(activeFighter);
                                clickedTile.removeSupplyLine();
                                getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                allButtonsTalkToFighters();
                                activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                            }else {
                                activeFighter.attack(activeTile);
                                activeTile.getFighters().remove(activeFighter);
                                activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                ((Fighter) clickedTile.getFighters().get(0)).fightIsFighting();
                                clickedTile.addFighter(activeFighter);
                                clickedTile.removeSupplyLine();
                                getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                talkToEngineFighters();
                                activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                            }

                        }else if ((!activeTile.getFighters().isEmpty()) && (clickedTile.getCapitol() != null) &&
                                (activeFighter.getPieceAlliance() != clickedTile.getCapitol().getPieceAlliance()) &&
                                (activeFighter.getMovementPoints() >= 1) &&(isNextTo(getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()))) &&
                                (!clickedTile.isTileBattlefield()) && (activeFighter.canHannibalAttack())&&
                                activeFighter.canAshokaAttack(null) &&
                                activeFighter.canNapoleonMove(engineBoard, clickedTile)){

                            activeFighter.attack(activeTile);
                            activeTile.getFighters().remove(activeFighter);
                            activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                            clickedTile.addFighter(activeFighter);
                            getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                            talkToEngineFighters();
                            activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                        }else if ((!activeTile.getFighters().isEmpty()) && (isNextTo(getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()))) &&
                                clickedTile.isTileBattlefield() && activeFighter.getMovementPoints() >= 1 &&
                                activeFighter.canNapoleonMove(engineBoard, clickedTile)){

                            assistedFighter = clickedTile.getBattlefieldAlly(activeFighter.getPieceAlliance());
                            activeFighter.helpFighter(assistedFighter);
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), false);

                            if (activeFighter.canHelpFighter(assistedFighter)){
                                activeFighter.zeroMovementPoints();
                                general.append("\n" + activeFighter.toString() + " helping " + assistedFighter.toString());
                                general.setCaretPosition(general.getDocument().getLength());
                            }else{
                                general.append("\n" + activeFighter.toString() + "wont or cant help " + assistedFighter.toString());
                                general.setCaretPosition(general.getDocument().getLength());
                            }
                        }
                    }else if (activePlayer.getTurnStage() == Stage.BATTLE) {

                        if ((!clickedTile.getFighters().isEmpty()) && (clickedTile.getTown() != null) &&
                                (((clickedTile.getTown().getPieceAlliance()) != ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance()) ||
                                        (clickedTile.getTown().getPieceAlliance() != (Alliance.UNOCCUPIED))) &&
                                !((Fighter) clickedTile.getFighters().get(0)).getHasFought()) {

                            activeFighter = (Fighter) clickedTile.getFighters().get(0);
                            activeFighter.fightHasFought();
                            activeFighter.exposeAttackFighter();
                            activeFighter.getBattleField(clickedTile);
                            clickedTile.getTown().cleanWhoIsHelpingHim();
                            clickedTile.getTown().exposeHelp();
                            clickedTile.getTown().removeMutiny();
                            int activeFighterFightBonus = activeFighter.calculateFightBonus(clickedTile.getTown()) + activeFighter.homeAwayBonus(clickedTile.getTown(), engineBoard);
                            int activeFighterCasualties = activeFighter.calculateCasualties(clickedTile.getTown());
                            int townFightBonus = clickedTile.getTown().getFightBonus(activeFighter) + clickedTile.getTown().homeAwayBonus(activeFighter, engineBoard);
                            int townCasualties = clickedTile.getTown().getCasualties(activeFighter);
                            Random random = new Random();
                            int attackingRoll = random.nextInt(20) + 1 + activeFighterFightBonus;
                            int defendingRoll = random.nextInt(20) + 1 + townFightBonus;

                            general.append("\nThe attacking general " + activeFighter.toString() + " rolled a " + attackingRoll);
                            general.append("\nThe defending Town rolled a " + defendingRoll);


                            if (clickedTile.getTown().getWhoIsHelpingHim().isEmpty()){
                                System.out.println("who is helping him is empty");

                                if (defendingRoll >= attackingRoll) {
                                    general.append("\nThe defending Town won");


                                    if ((activeFighter.getRegiments() >= townCasualties + 1) && (!activeFighter.isFighterTrapped(engineBoard))){
                                        activeFighter.releaseRegiments(townCasualties);
                                        activeFighter.setAfterBattleMovementPoints();

                                    }else if ((activeFighter.getRegiments() == 1) || (activeFighter.isFighterTrapped(engineBoard))){
                                        engineBoard.removePiece(activeFighter);
                                        general.append("\nThe attacking general " + activeFighter.toString() + " is dead");
                                        if (activeFighter.isLastFighter(engineBoard,activeFighter.getPieceAlliance())){
                                            engineBoard.removePlayer(activeFighter.getPieceAlliance());
                                            general.append("\nThe attacking Player is dead");
                                        }
                                    }
                                    talkToEngineFighters();


                                } else {
                                    general.append("\nThe attacking general " + activeFighter.toString() + " won");


                                    clickedTile.getTown().setMutiny(clickedTile.getTown().getPieceAlliance(), activeFighter);
                                    engineBoard.removePiece(clickedTile.getTown());
                                    engineBoard.setPiece(qcoord, rcoord, scoord, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
                                    activeFighter.setAfterBattleMovementPoints();
                                    getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                    talkToEngineFighters();
                                    setForeground(clickedTile.getTown().getPieceColor());
                                    activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                                }
                            } else if (!clickedTile.getTown().getWhoIsHelpingHim().isEmpty()){
                                System.out.println("who is helping him is not empty");
                                Fighter assistingGeneral = clickedTile.getTown().getWhoIsHelpingHim().get(0);
                                assistingGeneral.getBattleField(clickedTile);


                                if (defendingRoll >= attackingRoll) {
                                    general.append("\nThe defending Town won");


                                    if ((activeFighter.getRegiments() >= townCasualties + 1) && (!activeFighter.isFighterTrapped(engineBoard))){

                                        activeFighter.releaseRegiments(townCasualties);
                                        activeFighter.setAfterBattleMovementPoints();

                                    }else if ((activeFighter.getRegiments() <= townCasualties) || (activeFighter.isFighterTrapped(engineBoard))){
                                        engineBoard.removePiece(activeFighter);
                                        general.append("\nThe attacking general " + activeFighter.toString() + " is dead");
                                        if (activeFighter.isLastFighter(engineBoard,activeFighter.getPieceAlliance())){
                                            engineBoard.removePlayer(activeFighter.getPieceAlliance());
                                            general.append("\nThe attacking Player is dead");
                                        }
                                    }
                                    talkToEngineFighters();


                                } else {
                                    general.append("\nThe attacking general " + activeFighter.toString() + " won");


                                    if(((activeFighterCasualties + 1) <= assistingGeneral.getRegiments()) && (!activeFighter.isFighterTrapped(engineBoard))){

                                        assistingGeneral.releaseRegiments(activeFighterCasualties);
                                        getTileButton(assistingGeneral.getPieceQCoord(), assistingGeneral.getPieceRCoord(), assistingGeneral.getPieceSCoord()).talkToEngineFighters();
                                        activeFighter.setAfterBattleMovementPoints();

                                    }else if (((activeFighterCasualties == assistingGeneral.getRegiments()) && (!activeFighter.isFighterTrapped(engineBoard))) ||
                                            ((activeFighterCasualties <= assistingGeneral.getRegiments() + 1) && (activeFighter.isFighterTrapped(engineBoard)))){

                                        activeFighter.setAfterBattleMovementPoints();
                                        engineBoard.removePiece(assistingGeneral);
                                        allButtonsTalkToFighters();
                                        general.append("\nThe assisting general " + assistingGeneral.toString() + " is dead");
                                        if (activeFighter.isLastFighter(engineBoard,activeFighter.getPieceAlliance())){
                                            engineBoard.removePlayer(assistingGeneral.getPieceAlliance());
                                            general.append("\nThe defending Player is dead");
                                        }


                                    }else if ((activeFighterCasualties >= assistingGeneral.getRegiments() + 1)){

                                        clickedTile.getTown().setMutiny(clickedTile.getTown().getPieceAlliance(), activeFighter);
                                        engineBoard.removePiece(clickedTile.getTown());
                                        engineBoard.setPiece(qcoord, rcoord, scoord, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
                                        activeFighter.setAfterBattleMovementPoints();
                                        engineBoard.removePiece(assistingGeneral);
                                        allButtonsTalkToFighters();
                                        general.append("\nThe defending general " + assistingGeneral.toString() +" is dead");
                                        if (assistingGeneral.isLastFighter(engineBoard,assistingGeneral.getPieceAlliance())){
                                            engineBoard.removePlayer(assistingGeneral.getPieceAlliance());
                                            general.append("\nThe defending Player is dead");
                                        }
                                        getTileButton(assistingGeneral.getPieceQCoord(), assistingGeneral.getPieceRCoord(), assistingGeneral.getPieceSCoord()).talkToEngineFighters();
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        setForeground(clickedTile.getTown().getPieceColor());
                                        activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                                    }
                                    talkToEngineFighters();
                                }
                            }
                            allButtonsTalkToFighters();
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), true);
                            activeFighter = null;


                        }else if ((!clickedTile.getFighters().isEmpty()) && (clickedTile.getCapitol() != null) &&
                                (((clickedTile.getCapitol().getPieceAlliance()) != ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance()) ||
                                        (clickedTile.getCapitol().getPieceAlliance() != (Alliance.UNOCCUPIED))) &&
                                !((Fighter) clickedTile.getFighters().get(0)).getHasFought()) {

                            activeFighter = (Fighter) clickedTile.getFighters().get(0);
                            activeFighter.fightHasFought();
                            activeFighter.exposeAttackFighter();
                            activeFighter.getBattleField(clickedTile);
                            clickedTile.getCapitol().cleanWhoIsHelpingHim();
                            clickedTile.getCapitol().exposeHelp();
                            int activeFighterFightBonus = activeFighter.calculateFightBonus(clickedTile.getCapitol()) + activeFighter.homeAwayBonus(clickedTile.getCapitol(), engineBoard);
                            int activeFighterCasualties = activeFighter.calculateCasualties(clickedTile.getCapitol());
                            int capitolFightBonus = clickedTile.getCapitol().getFightBonus(activeFighter) + clickedTile.getCapitol().homeAwayBonus(activeFighter, engineBoard);
                            int capitolCasualties = clickedTile.getCapitol().getCasualties(activeFighter);
                            Random random = new Random();
                            int attackingRoll = random.nextInt(20) + 1 + activeFighterFightBonus;
                            int defendingRoll = random.nextInt(20) + 1 + capitolFightBonus;

                            general.append("\nThe attacking general " + activeFighter.toString() + " rolled a " + attackingRoll);
                            general.append("\nThe defending Capitol rolled a " + defendingRoll);


                            if (clickedTile.getCapitol().getWhoIsHelpingHim().isEmpty()){

                                if (defendingRoll >= attackingRoll) {
                                    general.append("\nThe defending Capitol won");


                                    if ((activeFighter.getRegiments() >= capitolCasualties + 1) && (!activeFighter.isFighterTrapped(engineBoard))){
                                        activeFighter.releaseRegiments(capitolCasualties);
                                        activeFighter.setAfterBattleMovementPoints();
                                        System.out.println("why is nothing happening?");

                                    }else if ((activeFighter.getRegiments() == 1) || (activeFighter.isFighterTrapped(engineBoard))){
                                        engineBoard.removePiece(activeFighter);
                                        general.append("\nThe attacking general " + activeFighter.toString() + " is dead");
                                        if (activeFighter.isLastFighter(engineBoard,activeFighter.getPieceAlliance())){
                                            engineBoard.removePlayer(activeFighter.getPieceAlliance());
                                            general.append("\nThe attacking Player is dead");
                                        }
                                    }
                                    talkToEngineFighters();


                                } else {
                                    general.append("\nThe attacking general " + activeFighter.toString() + " won");

                                    engineBoard.removePlayer(clickedTile.getCapitol().getPieceAlliance());
                                    general.append("\nThe defending player is dead");
                                    activeFighter.setAfterBattleMovementPoints();
                                    allButtonsTalkToFighters();
                                    setForeground(clickedTile.getTown().getPieceColor());
                                    activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                                }

                            }else if (!clickedTile.getCapitol().getWhoIsHelpingHim().isEmpty()){
                                Fighter assistingGeneral = clickedTile.getCapitol().getWhoIsHelpingHim().get(0);
                                assistingGeneral.getBattleField(clickedTile);

                                if (defendingRoll >= attackingRoll) {
                                    general.append("\nThe defending Capitol won");

                                    if ((activeFighter.getRegiments() >= capitolCasualties + 1) && (!activeFighter.isFighterTrapped(engineBoard))){
                                        activeFighter.releaseRegiments(capitolCasualties);
                                        activeFighter.setAfterBattleMovementPoints();

                                    }else if ((activeFighter.getRegiments() <= capitolCasualties) || (activeFighter.isFighterTrapped(engineBoard))){
                                        engineBoard.removePiece(activeFighter);
                                        general.append("\nThe attacking general " + activeFighter.toString() + " is dead");
                                        if (activeFighter.isLastFighter(engineBoard,activeFighter.getPieceAlliance())){
                                            engineBoard.removePlayer(activeFighter.getPieceAlliance());
                                            general.append("\nThe attacking Player is dead");
                                        }
                                    }
                                    talkToEngineFighters();


                                } else {
                                    general.append("\nThe attacking general " + activeFighter.toString() + " won");

                                    if(((activeFighterCasualties + 1) <= assistingGeneral.getRegiments()) && (!activeFighter.isFighterTrapped(engineBoard))){

                                        assistingGeneral.releaseRegiments(activeFighterCasualties);
                                        getTileButton(assistingGeneral.getPieceQCoord(), assistingGeneral.getPieceRCoord(), assistingGeneral.getPieceSCoord()).talkToEngineFighters();
                                        activeFighter.setAfterBattleMovementPoints();

                                    }else if (((activeFighterCasualties == assistingGeneral.getRegiments()) && (!activeFighter.isFighterTrapped(engineBoard))) ||
                                            ((activeFighterCasualties <= assistingGeneral.getRegiments() + 1) && (activeFighter.isFighterTrapped(engineBoard)))){

                                        activeFighter.setAfterBattleMovementPoints();
                                        engineBoard.removePiece(assistingGeneral);
                                        allButtonsTalkToFighters();
                                        general.append("\nThe defending general " + assistingGeneral.toString() + " is dead");
                                        if (assistingGeneral.isLastFighter(engineBoard,assistingGeneral.getPieceAlliance())){
                                            engineBoard.removePlayer(assistingGeneral.getPieceAlliance());
                                            general.append("\nThe defending Player is dead");
                                        }

                                    }else if ((activeFighterCasualties >= assistingGeneral.getRegiments() + 1)){

                                        engineBoard.removePiece(assistingGeneral);
                                        general.append("\nThe defending general " + assistingGeneral.toString() + " is dead");
                                        engineBoard.removePlayer(clickedTile.getCapitol().getPieceAlliance());
                                        general.append("\nThe defending player is dead");
                                        activeFighter.setAfterBattleMovementPoints();
                                        allButtonsTalkToFighters();
                                        setForeground(clickedTile.getTown().getPieceColor());
                                        activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                                    }
                                    talkToEngineFighters();
                                }
                            }
                            allButtonsTalkToFighters();
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), true);
                            activeFighter = null;



                        }else if (clickedTile.getFighters().size() == 2 &&
                                !((Fighter) clickedTile.getFighters().get(0)).getHasFought()){

                            activeFighter = (Fighter) clickedTile.getFighters().get(1);
                            enemyFighter = (Fighter) clickedTile.getFighters().get(0);
                            Fighter newWellington = activeFighter.wellingtonMethod(enemyFighter, clickedTile);
                            if (newWellington != null){
                                activeFighter = newWellington;
                            }
                            Fighter otherWellington = enemyFighter.wellingtonMethod(activeFighter, clickedTile);
                            if (otherWellington != null){
                                enemyFighter = otherWellington;
                                clickedTile.shuffleFighters();
                            }
                            activeFighter.fightHasFought();
                            activeFighter.exposeAttackFighter();
                            enemyFighter.fightHasFought();
                            enemyFighter.exposeDefenseFighter();
                            enemyFighter.getWhereAttackedFrom(activeFighter);
                            activeFighter.getBattleField(clickedTile);
                            enemyFighter.getBattleField(clickedTile);
                            int activeFighterFightBonus = activeFighter.calculateFightBonus(enemyFighter) + activeFighter.homeAwayBonus(enemyFighter, engineBoard);
                            int activeFighterCasualties = activeFighter.calculateCasualties(enemyFighter);
                            int enemyFighterFightBonus = enemyFighter.calculateFightBonus(activeFighter) + enemyFighter.homeAwayBonus(activeFighter, engineBoard);
                            int enemyFighterCasualties = enemyFighter.calculateCasualties(activeFighter);
                            Random random = new Random();
                            int attackingRoll = random.nextInt(20) + 1 + activeFighterFightBonus;
                            int defendingRoll = random.nextInt(20) + 1 + enemyFighterFightBonus;

                            if (activeFighter.willBribe(enemyFighter,
                                    (enemyFighter.isFighterTrapped(engineBoard)) ||
                                    enemyFighter.isFighterCornered(engineBoard) ||
                                    enemyFighter.isExposedRagnar())){
                                activeFighter.setAfterBattleMovementPoints();
                                activeFighter.musaMethod(enemyFighter);
                                clickedTile.shuffleFighters();
                                general.append("\nMusa buys a win.");

                            }else if (enemyFighter.willBribe(activeFighter,
                                    (activeFighter.isFighterTrapped(engineBoard)) ||
                                    activeFighter.isFighterCornered(engineBoard) ||
                                    activeFighter.isExposedRagnar())){
                                enemyFighter.setAfterBattleMovementPoints();
                                enemyFighter.musaMethod(activeFighter);
                                general.append("\nMusa buys a win.");
                            }else {

                                general.append("\nThe attacking general " + activeFighter.toString() + " rolled a " + attackingRoll);
                                general.append("\nThe defending general " + enemyFighter.toString() + " rolled a " + defendingRoll);

                                if (defendingRoll >= attackingRoll) {
                                    general.append("\nThe defending general " + enemyFighter.toString() + " won");
                                    activeFighter.setScaredOfSargon(enemyFighter);

                                    if ((activeFighter.getRegiments() >= enemyFighterCasualties + 1) &&
                                            (!activeFighter.isFighterTrapped(engineBoard)) &&
                                            ! activeFighter.isFighterCornered(engineBoard) &&
                                            !activeFighter.isExposedRagnar()){
                                        activeFighter.releaseRegiments(enemyFighterCasualties);
                                        activeFighter.setAfterBattleMovementPoints();
                                        enemyFighter.cyrusMethod(activeFighter, enemyFighter.calculateMainCasualties(activeFighter));

                                    }else if (((activeFighter.getRegiments() <= enemyFighterCasualties) ||
                                            (activeFighter.isFighterTrapped(engineBoard))) ||
                                            activeFighter.isFighterCornered(engineBoard) ||
                                            activeFighter.isExposedRagnar()){
                                        enemyFighter.cyrusMethod(activeFighter, activeFighter.getRegiments());
                                        engineBoard.setRevenge(activeFighter.killRagnar());
                                        engineBoard.removePiece(activeFighter);
                                        general.append("\nThe attacking general " + activeFighter.toString() + " is dead");
                                        if (activeFighter.isLastFighter(engineBoard,activeFighter.getPieceAlliance())){
                                            engineBoard.removePlayer(activeFighter.getPieceAlliance());
                                            engineBoard.setRevenge(false);
                                            general.append("\nThe attacking Player is dead");
                                        }
                                    }
                                    talkToEngineFighters();

                                } else {
                                    general.append("\nThe attacking general " + activeFighter.toString() + " won");
                                    enemyFighter.setScaredOfSargon(activeFighter);

                                    if ((enemyFighter.getRegiments() >= activeFighterCasualties + 1) &&
                                            (!enemyFighter.isFighterTrapped(engineBoard)) &&
                                            !enemyFighter.isFighterCornered(engineBoard) &&
                                            !enemyFighter.isExposedRagnar()) {
                                        enemyFighter.releaseRegiments(activeFighterCasualties);
                                        enemyFighter.setAfterBattleMovementPoints();
                                        activeFighter.shivajiMethod();
                                        activeFighter.cyrusMethod(enemyFighter, activeFighter.calculateMainCasualties(enemyFighter));
                                        clickedTile.shuffleFighters();

                                    } else if ((enemyFighter.getRegiments() <= activeFighterCasualties) ||
                                            (enemyFighter.isFighterTrapped(engineBoard)) ||
                                            enemyFighter.isFighterCornered(engineBoard) ||
                                            enemyFighter.isExposedRagnar()) {
                                        activeFighter.cyrusMethod(enemyFighter, enemyFighter.getRegiments());
                                        activeFighter.shivajiMethod();
                                        engineBoard.removePiece(enemyFighter);
                                        general.append("\nThe defending general " + enemyFighter.toString() + " is dead");
                                        if (enemyFighter.isLastFighter(engineBoard, enemyFighter.getPieceAlliance())) {
                                            engineBoard.removePlayer(enemyFighter.getPieceAlliance());
                                            general.append("\nThe defending Player is dead");
                                        }
                                    }
                                    talkToEngineFighters();
                                }
                            }
                            allButtonsTalkToFighters();
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), true);
                            activeFighter = null;



                        }else if ((clickedTile.getFighters().size() ==1) &&
                                (((Fighter)clickedTile.getFighters().get(0)).getPieceAlliance() == Alliance.UNOCCUPIED)){

                            ((Fighter)clickedTile.getFighters().get(0)).setPieceAlliance(activePlayer.getPlayerAlliance());
                            activePlayer.addPlayerPiece((Fighter)clickedTile.getFighters().get(0));
                            engineBoard.removeOtherFighters();
                            allButtonsTalkToFighters();
                            moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                        }else if (clickedTile.getFighters().size() == 2 &&
                                ((Fighter) clickedTile.getFighters().get(0)).getHasFought()){

                            activeFighter = (Fighter) clickedTile.getFighters().get(1);
                            activeFighter.setAfterBattleMovementPoints();
                            activeTile = clickedTile;

                        }else if ((!clickedTile.getFighters().isEmpty()) && (clickedTile.getTown() != null) &&
                                (((clickedTile.getTown().getPieceAlliance()) != ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance()) &&
                                (clickedTile.getTown().getPieceAlliance() != (Alliance.UNOCCUPIED))) &&
                                ((Fighter) clickedTile.getFighters().get(0)).getHasFought()){

                            activeFighter = (Fighter) clickedTile.getFighters().get(0);
                            activeFighter.setAfterBattleMovementPoints();
                            activeTile = clickedTile;
                            System.out.println("something other");


                        }else if ((!clickedTile.getFighters().isEmpty()) && (clickedTile.getTown() != null) &&
                                (((clickedTile.getTown().getPieceAlliance()) == ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance()) &&
                                ((Fighter) clickedTile.getFighters().get(0)).getHasFought())){

                            activeFighter = (Fighter) clickedTile.getFighters().get(0);
                            activeFighter.setAfterBattleMovementPoints();
                            activeFighter.genghisMethod();
                            activeTile = clickedTile;
                            System.out.println("something done");


                        }else if ((!clickedTile.getFighters().isEmpty()) && (clickedTile.getCapitol() != null) &&
                                (((clickedTile.getCapitol().getPieceAlliance()) == ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance()) &&
                                ((Fighter) clickedTile.getFighters().get(0)).getHasFought())){

                            activeFighter = (Fighter) clickedTile.getFighters().get(0);
                            activeFighter.setAfterBattleMovementPoints();
                            activeTile = clickedTile;
                            System.out.println("something");

                        }else if ((!clickedTile.getFighters().isEmpty()) && (clickedTile.getCapitol() != null) &&
                                (((clickedTile.getCapitol().getPieceAlliance()) != ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance()) &&
                                        (clickedTile.getCapitol().getPieceAlliance() != (Alliance.UNOCCUPIED))) &&
                                ((Fighter) clickedTile.getFighters().get(0)).getHasFought()){

                            activeFighter = (Fighter) clickedTile.getFighters().get(0);
                            activeFighter.setAfterBattleMovementPoints();
                            activeTile = clickedTile;
                            System.out.println("something else");

                        }else if ((!clickedTile.getFighters().isEmpty()) && (clickedTile.getTown() != null) &&
                                ((clickedTile.getTown().getPieceAlliance() == (Alliance.UNOCCUPIED))) &&
                                ((Fighter) clickedTile.getFighters().get(0)).getHasFought()){

                            activeFighter = (Fighter) clickedTile.getFighters().get(0);
                            activeFighter.setAfterBattleMovementPoints();
                            activeFighter.giveAcamaRegiment();
                            activeTile = clickedTile;

                            if (isLeftMouseButton(e)){
                                System.out.println("leftmouse");

                            }else if ((isRightMouseButton(e)) && ((Fighter) clickedTile.getFighters().get(0)).getRegiments() >= 2 &&
                                    !((Fighter) activeTile.getFighters().get(0)).getHasDroppedTown() && ((Fighter) activeTile.getFighters().get(0)).shermanCheck()){

                                Town oldTown = clickedTile.getTown();
                                engineBoard.removePiece(oldTown);
                                engineBoard.setPiece(qcoord, rcoord, scoord, (activeFighter.getPieceAlliance()), Piece.PieceType.TOWN);
                                clickedTile.getTown().transferMutiny(oldTown);
                                activeFighter.dropTown();
                                activeFighter.setAfterBattleMovementPoints();
                                activeFighter.genghisMethod();
                                activeFighter.sargonMethod();
                                getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                talkToEngineFighters();
                                setForeground(clickedTile.getTown().getPieceColor());
                                System.out.println("rightmouse");
                                moveList.addMove(engineBoard.getDeepClone(engineBoard), false);



                            }

                        } else if ((!clickedTile.getFighters().isEmpty()) && (clickedTile.getTown() != null) &&
                                (clickedTile.getTown().getPieceAlliance() == ((Fighter) clickedTile.getFighters().get(0)).getPieceAlliance())) {

                            activeFighter = (Fighter) clickedTile.getFighters().get(0);
                            activeTile = clickedTile;

                        } else if (clickedTile.getFighters().isEmpty() &&
                                (clickedTile.getTown() == null) &&
                                (clickedTile.getCapitol() == null)) {

                            if ((!activeTile.getFighters().isEmpty()) &&
                                    (isNextTo(getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()))) &&
                                    activeFighter != null &&
                                    activeFighter.canPostBattleFighterEscapeHere(clickedTile)) {

                                if (isLeftMouseButton(e) && (activeFighter.getMovementPoints() >= 1)) {

                                    if (((clickedTile.getSupplyLine() == null))) {

                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(false, false);
                                        clickedTile.addFighter(activeFighter);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);



                                    } else if ((clickedTile.getSupplyLine() != null) &&
                                            (clickedTile.getSupplyLine().getPieceAlliance() == activeFighter.getPieceAlliance())) {

                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(false, false);
                                        clickedTile.addFighter(activeFighter);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);



                                    }
                                }
                                activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());
                            }else if ((!activeTile.getFighters().isEmpty()) &&
                                    (isNextTo(getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()))) &&
                                    activeFighter != null &&
                                    activeFighter.genghisOrShivaji()){
                                if (isLeftMouseButton(e) && (activeFighter.getMovementPoints() >= 1)) {

                                    if (((clickedTile.getSupplyLine() == null))) {

                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(false, false);
                                        clickedTile.addFighter(activeFighter);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                    } else if ((clickedTile.getSupplyLine() != null) &&
                                            (clickedTile.getSupplyLine().getPieceAlliance() == activeFighter.getPieceAlliance())) {

                                        activeTile.getFighters().remove(activeFighter);
                                        activeFighter.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                        activeFighter.lowerMovementPoints(false, false);
                                        clickedTile.addFighter(activeFighter);
                                        getTileButton(activeTile.getTileQCoord(), activeTile.getTileRCoord(), activeTile.getTileSCoord()).talkToEngineFighters();
                                        talkToEngineFighters();
                                        moveList.addMove(engineBoard.getDeepClone(engineBoard), false);


                                    }
                                }
                                activeTile = engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());

                            }
                        }else if (clickedTile.getFighters().size() == 1 && ((Fighter)clickedTile.getFighters().get(0)).getPieceAlliance() == activePlayer.getPlayerAlliance() &&
                                ((Fighter)clickedTile.getFighters().get(0)).genghisOrShivaji()){
                            activeFighter = (Fighter)clickedTile.getFighters().get(0);
                            activeTile = clickedTile;
                            general.append("\nmps = " + activeFighter.getMovementPoints() + "\nregs = " + activeFighter.getRegiments());

                        }
                    }else if (activePlayer.getTurnStage() == Stage.SETASSIST){
                        if ((clickedTile.getFighters().size() == 1) &&
                                (((Fighter)clickedTile.getFighters().get(0)).getPieceAlliance() == activePlayer.getPlayerAlliance()) ){
                            if (isLeftMouseButton(e)){
                                helpingFighter = (Fighter)clickedTile.getFighters().get(0);
                                helpingTile = clickedTile;
                                general.append("\n" + helpingFighter.toString() + " ready to Help");
                                general.setCaretPosition(general.getDocument().getLength());

                            } else if (isRightMouseButton(e) &&
                                    (isNextTo(getTileButton(helpingTile.getTileQCoord(), helpingTile.getTileRCoord(), helpingTile.getTileSCoord())))){
                                assistedFighter = (Fighter)clickedTile.getFighters().get(0);
                                helpingFighter.helpFighter(assistedFighter);
                                if (helpingFighter.canHelpFighter(assistedFighter)){
                                    general.append("\n" + helpingFighter.toString() + " helping " + assistedFighter.toString());
                                    general.setCaretPosition(general.getDocument().getLength());
                                }else{
                                    general.append("\n" + helpingFighter.toString() + "wont or cant help " + assistedFighter.toString());
                                    general.setCaretPosition(general.getDocument().getLength());
                                }
                            } else if (isRightMouseButton(e) && helpingFighter.nurhaciHelp(clickedTile)){
                                assistedFighter = (Fighter)clickedTile.getFighters().get(0);
                                helpingFighter.helpFighter(assistedFighter);
                                if (helpingFighter.canHelpFighter(assistedFighter)){
                                    general.append("\n" + helpingFighter.toString() + " helping " + assistedFighter.toString());
                                    general.setCaretPosition(general.getDocument().getLength());
                                }else{
                                    general.append("\n" + helpingFighter.toString() + "wont or cant help " + assistedFighter.toString());
                                    general.setCaretPosition(general.getDocument().getLength());
                                }
                            }
                        }else if (helpingFighter != null && helpingTile != null && (clickedTile.getTown() != null) &&
                                clickedTile.getTown().getPieceAlliance() == activePlayer.getPlayerAlliance() &&
                                (isNextTo(getTileButton(helpingTile.getTileQCoord(), helpingTile.getTileRCoord(), helpingTile.getTileSCoord())))){
                            if (isRightMouseButton(e)){
                                assistedTown = clickedTile.getTown();
                                helpingFighter.helpTown(assistedTown);
                                general.append("\n" + helpingFighter.toString() + " helping Town");
                                general.setCaretPosition(general.getDocument().getLength());
                            }else if (isRightMouseButton(e) && helpingFighter.nurhaciHelp(clickedTile)){
                                assistedTown = clickedTile.getTown();
                                helpingFighter.helpTown(assistedTown);
                                general.append("\n" + helpingFighter.toString() + " helping Town");
                                general.setCaretPosition(general.getDocument().getLength());
                            }

                        }else if (helpingFighter != null && helpingTile != null && (clickedTile.getCapitol() != null) &&
                                clickedTile.getCapitol().getPieceAlliance() == activePlayer.getPlayerAlliance() &&
                                (isNextTo(getTileButton(helpingTile.getTileQCoord(), helpingTile.getTileRCoord(), helpingTile.getTileSCoord())))){
                            if (isRightMouseButton(e)){
                                assistedCapitol = clickedTile.getCapitol();
                                helpingFighter.helpCapitol(assistedCapitol);
                                general.append("\n" + helpingFighter.toString() + " helping Capitol");
                                general.setCaretPosition(general.getDocument().getLength());
                            }else if (isRightMouseButton(e) && helpingFighter.nurhaciHelp(clickedTile)){
                                assistedCapitol = clickedTile.getCapitol();
                                helpingFighter.helpCapitol(assistedCapitol);
                                general.append("\n" + helpingFighter.toString() + " helping Capitol");
                                general.setCaretPosition(general.getDocument().getLength());
                            }
                        }
                    }else if (activePlayer.getTurnStage() == Stage.MOVECOS){
                        if (activePlayer.isChiefConnected(clickedTile) && ((!clickedTile.getFighters().isEmpty() && ((Fighter)clickedTile.getFighters().get(0)).getPieceAlliance() == activePlayer.getPlayerAlliance()) ||
                                ((clickedTile.getCapitol() != null)&&(clickedTile.getCapitol().getPieceAlliance() == activePlayer.getPlayerAlliance())))){

                            //ChiefOfStaff activeChiefOfStaff = activePlayer.getChiefOfStaff();

                            if (!clickedTile.getFighters().isEmpty()){

                                //engineBoard.removePiece(activeChiefOfStaff);
                                //activeChiefOfStaff.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                //clickedTile.setChiefOfStaff(activeChiefOfStaff);
                                //activePlayer.addPlayerPiece(activeChiefOfStaff);
                                activePlayer.giveChiefOfStaff((Fighter)clickedTile.getFighters().get(0));


                            }else if (clickedTile.getCapitol() != null){

                                //engineBoard.removePiece(activeChiefOfStaff);
                                //activeChiefOfStaff.setNewCoordinates(getQCoord(), getRCoord(), getSCoord());
                                //clickedTile.setChiefOfStaff(activeChiefOfStaff);
                                //activePlayer.addPlayerPiece(activeChiefOfStaff);
                                activePlayer.giveChiefOfStaff(clickedTile.getCapitol());


                            }
                        }
                    }
                    boardPanel.validate();
                    boardPanel.repaint();
                }
                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Player activePlayer = engineBoard.getActivePlayer();
                    Tile enteredTile =  engineBoard.getTile(getQCoord(), getRCoord(), getSCoord());

                    if (enteredTile.getFighters().size() == 1 && activePlayer.getTurnStage() != Stage.BATTLE) {

                        Fighter fighter = (Fighter) enteredTile.getFighters().get(0);

                        if (fighter.getPieceAlliance() == activePlayer.getPlayerAlliance()) {
                            ruleCard.setText(fighter.toString() + fighter.exposedString() + fighter.fightsAndKills() +  "\n" + fighter.proDescription() + "\n" + fighter.conDescription());
                            ruleCard.setCaretPosition(0);

                        } else if (fighter.getPieceAlliance() != activePlayer.getPlayerAlliance()) {
                            if (fighter.getIsExposed()) {
                                if (fighter.isGhost()){
                                    ruleCard.setText(fighter.ghostName() + fighter.fightsAndKills() + "\n" + fighter.proDescription() + "\n" + fighter.conDescription());
                                    ruleCard.setCaretPosition(0);
                                }else{
                                    ruleCard.setText(fighter.toString() + fighter.fightsAndKills() + "\n" + fighter.proDescription() + "\n" + fighter.conDescription());
                                    ruleCard.setCaretPosition(0);
                                }
                            } else if (!fighter.getIsExposed()) {
                                ruleCard.setText("Unknown General \nfight bonus =? " + fighter.getRegiments() + "\nkills =? " + (int) Math.ceil((double)fighter.getRegiments() / 2));
                                ruleCard.setCaretPosition(0);
                            }
                        }
                    }else if (enteredTile.getFighters().size() == 2 && activePlayer.getTurnStage() != Stage.BATTLE){
                        Fighter fighter = (Fighter) enteredTile.getFighters().get(1);
                        ruleCard.setText(fighter.toString() + fighter.exposedString() + fighter.fightsAndKills() +  "\n" + fighter.proDescription() + "\n" + fighter.conDescription());
                        ruleCard.setCaretPosition(0);

                    }else if (activePlayer.getTurnStage() == Stage.BATTLE){

                        if (enteredTile.getFighters().size() == 1 && enteredTile.getTown() == null && enteredTile.getCapitol() == null) {

                            Fighter fighter = (Fighter) enteredTile.getFighters().get(0);

                            if (fighter.getPieceAlliance() == activePlayer.getPlayerAlliance()) {
                                ruleCard.setText(fighter.toString() + fighter.exposedString() + fighter.fightsAndKills() +  "\n" + fighter.proDescription() + "\n" + fighter.conDescription());
                                ruleCard.setCaretPosition(0);

                            } else if (fighter.getPieceAlliance() != activePlayer.getPlayerAlliance()) {
                                if (fighter.getIsExposed()) {
                                    if (fighter.isGhost()){
                                        ruleCard.setText(fighter.ghostName() + fighter.fightsAndKills() + "\n" + fighter.proDescription() + "\n" + fighter.conDescription());
                                        ruleCard.setCaretPosition(0);
                                    }else{
                                        ruleCard.setText(fighter.toString() + fighter.fightsAndKills() + "\n" + fighter.proDescription() + "\n" + fighter.conDescription());
                                        ruleCard.setCaretPosition(0);
                                    }
                                } else if (!fighter.getIsExposed()) {
                                    ruleCard.setText("Unknown General \nfight bonus =? " + fighter.getRegiments() + "\nkills =? " + (int) Math.ceil((double)fighter.getRegiments() / 2));
                                    ruleCard.setCaretPosition(0);
                                }
                            }
                        }else if (enteredTile.getFighters().size() == 2){
                            if (((Fighter)enteredTile.getFighters().get(1)).getHasFought()){
                                Fighter fighter = (Fighter) enteredTile.getFighters().get(1);
                                ruleCard.setText(fighter.toString() + fighter.exposedString() + fighter.fightsAndKills() +  "\n" + fighter.proDescription() + "\n" + fighter.conDescription());
                                ruleCard.setCaretPosition(0);
                            }else if (!((Fighter)enteredTile.getFighters().get(1)).getHasFought()){
                                Fighter defendingFighter = (Fighter) enteredTile.getFighters().get(0);
                                Fighter attackingFighter = (Fighter) enteredTile.getFighters().get(1);

                                int attackingFighterFightBonus = attackingFighter.calculateFightBonus(defendingFighter) + attackingFighter.homeAwayBonus(defendingFighter, engineBoard);
                                int attackingFighterCasualties = attackingFighter.calculateCasualties(defendingFighter);
                                int defendingFighterFightBonus = defendingFighter.calculateFightBonus(attackingFighter) + defendingFighter.homeAwayBonus(attackingFighter, engineBoard);
                                int defendingFighterCasualties = defendingFighter.calculateCasualties(attackingFighter);

                                ruleCard.setText("The attacking general " + attackingFighter.toString() + " has a fight bonus of " + attackingFighterFightBonus);
                                ruleCard.append("\n\nThe defending general " + defendingFighter.toString() + " has a fight bonus of " + defendingFighterFightBonus);
                                ruleCard.append("\n\nThe attacking general " + attackingFighter.toString() + " kills " + attackingFighterCasualties);
                                ruleCard.append("\n\nThe defending general " + defendingFighter.toString() + " kills " + defendingFighterCasualties);
                                ruleCard.setCaretPosition(0);
                            }

                        }else if (enteredTile.getFighters().size() == 1 && enteredTile.getTown() != null){
                            if (((Fighter)enteredTile.getFighters().get(0)).getHasFought()){
                                Fighter fighter = (Fighter) enteredTile.getFighters().get(0);
                                ruleCard.setText(fighter.toString() + fighter.exposedString() + fighter.fightsAndKills() +  "\n" + fighter.proDescription() + "\n" + fighter.conDescription());
                                ruleCard.setCaretPosition(0);
                            }else if (!((Fighter)enteredTile.getFighters().get(0)).getHasFought()){
                                Fighter attackingFighter = (Fighter) enteredTile.getFighters().get(0);
                                Town defendingTown = enteredTile.getTown();

                                int attackingFighterFightBonus = attackingFighter.calculateFightBonus(defendingTown) + attackingFighter.homeAwayBonus(defendingTown, engineBoard);
                                int attackingFighterCasualties = attackingFighter.calculateCasualties(defendingTown);
                                int defendingTownFightBonus = defendingTown.getFightBonus(attackingFighter) + defendingTown.homeAwayBonus(attackingFighter, engineBoard);
                                int defendingTownCasualties = defendingTown.getCasualties(attackingFighter);

                                ruleCard.setText("The attacking general " + attackingFighter.toString() + " has a fight bonus of " + attackingFighterFightBonus);
                                ruleCard.append("\n\nThe defending Town has a fight bonus of " + defendingTownFightBonus);
                                ruleCard.append("\n\nThe attacking general " + attackingFighter.toString() + " kills " + attackingFighterCasualties);
                                ruleCard.append("\n\nThe defending Town kills " + defendingTownCasualties);
                            }

                        }else if (enteredTile.getFighters().size() == 1 && enteredTile.getCapitol() != null){
                            if (((Fighter)enteredTile.getFighters().get(0)).getHasFought()){
                                Fighter fighter = (Fighter) enteredTile.getFighters().get(0);
                                ruleCard.setText(fighter.toString() + fighter.exposedString() + fighter.fightsAndKills() +  "\n" + fighter.proDescription() + "\n" + fighter.conDescription());
                                ruleCard.setCaretPosition(0);
                            }else if (!((Fighter)enteredTile.getFighters().get(0)).getHasFought()){
                                Fighter attackingFighter = (Fighter) enteredTile.getFighters().get(0);
                                Capitol defendingCapitol = enteredTile.getCapitol();

                                int attackingFighterFightBonus = attackingFighter.calculateFightBonus(defendingCapitol) + attackingFighter.homeAwayBonus(defendingCapitol, engineBoard);
                                int attackingFighterCasualties = attackingFighter.calculateCasualties(defendingCapitol);
                                int defendingCapitolFightBonus = defendingCapitol.getFightBonus(attackingFighter) + defendingCapitol.homeAwayBonus(attackingFighter, engineBoard);
                                int defendingCapitolCasualties = defendingCapitol.getCasualties(attackingFighter);

                                ruleCard.setText("The attacking general " + attackingFighter.toString() + " has a fight bonus of " + attackingFighterFightBonus);
                                ruleCard.append("\n\nThe defending Capitol has a fight bonus of " + defendingCapitolFightBonus);
                                ruleCard.append("\n\nThe attacking general " + attackingFighter.toString() + " kills " + attackingFighterCasualties);
                                ruleCard.append("\n\nThe defending Capitol kills " + defendingCapitolCasualties);
                            }

                        }

                    }
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
        public TileButton getTileButton(int qCoord,int rCoord, int sCoord){
            for (TileButton tileButton : boardTiles){
                if ((tileButton.getQCoord() == qCoord) && (tileButton.getRCoord() == rCoord) && (tileButton.getSCoord() == sCoord)) {
                    return tileButton;
                }
            }
            System.out.println("getTileButton cant find that tile");
            return null;
        }

        public boolean isNextTo(TileButton tileButton){
            if (((tileButton.getQCoord() == this.getQCoord()) &&
                    ((tileButton.getRCoord() + 1) == this.getRCoord()) &&
                    ((tileButton.getSCoord() - 1) == this.getSCoord())) ||
                    ((tileButton.getQCoord() == this.getQCoord()) &&
                            ((tileButton.getRCoord() - 1) == this.getRCoord()) &&
                            ((tileButton.getSCoord() + 1) == this.getSCoord())) ||
                    (((tileButton.getQCoord() + 1) == this.getQCoord()) &&
                            ((tileButton.getRCoord() - 1) == this.getRCoord()) &&
                            ((tileButton.getSCoord()) == this.getSCoord())) ||
                    (((tileButton.getQCoord() - 1) == this.getQCoord()) &&
                            ((tileButton.getRCoord() + 1) == this.getRCoord()) &&
                            ((tileButton.getSCoord()) == this.getSCoord())) ||
                    (((tileButton.getQCoord() - 1) == this.getQCoord()) &&
                            ((tileButton.getRCoord()) == this.getRCoord()) &&
                            ((tileButton.getSCoord() + 1) == this.getSCoord())) ||
                    (((tileButton.getQCoord() + 1) == this.getQCoord()) &&
                            ((tileButton.getRCoord()) == this.getRCoord()) &&
                            ((tileButton.getSCoord() - 1) == this.getSCoord()))){
                return true;
            }else {
                return false;
            }
        }

        public int getFontSize(){
            Font labelFont = this.getFont();
            String labelText = "30";

            int stringWidth =  (this.getFontMetrics(labelFont).stringWidth(labelText) * 3/2);
            int componentWidth = this.getWidth();

            // Find out how much the font can grow in width.
            double widthRatio = (double)componentWidth / (double)stringWidth;

            int newFontSize = (int)(labelFont.getSize() * widthRatio);
            int componentHeight = this.getHeight();

            // Pick a new font size so it will not be larger than the height of label.
            //System.out.println("getFontSize is active " + newFontSize + " " + componentHeight + " " + stringWidth );
            return newFontSize;
            //return Math.min(newFontSize, componentHeight);
        }

        public void talkToEngineFighters(){
            if (engineBoard.getTile(this.getQCoord(), this.getRCoord(), this.getSCoord()).getFighters().size() == 1) {
                Fighter fighter = ((Fighter) engineBoard.getTile(this.getQCoord(), this.getRCoord(), this.getSCoord()).getFighters().get(0));

                if (fighter.getIsExposed()){

                    this.setText(Integer.toString(fighter.getRegiments()));
                    this.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, this.getFontSize()));
                    this.setForeground(fighter.getPieceColor());

                }else if (!fighter.getIsExposed()){

                    this.setText(Integer.toString(fighter.getRegiments()));
                    this.setFont(new Font("Sanserif", Font.BOLD, this.getFontSize()));
                    this.setForeground(fighter.getPieceColor());
                }

            }else if (engineBoard.getTile(this.getQCoord(), this.getRCoord(), this.getSCoord()).getFighters().size() >= 2){
                Fighter fighter = ((Fighter) engineBoard.getTile(this.getQCoord(), this.getRCoord(), this.getSCoord()).getFighters().get(1));

                if (fighter.getIsExposed()){

                    this.setText(Integer.toString(fighter.getRegiments()));
                    this.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, this.getFontSize()));
                    this.setForeground(fighter.getPieceColor());

                }else if (!fighter.getIsExposed()){

                    this.setText(Integer.toString(fighter.getRegiments()));
                    this.setFont(new Font("Sanserif", Font.BOLD, this.getFontSize()));
                    this.setForeground(fighter.getPieceColor());
                }


            }else if (engineBoard.getTile(this.getQCoord(), this.getRCoord(), this.getSCoord()).getFighters().isEmpty()){
                this.setText(null);

            }
            boardPanel.validate();
            boardPanel.repaint();
        }


        @Override public Dimension getPreferredSize() {
            //Icon icon = getIcon();
            //Insets i = getInsets();
            //int iw = Math.max(icon.getIconWidth(), icon.getIconHeight());
            return new Dimension(circle, circle);
        }
        @Override protected void paintBorder(Graphics g) {
            initShape();
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            Tile thisTile = engineBoard.getTile(this.getQCoord(), this.getRCoord(), this.getSCoord());

            if (zoom == 1){
                g2.setStroke(new BasicStroke(2.0f));
            }else if (zoom == 2){
                g2.setStroke(new BasicStroke(3.0f));
            }else if (zoom == 3){
                g2.setStroke(new BasicStroke(4.0f));
            }


            if (thisTile.getSupplyLine() != null) {
                if (!thisTile.getFighters().isEmpty() &&
                        ((Fighter)thisTile.getFighters().get(0)).hasChiefOfStaff()){
                    this.addIcon(chiefofstaff);

                }else {
                    this.addIcon(null);

                }
                g2.setColor(thisTile.getSupplyLine().getPieceColor());
                g2.draw(shape);

            }else if ((thisTile.getTown() != null) &&
                    (!thisTile.getFighters().isEmpty()) &&
                    (thisTile.getTown().getPieceAlliance() !=
                            ((Fighter)thisTile.getFighters().get(0)).getPieceAlliance())){
                this.addIcon(battleimage);
                g2.setColor(thisTile.getTown().getPieceColor());
                g2.draw(shape);
                this.setForeground(((Fighter) thisTile.getFighters().get(0)).getPieceColor());


            }else if ((thisTile.getCapitol() != null) &&
                    (!thisTile.getFighters().isEmpty()) &&
                    (thisTile.getCapitol().getPieceAlliance() !=
                            ((Fighter)thisTile.getFighters().get(0)).getPieceAlliance())){
                this.addIcon(battleimage);
                this.setForeground(((Fighter) thisTile.getFighters().get(0)).getPieceColor());


            }else if ((thisTile.getCapitol() != null) &&
                    (thisTile.getFighters().isEmpty())){
                if (thisTile.getCapitol().hasChiefOfStaff()){
                    this.addIcon(chiefofstaff);

                }else {
                    this.addIcon(null);
                }
            }else if ((thisTile.getTown() != null) &&
                    (!thisTile.getFighters().isEmpty()) &&
                    (thisTile.getTown().getPieceAlliance() ==
                            ((Fighter)thisTile.getFighters().get(0)).getPieceAlliance())){
                this.addIcon(battleimage);
                this.setForeground(((Fighter) thisTile.getFighters().get(0)).getPieceColor());
                g2.setColor(thisTile.getTown().getPieceColor());
                g2.draw(shape);

            }else if (thisTile.getTown() != null) {
                this.addIcon(null);
                g2.setColor(thisTile.getTown().getPieceColor());
                g2.draw(shape);
                g2.fill(shape);

            }else if (thisTile.getFighters().size() >= 2){
                this.addIcon(battleimage);
                this.setForeground(((Fighter) thisTile.getFighters().get(1)).getPieceColor());

            }else if (thisTile.getFighters().size() == 1){
                if (((Fighter)thisTile.getFighters().get(0)).hasChiefOfStaff()){
                    this.addIcon(chiefofstaff);
                    this.setForeground(((Fighter) thisTile.getFighters().get(0)).getPieceColor());

                }else {
                    this.addIcon(null);
                    this.setForeground(((Fighter) thisTile.getFighters().get(0)).getPieceColor());
                }
            }else if ((thisTile.getFighters().isEmpty()) &&
                    (thisTile.getTown() == null) &&
                    (thisTile.getCapitol() == null) &&
                    thisTile.getSupplyLine() == null){
                this.addIcon(null);

            }
            boardPanel.validate();
            boardPanel.repaint();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);

        }
        @Override public boolean contains(int x, int y) {
            initShape();
            return shape.contains(x, y);
            //or return super.contains(x, y) && ((image.getRGB(x, y) >> 24) & 0xff) > 0;
        }
    }

}
