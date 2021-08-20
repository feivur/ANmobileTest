package com.example.anmobiletest.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.anmobiletest.R;
import com.example.anmobiletest.api.NetworkService;
import com.example.anmobiletest.api.camera.GetApiMethods;
import com.example.anmobiletest.database.UserData;
import com.example.anmobiletest.user.User;
import com.jakewharton.rxbinding.view.RxView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.ResponseBody;

public class ConnectionFragment extends Fragment {
    GetApiMethods getApiMethods;
    View root;
    User user;
    Map<String, String> header = new HashMap<>();

    public ConnectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        user = UserData.getUser();
        root = inflater.inflate(R.layout.fragment_connection, container, false);

        EditText inputUrl = root.findViewById(R.id.url_input);
        EditText loginText = root.findViewById(R.id.login_input);
        EditText passwordText = root.findViewById(R.id.password_input);

        inputUrl.setHint(user.getUrl());
        loginText.setHint(user.getLogin());

        Toast serverCorrectToast = Toast.makeText(root.getContext(),
                "Сервер найден", Toast.LENGTH_SHORT);

        Toast serverIncorrectToast = Toast.makeText(root.getContext(),
                "Сервер не найден", Toast.LENGTH_SHORT);

        Button submit_button = root.findViewById(R.id.submit_input);

        RxView.clicks(submit_button)
                .subscribe(click -> {

                    String login = loginText.getText().toString();
                    String password = passwordText.getText().toString();
                    String url = inputUrl.getText().toString().replaceAll("/$", "") + "/";

                    header.put("Authorization", Credentials.basic(login, password));

                    getApiMethods = NetworkService.getInstance().createService(GetApiMethods.class);
                    getVersion(url, header)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseBody>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                }

                                @Override
                                public void onNext(@NonNull ResponseBody r) {

                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    serverIncorrectToast.show();

                                }

                                @Override
                                public void onComplete() {
                                    serverCorrectToast.show();

                                    UserData.addUser(root.getContext(), login, password, url);
                                }
                            });

                });
        return root;
    }

    public Observable<ResponseBody> getVersion(String url, Map<String, String> header) {
        return getApiMethods.getVersion(header, url);
    }
}