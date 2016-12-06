package com.robapp.app.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.mytechia.robobo.framework.exception.ModuleNotFoundException;
import com.mytechia.robobo.framework.hri.emotion.DefaultEmotionModule;
import com.mytechia.robobo.framework.hri.emotion.Emotion;
import com.mytechia.robobo.framework.hri.emotion.IEmotionListener;
import com.mytechia.robobo.framework.hri.emotion.IEmotionModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.R;
import com.robapp.app.dialog.Launcher;

import com.robapp.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import ch.qos.logback.core.net.SyslogOutputStream;

public class BehaviorActivity extends BaseActivity implements IEmotionListener {

    Button startButton;
    TextView behaviorName;
    private Thread thread;
    WebView myWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
        setupDrawer();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(!Utils.isBehaviorStarted())
            thread = null;
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!robStarted)
                {
                    showRoboboDeviceSelectionDialog();
                    return;
                }

                if(selectedBehavior != null)
                {
                }

                if(!Utils.isBehaviorStarted())
                {
                    Utils.setBehaviorStarted(true);
                    Launcher launcher = new Launcher(BehaviorActivity.this,5,selectedBehavior);
                    launcher.show();
                    thread = launcher.getThread();

                }
                else
                {
                    Utils.setBehaviorStarted(true);
                    if(thread != null) {
                        thread.interrupt();
                        try {
                            IRobMovementModule module = Utils.getRoboboManager().getModuleInstance(IRobMovementModule.class);
                            module.stop();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        thread = null;
                    }

                }


            }
        });

        behaviorName =(TextView)findViewById(R.id.nameBehavior);
        if(selectedBehavior != null)
            behaviorName.setText(selectedBehavior.getName());

        myWebView = (WebView) findViewById(R.id.emotionRob);
        WebSettings webSettings = myWebView.getSettings();

        String fileN =  "file:///android_asset/emotions/emotions.html";


        myWebView.loadUrl(fileN);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
    }


    @Override
        public void newEmotion(Emotion emotion) {

        final Emotion emotionLocal = emotion;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                switch(emotionLocal) {
                    case NORMAL: myWebView.loadUrl("javascript:emotionNormal()"); break;
                    case HAPPY: myWebView.loadUrl("javascript:emotionHappy()"); break;
                    case ANGRY: myWebView.loadUrl("javascript:emotionAngry()"); break;
                    case LAUGHING: myWebView.loadUrl("javascript:emotionLaughing()"); break;
                    case EMBARRASED: myWebView.loadUrl("javascript:emotionEmbarrased()"); break;
                    case SAD: myWebView.loadUrl("javascript:emotionSad()"); break;
                    case SURPRISED: myWebView.loadUrl("javascript:emotionSurprised()"); break;
                    case SMYLING: myWebView.loadUrl("javascript:emotionSmyling()"); break;
                    case IN_LOVE: myWebView.loadUrl("javascript:emotionInLove()"); break;
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
