package com.robapp.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mytechia.robobo.framework.RoboboManager;
import com.robapp.app.activity.BaseActivity;
import com.robapp.app.activity.BehaviorActivity;
import com.robapp.behaviors.item.ItemDownload;
import com.robapp.behaviors.item.BehaviorFileItem;
import com.robapp.behaviors.listener.StatusListener;
import com.robapp.behaviors.natives.AngryRobotBehavior;
import com.robapp.behaviors.natives.DummyBehavior;
import com.robapp.behaviors.item.NativeBehaviorItem;
import com.robapp.behaviors.natives.InfiniteRoundBehavior;
import com.robapp.behaviors.natives.ReactiveBehavior;
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
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Arthur on 16/10/2016.
 */

public class Utils {

    private static final String xmlFileName = "robapp_behaviors_.xml";
    public  static String defaultUrl ="http://robhub.esy.es/";

    private static final int QRCodeSize = 400;
    private static boolean behaviorStarted = false;

    private static File downloadedDir;
    private static File importedDir;

    private static BaseActivity current = null;
    private static RoboboManager roboboManager;

    private static ArrayList<BehaviorItemI> behaviors;
    private static StatusListener statusListener;


    /**
     * Init the application content (behaviors)
     * @param context The application context
     * @throws IOException
     */
    public static void init(Context context) throws IOException {
        //Load all behaviors
        if(behaviors == null)
            behaviors = new ArrayList<BehaviorItemI>();
        else
            behaviors.clear();
        //Native behaviors
        behaviors.add(new NativeBehaviorItem("Dummy Behavior Native",new DummyBehavior()));
        behaviors.add(new NativeBehaviorItem("Round Trip",new RoundTripBehavior()));
        behaviors.add(new NativeBehaviorItem("Square Trip",new SquareTripBehavior()));
        behaviors.add(new NativeBehaviorItem("Infinite Shock Behavior",new InfiniteRoundBehavior()));
        behaviors.add(new NativeBehaviorItem("Reactive Behavior",new ReactiveBehavior()));
        behaviors.add(new NativeBehaviorItem("Angry", new AngryRobotBehavior()));

        //Init dir
        downloadedDir = context.getDir("behavior_downloaded",Context.MODE_PRIVATE);
        if(!downloadedDir.exists())
            downloadedDir.mkdir();
        //Init dir
        importedDir = new File(downloadedDir.getAbsolutePath()+File.separator+"imported");
        if(!importedDir.exists())
            importedDir.mkdir();

        //File which contains information about behavior
        File xmlFile = new File(downloadedDir.getAbsoluteFile()+ File.separator+xmlFileName);


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

            List<BehaviorFileItem> items = XMLParser.parse(new FileInputStream(xmlFile));
            if(items != null)
                behaviors.addAll(items);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set current Activity
     * @param current the current activity
     */
    public static void setCurrentActivity(BaseActivity current)
    {
        Utils.current = current;
    }

    /**
     * Get the current activity
     * @return The current activity
     */
    static public BaseActivity getCurrentActivity()
    {
        return current;
    }

    /**
     * Write a byte array to a file
     * @param data byte data
     * @param f the output
     */
    static public void writeDataToFile(byte [] data, File f)
    {
        try {
            if(f.exists())
                f.delete();

            FileOutputStream out = new FileOutputStream(f);
            out.write(data);
            out.close();
            f.createNewFile();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a downloaded file
     * @param itemD The item download of the behavior downloaded
     * @param data The data from the file
     * @throws Exception
     */
    static public void saveDownloadFile(ItemDownload itemD, byte [] data) throws Exception {

        String fileName = itemD.getFileName();
        File dir = new File(downloadedDir.getAbsolutePath()+File.separator+itemD.getId());

        try{
            if(dir.exists())
            {
                //update information and file
                dir.delete();
                for(int i = 0; i< behaviors.size();i++)
                {

                    BehaviorItemI item = behaviors.get(i);
                    if(item instanceof BehaviorFileItem)
                    {
                        int id1 = ((BehaviorFileItem) item).getId();
                        int id2 = Integer.parseInt(itemD.getId());
                        if(id1 == id2 )
                        {
                            behaviors.remove(i);
                            break;
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //Create a dir for the behavior -> id/file.dex
        dir.mkdir();
        File f = new File(downloadedDir.getAbsolutePath()+File.separator+itemD.getId()+File.separator+fileName);
        f.createNewFile();
        writeDataToFile(data,f);


        BehaviorFileItem item = new BehaviorFileItem();
        item.setUrl(itemD.getDetailsURL());
        item.setName(itemD.getLabel());


        item.setFile(f);
        item.setId(Integer.parseInt(itemD.getId()));

        behaviors.add(item);

        //Update the xml file
        File xmlFile = new File(downloadedDir.getAbsoluteFile()+ File.separator+xmlFileName);

        if(xmlFile.exists())
            xmlFile.delete();

        createXMLFile(xmlFile);


    }

    /**
     * Move a file on the file system
     * @param f The original file
     * @param dir the destination
     * @return The moved file
     * @throws Exception
     */
    static public File moveFileToDir(File f,File dir) throws Exception
    {

        File newF = new File(dir.getAbsolutePath()+"/"+ f.getName());
        if(newF.exists())
            newF.delete();
        newF.createNewFile();

        FileChannel in = new FileInputStream(f).getChannel();
        FileChannel out = new FileOutputStream(newF).getChannel();

        in.transferTo(0,in.size(),out);

        return newF;
    }

    /**
     *
     * Move a behavior imported
     * @param context The application context
     * @param f the file to move
     * @return
     */
    static public boolean moveBehaviorImported(Context context, File f)
    {

        //create a dit which is imported/yyyymmddhhmmss/file.dex
        //"yyyymmddhhmmss" is the time stamp for avoiding error

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String dirName = dateFormat.format(timestamp);
        File dir = new File(importedDir.getAbsolutePath()+File.separator+dirName);
        if(!dir.exists())
            dir.mkdir();
        try {

            File newF = moveFileToDir(f, dir);

            StringTokenizer tok = new StringTokenizer(newF.getName(),".");
            BehaviorFileItem item = new BehaviorFileItem();

            item.setUrl(defaultUrl);
            item.setName(tok.nextToken());
            item.setFile(newF);

            behaviors.add(item);

            File xmlFile = new File(downloadedDir.getAbsoluteFile()+ File.separator+xmlFileName);
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

    /**
     * Delete a behavior
     * @param item The item to remove
     */
    static public  void removeBehavior(BehaviorFileItem item)
    {
        behaviors.remove(item);
        File parent = item.getFile().getParentFile();
        item.getFile().delete();
        parent.delete();

        File xmlFile = new File(downloadedDir.getAbsoluteFile()+ File.separator+xmlFileName);
        if(xmlFile.exists())
            xmlFile.delete();

        try {
            createXMLFile(xmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get all behavior available in the application
     * @return A list which contains all behaviors
     */
    static public ArrayList<BehaviorItemI> getAllItem()
    {
        return behaviors;
    }

    /**
     * Set the Robobomanger
     * @param roboboManager The RoboboManger
     */
    static public void setRoboboManager(RoboboManager roboboManager)
    {
        Utils.roboboManager = roboboManager;
    }

    /**
     * Get the RoboboManager
     * @return
     */
    static public RoboboManager getRoboboManager()
    {
        return roboboManager;
    }

    /**
     * Create a file xml about behaviors
     * @param file The xml file
     * @throws IOException
     */
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
            buff.append("<id>"+((BehaviorFileItem) item).getId()+"</id>\n");
            buff.append("<name>"+item.getName()+"</name>\n");
            buff.append("<url>"+((BehaviorFileItem) item).getUrl()+"</url>\n");
            buff.append("<file>"+((BehaviorFileItem) item).getFile().getAbsolutePath()+"</file>\n");

            buff.append("</behavior>\n");

            writer.write(buff.toString());
        }

        writer.write("</behaviors>\n");
        writer.close();

    }

    /**
     * Create a QRCode from a string
     * @param data The data to encode
     * @param img The output picture
     * @throws WriterException
     */
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
            Toast.makeText(Utils.getCurrentActivity().getApplicationContext(), "Generation QRCode Error",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Tel if a behavior is currently running
     * @return True if a behavior is running
     */
    public static boolean isBehaviorStarted() {
        return behaviorStarted;
    }

    /**
     * Set if a behavior is running
     * @param behaviorStarted
     */
    public static void setBehaviorStarted(boolean behaviorStarted) {
        Utils.behaviorStarted = behaviorStarted;
    }

    /**
     * Update the button of the BehaviorActivity
     */
    public static void updateBehaviorActivity() {
            if(current instanceof  BehaviorActivity)
                    ((BehaviorActivity) current).updateStartButtonText(behaviorStarted);
    }


    /**
     * Get the status listener
     * @return the status listener
     */
    public static StatusListener getStatusListener() {
        return statusListener;
    }

    /**
     * Set the status listener
     * @param statusListener
     */
    public static void setStatusListener(StatusListener statusListener) {
        Utils.statusListener = statusListener;
    }
}
