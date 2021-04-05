# Przeksztalcanie obrazu (plik o takiej samej nazwie)
![image](https://user-images.githubusercontent.com/38810840/113569573-08232c80-9613-11eb-886e-21cab6118e4c.png)

Klasycznie pętla po obrazie (pixelach) z podzialem na kolory(RGB) w zależności od wyboru (liniowa/potegowa)podajemy wartosc danego koloru do funkcji:

### 1. Potegowa :

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

### 2. Liniowa:

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
    
# Mieszanie obrazu    
![image](https://user-images.githubusercontent.com/38810840/113571521-d3b16f80-9616-11eb-9a22-30d63a808ce5.png)

#### Podobnie jak poprzednio, funkcja "bierze" 2 wartosci kolorów (z 1 obrazka np red i z 2) oraz wartosc o - opcja (0-sumowanie itd)
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
