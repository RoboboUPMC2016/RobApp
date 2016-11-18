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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.robapp.R;
import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.service.RoboboServiceHelper;
import com.mytechia.robobo.rob.BluetoothRobInterfaceModule;
import com.robapp.app.adapter.BehaviorAdapter;
import com.robapp.app.dialog.Launcher;
import com.robapp.app.dialog.RobDeviceSelectionDialog;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.app.listener.FABListener;
import com.robapp.utils.Utils;

import java.util.ArrayList;

public class BehaviorActivity extends BaseActivity {

    private static int colorRed = Color.argb(0,100,0,0);
    private static int colorGreen = Color.argb(0,0,100,0);

    Button startButton;
    TextView behaviorName;
    boolean behaviorStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        behaviorStarted = false;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_behavior);
        setupDrawer();

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setTextColor(Color.GREEN);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!robStarted)
                {
                    showRoboboDeviceSelectionDialog();
                    return;
                }


                behaviorStarted = !behaviorStarted;
                if(behaviorStarted && selectedBehavior != null)
                {
                    behaviorStarted = true;
                    Launcher launcher = new Launcher(BehaviorActivity.this,5,selectedBehavior);
                    launcher.show();
                    startButton.setTextColor(Color.RED);
                    startButton.setText("Stopper");
                }
                else
                {
                    behaviorStarted = false;
                    startButton.setTextColor(Color.GREEN);
                    startButton.setText("Demarrer");
                }


            }
        });

        behaviorName =(TextView)findViewById(R.id.nameBehavior);
        if(selectedBehavior != null)
            behaviorName.setText(selectedBehavior.getName());

    }



}
