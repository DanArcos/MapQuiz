package com.cornbread.android.mapquiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends ActionBarActivity {
    public static final String EXTRA_ANSWER_IS_TRUE = "com.cornbread.android.mapquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.cornbread.android.mapquiz.answer_is_shown";

    public static final String KEY_ANSWER_SHOWN = "shown"; //Key to preserve across orientations

    private boolean mAnswerIsTrue;
    private boolean mAnswerShown;

    private TextView mAnswerTextView;
    private Button mShowAnswer;

    private void setAnswerShownResult (boolean isAnswerShown){
        Intent data = new Intent(); //Create new intent that will send data back
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown); //Populate intent with variable we want to send back
        setResult(RESULT_OK, data); //Set result that will be sent back
        mAnswerShown = isAnswerShown;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        //Answer will not be shown until the user presses the button
        setAnswerShownResult(false);

        if(savedInstanceState != null){
            setAnswerShownResult(savedInstanceState.getBoolean(KEY_ANSWER_SHOWN));
            //Log.i("TAG", Boolean.toString(mAnswerShown));
        }


        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cheat, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ANSWER_SHOWN, mAnswerShown);
    }
}
