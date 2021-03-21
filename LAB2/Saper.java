package com.zetcode;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;

import java.awt.*;

class Config_set extends JPanel implements ActionListener {

    private final int DELAY = 1500;
    private Timer timer;
    public JTextField wiel;
    public JTextField bomby;
    public JLabel o_wiel = new JLabel("Wprowadz wielkość planszy (n x n) n=");
    public JLabel o_bomby = new JLabel("Wprowadz ilosc bomb (musi być mniejsza od ilości pól) :");

    public Config_set() {
        wiel = new JTextField(15);
        bomby= new JTextField(15);
        setLayout(new FlowLayout());
        JButton b =new JButton("Uruchom gre");
        add(b,0);
        add(o_wiel,1);
        add(wiel,2);
        add(o_bomby,1);
        add(bomby,1);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("cos");

            }
        });



//        initTimer();
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer() {

        return timer;
    }

    private void doDrawing(Graphics g) {
        this.add(wiel);
        this.add(bomby);
//        Graphics2D g2d = (Graphics2D) g;
//
//        g2d.setPaint(Color.blue);
//        int w = getWidth();
//        int h = getHeight();
//
//        Random r = new Random();
//
//            int x = Math.abs(r.nextInt()) % w;
//            int y = Math.abs(r.nextInt()) % h;
//            g2d.drawLine(x, y, x, y);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

class BoardG extends JPanel {

    private final int NUM_IMAGES = 13;
    private final int CELL_SIZE = 25;

    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    private int N_MINES = 40;
    private int N_ROWS;
    private int N_COLS;

    private int BOARD_WIDTH;
    private int BOARD_HEIGHT;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    private int allCells;
    private JLabel statusbar;
    private Timer timer_game;
    public String lastString ="";
    public int czas=0;


    public BoardG(JLabel statusbar, int bombs, int wiel) {

        this.statusbar = statusbar;
        N_MINES = bombs;
        N_COLS = wiel;
        N_ROWS = wiel;
        BOARD_WIDTH = N_COLS * CELL_SIZE;
        BOARD_HEIGHT = N_ROWS * CELL_SIZE;

        initBoard();
    }

    public Timer getTimer() {

        return timer_game;
    }

    private void initBoard() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        img = new Image[NUM_IMAGES];


        for (int i = 0; i < NUM_IMAGES; i++) {
            var path = "src/resources/saper/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }
        addMouseListener(new MinesAdapter());
        timer_game = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                czas++;
                statusUpdate("",false);
            }
        });
        newGame();
    }
    void statusUpdate(String napis, boolean wyw){
        if(wyw){
            statusbar.setText(napis);
            lastString=napis;
        }else
        {
            statusbar.setText(lastString+" Czas:"+czas+"s");
        }

    }

    private void newGame() {

        int cell;
        var random = new Random();
        timer_game.start();
        inGame = true;
        minesLeft = N_MINES;
        allCells = N_ROWS * N_COLS;
        field = new int[allCells];
        lastString="";

        for (int i = 0; i < allCells; i++) {

            field[i] = COVER_FOR_CELL;
        }

//        statusbar.setText();
        statusUpdate("Pozostało: "+Integer.toString(minesLeft)+" oznaczeń.",true);
        int i = 0;

        while (i < N_MINES) {

            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)
                    && (field[position] != COVERED_MINE_CELL)) {

                int current_col = position % N_COLS;
                field[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - N_COLS;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + N_COLS - 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - N_COLS;
                if (cell >= 0) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + N_COLS;
                if (cell < allCells) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (N_COLS - 1)) {
                    cell = position - N_COLS + 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + N_COLS + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
        czas=0;
        timer_game.start();
    }

    private void find_empty_cells(int j) {
        int current_col = j % N_COLS;
        int cell;

        if (current_col > 0) {
            cell = j - N_COLS - 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j - 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + N_COLS - 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        cell = j - N_COLS;
        if (cell >= 0) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + N_COLS;
        if (cell < allCells) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        if (current_col < (N_COLS - 1)) {
            cell = j - N_COLS + 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + N_COLS + 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
            cell = j + 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        int uncover = 0;

        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {

                int cell = field[(i * N_COLS) + j];
                if (inGame && cell == MINE_CELL) {
                    inGame = false;
                }

                if (!inGame) {
                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }
                } else {
                    if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }
                g.drawImage(img[cell], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        if (uncover == 0 && inGame) {

            inGame = false;
            timer_game.stop();
//            statusbar.setText();
            statusUpdate("Wygrałeś/łaś. Liczba punktów:" + N_COLS*N_MINES/czas, true);

        } else if (!inGame) {
//            statusbar.setText();
            statusUpdate("Przegrałeś/łaś", true);
            timer_game.stop();
        }
    }

    private class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean doRepaint = false;

            if (!inGame) {
                if(timer_game.isRunning())
                    timer_game.stop();
                newGame();
                repaint();
            }

            if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (field[(cRow * N_COLS) + cCol] > MINE_CELL) {

                        doRepaint = true;

                        if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL) {

                            if (minesLeft > 0) {
                                field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
//                                statusbar.setText();
                                statusUpdate("Pozostało: "+msg+" oznaczeń.", true);
                            } else {
//                                statusbar.setText();
                                statusUpdate("Wyczerpano oznaczenia.", true);
                            }
                        } else {

                            field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusUpdate(msg, true);
//                            statusbar.setText(msg);
                        }
                    }

                } else {

                    if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL) {

                        return;
                    }

                    if ((field[(cRow * N_COLS) + cCol] > MINE_CELL)
                            && (field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)) {

                        field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
                        doRepaint = true;

                        if (field[(cRow * N_COLS) + cCol] == MINE_CELL) {
                            inGame = false;
                        }

                        if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL) {
                            find_empty_cells((cRow * N_COLS) + cCol);
                        }
                    }
                }

                if (doRepaint) {
                    repaint();
                }
            }
        }
    }
}


public class Saper extends JFrame {
    private JLabel statusbar;
    private JLabel timebar;
    private JTextField bomby = new JTextField("25",15);
    public int wys;
    public int bomb;
    public JTextField wiel = new JTextField("9",15);
    public JLabel o_wiel = new JLabel("Wprowadz wielkość planszy (n x n) n=");
    public JLabel o_bomby = new JLabel("Wprowadz ilosc bomb (musi być mniejsza od ilości pól) :");
    public JPanel pane = new JPanel();
    public Saper() {
        initUI();
    }

    private void initUI() {
        add(pane);
        pane.setLayout(new GridLayout(0,2,2,2));
        JButton b =new JButton("Uruchom gre");
        pane.add(o_bomby);
        pane.add(bomby);
        pane.add(o_wiel);
        pane.add(wiel);
        pane.add(b);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    bomb=Integer.parseInt(bomby.getText());
                    wys=Integer.parseInt(wiel.getText());
                }catch (Exception ex){
                    System.out.println(ex);
                    return;
//                    throw(ex);
                }
                if((bomb>=(wys-1)*(wys-1)) || (bomb<1) || wys<4){
                    return;
                }
                remove(pane);
                statusbar = new JLabel("");
//                timebar = new JLabel("cos");

                add(new BoardG(statusbar, bomb,wys));
                add(statusbar, BorderLayout.SOUTH);
//                add(timebar, BorderLayout.LINE_END);
                pack();
            }
        });
        setResizable(false);
        pack();
        //setSize(new Dimension(400, 300));

        setTitle("Saper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

        public static void main(String[] args) {
            EventQueue.invokeLater(() -> {
                var ex = new Saper();
                ex.setVisible(true);
            });
        }
}

