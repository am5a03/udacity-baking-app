package com.raymond.udacity.bakingapp.ui.detail;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.models.db.Step;
import com.raymond.udacity.bakingapp.ui.BaseFragment;

import butterknife.BindView;

public class RecipeDetailFragment extends BaseFragment {

    public static final String KEY_RECIPE_ID = "recipe_id";
    public static final String KEY_STEP_ID = "step_id";

    @BindView(R.id.player_view)
    PlayerView playerView;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.short_description)
    TextView shortDescription;

    private RecipeDetailViewModel viewModel;

    public static RecipeDetailFragment newInstance(int recipeId, int stepId) {

        Bundle args = new Bundle();

        args.putInt(KEY_RECIPE_ID, recipeId);
        args.putInt(KEY_STEP_ID, stepId);

        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RecipeDetailViewModel.class);
        viewModel.stepLiveData.observe(this, stepWrapper -> {
            playerView.setVisibility(stepWrapper.hasVideo ? View.VISIBLE : View.GONE);
            if (stepWrapper.hasVideo) {
                final SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(getContext());
                final DataSource.Factory dataSourceFactory =
                        new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "udacity-baking-app"));
                final MediaSource videoSource =
                        new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(stepWrapper.step.videoURL));
                player.prepare(videoSource);

                playerView.setPlayer(player);
            }

            shortDescription.setText(stepWrapper.step.shortDescription);
            description.setText(stepWrapper.step.description);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle bundle = getArguments();
        viewModel.loadStep(bundle.getInt(KEY_RECIPE_ID), bundle.getInt(KEY_STEP_ID));
    }
}
