package com.retrofitsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.retrofitsample.sharePreference.Preference;

/**
 * Created by kamal on 03/09/2018.
 */

public class BaseActivity extends AppCompatActivity{
    public Preference preference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference=new Preference(BaseActivity.this) ;
    }
}
