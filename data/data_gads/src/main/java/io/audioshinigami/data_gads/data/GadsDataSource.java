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

import retrofit2.Call;

/**
 *
 * @param <T> data type to be stored . [{@link UserTime}]
 * @param <I> data type to be stored . [{@link UserIq}]
 */

public interface GadsDataSource<T,I> {


    void saveUserTime(T obj);

    void saveUserIq(I obj);

    void deleteUserTime(T obj);

    void deleteUserIq(I obj);

    void saveUserIqList(List<I> userIqList);

    void saveUserTimeList(List<T> userTimeList);

    void deleteUserHours();

    void deleteUserIqs();

    List<UserTime> getUserList();

    List<UserIq> getSkillIqList();

    Call<List<UserTime>> getUserHours();

    Call<List<UserIq>> getUserIqs();

}
