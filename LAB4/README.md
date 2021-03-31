![image](https://user-images.githubusercontent.com/38810840/113054093-4e0e6980-91a9-11eb-9cc0-dece486e3b4b.png)
![image](https://user-images.githubusercontent.com/38810840/113150265-2dd7bc80-9234-11eb-9229-d232ca0cddad.png)
![image](https://user-images.githubusercontent.com/38810840/113151658-970bff80-9235-11eb-8107-cd658409f750.png)

Funkcja wczytująca dane z pliku do tablicy 4 wymiarowej (przy zachowanej strukturze pliku):

            File p= new File(pli);
            Scanner m = new Scanner(p);
            String liczby;
            String[] liczb_cz;
            count=m.nextInt(); // pobiera ilosc platow
            tablica = new double[count][][][];
            for(int i=0;i<count;i++){
                w_n=m.nextInt()+1; 
                tablica[i]= new double[w_n][w_n][];
                m.nextLine();
                for(int h=0;h<w_n;h++){  // (1)
                    for(int k=0;k<w_n;k++){ // wraz z (1) tworza macierz punktow n+1 (w przypadku pliku 4 na 4)
                        liczby=m.nextLine();
                        liczb_cz=liczby.split(" "); // dzielimy wiersz
                        tablica[i][h][k]=new double[liczb_cz.length];
                        for(int j=0;j<liczb_cz.length;j++){
                            tablica[i][h][k][j]= Double.parseDouble(liczb_cz[j]); // dla np (x y z) wprowadza kolejne wartosci (x - 0, y - 1 itd)
                        }
                    }
                }
            }
            
            
Funkcja zapisujaca punkty:
            
   
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
