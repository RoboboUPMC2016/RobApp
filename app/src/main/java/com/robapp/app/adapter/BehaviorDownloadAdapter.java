package com.robapp.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.robapp.R;
import com.robapp.app.activity.DownloadBehaviorActivity;
import com.robapp.app.activity.FileExplorerActivity;
import com.robapp.behaviors.interfaces.BehaviorItemI;
import com.robapp.behaviors.item.NativeBehaviorItem;

import org.apache.commons.codec.language.DoubleMetaphone;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Arthur on 29/01/2017.
 */

public class BehaviorDownloadAdapter extends BaseAdapter {

    private ArrayList<ItemDownload> list;
    private static LayoutInflater inflater = null;
    private DownloadBehaviorActivity act;

    public BehaviorDownloadAdapter (ArrayList<ItemDownload>list,DownloadBehaviorActivity act)
    {
        this.list = list;

        inflater = (LayoutInflater) act.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.act = act;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.behavior_item_download_act,null);

        final ItemDownload item =  (ItemDownload) getItem(position);
        TextView label = (TextView) convertView.findViewById(R.id.nameB);
        RatingBar note =  (RatingBar) convertView.findViewById(R.id.noteB);
        TextView desc =  (TextView) convertView.findViewById(R.id.descB);

        label.setText(item.getLabel());
        Double d = Double.parseDouble(item.getNote());
        note.setProgress(d.intValue());
        desc.setText(item.getDesc());

        if(item.isVisible())
            convertView.setVisibility(View.VISIBLE);
        else
            convertView.setVisibility(View.GONE);

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    act.downloadFile(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        return convertView;
    }

    public void clear()
    {
        list.clear();
    }

    public void addAll(Collection<ItemDownload> collection)
    {
        list.addAll(collection);
    }

    public void filtre(ArrayList<ItemDownload> newList,String text)
    {
        list.clear();
        for(ItemDownload item : newList)
        {
            if(text.length() == 0 || stringMatch(item,text))
                list.add(item);
        }

        notifyDataSetChanged();
    }

    private boolean stringMatch(ItemDownload item,String text)
    {
        String textUpper = text.toUpperCase();
        String labelUpper = item.getLabel().toUpperCase();

        if(labelUpper.contains(textUpper))
            return true;


        String descUpper = item.getDesc().toUpperCase();

        if(descUpper.contains(textUpper))
            return true;

        return false;
    }
}
