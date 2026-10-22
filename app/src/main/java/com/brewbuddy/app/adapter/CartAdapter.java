package com.brewbuddy.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brewbuddy.app.R;
import com.brewbuddy.app.model.CartItem;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface OnCartItemActionListener {
        void onQuantityChanged(String cartItemId, int newQuantity);
        void onItemRemoved(String cartItemId);
    }

    private List<CartItem> cartItems;
    private final OnCartItemActionListener listener;

    public CartAdapter(List<CartItem> cartItems, OnCartItemActionListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    public void updateList(List<CartItem> newList) {
        this.cartItems = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return cartItems != null ? cartItems.size() : 0;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgCoffee;
        private final TextView tvName;
        private final TextView tvSummary;
        private final TextView tvTotal;
        private final TextView tvQty;
        private final ImageButton btnDecrement;
        private final ImageButton btnIncrement;
        private final ImageButton btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCoffee = itemView.findViewById(R.id.img_cart_item);
            tvName = itemView.findViewById(R.id.tv_cart_item_name);
            tvSummary = itemView.findViewById(R.id.tv_cart_item_summary);
            tvTotal = itemView.findViewById(R.id.tv_cart_item_total);
            tvQty = itemView.findViewById(R.id.tv_cart_item_qty);
            btnDecrement = itemView.findViewById(R.id.btn_decrement_qty);
            btnIncrement = itemView.findViewById(R.id.btn_increment_qty);
            btnRemove = itemView.findViewById(R.id.btn_remove_item);
        }

        public void bind(CartItem item, OnCartItemActionListener listener) {
            imgCoffee.setImageResource(item.getCoffeeItem().getImageResId());
            tvName.setText(item.getCoffeeItem().getName());
            tvSummary.setText(item.getCustomizationSummary());
            tvTotal.setText(String.format(Locale.US, "$%.2f", item.calculateTotalPrice()));
            tvQty.setText(String.valueOf(item.getQuantity()));

            btnDecrement.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onQuantityChanged(item.getCartItemId(), item.getQuantity() - 1);
                }
            });

            btnIncrement.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onQuantityChanged(item.getCartItemId(), item.getQuantity() + 1);
                }
            });

            btnRemove.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemRemoved(item.getCartItemId());
                }
            });
        }
    }
}
