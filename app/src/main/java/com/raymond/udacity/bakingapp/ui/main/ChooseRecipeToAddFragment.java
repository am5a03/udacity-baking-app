package com.raymond.udacity.bakingapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.ui.BaseFragment;

public class ChooseRecipeToAddFragment extends BaseFragment {
    public static ChooseRecipeToAddFragment newInstance() {

        Bundle args = new Bundle();

        ChooseRecipeToAddFragment fragment = new ChooseRecipeToAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }
}
