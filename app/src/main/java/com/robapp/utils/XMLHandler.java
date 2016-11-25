package com.robapp.utils;

import android.content.Context;

import com.journeyapps.barcodescanner.Util;
import com.robapp.behaviors.item.BehaviorFileItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Arthur on 20/11/2016.
 */

public class XMLHandler extends DefaultHandler {

    private ArrayList<BehaviorFileItem> behaviors;
    private BehaviorFileItem tempItem;
    private String tempVal;
    private  File dir;

    public XMLHandler()
    {
        dir =  Utils.getCurrentActivity().getApplicationContext().getDir("behavior_downloaded", Context.MODE_PRIVATE);
        behaviors = new ArrayList<BehaviorFileItem>();
    }

    public XMLHandler(File dir)
    {
        this.dir = dir;
        behaviors = new ArrayList<BehaviorFileItem>();
    }

    public ArrayList<BehaviorFileItem> getItem()
    {
        return behaviors;
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        tempVal = "";
        if (qName.equalsIgnoreCase("behavior")) {
            tempItem = new BehaviorFileItem();
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        tempVal = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equalsIgnoreCase("behavior")) {
            behaviors.add(tempItem);
        } else if (qName.equalsIgnoreCase("url")) {
            if(tempVal.equals(""))
                tempVal = Utils.defaultUrl;
            tempItem.setUrl(tempVal);
        } else if (qName.equalsIgnoreCase("name")) {
            tempItem.setName(tempVal);
        } else if (qName.equalsIgnoreCase("file")) {
            File f = new File(tempVal);
            System.out.println(f);
            tempItem.setFile(f);
        }
    }
}
