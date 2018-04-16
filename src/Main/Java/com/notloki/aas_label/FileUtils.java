package com.notloki.aas_label;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.ListIterator;

public class FileUtils {

    public FileUtils() {
    }

    public void savePrintFileBAL(String file, String bp, String poNumber) throws IOException {
        File f = new File(file);
        RandomAccessFile raf = new RandomAccessFile(file, "rwd");

        if (bp == null || bp.isEmpty() || bp.length() > 8) {
            bp = "      ";
        }
        if (poNumber == null || poNumber.isEmpty() || poNumber.length() > 8) {
            poNumber = "        ";
        }

        raf.seek(Ref.BALANCE_BYTE_BAL);
        raf.write(addSpace(bp, 8).getBytes(), 0, bp.length());

        raf.seek(Ref.PO_BYTE_BAL);
        raf.write(poNumber.getBytes(), 0, 8);
        raf.close();
    }


    public void savePrintFileSO(String file,
                                String custName, String jobName,
                                String poNumber, String location, ArrayList<Data> lengthList,
                                ArrayList<Integer> byteList) throws IOException {


        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
        if (custName == null || custName.isEmpty()) {
            custName = "               ";
        }

        if (jobName == null || jobName.isEmpty()) {
            jobName = "               ";
        }
        if (location == null || location.isEmpty()) {
            location = "               ";
        }
        if (poNumber == null || poNumber.isEmpty() || poNumber.length() > 8) {
            poNumber = "        ";
        }

        Data blank = new Data();
        if (lengthList.size() < 12) {
            int count = lengthList.size();
            while (count <= 12) {
                count++;
                blank.setQty("              ");
                lengthList.add(blank);
            }
        }
        ListIterator lengthListIterator = lengthList.listIterator();
        ListIterator lengthByteIterator = byteList.listIterator();
        int temp;

        while (lengthByteIterator.hasNext()) {
            temp = Integer.valueOf(lengthByteIterator.next().toString());
            if (Ref.DEBUG_ENABLED) {
                System.out.println(temp);
            }
            raf.seek(temp);
            raf.write(addSpace(lengthListIterator.next().toString(), 14).getBytes(), 0, 14);
        }

        raf.seek(Ref.CUST_NAME_BYTE_SO);
        raf.write(addSpace(custName, 15).getBytes(), 0, 15);

        raf.seek(Ref.JOB_NAME_BYTE_SO);
        raf.write(addSpace(jobName, 15).getBytes(), 0, 15);

        raf.seek(Ref.LOCATION_BYTE_SO);
        raf.write(addSpace(location, 15).getBytes(), 0, 15);

        raf.seek(Ref.PO_BYTE_SO);
        raf.write(poNumber.getBytes(), 0, 8);
        raf.close();

    }

    private String addSpace(String string, int length) {
        while (string.length() < length) {
            string += " ";
        }
        return string;
    }
}
