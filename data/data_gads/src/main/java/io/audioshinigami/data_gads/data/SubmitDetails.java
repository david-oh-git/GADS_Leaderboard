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


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitDetails {

    @SerializedName("entry.1659819444")
    @Expose
    final String firstName;

    @SerializedName("entry.472564179")
    @Expose
    final String lastName;

    @SerializedName("entry.285899941")
    @Expose
    final String personalPhone;

    @SerializedName("entry.699265956")
    @Expose
    final String businessName;

    @SerializedName("entry.922324374")
    @Expose
    final String locationOfBusiness;

    @SerializedName("entry.163074460")
    @Expose
    final String businessEmail;

    @SerializedName("entry.729953638")
    @Expose
    final String businessPhone;

    @SerializedName("entry.352488134")
    @Expose
    final String governmentStat;

    @SerializedName("entry.1293650130")
    @Expose
    final String additionalInfo;

    public SubmitDetails(
            String firstName, String lastName, String personalPhone,
            String businessName, String locationOfBusiness, String businessEmail,
            String businessPhone, String governmentStat, String additionalInfo
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalPhone = personalPhone;
        this.businessName = businessName;
        this.locationOfBusiness = locationOfBusiness;
        this.businessEmail = businessEmail;
        this.businessPhone = businessPhone;
        this.governmentStat = governmentStat;
        this.additionalInfo = additionalInfo;
    }
}
