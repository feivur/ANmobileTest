package com.example.anmobiletest.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.anmobiletest.R;
import com.example.anmobiletest.adapters.FeedAdaptersSearch;
import com.example.anmobiletest.api.camera.PostDataMaker;
import com.example.anmobiletest.api.pojomodels.Post;

import java.util.ArrayList;
import java.util.Collection;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment {
    private final PostDataMaker cameraRQ = new PostDataMaker();
    private SwipeRefreshLayout refresher;
    private FeedAdaptersSearch feedAdapter;
    Collection<Post> posts = new ArrayList<>();

    View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView postRecyclerView = root.findViewById(R.id.rc_view);
        EditText cameraName = root.findViewById(R.id.camera_name);
        initRecyclerView(postRecyclerView);
        refresher = root.findViewById(R.id.swipe_search);
        loadPosts(500, 0, 1);
        feedAdapter.setItems(posts);
        refresher.setOnRefreshListener(() -> {
            loadPosts(20, 5, 1);
            feedAdapter.clearItems();
        });

        cameraName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                feedAdapter.filter(posts, s.toString());
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return root;

    }


    public void loadPosts(int limit, int offset, int join) {
        cameraRQ.getPostsObservable(limit, offset, join).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        refresher.setRefreshing(true);
                        posts.clear();
                        feedAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(@NonNull Post post) {
                        posts.add(post);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        feedAdapter.setItems(posts);
                        feedAdapter.notifyDataSetChanged();
                        refresher.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        feedAdapter.setItems(posts);
                        feedAdapter.notifyDataSetChanged();
                        refresher.setRefreshing(false);
                    }
                });


    }


    private void initRecyclerView(RecyclerView postRecyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        postRecyclerView.setLayoutManager(mLayoutManager);
        feedAdapter = new FeedAdaptersSearch();
        postRecyclerView.setAdapter(feedAdapter);

    }


}