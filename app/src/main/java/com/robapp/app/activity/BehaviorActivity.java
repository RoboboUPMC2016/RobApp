package com.robapp.app.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.mytechia.robobo.framework.hri.emotion.Emotion;
import com.mytechia.robobo.framework.hri.emotion.IEmotionListener;
import com.mytechia.robobo.framework.hri.emotion.IEmotionModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.R;
import com.robapp.app.dialog.Launcher;

import com.robapp.behaviors.executions.ContextManager;
import com.robapp.tools.Utils;

public class BehaviorActivity extends BaseActivity implements IEmotionListener, DialogInterface.OnDismissListener {

    Button startButton;
    WebView myWebView;
    Launcher launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
        setupDrawer();

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

                Thread thread = launcher.getThread();
                if(thread != null)
                {
                    Handler handler = new Handler();
                    Utils.setBehaviorStarted(true);
                    updateStartButtonText(true);
                    Utils.getRoboboManager().getModuleInstance(IEmotionModule.class).subscribe(this);

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
        if(Utils.isBehaviorStarted()) {
            ContextManager.stopExecution();
            Utils.setBehaviorStarted(false);
            try {
                IRobMovementModule module = Utils.getRoboboManager().getModuleInstance(IRobMovementModule.class);
                module.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
