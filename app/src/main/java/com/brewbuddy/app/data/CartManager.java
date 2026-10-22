package com.brewbuddy.app.data;

import com.brewbuddy.app.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    public interface OnCartChangeListener {
        void onCartChanged();
    }

    private static CartManager instance;
    private final List<CartItem> cartItems;
    private final List<OnCartChangeListener> listeners;

    private CartManager() {
        cartItems = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addListener(OnCartChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(OnCartChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (OnCartChangeListener listener : new ArrayList<>(listeners)) {
            listener.onCartChanged();
        }
    }

    public void addItem(CartItem newItem) {
        // Check if an identical customization already exists in cart
        for (CartItem existing : cartItems) {
            if (isSameConfiguration(existing, newItem)) {
                existing.setQuantity(existing.getQuantity() + newItem.getQuantity());
                notifyListeners();
                return;
            }
        }
        cartItems.add(newItem);
        notifyListeners();
    }

    public void updateQuantity(String cartItemId, int newQuantity) {
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.getCartItemId().equals(cartItemId)) {
                if (newQuantity <= 0) {
                    cartItems.remove(i);
                } else {
                    item.setQuantity(newQuantity);
                }
                notifyListeners();
                return;
            }
        }
    }

    public void removeItem(String cartItemId) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getCartItemId().equals(cartItemId)) {
                cartItems.remove(i);
                notifyListeners();
                return;
            }
        }
    }

    public void clearCart() {
        cartItems.clear();
        notifyListeners();
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public int getItemCount() {
        int count = 0;
        for (CartItem item : cartItems) {
            count += item.getQuantity();
        }
        return count;
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : cartItems) {
            subtotal += item.calculateTotalPrice();
        }
        return subtotal;
    }

    public double getTax() {
        return getSubtotal() * 0.08;
    }

    public double getGrandTotal() {
        return getSubtotal() + getTax();
    }

    private boolean isSameConfiguration(CartItem a, CartItem b) {
        return a.getCoffeeItem().getId().equals(b.getCoffeeItem().getId())
                && a.getSize() == b.getSize()
                && a.isIced() == b.isIced()
                && a.hasExtraShot() == b.hasExtraShot()
                && a.hasVanilla() == b.hasVanilla()
                && a.hasCaramel() == b.hasCaramel()
                && a.hasWhippedCream() == b.hasWhippedCream()
                && a.hasChocolate() == b.hasChocolate();
    }
}
