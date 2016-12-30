package com.app.slowhand.settingssample;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.slowhand.settingssample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Settings mSettings;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mSettings = new Settings(this);
        mBinding.setSettings(mSettings);

    }
}
