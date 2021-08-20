package com.example.anmobiletest.fragments;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.anmobiletest.R;
import com.example.anmobiletest.ServerChangedService;
import com.example.anmobiletest.adapters.FeedAdaptersHome;
import com.example.anmobiletest.api.camera.PostDataMaker;
import com.example.anmobiletest.api.pojomodels.Post;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements LifecycleOwner {
    private final PostDataMaker cameraRQ = new PostDataMaker();
    private SwipeRefreshLayout refresher;
    private FeedAdaptersHome feedAdapter;
    private Boolean isLoading = false;
    private int offset = 0;

    Collection<Post> posts = new ArrayList<>();
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView postRecyclerView = root.findViewById(R.id.feed_recycler);
        initRecyclerView(postRecyclerView);
        refresher = root.findViewById(R.id.refresher);
        refresher.setOnRefreshListener(() -> {
            loadPosts(20, 5, 1);
            feedAdapter.clearItems();
        });
        loadPosts(20, 5, 1);

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
        feedAdapter = new FeedAdaptersHome();
        postRecyclerView.setAdapter(feedAdapter);
        postRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@androidx.annotation.NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItems) >= totalItemCount) {
                        isLoading = true;
                        loadPosts(5, offset, 1);
                        offset = offset + 5;
                    }

                } else isLoading = false;
            }
        });
    }




}