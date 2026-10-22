package com.brewbuddy.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brewbuddy.app.R;
import com.brewbuddy.app.adapter.CartAdapter;
import com.brewbuddy.app.data.CartManager;
import com.brewbuddy.app.model.CartItem;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment implements CartManager.OnCartChangeListener {

    public interface NavigationListener {
        void navigateToMenu();
    }

    private NavigationListener navigationListener;

    private LinearLayout layoutEmptyCart;
    private LinearLayout layoutCartContent;
    private TextView tvSubtotal;
    private TextView tvTax;
    private TextView tvGrandTotal;
    private CartAdapter adapter;

    public void setNavigationListener(NavigationListener listener) {
        this.navigationListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutEmptyCart = view.findViewById(R.id.layout_empty_cart);
        layoutCartContent = view.findViewById(R.id.layout_cart_content);
        tvSubtotal = view.findViewById(R.id.tv_subtotal);
        tvTax = view.findViewById(R.id.tv_tax);
        tvGrandTotal = view.findViewById(R.id.tv_grand_total);
        MaterialButton btnBrowseEmpty = view.findViewById(R.id.btn_browse_menu_empty);
        MaterialButton btnCheckout = view.findViewById(R.id.btn_checkout);
        RecyclerView rvCart = view.findViewById(R.id.rv_cart_items);

        btnBrowseEmpty.setOnClickListener(v -> {
            if (navigationListener != null) {
                navigationListener.navigateToMenu();
            }
        });

        btnCheckout.setOnClickListener(v -> {
            if (getActivity() != null && CartManager.getInstance().getItemCount() > 0) {
                Intent intent = new Intent(requireActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });

        adapter = new CartAdapter(CartManager.getInstance().getCartItems(), new CartAdapter.OnCartItemActionListener() {
            @Override
            public void onQuantityChanged(String cartItemId, int newQuantity) {
                CartManager.getInstance().updateQuantity(cartItemId, newQuantity);
            }

            @Override
            public void onItemRemoved(String cartItemId) {
                CartManager.getInstance().removeItem(cartItemId);
            }
        });

        rvCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCart.setAdapter(adapter);

        updateCartUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        CartManager.getInstance().addListener(this);
        updateCartUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        CartManager.getInstance().removeListener(this);
    }

    @Override
    public void onCartChanged() {
        if (isAdded()) {
            updateCartUI();
        }
    }

    private void updateCartUI() {
        List<CartItem> items = CartManager.getInstance().getCartItems();
        if (items.isEmpty()) {
            layoutEmptyCart.setVisibility(View.VISIBLE);
            layoutCartContent.setVisibility(View.GONE);
        } else {
            layoutEmptyCart.setVisibility(View.GONE);
            layoutCartContent.setVisibility(View.VISIBLE);

            adapter.updateList(items);
            tvSubtotal.setText(String.format(Locale.US, "$%.2f", CartManager.getInstance().getSubtotal()));
            tvTax.setText(String.format(Locale.US, "$%.2f", CartManager.getInstance().getTax()));
            tvGrandTotal.setText(String.format(Locale.US, "$%.2f", CartManager.getInstance().getGrandTotal()));
        }
    }
}
