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

import io.audioshinigami.data_gads.data.DefaultRepository;
import io.audioshinigami.data_gads.data.GadsRepository;
import io.audioshinigami.data_gads.data.UserIq;
import io.audioshinigami.data_gads.data.UserTime;

public class SharedViewModel extends ViewModel {

    private final GadsRepository repository;
    private final String TAG = SharedViewModel.class.getSimpleName();

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

        init();
    }

    public void init(){
        repository.getExecutor().execute( () -> {
            _userIqList.postValue( repository.getSkillIqs());
            _userList.postValue( repository.getUserHours() );
        });

        repository.setUpdateActionListener(new DefaultRepository.UpdateDataActionListener() {
            @Override
            public void setUserHourIsLoading(Boolean value) {
                _isLearningHoursLoading.postValue(value);
            }

            @Override
            public void setSkillIqIsLoading(Boolean value) {
                _isSkillIqLoading.postValue(value);
            }

            @Override
            public void updateUserHours() {
                repository.getExecutor().execute(() -> {
                    _userList.postValue( repository.getUserHours() );
                });
            }

            @Override
            public void updateSkillIq() {
                repository.getExecutor().execute(() -> {
                    _userIqList.postValue( repository.getSkillIqs());
                });
            }
        });
    }

    public void loadUserHours(Boolean value){

        _isLearningHoursLoading.postValue(true);

        repository.getUserHours(value);
    }

    public void loadUserIq(Boolean value){

        _isSkillIqLoading.postValue(true);

        repository.getUserIqs(value);
    }
}

