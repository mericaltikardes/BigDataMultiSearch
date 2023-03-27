package com.mericaltikardes.bigdatamultisearch;

import com.jfoenix.controls.JFXButton;
import com.mericaltikardes.bigdatamultisearch.controller.DetailsPageController;
import com.mericaltikardes.bigdatamultisearch.data.Information;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class FirstPageController implements Initializable {

    public TableView<Information> tableView;
    public TableColumn<Information, String> colProduct;
    public TableColumn<Information, String> colIssue;
    public TableColumn<Information, String> colCompany;
    public TableColumn<Information, String> colState;
    public TableColumn<Information, String> colZipCode;
    public TableColumn<Information, String> colComplaintId;
    @FXML
    private ListView<String> liste2;
    @FXML
    private TextField txtBenzerlikOraniSingle;
    @FXML
    private ListView<String> liste3;
    @FXML
    private TextField txtBenzerlikOraniMulti;
    @FXML
    private TextField txtEsitlikMulti;
    @FXML
    private ListView<String> liste4;



    @FXML
    public ListView<String> liste;


    @FXML
    private TextField txtThreadSayisiSingle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readCSV();
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colIssue.setCellValueFactory(new PropertyValueFactory<>("issue"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colState.setCellValueFactory(new PropertyValueFactory<>("state"));
        colZipCode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        colComplaintId.setCellValueFactory(new PropertyValueFactory<>("complaintId"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                tableView.setItems(dataList);
                liste.getItems().addAll("Product", "Issue", "Company", "State", "ZipCode", "ComplaintId");
                liste2.getItems().addAll("Product", "Issue", "Company", "State", "ZipCode", "ComplaintId");
                liste3.getItems().addAll("Product", "Issue", "Company", "State", "ZipCode", "ComplaintId");
                liste4.getItems().addAll("Product", "Issue", "Company", "State", "ZipCode", "ComplaintId");

            }
        }).start();
//        tableView.setItems(dataList);
//        liste.getItems().addAll("Product", "Issue", "Company", "State", "ZipCode", "ComplaintId");
//        liste2.getItems().addAll("Product", "Issue", "Company", "State", "ZipCode", "ComplaintId");
//        liste3.getItems().addAll("Product", "Issue", "Company", "State", "ZipCode", "ComplaintId");
//        liste4.getItems().addAll("Product", "Issue", "Company", "State", "ZipCode", "ComplaintId");

    }

    @FXML
    private JFXButton btnBenzerlikThread;
    private Parent rootNode;

    @FXML
    void btnGiris(ActionEvent event) throws IOException {
        int singleThreadExecuted = Integer.parseInt(txtThreadSayisiSingle.getText());
        if (liste.getSelectionModel().getSelectedItems().get(0).equals(liste.getItems().get(0))) {
            System.out.println(Integer.parseInt(txtBenzerlikOraniSingle.getText()));
            DetailsPageController.distinctinColumnProduct(dataList);
            HashSet productDistinct = new HashSet();
            productDistinct = DetailsPageController.distinctinColumnProduct(dataList);
            ObservableList<String> arrForProductNameDistinct = FXCollections.observableArrayList(productDistinct);
            DetailsPageController.benzerleriBulProduct(dataList, arrForProductNameDistinct, Integer.parseInt(txtBenzerlikOraniSingle.getText()), singleThreadExecuted);

        } else if (liste.getSelectionModel().getSelectedItems().get(0).equals(liste.getItems().get(1))) {
            HashSet issueDistinct = new HashSet();
            issueDistinct = DetailsPageController.distinctinColumnIssue(dataList);
            ObservableList<String> arrForIssueNameDistinct = FXCollections.observableArrayList(issueDistinct);
            DetailsPageController.benzerleriBulIssue(dataList, arrForIssueNameDistinct, Integer.parseInt(txtBenzerlikOraniSingle.getText()), singleThreadExecuted);
        } else if (liste.getSelectionModel().getSelectedItems().get(0).equals(liste.getItems().get(2))) {
            HashSet companyDistinct = new HashSet();
            companyDistinct = DetailsPageController.distinctinColumnCompany(dataList);
            ObservableList<String> arrForCompanyNameDistinct = FXCollections.observableArrayList(companyDistinct);
            DetailsPageController.benzerleriBulIssue(dataList, arrForCompanyNameDistinct, Integer.parseInt(txtBenzerlikOraniSingle.getText()), singleThreadExecuted);
        } else if (liste.getSelectionModel().getSelectedItems().get(0).equals(liste.getItems().get(3))) {
            HashSet stateDistinct = new HashSet();
            stateDistinct = DetailsPageController.distinctinColumnState(dataList);
            ObservableList<String> arrForCompanyNameDistinct = FXCollections.observableArrayList(stateDistinct);
            DetailsPageController.benzerleriBulState(dataList, arrForCompanyNameDistinct, Integer.parseInt(txtBenzerlikOraniSingle.getText()), singleThreadExecuted);
        } else if (liste.getSelectionModel().getSelectedItems().get(0).equals(liste.getItems().get(4))) {
            HashSet zipCodeDistinct = new HashSet();
            zipCodeDistinct = DetailsPageController.distinctinColumnZipCode(dataList);
            ObservableList<String> arrForZipCodeNameDistinct = FXCollections.observableArrayList(zipCodeDistinct);
            DetailsPageController.benzerleriBulZipCode(dataList, arrForZipCodeNameDistinct, Integer.parseInt(txtBenzerlikOraniSingle.getText()), singleThreadExecuted);
        } else if (liste.getSelectionModel().getSelectedItems().get(0).equals(liste.getItems().get(5))) {
            HashSet complaintIdDistinct = new HashSet();
            complaintIdDistinct = DetailsPageController.distinctinColumnComplaintId(dataList);
            ObservableList<String> arrForComplaintIdNameDistinct = FXCollections.observableArrayList(complaintIdDistinct);
            DetailsPageController.benzerleriBulComplaintId(dataList, arrForComplaintIdNameDistinct, Integer.parseInt(txtBenzerlikOraniSingle.getText()), singleThreadExecuted);
        }

    }


    public void btnGirisMulti(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("second-page.fxml"));
        DetailsPageController yeniSayfaController = loader.getController();
        HashSet companyDistinct = new HashSet();
        companyDistinct = DetailsPageController.distinctinColumnCompany(dataList);
        ObservableList<String> arrForCompanyNameDistinct = FXCollections.observableArrayList(companyDistinct);
        DetailsPageController.multiSearch(dataList, liste2.getSelectionModel().getSelectedItems().get(0),
                txtEsitlikMulti.getText(), Integer.parseInt(txtBenzerlikOraniMulti.getText()),
                liste3.getSelectionModel().getSelectedItems().get(0),
                liste4.getSelectionModel().getSelectedItems().get(0));
        rootNode = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.showAndWait();

    }

    public final ObservableList<Information> dataList
            = FXCollections.observableArrayList();

    private void readCSV() {


        String CsvFile = "C:\\Users\\Meriç\\Masaüstü\\kiyas.csv";
        String FieldDelimiter = ";";

        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(CsvFile));

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(FieldDelimiter, -1);

                Information record = new Information(fields[0], fields[1], fields[2],
                        fields[3], fields[4], fields[5]);
                dataList.add(record);

            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }


}