package education.zhiyuan.com.picturebooks.utils;

import android.media.AudioManager;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MediaPlayer implements OnBufferingUpdateListener, OnCompletionListener,
        OnPreparedListener {
    public android.media.MediaPlayer mediaPlayer;// 媒体播放器
    private SeekBar seekBar;// 拖动条
    private Timer mTimer;
    private PlayListener playListener;
    private TextView tvNow;
    private int duration = 0;

    // 初始化播放器
    public MediaPlayer(SeekBar seekBar, TextView tvNow) {
        super();
        this.seekBar = seekBar;
        this.tvNow = tvNow;
        try {
            mediaPlayer = new android.media.MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlayListener(PlayListener listener) {
        this.playListener = listener;
    }

    // 计时器
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (mediaPlayer == null)
                return;
            if (mediaPlayer.isPlaying() && seekBar.isPressed() == false) {
                handler.sendEmptyMessage(0);
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            if (duration > 0) {
                // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                if (position == duration) {
                    tvNow.setText(mediaPlayer.getDuration());
                }
                tvNow.setText(timeParse(position));
                long pos = seekBar.getMax() * position / duration;
                seekBar.setProgress((int) pos);
            }
        }
    };


    /**
     * @param url 地址
     */
    public void playUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        mediaPlayer.start();
        //每一秒触发一次
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(timerTask, 0, 1000);
        }
    }

    // 暂停
    public void pause() {
        mediaPlayer.pause();
    }

    public String getRuration() {
        return timeParse(mediaPlayer.getDuration());
    }

    public String timeParse(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (second == 60) {
            minute += 1;
            second = 0;
        }
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

    // 停止
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // 播放准备
    @Override
    public void onPrepared(android.media.MediaPlayer mp) {
        mp.start();
    }

    // 播放完成
    @Override
    public void onCompletion(android.media.MediaPlayer mp) {
        playListener.complete();
        handler.removeMessages(0);
    }

    /**
     * 设置缓冲进度
     *
     * @param mp
     * @param percent
     */
    @Override
    public void onBufferingUpdate(android.media.MediaPlayer mp, int percent) {
//        seekBar.setSecondaryProgress(percent);
//        int currentProgress = seekBar.getMax()
//                * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
    }


    public interface PlayListener {
        void play();

        void stop();

        void complete();
    }
}
