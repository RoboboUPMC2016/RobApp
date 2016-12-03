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

import com.mytechia.robobo.framework.exception.ModuleNotFoundException;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.R;
import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.service.RoboboServiceHelper;
import com.mytechia.robobo.rob.BluetoothRobInterfaceModule;
import com.robapp.app.adapter.BehaviorAdapter;
import com.robapp.app.dialog.BehaviorSelectionDialog;
import com.robapp.app.dialog.Launcher;
import com.robapp.app.dialog.RobDeviceSelectionDialog;
import com.robapp.app.task.AsyncTask;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.app.listener.FABListener;
import com.robapp.utils.Utils;

import java.util.ArrayList;

public class BehaviorActivity extends BaseActivity {

    Button startButton;
    TextView behaviorName;
    private Thread thread;


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

    }



}
