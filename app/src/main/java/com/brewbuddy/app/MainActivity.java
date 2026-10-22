package com.brewbuddy.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.brewbuddy.app.data.CartManager;
import com.brewbuddy.app.data.ThemeManager;
import com.brewbuddy.app.ui.CartFragment;
import com.brewbuddy.app.ui.HomeFragment;
import com.brewbuddy.app.ui.MenuFragment;
import com.brewbuddy.app.ui.ProfileFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements CartManager.OnCartChangeListener {

    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment;
    private MenuFragment menuFragment;
    private CartFragment cartFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        homeFragment = new HomeFragment();
        menuFragment = new MenuFragment();
        cartFragment = new CartFragment();
        profileFragment = new ProfileFragment();

        homeFragment.setNavigationListener(() -> bottomNavigationView.setSelectedItemId(R.id.navigation_menu));
        cartFragment.setNavigationListener(() -> bottomNavigationView.setSelectedItemId(R.id.navigation_menu));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                selectedFragment = homeFragment;
            } else if (itemId == R.id.navigation_menu) {
                selectedFragment = menuFragment;
            } else if (itemId == R.id.navigation_cart) {
                selectedFragment = cartFragment;
            } else if (itemId == R.id.navigation_profile) {
                selectedFragment = profileFragment;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });

        updateCartBadge();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CartManager.getInstance().addListener(this);
        updateCartBadge();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CartManager.getInstance().removeListener(this);
    }

    @Override
    public void onCartChanged() {
        updateCartBadge();
    }

    private void updateCartBadge() {
        if (bottomNavigationView == null) return;

        int itemCount = CartManager.getInstance().getItemCount();
        if (itemCount > 0) {
            BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.navigation_cart);
            badge.setVisible(true);
            badge.setNumber(itemCount);
            badge.setBackgroundColor(ContextCompat.getColor(this, R.color.accent_caramel));
        } else {
            bottomNavigationView.removeBadge(R.id.navigation_cart);
        }
    }
}
