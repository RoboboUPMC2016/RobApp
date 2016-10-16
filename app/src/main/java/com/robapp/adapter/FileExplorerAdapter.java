package com.robapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arthur.roboboapp.R;
import com.robapp.listener.FileExplorerListener;
import com.robapp.listener.FileExplorerLongListener;
import com.robapp.activity.FileExplorerActivity;

import java.io.File;
import java.util.ArrayList;


public class FileExplorerAdapter extends BaseAdapter {

    private ArrayList<File> files;
    private File dir;
    private static LayoutInflater inflater = null;
    private FileExplorerActivity  act;


    public FileExplorerAdapter(File dir, Context cont)
    {
        this.dir = dir;
        this.act = (FileExplorerActivity) cont;

        TextView text =  ((TextView)act.findViewById(R.id.dirName));
        text.setText(dir.getName());

        inflater = (LayoutInflater) cont
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        files = new ArrayList<File>();
        files.add(this.dir);

        if(this.dir.listFiles() == null)
            return;

        for(File f : this.dir.listFiles())
            files.add(f);
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        if (vi == null)
            vi = inflater.inflate(R.layout.file_list_item, null);

        ImageView view = (ImageView) vi.findViewById(R.id.imageView);

        if(files.get(position).isDirectory())
            view.setImageResource(R.drawable.ic_folder);
        else
            view.setImageResource(R.drawable.ic_file);

        TextView text = (TextView) vi.findViewById(R.id.textView10);

        File f = files.get(position);
        String path = f.getAbsolutePath();
        String root = "/storage";
        System.out.println("===> "+path);


        if(position == 0)
        {
            if(root.compareTo(path) != 0)
            {
                text.setText("..");
                vi.setOnClickListener(new FileExplorerListener(this,new File(dir.getParent())));
                vi.setVisibility(View.VISIBLE);
            }
            else
            {
                text.setText(".");
                vi.setOnClickListener(new FileExplorerListener(this,dir));
                vi.setVisibility(View.GONE);
            }
        }
        else
        {

            text.setText(f.getName());

            if(f.isFile())
                vi.setOnLongClickListener(new FileExplorerLongListener(this, f,act));
            else
                vi.setOnClickListener(new FileExplorerListener(this, f));


        }

        return vi;
    }

    public void setDir(File dir)
    {

        TextView text =  ((TextView)act.findViewById(R.id.dirName));
        String value = dir.getName();
        value.replaceAll("\n","");
        text.setText(value);
        this.dir = dir;

        files.clear();
        files.add(dir);

        if(this.dir.listFiles() == null)
            return;

        for(File f : this.dir.listFiles())
            files.add(f);
    }
}
