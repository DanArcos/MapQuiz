package com.cornbread.android.mapquiz;

public class TrueFalse {

    private int mQuestion;

    private boolean mTrueQuestion;

    private boolean mCheater;

    public TrueFalse(int question, boolean trueQuestion, boolean cheater) {
        mQuestion =  question;
        mTrueQuestion = trueQuestion;
        mCheater = cheater;
    }

    public int getQuestion(){
        return mQuestion;
    }

    public void setQuestion(int question){
        mQuestion = question;
    }

    public boolean isTrueQuestion(){
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion){
        mTrueQuestion = trueQuestion;
    }

    public boolean isCheater (){
        return mCheater;
    }

    public void setCheater (boolean cheater){
        mCheater = cheater;
    }
}
