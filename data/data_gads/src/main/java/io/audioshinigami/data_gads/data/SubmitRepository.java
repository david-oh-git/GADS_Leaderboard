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

package io.audioshinigami.data_gads.data;

import androidx.annotation.NonNull;

import io.audioshinigami.data_gads.network.GadsSubmitApiService;
import io.audioshinigami.data_gads.utility.LogHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitRepository implements GadSubmitRepository {

    private final String TAG = SubmitRepository.class.getSimpleName();

    private final GadsSubmitApiService apiService;

    public SubmitRepository(GadsSubmitApiService apiService){
        this.apiService = apiService;
    }

    @Override
    public void submit(SubmitDetails submitDetails) {
        apiService.submit(submitDetails.firstName, submitDetails.lastName, submitDetails.email,
                submitDetails.track, submitDetails.github )
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        LogHelper.log(TAG, "The response was " + response.body() );
                        LogHelper.log(TAG, "The response code is " + response.code() );
                        LogHelper.log(TAG, "Successful ? : " + response.isSuccessful() );
                        LogHelper.log(TAG, "The message is " + response.errorBody() );
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        LogHelper.log(TAG, "Error submitting !!!!!! \n Error msg :  " + t.getMessage()  );
                    }
                });
    }
}
