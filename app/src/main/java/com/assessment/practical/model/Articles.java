package com.assessment.practical.model;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Articles  implements Serializable {


    private String status;
    private String copyright;
    private String num_results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getNum_results() {
        return num_results;
    }

    public void setNum_results(String num_results) {
        this.num_results = num_results;
    }

    public ArrayList<ResultsList> getResultsList() {
        return resultsList;
    }

    public void setResultsList(ArrayList<ResultsList> resultsList) {
        this.resultsList = resultsList;
    }

    private ArrayList<ResultsList> resultsList;





}
