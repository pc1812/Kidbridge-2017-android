package education.zhiyuan.com.picturebooks.utils;

import android.media.MediaPlayer;


/**
 * Created by hfx on 14-10-15.
 */
public class MediaPlayUtil {
    private static MediaPlayUtil mMediaPlayUtil;
    private MediaPlayer mMediaPlayer;

    public void setPlayOnCompleteListener(MediaPlayer.OnCompletionListener playOnCompleteListener) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setOnCompletionListener(playOnCompleteListener);
        }
    }

    public void setPlayingListener() {
    }

    public static MediaPlayUtil getInstance() {
        if (mMediaPlayUtil == null) {
            mMediaPlayUtil = new MediaPlayUtil();
        }
        return mMediaPlayUtil;
    }

    private MediaPlayUtil() {
        mMediaPlayer = new MediaPlayer();
    }

    public void prepare(String soundFilePath) {
        if (mMediaPlayer == null) {
            return;
        }
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(soundFilePath);
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(String soundFilePath) {
//        if(mMediaPlayer == null){
//            return;
//        }
        try {
            prepare(soundFilePath);
            mMediaPlayer.start();
//            mMediaPlayer.reset();
//            mMediaPlayer.setDataSource(soundFilePath);
//            mMediaPlayer.prepare();
//            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.pause();
            }

        }
    }

    public void stop() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    public void reset() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public int getCurrentPosition() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            return mMediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    public int getDutation() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            return mMediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        } else {
            return false;
        }
    }
}
