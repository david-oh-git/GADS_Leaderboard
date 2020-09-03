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

package io.audioshinigami.data_gads.data;

import java.util.List;
import java.util.concurrent.Executor;

import io.audioshinigami.data_gads.utility.LogHelper;
import io.reactivex.rxjava3.core.Single;

public class DefaultRepository implements GadsRepository{

    private final GadsDataSource<UserTime, UserIq> localDataSource;
    private final GadsDataSource<UserTime, UserIq> remoteDataSource;
    private final Executor executor;
    private final String TAG = DefaultRepository.class.getSimpleName();

    public DefaultRepository(
            GadsDataSource<UserTime, UserIq> localDataSource,
            GadsDataSource<UserTime, UserIq> remoteDataSource,
            Executor executor
    ){
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        this.executor = executor;
    }

    @Override
    public Single<List<UserTime>> getUserHours(Boolean update) {
        if(!update)
            return localDataSource.getUserHours();

        return remoteDataSource.getUserHours();
    }

    @Override
    public Single<List<UserIq>> getUserIqs(Boolean update) {
        if(!update)
            return localDataSource.getUserIqs();

        return remoteDataSource.getUserIqs();
    }

    @Override
    public void updateUserIqDb(List<UserIq> userIqList) {
        executor.execute(() -> {
            localDataSource.deleteUserIqs();
            localDataSource.saveUserIqList(userIqList);
        });

    }

    @Override
    public void updateUserTimeDb(List<UserTime> userTimeList) {

        executor.execute(() -> {
            localDataSource.deleteUserHours();
            localDataSource.saveUserTimeList(userTimeList);
            LogHelper.log(TAG, "data is saved");
        });

    }

}
