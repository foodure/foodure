package com.foodure.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.FoodPost;
import com.foodure.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private final List<FoodPost> foodDetailsList;
    private final OnFoodClickListener onFoodClickListener;

    public interface OnFoodClickListener {
        void onDeleteFood(int position);
    }

    public MainAdapter(List<FoodPost> foodDetailsList, OnFoodClickListener onFoodClickListener) {
        this.foodDetailsList = foodDetailsList;
        this.onFoodClickListener = onFoodClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view, onFoodClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        FoodPost foodPost = foodDetailsList.get(position);
        holder.restaurantLabel.setText(foodPost.getRestaurant().getTitle());
        holder.foodLabel.setText(foodPost.getTitle());
        holder.quantityLabel.setText(foodPost.getQuantity());
        holder.locationLabel.setText(foodPost.getLocation());

    }


    @Override
    public int getItemCount() {
        return this.foodDetailsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView restaurantLabel;
        private final TextView foodLabel;
        private final TextView quantityLabel;
        private final TextView locationLabel;


        public ViewHolder(@NonNull View itemView, OnFoodClickListener onFoodClickListener) {
            super(itemView);
            restaurantLabel = itemView.findViewById(R.id.restaurant_label_card);
            foodLabel = itemView.findViewById(R.id.food_title_label_card);
            quantityLabel = itemView.findViewById(R.id.quantity_label_card);
            locationLabel = itemView.findViewById(R.id.food_type_label_card);

        }
    }
}
