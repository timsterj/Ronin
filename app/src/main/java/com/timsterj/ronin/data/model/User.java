package com.timsterj.ronin.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class User {

    private String regType;

    @NotNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_phone_number")
    private String phoneNumber;
    @ColumnInfo(name = "user_name")
    private String name;

    private String descr;
    @ColumnInfo(name = "user_street")
    private String street;
    @ColumnInfo(name = "user_home")
    private String home;
    @ColumnInfo(name = "user_pod")
    private String pod;
    @ColumnInfo(name = "user_et")
    private String et;
    @ColumnInfo(name = "user_apart")
    private String apart;

    public User(String regType, @NotNull String phoneNumber, String name, String descr, String street, String home, String pod, String et, String apart) {
        this.regType = regType;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.descr = descr;
        this.street = street;
        this.home = home;
        this.pod = pod;
        this.et = et;
        this.apart = apart;
    }

    @Ignore
    public User() {
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public String getApart() {
        return apart;
    }

    public void setApart(String apart) {
        this.apart = apart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
