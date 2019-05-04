package com.raymond.udacity.bakingapp.util;

import android.os.Bundle;

import androidx.collection.ArrayMap;
import androidx.core.util.Pair;

import com.raymond.udacity.bakingapp.SimpleFragmentHolderActivity;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment;

import java.util.Map;

public final class Util {
    private Util(){}

    public static Map<String, String> bundleToMap(Bundle bundle) {
        final ArrayMap<String, String> map = new ArrayMap<>();
        for (String s : bundle.keySet()) {
            map.put(s, String.valueOf(bundle.get(s)));
        }
        return map;
    }

    public static Pair<Bundle, Bundle> createMasterDetailBundleFromRecipe(Recipe recipe) {
        final Bundle masterBundle = new Bundle();
        masterBundle.putInt(RecipeStepListFragment.KEY_RECIPE_ID, recipe.id);
        masterBundle.putBoolean(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED, true);
        masterBundle.putString(SimpleFragmentHolderActivity.KEY_TITLE, recipe.name);

        final Bundle detailBundle = new Bundle();
        detailBundle.putInt(RecipeAllDetailFragment.KEY_RECIPE_ID, recipe.id);
        detailBundle.putInt(RecipeAllDetailFragment.KEY_STEP_ID, 0);

        return Pair.create(masterBundle, detailBundle);
    }
}
