package com.codepath.apps.simpletweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpletweet.R;
import com.codepath.apps.simpletweet.fragments.NewTweetDialogFragment;
import com.codepath.apps.simpletweet.models.Tweet;
import com.codepath.apps.simpletweet.models.User;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity implements NewTweetDialogFragment.OnNewTweetDialogFragmentListener{

    public static final int REQUEST_CODE =123 ;
    public static final String TWEET ="tweet" ;
    public static final java.lang.String REFRESH_TWEETS ="refresh tweets" ;
    protected Toolbar toolbar;
    protected AppBarLayout appBarLayout;
    ImageView ivProfileImage;
    TextView tvName, tvScreenname, tvStatus;
    Button btnReply;
    boolean refreshTweets;
    private Tweet mTweet = new Tweet();
    private User mUser = new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        mTweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        mUser = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        Log.d("DEBUG", "mTweet="+mTweet);

        setupUI();
        setupToolbar();

    }

    private void setupUI() {
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName = (TextView) findViewById(R.id.tvName);
        tvScreenname = (TextView) findViewById(R.id.tvScreenname);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnReply = (Button) findViewById(R.id.btnReply);

        if (mTweet != null) {

            tvName.setText(mTweet.getUser().getName());
            tvScreenname.setText(mTweet.getUser().getScreennameToShow());
            Glide.with(this).load(mTweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        }
        tvStatus.setText(mTweet.getText());

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                NewTweetDialogFragment fragment = new NewTweetDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", Parcels.wrap(mUser));
                bundle.putParcelable("tweet", Parcels.wrap(mTweet));
                fragment.setArguments(bundle);
                fragment.show(fm, "compose_tweet");
            }
        });


    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                if (!isTaskRoot()) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        }
    }

    @Override
    public void onTimeLineChanged(Tweet tweet) {
        refreshTweets = true;

    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra(TWEET, Parcels.wrap(mTweet));
        data.putExtra(REFRESH_TWEETS, refreshTweets);
        setResult(RESULT_OK, data);
        finish();
    }
}
