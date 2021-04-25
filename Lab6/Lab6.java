import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.image.renderable.RenderableImage;
import java.io.*;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import javax.imageio.*;
import java.awt.geom.Rectangle2D;



public class Lab6 extends JFrame implements ActionListener {
    static class ImagePanel extends JComponent {
        protected BufferedImage image;
        public ImagePanel(){}
        public ImagePanel(BufferedImage img){ image = img;}
        public void setImage(BufferedImage img){ image = img; }
        public BufferedImage getImage() { return image; }
        public Dimension getPreferredSize(){
            try {
                return new Dimension(image.getWidth(),image.getHeight());
            } catch (Exception e) {return new Dimension(1,1);}
        }
        public void paintComponent(Graphics g){
            Rectangle rect = this.getBounds();
            if(image != null) {
                g.drawImage(image, 0,0,rect.width, rect.height, this);
            }
        }
    }

    class Surface extends JPanel {
        public int[] tablica_red = new int[256];
        int[] tablica_green = new int[256];
        int[] tablica_blue = new int[256];
        public Surface() { }
        private void doDrawing(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();

//        g2d.dispose();
        }

        @Override
        public void paintComponent(Graphics g) {
            //super.paintComponent(g);
            doDrawing(g);
        }
    }

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
    JLabel gammal=new JLabel("gamma= 1.00");
    JSlider gamma = new JSlider(JSlider.HORIZONTAL,0,255,85);
    JLabel alphal=new JLabel("c= 1");
    JSlider alpha = new JSlider(JSlider.HORIZONTAL,-127,126,1);

    JPanel con_slider_a = new JPanel();
    JLabel b_wspoll = new JLabel("b= 0");
    JSlider b_wspol = new JSlider(JSlider.HORIZONTAL,-255,255,0);
    JLabel alfal=new JLabel("a= 1.00");
    JSlider alfa = new JSlider(JSlider.HORIZONTAL,-100,100,1);

    JPanel img = new JPanel();


    ImagePanel imagepanel = new ImagePanel();
    ImagePanel imagepanel1 = new ImagePanel();
    ImagePanel imagepanel2 = new ImagePanel();
    ImagePanel imagepanel3 = new ImagePanel();

    JPanel imagecontainer = new JPanel();


    double gammav = 1.0;
    int Var_c = 1;
    int b_wspolv = 0;
    double alfav=1.0;
//    byte LUT[] = new byte[256];

    public Lab6()
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
                alpha.setValue(1);
                gammav=1.0;
                Var_c =1;
                b_wspolv=0;
                t_Oblicz();
            }
        });
        gamma.addChangeListener(new ChangeListener( ) {
            public void stateChanged(ChangeEvent e){
                gammav=gamma.getValue();
                gammav/=gamma.getMaximum();
                gammav*=3;
                gammav=Math.round(gammav*100);
                gammav/=100;
                String s="gamma(n) "+Double.toString(gammav);
                while (s.length()<10) s+="0";
                gammal.setText(s);
                gammal.repaint();
            }
        });
        alfa.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                alfav=alfa.getValue();
//                alfav/=100;
                alfal.setText("a= "+alfav);
            }
        });

        b_wspol.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                b_wspolv=b_wspol.getValue();
                b_wspoll.setText("b= "+ b_wspolv);
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
        con_slider.add(alphal,new GridBagConstraints(0,1,1,1,1.0,1.0,
                GridBagConstraints.WEST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));
        con_slider.add(alpha, new GridBagConstraints(1,1,1,1,1.0,1.0,
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
        tabs.insertTab("Kontrast", null, potegowa, "Oblicz kontrast", 0);
        tabs.insertTab("...", null, liniowa, "Oblicz his", 1);
        cont_opcje.add(ctl, BorderLayout.NORTH);
        cont_opcje.add(tabs, BorderLayout.SOUTH);
        this.getContentPane().add(img, BorderLayout.CENTER);
        this.getContentPane().add(cont_opcje, BorderLayout.SOUTH);

        this.setSize(900,600);
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("LAB6");
    }

    public void actionPerformed(ActionEvent e){
//        String cmd = e.getActionCommand();
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
            img.repaint();
        } catch (Exception e) { System.err.println("Nie można wczytać - exception");}
    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public int f_oblicz(int b) //
    {

//        v’(x,y)=(127/(127-c))*(v(x,y)-c) -> zwiększanie kontrastu
//        b)
//        v’(x,y)=((127+c)/127)*(v(x,y)-c) -> zmniejszenie kontrastu
//        gdzie
//        c jest wartością kontrastu w zakresie [0..127] dla przypadku (a)
//            i wartością kontrastu w zakresie [−128..0) dla przypadku (b)

        int x=b;
        if (x<0) x+=255;
        double b1=x;
//        b1/=255;
        double w;
        if(Var_c >=0)
            w= ( 127/(127- Var_c) )*(b- Var_c);
        else
            w= ( (127 -Var_c)/127 *(b+Var_c) );
//        w*=255;
        if(w>255)
            return 255;
        if(w<0)
            return 0;
        return (int)Math.round(w);
    }

    public int f_oblicz_w2(int b) //
    {

//        v’(x,y)= (127-c)/127)*v(x,y) dla v(x,y)<127
//        (127-c)/127)*v(x,y)+2c dla v(x,y)≥127
//        b)
//        v’(x,y)= (127/(127+c))*v(x,y) dla v(x,y)<127+c
//            (127*v(x,y)+255*c)/(127)+c) dla v(x,y)>127-c
//        127 dla pozostałych

        int col=b;
//        if (x<0) x+=255;
//        double b1=x;
//        b1/=255;
        double w=b;
        if(Var_c>0 && Var_c<127)
        {
            if (col<127){
//                w= ( (127-Var_c)/127)*col;
                w = (1 - (Var_c/127))*col;
            }
            else if (col>=127){
//                w=( ( (127-Var_c) /127 )*col) + (2*Var_c) ;
                w=(1 - (Var_c/127))*col;
                w+=(2*Var_c);
            }
        }
        else if ((Var_c>-127) && (Var_c < 0)){
            if(col>127+Var_c)
                w=( (127/(127+Var_c))*col );
            else if(col<127-Var_c)
                w=((127*col+255*(Var_c))/(127)+Var_c) ;
            else
                w=127;
        }

//        w*=255;
        if(w>255)
            return 255;
        if(w<0)
            return 0;
        return (int)Math.round(w);
    }

    public int f_linio(int b)
    {
        int x=b;
        if (x<0) x+=255;
        double b1=x;
        b1/=255;
        double w=(alfav*b1);
        w*=255;
        w+=b_wspolv;
        if(w>255)
            return 255;
        if(w<0)
            return 0;
        return (int)Math.round(w);
    }

    public void t_Oblicz(){
        BufferedImage image_ = deepCopy(orgimg);
        int opcja=tabs.getSelectedIndex();
        int[] tablica_red = new int[256];
        int[] tablica_green = new int[256];
        int[] tablica_blue = new int[256];
        int[][] tablica_histogram = new int[3][256];
        int[][] M = new int[][] { {1,1,1}, {1,1,1}, {1,1,1}};
        for(int i=1; i<image_.getHeight()-1; i++){
            for(int j=1; j<image_.getWidth()-1; j++){

                Color co = new Color(image_.getRGB(j, i));
                int r = co.getRed();
                tablica_red[r]++;
                int g = co.getGreen();
                tablica_green[g]++;
                int b = co.getBlue();
                tablica_blue[b]++;
                int red, green, blue;
                int pomoc_r=0,pomoc_g=0,pomoc_b=0;

                switch (opcja){
                    case 0:
                        red= f_oblicz_w2(r);
                        green= f_oblicz_w2(g);
                        blue= f_oblicz_w2(b);
                        break;
                    case 1:
                        red= f_oblicz(r);
                        green= f_oblicz(g);
                        blue= f_oblicz(b);
                        break;
//                    case 2:
                        //
//                        for(int k=-1;k<=1;k++){
//                            for(int l=-1;l<=1;l++){
//                                Color co1 = new Color(image_.getRGB(j+k, i+l));
//                                pomoc_r+=co1.getRed()*M[k+1][l+1];
//                               //         Image[i+k][j+l]*
//                                pomoc_g+=co1.getGreen()*M[k+1][l+1];
//                                pomoc_b+=co1.getBlue()*M[k+1][l+1];
//                            }
//                        }
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
                        break;
                }
                Color newColor = new Color(red, green, blue);
                image_.setRGB( j,i,newColor.getRGB() );
            }
        }
        BufferedImage image1 = new BufferedImage(270, 256, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image1.createGraphics();
        g2d.setColor(Color.red);
        float alpha = 0.3f;
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
//        for (int i =0; i<256;i++)
//            g2d.drawLine(i,256, i, 256-tablica_red[i]);
//        g2d.setColor(Color.green);
//        for (int i =0; i<256;i++)
//            g2d.drawLine(i,256, i, 256-tablica_green[i]);
//        g2d.setColor(Color.blue);
//        for (int i =0; i<256;i++)
//            g2d.drawLine(i,256, i, 256-tablica_blue[i]);
        imagepanel.setImage(image_);
        int xOffset = 5;
        int yOffset = 5;
        int width = image1.getWidth() - 1 - (xOffset * 2);
        int height = image1.getHeight() - 1 - (yOffset * 2);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(xOffset, yOffset, width+3, height);
        int MIN_BAR_WIDTH;
        int barWidth = Math.max(3, (int) Math.floor((float) width / (float) tablica_red.length ) );
//        System.out.println("width = " + width + ";"+height+" size = " + tablica_red.length + "; barWidth = " + barWidth);
        int maxValue=0;
        for (int i = 0; i < tablica_red.length; i++) {
            maxValue = Math.max(maxValue, tablica_red[i]);
        }
        //System.out.println(ismax);
        int xPos = xOffset;
        for (int i = 0; i < tablica_red.length; i++) {
            int value = tablica_red[i];
            int barHeight = Math.round(((float) value / (float) maxValue) * height);
            g2d.setColor(new Color(255, i, i));
            int yPos = height + yOffset - barHeight;
//Rectangle bar = new Rectangle(xPos, yPos, barWidth, barHeight);
            Rectangle2D bar = new Rectangle2D.Float(
                    xPos, yPos, barWidth, barHeight);
            g2d.fill(bar);
            //g2d.setColor(Color.DARK_GRAY);
            g2d.draw(bar);
            xPos += barWidth;
        }

        imagepanel1.setImage(image1);

        BufferedImage image2 = new BufferedImage(260, 256, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dg = image2.createGraphics();
        g2dg.setColor(Color.green);
        g2dg.setComposite(alcom);
        width = image2.getWidth() - 1 - (xOffset * 2);
        height = image2.getHeight() - 1 - (yOffset * 2);
        g2dg.setColor(Color.DARK_GRAY);
        g2dg.drawRect(xOffset, yOffset, width, height);
        MIN_BAR_WIDTH = 3;
        barWidth = Math.max(MIN_BAR_WIDTH, (int) Math.floor((float) width / (float) tablica_green.length ) );
//        System.out.println("width = " + width + ";"+height+" size = " + tablica_green.length + "; barWidth = " + barWidth);
        maxValue=0;
        for (int i = 0; i < tablica_green.length; i++) {
            maxValue = Math.max(maxValue, tablica_green[i]);
        }
        //System.out.println(ismax);
        xPos = xOffset;
        for (int i = 0; i < tablica_green.length; i++) {
            int value = tablica_green[i];
            int barHeight = Math.round(((float) value / (float) maxValue) * height);
            g2dg.setColor(new Color(i, 255, i));
            int yPos = height + yOffset - barHeight;
//Rectangle bar = new Rectangle(xPos, yPos, barWidth, barHeight);
            Rectangle2D bar = new Rectangle2D.Float(
                    xPos, yPos, barWidth, barHeight);
            g2dg.fill(bar);
            //g2d.setColor(Color.DARK_GRAY);
            g2dg.draw(bar);
            xPos += barWidth;
        }

        imagepanel2.setImage(image2);


        BufferedImage image3 = new BufferedImage(260, 256, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2db = image3.createGraphics();
        g2db.setColor(Color.blue);
        g2db.setComposite(alcom);
        width = image3.getWidth() - 3 - (xOffset * 2);
        height = image3.getHeight() - 3 - (yOffset * 2);
        g2db.setColor(Color.DARK_GRAY);
        g2db.drawRect(xOffset, yOffset, width, height);
        MIN_BAR_WIDTH = 3;
        barWidth = Math.max(MIN_BAR_WIDTH, (int) Math.floor((float) width / (float) tablica_blue.length ) );
//        System.out.println("width = " + width + ";"+height+" size = " + tablica_blue.length + "; barWidth = " + barWidth);
        maxValue=0;
        for (int i = 0; i < tablica_blue.length; i++) {
            maxValue = Math.max(maxValue, tablica_blue[i]);
        }
        xPos = xOffset;
        for (int i = 0; i < tablica_blue.length; i++) {
            int value = tablica_blue[i];
            int barHeight = Math.round(((float) value / (float) maxValue) * height);
            g2db.setColor(new Color(i, i, 255));
            int yPos = height + yOffset - barHeight;
//Rectangle bar = new Rectangle(xPos, yPos, barWidth, barHeight);
            Rectangle2D bar = new Rectangle2D.Float(
                    xPos, yPos, barWidth, barHeight);
            g2db.fill(bar);
            g2db.draw(bar);
            xPos += barWidth;
        }

        imagepanel3.setImage(image3);

        img.repaint();
        g2d.dispose();
    }

    protected class Graph extends JPanel {

        protected static final int MIN_BAR_WIDTH = 4;
        private Map<Integer, Integer> mapHistory;

        public Graph(Map<Integer, Integer> mapHistory) {
            this.mapHistory = mapHistory;
            int width = (mapHistory.size() * MIN_BAR_WIDTH) + 11;
            Dimension minSize = new Dimension(width, 128);
            Dimension prefSize = new Dimension(width, 256);
            setMinimumSize(minSize);
            setPreferredSize(prefSize);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (mapHistory != null) {
                int xOffset = 5;
                int yOffset = 5;
                int width = getWidth() - 1 - (xOffset * 2);
                int height = getHeight() - 1 - (yOffset * 2);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect(xOffset, yOffset, width, height);
                int barWidth = Math.max(MIN_BAR_WIDTH,
                        (int) Math.floor((float) width
                                / (float) mapHistory.size()));
                System.out.println("width = " + width + "; size = "
                        + mapHistory.size() + "; barWidth = " + barWidth);
                int maxValue = 0;
                for (Integer key : mapHistory.keySet()) {
                    int value = mapHistory.get(key);
                    maxValue = Math.max(maxValue, value);
                }
                int xPos = xOffset;
                for (Integer key : mapHistory.keySet()) {
                    int value = mapHistory.get(key);
                    int barHeight = Math.round(((float) value
                            / (float) maxValue) * height);
                    g2d.setColor(new Color(key, key, key));
                    int yPos = height + yOffset - barHeight;
                    Rectangle bar = new Rectangle(xPos, yPos, barWidth, barHeight);
                    //Rectangle2D bar = new Rectangle2D.Float(xPos, yPos, barWidth, barHeight);
                    g2d.fill(bar);
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.draw(bar);
                    xPos += barWidth;
                }
                g2d.dispose();
            }}}


    public void saveImage()
    {
        int retval = opendialog.showSaveDialog(this);
        if (retval==JFileChooser.CANCEL_OPTION) return;
        try {
            ImageIO.write((RenderedImage)(imagepanel.getImage()), "jpg", opendialog.getSelectedFile());
        } catch (IOException e) { System.err.println("Exception!!!");}
    }

    public static void main(String[] args) {

        Lab6 o = new Lab6();
    }
}

