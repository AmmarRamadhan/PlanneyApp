package com.example.firebaseuserauthentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentMyPlan extends Fragment {
    ImageView myplancreate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myplan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myplancreate = getActivity().findViewById(R.id.newplancreate);
        myplancreate.setOnClickListener(v -> {
            Fragment fragment = new FragmentMyPlanCreate();
            getParentFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment).commit();
        });
    }
}
