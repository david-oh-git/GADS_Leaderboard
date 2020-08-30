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

package io.audioshinigami.data_gads.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.audioshinigami.data_gads.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  contains all helper methods to create a [{@link GadsApiService}] instance
 */

public class ApiFactory {

    private ApiFactory(){
        // don't create an instance of this class , oh how i miss kotlin
    }

    /**
     * @return [OkHttpClient] instance + logs only for debug build
     */
    private static OkHttpClient provideHttpClient(){

        if (BuildConfig.DEBUG ){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY);

            return new OkHttpClient()
                    .newBuilder()
                    .addInterceptor( loggingInterceptor )
                    .build();
        }else {

            return new OkHttpClient()
                    .newBuilder()
                    .build();
        }
    }

    private static Gson provideGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    private static Retrofit retrofit(){
        String BASE_URL = "https://gadsapi.herokuapp.com";
        return new Retrofit.Builder()
                .client( provideHttpClient() )
                .baseUrl(BASE_URL)
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
    }

    public static synchronized GadsApiService provideGadsApi(){
        return retrofit().create(GadsApiService.class);
    }

}
