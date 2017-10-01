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

import java.util.ArrayList;

/**
 * Created by luba on 9/27/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Tweet> mTweets;
    Context context;

    private final int TWEET = 0, PHOTO_TWEET = 1, VIDEO_TWEET=3;

    public interface OnTweetAdapterListener {
        void selectedTweet(Tweet tweet);
    }


    private OnTweetAdapterListener mListener;


    //pas in the Tweets array in the constructor
    public TweetAdapter (ArrayList<Tweet> twwets, OnTweetAdapterListener listener) {
        this.mTweets = twwets;
        this.mListener = listener;
    }

    //for each row. inflate layout and cache references into ViewHolder

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent,false);
        TweetViewHolder viewHolder = new TweetViewHolder(tweetView);
        return viewHolder;
    }

    //bind the values base on the position of the element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TWEET:
                ((TweetViewHolder) holder).setupTweetView(mTweets.get(position));
                break;
        }

    }

    //create ViewHolder class
    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    @Override
    public int getItemViewType(int position) {
        Tweet tweet = mTweets.get(position);
        /*if (tweet.hasVideo()) {
            return VIDEO_TWEET;
        } else if (tweet.hasPhoto()) {
            return PHOTO_TWEET;
        } else {*/
            return TWEET;
        //}
    }

    public void notifyDataSetChanged(ArrayList<Tweet> tweets) {
        this.mTweets = new ArrayList<>(tweets);
        notifyDataSetChanged();
    }


    public class TweetViewHolder extends RecyclerView.ViewHolder{
        protected Tweet tweet;
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvCreatedAt;

        public TweetViewHolder(View itemView) {
            super(itemView);

            //perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvTimestamp);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.selectedTweet(tweet);
                }

            });


        }

        public void setupTweetView(Tweet tweet) {
            this.tweet = tweet;

            //populate the views according to these data
            tvUsername.setText(tweet.user.name);
            tvBody.setText(tweet.getText());

            tvCreatedAt.setText(tweet.getRelativeTimeAgo());

            Glide.with(context).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);



        }
    }
}
