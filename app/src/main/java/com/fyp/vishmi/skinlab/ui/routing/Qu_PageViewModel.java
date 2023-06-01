package com.fyp.vishmi.skinlab.ui.routing;

import java.util.ArrayList;

public class Qu_PageViewModel {

    private String head;
    private String sub;
    private ArrayList<String> que_sub;

    public Qu_PageViewModel(String head, String sub, ArrayList<String> que_sub) {
        this.head = head;
        this.sub = sub;
        this.que_sub = que_sub;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public ArrayList<String> getQue_sub() {
        return que_sub;
    }

    public void setQue_sub(ArrayList<String> que_sub) {
        this.que_sub = que_sub;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
