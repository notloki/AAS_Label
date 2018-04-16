package com.notloki.aas_label;

public class Ref {

    // For Title
    public static final String COMPANY_NAME = "All American Steel";
    public static final String VERSION = "0.4.0b";
    public static final String VERSION_DATE = "04/16/2018";

    // Screen Starting Size
    public static final int WIDTH = 850;
    public static final int HEIGHT = 920;


    // Weight of steel per unit  Default is 2 pounds per linear foot.
    public static final int STEEL_WEIGHT = 2;

    // Printer Network Data
    public static final String PRINTER_IP = "10.100.89.70";
    public static final int PRINTER_PORT = 9100;


    // Path to Datamax Print file.
    public static final String FILE_PATH_BAL = "./Balance.prn";
    public static final String FILE_PATH_SO = "./specialOrder.prn";


    // Byte addresses for Balance Label
    public static final int PO_BYTE_BAL = 5321;
    public static final int BALANCE_BYTE_BAL = 5354;


    // Byte addresses for SO Label

    public static final int ROW_ONE_BYTE_SO = 2558;
    public static final int ROW_TWO_BYTE_SO = 2597;
    public static final int ROW_THREE_BYTE_SO = 2636;
    public static final int ROW_FOUR_BYTE_SO = 2675;
    public static final int ROW_FIVE_BYTE_SO = 2714;
    public static final int ROW_SIX_BYTE_SO = 2753;
    public static final int ROW_SEVEN_BYTE_SO = 2792;
    public static final int ROW_EIGHT_BYTE_SO = 2831;
    public static final int ROW_NINE_BYTE_SO = 2870;
    public static final int ROW_TEN_BYTE_SO = 2909;
    public static final int ROW_ELEVEN_BYTE_SO = 2948;
    public static final int ROW_TWELVE_BYTE_SO = 2987;

    public static final int PO_BYTE_SO = 2520;
    public static final int CUST_NAME_BYTE_SO = 3074;
    public static final int JOB_NAME_BYTE_SO = 3157;
    public static final int LOCATION_BYTE_SO = 3245;

    public static final int BALANCE_LABELS = 0;
    public static final int SO_LABELS = 1;

    public static final boolean DEBUG_ENABLED = false;
    public static final boolean DISABLE_PRINTING = false;
    public static final boolean ENABLE_FILL = false;
}

