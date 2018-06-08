package edu.wccnet.rmajidi.jh7_rmajidi;

import java.util.ArrayList;

/**
 * Created by rashin on 4/16/17.
 */

public class GameInfo {
    Ball ball;
    ArrayList<Floor> floors;

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setFloors(ArrayList<Floor> floors) {
        this.floors = floors;
    }

    public ArrayList<Floor> getFloors() {
        return floors;
    }

    public Ball getBall() {
        return ball;
    }
}
