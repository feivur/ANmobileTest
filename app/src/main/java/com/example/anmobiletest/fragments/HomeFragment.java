package com.example.anmobiletest.fragments;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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
    private RecyclerView postRecyclerView;
    private SwipeRefreshLayout refresher;
    private FeedAdaptersHome feedAdapter;
    private PostDataMaker cameraRQ = new PostDataMaker();
    private Boolean isLoading = false;
    int offset = 0;

    Collection<Post> posts = new ArrayList<>();

    View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_home, container, false);
        postRecyclerView = root.findViewById(R.id.feed_recycler);
        initRecyclerView(postRecyclerView);
        refresher = root.findViewById(R.id.refresher);
        loadPosts(5, 5, 1);
        startServiceAlaramManager();
        refresher.setOnRefreshListener(() -> loadPosts(5, 5, 1));

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
                    }

                    @Override
                    public void onNext(@NonNull Post post) {

                        saveImage(post);
                        posts.add(post);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        feedAdapter.setItems(posts);
                        feedAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                        feedAdapter.setItems(posts);
                        feedAdapter.notifyDataSetChanged();
                        refresher.setRefreshing(false);
                    }
                });


    }

    private void saveImage(Post post) {
        Glide.with(root.getContext())
                .load(post.getPostImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .fitCenter()
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@androidx.annotation.NonNull Drawable resource, Transition<? super Drawable> transition) {
                        post.setPostImage(resource);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {

                    }
                });
    }

    public void startServiceAlaramManager() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 10);
        Intent intent = new Intent(getActivity(), ServerChangedService.class);
        PendingIntent pintent = PendingIntent.getService(getActivity(), 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                60000, pintent);

        getActivity().startService(new Intent(getActivity(), ServerChangedService.class));
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