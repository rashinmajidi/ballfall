package edu.wccnet.rmajidi.jh7_rmajidi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import edu.wccnet.rmajidi.jh5_rmajidi_wordmanager.WordFileManager;
import java.util.ArrayList;

/**
 * Created by rashin on 4/15/17.
 */

public class GameView extends View implements View.OnTouchListener {
    MyAsync myAsync;
    DialogBox gameOverDialogBox;
    int velocity = 10;
    int height;
    Paint arcpaint;
    int width;
    int highScore=0;
    WordFileManager fileManager;
    int startY = 0;
    int endY = 0;
    int holeStart = 0;
    int holeEnd = 0;
    Ball ball;
    Paint floorPaint;
    Paint textPaint;
    Paint ballPaint;
    int ball_velocityX = 0;
    Context context;
    MediaPlayer mediaPlayer;
    GameView gameview;
    GameInfo gameInfo = new GameInfo();
    int score = 0;
    ArrayList<Floor> floors = new ArrayList<Floor>();

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context,
            AttributeSet ats,
            int defaultStyle) {
        super(context, ats, defaultStyle);
    }

    private void reset() {
        floors.clear();
        score=0;
        velocity=10;
        ball_velocityX=0;
    }
    public void startThread(Context context,GameView gameview) {
        Log.d("mine", "startThread: ");
        reset();
        this.context=context;
        this.gameview=gameview;
        invalidate();
        myAsync = new MyAsync();

        myAsync.execute(startY, endY);
    }



    public void stopThread() {
        myAsync.cancel(true);
        mediaPlayer.pause();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Draw the background...
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(1);
        textPaint.setColor(Color.rgb(255, 242, 170));
        textPaint.setTextSize(50);
        setBackgroundColor(Color.BLACK);
        //canvas.drawPaint(textPaint);
        canvas.drawText("Score: " + score, 10, 50, textPaint);
        String text="High Score: "+highScore;
        int textWidth = (int)textPaint.measureText(text);
        canvas.drawText(text, width-textWidth, 50, textPaint);
        width = getWidth();
        height = getHeight();
        startY = height / 3;
        endY = (height / 3) + 30;

        if (floors.isEmpty()) {
            return;
        }
        for (int i = 0; i < 8; i++) {

            floorPaint.setColor(Color.rgb(floors.get(i).r, floors.get(i).g, floors.get(i).b));
            startY = floors.get(i).positionY;
            holeStart = floors.get(i).holePosition;
            canvas.drawLine(0, startY, holeStart - 60, startY, floorPaint);
            canvas.drawArc(holeStart - 70 - 30, startY, holeStart - 30, startY + 70, -90, 90, false,
                    floorPaint);
            holeEnd = floors.get(i).holePosition + floors.get(i).holeWidth;
            canvas.drawRoundRect(holeEnd + 30, startY, width, startY + 30, 70, 70, floorPaint);
        }
        if (ball.y >= 0) {
            canvas.drawCircle(ball.x, ball.y - (ball.radius + 10), ball.radius, ballPaint);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int index = MotionEventCompat.getActionIndex(event);

        if ((event.getAction() & MotionEvent.ACTION_MASK) ==
                (MotionEvent.ACTION_DOWN)) {
            //  Log.d("Rash", "onTouch: " + index);
            float x = event.getX(index);
            if (x <= width / 2) {
                ball_velocityX = -40;
            } else {
                ball_velocityX = 40;
            }
        } else if ((event.getAction() & MotionEvent.ACTION_MASK) ==
                MotionEvent.ACTION_POINTER_DOWN) {
            //   Log.d("Rash", "onTouch: " + index);
            float x = event.getX(index);
            if (x <= width / 2) {
                ball_velocityX = -40;
            } else {
                ball_velocityX = 40;
            }
        } else if ((event.getAction() & MotionEvent.ACTION_MASK) ==
                MotionEvent.ACTION_UP) {
            // ball_velocityX = 0;
        }
        return true;
    }

    //Inner Class
    class MyAsync extends AsyncTask<Integer, GameInfo, Boolean> {
        int outOfWindow = 0;

        // Excution in the Thread
        protected Boolean doInBackground(Integer... parm) {
            int counter = 0;
            boolean doOnce = true;
            Log.d("rash", "doInBackground: ");

            while (true) {
                if (isCancelled()) {
                    break;
                }
                if (width == 0 && height == 0) {
                    continue;
                } else if (doOnce) {
                    Log.d("rash", "doInBackground: doOnce");
                    init();
                    doOnce = false;
                }
                ball.setY(ball.y + ball.speed);
                if (ball.x + ball_velocityX >= width || ball.x + ball_velocityX <= 0) {
                    ball_velocityX = 0;
                }
                ball.setX(ball.x + ball_velocityX);
                if (ball.y >= height) {
                    outOfWindow = 70;
                } else {
                    outOfWindow = 0;
                }
                for (int i = 0; i < 8; i++) {
                    int newPos = floors.get(i).positionY - (velocity + outOfWindow);
                    if (newPos < 0) {
                        score++;
                    }
                    floors.get(i).setPositionY(newPos);
                }
                if (!hitFloor()) {
                    if (outOfWindow != 0) {
                        ball.setY(height);
                    }
                }
                if (counter % 40 == 0) {
                    velocity += 1;
                }

                if (isGameOver()) {
                    return false;
                }
                counter++;
                gameInfo.setBall(ball);
                gameInfo.setFloors(floors);
                publishProgress(gameInfo);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
            }
            return true;
        }

        private boolean isGameOver() {
            if (ball.y - ball.radius <= 0) {
                return true;
            }
            return false;
        }

        private boolean hitFloor() {
            int a = ball.y - ball.speed;
            int b = ball.y;

            for (int i = 0; i < 8; i++) {
                int c = floors.get(i).positionY;
                int d = floors.get(i).positionY + velocity + outOfWindow;
                if ((c <= b && c >= a) || (d <= b && d >= a) || (d >= b && c <= a)) {
                    if ((ball.x <= (floors.get(i).holePosition)) || (ball.x >= (floors.get(i)
                            .holePosition + floors.get(i).holeWidth))) {
                        ball.setY(c);
                        return true;
                    }
                }
            }
            return false;
        }

        private void init() {
            fileManager=new WordFileManager(context);
            fileManager.storeMyFileName("high_score");
            if(fileManager.getData(fileManager.getMyLastFileName())!=0 &&
                    fileManager.getData
                            (fileManager.getMyLastFileName())
                            <score){
                fileManager.saveData(score,fileManager.getMyLastFileName());
            }
            highScore=fileManager.getData(fileManager.getMyLastFileName());
            if(gameOverDialogBox!=null)
                gameOverDialogBox.dismiss();
            arcpaint = new Paint();
            mediaPlayer=mediaPlayer.create(context,R.raw.yann_tiersen);
            mediaPlayer.start();
             mediaPlayer.setLooping(true);
            floorPaint = new Paint();
            floorPaint.setStyle(Paint.Style.STROKE);
            floorPaint.setStrokeWidth(6);

            for (int i = 0; i < 8; i++) {
                floors.add(new Floor((height + (height / 3)) - (i * (height / 8)), width, height));
            }
            ballPaint = new Paint();
            ballPaint.setColor(Color.rgb(255, 242, 170));
            ballPaint.setStyle(Paint.Style.STROKE);
            ballPaint.setStrokeWidth(10);
            ball = new Ball(width / 2, 0);
        }

        // Executed on the Gui side
        protected void onProgressUpdate(GameInfo... progress) {
            floors = gameInfo.getFloors();
            ball = gameInfo.getBall();
            invalidate();
        }

        // Excecuted on the GUI side
        protected void onPostExecute(Boolean result) {

            if(fileManager.getData(fileManager.getMyLastFileName())!=0 &&
                    fileManager.getData
                    (fileManager.getMyLastFileName())
                    <score){
                fileManager.saveData(score,fileManager.getMyLastFileName());
            }
            highScore=fileManager.getData(fileManager.getMyLastFileName());
             gameOverDialogBox =
                    new DialogBox(context, gameview, score,highScore);
            gameOverDialogBox.show();
            stopThread();
        }
    }
}
