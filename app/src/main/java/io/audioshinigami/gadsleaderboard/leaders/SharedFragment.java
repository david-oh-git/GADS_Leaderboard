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

package io.audioshinigami.gadsleaderboard.leaders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.audioshinigami.data_gads.ServiceLocator;
import io.audioshinigami.gadsleaderboard.SharedViewModel;
import io.audioshinigami.gadsleaderboard.SharedViewModelFactory;
import io.audioshinigami.gadsleaderboard.databinding.FragmentSharedBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SharedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharedFragment extends Fragment {

    public static String FRAGMENT_TYPE_KEY = "io.audioshinigami.gadsleaderboard.leaders.FRAGMENT_TYPE_KEY";
    private FragmentType fragmentType;
    private FragmentSharedBinding binding;

    private SharedViewModel viewModel;

    public SharedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentType = (FragmentType) getArguments().getSerializable(FRAGMENT_TYPE_KEY);
        }

        viewModel = new ViewModelProvider(requireActivity(), new SharedViewModelFactory(ServiceLocator.provideGadsRepository(requireContext())) )
                .get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSharedBinding.inflate(inflater);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpViews();
        loadData();
    }

    private void loadData() {
        switch (fragmentType){
            case LEARNING_SKILL_IQ:
                viewModel.loadUserIq();
                break;
            case LEARNING_HOURS:
                viewModel.loadUserHours();
                break;
        }
    }

    private void setUpViews() {
        setUpRecyclerView();
        setUpRefreshLayout();
    }

    private void setUpRefreshLayout() {

        switch (fragmentType){
            case LEARNING_HOURS:
                setUpLearningHoursSwipeLayout();
                break;
            case LEARNING_SKILL_IQ:
                setUpSkillsIqSwipeLayout();
        }
    }

    private void setUpSkillsIqSwipeLayout() {
        viewModel.isSkillIqLoading.observe(this, isSkillIQLoading -> binding.swipeRefreshLayout.setRefreshing(isSkillIQLoading));
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.loadUserIq());
    }

    private void setUpLearningHoursSwipeLayout() {
        viewModel.isLearningHoursLoading.observe(this, isHoursLoading -> binding.swipeRefreshLayout.setRefreshing(isHoursLoading) );
        binding.swipeRefreshLayout.setOnRefreshListener( () -> viewModel.loadUserHours() );
    }

    private void setUpRecyclerView() {
        switch (fragmentType){
            case LEARNING_SKILL_IQ:
                setUpSkillIqRecyclerView();
                break;
            case LEARNING_HOURS:
                setUpLearningHoursRecyclerView();
        }
    }

    private void setUpLearningHoursRecyclerView(){
        final UserTimeAdaptor adaptor = new UserTimeAdaptor();

        binding.recyclerView.setAdapter(adaptor);

        viewModel.userList.observe(this, adaptor::submitList);
    }

    private void setUpSkillIqRecyclerView(){
        final UserIqAdaptor adaptor = new UserIqAdaptor();

        binding.recyclerView.setAdapter(adaptor);

        viewModel.userIqList.observe(this, adaptor::submitList);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fragmentType states the type of fragment , if for Learning hours or skill IQ Fragment
     * @return A new instance of fragment SharedFragment.
     */
    public static SharedFragment newInstance(FragmentType fragmentType) {
        SharedFragment fragment = new SharedFragment();
        Bundle args = new Bundle();
        args.putSerializable( FRAGMENT_TYPE_KEY,fragmentType);
        fragment.setArguments(args);
        return fragment;
    }
}