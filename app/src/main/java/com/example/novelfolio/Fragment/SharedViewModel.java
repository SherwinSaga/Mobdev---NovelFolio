package com.example.novelfolio.Fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Boolean> isON = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsON() {
        return isON;
    }

    public void setIsON(boolean value) {
        isON.setValue(value);
    }

}