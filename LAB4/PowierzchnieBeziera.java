import java.io.*;
import java.util.Scanner;

public class PowierzchnieBeziera
{
    static int facotori(int m, int i){
        if(m<i){
            return 1;
        }
        int wyn=1;
        int wy=1;
        for(int a=m;a>i;a--){
            wyn*=a;
        }
        for(int a=m-i;a>0;a-- )
            wy*=a;
        return wyn/wy;
    }

    static void bezier_surf(double[][][][] tab, String n_file){
        double w,v;
        int m=3;
        int n=3;
        double xt,yt,zt;
        try {
            PrintWriter zap = new PrintWriter(n_file);
            for (int a = 0; a < tab.length; a++) {
                for (v = 0.0; v < 1.0; v += 0.005) {
                    for (w = 0.0; w < 1.0; w += 0.005) {
                        xt = 0;
                        yt = 0;
                        zt = 0;
                        for (int i = 0; i <= m; i++) {
                            for (int j = 0; j <= n; j++) {
                                xt += facotori(m, i) * Math.pow(v, i) * Math.pow(1 - v, m - i) * tab[a][i][j][0] * facotori(m, j) * Math.pow(w, j) * Math.pow(1 - w, m - j);
                                yt += facotori(m, i) * Math.pow(v, i) * Math.pow(1 - v, m - i) * tab[a][i][j][1] * facotori(m, j) * Math.pow(w, j) * Math.pow(1 - w, m - j);
                                zt += facotori(m, i) * Math.pow(v, i) * Math.pow(1 - v, m - i) * tab[a][i][j][2] * facotori(m, j) * Math.pow(w, j) * Math.pow(1 - w, m - j);
                            }
                        }
                        zap.println(xt+" "+yt+" "+zt);
                    }
                }
            }
            zap.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static double[][][][] odczyt_bpt(String pli){
        double[][][][] tablica;
        int count;
        int w_n;
        try{
            File p= new File(pli);
            Scanner m = new Scanner(p);
            String liczby;
            String[] liczb_cz;
            count=m.nextInt();
            tablica = new double[count][][][];
            for(int i=0;i<count;i++){
                w_n=m.nextInt()+1;
                tablica[i]= new double[w_n][w_n][];
                m.nextLine();
                for(int h=0;h<w_n;h++){
                    for(int k=0;k<w_n;k++){
                        liczby=m.nextLine();
                        liczb_cz=liczby.split(" ");
                        tablica[i][h][k]=new double[liczb_cz.length];
                        for(int j=0;j<liczb_cz.length;j++){
                            tablica[i][h][k][j]= Double.parseDouble(liczb_cz[j]);
                        }
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
        return tablica;
    }

    public static void main(String[] args) {
        double[][][][] tab = odczyt_bpt("plik.txt"); // dzbanek
        bezier_surf(tab, "teapot_p.txt");

        double[][][][] tab1 = odczyt_bpt("teacup.txt");
        bezier_surf(tab1, "teacup_p.txt");

        double[][][][] tab2 = odczyt_bpt("teaspoon.txt");
        bezier_surf(tab2, "teaspoon_p.txt");
    }
}
