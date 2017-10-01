package com.codepath.apps.simpletweet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpletweet.R;
import com.codepath.apps.simpletweet.TwitterApplication;
import com.codepath.apps.simpletweet.models.Tweet;
import com.codepath.apps.simpletweet.models.User;
import com.codepath.apps.simpletweet.network.NewPostTweetCallback;
import com.codepath.apps.simpletweet.network.NewTweetRequest;
import com.codepath.apps.simpletweet.network.TwitterClient;

import org.parceler.Parcels;


/**
 * Created by luba on 9/27/17.
 */

public class NewTweetDialogFragment extends DialogFragment {

    private static final int TWEET_LIMIT = 140;

    Button btnComposeNewTweet;
    ImageView mProfileImage;
    TextView mUserName, mScreenName, mCounter;
    private User mUser = new User();
    ImageView btnClose;
    EditText etStatus;
    private String mStatus;

    public interface OnNewTweetDialogFragmentListener {

        void onTimeLineChanged(Tweet tweet);

    }

    private OnNewTweetDialogFragmentListener mListener;

    public NewTweetDialogFragment () {}

    public static NewTweetDialogFragment newInstance() {
        return new NewTweetDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) Parcels.unwrap(getArguments().getParcelable("user"));
        }

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewTweetDialogFragmentListener) {
            mListener = (OnNewTweetDialogFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " OnNewTweetDialogFragmentListener should be implemented");
        }
    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        btnComposeNewTweet = (Button) view.findViewById(R.id.btnComposeNewTweet);
        btnClose = (ImageView) view.findViewById(R.id.btnClose);
        mProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
        mUserName = (TextView) view.findViewById(R.id.tvUserName);
        mScreenName = (TextView) view.findViewById(R.id.tvScreenName);
        etStatus = (EditText) view.findViewById(R.id.etTweetText);
        mCounter = (TextView) view.findViewById(R.id.tvCounter);

        if (mUser != null) {

            mUserName.setText(mUser.getName());
            mScreenName.setText(mUser.getScreennameToShow());
            Glide.with(getActivity()).load(mUser.getProfileImageUrl()).into(mProfileImage);

        }

        etStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                mStatus = e != null ? e.toString() : null;
                updateCounter();
                enableTweetButton();

            }
        });

        //updateCounter();
        //enableTweetButton();


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        btnComposeNewTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTweetRequest request = new NewTweetRequest();
                request.setStatus(etStatus.getText().toString());

                TwitterClient client = TwitterApplication.getRestClient();
                client.postNewTweet(request, new NewPostTweetCallback() {
                    @Override
                    public void onSuccess(Tweet tweet) {
                        mListener.onTimeLineChanged(tweet);
                        dismiss();
                    }

                    @Override
                    public void onError(Error error) {

                    }

                });
            }
            });


    }

    private void enableTweetButton() {
        btnComposeNewTweet.setEnabled(mStatus != null && mStatus.length() > 0);
    }

    private void updateCounter() {
        int count = mStatus != null ? mStatus.length() : 0;
        mCounter.setText(String.valueOf(TWEET_LIMIT - count));
        if ((TWEET_LIMIT - count) < 0) {
            mCounter.setTextColor(getResources().getColor(R.color.red));
            btnComposeNewTweet.setEnabled(false);
            btnComposeNewTweet.setBackgroundColor(getResources().getColor(R.color.blue));
        } else {
            mCounter.setTextColor(getResources().getColor(R.color.gray));
            btnComposeNewTweet.setEnabled(true);
            btnComposeNewTweet.setBackgroundColor(getResources().getColor(R.color.dark_blue));
        }

    }

}
