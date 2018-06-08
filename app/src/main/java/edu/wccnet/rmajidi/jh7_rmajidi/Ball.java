package edu.wccnet.rmajidi.jh7_rmajidi;

/**
 * Created by rashin on 4/15/17.
 */

public class Ball {
    int radius=30;
    int x=0;
    int y=0;
    int speed=70;
    Ball(int x, int y){
        this.x=x;
        this.y=y;
    }

    public void setX(int x) {
        this.x=x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
