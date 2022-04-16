package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.media.AwsUploadedFilesJson;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MediaService {

    // explanation at https://stackoverflow.com/a/38891018/4745591
    @Multipart
    @POST("media/recipe")
    Call<AwsUploadedFilesJson> uploadRecipeImagesToAws(
            @Header("Cookie") String userSessId,
            @Part MultipartBody.Part media
    );
}
