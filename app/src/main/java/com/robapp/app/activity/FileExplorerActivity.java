package com.robapp.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.content.Intent;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.robapp.R;
import com.robapp.app.adapter.FileExplorerAdapter;
import com.robapp.utils.Utils;

import java.io.File;




public class FileExplorerActivity extends MainActivity {

    private ViewFlipper viewFlipper;

    private File file;
    private FileExplorerAdapter adapter;
    private File root;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fileexplorer_activity);

        root = new File("/storage");
        adapter = new FileExplorerAdapter(root,this);

        ListView list = (ListView) findViewById(R.id.listView7);
        list.setAdapter(adapter);

        Intent intent = new Intent(this,MainActivity.class);
    }

   /* @Override
    public void onBackPressed()
    {
            goBack();
    }*/

    public void fileChoosen(File f)
    {

        File dir = getDir("behavior",MODE_PRIVATE);

        try {
            Utils.moveFileToDir(f, dir);
            intent.putExtra("Selected",f.getName());
            goBack();
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
