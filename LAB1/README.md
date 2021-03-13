NR.indeksu: 80249
![image](https://user-images.githubusercontent.com/38810840/111038765-f642d500-842a-11eb-844e-d0cbeb5aa14b.png)

Stałe:

    private final int DELAY = 150; // opoznienie
    private final int INITIAL_DELAY = 200; // opoznienie
    private int x = 1; // zmienna przechowujaca wartosc odsuwania chmur
    private float alpha = 1; // zmienna przechowujaca wartosc kanalu alfa


Funkcja doDrawing

        Graphics2D g2d = (Graphics2D) g.create();

        int w = getWidth(); //przypisanie szerokosci okna
        int h = getHeight(); //przypisanie wysokosci okna

        GradientPaint gp2 = new GradientPaint(0, 0, Color.blue, 0, 2*h/3, new Color(85, 187, 216), true);  // utworzenie nowego gradientu 
        g2d.setPaint(gp2); //ustawienie koloru dla kolejnych elementow
        g2d.fillRect(0, 0, w, 2*h/3); //tworzenie prostokata (w tym wypadku niebo)

        GradientPaint gp1 = new GradientPaint(0, 2*h/3-10, new Color(35, 220, 16), 0, h, new Color(80, 117, 10), true);

        g2d.setPaint(gp1);
        g2d.fillRect(0, 2*h/3, w, h);  // gradient dół (trawa)

        g2d.setPaint(Color.black);

        BasicStroke bs1 = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL); // tworzenie "krawędzi" (zaokrąglona szerokosc 2)
        g2d.setStroke(bs1); //ustawienie "krawędzi" kolejnych elementow
        g2d.drawRect(15, h/2, w/2, h/4);  // zarys ciala chatki
        g2d.setPaint(Color.GRAY);
        int chatka_w=w/2-1;
        int chatka_h=h/4-1;
        g2d.fillRect(15, h/2, chatka_w, chatka_h); // cialo chatki

        g2d.setPaint(new Color(100, 55, 35));

        int xRoof[] = {10, 12+10, chatka_w, chatka_w+20};
        int yRoof[] = {h/2, h/2-90, h/2-90, h/2};
        g2d.fillPolygon(xRoof, yRoof, 4); //dach chatki

        g2d.setPaint(new Color(150, 90, 50));
        g2d.fillRect(w/3-1, h/2+10, 60, chatka_h-10); // drzwi

        g2d.setPaint(Color.white);
        g2d.fillRect(40, h/2+20, w/10, w/9); //okno
        g2d.fillOval(w/3-1+45, (h/2+10)+(chatka_h-10)/2, 7, 7); //klamka drzwi
        g2d.setPaint(Color.black);
        g2d.fillRect(40+w/20, h/2+20, 4, w/9);
        g2d.fillRect(40, h/2+w/12, w/10, 4); // szczebliny w oknach

        g2d.setPaint(Color.red);

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        Font font = new Font("Dialog", Font.PLAIN, 25);
        g2d.setFont(font); // ustawienie czcionki

        FontMetrics fm = g2d.getFontMetrics();
        String s = "GKM";
        Dimension size = getSize();
        
        int stringWidth = fm.stringWidth(s); // szerokosc napisu
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-alpha); //ustawienie kanalu alfa (przezroczystosc)
        g2d.setComposite(ac);

        g2d.drawString(s, w -stringWidth-5, h-20); // rysowanie napisu

        g2d.setPaint(Color.yellow);
        g2d.fillOval(w/2,28,(h/9)%w,h/9); //slonce

Chmury:

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
        
Deszcz: 

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
        
        
        g2d.dispose(); // Usuwa ten kontekst graficzny i zwalnia wszystkie używane zasoby systemowe.

Funkcja mająca za zadanie rozszerzania pozycji chmur i zwiekszanie/zmniejszanie kanalu alfa poszczegolnych elementow aż do zatrzymania "timera":

    private void step() {
        x+=1;
        alpha -= 0.01;

        if (alpha <= 0.01)
            timer.stop(); // zatrzymanie timer'a 
    }

Główne ograniczenia/ustawienia:

        setTitle("Obrazek2D"); // nazwa okna
        setSize(550, 520); // wielkosc okna
        setMinimumSize(new Dimension(550,500)); // min wielkosc okna
        setLocationRelativeTo(null);  // Ustawia położenie okna względem określonego komponentu (w tym przypadku null)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // akcja zamkniecia okna poprzez przycisk "X"
    
