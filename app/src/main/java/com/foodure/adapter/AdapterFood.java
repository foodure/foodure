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

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder> {
    private List<FoodPost> foodDetailsList;
    private OnFoodClickListener onFoodClickListener;

    public interface OnFoodClickListener {
        void onDeleteFood(int position) ;
        void onItemClick(int position) ;
    }

    public AdapterFood(List<FoodPost> foodDetailsList , OnFoodClickListener onFoodClickListener){
        this.foodDetailsList = foodDetailsList ;
        this.onFoodClickListener = onFoodClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_restaurant, parent , false);
        return new ViewHolder(view , onFoodClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFood.ViewHolder holder, int position) {
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

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView restaurantLabel ;
        private TextView foodLabel ;
        private TextView quantityLabel ;
        private TextView locationLabel ;
        private Button deleteButton ;


        public ViewHolder(@NonNull View itemView , OnFoodClickListener onFoodClickListener) {
            super(itemView);
            restaurantLabel  = itemView.findViewById(R.id.restaurantLabel);
            foodLabel = itemView.findViewById(R.id.foodLabel);
            quantityLabel  = itemView.findViewById(R.id.quantityLabel);
            locationLabel = itemView.findViewById(R.id.locationLabel);
            deleteButton = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(view -> {
                onFoodClickListener.onItemClick(getBindingAdapterPosition());
            });

            deleteButton.setOnClickListener(view -> {

                onFoodClickListener.onDeleteFood(getBindingAdapterPosition());
            });

        }
    }
}
