package com.robapp.app.listener;

import android.view.View;
import com.robapp.app.adapter.FileExplorerAdapter;
import java.io.File;


/**
 * OnClick listener for items of the file explorer activity
 */
public class FileExplorerListener implements View.OnClickListener {

    FileExplorerAdapter adapter;
    File f;

    public FileExplorerListener(FileExplorerAdapter adapter,File f){
        this.adapter=adapter;
        this.f=f;
    }

    @Override
    public void onClick(View v) {

        if(f.isDirectory())
        {
            adapter.setDir(f);
            adapter.notifyDataSetChanged();
        }

    }

}
