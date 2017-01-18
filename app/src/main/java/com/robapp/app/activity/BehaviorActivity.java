package com.robapp.app.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

import com.robapp.behaviors.executions.BehaviorThread;
import com.robapp.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import ch.qos.logback.core.net.SyslogOutputStream;

public class BehaviorActivity extends BaseActivity implements IEmotionListener, DialogInterface.OnDismissListener {

    Button startButton;
    TextView behaviorName;
    private BehaviorThread thread;
    WebView myWebView;
    Launcher launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
        setupDrawer();


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

                if(!Utils.isBehaviorStarted())
                {
                    launcher = new Launcher(BehaviorActivity.this,5,selectedBehavior);
                    launcher.setOnDismissListener(BehaviorActivity.this);
                    launcher.show();
                }
                else
                    stopBehavior();


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
    public void newEmotion(final Emotion emotion) {

        final Emotion emotionLocal = emotion;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                switch(emotionLocal) {
                    case NORMAL: BehaviorActivity.this.myWebView.loadUrl("javascript:emotionNormal()"); break;
                    case HAPPY: BehaviorActivity.this.myWebView.loadUrl("javascript:emotionHappy()"); break;
                    case ANGRY: BehaviorActivity.this.myWebView.loadUrl("javascript:emotionAngry()"); break;
                    case LAUGHING: BehaviorActivity.this.myWebView.loadUrl("javascript:emotionLaughing()"); break;
                    case EMBARRASED: BehaviorActivity.this.myWebView.loadUrl("javascript:emotionEmbarrased()"); break;
                    case SAD: BehaviorActivity.this.myWebView.loadUrl("javascript:emotionSad()"); break;
                    case SURPRISED: BehaviorActivity.this.myWebView.loadUrl("javascript:emotionSurprised()"); break;
                    case SMYLING: BehaviorActivity.this.myWebView.loadUrl("javascript:emotionSmyling()"); break;
                    case IN_LOVE: BehaviorActivity.this.myWebView.loadUrl("javascript:emotionInLove()"); break;

                }
                System.out.println("New Emotion : "+emotion);
                myWebView.reload();

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void updateStartButtonText(final boolean start)
    {
        if(!start)
            startButton.post(new Runnable() {
                @Override
                public void run() {
                    startButton.setText("Lancer");
                }
            });
        else
            startButton.post(new Runnable() {
                @Override
                public void run() {
                    startButton.setText("Stopper");
                }
            });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(launcher.launchConfirmed())
        {
            try{

                thread = launcher.getThread();
                if(thread != null)
                {
                    Handler handler = new Handler();
                    Utils.setBehaviorStarted(true);
                    updateStartButtonText(true);
                    Utils.getRoboboManager().getModuleInstance(IEmotionModule.class).subscribe(this);
                    Utils.setThread(thread);

                    thread.start();

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public void stopBehavior(){
        if(thread != null) {
            thread.interrupt();
            try {
                IRobMovementModule module = Utils.getRoboboManager().getModuleInstance(IRobMovementModule.class);
                module.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            thread = null;
            Utils.setThread(null);
        }
    }
}
