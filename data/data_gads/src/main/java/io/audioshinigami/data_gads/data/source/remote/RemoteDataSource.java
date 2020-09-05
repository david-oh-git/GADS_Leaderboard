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

import java.util.List;

import io.audioshinigami.data_gads.data.GadsDataSource;
import io.audioshinigami.data_gads.data.UserIq;
import io.audioshinigami.data_gads.data.UserTime;
import io.audioshinigami.data_gads.network.GadsApiService;
import retrofit2.Call;

public class RemoteDataSource implements GadsDataSource<UserTime, UserIq> {

    private GadsApiService apiService;

    public RemoteDataSource(GadsApiService apiService){
        this.apiService = apiService;
    }

    @Override
    public List<UserTime> getUserList() {
        // Not required
        return null;
    }

    @Override
    public List<UserIq> getSkillIqList() {
        // Not required
        return null;
    }

    @Override
    public Call<List<UserTime>> getUserHours() {
        return apiService.getUserHours();
    }

    @Override
    public Call<List<UserIq>> getUserIqs() {
        return apiService.getUserIqs();
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
