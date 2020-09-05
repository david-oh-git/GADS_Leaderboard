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

package io.audioshinigami.gadsleaderboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.audioshinigami.data_gads.data.GadsRepository;
import io.audioshinigami.data_gads.data.UserIq;
import io.audioshinigami.data_gads.data.UserTime;
import io.audioshinigami.data_gads.utility.LogHelper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SharedViewModel extends ViewModel {

    private final GadsRepository repository;
    private final String TAG = SharedViewModel.class.getSimpleName();

    private Boolean hasHoursApiCallFailed = false;
    private Boolean hasSkillIqApiCallFailed = false;

    private final MutableLiveData<Boolean> _isLearningHoursLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLearningHoursLoading = _isLearningHoursLoading;

    private final MutableLiveData<Boolean> _isSkillIqLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isSkillIqLoading = _isSkillIqLoading;

    private MutableLiveData<List<UserIq>> _userIqList = new MutableLiveData<>();
    public final LiveData<List<UserIq>> userIqList = _userIqList;

    private final MutableLiveData<List<UserTime>> _userList = new MutableLiveData<>();
    public final LiveData<List<UserTime>> userList = _userList;

    public SharedViewModel(GadsRepository repository) {
        super();
        this.repository = repository;
    }

    public void loadUserHours(Boolean value){

        _isLearningHoursLoading.postValue(true);

        repository.getUserHours(value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<UserTime>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<UserTime> userTimes) {
                        _userList.postValue(userTimes);
                        _isLearningHoursLoading.postValue(false);

                        // saves userList to DB only when call is from API
                        if(value){
                            repository.updateUserTimeDb(userTimes);
                            hasHoursApiCallFailed = false;
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        LogHelper.log(TAG, " Error getting data");
                        // if API call fails get data for DB
                        if (!hasHoursApiCallFailed){
                            hasHoursApiCallFailed = true;
                            loadUserHours(false);
                        }

                        _isLearningHoursLoading.postValue(false);
                    }
                });
    }

    public void loadUserIq(Boolean value){

        _isSkillIqLoading.postValue(true);

        repository.getUserIqs(value)
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<UserIq>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<UserIq> userIqs) {
                        _userIqList.postValue(userIqs);
                        _isSkillIqLoading.postValue(false);

                        // saves userList to DB only when call is from API
                        if(value){
                            repository.updateUserIqDb(userIqs);
                            hasSkillIqApiCallFailed = false;
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        LogHelper.log(TAG, " Error getting data");
                        // if API call fails get data for DB
                        if (!hasSkillIqApiCallFailed){
                            hasHoursApiCallFailed = true;
                            loadUserIq(false);
                        }

                        _isSkillIqLoading.postValue(false);
                    }
                });
    }
}

