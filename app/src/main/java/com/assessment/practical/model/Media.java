package com.assessment.practical.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Media extends ArrayList<MediaMetadata> implements Serializable {
    private String type;
    private String subtype;
    private String caption;
    private String copyright;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getApproved_for_syndication() {
        return approved_for_syndication;
    }

    public void setApproved_for_syndication(String approved_for_syndication) {
        this.approved_for_syndication = approved_for_syndication;
    }

    public ArrayList<MediaMetadata> getMediametadata() {
        return mediametadata;
    }

    public void setMediametadata(ArrayList<MediaMetadata> mediametadata) {
        this.mediametadata = mediametadata;
    }

    private String approved_for_syndication;
    private ArrayList<MediaMetadata> mediametadata;

}
