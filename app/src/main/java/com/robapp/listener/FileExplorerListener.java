package com.robapp.listener;

import android.view.View;
import com.robapp.adapter.FileExplorerAdapter;
import java.io.File;


/**
 * Created by Arthur on 17/07/2015.
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
