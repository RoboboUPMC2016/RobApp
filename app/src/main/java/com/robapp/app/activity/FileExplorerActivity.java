package com.robapp.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.content.Intent;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.robapp.R;
import com.robapp.app.adapter.FileExplorerAdapter;
import com.robapp.app.dialog.BehaviorSelectionDialog;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.utils.Utils;

import java.io.File;




public class FileExplorerActivity extends BaseActivity {

    private ViewFlipper viewFlipper;

    private File file;
    private FileExplorerAdapter adapter;
    private File root;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file_explorer);
        setupDrawer();

        root = new File("/storage");
        adapter = new FileExplorerAdapter(root,this);

        ListView list = (ListView) findViewById(R.id.listView7);
        list.setAdapter(adapter);

        Intent intent = new Intent(this,BehaviorActivity.class);
    }


    public void fileChoosen(File f)
    {

        try {
            Utils.moveBehaviorDownloaded(getApplicationContext(),f);

            final BehaviorSelectionDialog dialog = new BehaviorSelectionDialog();
            dialog.setListener(new BehaviorSelectionDialog.Listener() {
                @Override
                public void behaviorSelected(BehaviorItemI item) {
                    selectedBehavior = item;
                    Intent intent = new Intent(Utils.getCurrentActivity(),BehaviorActivity.class);
                    Utils.getCurrentActivity().startActivity(intent);
                }

                public void selectionCancelled()
                {}
            });

            dialog.show(getFragmentManager(),"Selection Comportement");

        }
        catch(Exception e) {
            String msg = "Probleme lors de la copie du ficher " + f.getName();
            msg+="\n\t"+e.getMessage();
            showChooseFileError(msg);
        }

    }

    public void showChooseFileError(String msg)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(FileExplorerActivity.this).create();
        alertDialog.setTitle("Ouups");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goBack();
                    }
                });
        alertDialog.show();
    }

    public void goBack()
    {
        this.startActivity(intent);
    }
}
