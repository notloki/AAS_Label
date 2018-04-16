package com.notloki.aas_label;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Controller {
    @FXML
    TextField bp;
    @FXML
    TextField poNumber;
    @FXML
    TextField cName;
    @FXML
    TextField jName;
    @FXML
    ComboBox<String> loc;
    @FXML
    Button printButton;
    @FXML
    Button processButton;
    @FXML
    Button quitButton;
    @FXML
    Button fillFields;
    private ArrayList<String> inchArray = new ArrayList<>();
    private ArrayList<String> feetArray = new ArrayList<>();
    private ArrayList<String> qtyArray = new ArrayList<>();
    private ArrayList<Data> steelDimListArray = new ArrayList<>();
    private ArrayList<Double> panelMassList = new ArrayList<>();
    private ArrayList<Double> panelDatumList = new ArrayList<>();
    private ArrayList<Integer> finalByte = new ArrayList<>();
    private double moment;
    private double massTotal;
    private Data otherData = new Data();
    private String finalBalancePoint;
    @FXML
    private Label logo;
    @FXML
    private GridPane steelDimParent;

    @FXML
    private void quit() {
        System.exit(0);
    }

    @FXML
    public void print() {

        if (Ref.DISABLE_PRINTING) {
            printNow(Ref.FILE_PATH_SO);
            printNow(Ref.FILE_PATH_BAL);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Print Dialog");
        alert.setContentText("Printing Now");
        alert.showAndWait();
        printButton.setDisable(true);

    }

    @FXML
    public void fillFields() {
        ObservableList<Node> textFieldList = steelDimParent.getChildren();
        ListIterator textFieldListIterator = textFieldList.listIterator();
        while (textFieldListIterator.hasNext()) {
            Object obj = textFieldListIterator.next();
            if (obj instanceof TextField) {
                ((TextField) obj).setText("10");
            }
        }
    }


    @FXML
    private void printNow(String file) {
        try {
            Socket socket = new Socket(Ref.PRINTER_IP, Ref.PRINTER_PORT);

            InputStream is = new FileInputStream(file);

            DataOutputStream os = new DataOutputStream(socket.getOutputStream());

            BufferedInputStream bis = new BufferedInputStream(is);

            DataInputStream dis = new DataInputStream(bis);

            while (dis.available() != 0) {
                os.write(dis.read());

            }
            os.close();
            dis.close();
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void process() throws IOException {

        if (Ref.DEBUG_ENABLED) {
            System.out.println("Processing...");
        }
        ObservableList<Node> textFieldList = steelDimParent.getChildren();
        ListIterator textFieldListIterator = textFieldList.listIterator();
        while (textFieldListIterator.hasNext()) {
            Object temp = textFieldListIterator.next();
            if (temp instanceof TextField) {
                ((TextField) temp).setDisable(true);

            }
        }
        printButton.setDisable(false);
        processButton.setDisable(true);


        loc.setDisable(true);
        poNumber.setDisable(true);
        cName.setDisable(true);
        jName.setDisable(true);

        collectSteelDimData();
        collectOtherData();

        processSteelDimData();
        processOtherData();

    }

    @FXML
    private void processOtherData() throws IOException {

        if (Ref.DEBUG_ENABLED) {
            System.out.println("Processing Other Data");
            System.out.println("PO Number : " + otherData.getPoNumber());
            System.out.println("Customer Name : " + otherData.getCustName());
            System.out.println("Job Name : " + otherData.getJobName());
            System.out.println("Location : " + otherData.getLocation());
        }


        try {
            FileUtils fuSO = new FileUtils();

            fuSO.savePrintFileSO(Ref.FILE_PATH_SO, otherData.getCustName(), otherData.getJobName(),
                    otherData.getPoNumber(), otherData.getLocation(), steelDimListArray, finalByte);

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        try {
            FileUtils fuBal = new FileUtils();

            fuBal.savePrintFileBAL(Ref.FILE_PATH_BAL, finalBalancePoint, otherData.getPoNumber());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @FXML
    private void processSteelDimData() {
        if (Ref.DEBUG_ENABLED) {
            System.out.println("Processing Steel DimData...");
        }
        ListIterator steelDimListArrayIterator = steelDimListArray.listIterator();
        while (steelDimListArrayIterator.hasNext()) {
            Data data2 = (Data) steelDimListArrayIterator.next();

            if ((data2.getQty() != null)) {
                qtyArray.add(data2.getQty());
            }
            if (data2.getFt() != null) {
                feetArray.add(data2.getFt());

            }
            if ((data2.getIn() != null)) {
                inchArray.add(data2.getIn());

            }

        }
        Iterator qtyArrayIterator = qtyArray.iterator();
        Iterator feetArrayIterator = feetArray.iterator();
        Iterator inchArrayIterator = inchArray.iterator();
        while (qtyArrayIterator.hasNext()) {
            while (feetArrayIterator.hasNext()) {
                while (inchArrayIterator.hasNext()) {

                    String inchHolder = inchArrayIterator.next().toString();
                    if (Ref.DEBUG_ENABLED) {
                        System.out.println("Inch : " + inchHolder);
                    }

                    String qtyHolder = qtyArrayIterator.next().toString();
                    if (Ref.DEBUG_ENABLED) {
                        System.out.println("Qty : " + qtyHolder);
                    }

                    String feetHolder = feetArrayIterator.next().toString();
                    if (Ref.DEBUG_ENABLED) {
                        System.out.println("Feet : " + feetHolder);
                    }

                    double feet;
                    double inch;
                    int qty;
                    if (feetHolder != null || !(feetHolder.trim().isEmpty())) {
                        feet = Double.valueOf(feetHolder);

                    } else {
                        feet = 0;
                    }

                    if (inchHolder != null || !(inchHolder.trim().isEmpty())) {
                        inch = Double.valueOf(inchHolder);

                    } else {
                        inch = 0;
                    }
                    if (qtyHolder != null || !(inchHolder.trim().isEmpty())) {
                        qty = Integer.valueOf(qtyHolder);

                    } else {
                        qty = 0;
                    }

                    double mass = (feet + (inch / 12)) * Ref.STEEL_WEIGHT;
                    if (Ref.DEBUG_ENABLED) {
                        System.out.println("Mass = " + mass);
                    }
                    double datum = ((feet + (inch / 12))) / 2;
                    if (Ref.DEBUG_ENABLED) {
                        System.out.println("Datum = " + datum);
                    }
                    while (qty > 0) {
                        panelDatumList.add(datum);
                        panelMassList.add(mass);
                        qty--;

                    }
                }
            }
        }
        Iterator panelDatumListIterator = panelDatumList.listIterator();
        ListIterator panelMassListIterator = panelMassList.listIterator();

        while (panelDatumListIterator.hasNext()) {
            while (panelMassListIterator.hasNext()) {
                moment += (Double.valueOf(panelDatumListIterator.next().toString())
                        * Double.valueOf(panelMassListIterator.next().toString()));
            }
        }
        if (Ref.DEBUG_ENABLED) {
            System.out.println("Moment : " + moment);
        }
        while (panelMassListIterator.hasPrevious()) {
            panelMassListIterator.previous();
        }
        while (panelMassListIterator.hasNext()) {
            massTotal += Double.valueOf(panelMassListIterator.next().toString());
        }
        if (Ref.DEBUG_ENABLED) {
            System.out.println("Mass Total : " + massTotal);
        }

        double balancePoint = moment / massTotal;

        if (Ref.DEBUG_ENABLED) {
            System.out.println("Balance Point : " + balancePoint);
        }

        int finalFeet = (int) balancePoint;
        double finalIn = (balancePoint - (int) balancePoint) * 12;

        finalBalancePoint = finalFeet + "'" + " " + (int) finalIn + "\"";
        bp.setText(String.valueOf(finalBalancePoint));

    }

    @FXML
    private void initialize() {

        Image image = new Image(getClass().getResourceAsStream("/AASLogo.jpg"));
        logo.setGraphic(new ImageView(image));
        finalByte.add(Ref.ROW_ONE_BYTE_SO);
        finalByte.add(Ref.ROW_TWO_BYTE_SO);
        finalByte.add(Ref.ROW_THREE_BYTE_SO);
        finalByte.add(Ref.ROW_FOUR_BYTE_SO);
        finalByte.add(Ref.ROW_FIVE_BYTE_SO);
        finalByte.add(Ref.ROW_SIX_BYTE_SO);
        finalByte.add(Ref.ROW_SEVEN_BYTE_SO);
        finalByte.add(Ref.ROW_EIGHT_BYTE_SO);
        finalByte.add(Ref.ROW_NINE_BYTE_SO);
        finalByte.add(Ref.ROW_TEN_BYTE_SO);
        finalByte.add(Ref.ROW_ELEVEN_BYTE_SO);
        finalByte.add(Ref.ROW_TWELVE_BYTE_SO);
        printButton.setDisable(true);

    }

    @FXML
    private void collectSteelDimData() {
        if (Ref.DEBUG_ENABLED) {
            System.out.println("Collecting Data...");
        }
        ObservableList<Node> steelDimList = steelDimParent.getChildren();
        ListIterator steelDimListIterator = steelDimList.listIterator();

        while (steelDimListIterator.hasNext()) {
            Data data = new Data();
            Object ft = steelDimListIterator.next();
            if (ft instanceof TextField) {
                String temp = ((TextField) ft).getText();
                if (!(temp.isEmpty())) {
                    data.setFt(((TextField) ft).getText());
                    if (Ref.DEBUG_ENABLED) {
                        System.out.println(temp + " TextField Ft - collectSteelDimData");
                    }
                }
            }

            Object in = steelDimListIterator.next();
            if (in instanceof TextField) {
                if (!(((TextField) in).getText().isEmpty()) || (((TextField) in).getText() != null)) {
                    data.setIn(((TextField) in).getText());
                }
            }
            Object qty = steelDimListIterator.next();
            if (qty instanceof TextField) {
                String temp = ((TextField) qty).getText();
                if (!(temp.isEmpty())) {
                    data.setQty(temp);
                    if (Ref.DEBUG_ENABLED) {
                        System.out.println(temp + " TextField Qty - collectSteelDimData");
                    }
                }
            }

            if (data.getQty() != null) {
                steelDimListArray.add(data);
            } else if (Ref.DEBUG_ENABLED) {
                System.out.println("data.getQty().isEmpty() == true");
            }
        }

    }

    @FXML
    private void collectOtherData() {
        if (Ref.DEBUG_ENABLED) {
            System.out.println("Collecting Other Data...");
        }
        if ((poNumber.getText() != null) || (!(poNumber.getText().trim().isEmpty()))) {
            otherData.setPoNumber(poNumber.getText());
        } else if (Ref.DEBUG_ENABLED) {
            System.out.println("No PO Entered...");
            otherData.setPoNumber("n/a");
        }
        if (cName.getText() != null || (!(cName.getText().trim().isEmpty()))) {
            otherData.setCustName(cName.getText());
        } else if (Ref.DEBUG_ENABLED) {
            System.out.println("No Customer Name was Entered...");
        }
        if (jName.getText() != null || (!(jName.getText().trim().isEmpty()))) {
            otherData.setJobName(jName.getText());
        } else if (Ref.DEBUG_ENABLED) {
            System.out.println("No Job Name was Entered...");
        }
        if (!(loc.getValue() == null)) {
            otherData.setLocation(loc.getValue());
        } else if (Ref.DEBUG_ENABLED) {
            System.out.println("No Location was Selected...");
        }

    }
}
