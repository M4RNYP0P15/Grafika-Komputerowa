Krzywe beziera_Inicjały

![image](https://user-images.githubusercontent.com/38810840/112131029-5ecb4800-8bc9-11eb-95cb-76039497025a.png)

Główna częśc kodu:

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
