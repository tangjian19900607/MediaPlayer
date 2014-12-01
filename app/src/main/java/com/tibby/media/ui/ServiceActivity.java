package com.tibby.media.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.tibby.media.R;

public class ServiceActivity extends ActionBarActivity implements View.OnClickListener {
    private Button mStartButton;
    private Button mStopButton;
    private Intent mIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        init();
        initView();
        setListener();
    }

    private void init() {
        mIntent = new Intent(this,MusicService.class);
    }

    private void initView() {
        this.mStartButton = (Button) this.findViewById(R.id.start_service);
        this.mStopButton = (Button) this.findViewById(R.id.stop_service);
    }

    private void setListener() {
        this.mStartButton.setOnClickListener(this);
        this.mStopButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                start();
                break;
            case R.id.stop_service:
                stop(mIntent);
                break;
            default:
                break;
        }
    }

    private void start() {
        startService(mIntent);
    }

    private void stop(Intent intent) {
        stopService(intent);
    }
}
