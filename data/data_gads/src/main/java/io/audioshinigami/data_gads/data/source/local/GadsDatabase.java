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

package io.audioshinigami.data_gads.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import io.audioshinigami.data_gads.data.UserIq;
import io.audioshinigami.data_gads.data.UserTime;

@Database(entities = {UserIq.class, UserTime.class}, version = 1)
public abstract class GadsDatabase extends RoomDatabase {

    abstract public UserIQDao userIQDao();

    abstract public UserTimeDao userTimeDao();

    public static GadsDatabase providegadsDatabase(Context context){
        return Room.databaseBuilder(
                context.getApplicationContext(),
                GadsDatabase.class,
                "gads_db"
        ).build();
    }

    public static GadsDatabase provideInMemoryDatabase(Context context){
        return Room.inMemoryDatabaseBuilder(
                context, GadsDatabase.class
        ).allowMainThreadQueries().build();
    }
}
