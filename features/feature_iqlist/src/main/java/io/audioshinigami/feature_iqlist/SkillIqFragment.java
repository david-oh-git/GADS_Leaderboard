package io.audioshinigami.feature_iqlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.audioshinigami.data_gads.ServiceLocator;
import io.audioshinigami.feature_iqlist.databinding.FragmentSkillIqBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkillIqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SkillIqFragment extends Fragment {

    private final String TAG = SkillIqFragment.class.getSimpleName();
    private FragmentSkillIqBinding binding;
    private SkillIqViewModel viewModel;

    public SkillIqFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, new SkillIqViewModelFactory(ServiceLocator.provideGadsRepository(requireContext())))
                .get(SkillIqViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSkillIqBinding.inflate(inflater);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.loadUserIq(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpViews();
    }

    private void setUpViews() {
        setUpRecyclerView();
        setUpRefreshLayout();
    }

    private void setUpRefreshLayout() {

    }

    private void setUpRecyclerView() {
        final UserIqAdaptor adaptor = new UserIqAdaptor();

        binding.recyclerView.setAdapter(adaptor);

        viewModel.userIqList.observe(this, adaptor::submitList);
    }

    public static SkillIqFragment newInstance() {
        return new SkillIqFragment();
    }
}