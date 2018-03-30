package education.zhiyuan.com.picturebooks.adpter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.utils.MediaPlayUtil;
import education.zhiyuan.com.picturebooks.utils.TimeTools;

/**
 * Created by Lance on 2017/6/17.
 * Email : COCOINUT@163.com
 * Introduce :
 */

public class AdapterCommentVoice extends RecyclerView.Adapter<AdapterCommentVoice.ViewHolder> {

    private List<Map<String, Object>> list;
    private Context context;
    private MediaPlayUtil mMediaPlayUtil;
    private MediaPlayer mediaPlayer;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
        if (flag == -1) {
            //关闭
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        }
    }

    private int flag;

    public AdapterCommentVoice(Context context, List<Map<String, Object>> list, int flag) {
        this.context = context;
        this.list = list;
        this.flag = flag;
    }

    /**
     * 录音存放路径
     */
    public void initSoundData() {
        mMediaPlayUtil = MediaPlayUtil.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item__record_voice, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        if (MyApp.resolutionW != -1) {
//            int length = Integer.valueOf(list.get(position).get("time").toString());
//            int width = MyApp.resolutionW / 16 * 3;
//            if (length >= 20 * 1000) {
//                width = MyApp.resolutionW / 3 * 1;
//            } else if (length >= 10 * 1000) {
//                width += MyApp.resolutionW / 100 * (length / 1000);
//            } else if (length >= 5 * 1000) {
//                width += MyApp.resolutionW / 140 * (length / 1000);
//            }
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
//            holder.voiceLayout.setLayoutParams(lp);
//        }
        holder.ivVoiceImage.setImageResource(R.drawable.iv_play_white);
        holder.chatTvVoiceLen.setText(list.get(position).get("time") + "" + '"');
        holder.chatTvVoiceLen.setText(TimeTools.timeParse(Integer.valueOf(list.get(position).get("time") + "")));
        holder.voiceLayout.setOnClickListener(new View.OnClickListener() { //播放或暂停
            @Override
            public void onClick(View v) {

                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(list.get(position).get("path").toString());
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayer.start();
                                holder.ivVoiceImage.setImageResource(R.drawable.iv_isplay_white);
                            }
                        });
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.pause();
                                holder.ivVoiceImage.setImageResource(R.drawable.iv_play_white);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        holder.ivVoiceImage.setImageResource(R.drawable.iv_play_white);
                    } else {
                        mediaPlayer.start();
                        holder.ivVoiceImage.setImageResource(R.drawable.iv_isplay_white);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chat_tv_voice_len)
        TextView chatTvVoiceLen;
        @BindView(R.id.iv_voice_image)
        ImageView ivVoiceImage;
        @BindView(R.id.voice_layout)
        RelativeLayout voiceLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
