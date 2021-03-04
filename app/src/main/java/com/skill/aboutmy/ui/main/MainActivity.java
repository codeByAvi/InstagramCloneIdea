package com.skill.aboutmy.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProvider;

import com.skill.aboutmy.R;
import com.skill.aboutmy.databinding.MainActivityBinding;
import com.skill.aboutmy.ui.splash.SplashFragment;
import com.skill.aboutmy.utils.MediaViewModelFactory;
import com.skill.aboutmy.viewmodels.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MainActivityBinding mainActivityBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this,R.layout.main_activity);

        if (savedInstanceState == null) {
            SplashFragment splashFragment = SplashFragment.newInstance();
            splashFragment.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(mainActivityBinding.container.getId(),splashFragment )
                    .commitNow();
        }

    }
}