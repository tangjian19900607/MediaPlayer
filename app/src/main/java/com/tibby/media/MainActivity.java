package com.tibby.media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tangjian on 27/11/14.
 * QQ:562980080
 * Email:tangjian19900607@gmail.com
 */
public class MainActivity extends ActionBarActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private ListView mListView;
    private Button mPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mListView = (ListView) this.findViewById(R.id.listview);
        mListView.setAdapter(new MediaAdapter(getData(), this));
    }

    private ArrayList<Media> getData() {
        ArrayList<Media> list = new ArrayList<Media>();
        list.add(new Media("http://yinyueshiting.baidu.com/data2/music/124538462/285827212400128.mp3?xcode=c72e3f517e53a51458dcd0329c6c614606113216f1e043f7", "光不上的窗.mp3"));
        list.add(new Media("http://yinyueshiting.baidu.com/data2/music/124469106/12414241243200128.mp3?xcode=3662728d43002b6f748bfabb3b6f0aa829ee863b3def57cc", "樱花雪.mp3"));
        return list;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayHelper.getInstance().stopPlay();
    }
}
