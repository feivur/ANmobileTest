package com.example.anmobiletest.api.camera;


import com.example.anmobiletest.api.NetworkService;
import com.example.anmobiletest.api.pojomodels.camera.Camera;
import com.example.anmobiletest.api.pojomodels.Post;
import com.example.anmobiletest.api.pojomodels.event.Event;


import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;


public class PostDataMaker {
    GetApiMethods getApiMethods = NetworkService.getInstance().createService(GetApiMethods.class);
    Observable<Camera> cameras = getCamera();

    public Observable<Post> getPostsObservable(int limit, int offset, int join) {
        //i нечитаемо, такое надо форматировать
        return cameras.flatMap(camera ->
                getEvents(camera.getArchives().get(0).getAccessPoint().replace("hosts", ""), limit, offset, join)
                        .concatMap(event -> getdetectorImage(camera.getArchives().get(0)
                                .getAccessPoint().replace("hosts", ""), event.getTimestamp(), event.getId())
                                .map(image -> new Post(camera.getDisplayName(), image.bytes(), event.getType(), event.getTimestamp()))));

    }


    public Observable<Event> getEvents(String cameraName, int limit, int offset, int join) {
        return getApiMethods.getEvents(cameraName, limit, offset, join).concatMapIterable(events -> events);

    }


    public Observable<Camera> getCamera() {
        return getApiMethods.getCameras().concatMapIterable(camera -> camera);
    }

    public Observable<ResponseBody> getdetectorImage(String cameraId, String timestamp, String archiveID) {
        return getApiMethods.getDetectorImage(cameraId, timestamp);
    }


}
