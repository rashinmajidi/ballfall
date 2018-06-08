package edu.wccnet.rmajidi.jh7_rmajidi;

import java.util.Random;

/**
 * Created by rashin on 4/15/17.
 */
enum Colors {Yellow, Green_light, Green_Dark, Blue_Dark, Blue_light,Orange, Light_Pink,Pink,
    Purple_Light,Purple_Dark}

public class Floor {
    public int positionY;
    int holePosition;
    int holeWidth;
    int width;
    int height;
    Colors color;
    int r,g,b;
    private Random rand=new Random();

    public Floor(int positionY, int width, int height) {
        createRandomColor();
        this.width = width;
        this.height = height;
        this.positionY = positionY;
        setTheHole();
    }

    private int justifyPosition(int positionY) {

        if (positionY < 0) {
            createRandomColor();
            int delta = 0 - positionY;
            positionY = height - delta;
            setTheHole();
        }
        return positionY;
    }

    private void setTheHole() {
        Random rand = new Random();
        holeWidth = rand.nextInt(width / 4) + 150;
        holePosition = rand.nextInt(width - holeWidth);
    }

    public void setPositionY(int positionY) {
        this.positionY = justifyPosition(positionY);
    }

    private void createRandomColor() {
        int r=rand.nextInt(12);
        switch (r){
            case 0:
                color=Colors.Yellow;
                setRGB(245, 237, 27);
                break;
            case 1:
                color=Colors.Blue_Dark;
                setRGB(122, 200, 255);
                break;
            case 2:
                color=Colors.Blue_light;
                setRGB(122, 255, 251);
                break;
            case 3:
                color=Colors.Green_Dark;
                setRGB(34, 142, 72);
                break;
            case 4:
                color=Colors.Green_light;
                setRGB(194, 255, 122);
                break;
            case 5:
                color=Colors.Orange;
                setRGB(245, 132, 27);
                break;
            case 6:
                color=Colors.Pink;
                setRGB(255, 122, 122);
                break;
            case 7:
                color=Colors.Purple_Light;
                setRGB(122, 129, 255);
                break;
            case 8:
                color=Colors.Light_Pink;
                setRGB(255, 159, 122);
                break;
            case 9:
                color=Colors.Purple_Dark;
                setRGB(171, 122, 255);
                break;
            case 10:
                color=Colors.Pink;
                setRGB(255, 109, 176);
                break;
            case 11:
                color=Colors.Yellow;
                setRGB(255, 243, 12);
                break;
        }


    }

    private void setRGB(int r, int g, int b) {
        this.r=r;
        this.b=b;
        this.g=g;
    }
}
