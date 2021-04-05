import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class MieszanieObrazu extends JFrame implements ActionListener {
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
    BufferedImage orgimg2;
    JPanel cont_opcje = new JPanel();

    JPanel cont_slider = new JPanel();
    JComboBox cbox = new JComboBox(new String[]{"Suma","Odejmowanie", "Różnica", "Mnożenie", "Mnożenie odwrotności", "Negacja", "Ciemniejsze", "Jaśniejsze", "Wyłączenie", "Nakładka", "Ostre światło", "Łagodne światło", "Rozcieńczenie", "Wypalanie", "Reflect mode", "Przezroczystość"});

    JPanel ctl = new JPanel();
    JButton prev = new JButton("Podglad");
    JButton open = new JButton("Wybierz 1 obraz");
    JButton open1 = new JButton("Wybierz 2 obraz");
    JButton save = new JButton("Zapisz");

    JPanel con_slider = new JPanel();
    JLabel alphal=new JLabel("Alfa= 1.00");
    JSlider alpha = new JSlider(JSlider.HORIZONTAL,0,100,100);

    JPanel img = new JPanel();

    ImagePanel imagepanel = new ImagePanel();
    JPanel imagecontainer = new JPanel();

    double alphav = 1.0;

    public MieszanieObrazu()
    {
        Container cont = new Container();//this.getContentPane();
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readImage();
            }
        });
        open1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readImage1();
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

        alpha.addChangeListener(new ChangeListener( ) {
            public void stateChanged(ChangeEvent e){
                alphav=alpha.getValue();
                alphav/=alpha.getMaximum();
                alphal.setText("Alfa= " + alphav);
                alphal.repaint();
            }
        });
        cbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (cbox.getSelectedIndex()){
                    case 15:
                        if(! con_slider.isVisible())
                            con_slider.setVisible(true);
                        break;
                    default:
                        if(con_slider.isVisible())
                            con_slider.setVisible(false);
                        break;
                }
            }
        });
        img.setBorder(BorderFactory.createEtchedBorder());
        img.setLayout(new BorderLayout());
        img.add(new JScrollPane(imagecontainer), BorderLayout.CENTER);
        imagecontainer.add(imagepanel);

        con_slider.setBorder(BorderFactory.createEtchedBorder());
        con_slider.setLayout(new GridBagLayout());
        con_slider.add(alphal,new GridBagConstraints(0,0,1,1,1.0,1.0,
                GridBagConstraints.WEST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));
        con_slider.add(alpha, new GridBagConstraints(1,0,1,1,1.0,1.0,
                GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));

        cont_slider.add(cbox, BorderLayout.NORTH);
        cont_slider.add(con_slider, BorderLayout.EAST);
        con_slider.setVisible(false);
        ctl.setBorder(BorderFactory.createEtchedBorder());
        ctl.add(prev);
        ctl.add(open);
        ctl.add(open1);
        ctl.add(save);
        cont_opcje.setLayout(new BorderLayout());
        cont_opcje.add(ctl, BorderLayout.NORTH);
        cont_opcje.add(cont_slider, BorderLayout.SOUTH);
        this.getContentPane().add(img, BorderLayout.CENTER);
        this.getContentPane().add(cont_opcje, BorderLayout.SOUTH);

        this.setSize(800,400);
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Mieszanie obrazu");
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

    public void readImage1(){
        BufferedImage image;

        int retval = opendialog.showOpenDialog(this);
        if (retval==JFileChooser.CANCEL_OPTION) return;
        try {
            File input = new File(opendialog.getSelectedFile().getPath());
            image = ImageIO.read(input);
//            Graphics2D g2 = image.createGraphics();
//            g2.drawImage(image,0,0,null);
            orgimg2=image;

//            imagepanel.setImage(image);
//            imagepanel.setSize(image.getWidth(this),image.getHeight(this));
//            img.repaint();
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
        BufferedImage image_1 = orgimg2;
        int opcja=cbox.getSelectedIndex();
        if(image_.getHeight() > orgimg2.getHeight() || image_.getWidth() > orgimg2.getWidth())
            System.out.println("Problem z wielkoscia");
        else
        {
            for(int i=0; i<image_.getHeight(); i++){
                for(int j=0; j<image_.getWidth(); j++){

                    Color co = new Color(image_.getRGB(j, i));
                    int r = co.getRed();
                    int g = co.getGreen();
                    int b = co.getBlue();

                    Color co2 = new Color(image_1.getRGB(j, i));
                    int r2 = co2.getRed();
                    int g2 = co2.getGreen();
                    int b2 = co2.getBlue();
                    int red, green, blue;


                    red= f_0(r,r2, opcja);
                    green= f_0(g,g2, opcja);
                    blue= f_0(b,b2,opcja);

                    Color newColor = new Color((int)red, (int)green,(int)blue);
                    image_.setRGB( j,i,newColor.getRGB() );
                }
            }
            imagepanel.setImage(image_);
            img.repaint();
        }
    }
    public int f_0(int a, int b, int o){
        int x=a;
        int y=b;
        if (x<0) x+=255;
        if (y<0) y+=255;
        double b1=x;
        double b2=y;
        b1/=255;
        b2/=255;
        double w;
        switch (o){
            case 0:
                w=b1+b2;
                break;
            case 1:
                w=(b1+b2)-1;
                break;
            case 2:
                w=b1-b2;
                break;
            case 3:
                w=b1*b2;
                break;
            case 4:
                w=(1-(1-b1)*(1-b2));
                break;
            case 5:
                w=1-(b1-b2);
                break;
            case 6:
                if(b1<b2)
                    w=b1;
                else
                    w=b2;
                break;
            case 7:
                if(b1>b2)
                    w=b1;
                else
                    w=b2;
                break;
            case 8:
                w=(b1+b2)-(2*b1*b2);
                break;
            case 9:
                if(b1<0.5)
                    w=(2*b1*b2);
                else
                    w=(1-(2*(1-b1)*(1-b2)));
                break;
            case 10:
                if(b2<0.5)
                    w=(2*b1*b2);
                else
                    w=(1-(2*(1-b1)*(1-b2)));
                break;
            case 11:
                if(b2<0.5)
                    w=2*b1*b2+b1*b1*(1-2*b2);
                else
                    w=Math.sqrt(b1)*(2*b2-1)+(2*b1)*(1-b2);
                break;
            case 12:
                w=b1/(1-b2);
                break;
            case 13:
                w=1-(1-b1)/b2;
                break;
            case 14:
                w=b1*b1/(1-b2);
                break;
            case 15:
                w=(1-alphav)*b2+(alphav*b1);
                break;
            default:
                w=b1;
                break;
        }
        w*=255;
        if(w>255)
            return 255;
        if(w<0)
            return 0;
        return (int)Math.round(w);
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
        MieszanieObrazu o = new MieszanieObrazu();
    }
}
