package com.example.anmobiletest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.example.anmobiletest.api.camera.PostDataMaker;
import com.example.anmobiletest.api.pojomodels.Post;
import com.example.anmobiletest.api.pojomodels.event.Event;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ServerChangedService extends IntentService {
    private final PostDataMaker cameraRQ = new PostDataMaker();
    String prevTime = "0";

    public ServerChangedService() {
        super("newEventListener");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cameraRQ.getEvents(1,0,0).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Event>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Event event) {
                            if (!event.getTimestamp().equals(prevTime)) {
                                prevTime = event.getTimestamp();
                                Log.d("asd","asd23");
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Новое событие детектора", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }

    }
}