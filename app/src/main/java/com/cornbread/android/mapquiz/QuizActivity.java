package com.cornbread.android.mapquiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends ActionBarActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX= "index"; //Key for key-value pair for keeping a persistent index across orientations
    private static final String KEY_CHEATED = "cheated" ; //Key for key-value pair for keeping a cheated state across orientations

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPreviousButton;
    private Button mNextButton;
    private Button mCheatButton;

    private TextView mQuestionTextView;

    //Set up array to hold TrueFalse objects. Constructor called multiple times.
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans,true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0; //Index of questions to navigate mQuestionBank

    private boolean mIsCheater;

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question); //setText takes an INT and converts it to text
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId = 0; //Initialize variable

        if(mIsCheater){
            messageResId = R.string.judgment_toast;
        } else{

            if(userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            }
            else{
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view); //Tie textview to layout widget

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATED);
        }

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mPreviousButton = (Button)findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex == 0){
                    mCurrentIndex = 4;
                    mIsCheater = false;
                }
                else{
                    mCurrentIndex = (mCurrentIndex-1) % mQuestionBank.length;
                    mIsCheater = false;
                }

                updateQuestion();
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Cheat Activity
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, 0); //Zero parameter means we don't care what we send back
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    //This is called right before activity is stopped
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //Log.i(TAG, "onSaveInstanceState");

        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CHEATED, mIsCheater);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }
}
