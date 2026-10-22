package com.brewbuddy.app.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.brewbuddy.app.MainActivity;
import com.brewbuddy.app.R;
import com.brewbuddy.app.data.CartManager;
import com.brewbuddy.app.data.ThemeManager;
import com.brewbuddy.app.model.CartItem;
import com.brewbuddy.app.model.Order;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CheckoutActivity extends AppCompatActivity {

    private TextInputLayout tilName;
    private TextInputEditText etName;
    private TextInputEditText etNotes;
    private RadioGroup rgPickup;
    private RadioButton rbInStore;
    private TextView tvSummaryItems;
    private TextView tvSubtotal;
    private TextView tvTax;
    private TextView tvTotal;
    private MaterialButton btnConfirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ImageButton btnBack = findViewById(R.id.btn_back_checkout);
        tilName = findViewById(R.id.til_customer_name);
        etName = findViewById(R.id.et_customer_name);
        etNotes = findViewById(R.id.et_order_notes);
        rgPickup = findViewById(R.id.rg_pickup_method);
        rbInStore = findViewById(R.id.rb_pickup_instore);
        tvSummaryItems = findViewById(R.id.tv_checkout_items_summary);
        tvSubtotal = findViewById(R.id.tv_checkout_subtotal);
        tvTax = findViewById(R.id.tv_checkout_tax);
        tvTotal = findViewById(R.id.tv_checkout_total);
        btnConfirmOrder = findViewById(R.id.btn_confirm_order);

        btnBack.setOnClickListener(v -> finish());

        populateSummary();

        btnConfirmOrder.setOnClickListener(v -> processCheckout());
    }

    private void populateSummary() {
        List<CartItem> items = CartManager.getInstance().getCartItems();
        if (items.isEmpty()) {
            finish();
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (CartItem item : items) {
            sb.append(item.getQuantity()).append("x ")
                    .append(item.getCoffeeItem().getName())
                    .append(" (").append(String.format(Locale.US, "$%.2f", item.calculateTotalPrice())).append(")\n");
        }

        tvSummaryItems.setText(sb.toString().trim());
        tvSubtotal.setText(String.format(Locale.US, "$%.2f", CartManager.getInstance().getSubtotal()));
        tvTax.setText(String.format(Locale.US, "$%.2f", CartManager.getInstance().getTax()));
        tvTotal.setText(String.format(Locale.US, "$%.2f", CartManager.getInstance().getGrandTotal()));
    }

    private void processCheckout() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        if (TextUtils.isEmpty(name)) {
            tilName.setError(getString(R.string.error_name_required));
            return;
        }
        tilName.setError(null);

        btnConfirmOrder.setEnabled(false);

        String pickupMethod = rbInStore.isChecked() ?
                getString(R.string.pickup_instore) : getString(R.string.pickup_curbside);
        String notes = etNotes.getText() != null ? etNotes.getText().toString().trim() : "";

        String orderId = "BB-" + (1000 + new Random().nextInt(9000));

        Order order = new Order(
                orderId,
                name,
                pickupMethod,
                notes,
                CartManager.getInstance().getCartItems(),
                CartManager.getInstance().getSubtotal(),
                CartManager.getInstance().getTax(),
                CartManager.getInstance().getGrandTotal()
        );

        // Clear cart after successful order placement
        CartManager.getInstance().clearCart();

        showOrderConfirmationDialog(order);
    }

    private void showOrderConfirmationDialog(Order order) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_order_confirmation);
        dialog.setCancelable(false);

        TextView tvRef = dialog.findViewById(R.id.tv_order_ref);
        TextView tvMsg = dialog.findViewById(R.id.tv_order_msg);
        MaterialButton btnHome = dialog.findViewById(R.id.btn_back_home);

        tvRef.setText(getString(R.string.order_ref_format, order.getOrderId()));
        tvMsg.setText(getString(R.string.order_confirmed_msg, order.getCustomerName()));

        btnHome.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        dialog.show();
    }
}
