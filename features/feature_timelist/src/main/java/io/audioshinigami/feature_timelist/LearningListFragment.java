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

package io.audioshinigami.feature_timelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.audioshinigami.data_gads.ServiceLocator;
import io.audioshinigami.feature_timelist.databinding.FragmentLearningListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LearningListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearningListFragment extends Fragment {

    private final String TAG = LearningListFragment.class.getSimpleName();
    private FragmentLearningListBinding binding;
    private LearningViewModel viewModel;

    public LearningListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, new LearningViewModelFactory(ServiceLocator.provideGadsRepository(requireContext())))
                .get(LearningViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLearningListBinding.inflate(inflater);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel( viewModel );

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.loadUserHours(true);
    }

    private void setUpViews(){
        setUpRecyclerView();
        setUpRefreshLayout();

    }

    private void setUpRefreshLayout() {

    }

    private void setUpRecyclerView() {
        final UserTimeAdaptor adaptor = new UserTimeAdaptor();

        binding.recyclerView.setAdapter(adaptor);

        viewModel.userList.observe(this, adaptor::submitList);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static LearningListFragment newInstance() {

//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return new LearningListFragment();
    }
}