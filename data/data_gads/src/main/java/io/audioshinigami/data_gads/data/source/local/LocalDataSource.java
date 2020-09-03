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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.data_gads.data.source.local;

import java.util.List;

import io.audioshinigami.data_gads.data.GadsDataSource;
import io.audioshinigami.data_gads.data.UserIq;
import io.audioshinigami.data_gads.data.UserTime;
import io.audioshinigami.data_gads.utility.LogHelper;
import io.reactivex.rxjava3.core.Single;

public class LocalDataSource implements GadsDataSource<UserTime,UserIq> {

    private UserIQDao userIQDao;
    private UserTimeDao userTimeDao;
    private final String TAG = LocalDataSource.class.getSimpleName();

    public LocalDataSource(UserIQDao userIQDao, UserTimeDao userTimedao){
        this.userIQDao = userIQDao;
        this.userTimeDao = userTimedao;
    }

    @Override
    public Single<List<UserTime>> getUserHours() {
        return Single.create( emitter -> {

            try {
                List<UserTime> userTimeList = userTimeDao.getTimeList();

                emitter.onSuccess( userTimeList);
            }
            catch (Throwable throwable){
                LogHelper.log(TAG, "Error getting data from DB");
                LogHelper.log(TAG, "Error message is: " + throwable.getMessage());

                emitter.onError(throwable);
            }
        });
    }

    @Override
    public Single<List<UserIq>> getUserIqs() {
        return Single.create(emitter -> {
            try {
                List<UserIq> userIqList = userIQDao.getIqList();
                emitter.onSuccess(userIqList);
            }catch ( Throwable throwable){
                LogHelper.log(TAG, "Error getting data from DB");
                LogHelper.log(TAG, "Error message is: " + throwable.getMessage());

                emitter.onError(throwable);
            }
        });
    }

    @Override
    public void saveUserTime(UserTime obj) {
        userTimeDao.save(obj);
    }

    @Override
    public void saveUserIq(UserIq obj) {
        userIQDao.save(obj);
    }

    @Override
    public void deleteUserTime(UserTime obj) {
        userTimeDao.delete(obj);
    }

    @Override
    public void deleteUserIq(UserIq obj) {
        userIQDao.delete(obj);
    }

    @Override
    public void saveUserIqList(List<UserIq> userIqList) {
        userIQDao.saveAll(userIqList);
    }

    @Override
    public void saveUserTimeList(List<UserTime> userTimeList) {
        userTimeDao.saveAll(userTimeList);
    }

    @Override
    public void deleteUserHours() {
        userTimeDao.deleteAll();
    }

    @Override
    public void deleteUserIqs() {
        userIQDao.deleteAll();
    }
}
