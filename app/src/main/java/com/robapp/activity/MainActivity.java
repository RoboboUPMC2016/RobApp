package com.robapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Spinner;

import com.robapp.R;
import com.mytechia.robobo.framework.RoboboManager;
import com.mytechia.robobo.framework.service.RoboboServiceHelper;
import com.mytechia.robobo.rob.BluetoothRobInterfaceModule;
import com.mytechia.robobo.rob.IRobInterfaceModule;
import com.mytechia.robobo.rob.movement.IRobMovementModule;
import com.robapp.adapter.BehaviorAdapter;
import com.robapp.adapter.FileExplorerAdapter;
import com.robapp.dialog.Launcher;
import com.robapp.interfaces.BehaviorItemI;
import com.robapp.listener.FABListener;
import com.robapp.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private static ProgressDialog waitDial = null;
    private RoboboServiceHelper roboboHelper;
    private View mainButton;
    private BehaviorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.mainactivitylayout);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new FABListener(this));

        roboboHelper = new RoboboServiceHelper(this,new RobHelperListener());

        Bundle options = new Bundle();
        options.putString(BluetoothRobInterfaceModule.ROBOBO_BT_NAME_OPTION, getIntent().
                getStringExtra(BluetoothRobInterfaceModule.ROBOBO_BT_NAME_OPTION));
        roboboHelper.bindRoboboService(options);

        System.out.println("RobName : "+getIntent().getStringExtra(BluetoothRobInterfaceModule.ROBOBO_BT_NAME_OPTION));

        adapter = new BehaviorAdapter(this);


        mainButton = findViewById(R.id.mainButton);

        Spinner list = (Spinner) findViewById(R.id.spinnerBehavior);
        list.setAdapter(adapter);

    }

    public static void setProgessDialog(ProgressDialog waitDial)
    {
        MainActivity.waitDial = waitDial;
    }

    private static void dissmisProgressDial()
    {
        MainActivity.waitDial.dismiss();
        MainActivity.waitDial=null;
    }

    protected void showErrorDialog(final String msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle(R.string.ErrorDialogTitle).
                        setMessage(msg);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                        //System.exit(0);
                    }
                })
                        .setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    //Inner Class for listening RoboboServiceHelper event
    private class RobHelperListener implements RoboboServiceHelper.Listener
    {

        @Override
        public void onRoboboManagerStarted(RoboboManager roboboManaer) {

            Utils.setRoboboManager(roboboManaer);
            dissmisProgressDial();
             mainButton.setClickable(true);
             mainButton.setOnClickListener(new View.OnClickListener() {

                 @Override
                 public void onClick(View v) {
                     Spinner list = (Spinner) findViewById(R.id.spinnerBehavior);
                     BehaviorItemI item = (BehaviorItemI)list.getSelectedItem();
                     Launcher launcher = new Launcher( MainActivity.this,5,item);
                     launcher.show();
                 }
             });
        }

        @Override
        public void onError(String errorMsg) {
            showErrorDialog(errorMsg);

            //When the rob is not connected the button for starting behavior should be change
            //If the connexion failed
            // the button will allow user to try the connexion on another device
            mainButton.setClickable(true);
            mainButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   goBackToConnexion();
               }
           });
        }

        public void goBackToConnexion()
        {
            Intent intent = new Intent(MainActivity.this,StartingActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }
}
