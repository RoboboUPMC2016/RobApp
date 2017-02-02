package com.robapp.behaviors.item;

/**
 * Created by Arthur on 29/01/2017.
 */

/**
 * The class which represents the beavior to download
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

    /**
     * Constructor
     * @param id The behavior ID
     * @param label The behavior label
     * @param dexURL The behavior download URL
     * @param detailsURL The behavior details page URL
     * @param note The behavior marl
     * @param desc The behavior description
     * @param fileName The behavior class name
     */
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

    /**
     * Get the behavior ID
     * @return The behavior ID
     */
    public String getId() {
        return id;
    }
    /**
     * Set the behavior ID
     * @param id The behavior ID
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Get the behavior label
     * @return The behavior label
     */
    public String getLabel() {
        return label;
    }
    /**
     * Set the behavior lablel
     * @param label The behavior label
     */
    public void setLabel(String label) {
        this.label = label;
    }
    /**
     *  Get the download URL
     * @return The download URL
     */
    public String getDexURL() {
        return dexURL;
    }

    /**
     * Set the download URL
     * @param dexURL The download URL
     */
    public void setDexURL(String dexURL) {
        this.dexURL = dexURL;
    }
    /**
     *  Get the behavior mark
     * @return The behavior mark
     */
    public String getNote() {
        return note;
    }
    /**
     *  Set the behavior mark
     * @param note The behavior mark
     */
    public void setNote(String note) {
        this.note = note;
    }
    /**
     *  Get the details page url
     * @return The detals page url
     */
    public String getDetailsURL() {
        return detailsURL;
    }
    /**
     *  Set the details page URL
     * @param detailsURL The details page URL
     */
    public void setDetailsURL(String detailsURL) {
        this.detailsURL = detailsURL;
    }
    /**
     * Get the behavior class name
     * @return
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * Set the behavior class name
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * Get the behavior description
     * @return The behavior description
     */
    public String getDesc() {
        return desc;
    }
    /**
     * Set the behavior description
     * @param desc The behavior description
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    /**
     * Get the visibility of the behavior
     * @return The behavior visibility
     */
    public boolean isVisible() {
        return visible;
    }
    /**
     * Set the behavior visibility
     * @param visible The behavior visibility
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
