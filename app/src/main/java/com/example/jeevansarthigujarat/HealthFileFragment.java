package com.example.jeevansarthigujarat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HealthFileFragment extends Fragment {
Button profile;


    public HealthFileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_file, container, false);

        profile = view.findViewById(R.id.profile_activity_button);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* .edit().clear().commit();*/
                new ToastIntentClass(getActivity(), LoginActivity.class);
            }
        });

return  view;

    }
}