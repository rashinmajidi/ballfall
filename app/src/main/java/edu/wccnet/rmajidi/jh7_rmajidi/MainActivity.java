package edu.wccnet.rmajidi.jh7_rmajidi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        gameView = (GameView) findViewById(R.id.myview);
        gameView.setOnTouchListener(gameView);
    }

    @Override
    public void onResume() {
        super.onResume();
        gameView.startThread(this,gameView);
        Log.d("Mine", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        gameView.stopThread();
        Log.d("Mine", "onPause");
    }
}
