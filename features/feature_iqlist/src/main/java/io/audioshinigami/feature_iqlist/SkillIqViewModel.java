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

package io.audioshinigami.feature_iqlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import io.audioshinigami.data_gads.data.GadsRepository;
import io.audioshinigami.data_gads.data.UserIq;
import io.audioshinigami.data_gads.utility.LogHelper;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SkillIqViewModel extends ViewModel {

    private final GadsRepository repository;
    private final String TAG = SkillIqViewModel.class.getSimpleName();

    private Boolean hasFailedApiTries = false;

    public SkillIqViewModel(GadsRepository repository) {
        super();
        this.repository = repository;
    }

    private final MutableLiveData<Boolean> _isDataLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isDataLoading = _isDataLoading;

    private MutableLiveData<List<UserIq>> _userIqList = new MutableLiveData<>();
    LiveData<List<UserIq>> userIqList = _userIqList;

    public void loadUserIq(Boolean value){

        _isDataLoading.postValue(true);

        repository.getUserIqs(value)
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<UserIq>>() {
                    @Override
                    public void onSuccess(@NonNull List<UserIq> userIqs) {
                        _userIqList.postValue(userIqs);
                        _isDataLoading.postValue(false);

                        // saves userList to DB only when call is from API
                        if(value){
                            repository.updateUserIqDb(userIqs);
                            hasFailedApiTries = false;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogHelper.log(TAG, " Error getting data");
                        // if API call fails get data for DB
                        if (!hasFailedApiTries){
                            hasFailedApiTries = true;
                            loadUserIq(false);
                        }

                        _isDataLoading.postValue(false);
                    }
                });
    }
}

class SkillIqViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final GadsRepository repository;

    public SkillIqViewModelFactory(GadsRepository repository) {
        super();
        this.repository = repository;
    }

    @androidx.annotation.NonNull
    @Override
    public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
        return (T) new SkillIqViewModel(repository);
    }
}
