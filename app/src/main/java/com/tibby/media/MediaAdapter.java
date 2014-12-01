package com.tibby.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tangjian on 27/11/14.
 * QQ:562980080
 * Email:tangjian19900607@gmail.com
 */
public class MediaAdapter extends BaseAdapter {
    private List<Media> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MediaAdapter(List<Media> list, Context context) {
        this.mList = list;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.media_item, null);
            viewHolder.playButton = (ImageView) convertView.findViewById(R.id.play_button);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.play_title);
            viewHolder.playTimeTextView = (TextView) convertView.findViewById(R.id.play_time);
            viewHolder.seekBar = (SeekBar) convertView.findViewById(R.id.seekbar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String url = mList.get(position).getUrl();
        if (url.endsWith(".pls")) {
            viewHolder.playTimeTextView.setVisibility(View.GONE);
            viewHolder.seekBar.setVisibility(View.GONE);
        } else {
            viewHolder.playTimeTextView.setVisibility(View.VISIBLE);
            viewHolder.seekBar.setVisibility(View.VISIBLE);
        }
        Media media = mList.get(position);
        if (media == MediaPlayHelper.getInstance().getMedia()){
            if (MediaPlayHelper.getInstance().isPlaying()){
                MediaPlayHelper.getInstance().setViewHolder(viewHolder);
                viewHolder.playButton.setBackgroundResource(R.drawable.ic_audio_pause);
            }else {
                viewHolder.playButton.setBackgroundResource(R.drawable.ic_audio_play);
            }
        }else {
            viewHolder.playButton.setBackgroundResource(R.drawable.ic_audio_play);
            viewHolder.playTimeTextView.setText("");
            viewHolder.seekBar.setMax(0);
            viewHolder.seekBar.setProgress(0);
        }
        viewHolder.titleTextView.setText(mList.get(position).getName());
        viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Media media = mList.get(position);
                if(media != MediaPlayHelper.getInstance().getMedia()){
                    MediaPlayHelper.getInstance().stopPlay();
                }
                MediaPlayHelper.getInstance().startPlay(media);
                MediaAdapter.this.notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        private ImageView playButton;
        private TextView titleTextView;
        public TextView playTimeTextView;
        public SeekBar seekBar;
    }
}
