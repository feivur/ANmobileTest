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
    @Headers({
            "User-Agent: OkHttp",
            "Content-Type: application/json"
    })
    @GET("camera/list")
    Observable<allCameras> getCameras();


    @Headers({
            "User-Agent: OkHttp",
            "Content-Type: application/json"
    })
    @GET("archive/events/detectors{videoSourceId}/past/future")
    Observable<AllEvents> getEvents(@Path(value = "videoSourceId", encoded = true) String videoSourceId, @Query("limit") int limit, @Query("offset") int offset, @Query("join") int join);

    @Headers({
            "User-Agent: OkHttp",
            "content-type: image/jpeg"
    })
    @GET("archive/media{videoSourceId}/{time}/")
    Observable<ResponseBody> getDetectorImage(@Path(value = "videoSourceId", encoded = true) String videoSourceId, @Path(value = "time", encoded = true) String time);


    @Headers({
            "User-Agent: OkHttp",
            "Content-Type: application/json"

    })
    @GET("archive/events/detectors/")
    Observable<AllEvents> getEventsAllCamera();


    @Headers({
            "User-Agent: OkHttp Example",
            "Content-Type: application/json"

    })
    @GET
    Observable<ResponseBody> getVersion(@Url String fullUrl);


}
