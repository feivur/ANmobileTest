package com.example.anmobiletest.api.camera;

import com.example.anmobiletest.api.NetworkService;
import com.example.anmobiletest.api.pojomodels.camera.Camera;
import com.example.anmobiletest.api.pojomodels.Post;
import com.example.anmobiletest.api.pojomodels.camera.allCameras;
import com.example.anmobiletest.api.pojomodels.event.Event;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class PostDataMaker {
    GetApiMethods getApiMethods = NetworkService.getInstance().createService(GetApiMethods.class);
    Map<String, String> camerasNameIdMap = new HashMap<>();

    public Observable<Post> getPostsObservable(int limit, int offset, int join) {
        if (camerasNameIdMap.isEmpty()) {
            getCameraNameMap(camerasNameIdMap);
        }
        return
                getEvents(limit, offset, join)
                        .map(event -> new Post(event.getSource(),camerasNameIdMap.get(event.getSource()), event.getType(), event.getTimestamp()));

    }


    public Observable<Event> getEvents(int limit, int offset, int join) {
        return getApiMethods.getEvents(limit, offset, join).concatMapIterable(events -> events);
    }

    public void getCameraNameMap(Map<String, String> camerasNameIdMap)
    {
        getCamera().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Camera>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ArrayList<Camera> camera) {
                        for (int i=0; i<camera.size();i++) {
                            camerasNameIdMap.put(camera.get(i).getArchives().get(0).getAccessPoint(), camera.get(i).getDisplayName());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public Observable<ArrayList<Camera>> getCamera() {
        return getApiMethods.getCameras().map(allCameras::getCameras);
    }

}
