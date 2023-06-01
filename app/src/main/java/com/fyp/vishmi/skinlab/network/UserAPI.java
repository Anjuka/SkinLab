package com.fyp.vishmi.skinlab.network;

import com.fyp.vishmi.skinlab.model.PredictRespones;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPI {
    /**
     * Predict Request
     *
     * @param img_face
     * @return
     */

    /*@POST("/api/v0.1/prediction")
    Call<PredictRespones> predictSkinType(@Body File img_face);*/

    @Multipart
    @POST("/api/v0.1/prediction")
    Call<PredictRespones> predictSkinType(
            @Part MultipartBody.Part photo);

}
