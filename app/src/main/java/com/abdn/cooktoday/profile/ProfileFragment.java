package com.abdn.cooktoday.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.local_data.Cache;
import com.abdn.cooktoday.onboarding.OnBoardingActivity;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);

        handleLogoutClicked(layout);

        return layout;
    }

    private void handleLogoutClicked(View view) {
        Cache.init(getContext());

        LinearLayout logout = view.findViewById(R.id.llProfileLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cache.write_bool(Cache.KEY_USER_LOGGED_IN, false);
                startActivity(new Intent(getActivity(), OnBoardingActivity.class));
                getActivity().finish();
            }
        });
    }
}