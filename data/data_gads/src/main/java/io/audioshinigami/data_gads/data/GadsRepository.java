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

/**
 *  Blueprint for repository
 */

public interface GadsRepository {

    /**
     *  make API call for {@link UserTime} list , update Database
     */
    void updateUserHoursFromApi();

    /**
     *  make API call for {@link UserIq} list , update Database
     */
    void updateUserIqFromApi();

    /**
     *  clears database first then save data from API
     * @param userIqList updated data from API
     */
    void updateUserIqDb(List<UserIq> userIqList);

    /**
     * clears database first then save data from API
     * @param userTimeList updated data from API
     */
    void updateUserTimeDb(List<UserTime> userTimeList);

    /**
     * @return list of {@link UserTime} from database
     */
    List<UserTime> getUserHours();

    /**
     * @return list of {@link UserIq} from database
     */
    List<UserIq> getSkillIqs();

    /**
     *
     * @return io.audioshinigami.data_gads.background.BackGroundExecutor for
     * running background tasks
     */
    Executor getExecutor();

    /**
     *
     * @param listener listener for repository
     */
    void setUpdateActionListener(DefaultRepository.UpdateDataActionListener listener);

}
