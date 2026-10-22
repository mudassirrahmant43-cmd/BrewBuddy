package com.brewbuddy.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brewbuddy.app.R;
import com.brewbuddy.app.adapter.CoffeeAdapter;
import com.brewbuddy.app.data.CoffeeRepository;
import com.brewbuddy.app.model.Category;
import com.brewbuddy.app.model.CoffeeItem;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class MenuFragment extends Fragment {

    private CoffeeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChipGroup chipGroup = view.findViewById(R.id.chip_group_categories);
        RecyclerView rvMenu = view.findViewById(R.id.rv_menu_coffee);

        List<CoffeeItem> allCoffee = CoffeeRepository.getAllCoffeeItems();
        adapter = new CoffeeAdapter(allCoffee, coffeeItem -> {
            CustomizationBottomSheetDialogFragment dialog =
                    CustomizationBottomSheetDialogFragment.newInstance(coffeeItem);
            dialog.show(getParentFragmentManager(), "customization_dialog");
        });

        rvMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvMenu.setAdapter(adapter);

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chip_hot) {
                adapter.updateList(CoffeeRepository.getByCategory(Category.HOT));
            } else if (checkedId == R.id.chip_iced) {
                adapter.updateList(CoffeeRepository.getByCategory(Category.ICED));
            } else if (checkedId == R.id.chip_specialty) {
                adapter.updateList(CoffeeRepository.getByCategory(Category.SPECIALTY));
            } else {
                adapter.updateList(CoffeeRepository.getByCategory(Category.ALL));
            }
        });
    }
}
