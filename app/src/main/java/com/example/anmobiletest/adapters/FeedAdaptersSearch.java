package com.example.anmobiletest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anmobiletest.R;
import com.example.anmobiletest.api.pojomodels.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FeedAdaptersSearch extends RecyclerView.Adapter<FeedAdaptersSearch.FeedViewHolder> {
    private List<Post> eventList = new ArrayList<>();



    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item_search, parent, false);

        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdaptersSearch.FeedViewHolder holder, int position) {
        holder.bind(eventList.get(position));
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

    public void filter(Collection<Post> posts,String cameraName) {
        eventList.clear();
            for(Post item: posts){
                if(item.getCameraName().toLowerCase().equals(cameraName.toLowerCase())){
                    eventList.add(item);
                }
            }
        }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private ImageView postImage;
        private TextView postInfo;

        public void bind(Post Post) {
            userName.setText(Post.getCameraName());
            postImage.setImageDrawable(Post.getPostImage());
            postInfo.setText("Тип детектора: " + Post.getPostInfo() + "\nВремя срабатываения: " + Post.getPostTime());


        }


        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            postImage = itemView.findViewById(R.id.post_image);
            postInfo = itemView.findViewById(R.id.post_info);
        }

    }
}
