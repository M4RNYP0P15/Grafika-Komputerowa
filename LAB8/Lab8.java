import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.util.Map;
import javax.imageio.*;

public class Lab8 extends JFrame {
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
                    repaint();
                }
            });

        }
        public BufferedImage getImage() { return image; }
        public Dimension getPreferredSize(){
            try {
                return new Dimension(image.getWidth(),image.getHeight());
            } catch (Exception e) {
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
    JComboBox combo1 = new JComboBox();
    JComboBox combo2 = new JComboBox();

    JPanel con_slider_a = new JPanel();
    JLabel b_wspoll = new JLabel("r= 1");
    JSlider b_wspol = new JSlider(JSlider.HORIZONTAL,1,15,1);

    JPanel img = new JPanel();

    ImagePanel imagepanel = new ImagePanel();
    //ImagePanel imagepanel1 = new ImagePanel();

    JPanel imagecontainer = new JPanel();

    int b_wspolv = 1;


    public Lab8()
    {
        Container cont = new Container();//this.getContentPane();
        combo1.addItem("Robertsa (gradientu pionowego)");
        combo1.addItem("Robertsa (gradientu poziomego)");
        combo1.addItem("Prewitta (gradientu pionowego)");
        combo1.addItem("Prewitta (gradientu poziomego)");
        combo1.addItem("Sobela (gradientu pionowego)");
        combo1.addItem("Sobela (gradientu poziomego)");
        combo1.addItem("Laplace’a 1");
        combo1.addItem("Laplace’a 2");
        combo1.addItem("Laplace’a 3");
        combo2.addItem("Mediana");
        combo2.addItem("Min");
        combo2.addItem("Max");
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
                b_wspolv=3;
                t_Oblicz();
            }
        });
        b_wspol.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                b_wspolv=b_wspol.getValue();
                b_wspoll.setText("r= "+ b_wspolv);
                t_Oblicz();
            }
        });
        combo1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t_Oblicz();
            }
        });
        combo2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t_Oblicz();
            }
        });
        img.setBorder(BorderFactory.createEtchedBorder());
        img.setLayout(new BorderLayout());
        img.add(new JScrollPane(imagecontainer));
        imagecontainer.add(imagepanel);
        //imagecontainer.add(imagepanel1);

        con_slider.setBorder(BorderFactory.createEtchedBorder());
        con_slider.setLayout(new GridBagLayout());
        con_slider.add(combo1,new GridBagConstraints(0,1,1,1,1.0,1.0,
                GridBagConstraints.WEST,
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
        con_slider_a.add(combo2,new GridBagConstraints(0,1,1,1,1.0,1.0,
                GridBagConstraints.WEST,
                GridBagConstraints.NONE,
                new Insets(2,2,2,2),0,0));
        potegowa.add(con_slider, BorderLayout.CENTER);
        liniowa.add(con_slider_a, BorderLayout.CENTER);
        ctl.setBorder(BorderFactory.createEtchedBorder());
        ctl.add(prev);
        ctl.add(open);
        ctl.add(save);
//        ctl.add(reset);

        tabs.insertTab("Filtry wyostrzające", null, potegowa, "Wybierz filtr wyostrzający...", 0);
        tabs.insertTab("Filtry statyczne", null, liniowa, "Wybierz promien i rodzaj filtru statycznego", 1);
        cont_opcje.add(ctl, BorderLayout.NORTH);
        cont_opcje.add(tabs, BorderLayout.SOUTH);
        this.getContentPane().add(img, BorderLayout.CENTER);
        this.getContentPane().add(cont_opcje, BorderLayout.SOUTH);

        this.setSize(900,600);
        this.repaint();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("LAB 8");
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
            this.setTitle("LAB 8 ("+opendialog.getSelectedFile().getPath());
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
        int[][] M;
        switch (combo1.getSelectedIndex()){
            case 0:
                M = new int[][] { {0,0,0}, {0,1,-1}, {0,0,0}};
                break;
            case 1:
                M = new int[][] { {0,0,0}, {0,1,0}, {0,-1,0}};
                break;
            case 2:
                M = new int[][] { {1,1,1}, {0,0,0}, {-1,-1,-1}};
                break;
            case 3:
                M = new int[][] { {1,0,-1}, {1,0,-1}, {1,0,-1}};
                break;
            case 4:
                M = new int[][] { {1,2,1}, {0,0,0}, {-1,-2,-1}};
                break;
            case 5:
                M = new int[][] { {1,0,-1}, {2,0,-2}, {1,0,-1}};
                break;
            case 6:
                M = new int[][] { {0,-1,0}, {-1,4,-1}, {0,-1,0}};
                break;
            case 7:
                M = new int[][] { {-1,-1,-1}, {-1,8,-1}, {-1,-1,-1}};
                break;
            case 8:
                M = new int[][] { {-2,1,-2}, {1,4,1}, {-2,1,-2}};
                break;
            default:
                M = new int[][] { {0,0,0}, {0,1,-1}, {0,0,0}};
        }
        Color newColor;
        int width = image_.getWidth();
        int height = image_.getHeight();
        int r=b_wspolv;
        switch (opcja){
            case 0:
                for(int i=1; i<height - 1; i++) {
                    for (int j = 1; j < width - 1; j++) {
                        int pomoc_r = 0, pomoc_g = 0, pomoc_b = 0;
                        for (int k = -1; k <= 1; k++) {
                            for (int l = -1; l <= 1; l++) {
                                Color c = new Color(orgimg.getRGB(j + k, i + l));

                                pomoc_r += (c.getRed()) * M[k + 1][l + 1];
                                pomoc_g += (c.getGreen()) * M[k + 1][l + 1];
                                pomoc_b += (c.getBlue()) * M[k + 1][l + 1];
                            }
                        }
                        pomoc_r=limit(pomoc_r);
                        pomoc_g=limit(pomoc_g);
                        pomoc_b=limit(pomoc_b);

                        newColor = new Color(pomoc_r, pomoc_g, pomoc_b);
                        image_.setRGB(j, i, newColor.getRGB());
                    }
                }
                break;
            case 1:
                int wiel= 2*r +1;
                wiel*=wiel;
                int max = wiel-1;
                int median = wiel/2;
                int[] R=new int[wiel];
                int[] G=new int[wiel];
                int[] B=new int[wiel];
                Color[] pixel=new Color[wiel+1];
                for(int i=r; i<height -r; i++) {
                    for (int j = r; j < width - r; j++) {

                        int ko = 0;
                        for (int l = -r; l <= r; l++) {
                            for (int m = -r; m <= r; m++) {
                                pixel[ko]= new Color(orgimg.getRGB(j+l,i+m));
                                ko++;
                            }
                        }

                        for (int k = 0; k < wiel; k++) {
                            R[k]=pixel[k].getRed();
                            G[k]=pixel[k].getGreen();
                            B[k]=pixel[k].getBlue();
                        }
                        Arrays.sort(R);
                        Arrays.sort(G);
                        Arrays.sort(B);
                        switch (combo2.getSelectedIndex()){
                            case 0:
                                newColor = new Color(R[median],B[median],G[median]);
                                break;
                            case 1:
                                newColor = new Color(R[0],B[0],G[0]); //min
                                break;
                            case 2:
                                newColor = new Color(R[max],B[max],G[max]);
                                break;
                            default:
                                newColor = new Color(R[max],B[max],G[max]);
                        }
                        image_.setRGB(j, i, newColor.getRGB());
                    }
                }
                break;
                default:
        }
        imagepanel.setImage(image_);
        img.validate();
        img.repaint();
        imagecontainer.validate();
        this.repaint();
    }

    int limit(int a){
        a=Math.abs(a);
        if(a < 0)
            return 0;
        if(a > 255)
            return 0;
        return a;
    }
//    int limit(int a){
//        //a=Math.abs(a);
//        if(a < -255)
//            return 0;
//        if(a > 255)
//            return 255;
//        return (int) Math.abs(a);
//    }

    public void saveImage()
    {
        int retval = opendialog.showSaveDialog(this);
        if (retval==JFileChooser.CANCEL_OPTION) return;
        try {
            ImageIO.write((RenderedImage)(imagepanel.getImage()), "jpg", opendialog.getSelectedFile());
        } catch (IOException e) { System.err.println("Exception!!!");}
    }

    static public void main(String args[]) throws Exception
    {
        Lab8 obj = new Lab8();
    }
}