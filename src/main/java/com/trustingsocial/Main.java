package com.trustingsocial;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        // Default input file name
        String inFileName = "test.csv";
        // Default output file name
        String outFileName = "testOut.csv";
        // Output header
        final String header = "PHONE_NUMBER,REAL_ACTIVATION_DATE";

        // Get input file name
        if (args.length > 0) {
            inFileName = args[0];
        }

        // Get output file name
        if (args.length > 1) {
            outFileName = args[1];
        }

        // Get input data, sort and convert to list
        List<PhoneAccount> phoneAccountList = convertFileToList(inFileName);

        if (phoneAccountList != null && phoneAccountList.size() > 0) {
            // Get list of actual activation date
            phoneAccountList = getActualActivationDateList(phoneAccountList);

            // Write list of actual activation date to output file
            writeToFile(outFileName, header, phoneAccountList);
        }

    }

    /**
     * Convert data from csv file to the list of sorted PhoneAccount objects.
     *
     * @param fileName input file name
     * @return the list of sorted PhoneAccount objects
     */
    public static List<PhoneAccount> convertFileToList(String fileName) {
        List<PhoneAccount> phoneAccountList = null;

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            phoneAccountList = stream
                    // skip the header
                    .skip(1)
                    // convert record data to Phone Account
                    .map(PhoneAccount::new)
                    // sort Phone Account
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            // TODO logging in production
            e.printStackTrace();
        }

        return phoneAccountList;

    }

    /**
     * Get the list of phone accounts which have the actual activate date.
     *
     * @param phoneList the list of sorted PhoneAccount objects
     * @return the list of phone accounts which have the actual activation date
     */
    public static List<PhoneAccount> getActualActivationDateList(List<PhoneAccount> phoneList) {
        List<PhoneAccount> resultList = new ArrayList<>();
        // variable to store the previous phone account
        PhoneAccount prevPhoneAccount = new PhoneAccount();
        // flag to indicate the first record of each phone number block which is activated
        boolean foundActivatedPhone = false;
        for (PhoneAccount phoneAccount : phoneList) {
            if (foundActivatedPhone) { // if the first record has been found
                if (phoneAccount.getPhoneNumber().equals(prevPhoneAccount.getPhoneNumber())
                        && phoneAccount.getDeactivationDate().equals(prevPhoneAccount.getActivationDate())) {
                    // if the phone account number equals to the previous one
                    // and the deactivation date equals to the previous one's activation date
                    // it means that this phone number is still in the block and is owned by current user
                    // so just store the current phone account and move to next loop
                    prevPhoneAccount = phoneAccount;

                    if (phoneList.indexOf(phoneAccount) == phoneList.size() - 1) {
                        // if this is the last record so no need to wait for te next loop to evaluate the activation date
                        // it is the actual activation date so just add it to the result list
                        resultList.add(phoneAccount);
                    }
                } else {
                    // if phone account number doesn't equal to the previous one
                    // or the deactivation date doesn't equal to the previous one's activation date
                    // it means that this phone number is out of the current block or is owned by the old user
                    // so add the previous one to the list because that is the actual activation date of the current user
                    resultList.add(prevPhoneAccount);
                    // turn of the the flag
                    foundActivatedPhone = false;

                    if (phoneList.indexOf(phoneAccount) == phoneList.size() - 1
                            && !phoneAccount.getPhoneNumber().equals(prevPhoneAccount.getPhoneNumber())) {
                        // if this is the last record and its phone number is different from the previous one
                        // so this is the phone number owned by only one person and the plan has not been changed
                        resultList.add(phoneAccount);
                    }
                }
            } else { // if the first record has not been found
                if (!phoneAccount.getPhoneNumber().equals(prevPhoneAccount.getPhoneNumber())) {
                    // if this phone number does not equal to the previous phone number
                    // this is the first record of phone number block so turn on the flag
                    foundActivatedPhone = true;
                    // store current phone account in the previous phone account variable
                    prevPhoneAccount = phoneAccount;
                }
            }
        }

        return resultList;
    }

    /**
     * Convert data from csv file to the list of sorted PhoneAccount objects.
     *
     * @param fileName  output file name
     * @param header    output header record
     * @param phoneList the list of output records
     */
    public static void writeToFile(String fileName, String header, List<PhoneAccount> phoneList) {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(fileName)))) {
            // write header
            pw.println(header);
            // write all the records from the list
            phoneList.forEach(pw::println);
        } catch (IOException e) {
            // TODO logging in production
            e.printStackTrace();
        }
    }
}
