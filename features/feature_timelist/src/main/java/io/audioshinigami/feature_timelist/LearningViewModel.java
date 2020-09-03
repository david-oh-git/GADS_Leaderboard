/*
 * MIT License
 *
 * Copyright (c) 2020 David O.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.feature_timelist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import io.audioshinigami.data_gads.data.GadsRepository;
import io.audioshinigami.data_gads.data.UserTime;
import io.audioshinigami.data_gads.utility.LogHelper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LearningViewModel extends ViewModel {

    private final GadsRepository repository;
    private final String TAG = LearningViewModel.class.getSimpleName();

    private Boolean hasFailedApiTries = false;

    public LearningViewModel(GadsRepository repository){
        super();
        this.repository = repository;

    }

    private final MutableLiveData<Boolean> _isDataLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isDataLoading = _isDataLoading;

    private final MutableLiveData<List<UserTime>> _userList = new MutableLiveData<>();
    public final LiveData<List<UserTime>> userList = _userList;

    /**
     *
     * @param value booleans that determines if data is from API call or DB
     */
    public void loadUserHours(Boolean value){

        _isDataLoading.postValue(true);

        repository.getUserHours(value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<UserTime>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<UserTime> userTimes) {
                        _userList.postValue(userTimes);
                        _isDataLoading.postValue(false);

                        // saves userList to DB only when call is from API
                        if(value){
                            repository.updateUserTimeDb(userTimes);
                            hasFailedApiTries = false;
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        LogHelper.log(TAG, " Error getting data");
                        // if API call fails get data for DB
                        if (!hasFailedApiTries){
                            hasFailedApiTries = true;
                            loadUserHours(false);
                        }

                        _isDataLoading.postValue(false);
                    }
                });
    }
}


class LearningViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final GadsRepository repository;

    public LearningViewModelFactory(GadsRepository repository) {
        super();
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LearningViewModel(repository);
    }
}