package com.raymond.udacity.bakingapp.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.models.db.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.VH> {
    private final List<Recipe> recipes = new ArrayList<>();
    private final View.OnClickListener clickListener;

    RecipeAdapter(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    void setRecipes(List<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recipe_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.thumbnail.setImageURI(recipes.get(position).image);
        holder.title.setText(recipes.get(position).name);
        holder.recipeCard.setTag(recipes.get(position));
        holder.recipeCard.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail)
        public SimpleDraweeView thumbnail;

        @BindView(R.id.title)
        public TextView title;

        @BindView(R.id.recipe_card)
        public CardView recipeCard;

        VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
