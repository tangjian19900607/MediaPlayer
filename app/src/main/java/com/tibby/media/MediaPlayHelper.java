package com.tibby.media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

/**
 * Created by tangjian on 27/11/14.
 * QQ:562980080
 * Email:tangjian19900607@gmail.com
 */
public final class MediaPlayHelper {


    private MediaPlayer mMediaPlayer;
    private static MediaPlayHelper mInstance;
    private boolean hasPaused = false;
    private boolean isPlaying = false;
    private Media mMedia;
    private static final int DELAY_TIME = 100;
    private MediaAdapter.ViewHolder mViewHolder;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    private MediaPlayHelper() {
    }

    public synchronized static MediaPlayHelper getInstance() {
        if (null == mInstance) {
            mInstance = new MediaPlayHelper();
        }
        return mInstance;
    }

    public void setViewHolder(MediaAdapter.ViewHolder viewHolder) {
        mViewHolder = viewHolder;
    }

    public void startPlay(Media media) {
        Log.d("url", "url=============" + media.getUrl());
        mMedia = media;
        if (isPlaying) {
            isPlaying = false;
            if (null != mMediaPlayer) {
                mMediaPlayer.pause();
                hasPaused = true;
            }

        } else {
            if (hasPaused) {
                if (null != mMediaPlayer) {
                    mMediaPlayer.start();
                    mHandler.postDelayed(runnable, DELAY_TIME);
                }
            }
            isPlaying = true;
        }
        if (null == mMediaPlayer) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(completionListener);
            mMediaPlayer.setOnPreparedListener(preparedListener);
            mMediaPlayer.setOnErrorListener(errorListener);
            try {
                mMediaPlayer.setDataSource(media.getUrl());
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public Media getMedia() {
        return mMedia;
    }

    public void stopPlay() {
        mHandler.removeCallbacks(runnable);
        hasPaused = false;
        mViewHolder = null;
        isPlaying = false;
        mMedia = null;
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.seekTo(mp.getCurrentPosition());
            mp.start();
            mHandler.postDelayed(runnable, DELAY_TIME);
        }
    };
    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

        }
    };
    MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            mHandler.removeCallbacks(runnable);
            hasPaused = false;
            mViewHolder = null;
            mMedia = null;
            if (mMediaPlayer != null) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
            return false;
        }
    };
    MediaRunnable runnable = new MediaRunnable() {
        @Override
        public void run() {
            super.run();
            if ((null != mMediaPlayer) && (mMediaPlayer.isPlaying())) {
                mHandler.postDelayed(runnable, DELAY_TIME);
            }
        }
    };

    class MediaRunnable implements Runnable {

        @Override
        public void run() {
            if (null != mViewHolder) {
                mViewHolder.seekBar.setMax(mMediaPlayer.getDuration());
                mViewHolder.seekBar.setProgress(mMediaPlayer.getCurrentPosition());
                mViewHolder.playTimeTextView.setText(CommonUtil
                        .convertSecondsToTimeText(mMediaPlayer
                                .getCurrentPosition() / 1000)
                        + "/"
                        + CommonUtil
                        .convertSecondsToTimeText(mMediaPlayer
                                .getDuration() / 1000));
            }
        }
    }
}
