package com.example.anmobiletest.api.camera;


import com.example.anmobiletest.api.pojomodels.camera.allCameras;
import com.example.anmobiletest.api.pojomodels.event.AllEvents;


import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetApiMethods {

    @GET("camera/list")
    Observable<allCameras> getCameras();

    @GET("archive/events/detectors{videoSourceId}/past/future")
    Observable<AllEvents> getEvents(@Path(value = "videoSourceId", encoded = true) String videoSourceId, @Query("limit") int limit, @Query("offset") int offset, @Query("join") int join);

    @GET("archive/media{videoSourceId}/{time}/")
    Observable<ResponseBody> getDetectorImage(@Path(value = "videoSourceId", encoded = true) String videoSourceId, @Path(value = "time", encoded = true) String time);

    @GET("archive/events/detectors/")
    Observable<AllEvents> getEventsAllCamera();

    @GET ("{baseUrl}/product/version/")
    Observable<ResponseBody> getVersion(@Path(value = "baseUrl", encoded = true) String baseUrl);


}
