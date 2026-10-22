package com.brewbuddy.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brewbuddy.app.R;
import com.brewbuddy.app.adapter.CoffeeAdapter;
import com.brewbuddy.app.data.CoffeeRepository;
import com.brewbuddy.app.model.CoffeeItem;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class HomeFragment extends Fragment {

    public interface NavigationListener {
        void navigateToMenu();
    }

    private NavigationListener navigationListener;

    public void setNavigationListener(NavigationListener listener) {
        this.navigationListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CoffeeItem featuredItem = CoffeeRepository.getFeaturedItem();
        MaterialButton btnOrderFeatured = view.findViewById(R.id.btn_order_featured);
        TextView tvExploreMenu = view.findViewById(R.id.btn_explore_menu);
        RecyclerView rvPopular = view.findViewById(R.id.rv_popular_coffee);

        btnOrderFeatured.setOnClickListener(v -> {
            CustomizationBottomSheetDialogFragment dialog =
                    CustomizationBottomSheetDialogFragment.newInstance(featuredItem);
            dialog.show(getParentFragmentManager(), "customization_dialog");
        });

        tvExploreMenu.setOnClickListener(v -> {
            if (navigationListener != null) {
                navigationListener.navigateToMenu();
            }
        });

        List<CoffeeItem> popularList = CoffeeRepository.getPopularItems();
        CoffeeAdapter adapter = new CoffeeAdapter(popularList, coffeeItem -> {
            CustomizationBottomSheetDialogFragment dialog =
                    CustomizationBottomSheetDialogFragment.newInstance(coffeeItem);
            dialog.show(getParentFragmentManager(), "customization_dialog");
        });

        rvPopular.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvPopular.setAdapter(adapter);
    }
}
