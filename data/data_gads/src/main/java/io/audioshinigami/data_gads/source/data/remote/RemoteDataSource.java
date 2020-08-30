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

package io.audioshinigami.data_gads.source.data.remote;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.audioshinigami.data_gads.network.GadsApiService;
import io.audioshinigami.data_gads.source.GadsDataSource;
import io.audioshinigami.data_gads.source.UserIq;
import io.audioshinigami.data_gads.source.UserTime;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource implements GadsDataSource {

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

                    emitter.onSuccess(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<List<UserTime>> call, @NotNull Throwable t) {
                    emitter.onError(t);
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

//    TODO refactor later
//    private <T> Single<List<T>> apiResponse( Class<T> classType){
//        return Single.create( emitter -> {
//            if(classType.isAssignableFrom(UserTime.class)){
//                Call<List<UserTime>> call = apiService.getUserHours();
//
//                call.enqueue( new GadsRetrofitCallback<List<UserTime>>(emitter));
//
//            }
//        });
//    }

    private class GadsRetrofitCallback<T> implements Callback<List<T>> {

        private SingleEmitter<@NonNull T> emitter;

        public GadsRetrofitCallback(SingleEmitter<@NonNull T> emitter){
            this.emitter = emitter;
        }


        @Override
        public void onResponse(Call<List<T>> call, Response<List<T>> response) {
            emitter.onSuccess( (T) response.body() );
        }

        @Override
        public void onFailure(Call<List<T>> call, Throwable t) {
            emitter.onError(t);
        }
    }

}
