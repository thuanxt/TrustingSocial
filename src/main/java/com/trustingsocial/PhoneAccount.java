package com.trustingsocial;

import java.time.LocalDate;

public class PhoneAccount implements Comparable<PhoneAccount> {
    static private final String CSV_DELIMITER = ",";

    private String phoneNumber;
    private LocalDate activationDate;
    private LocalDate deactivationDate;

    public PhoneAccount() {
    }

    public PhoneAccount(String line) {
        if (line != null && line.trim().length() > 0) {
            // split the record by csv delimiter
            String[] data = line.split(CSV_DELIMITER);

            // get the phone number
            if (data.length > 0 && data[0] != null && data[0].trim().length() > 0) {
                this.setPhoneNumber(data[0]);
            }

            // get the activation date
            if (data.length > 1 && data[1] != null && data[1].trim().length() > 0) {
                this.setActivationDate(LocalDate.parse(data[1]));
            }

            // get the deactivation date
            if (data.length > 2 && data[2] != null && data[2].trim().length() > 0) {
                this.setDeactivationDate(LocalDate.parse(data[2]));
            }
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDate getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(LocalDate deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    @Override
    public String toString() {
        return (phoneNumber == null ? "" : phoneNumber) + ","
                + (activationDate == null ? "" : activationDate);
    }

    @Override
    public int compareTo(PhoneAccount o) {
        if (this.phoneNumber.compareTo(o.phoneNumber) == 0) {
            // if the same phone number, order the activation date by descending method
            return this.activationDate.compareTo(o.activationDate) * (-1);
        }

        // otherwise, order the phone number by ascending method
        return this.phoneNumber.compareTo(o.phoneNumber);
    }
}
