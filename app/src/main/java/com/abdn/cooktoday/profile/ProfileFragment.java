package com.abdn.cooktoday.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.APIRepository;
import com.abdn.cooktoday.api_connection.jsonmodels.LogoutMessageJSONModel;
import com.abdn.cooktoday.help.HelpActivity;
import com.abdn.cooktoday.local_data.Cache;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.onboarding.OnBoardingActivity;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ProfileFragment
 *
 * Contains information about the user and allows the user to logout.
 *
 * @author Team Alpha, University of Aberdeen
 */
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

        // set username
        String userFName = "Anakin";
        String loggedInUserFName = LoggedInUser.user().getFistName();
        if (!loggedInUserFName.equals(""))
            userFName = loggedInUserFName;
        ((TextView) layout.findViewById(R.id.tvHelloAnakin)).setText("Hello, " + userFName);

        handleLogoutClicked(layout);
        handleHelpClicked(layout);

        return layout;
    }

    private void handleHelpClicked(View layout) {
        LinearLayout helpLayout = layout.findViewById(R.id.llProfile_Help);
        helpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleLogoutClicked(View view) {
        Cache.init(getContext());

        LinearLayout logout = view.findViewById(R.id.llProfileLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cache.write_bool(Cache.KEY_USER_LOGGED_IN, false);

                // make logout request to server (on background thread):
                Executor logoutExec = new Executor() {
                    @Override
                    public void execute(Runnable runnable) {
                        runnable.run();
                    }
                };
                logoutExec.execute(new Runnable() {
                    @Override
                    public void run() {
                        APIRepository.getInstance().getUserService().logoutUser().enqueue(new Callback<LogoutMessageJSONModel>() {
                            @Override
                            public void onResponse(Call<LogoutMessageJSONModel> call, Response<LogoutMessageJSONModel> r) {
                                if (r.code() == 200) {
                                    Log.i("ProfileFragment", "User logged out SUCCESSFULLY!");
                                } else {
                                    Log.i("ProfileFragment", "User logout ERROR!");
                                }
                            }

                            @Override
                            public void onFailure(Call<LogoutMessageJSONModel> call, Throwable t) {
                                Log.i("ProfileFragment", "User logout ERROR!");
                            }
                        });
                    }
                });

                startActivity(new Intent(getActivity(), OnBoardingActivity.class));
                getActivity().finish();
            }
        });
    }
}