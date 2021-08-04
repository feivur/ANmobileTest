package com.example.anmobiletest.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifImageView;

import static com.example.anmobiletest.HomeActivity.APP_PREFERENCES_LOGIN;
import static com.example.anmobiletest.HomeActivity.APP_PREFERENCES_PASSWORD;
import static com.example.anmobiletest.HomeActivity.APP_PREFERENCES_URL;

//i название сбивает с толку. Может быть лучше ConnectionFragment?
public class ShareFragment extends Fragment {
    GetApiMethods getApiMethods = NetworkService.getInstance().createService(GetApiMethods.class);
    private Button submit_button;

    String url;
    View root;


    public ShareFragment() {
    }

    public static ShareFragment newInstance() {
        return new ShareFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_share, container, false);
        EditText inputUrl = root.findViewById(R.id.url_input);
        EditText loginText = root.findViewById(R.id.login_input);
        EditText passwordText = root.findViewById(R.id.password_input);
        submit_button = root.findViewById(R.id.submit_input);
        //e progressBar бесполезен - его закрывает клавиатура
        GifImageView progressBar = root.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        submit_button.setEnabled(false);

        inputUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                progressBar.setVisibility(View.VISIBLE);
                /**w следует сделать проверку версии аналогично остальным методам
                 *   в {@link com.example.anmobiletest.api.camera.GetApiMethods}
                 *   вместо прямного использования /product/version
                 */
            getVersion(s.toString()+"/product/version")
                    .delay(5, TimeUnit.SECONDS)
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
                            progressBar.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onComplete() {
                            //e не выполнится никогда
                            // потому, что при проверке доступности сервера не используется авторизация,
                            // будет вечная ошибка 401
                            progressBar.setVisibility(View.INVISIBLE);
                            submit_button.setEnabled(true);
                        }
                    });
            }

            @Override
            public void afterTextChanged(Editable s) {
                url=s.toString();


            }
        });

                submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = HomeActivity.mSettings.edit();
                editor.putString(APP_PREFERENCES_URL, url);
                editor.putString(APP_PREFERENCES_LOGIN, loginText.getText().toString());
                editor.putString(APP_PREFERENCES_PASSWORD, passwordText.getText().toString());
                editor.apply();


            }
        });

        return root;
    }

    public Observable<ResponseBody> getVersion(String url) {
        return getApiMethods.getVersion(url);

    }
}