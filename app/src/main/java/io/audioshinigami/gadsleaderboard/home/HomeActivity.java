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

package io.audioshinigami.gadsleaderboard.home;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

import io.audioshinigami.data_gads.utility.LogHelper;
import io.audioshinigami.gadsleaderboard.R;
import io.audioshinigami.gadsleaderboard.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = HomeActivity.class.getSimpleName();
    private final SubmitButtonClickListener _listener = new SubmitButtonClickListener() {
        @Override
        public void onClicked() {
            // TODO launch Submit Activity
            Toast.makeText(getApplicationContext(), "Hello punk bitch", Toast.LENGTH_SHORT).show();
        }
    };

    private  ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_home
        );
        binding.setLifecycleOwner(this);
        binding.setListener(_listener);

//        Objects.requireNonNull(getSupportActionBar()).setCustomView();

        LogHelper.log(TAG, "HomeAct onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        setupViews(binding);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        LogHelper.log(TAG, "HomeAct onResume");
    }

    private void setupViews(ActivityHomeBinding binding) {
        final HomePagerAdaptor adaptor = new HomePagerAdaptor(this);

        binding.viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        binding.viewPager.setAdapter( adaptor );


        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                ((tab, position) -> {

                    switch (position){
                        case 0:
                            tab.setText(R.string.learning_tab_tile);
                            break;
                        case 1:
                            tab.setText(R.string.iq_tab_title);
                            break;
                    }

                })
        ).attach();
    }
}