package com.example.anmobiletest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.anmobiletest.api.NetworkService;
import com.example.anmobiletest.api.camera.GetApiMethods;
import com.example.anmobiletest.api.pojomodels.event.Event;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ServerChangedService extends Service {
    GetApiMethods getApiMethods = NetworkService.getInstance().createService(GetApiMethods.class);
    List<String> listEvent= new ArrayList<>();
    List<String> listEventBuf= new ArrayList<>();
    public ServerChangedService() {

    }


    public Observable<Event> getEvents() {
        return getApiMethods.getEventsAllCamera().concatMapIterable(events -> events);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getEvents().map(event -> event)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Event>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Event event) {
                        listEvent.add(event.getTimestamp());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        if (!listEvent.equals(listEventBuf))
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Новое срабатывание детектора", Toast.LENGTH_SHORT);

            toast.show();
        }
        else {
            Log.d("Server", listEvent.toString());
            Log.d("Server buffer", listEventBuf.toString());
        }
        listEventBuf=listEvent;


        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}