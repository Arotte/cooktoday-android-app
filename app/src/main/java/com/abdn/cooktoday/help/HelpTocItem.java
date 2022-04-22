package com.abdn.cooktoday.help;

public class HelpTocItem {
    private String title;
    private String shortTitle;
    private int layoutId;

    public HelpTocItem(String title, String shortTitle, int layoutId) {
        this.title = title;
        this.shortTitle = shortTitle;
        this.layoutId = layoutId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }
}