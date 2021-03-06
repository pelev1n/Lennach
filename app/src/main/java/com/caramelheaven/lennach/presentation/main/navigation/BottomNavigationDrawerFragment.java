package com.caramelheaven.lennach.presentation.main.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.caramelheaven.lennach.R;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    private NavigationView nvContainer;

    public static BottomNavigationDrawerFragment newInstance() {

        Bundle args = new Bundle();

        BottomNavigationDrawerFragment fragment = new BottomNavigationDrawerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_navigation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        nvContainer = view.findViewById(R.id.nv_container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nvContainer.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav1:
                    Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                    dismiss();
                    break;
                case R.id.nav2:
                    Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav3:
                    Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }
}
