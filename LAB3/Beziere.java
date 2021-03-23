package com.zetcode;

import javax.swing.*;
import java.awt.*;

class Surface extends JPanel {
    public Surface() { }

    void beziere(int[][] x, int[][] y, Graphics2D g2d){
        double t;
        BasicStroke bs1 = new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs1);
        g2d.setColor(Color.red);
        for(int a=1;a<x.length;a++){
            for(t=0.0;t<1.0;t+=0.01){
                double xt= Math.pow(1-t,3)*x[a-1][2] + 3*t*Math.pow(1-t,2)*x[a][0] + 3*Math.pow(t,2)*(1-t)*x[a][1] + Math.pow(t,3)*x[a][2];
                double yt= Math.pow(1-t,3)*y[a-1][2] + 3*t*Math.pow(1-t,2)*y[a][0] + 3*Math.pow(t,2)*(1-t)*y[a][1] + Math.pow(t,3)*y[a][2];

                g2d.drawLine((int)xt,(int)yt,(int)xt,(int)yt);
            }
        }
        BasicStroke bs2 = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs2);
        for(int b=0;b<x.length;b++){
            for(int a=0;a<3;a++){
                int xt= x[b][a];
                int yt= y[b][a];

                g2d.setColor(Color.black);
                g2d.drawLine(xt,yt,xt,yt);
            }
        }

    }

    void beziere_line(int[] x, int[] y, Graphics2D g2d){
        double t;
        g2d.setColor(Color.red);
        for(t=0.0;t<1.0;t+=0.01){
            double xt= Math.pow(1-t,3)*x[0] + 3*t*Math.pow(1-t,2)*x[1] + 3*Math.pow(t,2)*(1-t)*x[2] + Math.pow(t,3)*x[3];
            double yt= Math.pow(1-t,3)*y[0] + 3*t*Math.pow(1-t,2)*y[1] + 3*Math.pow(t,2)*(1-t)*y[2] + Math.pow(t,3)*y[3];

            g2d.drawLine((int)xt,(int)yt,(int)xt,(int)yt);
        }
        BasicStroke bs2 = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs2);
        g2d.setColor(Color.black);
        for(int a=0;a<3;a++){
            int xt= x[a];
            int yt= y[a];
            g2d.drawLine(xt,yt,xt,yt);
        }
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int xoff=340; // przesuniecie m (po x - odleglos od lewej strony)
        int yoff=-170; //przesuniecie m (po y -wysokosc)
        int xgoff=-50;
        int ygoff=-5;
        int xkoff=190;
        int ykoff=20;
        int[][] xk = new int[][]{ //współrzędne (x) k
                { 0     , 0   , 22+xkoff  },
                {22  + xkoff, 27  + xkoff, 27  + xkoff},
                {27  + xkoff, 57  + xkoff, 57  + xkoff},
                {57  + xkoff, 57  + xkoff, 57  + xkoff},
                {57  + xkoff, 115 + xkoff, 116 + xkoff},
                {118 + xkoff, 148 + xkoff, 149 + xkoff},
                {150 + xkoff, 79  + xkoff, 80  + xkoff},
                {86  + xkoff, 157 + xkoff, 153 + xkoff},
                {145 + xkoff, 127 + xkoff, 124 + xkoff},
                {121 + xkoff, 54  + xkoff, 54  + xkoff},
                {54  + xkoff, 52  + xkoff, 52  + xkoff},
                {52  + xkoff, 27  + xkoff, 27  + xkoff}};

        int[][] yk = new int[][]{ //współrzędne (y) k
                { 0     , 0   , 29+ykoff  },
                { 14  + ykoff   ,217 + ykoff  , 224 + ykoff },
                { 228 + ykoff   ,230 + ykoff  , 228 + ykoff },
                { 222 + ykoff   ,130 + ykoff  , 132 + ykoff },
                { 142 + ykoff   ,226 + ykoff  , 229 + ykoff },
                { 236 + ykoff   ,237 + ykoff  , 233 + ykoff },
                { 227 + ykoff   ,119 + ykoff  , 118 + ykoff },
                { 112 + ykoff   ,33  + ykoff  , 32  + ykoff },
                { 30  + ykoff   ,27  + ykoff  , 31  + ykoff },
                { 34  + ykoff   ,109 + ykoff   ,102 + ykoff },
                { 97  + ykoff   ,36  + ykoff   ,34  + ykoff },
                { 33  + ykoff   ,31  + ykoff   ,33  + ykoff }};

        int[][] xm = new int[][]{ //współrzędne (x) m
                {0             , 0   , 20 + xoff},
                { 20  + xoff    ,90  + xoff  , 93  + xoff },
                { 96 + xoff    ,144 + xoff  , 147 + xoff },
                { 150 + xoff   ,200 + xoff  , 203 + xoff },
                { 207 + xoff   ,263 + xoff  , 267 + xoff },
                { 269 + xoff   ,240 + xoff  , 238 + xoff },
                { 234 + xoff   ,198 + xoff  , 196 + xoff },
                { 194 + xoff   ,154 + xoff  , 150 + xoff },
                { 146 + xoff   ,99  + xoff  , 96  + xoff },
                { 93  + xoff   ,56  + xoff  , 53  + xoff },
                { 50  + xoff   ,25  + xoff  , 23  + xoff }};

        int[][] ym = new int[][]{ //współrzędne (y) m
                { 0     , 0   , 429+yoff  },
                { 430 + yoff   ,213 + yoff  , 210 + yoff },
                { 208 + yoff   ,335 + yoff  , 330 + yoff },
                { 325 + yoff   ,208 + yoff  , 210 + yoff },
                { 213 + yoff   ,427 + yoff  , 428 + yoff },
                { 434 + yoff   ,434 + yoff  , 430 + yoff },
                { 434 + yoff   ,293 + yoff  , 299 + yoff },
                { 294 + yoff   ,413 + yoff  , 417 + yoff }, //2 ostatnie środek m
                { 420 + yoff   ,293 + yoff  , 299 + yoff },
                { 294 + yoff   ,428 + yoff   ,430 + yoff },
                { 432 + yoff   ,434 + yoff   ,432 + yoff }};

        int[][] xg = new int[][]{ //G
                {0            ,0          , 237+xgoff },
                { 77+xgoff    ,62 +xgoff  , 252+xgoff },
                { 255 +xgoff  ,254+xgoff  , 254+xgoff },
                { 254 +xgoff  ,206+xgoff  , 206+xgoff },
                { 207 +xgoff  ,209+xgoff  , 209+xgoff },
                { 219 +xgoff  ,232+xgoff  , 232+xgoff },
                { 231 +xgoff  ,238+xgoff  , 227+xgoff },
                { 79  +xgoff  ,136+xgoff  , 232+xgoff },
                { 240 +xgoff  ,235+xgoff  , 238+xgoff }};

        int[][] yg = new int[][]{ //G
                { 0, 0,66+ygoff},
                { 54+ygoff    ,258+ygoff  , 258+ygoff }, // 1 2 2
                { 258+ygoff   ,160+ygoff  , 159+ygoff }, // 2 3 3
                { 155+ygoff   ,156+ygoff  , 158+ygoff }, // 3 4 4 itd
                { 174+ygoff   ,171+ygoff  , 176+ygoff },
                { 177+ygoff   ,176+ygoff  , 175+ygoff },
                { 177+ygoff   ,237+ygoff  , 235+ygoff },
                { 213+ygoff   ,72 +ygoff  , 87 +ygoff },
                { 89 +ygoff   ,78 +ygoff  , 72 +ygoff } };
        beziere(xm,ym, g2d);
        beziere(xk,yk,g2d);
        beziere(xg,yg,g2d);
//        g2d.dispose();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}

class PointsE extends JFrame {
    public PointsE() {
        initUI();
    }

    private void initUI() {
        final com.zetcode.Surface surface = new com.zetcode.Surface();
        add(surface);

        setTitle("Krzywe Bezier'a");
        setSize(640, 450);
        setMinimumSize(new Dimension(640,450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PointsE ex = new PointsE();
                ex.setVisible(true);
            }
        });
    }
}
