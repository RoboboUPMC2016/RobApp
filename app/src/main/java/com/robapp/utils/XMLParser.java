package com.robapp.utils;

import android.util.Log;

import com.robapp.app.activity.BaseActivity;
import com.robapp.behaviors.item.BehaviorFileItem;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Arthur on 20/11/2016.
 */

public class XMLParser {

    public static List<BehaviorFileItem> parse(InputStream is) {
        List<BehaviorFileItem> items = null;
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            // create a SAXXMLHandler
            XMLHandler handler = new XMLHandler();
            // store handler in XMLReader
            xmlReader.setContentHandler(handler);
            // the process starts
            xmlReader.parse(new InputSource(is));
            // get the `Employee list`
            items = handler.getItem();

        } catch (Exception ex) {
            Log.d("XML", "SAXXMLParser: parse() failed");
        }

        // return Employee list
        return items;
    }
}
