package com.retrofitsample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.retrofitsample.sharePreference.Preference;

/**
 * Created by kamal on 03/09/2018.
 */

public class BaseActivity extends AppCompatActivity{
    public Preference preference;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference=new Preference(BaseActivity.this) ;
    }




    public void showLoading() {
       // if (mProgressDialog == null && !mProgressDialog.isShowing()) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading, please wait.");
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
      //   }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
        mProgressDialog.dismiss();
        mProgressDialog = null;
        }
    }


}
