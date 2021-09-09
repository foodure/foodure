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

public class RequestedFoodAdapter extends RecyclerView.Adapter<RequestedFoodAdapter.ViewHolder> {

    final List<FoodPost> foodDetailsList;
    final RequestedFoodAdapter.OnFoodClickListener onFoodClickListener;

    public interface OnFoodClickListener {
        void onRequestFood(int position);

        void onItemClick(int position);
    }

    public RequestedFoodAdapter(List<FoodPost> foodDetailsList, RequestedFoodAdapter.OnFoodClickListener onFoodClickListener) {
        this.foodDetailsList = foodDetailsList;
        this.onFoodClickListener = onFoodClickListener;
    }

    @NonNull
    @Override
    public RequestedFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_requested_food, parent, false);
        return new RequestedFoodAdapter.ViewHolder(view, onFoodClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedFoodAdapter.ViewHolder holder, int position) {
        FoodPost foodPost = foodDetailsList.get(position);
        holder.restaurantLabel.setText(foodPost.getRestaurant().getTitle());
        holder.foodLabel.setText(foodPost.getTitle());
        String str = "Quantity: " + foodPost.getQuantity();
        holder.quantityLabel.setText(str);


    }


    @Override
    public int getItemCount() {
        return this.foodDetailsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView restaurantLabel;
        final TextView foodLabel;
        final TextView quantityLabel;



        public ViewHolder(@NonNull View itemView, RequestedFoodAdapter.OnFoodClickListener onFoodClickListener) {
            super(itemView);
            restaurantLabel = itemView.findViewById(R.id.requestedFood_label_card);
            foodLabel = itemView.findViewById(R.id.food_title_label_card);
            quantityLabel = itemView.findViewById(R.id.reqQuantity_label_card);

            itemView.setOnClickListener(view -> {
                onFoodClickListener.onItemClick(getBindingAdapterPosition());
            });
        }
    }
}
