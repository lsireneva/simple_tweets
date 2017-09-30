package com.codepath.apps.restclienttemplate.activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.fragments.NewTweetDialogFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.network.HomeTimelineCallback;
import com.codepath.apps.restclienttemplate.network.UserCredentialsCallback;

import org.parceler.Parcels;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity implements NewTweetDialogFragment.OnNewTweetDialogFragmentListener{

    TweetAdapter tweetAdapter;
    ArrayList<Tweet> mTweets;
    RecyclerView rvTweets;
    FloatingActionButton fabCompose;
    private User mUserInfo;
    TwitterClient mTwitterClient;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        mTwitterClient = TwitterApplication.getRestClient();
        loadUserCredentials();

        fabCompose = (FloatingActionButton) findViewById(R.id.fabCompose);
        fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d ("DEBUG", "fabCompose.setOnClickListener");
                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", Parcels.wrap(mUserInfo));
                NewTweetDialogFragment frag = new NewTweetDialogFragment();
                frag.setArguments(bundle);
                frag.show(fm, "compose_tweet");
            }
        });

        //find the RecyclerView
        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
        //init the ArrayList or data source
        mTweets = new ArrayList<>();
        //construct the adapter from the data source
        tweetAdapter = new TweetAdapter(mTweets);
        //setup RecyclerView (layout manager, use adapter)
        mLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(mLayoutManager);
        rvTweets.setHasFixedSize(true);
        //set adapter
        rvTweets.setAdapter(tweetAdapter);
        mTwitterClient = TwitterApplication.getRestClient();
        loadTimeline();


    }

    private void loadUserCredentials () {
        Toast.makeText(TimelineActivity.this, "Loading user credentials", Toast.LENGTH_SHORT).show();
        mTwitterClient.getUserCredentials(new UserCredentialsCallback() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    mUserInfo = new User();
                    mUserInfo.setName(user.getName());
                    mUserInfo.setScreenName(user.getScreenName());
                    mUserInfo.setProfileImageUrl(user.getProfileImageUrl());
                }
            }

            @Override
            public void onError(Error error) {

            }
        });
    }

    private void loadTimeline() {
        Toast.makeText(TimelineActivity.this, "Loading tweets", Toast.LENGTH_SHORT).show();

        mTwitterClient.getHomeTimeline(new HomeTimelineCallback() {
            @Override
            public void onSuccess(ArrayList<Tweet> tweets) {
                // Save in local database
                //saveTweets(tweets);

                // Process tweets
                if (tweets != null) {
                    if (mTweets == null) {
                        mTweets = new ArrayList<>();
                    }
                    mTweets.addAll(tweets);
                    tweetAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Error error) {
                Toast.makeText(TimelineActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onTimeLineChanged(Tweet tweet) {
        mTweets.add(0, tweet);
        tweetAdapter.notifyDataSetChanged();
        mLayoutManager.scrollToPosition(0);

    }
}
