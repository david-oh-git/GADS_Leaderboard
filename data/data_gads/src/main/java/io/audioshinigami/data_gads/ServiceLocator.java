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

package io.audioshinigami.data_gads;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.List;

import io.audioshinigami.data_gads.data.DefaultRepository;
import io.audioshinigami.data_gads.data.GadsDataSource;
import io.audioshinigami.data_gads.data.GadsRepository;
import io.audioshinigami.data_gads.data.UserIq;
import io.audioshinigami.data_gads.data.UserTime;
import io.audioshinigami.data_gads.data.source.local.GadsDatabase;
import io.audioshinigami.data_gads.data.source.local.LocalDataSource;
import io.audioshinigami.data_gads.data.source.remote.RemoteDataSource;
import io.audioshinigami.data_gads.network.ApiFactory;
import io.audioshinigami.data_gads.utility.LogHelper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 *  provide single instance object Instance
 */

public class ServiceLocator {

    private ServiceLocator(){}

    private static final String DATABASE_NAME = "io.audioshinigami.data_gads.DB_NAME";
    private static final String TAG = ServiceLocator.class.getSimpleName();
    private static GadsDatabase gadsDatabase = null;
    private static GadsRepository repository = null;

    public static GadsRepository provideGadsRepository(Context context){
        synchronized (ServiceLocator.class){
            if(repository != null)
                return repository;

            repository = new DefaultRepository(
                    createLocalGadsDataSource(context),
                    createRemoteGadsDataSource()
            );

            return repository;
        }
    }

    public static GadsDatabase provideGadsDatabase(Context context){
        synchronized (ServiceLocator.class){
            if (gadsDatabase != null)
                return gadsDatabase;

            gadsDatabase = Room.databaseBuilder(
                    context.getApplicationContext() ,
                    GadsDatabase.class, DATABASE_NAME
            ).build();

            return gadsDatabase;
        }
    }

    private static GadsDataSource<UserTime, UserIq> createLocalGadsDataSource(Context context){
        return new LocalDataSource(provideGadsDatabase(context).userIQDao(), provideGadsDatabase(context).userTimeDao());
    }

    private static GadsDataSource<UserTime, UserIq> createRemoteGadsDataSource(){
        return new RemoteDataSource(ApiFactory.provideGadsApi());
    }

    public static void test(Context context){
        provideGadsRepository(context).getUserHours(false)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<UserTime>>() {
                    @Override
                    public void onSuccess(@NonNull List<UserTime> userTimes) {
                        LogHelper.log(TAG, " data gotten successfully");

                        for (UserTime userTime: userTimes){
                            LogHelper.log(TAG, userTime.country);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogHelper.log(TAG, " Error getting data");
                    }
                });

        provideGadsRepository(context).getUserIqs(false)
                .subscribeOn(Schedulers.io())
                .observeOn( Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<UserIq>>() {
                    @Override
                    public void onSuccess(@NonNull List<UserIq> userIqs) {
                        LogHelper.log(TAG, " data gotten successfully");

                        for (UserIq userIq: userIqs){
                            LogHelper.log(TAG, userIq.toString());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogHelper.log(TAG, " Error getting data");
                    }
                });

    }

}
