package com.example.anmobiletest.api.camera;


import com.example.anmobiletest.api.pojomodels.camera.allCameras;
import com.example.anmobiletest.api.pojomodels.event.AllEvents;


import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetApiMethods {

    @GET("camera/list")
    Observable<allCameras> getCameras();

    @GET("archive/events/detectors/future/past")
    Observable<AllEvents> getEvents(@Query("limit") int limit, @Query("offset") int offset, @Query("join") int join);

    @GET("archive/media{videoSourceId}/{time}/")
    Observable<ResponseBody> getDetectorImage(@Path(value = "videoSourceId", encoded = true) String videoSourceId, @Path(value = "time", encoded = true) String time);


    @GET("archive/events/detectors/")
    Observable<AllEvents> getEventsAllCamera();

    @GET ("{baseUrl}product/version/")
    Observable<ResponseBody> getVersion(@HeaderMap Map<String, String> header,@Path(value = "baseUrl", encoded = true) String baseUrl);


}
