package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

/**
 * Created by luba on 9/27/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;


    //pas in the Tweets array in the constructor
    public TweetAdapter (List<Tweet> twwets) {
        mTweets = twwets;
    }

    //for each row. inflate layout and cache references into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent,false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    //bind the values base on the position of the element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get the data accroding to the position
        Tweet tweet = mTweets.get(position);

        //populate the views according to these data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.getText());

        holder.tvCreatedAt.setText(tweet.getRelativeTimeAgo());

        Glide.with(context).load(tweet.getUser().getProfileImageUrl()).into(holder.ivProfileImage);


    }

    //create ViewHolder class
    @Override
    public int getItemCount() {
        return mTweets.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvCreatedAt;

        public ViewHolder(View itemView) {
            super(itemView);

            //perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvTimestamp);


        }
    }
}
