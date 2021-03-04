package com.skill.aboutmy.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.skill.aboutmy.models.MediaItem;
import com.skill.aboutmy.viewmodels.MainViewModel;

import java.util.List;

public class MediaViewModelFactory  extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;


    public MediaViewModelFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MainViewModel.class) {
            return (T) new MainViewModel(application);
        }
        return null;
    }
}