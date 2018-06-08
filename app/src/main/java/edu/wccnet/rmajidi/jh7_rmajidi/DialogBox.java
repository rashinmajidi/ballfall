package edu.wccnet.rmajidi.jh7_rmajidi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by rashin on 4/16/17.
 */

public class DialogBox extends Dialog
        implements View.OnClickListener {
    TextView scoreView;
    int score;
    Button button;
    GameView gameView;
    Context context;
    int highScore;
    public DialogBox(Context context,GameView gameView, int score,int highScore) {
        super(context);
        this.context=context;
        this.score=score;
        this.gameView=gameView;
        this.highScore=highScore;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_layout);
        this.getWindow().setBackgroundDrawableResource(R.color.translucent_black);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);


        scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("Your Score: "+score+"\n High Score: "+highScore);

        button=(Button) findViewById(R.id.retry);
        button.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        gameView.startThread(context,gameView);
        dismiss();
    }
}
