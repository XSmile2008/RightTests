package com.xsmile2008.righttests.livedata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

/**
 * Is used to observe {@link LiveData} when object does not have reference to {@link androidx.lifecycle.LifecycleOwner}.
 * It takes {@link LiveData} and {@link Observer} as a constructor parameters and calls {@link LiveData#observeForever(Observer)}.
 * Call {@link ForeverObserver#removeObserver()} to unsubscribe from LiveData updates or, for example, inside {@link ViewModel#onCleared()}.
 * @param <T> The type of data held by {@link LiveData} and {@link Observer}
 */
public class ForeverObserver<T> {

    @NonNull
    private final LiveData<T> liveData;

    @NonNull
    private final Observer<T> observer;

    public ForeverObserver(@NonNull LiveData<T> liveData, @NonNull Observer<T> observer) {
        this.liveData = liveData;
        this.observer = observer;
        liveData.observeForever(observer);
    }

    public void removeObserver() {
        liveData.removeObserver(observer);
    }

    @NonNull
    public LiveData<T> getLiveData() {
        return liveData;
    }

    @NonNull
    public Observer<T> getObserver() {
        return observer;
    }
}
