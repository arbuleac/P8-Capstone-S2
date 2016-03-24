package com.arbuleac.loan.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @since 3/23/16.
 */
public class Bank implements Parcelable {
    //Banca
    private String name;
    //Logo banca
    private String logo;
    //Adresa de contact
    private String contactAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.logo);
        dest.writeString(this.contactAddress);
    }

    public Bank() {
    }

    protected Bank(Parcel in) {
        this.name = in.readString();
        this.logo = in.readString();
        this.contactAddress = in.readString();
    }

    public static final Parcelable.Creator<Bank> CREATOR = new Parcelable.Creator<Bank>() {
        @Override
        public Bank createFromParcel(Parcel source) {
            return new Bank(source);
        }

        @Override
        public Bank[] newArray(int size) {
            return new Bank[size];
        }
    };
}
