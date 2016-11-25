package com.robapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mytechia.robobo.framework.RoboboManager;
import com.robapp.R;
import com.robapp.app.activity.BaseActivity;
import com.robapp.behaviors.item.BehaviorFileItem;
import com.robapp.behaviors.natives.DummyBehavior;
import com.robapp.behaviors.item.NativeBehaviorItem;
import com.robapp.behaviors.natives.RoundTripBehavior;
import com.robapp.behaviors.natives.SquareTripBehavior;
import com.robapp.behaviors.interfaces.BehaviorItemI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ch.qos.logback.core.net.SyslogOutputStream;

/**
 * Created by Arthur on 16/10/2016.
 */

public class Utils {

    private static BaseActivity current = null;
    private static RoboboManager roboboManager;
    private static ArrayList<BehaviorItemI> behaviors;
    private static File privateDir;
    private static final String xmlFileName = "robapp_behaviors_.xml";
    public static String defaultUrl ="http://robhub.esy.es/";
    private static final int QRCodeSize = 400;


    public static void init(Context context)
    {
        if(behaviors == null)
            behaviors = new ArrayList<BehaviorItemI>();
        else
            behaviors.clear();

        behaviors.add(new NativeBehaviorItem("Dummy Behavior Native",new DummyBehavior()));
        behaviors.add(new NativeBehaviorItem("Round Trip",new RoundTripBehavior()));
        behaviors.add(new NativeBehaviorItem("Square Trip",new SquareTripBehavior()));

        privateDir = context.getDir("behavior_downloaded",Context.MODE_PRIVATE);

        File xmlFile = new File(privateDir.getAbsoluteFile()+ File.separator+xmlFileName);
        System.out.println("Downloaded Behavior ==> "+xmlFile.getAbsolutePath());


        try{
            if(!xmlFile.exists())
              createXMLFile(xmlFile);

            FileReader reader = new FileReader(xmlFile);

            int data = reader.read();
            StringBuffer buff = new StringBuffer("");

            while(data != -1)
            {
                buff.append(""+(char)data);
                data = reader.read();
            }

            System.out.println("Buffer ==> "+buff.toString());

            List<BehaviorFileItem> items = XMLParser.parse(new FileInputStream(xmlFile));
            if(items != null)
                behaviors.addAll(items);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void setCurrentActivity(BaseActivity current)
    {
        Utils.current = current;
    }

    static public BaseActivity getCurrentActivity()
    {
        return current;
    }

    static public File moveFileToDir(File f,File dir) throws Exception
    {

        System.out.println("File : "+f.getAbsolutePath());
        File newF = new File(dir.getAbsolutePath()+"/"+ f.getName());
        if(newF.exists())
            newF.delete();
        newF.createNewFile();
        System.out.println("New File : "+newF.getAbsolutePath());
        FileChannel in = new FileInputStream(f).getChannel();
        FileChannel out = new FileOutputStream(newF).getChannel();

        in.transferTo(0,in.size(),out);

        return newF;
    }

    static public boolean moveBehaviorDownloaded(Context context,File f)
    {

        File dir = context.getDir("behavior_downloaded",Context.MODE_PRIVATE);

        try {

            File newF = moveFileToDir(f, dir);
            StringTokenizer tok = new StringTokenizer(newF.getName(),".");

            BehaviorFileItem item = new BehaviorFileItem();
            item.setUrl(defaultUrl);
            item.setName(tok.nextToken());
            item.setFile(newF);

            behaviors.add(item);

            File xmlFile = new File(privateDir.getAbsoluteFile()+ File.separator+xmlFileName);

            if(xmlFile.exists())
                xmlFile.delete();

            createXMLFile(xmlFile);

            return true;
        }
        catch(Exception e)
        {
            System.err.println("Error : behavior not moved");
            e.printStackTrace();
        }

        return false;

    }

    static public ArrayList<BehaviorItemI> getAllItem()
    {
        return behaviors;
    }

    static public void setRoboboManager(RoboboManager roboboManager)
    {
        Utils.roboboManager = roboboManager;
    }

    static public RoboboManager getRoboboManager()
    {
        return roboboManager;
    }

    static void createXMLFile(File file) throws IOException {

        FileWriter writer = new FileWriter(file);
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<behaviors>\n");

        for(BehaviorItemI item : behaviors)
        {
            if(!(item instanceof BehaviorFileItem))
                continue;

            StringBuffer buff = new StringBuffer("");
            buff.append("<behavior>\n");
            buff.append("<name>"+item.getName()+"</name>\n");
            buff.append("<url>"+((BehaviorFileItem) item).getUrl()+"</url>\n");
            buff.append("<file>"+((BehaviorFileItem) item).getFile().getAbsolutePath()+"</file>\n");
            buff.append("</behavior>\n");

            writer.write(buff.toString());
        }

        writer.write("</behaviors>\n");
        writer.close();

    }

    public static void generateQRCode(String data,ImageView img) throws WriterException {

        com.google.zxing.Writer writer = new QRCodeWriter();

        BitMatrix bm = writer.encode(data, BarcodeFormat.QR_CODE,QRCodeSize,QRCodeSize);
        Bitmap ImageBitmap = Bitmap.createBitmap(QRCodeSize, QRCodeSize, Bitmap.Config.ARGB_8888);

        for (int i = 0; i <QRCodeSize; i++) {//width
            for (int j = 0; j < QRCodeSize; j++) {//height
                ImageBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK: Color.WHITE);
            }
        }

        if (ImageBitmap != null) {
            img.setImageBitmap(ImageBitmap);
        } else {
            Toast.makeText(Utils.getCurrentActivity().getApplicationContext(), "Génération QRCode Error",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
