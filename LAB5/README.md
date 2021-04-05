![image](https://user-images.githubusercontent.com/38810840/113569573-08232c80-9613-11eb-886e-21cab6118e4c.png)

Klasycznie pętla po obrazie (pixelach) z podzialem na kolory(RGB) w zależności od wyboru ((podajemy wartosc danego koloru)):
    Potegowa :

    public int f_Potegowa(int b)
    {
        int x=b;
        if (x<0) x+=255;
        double b1=x;
        b1/=255;
        double w=alphav*Math.pow(b1,gammav);
        w*=255;
        return (int)Math.round(w);
    }

  Liniowa:

    public int f_linio(int b) // mozna bylo czesc rzeczy pominac
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
