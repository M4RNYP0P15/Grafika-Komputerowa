import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Map;
import javax.imageio.*;
import java.awt.geom.Rectangle2D;

public class Lab7 extends JFrame implements ActionListener {
    static class ImagePanel extends JComponent {
        protected BufferedImage image;
        public ImagePanel(){}
        public ImagePanel(BufferedImage img){ image = img;}
        public void setImage(BufferedImage img){
            image = img;
            EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    getPreferredSize();
                    validate();
                    ImagePanel.super.validate();

                    repaint();
                }
            });

        }
        public BufferedImage getImage() { return image; }
        public Dimension getPreferredSize(){
            try {
                return new Dimension(image.getWidth(),image.getHeight());
            } catch (Exception e) {
                //System.out.println("blad");
                return new Dimension(200,200);
            }
        }
        public void paintComponent(Graphics g){
            Rectangle rect = this.getBounds();
            if(image != null) {
                this.getPreferredSize();
                g.drawImage(image, 0,0, rect.width, rect.height, this);
            }
        }
    }
    int[] tablica_red = new int[256];
    int[] tablica_green = new int[256];
    int[] tablica_blue = new int[256];

    JFileChooser opendialog = new JFileChooser();
    static BufferedImage orgimg;
    JTabbedPane tabs = new JTabbedPane();
    JPanel liniowa = new JPanel();
    JPanel potegowa = new JPanel();
    JPanel ctl = new JPanel();

    JPanel cont_opcje = new JPanel();
    JButton prev = new JButton("Podglad");
    JButton open = new JButton("Otworz");
    JButton reset = new JButton("Resetuj");
    JButton save = new JButton("Zapisz");

    JPanel con_slider = new JPanel();
    JButton gauss = new JButton("Gauss");
    JButton druga = new JButton("Uśredniający");
    JLabel gammal=new JLabel("gamma= 1.00");
    JSlider gamma = new JSlider(JSlider.HORIZONTAL,0,255,85);
    JLabel alphal=new JLabel("c= 1");
    JSlider alpha = new JSlider(JSlider.HORIZONTAL,-127,126,1);

    JPanel con_slider_a = new JPanel();
    JLabel b_wspoll = new JLabel("b= 205");
    JSlider b_wspol = new JSlider(JSlider.HORIZONTAL,0,255,205);
    JLabel alfal=new JLabel("a= 50.00");
    JSlider alfa = new JSlider(JSlider.HORIZONTAL,0,250,50);

    JPanel img = new JPanel();


    ImagePanel imagepanel = new ImagePanel();
    ImagePanel imagepanel1 = new ImagePanel();
    ImagePanel imagepanel2 = new ImagePanel();
    ImagePanel imagepanel3 = new ImagePanel();

    JPanel imagecontainer = new JPanel();

    double gammav = 1.0;
    int Var_c = 1;
    int b_wspolv = 205;
    double alfav=50.0;
    boolean gaussem = false;

    public Lab7()
    {
        Container cont = new Container();//this.getContentPane();
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readImage();
            }
        });
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t_Oblicz();
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamma.setValue(85);
                alpha.setValue(50);
                gammav=1.0;
                Var_c =1;
                b_wspolv=205;
                t_Oblicz();
            }
        });

        alfa.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                alfav=alfa.getValue();
//                alfav/=100;
                alfal.setText("a= "+alfav);
                t_Oblicz();
            }
        });

        b_wspol.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                b_wspolv=b_wspol.getValue();
                b_wspoll.setText("b= "+ b_wspolv);
                t_Oblicz();
            }
        });
        gauss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gaussem = true;
                t_Oblicz();
            }
        });
        druga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gaussem = false;
                t_Oblicz();
            }
        });
        alpha.addChangeListener(new ChangeListener( ) {
            public void stateChanged(ChangeEvent e){
                Var_c =alpha.getValue();
//                alphav/=alpha.getMaximum();
//                alphav=Math.round(alphav*100);
//                alphav/=100;
                String s = "C " + Integer.toString(Var_c);
//                while (s.length()<10) s+="0";
                alphal.setText(s);
//                alphal.repaint();
                t_Oblicz();
            }
        });
        img.setBorder(BorderFactory.createEtchedBorder());
        img.setLayout(new BorderLayout());
        img.add(new JScrollPane(imagecontainer));
        imagecontainer.add(imagepanel);
        imagecontainer.add(imagepanel1);
        imagecontainer.add(imagepanel2);
        imagecontainer.add(imagepanel3);


        con_slider.setBorder(BorderFactory.createEtchedBorder());
        con_slider.setLayout(new GridBagLayout());
//        con_slider.add(gammal,new GridBagConstraints(0,0,1,1,1.0,1.0,
//                GridBagConstraints.WEST,
//                GridBagConstraints.NONE,
//                new Insets(2,2,2,2),0,0));
//        con_slider.add(gamma, new GridBagConstraints(1,0,1,1,1.0,1.0,
//                GridBagConstraints.EAST,
//                GridBagConstraints.NONE,
//                new Insets(2,2,2,2),0,0));
        con_slider.add(gauss,new GridBagConstraints(0,1,1,1,1.0,1.0,
                GridBagConstraints.WEST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));
        con_slider.add(druga, new GridBagConstraints(1,1,1,1,1.0,1.0,
                GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));

        con_slider_a.setBorder(BorderFactory.createEtchedBorder());
        con_slider_a.setLayout(new GridBagLayout());
        con_slider_a.add(b_wspoll,new GridBagConstraints(0,0,1,1,1.0,1.0,
                GridBagConstraints.WEST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));
        con_slider_a.add(b_wspol, new GridBagConstraints(1,0,1,1,1.0,1.0,
                GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));
        con_slider_a.add(alfal,new GridBagConstraints(0,1,1,1,1.0,1.0,
                GridBagConstraints.WEST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));
        con_slider_a.add(alfa, new GridBagConstraints(1,1,1,1,1.0,1.0,
                GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));
        potegowa.add(con_slider, BorderLayout.CENTER);
        liniowa.add(con_slider_a, BorderLayout.CENTER);
        ctl.setBorder(BorderFactory.createEtchedBorder());
        ctl.add(prev);
        ctl.add(open);
        ctl.add(save);
        ctl.add(reset);

        tabs.insertTab("Rozmycie", null, potegowa, "Wybierz rozmycie...", 0);
        tabs.insertTab("Skalowanie", null, liniowa, "Skaluj", 1);
        cont_opcje.add(ctl, BorderLayout.NORTH);
        cont_opcje.add(tabs, BorderLayout.SOUTH);
        this.getContentPane().add(img, BorderLayout.CENTER);
        this.getContentPane().add(cont_opcje, BorderLayout.SOUTH);

        this.setSize(900,600);
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("LAB7");
    }

    public void actionPerformed(ActionEvent e){

    }

    public void readImage(){
        BufferedImage image;

        int retval = opendialog.showOpenDialog(this);
        if (retval==JFileChooser.CANCEL_OPTION) return;
        try {
            File input = new File(opendialog.getSelectedFile().getPath());
            image = ImageIO.read(input);
            Graphics2D g2 = image.createGraphics();
            g2.drawImage(image,0,0,null);
            orgimg=image;

            imagepanel.setImage(image);
            imagepanel.setSize(image.getWidth(this),image.getHeight(this));
            img.validate();
            img.repaint();

        } catch (Exception e) { System.err.println("Nie można wczytać - exception");}
    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void t_Oblicz(){
        BufferedImage image_ = deepCopy(orgimg);
        int opcja=tabs.getSelectedIndex();
        int[][] M = new int[][] { {1,1,1}, {1,1,1}, {1,1,1}};// filtr uśredniający
        int ra;
        int sum=0;
        switch (opcja){
            case 0:
                if(gaussem)
                    M = new int[][] { {1,2,1}, {2,4,2}, {1,2,1}}; // gauss

                for (int k = 0; k < M.length; k++) {
                    for (int i = 0; i < M[k].length; i++) {
                        sum += M[k][i];
                    }
                }
                ra = M.length/2;
                break;
            case 1:
                ra = 0;
                break;
            default:
                ra=0;
        }
        for(int i=ra; i<image_.getHeight()-1; i++){
            for(int j=ra; j<image_.getWidth()-1; j++){
                Color co = new Color(image_.getRGB(j, i));
                int r = co.getRed();
                int g = co.getGreen();
                int b = co.getBlue();
                int red, green, blue;
                int pomoc_r=0,pomoc_g=0,pomoc_b=0;
                Color newColor;

                switch (opcja){
                    case 0:
                        for (int k=-ra;k<=ra;k++){
                            for(int l=-ra;l<=ra;l++){
                                Color co1 = new Color(image_.getRGB(j+k, i+l));
                                pomoc_r+=co1.getRed()*M[k+1][l+1];
                                pomoc_g+=co1.getGreen()*M[k+1][l+1];
                                pomoc_b+=co1.getBlue()*M[k+1][l+1];
                            }
                        }
                        pomoc_r/=sum;
                        pomoc_b/=sum;
                        pomoc_g/=sum;
                        newColor = new Color(pomoc_r, pomoc_g, pomoc_b);
                        break;
                    case 1:
                        red= (int)skalowanie(r, (int)alfav, b_wspolv, 0, 255);
                        green= (int)skalowanie(g, (int)alfav, b_wspolv, 0, 255);
                        blue= (int)skalowanie(b, (int)alfav, b_wspolv, 0, 255);
                        newColor = new Color(red, green, blue);
                        break;
//                    case 2:
//                        Image_r[i][j]=pomoc_r;
//                        Image_g[i][j]=pomoc_g;
//                        Image_b[i][j]=pomoc_b;
//                        if(pomoc_r>255)
//                            pomoc_r=255;
//                        if(pomoc_g>255)
//                            pomoc_g=255;
//                        if(pomoc_b>255)
//                            pomoc_b=255;
//                        Color newColor = new Color(pomoc_r, pomoc_g, pomoc_b);
//                        image_.setRGB( j,i,newColor.getRGB() );
//                        break;
                    default:
                        red=r;
                        blue=b;
                        green=g;
                        newColor = new Color(red, green, blue);
                        break;
                }
                image_.setRGB( j,i,newColor.getRGB() );
            }
        }
        imagepanel.setImage(image_);
        zczytaj();
        histogram(tablica_red, tablica_green, tablica_blue);
        img.validate();
        img.repaint();
        imagepanel.validate();
        imagepanel.repaint();
        imagepanel1.validate();
        imagepanel1.repaint();
        imagecontainer.validate();
        this.repaint();
    }

    double hc(double[] tab, int a){
        if(a<=0)
            return tab[0];
        double wy =0;
        for (int i = a; i>0; i--) {
            wy+=tab[i]+ tab[i-1];
        }
        //wy+= tab[0];
        return wy;
    }

    void zczytaj(){
        for (int i = 0; i < tablica_green.length; i++) {
            tablica_green[i]=0;
        }
        for (int i = 0; i < tablica_red.length; i++) {
            tablica_red[i]=0;
        }
        for (int i = 0; i < tablica_blue.length; i++) {
            tablica_blue[i]=0;
        }
        for(int i=0; i<imagepanel.image.getHeight(); i++) {
            for (int j = 0; j < imagepanel.image.getWidth(); j++) {
                Color co = new Color(imagepanel.image.getRGB(j, i));
                int r = co.getRed();
                tablica_red[r]++;
                int g = co.getGreen();
                tablica_green[g]++;
                int b = co.getBlue();
                tablica_blue[b]++;
            }
        }

        int pom = imagepanel.image.getHeight() * imagepanel.image.getWidth();
        double[] tab_red = new double[256];
        for (int i = 0; i < tablica_red.length; i++) {
            tab_red[i]= tablica_red[i]/(pom*1.0);
        }
        double[] tab_green = new double[256];
        for (int i = 0; i < tablica_green.length; i++) {
            tab_green[i]= tablica_green[i]/(pom*1.0);
        }
        double[] tab_blue = new double[256];
        for (int i = 0; i < tablica_blue.length; i++) {
            tab_blue[i]= tablica_blue[i]/(pom*1.0);
        }
    }

    double skalowanie(double p, int a, int b, int c, int d){
        double wynik = ((p-a)*(d-c)/(b-a)+c);
        if(wynik >255)
            return 255;
        if(wynik <0)
            return 0;
        return wynik;
    }

    void histogram(int[] tablica_red, int[] tablica_green, int[]tablica_blue){
        BufferedImage image1 = new BufferedImage(266, 256, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image1.createGraphics();
        float alpha = 0.3f;
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        int xOffset = 5;
        int yOffset = 5;
        int width = image1.getWidth() - 8 - (xOffset * 2);
        int height = image1.getHeight() - 1 - (yOffset * 2);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(xOffset, yOffset, image1.getWidth()-8, height);
        int barWidth = Math.max(1, (int) Math.floor((float) width / (float) tablica_red.length ) );
        int maxValue=0;
        for (int i = 0; i < tablica_red.length; i++) {
            maxValue = Math.max(maxValue, tablica_red[i]);
        }
        int xPos = xOffset;
        for (int i = 0; i < tablica_red.length; i++) {
            int value = tablica_red[i];
            int barHeight = Math.round(((float) value / (float) maxValue) * height);
            g2d.setColor(new Color(255, i/2, i/2));
            int yPos = height + yOffset - barHeight;
            //Rectangle bar = new Rectangle(xPos, yPos, barWidth, barHeight);
            Rectangle2D bar = new Rectangle2D.Float(xPos, yPos, barWidth, barHeight);
            g2d.fill(bar);
            g2d.draw(bar);
            xPos += barWidth;
        }
        imagepanel1.setImage(image1);

        BufferedImage image2 = new BufferedImage(265, 256, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dg = image2.createGraphics();
        g2dg.setComposite(alcom);
        g2dg.setColor(Color.DARK_GRAY);
        g2dg.drawRect(xOffset, yOffset, width+8, height);
        //barWidth = Math.max(3, (int) Math.floor((float) width / (float) tablica_green.length ) );
        maxValue=0;
        for (int i = 0; i < tablica_green.length; i++) {
            maxValue = Math.max(maxValue, tablica_green[i]);
        }
        xPos = xOffset;
        for (int i = 0; i < tablica_green.length; i++) {
            int value = tablica_green[i];
            int barHeight = Math.round(((float) value / (float) maxValue) * height);
            g2dg.setColor(new Color(i/2, 255, i/2));
            int yPos = height + yOffset - barHeight;
            //Rectangle bar = new Rectangle(xPos, yPos, barWidth, barHeight);
            Rectangle2D bar = new Rectangle2D.Float(xPos, yPos, barWidth, barHeight);
            g2dg.fill(bar);
            g2dg.draw(bar);
            xPos += barWidth;
        }
        imagepanel2.setImage(image2);

        BufferedImage image3 = new BufferedImage(265, 256, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2db = image3.createGraphics();
        g2db.setComposite(alcom);
        g2db.setColor(Color.DARK_GRAY);
        g2db.drawRect(xOffset, yOffset, width+8, height);
        //barWidth = Math.max(3, (int) Math.floor((float) width / (float) tablica_blue.length ) );
//        System.out.println("width = " + width + ";"+height+" size = " + tablica_blue.length + "; barWidth = " + barWidth);
        maxValue=0;
        for (int i = 0; i < tablica_blue.length; i++) {
            maxValue = Math.max(maxValue, tablica_blue[i]);
        }
        xPos = xOffset;
        for (int i = 0; i < tablica_blue.length; i++) {
            int value = tablica_blue[i];
            int barHeight = Math.round(((float) value / (float) maxValue) * height);
            g2db.setColor(new Color(i/2, i/2, 255));
            int yPos = height + yOffset - barHeight;
            //Rectangle bar = new Rectangle(xPos, yPos, barWidth, barHeight);
            Rectangle2D bar = new Rectangle2D.Float(xPos, yPos, barWidth, barHeight);
            g2db.fill(bar);
            g2db.draw(bar);
            xPos += barWidth;
        }
        imagepanel3.setImage(image3);
        imagecontainer.validate();
        imagepanel3.validate();
        imagepanel3.getPreferredSize();
        imagepanel3.repaint();
        img.validate();
        img.repaint();
        g2d.dispose();
        this.repaint();
    }

    public void saveImage()
    {
        int retval = opendialog.showSaveDialog(this);
        if (retval==JFileChooser.CANCEL_OPTION) return;
        try {
            ImageIO.write((RenderedImage)(imagepanel.getImage()), "jpg", opendialog.getSelectedFile());
        } catch (IOException e) { System.err.println("Exception!!!");}
    }

    public static void main(String[] args) {

        Lab7 o = new Lab7();
    }
}
