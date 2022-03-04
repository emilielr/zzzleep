package com.example.zzzleep.ui.statistics;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.zzzleep.R;

public class StatisticsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StatisticsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is statistics fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}