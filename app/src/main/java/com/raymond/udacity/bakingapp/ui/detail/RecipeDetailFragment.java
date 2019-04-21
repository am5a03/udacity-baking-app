package com.raymond.udacity.bakingapp.ui.detail;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import timber.log.Timber;

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
    @Nullable private SimpleExoPlayer player;

    Dialog fullscreenDialog;
    private boolean isExoPlayerFullscreen;

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
                player = ExoPlayerFactory.newSimpleInstance(getContext());
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

        fullscreenDialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            @Override
            public void onBackPressed() {
                if (isExoPlayerFullscreen) {
                    closeFullscreenDialog();
                }
                super.onBackPressed();
            }
        };

        if (!getResources().getBoolean(R.bool.is_twopane) && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            openFullscreenDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        closeFullscreenDialog();
        if (player != null) {
            player.release();
            playerView.setPlayer(null);
            Timber.d("release player");
        }
    }

    private void closeFullscreenDialog() {
        if (!getResources().getBoolean(R.bool.is_twopane)) {
            isExoPlayerFullscreen = false;
            fullscreenDialog.dismiss();
            getBaseActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void openFullscreenDialog() {
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        fullscreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        isExoPlayerFullscreen = true;
        fullscreenDialog.show();
    }
}
