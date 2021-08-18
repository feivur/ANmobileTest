package com.example.anmobiletest.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.anmobiletest.HomeActivity;
import com.example.anmobiletest.R;
import com.example.anmobiletest.api.NetworkService;
import com.example.anmobiletest.api.camera.GetApiMethods;
import com.jakewharton.processphoenix.ProcessPhoenix;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.example.anmobiletest.HomeActivity.APP_PREFERENCES_LOGIN;
import static com.example.anmobiletest.HomeActivity.APP_PREFERENCES_PASSWORD;
import static com.example.anmobiletest.HomeActivity.APP_PREFERENCES_URL;

public class ConnectionFragment extends Fragment {
    GetApiMethods getApiMethods;
    private Button submit_button;

    String url;
    View root;


    public ConnectionFragment() {
    }

    public static ConnectionFragment newInstance() {
        return new ConnectionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_connection, container, false);
        EditText inputUrl = root.findViewById(R.id.url_input);
        EditText loginText = root.findViewById(R.id.login_input);
        EditText passwordText = root.findViewById(R.id.password_input);
        SharedPreferences.Editor editor = HomeActivity.mSettings.edit();

        //E После ввода параметров, при повторном отображении экрана такущие URL и логин не отображаются в полях.

        submit_button = root.findViewById(R.id.submit_input);
        submit_button.setEnabled(false);

        inputUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getApiMethods=NetworkService.getInstance().createService(GetApiMethods.class);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //E Если при вводе текста ответ об ошибке соединения придёт позже ответа об успешном соединении,
                //  кнопка моргнёт и станет опять недоступной. Надо или прерывать текущую проверку доступности url
                //  перед запуском следующей, или лучше вообще не запрещать кнопку, и делать проверку по клику,
                //  с сообщением об ошибке или успехе в виде toast.
            getVersion(s.toString())
                    .delay(2, TimeUnit.SECONDS)
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
                            submit_button.setEnabled(false);

                        }

                        @Override
                        public void onComplete() {

                            submit_button.setEnabled(true);
                        }
                    });
            }

            @Override
            public void afterTextChanged(Editable s) {
                url=s.toString();


            }
        });

        loginText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString(APP_PREFERENCES_LOGIN, loginText.getText().toString());
                editor.apply();
            }
        });


        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString(APP_PREFERENCES_PASSWORD, passwordText.getText().toString());
                editor.apply();

            }
        });

                submit_button.setOnClickListener(v -> {
                    editor.putString(APP_PREFERENCES_URL, url);
                    editor.apply();
                    //W Жесткий ход - рестартовать весь процесс. А если у нас "дорогая" инициализация приложения?
                    ProcessPhoenix.triggerRebirth(root.getContext());

                });

        return root;
    }

    public Observable<ResponseBody> getVersion(String url) {
        return getApiMethods.getVersion(url);

    }
}