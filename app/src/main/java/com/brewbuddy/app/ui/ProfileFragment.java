package com.brewbuddy.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.brewbuddy.app.R;
import com.brewbuddy.app.data.ThemeManager;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwitchMaterial switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        if (switchDarkMode != null) {
            switchDarkMode.setChecked(ThemeManager.isDarkModeEnabled(requireContext()));
            switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked != ThemeManager.isDarkModeEnabled(requireContext())) {
                    ThemeManager.setDarkModeEnabled(requireContext(), isChecked);
                }
            });
        }
    }
}
