package com.example.anmobiletest.api.camera;

import com.example.anmobiletest.api.NetworkService;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;

public class GetCameraData {
    GetApiMethods getApiMethods = NetworkService.getInstance().createService(GetApiMethods.class);

    public Observable<ResponseBody> getDetectorImageByte(String cameraId, String timestamp) {
        return getApiMethods.getDetectorImage(cameraId, timestamp);
    }

}
