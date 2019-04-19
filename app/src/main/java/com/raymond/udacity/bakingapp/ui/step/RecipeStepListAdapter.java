package com.raymond.udacity.bakingapp.ui.step;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.models.db.Ingredient;
import com.raymond.udacity.bakingapp.models.db.Step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepListAdapter extends RecyclerView.Adapter {

    private final List<Step> steps = new ArrayList<>();
    private final List<Ingredient> ingredients = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.view_item_ingredients:
                return new IngredientVH(v);
            case R.layout.view_recipe_step:
                return new VH(v);
            default:
                throw new IllegalArgumentException("view typ not supported");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IngredientVH) {
            if (position == 0) {
                ((IngredientVH) holder).ingredientHeadline.setVisibility(View.VISIBLE);
            } else {
                ((IngredientVH) holder).ingredientHeadline.setVisibility(View.GONE);
            }

            final Ingredient ingredient = ingredients.get(position);
            ((IngredientVH) holder).ingredientName.setText(ingredient.ingredient);
            ((IngredientVH) holder).ingredientMeasure.setText(ingredient.quantity + " " + ingredient.measure);
        }

        if (holder instanceof VH) {
            final int offset = position - ingredients.size();
            if (offset == 0) {
                ((VH) holder).stepHeadline.setVisibility(View.VISIBLE);
            } else {
                ((VH) holder).stepHeadline.setVisibility(View.GONE);
            }
            final Step step = steps.get(offset);
            ((VH) holder).step.setText((offset + 1) + ". " + step.shortDescription);
            ((VH) holder).itemView.setTag(step);
        }
    }

    public void setData(Ingredient[] ingredients, Step[] steps) {
        this.ingredients.clear();
        this.ingredients.addAll(Arrays.asList(ingredients));

        this.steps.clear();
        this.steps.addAll(Arrays.asList(steps));

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ingredients.size() + steps.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < ingredients.size()) return R.layout.view_item_ingredients;
        return R.layout.view_recipe_step;
    }

    static class IngredientVH extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_headline)
        TextView ingredientHeadline;

        @BindView(R.id.ingredient_name)
        TextView ingredientName;

        @BindView(R.id.ingredient_measure)
        TextView ingredientMeasure;

        IngredientVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.step_headline)
        TextView stepHeadline;

        @BindView(R.id.step)
        TextView step;

        VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
