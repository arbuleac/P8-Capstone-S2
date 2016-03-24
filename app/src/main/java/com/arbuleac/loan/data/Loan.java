package com.arbuleac.loan.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @since 3/23/16.
 */
public class Loan implements Parcelable {
    //Denumire produs
    private String name;
    //Bank
    private Bank bank;
    //Comision la acordare (platit  la obtinerea creditului)
    private String initialCommission;
    //Comision anual
    private String annualCommission;
    //Costul cu asigurarile (primul an)
    private String insuranceCost;
    //DAE
    private String dae;
    //Tip dobanda
    private String loanInterestsType;
    //Mod de functionare dobanda
    private String loanInterestsDetails;
    //Coditii speciale
    private String specialConditions;
    //Comision de rambursare anticipata
    private String anticipatedCommission;
    //Varsta minima
    private String minimumAge;
    //Grad maxim de indatorare
    private String maximumDebt;
    //Perioada de gratie
    private String freePeriod;
    //Avans minim
    private String minimalInitialPayment;
    //Asigurari
    private String insurance;
    //Garantii
    private String guarantee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getInitialCommission() {
        return initialCommission;
    }

    public void setInitialCommission(String initialCommission) {
        this.initialCommission = initialCommission;
    }

    public String getAnnualCommission() {
        return annualCommission;
    }

    public void setAnnualCommission(String annualCommission) {
        this.annualCommission = annualCommission;
    }

    public String getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(String insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public String getDae() {
        return dae;
    }

    public void setDae(String dae) {
        this.dae = dae;
    }

    public String getLoanInterestsType() {
        return loanInterestsType;
    }

    public void setLoanInterestsType(String loanInterestsType) {
        this.loanInterestsType = loanInterestsType;
    }

    public String getLoanInterestsDetails() {
        return loanInterestsDetails;
    }

    public void setLoanInterestsDetails(String loanInterestsDetails) {
        this.loanInterestsDetails = loanInterestsDetails;
    }

    public String getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public String getAnticipatedCommission() {
        return anticipatedCommission;
    }

    public void setAnticipatedCommission(String anticipatedCommission) {
        this.anticipatedCommission = anticipatedCommission;
    }

    public String getMaximumDebt() {
        return maximumDebt;
    }

    public void setMaximumDebt(String maximumDebt) {
        this.maximumDebt = maximumDebt;
    }

    public String getFreePeriod() {
        return freePeriod;
    }

    public void setFreePeriod(String freePeriod) {
        this.freePeriod = freePeriod;
    }

    public String getMinimalInitialPayment() {
        return minimalInitialPayment;
    }

    public void setMinimalInitialPayment(String minimalInitialPayment) {
        this.minimalInitialPayment = minimalInitialPayment;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(String minimumAge) {
        this.minimumAge = minimumAge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.bank, flags);
        dest.writeString(this.initialCommission);
        dest.writeString(this.annualCommission);
        dest.writeString(this.insuranceCost);
        dest.writeString(this.dae);
        dest.writeString(this.loanInterestsType);
        dest.writeString(this.loanInterestsDetails);
        dest.writeString(this.specialConditions);
        dest.writeString(this.anticipatedCommission);
        dest.writeString(this.minimumAge);
        dest.writeString(this.maximumDebt);
        dest.writeString(this.freePeriod);
        dest.writeString(this.minimalInitialPayment);
        dest.writeString(this.insurance);
        dest.writeString(this.guarantee);
    }

    public Loan() {
    }

    protected Loan(Parcel in) {
        this.name = in.readString();
        this.bank = in.readParcelable(Bank.class.getClassLoader());
        this.initialCommission = in.readString();
        this.annualCommission = in.readString();
        this.insuranceCost = in.readString();
        this.dae = in.readString();
        this.loanInterestsType = in.readString();
        this.loanInterestsDetails = in.readString();
        this.specialConditions = in.readString();
        this.anticipatedCommission = in.readString();
        this.minimumAge = in.readString();
        this.maximumDebt = in.readString();
        this.freePeriod = in.readString();
        this.minimalInitialPayment = in.readString();
        this.insurance = in.readString();
        this.guarantee = in.readString();
    }

    public static final Parcelable.Creator<Loan> CREATOR = new Parcelable.Creator<Loan>() {
        @Override
        public Loan createFromParcel(Parcel source) {
            return new Loan(source);
        }

        @Override
        public Loan[] newArray(int size) {
            return new Loan[size];
        }
    };
}
