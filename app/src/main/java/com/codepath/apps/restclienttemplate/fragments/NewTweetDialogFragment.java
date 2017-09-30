package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.network.NewPostTweetCallback;
import com.codepath.apps.restclienttemplate.network.NewTweetRequest;

import org.parceler.Parcels;


/**
 * Created by luba on 9/27/17.
 */

public class NewTweetDialogFragment extends DialogFragment {

    Button btnComposeNewTweet;
    ImageView mProfileImage;
    TextView mUserName, mScreenName;
    private User mUser = new User();
    ImageView btnClose;
    EditText mStatus;

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
        mStatus = (EditText) view.findViewById(R.id.etTweetText);

        mUserName.setText(mUser.getName());
        mScreenName.setText(mUser.getScreenName());


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        Glide.with(getActivity()).load(mUser.getProfileImageUrl()).into(mProfileImage);
        Log.d("DEBUG", "profile imagee=" + mUser.getProfileImageUrl());

        btnComposeNewTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTweetRequest request = new NewTweetRequest();
                request.setStatus(mStatus.getText().toString());

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
}
