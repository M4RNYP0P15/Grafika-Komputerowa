import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class PrzeksztalcanieObrazu extends JFrame implements ActionListener {
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
    JLabel alphal=new JLabel("alpha= 1.00");
    JSlider alpha = new JSlider(JSlider.HORIZONTAL,0,255,255);

    JPanel con_slider_a = new JPanel();
    JLabel b_wspoll = new JLabel("b= 0");
    JSlider b_wspol = new JSlider(JSlider.HORIZONTAL,-255,255,0);
    JLabel alfal=new JLabel("a= 1.00");
    JSlider alfa = new JSlider(JSlider.HORIZONTAL,-100,100,1);

    JPanel img = new JPanel();

    ImagePanel imagepanel = new ImagePanel();
    JPanel imagecontainer = new JPanel();

    double gammav = 1.0;
    double alphav = 1.0;
    int b_wspolv = 0;
    double alfav=1.0;
//    byte LUT[] = new byte[256];

    public PrzeksztalcanieObrazu()
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
                alpha.setValue(255);
                gammav=1.0;
                alphav=1.0;
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
                alfav/=100;
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
                alphav=alpha.getValue();
                alphav/=alpha.getMaximum();
                alphav=Math.round(alphav*100);
                alphav/=100;
                String s = "Alfa " + Double.toString(alphav);
                while (s.length()<10) s+="0";
                alphal.setText(s);
                alphal.repaint();
            }
        });
        img.setBorder(BorderFactory.createEtchedBorder());
        img.setLayout(new BorderLayout());
        img.add(new JScrollPane(imagecontainer));
        imagecontainer.add(imagepanel);


        con_slider.setBorder(BorderFactory.createEtchedBorder());
        con_slider.setLayout(new GridBagLayout());
        con_slider.add(gammal,new GridBagConstraints(0,0,1,1,1.0,1.0,
                GridBagConstraints.WEST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));
        con_slider.add(gamma, new GridBagConstraints(1,0,1,1,1.0,1.0,
                GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));
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
        tabs.insertTab("Potegowa", null, potegowa, "Oblicz potegowo", 0);
        tabs.insertTab("Liniowa", null, liniowa, "Oblicz liniowa", 1);
        cont_opcje.add(ctl, BorderLayout.NORTH);
        cont_opcje.add(tabs, BorderLayout.SOUTH);
        this.getContentPane().add(img, BorderLayout.CENTER);
        this.getContentPane().add(cont_opcje, BorderLayout.SOUTH);

        this.setSize(800,400);
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Przeksztalcenia obrazu");
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

    public int f_Potegowa(int b) //
    {
        int x=b;
        if (x<0) x+=255;
        double b1=x;
        b1/=255;
        double w=alphav*Math.pow(b1,gammav);
        w*=255;
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
//        for(int i=0;i<256;i++) LUT[i]=transformPixel(i); // oszczedzanie obliczen dla wiekszych obrazow
        for(int i=0; i<image_.getHeight(); i++){
            for(int j=0; j<image_.getWidth(); j++){

                Color co = new Color(image_.getRGB(j, i));
                    int r = co.getRed();
                    int g = (co.getGreen());
                    int b = (co.getBlue());
                    int red, green, blue;

                switch (opcja){
                    case 0:
                        red= f_Potegowa(r);
                        green= f_Potegowa(g);
                        blue= f_Potegowa(b);
                        break;
                    case 1:
                        red= f_linio(r);
                        green= f_linio(g);
                        blue= f_linio(b);
                        break;
                    default:
                        red=r;
                        blue=b;
                        green=g;
                        break;
                }
                Color newColor = new Color((int)red, (int)green,(int)blue);
                image_.setRGB( j,i,newColor.getRGB() );
            }
        }
        imagepanel.setImage(image_);
        img.repaint();
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

        PrzeksztalcanieObrazu o = new PrzeksztalcanieObrazu();
    }
}
