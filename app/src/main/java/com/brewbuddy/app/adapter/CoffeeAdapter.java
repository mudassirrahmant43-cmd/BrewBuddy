package com.brewbuddy.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brewbuddy.app.R;
import com.brewbuddy.app.model.CoffeeItem;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder> {

    public interface OnCoffeeClickListener {
        void onCoffeeClick(CoffeeItem coffeeItem);
    }

    private List<CoffeeItem> coffeeList;
    private final OnCoffeeClickListener listener;

    public CoffeeAdapter(List<CoffeeItem> coffeeList, OnCoffeeClickListener listener) {
        this.coffeeList = coffeeList;
        this.listener = listener;
    }

    public void updateList(List<CoffeeItem> newList) {
        this.coffeeList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CoffeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coffee, parent, false);
        return new CoffeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeViewHolder holder, int position) {
        CoffeeItem item = coffeeList.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return coffeeList != null ? coffeeList.size() : 0;
    }

    static class CoffeeViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgCoffee;
        private final TextView tvCategoryBadge;
        private final TextView tvName;
        private final TextView tvDescription;
        private final TextView tvPrice;
        private final MaterialButton btnCustomize;

        public CoffeeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCoffee = itemView.findViewById(R.id.img_coffee);
            tvCategoryBadge = itemView.findViewById(R.id.tv_category_badge);
            tvName = itemView.findViewById(R.id.tv_coffee_name);
            tvDescription = itemView.findViewById(R.id.tv_coffee_description);
            tvPrice = itemView.findViewById(R.id.tv_coffee_price);
            btnCustomize = itemView.findViewById(R.id.btn_customize);
        }

        public void bind(CoffeeItem item, OnCoffeeClickListener listener) {
            imgCoffee.setImageResource(item.getImageResId());
            tvName.setText(item.getName());
            tvDescription.setText(item.getDescription());
            tvPrice.setText(String.format(Locale.US, "$%.2f", item.getBasePrice()));
            tvCategoryBadge.setText(item.getCategory().getDisplayName().toUpperCase());

            View.OnClickListener clickListener = v -> {
                if (listener != null) {
                    listener.onCoffeeClick(item);
                }
            };

            itemView.setOnClickListener(clickListener);
            btnCustomize.setOnClickListener(clickListener);
        }
    }
}
