package com.abdn.cooktoday.local_data;

import com.abdn.cooktoday.api_connection.APIRepository;
import com.abdn.cooktoday.api_connection.jsonmodels.UserJSONModel;
import com.abdn.cooktoday.local_data.model.LoggedInUser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private LoggedInUser loggedInUser;

    public Result<LoggedInUser>
    login(String username, String password) {
        try {
            loggedInUser = null;

            // log in user
            APIRepository.getInstance().getUserService().loginUser(username, password).enqueue(new Callback<UserJSONModel>() {
                @Override
                public void onResponse(Call<UserJSONModel> call, Response<UserJSONModel> r) {
                    System.out.println("USER LOGGED IN: " + r.body().getEmail() + ", " + r.body().getFirstName() + ", " + r.body().getLastName());
                    UserJSONModel user = r.body();
                    LoggedInUser loggedInUser = new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            user.getFirstName() + " " + user.getLastName(),
                            user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getRole()
                    );
                    // Toast.makeText(getApplicationContext(), "Comment " + r.body().getComment() + " created", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<UserJSONModel> call, Throwable t) {
                    System.out.println("ERROR LOGGING IN USER: " + t.getMessage());
                    // Toast.makeText(getApplicationContext(), "Error Creating Comment: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            if (loggedInUser == null)
                throw new Exception();

            return new Result.Success<>(loggedInUser);

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public void register() {
        // TODO
    }
}