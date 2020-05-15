package com.timsterj.ronin.model;

import com.timsterj.ronin.contracts.Contracts;

public class AboutUsItem {

    private AboutUs aboutUs;
    private Poster poster;
    int viewType;

    public AboutUsItem(AboutUs aboutUs) {
        this.aboutUs = aboutUs;
        viewType = Contracts.AdapterConstant.ABOUT_US_VIEW_TYPE;
    }

    public AboutUsItem(Poster poster) {
        this.poster = poster;
        viewType = Contracts.AdapterConstant.POSTER_VIEW_TYPE;
    }


    public AboutUs getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(AboutUs aboutUs) {
        this.aboutUs = aboutUs;
    }

    public Poster getPoster() {
        return poster;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
