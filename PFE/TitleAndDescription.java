package com.reydev.tuto.androiddatabase;

/**
 * THIS CLASS IS MADE TO MANIPULATE A TWO TEXT VIEw ELEMENTS IN A LISTVIEW
 *
 * @author MehdiReysey@gmail.com
 * @version 1.0
 * @since 2020
 */

public class TitleAndDescription {
    private String TAD_Title;
    private String TAD_Description;


    /**
     * TitleAndDescription Constructor
     * @param title Represent the title.
     * @param desc Represent the description
     */

    public TitleAndDescription(String title, String desc) {
        this.TAD_Title          = title;
        this.TAD_Description    = desc;
    }

    public String getTAD_Title() {
        return TAD_Title;
    }

    public String getTAD_Description() {
        return TAD_Description;
    }
}
