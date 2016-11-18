package com.robapp.app.listener;

import android.content.Context;
import android.view.View;

import com.robapp.app.activity.FileExplorerActivity;
import com.robapp.app.adapter.FileExplorerAdapter;

import java.io.File;

/**
 * Created by Arthur on 15/10/2016.
 */

public class FileExplorerLongListener implements View.OnLongClickListener {

    FileExplorerAdapter adapter;
    File f;
    FileExplorerActivity activity;

    public FileExplorerLongListener(FileExplorerAdapter adapter, File f, FileExplorerActivity activity){
        this.adapter=adapter;
        this.f=f;
        this.activity=activity;
    }

    @Override
    public boolean onLongClick(View v) {
      if(f.isFile())
      {
          activity.fileChoosen(f);
      }
        return true;
    }
}
