package com.zetcode;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

class Surface extends JPanel implements ActionListener {

    private final int DELAY = 150;
    private final int INITIAL_DELAY = 200;
    private int x = 1;
    private float alpha = 1;
    private Timer timer;

    public Surface() {
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }

    public Timer getTimer() {
        return timer;
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int w = getWidth();
        int h = getHeight();

        GradientPaint gp2 = new GradientPaint(0, 0, Color.blue, 0, 2*h/3, new Color(85, 187, 216), true);
        g2d.setPaint(gp2);
        g2d.fillRect(0, 0, w, 2*h/3);
//        g2d.setColor(new Color(105, 207, 116));
//        g2d.fillRect(0, 2*h/3, w, h);

        GradientPaint gp1 = new GradientPaint(0, 2*h/3-10, new Color(35, 220, 16), 0, h, new Color(80, 117, 10), true);

        g2d.setPaint(gp1);
        g2d.fillRect(0, 2*h/3, w, h);  // gradient dół (trawa)

        g2d.setPaint(Color.black);
//        Graphics2D g2d1 = (Graphics2D) g.create();

        BasicStroke bs1 = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs1);
        g2d.drawRect(15, h/2, w/2, h/4);  // zarys ciala chatki
        g2d.setPaint(Color.GRAY);
        int chatka_w=w/2-1;
        int chatka_h=h/4-1;
        g2d.fillRect(15, h/2, chatka_w, chatka_h); // cialo chatki

//        BasicStroke bs2 = new BasicStroke(1, BasicStroke.CAP_ROUND,
//                BasicStroke.JOIN_BEVEL);
//        g2d.setStroke(bs2);
//        g2d.drawRect(15, h/2, w/2, h/4);

        g2d.setPaint(new Color(100, 55, 35));

        int xRoof[] = {10, 12+10, chatka_w, chatka_w+20};
        int yRoof[] = {h/2, h/2-90, h/2-90, h/2};
        g2d.fillPolygon(xRoof, yRoof, 4); //dach

        g2d.setPaint(new Color(150, 90, 50));
        g2d.fillRect(w/3-1, h/2+10, 60, chatka_h-10); // drzwi

        g2d.setPaint(Color.white);
        g2d.fillRect(40, h/2+20, w/10, w/9); //okno
        g2d.fillOval(w/3-1+45, (h/2+10)+(chatka_h-10)/2, 7, 7); //klamka drzwi
        g2d.setPaint(Color.black);
        g2d.fillRect(40+w/20, h/2+20, 4, w/9); //
        g2d.fillRect(40, h/2+w/12, w/10, 4);

        g2d.setPaint(Color.red);

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        Font font = new Font("Dialog", Font.PLAIN, 25);
        g2d.setFont(font);
//
        FontMetrics fm = g2d.getFontMetrics();
        String s = "GKM";
        Dimension size = getSize();
//
        int stringWidth = fm.stringWidth(s);
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-alpha);
        g2d.setComposite(ac);

        g2d.drawString(s, w -stringWidth-5, h-20);

        g2d.setPaint(Color.yellow);
        g2d.fillOval(w/2,28,(h/9)%w,h/9);

        g2d.setColor(Color.white);
        AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
        g2d.setComposite(acomp);
        int chmuraX=w/3-x;
        int chmuraY=h/7;
        g2d.fillOval(chmuraX, chmuraY, w/11, h/10); //4
        g2d.fillOval(chmuraX+15, chmuraY-25, w/10, h/10);//1
        g2d.fillOval(chmuraX+30, chmuraY, w/10, h/10);
        g2d.fillOval(chmuraX+40, chmuraY-30, w/10, h/10);
        g2d.fillOval(chmuraX+50, chmuraY-20, w/10, h/10);
        g2d.fillOval(chmuraX+60, chmuraY, w/10, h/10);

        int chmura2X=w/2+x;
        int chmura2Y=h/7;
        g2d.fillOval(chmura2X, chmura2Y, w/11, h/10); //4
        g2d.fillOval(chmura2X+15, chmura2Y-25, w/10, h/10);//1
        g2d.fillOval(chmura2X+30, chmura2Y, w/10, h/10);
        g2d.fillOval(chmura2X+40, chmura2Y-30, w/10, h/10);
        g2d.fillOval(chmura2X+50, chmura2Y-20, w/10, h/10);
        g2d.fillOval(chmura2X+60, chmura2Y, w/10, h/10);

        AlphaComposite ac1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha/10);
        g2d.setComposite(ac1);
        g2d.setPaint(Color.blue);
        Random r = new Random();
        for (int i = 0; i < 150; i++) {
            int x = Math.abs(r.nextInt()) % w;
            int y = Math.abs(r.nextInt()) % h;
            int y1 = Math.abs(r.nextInt()) % h;
            g2d.drawLine(x, y, x, y1); // imitacja deszczu
        }
        g2d.dispose();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    private void step() {
        x+=1;
        alpha -= 0.01;

        if (alpha <= 0.01)
            timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
        repaint();
    }
}

class PointsEx extends JFrame {

    public PointsEx() {

        initUI();
    }

    private void initUI() {

        final Surface surface = new Surface();
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });

        setTitle("Obrazek2D");
        setSize(550, 520);
        setMinimumSize(new Dimension(550,500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                PointsEx ex = new PointsEx();
                ex.setVisible(true);
            }
        });
    }
}
