package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.fragments.NewTweetDialogFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.network.HomeTimelineCallback;
import com.codepath.apps.restclienttemplate.network.HomeTimelineRequest;
import com.codepath.apps.restclienttemplate.network.TwitterClient;
import com.codepath.apps.restclienttemplate.network.UserCredentialsCallback;
import com.codepath.apps.restclienttemplate.utils.EndlessRecyclerViewScrollListener;

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
    DividerItemDecoration mDividerItemDecoration;
    private SwipeRefreshLayout swipeContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupUI();
        loadUserCredentials();

        // Swipe to refresh
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mTweets.isEmpty()) {
                    loadTimeline(mTweets.get(0).getTweetId(), null);
                    Log.d ("DEBUG", "!mTweets.isEmpty()"+mTweets.get(0).getTweetId());
                } else {
                    Log.d ("DEBUG", "empty");
                    loadTimeline(null, null);
                }
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_blue_dark);

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


        mDividerItemDecoration = new DividerItemDecoration(this,
                mLayoutManager.getOrientation());
        rvTweets.addItemDecoration(mDividerItemDecoration);

        EndlessRecyclerViewScrollListener endlessListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("DEBUG", "loading page=: " + String.valueOf(page));
                loadTimeline(null, mTweets.get(totalItemsCount - 1).getTweetId());

            }
        };
        rvTweets.addOnScrollListener(endlessListener);
        mTwitterClient = TwitterApplication.getRestClient();
        //loadTimeline();

        if (!mTweets.isEmpty()) {
            tweetAdapter.notifyDataSetChanged(mTweets);
        } else {
            loadTimeline(null,  null);}
    }

    private void setupUI() {
        mTwitterClient = TwitterApplication.getRestClient();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        fabCompose = (FloatingActionButton) findViewById(R.id.fabCompose);
        //find the RecyclerView
        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
        //init the ArrayList or data source
        mTweets = new ArrayList<>();
        //construct the adapter from the data source
        tweetAdapter = new TweetAdapter(mTweets, new TweetAdapter.OnTweetAdapterListener() {
            @Override
            public void selectedTweet(Tweet tweet) {
                openTweetDetails(tweet);
            }
        });
        //setup RecyclerView (layout manager, use adapter)
        mLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(mLayoutManager);
        rvTweets.setHasFixedSize(true);
        //set adapter
        rvTweets.setAdapter(tweetAdapter);
    }

    private void openTweetDetails(Tweet tweet) {
        Intent intent = new Intent(TimelineActivity.this, TweetDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("tweet", Parcels.wrap(tweet));
        intent = intent.putExtras(bundle);

        startActivity(intent);
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

    private void loadTimeline(Long sinceID, Long maxID) {
        Toast.makeText(TimelineActivity.this, "Loading tweets", Toast.LENGTH_SHORT).show();
        HomeTimelineRequest request = new HomeTimelineRequest();
        request.setSinceId(sinceID);
        request.setMaxId(maxID);

        mTwitterClient.getHomeTimeline(request, new HomeTimelineCallback() {
            @Override
            public void onSuccess(ArrayList<Tweet> tweets) {
                // Save in local database
                //saveTweets(tweets);

                // Process tweets
                if (!tweets.isEmpty()) {
                    if (mTweets.isEmpty()) {
                        mTweets = new ArrayList<>();
                    }
                    //mTweets.clear();
                    mTweets.addAll(tweets);
                    Log.d ("DEBUG", "mTweets"+mTweets);
                    tweetAdapter.notifyDataSetChanged(mTweets);

                }
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onError(Error error) {
                Toast.makeText(TimelineActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    @Override
    public void onTimeLineChanged(Tweet tweet) {
        mTweets.add(0, tweet);
        tweetAdapter.notifyDataSetChanged(mTweets);
        mLayoutManager.scrollToPosition(0);

    }
}
