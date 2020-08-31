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

package io.audioshinigami.data_gads.source;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


/**
 *  POJO representing an item of data from API response for IQ
 *  also Entity for Room DB
 */

@Entity( tableName = "io.audioshinigami.data_gads.source.iq_table_name" )
public class UserIq {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo
    @SerializedName("name")
    public String name;

    @ColumnInfo
    @SerializedName("score")
    public int score;

    @ColumnInfo
    @SerializedName("country")
    public String country;

    @ColumnInfo
    @SerializedName("badgeUrl")
    public String badgeUrl;

    public UserIq( String name, int score, String country, String badgeUrl){
        this.name = name;
        this.score = score;
        this.country = country;
        this.badgeUrl = badgeUrl;
        this.uid = 0;

    }

}
