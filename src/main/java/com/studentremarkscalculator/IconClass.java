package com.studentremarkscalculator;

/**
 * Created by Admin on 12/10/2016.
 */
public class IconClass {

    private int drawableID;
    private String titleStr;
    private String descStr;

    public IconClass() {
        super();         // TODO Auto-generated constructor stub
    }

    public IconClass(int drawableID, String titleStr, String descStr) {
        super();
        this.drawableID = drawableID;
        this.titleStr = titleStr;
        this.descStr = descStr;
    }

    public int getDrawableID() {
        return drawableID;
    }

    public void setDrawableID(int drawableID) {
        this.drawableID = drawableID;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getDescStr() {
        return descStr;
    }

    public void setDescStr(String descStr) {
        this.descStr = descStr;
    }

    @Override
    public String toString() {
        return "IconClass [drawableID=" + drawableID + ", titleStr=" + titleStr
                + ", descStr=" + descStr + "]";
    }
}
