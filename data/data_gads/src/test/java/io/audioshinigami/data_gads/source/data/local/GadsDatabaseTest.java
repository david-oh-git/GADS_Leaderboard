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

package io.audioshinigami.data_gads.source.data.local;


import android.content.Context;
import android.os.Build;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import io.audioshinigami.data_gads.source.UserIq;
import io.audioshinigami.data_gads.source.UserTime;

import static com.google.common.truth.Truth.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class GadsDatabaseTest {

    private GadsDatabase db;

    @Before
   public void init(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(
                context, GadsDatabase.class
        ).allowMainThreadQueries().build();

   }

   @After
   public void reset(){
        db.close();
   }

   @Test
   public void saveUserTimeToDb_ConfirmSaved(){
        // Arrange: create userTime data & save to DB
       UserTime karabo = new UserTime("Karabo Makeba", 19, "South Africa",
               "http://whogobuy.com/store/karabo1.jpg");
       UserTime efe = new UserTime("efe kabasa", 23, "Nigeria",
               "http://whogobuy.com/store/efe101.jpg");

       db.userTimeDao().save(karabo);
       db.userTimeDao().save(efe);

       // Act: get all userTime data from db
       List<UserTime> userHourListResult = db.userTimeDao().getTimeList();

       // Assert:
       assertThat(userHourListResult.isEmpty()).isEqualTo(false);
   }

   @Test
    public void saveUserTime_getAllHours(){
       // Arrange: create userTime data & save to DB
       UserTime karabo = new UserTime("Karabo Makeba", 19, "South Africa",
               "http://whogobuy.com/store/karabo1.jpg");
       db.userTimeDao().save(karabo);

       // Act: get all userTime data from db
       List<UserTime> userHourListResult = db.userTimeDao().getTimeList();

       // Assert: only 1 object in database, confirm expected values
       assertThat(userHourListResult.size()).isEqualTo(1);
       assertThat(userHourListResult.get(0).name).isEqualTo(karabo.name);
       assertThat(userHourListResult.get(0).hours).isEqualTo(karabo.hours);
       assertThat(userHourListResult.get(0).country).isEqualTo(karabo.country);
       assertThat(userHourListResult.get(0).badgeUrl).isEqualTo(karabo.badgeUrl);
   }

    @Test
    public void saveUserIqToDb_ConfirmSaved(){
        // Arrange: create userTime data & save to DB
        UserIq karabo = new UserIq("Karabo Makeba", 19, "South Africa",
                "http://whogobuy.com/store/karabo1.jpg");
        UserIq efe = new UserIq("efe kabasa", 23, "Nigeria",
                "http://whogobuy.com/store/efe101.jpg");

        db.userIQDao().save(karabo);
        db.userIQDao().save(efe);

        // Act: get all userTime data from db
        List<UserIq> userHourListResult = db.userIQDao().getIqList();

        // Assert:
        assertThat(userHourListResult.isEmpty()).isEqualTo(false);
    }

    @Test
    public void saveUserIq_getAllHours(){
        // Arrange: create userTime data & save to DB
        UserIq karabo = new UserIq("Karabo Makeba", 19, "South Africa",
                "http://whogobuy.com/store/karabo1.jpg");
        db.userIQDao().save(karabo);

        // Act: get all userTime data from db
        List<UserIq> userHourListResult = db.userIQDao().getIqList();

        // Assert: only 1 object in database, confirm expected values
        assertThat(userHourListResult.size()).isEqualTo(1);
        assertThat(userHourListResult.get(0).name).isEqualTo(karabo.name);
        assertThat(userHourListResult.get(0).score).isEqualTo(karabo.score);
        assertThat(userHourListResult.get(0).country).isEqualTo(karabo.country);
        assertThat(userHourListResult.get(0).badgeUrl).isEqualTo(karabo.badgeUrl);
    }
}