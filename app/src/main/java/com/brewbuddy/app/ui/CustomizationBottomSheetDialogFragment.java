package com.brewbuddy.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.brewbuddy.app.R;
import com.brewbuddy.app.data.CartManager;
import com.brewbuddy.app.model.CartItem;
import com.brewbuddy.app.model.CoffeeItem;
import com.brewbuddy.app.model.Size;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class CustomizationBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_COFFEE_ITEM = "arg_coffee_item";

    private CoffeeItem coffeeItem;
    private int quantity = 1;

    private ImageView imgCoffee;
    private TextView tvName;
    private TextView tvDesc;
    private RadioGroup rgTemperature;
    private RadioButton rbIced;
    private RadioGroup rgSize;
    private RadioButton rbSizeSmall;
    private RadioButton rbSizeLarge;
    private CheckBox cbExtraShot;
    private CheckBox cbVanilla;
    private CheckBox cbCaramel;
    private CheckBox cbWhippedCream;
    private CheckBox cbChocolate;
    private TextView tvQuantity;
    private TextView tvTotalPrice;

    public static CustomizationBottomSheetDialogFragment newInstance(CoffeeItem coffeeItem) {
        CustomizationBottomSheetDialogFragment fragment = new CustomizationBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COFFEE_ITEM, coffeeItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            coffeeItem = (CoffeeItem) getArguments().getSerializable(ARG_COFFEE_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_customization, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (coffeeItem == null) {
            dismiss();
            return;
        }

        ImageButton btnClose = view.findViewById(R.id.btn_close_customization);
        imgCoffee = view.findViewById(R.id.img_dialog_coffee);
        tvName = view.findViewById(R.id.tv_dialog_coffee_name);
        tvDesc = view.findViewById(R.id.tv_dialog_coffee_desc);
        rgTemperature = view.findViewById(R.id.rg_temperature);
        rbIced = view.findViewById(R.id.rb_iced);
        rgSize = view.findViewById(R.id.rg_size);
        rbSizeSmall = view.findViewById(R.id.rb_size_small);
        rbSizeLarge = view.findViewById(R.id.rb_size_large);
        cbExtraShot = view.findViewById(R.id.cb_extra_shot);
        cbVanilla = view.findViewById(R.id.cb_vanilla);
        cbCaramel = view.findViewById(R.id.cb_caramel);
        cbWhippedCream = view.findViewById(R.id.cb_whipped_cream);
        cbChocolate = view.findViewById(R.id.cb_chocolate);
        tvQuantity = view.findViewById(R.id.tv_dialog_quantity);
        tvTotalPrice = view.findViewById(R.id.tv_dialog_total_price);
        ImageButton btnDecrement = view.findViewById(R.id.btn_dialog_decrement);
        ImageButton btnIncrement = view.findViewById(R.id.btn_dialog_increment);
        MaterialButton btnAddToCart = view.findViewById(R.id.btn_add_to_cart);

        btnClose.setOnClickListener(v -> dismiss());

        imgCoffee.setImageResource(coffeeItem.getImageResId());
        tvName.setText(coffeeItem.getName());
        tvDesc.setText(coffeeItem.getDescription());

        if (coffeeItem.getCategory() == com.brewbuddy.app.model.Category.ICED) {
            rbIced.setChecked(true);
        }

        View.OnClickListener updatePriceListener = v -> updatePriceDisplay();

        rgTemperature.setOnCheckedChangeListener((group, checkedId) -> updatePriceDisplay());
        rgSize.setOnCheckedChangeListener((group, checkedId) -> updatePriceDisplay());
        cbExtraShot.setOnClickListener(updatePriceListener);
        cbVanilla.setOnClickListener(updatePriceListener);
        cbCaramel.setOnClickListener(updatePriceListener);
        cbWhippedCream.setOnClickListener(updatePriceListener);
        cbChocolate.setOnClickListener(updatePriceListener);

        btnDecrement.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updatePriceDisplay();
            }
        });

        btnIncrement.setOnClickListener(v -> {
            if (quantity < 10) {
                quantity++;
                tvQuantity.setText(String.valueOf(quantity));
                updatePriceDisplay();
            }
        });

        btnAddToCart.setOnClickListener(v -> {
            CartItem item = buildCartItem();
            CartManager.getInstance().addItem(item);
            Toast.makeText(requireContext(),
                    getString(R.string.item_added_to_cart, coffeeItem.getName()),
                    Toast.LENGTH_SHORT).show();
            dismiss();
        });

        updatePriceDisplay();
    }

    private Size getSelectedSize() {
        if (rbSizeSmall.isChecked()) return Size.SMALL;
        if (rbSizeLarge.isChecked()) return Size.LARGE;
        return Size.MEDIUM;
    }

    private CartItem buildCartItem() {
        Size size = getSelectedSize();
        boolean isIced = rbIced.isChecked();
        boolean extraShot = cbExtraShot.isChecked();
        boolean vanilla = cbVanilla.isChecked();
        boolean caramel = cbCaramel.isChecked();
        boolean whipped = cbWhippedCream.isChecked();
        boolean chocolate = cbChocolate.isChecked();

        return new CartItem(coffeeItem, size, isIced, extraShot, vanilla, caramel, whipped, chocolate, quantity);
    }

    private void updatePriceDisplay() {
        CartItem tempItem = buildCartItem();
        tvTotalPrice.setText(String.format(Locale.US, "$%.2f", tempItem.calculateTotalPrice()));
    }
}
