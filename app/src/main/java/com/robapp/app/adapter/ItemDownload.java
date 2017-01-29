package com.robapp.app.adapter;

/**
 * Created by Arthur on 29/01/2017.
 */

public class ItemDownload {

    private String id;
    private String label;
    private String dexURL;
    private String detailsURL;
    private String note;
    private String desc;
    private String fileName;

    private boolean visible;

    public ItemDownload(String id, String label, String dexURL, String detailsURL, String note, String desc, String fileName) {
        this.id = id;
        this.label = label;
        this.dexURL = dexURL;
        this.detailsURL = detailsURL;
        this.note = note;
        this.desc = desc;
        this.fileName = fileName;

        visible = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDexURL() {
        return dexURL;
    }

    public void setDexURL(String dexURL) {
        this.dexURL = dexURL;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDetailsURL() {
        return detailsURL;
    }

    public void setDetailsURL(String detailsURL) {
        this.detailsURL = detailsURL;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
