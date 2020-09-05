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

package io.audioshinigami.data_gads.data.source.remote;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.audioshinigami.data_gads.data.GadsDataSource;
import io.audioshinigami.data_gads.data.UserIq;
import io.audioshinigami.data_gads.data.UserTime;
import io.audioshinigami.data_gads.network.GadsApiService;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource implements GadsDataSource<UserTime,UserIq> {

    private GadsApiService apiService;

    public RemoteDataSource(GadsApiService apiService){
        this.apiService = apiService;
    }

    @Override
    public Single<List<UserTime>> getUserHours() {
        return Single.create(emitter -> {
            Call<List<UserTime>> call = apiService.getUserHours();

            call.enqueue(new Callback<List<UserTime>>() {
                @Override
                public void onResponse(@NotNull Call<List<UserTime>> call, @NotNull Response<List<UserTime>> response) {

                    emitter.onSuccess( response.body() );
                }

                @Override
                public void onFailure(@NotNull Call<List<UserTime>> call, @NotNull Throwable t) {
                    emitter.onError( t);
                }
            });
        });
    }

    @Override
    public Single<List<UserIq>> getUserIqs() {
        return Single.create( emitter -> {
            Call<List<UserIq>> call = apiService.getUserIqs();

            call.enqueue(new Callback<List<UserIq>>() {
                @Override
                public void onResponse(@NotNull Call<List<UserIq>> call, @NotNull Response<List<UserIq>> response) {
                    emitter.onSuccess(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<List<UserIq>> call, @NotNull Throwable t) {
                    emitter.onError(t);
                }
            });
        });
    }

    @Override
    public void saveUserTime(UserTime obj) {
        // Not required
    }

    @Override
    public void saveUserIq(UserIq obj) {
        // Not required
    }

    @Override
    public void deleteUserTime(UserTime obj) {
        // Not required
    }

    @Override
    public void deleteUserIq(UserIq obj) {
        // Not required
    }

    @Override
    public void saveUserIqList(List<UserIq> userIqList) {
        // Not required
    }

    @Override
    public void saveUserTimeList(List<UserTime> userTimeList) {
        // Not required
    }

    @Override
    public void deleteUserHours() {
        // Not required
    }

    @Override
    public void deleteUserIqs() {
        // Not required
    }

}
