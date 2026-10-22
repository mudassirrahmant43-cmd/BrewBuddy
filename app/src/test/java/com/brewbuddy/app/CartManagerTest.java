package com.brewbuddy.app;

import com.brewbuddy.app.data.CartManager;
import com.brewbuddy.app.data.CoffeeRepository;
import com.brewbuddy.app.model.CartItem;
import com.brewbuddy.app.model.CoffeeItem;
import com.brewbuddy.app.model.Size;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CartManagerTest {

    @Before
    public void setUp() {
        CartManager.getInstance().clearCart();
    }

    @Test
    public void testCartItemPriceCalculation() {
        CoffeeItem latte = CoffeeRepository.getAllCoffeeItems().get(3); // Caffè Latte ($4.75)
        // Large size (+ $1.00), Extra shot (+ $1.00), Vanilla (+ $0.50)
        CartItem cartItem = new CartItem(latte, Size.LARGE, true, true, true, false, false, false, 2);

        // Unit price = 4.75 + 1.00 + 1.00 + 0.50 = $7.25
        assertEquals(7.25, cartItem.calculateUnitPrice(), 0.001);
        // Total price for 2 qty = $14.50
        assertEquals(14.50, cartItem.calculateTotalPrice(), 0.001);
    }

    @Test
    public void testCartManagerAddAndClear() {
        CoffeeItem espresso = CoffeeRepository.getAllCoffeeItems().get(0); // $3.50
        CartItem item1 = new CartItem(espresso, Size.SMALL, false, false, false, false, false, false, 1);

        CartManager.getInstance().addItem(item1);
        assertEquals(1, CartManager.getInstance().getItemCount());
        assertEquals(3.50, CartManager.getInstance().getSubtotal(), 0.001);

        CartManager.getInstance().clearCart();
        assertEquals(0, CartManager.getInstance().getItemCount());
        assertEquals(0.0, CartManager.getInstance().getSubtotal(), 0.001);
    }

    @Test
    public void testIdenticalCustomizationMerging() {
        CoffeeItem cappuccino = CoffeeRepository.getAllCoffeeItems().get(2);
        CartItem item1 = new CartItem(cappuccino, Size.MEDIUM, false, true, false, false, false, false, 1);
        CartItem item2 = new CartItem(cappuccino, Size.MEDIUM, false, true, false, false, false, false, 2);

        CartManager.getInstance().addItem(item1);
        CartManager.getInstance().addItem(item2);

        // Should merge into 1 cart entry with quantity 3
        assertEquals(1, CartManager.getInstance().getCartItems().size());
        assertEquals(3, CartManager.getInstance().getItemCount());
    }
}
