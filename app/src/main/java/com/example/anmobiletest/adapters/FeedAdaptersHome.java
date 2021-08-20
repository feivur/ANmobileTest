package com.example.anmobiletest.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anmobiletest.R;
import com.example.anmobiletest.api.camera.GetCameraData;
import com.example.anmobiletest.api.pojomodels.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class FeedAdaptersHome extends RecyclerView.Adapter<FeedAdaptersHome.FeedViewHolder> {

    private final List<Post> eventList = new ArrayList<>();

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item_home, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdaptersHome.FeedViewHolder holder, int position) {
        try {
            holder.bind(eventList.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    public void setItems(Collection<Post> posts) {
        eventList.addAll(posts);
        notifyDataSetChanged();
    }

    public void clearItems() {
        eventList.clear();
        notifyDataSetChanged();
    }


    static class FeedViewHolder extends RecyclerView.ViewHolder {
        Dialog dialog;
        private final GetCameraData cameraData = new GetCameraData();
        private final TextView userName;
        private final ImageView postImage;
        private final TextView postInfo;
        private final SimpleDateFormat inputDateFormatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSSSSS");
        private final SimpleDateFormat outputDateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        public void bind(Post post) throws ParseException {

            Date actuationDate = inputDateFormatter.parse(post.getPostTime());
            userName.setText(post.getCameraName());
            cameraData.getDetectorImageByte(post.getVideoSurceId(), post.getPostTime()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseBody responseBody) {
                            postImage.setImageBitmap(BitmapFactory.decodeStream(responseBody.byteStream()));
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
            postInfo.setText("Время срабатываения: " + outputDateFormatter.format(actuationDate));

            postImage.setOnClickListener(v -> showDialog(post, postImage, v.getContext()));

        }

        public void showDialog(Post post, ImageView image, Context context) {
            dialog = new Dialog(context);
            dialog.setTitle(post.getCameraName());
            dialog.setContentView(R.layout.dialog_item_click);
            TextView cameraName = dialog.findViewById(R.id.dialog_user_name);
            TextView dialogInfo = dialog.findViewById(R.id.dialog_post_info);
            ImageView imageDialog = dialog.findViewById(R.id.dialog_post_image);
            imageDialog.setImageDrawable(image.getDrawable());
            cameraName.setText(post.getCameraName());
            dialogInfo.setText("Тип детектора: " + post.getPostInfo());
            dialog.show();
        }


        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            postImage = itemView.findViewById(R.id.post_image);
            postInfo = itemView.findViewById(R.id.post_info);
        }


    }
}
