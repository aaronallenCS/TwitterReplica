package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.codepath.apps.restclienttemplate.TimelineActivity.TAG;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>
{
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets)
    {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         Tweet tweet = tweets.get(position);
         holder.bind(tweet);
    }

    public void clear()
    {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweetList)
    {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvLikedCounts;
        TextView tvRetweetCount;
        TextView postedAt;
        TextView atHandle;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            postedAt = itemView.findViewById(R.id.postedTime);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvLikedCounts = itemView.findViewById(R.id.favoriteCount);
            tvRetweetCount = itemView.findViewById(R.id.retweetCount);
            atHandle = itemView.findViewById(R.id.tvAtHandle);


        }

        public void bind(final Tweet tweet)
        {
            final ImageView favoriteButton = itemView.findViewById(R.id.favorite_button);
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_filled_24);
                    tweet.favoriteCount = tweet.favoriteCount + 1;
                    tvLikedCounts.setText("" + tweet.favoriteCount);
                }
            });
            final ImageView retweetButton = itemView.findViewById(R.id.retweet);
            retweetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    retweetButton.setImageResource(R.drawable.ic_baseline_retweet_filled_24);
                    tweet.retweetCount = tweet.retweetCount + 1;
                    tvRetweetCount.setText(""+tweet.retweetCount);
                }
            });
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            if(tweet.favoriteCount == 0)
            {
                tvLikedCounts.setText("");
            }
            else
            {
                tvLikedCounts.setText(""+tweet.favoriteCount);
            }
            if(tweet.retweetCount == 0)
            {
                tvRetweetCount.setText("");
            }
            else
            {
                tvRetweetCount.setText(""+tweet.retweetCount);
            }
            postedAt.setText(getRelativeTimeAgo(tweet.createdAt));
            atHandle.setText("@"+tweet.user.name);
            Glide.with(context).load(tweet.user.publicImageUrl).into(ivProfileImage);
        }
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            long time = sf.parse(rawJsonDate).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (ParseException e) {
            Log.i(TAG, "getRelativeTimeAgo failed");
            e.printStackTrace();
        }

        return "";
    }
}
