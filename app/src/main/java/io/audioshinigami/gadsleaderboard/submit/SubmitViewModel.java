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

package io.audioshinigami.gadsleaderboard.submit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.audioshinigami.data_gads.data.GadSubmitRepository;
import io.audioshinigami.data_gads.data.SubmitDetails;
import io.audioshinigami.data_gads.utility.LogHelper;

public class SubmitViewModel extends ViewModel {

    private final GadSubmitRepository repository;
    private final String TAG =  SubmitViewModel.class.getSimpleName();

    public final MutableLiveData<String> firstName = new MutableLiveData<>();
    public final MutableLiveData<String> lastName = new MutableLiveData<>();
    public final MutableLiveData<String> emailAddress = new MutableLiveData<>();
    public final MutableLiveData<String> githubUrl = new MutableLiveData<>();

    public SubmitViewModel(GadSubmitRepository repository){
        super();
        this.repository = repository;
    }

    public void submit(){
        SubmitDetails submitDetails = new SubmitDetails(firstName.getValue(), lastName.getValue() ,
                "+234-805-XXXXXXX", null, "Uyo, Nigeria", emailAddress.getValue(),
                "+234-803-XXXXXXX", null, null);

        repository.submit(submitDetails);
        LogHelper.log(TAG, "First name is " + firstName.getValue() );
    }
}
