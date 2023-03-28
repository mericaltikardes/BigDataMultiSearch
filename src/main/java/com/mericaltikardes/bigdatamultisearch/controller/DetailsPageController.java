package com.mericaltikardes.bigdatamultisearch.controller;

import com.mericaltikardes.bigdatamultisearch.data.CompareDatas;
import com.mericaltikardes.bigdatamultisearch.data.Information;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Yenisayfa Controller
public class DetailsPageController implements Initializable {
    @FXML
    public TableView<CompareDatas> tableView;
    public TableColumn colBenzerlik;
    public TableColumn colProduct2;
    public TableColumn colProduct1;
    public static ObservableList<CompareDatas> compareData = FXCollections.observableArrayList();
    public static ObservableList<Information> equalsData = FXCollections.observableArrayList();
    @FXML
    private TextField totalTime;
    @FXML
    private TextField threadCounter;
    private static double time;
    private static double thread;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setItems(compareData);
        totalTime.setText("Geçen süre:" + time + " Saniye");
        threadCounter.setText(" Thread Sayisi:" + (int) thread);
        colBenzerlik.setCellValueFactory(new PropertyValueFactory<>("Product1"));
        colProduct1.setCellValueFactory(new PropertyValueFactory<>("benzerlik"));
        colProduct2.setCellValueFactory(new PropertyValueFactory<>("Product2"));
        compareData = FXCollections.observableArrayList();
    }


    public static HashSet distinctinColumnProduct(ObservableList<Information> infs) {
        ArrayList<String> arraylistForProductName = new ArrayList<>();
        for (int i = 0; i < infs.size(); i++) {
            arraylistForProductName.add(infs.get(i).getProduct());
        }
        HashSet<String> hsetProductName = new HashSet<>(arraylistForProductName);
        return hsetProductName;
    }

    public static HashSet distinctinColumnIssue(ObservableList<Information> infs) {
        ArrayList<String> arraylistForIssueName = new ArrayList<>();
        for (int i = 0; i < infs.size(); i++) {
            arraylistForIssueName.add(infs.get(i).getIssue());
        }
        HashSet<String> hsetProductName = new HashSet<>(arraylistForIssueName);
        return hsetProductName;
    }

    public static HashSet distinctinColumnState(ObservableList<Information> infs) {
        ArrayList<String> arraylistForStateName = new ArrayList<>();
        for (int i = 0; i < infs.size(); i++) {
            arraylistForStateName.add(infs.get(i).getState());
        }
        HashSet<String> hsetStateName = new HashSet<>(arraylistForStateName);
        return hsetStateName;
    }

    public static HashSet distinctinColumnCompany(ObservableList<Information> infs) {
        ArrayList<String> arraylistForCompanyName = new ArrayList<>();
        for (int i = 0; i < infs.size(); i++) {
            arraylistForCompanyName.add(infs.get(i).getCompany());
        }
        HashSet<String> hsetCompanyName = new HashSet<>(arraylistForCompanyName);
        return hsetCompanyName;
    }

    public static HashSet distinctinColumnZipCode(ObservableList<Information> infs) {
        ArrayList<String> arraylistForZipCodeName = new ArrayList<>();
        for (int i = 0; i < infs.size(); i++) {
            arraylistForZipCodeName.add(infs.get(i).getZipCode());
        }
        HashSet<String> hsetZipCodeName = new HashSet<>(arraylistForZipCodeName);
        return hsetZipCodeName;
    }

    public static HashSet distinctinColumnComplaintId(ObservableList<Information> infs) {
        ArrayList<String> arraylistForComplaintIDName = new ArrayList<>();
        for (int i = 0; i < infs.size(); i++) {
            arraylistForComplaintIDName.add(infs.get(i).getComplaintId());
        }
        HashSet<String> hsetComplaintId = new HashSet<>(arraylistForComplaintIDName);
        return hsetComplaintId;
    }

    public static ObservableList<Information> benzerleriBulProduct(ObservableList<Information> inf, ObservableList<String> arrForProductNameDistinct,
                                                                   int BenzerlikOrani, int threadSayisi) {
        thread = threadSayisi;
        long startTime = System.nanoTime();
        ObservableList<Information> geciciList = FXCollections.observableArrayList();
        ExecutorService executorService = Executors.newFixedThreadPool(threadSayisi);
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                int counter = 0;
                int benzerlik = 0;
                //Productun ilk değeri için
                for (int i = 0; i < arrForProductNameDistinct.size(); i++) {
                    String[] gecici1 = (arrForProductNameDistinct.get(i).split(" "));
                    for (int j = 0; j < inf.size(); j++) {
                        //getproduct alınmış
                        String[] gecici2 = (inf.get(j).getProduct().split(" "));
                        int max = Math.max(gecici1.length, gecici2.length);
                        int min = Math.min(gecici1.length, gecici2.length);
                        for (int k = 0; k < max; k++) {
                            for (int l = 0; l < min; l++) {
                                if (gecici1.length >= gecici2.length) {
                                    if (gecici1[k].equals(gecici2[l])) {
                                        counter++;
                                    }
                                } else if (gecici2[k].equals(gecici1[l])) {
                                    counter++;
                                }
                            }
                        }
                        if (counter >= max) counter = max;
                        benzerlik = (int) (((float) counter / (float) max) * 100);
                        if (benzerlik >= BenzerlikOrani) {
                            // System.out.println(benzerlik + " " + arrForProductNameDistinct.get(i) + " " + inf.get(j).getProduct());
                            CompareDatas record = new CompareDatas(arrForProductNameDistinct.get(i), inf.get(j).getProduct(), benzerlik);
                            compareData.add(record);
                            Information rec = new Information(inf.get(j).getProduct(), inf.get(j).getIssue(), inf.get(j).getCompany(), inf.get(j).getState(), inf.get(j).getZipCode(), inf.get(j).getComplaintId());
                            System.out.println(rec);
                            geciciList.add(rec);
                        }
                        counter = 0;
                    }
                    System.out.println(geciciList);
                }
            }
        });

        try {
            // işlem tamamlanana kadar bekle
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime); // nanosaniye cinsinden süre
        time = (double) duration / 1_000_000_000.0; // saniye cinsinden süre
        String fxmlPath = "/com/mericaltikardes/bigdatamultisearch/second-page.fxml";
        File file = new File(fxmlPath);
        FXMLLoader loader = new FXMLLoader(DetailsPageController.class.getResource(fxmlPath));
        Parent rootNode;
        try {

            rootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.showAndWait();

        return geciciList;


    }

    public static ObservableList<Information> benzerleriBulIssue(ObservableList<Information> inf,
                                                                 ObservableList<String> arrForIssueNameDistinct,
                                                                 int BenzerlikOrani, int threadSayisi) {
        thread = threadSayisi;
        long startTime = System.nanoTime();
        String temp;
        ObservableList<Information> geciciList = FXCollections.observableArrayList();
        ExecutorService executorService = Executors.newFixedThreadPool(threadSayisi);
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                int counter = 0;
                float benzerlik = 0;
                for (int i = 0; i < arrForIssueNameDistinct.size(); i++) {
                    String[] gecici1 = (arrForIssueNameDistinct.get(i).split(" "));
                    for (int j = 0; j < inf.size(); j++) {
                        String[] gecici2 = (inf.get(j).getIssue().split(" "));
                        int max = Math.max(gecici1.length, gecici2.length);
                        int min = Math.min(gecici1.length, gecici2.length);
                        for (int k = 0; k < max; k++) {
                            for (int l = 0; l < min; l++) {
                                if (gecici1.length >= gecici2.length) {
                                    if (gecici1[k].equals(gecici2[l])) {
                                        counter++;
                                    }
                                } else if (gecici2[k].equals(gecici1[l])) {
                                    counter++;
                                }
                            }
                        }
                        if (counter >= max) counter = max;
                        benzerlik = ((float) counter / (float) max) * 100;
                        if (benzerlik >= BenzerlikOrani) {
                            System.out.println(benzerlik + " " + arrForIssueNameDistinct.get(i) + " " + inf.get(j).getIssue());
                            CompareDatas record1 = new CompareDatas(arrForIssueNameDistinct.get(i), inf.get(j).getIssue(), (int) benzerlik);
                            compareData.add(record1);
                        }
                        counter = 0;
                    }
                }
            }
        });
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        time = (double) duration / 1_000_000_000.0;
        String fxmlPath = "/com/mericaltikardes/bigdatamultisearch/second-page.fxml";
        File file = new File(fxmlPath);
        FXMLLoader loader = new FXMLLoader(DetailsPageController.class.getResource(fxmlPath));
        Parent rootNode;
        try {

            rootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.showAndWait();
        return geciciList;
    }

    public static ObservableList<Information> benzerleriBulCompany(ObservableList<Information> inf,
                                                                   ObservableList<String> arrForCompanyNameDistinct,
                                                                   int BenzerlikOrani, int threadSayisi) {
        thread = threadSayisi;
        long startTime = System.nanoTime();
        ObservableList<Information> geciciList = FXCollections.observableArrayList();
        ExecutorService executorService = Executors.newFixedThreadPool(threadSayisi);
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                int counter = 0;
                float benzerlik = 0;
                for (int i = 0; i < arrForCompanyNameDistinct.size(); i++) {
                    String[] gecici1 = (arrForCompanyNameDistinct.get(i).split(" "));
                    for (int j = 0; j < inf.size(); j++) {
                        String[] gecici2 = (inf.get(j).getCompany().split(" "));
                        int max = Math.max(gecici1.length, gecici2.length);
                        int min = Math.min(gecici1.length, gecici2.length);
                        for (int k = 0; k < max; k++) {
                            for (int l = 0; l < min; l++) {
                                if (gecici1.length >= gecici2.length) {
                                    if (gecici1[k].equals(gecici2[l])) {
                                        counter++;
                                    }
                                } else if (gecici2[k].equals(gecici1[l])) {
                                    counter++;
                                }
                            }
                        }
                        if (counter >= max) counter = max;
                        benzerlik = ((float) counter / (float) max) * 100;
                        if (benzerlik >= BenzerlikOrani) {
                            System.out.println(benzerlik + " " + arrForCompanyNameDistinct.get(i) + " " + inf.get(j).getCompany());
                            CompareDatas record1 = new CompareDatas(arrForCompanyNameDistinct.get(i), inf.get(j).getCompany(), (int) (benzerlik));
                            compareData.add(record1);
                        }
                        counter = 0;
                    }
                }
            }
        });
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        time = (double) duration / 1_000_000_000.0;
        String fxmlPath = "/com/mericaltikardes/bigdatamultisearch/second-page.fxml";
        File file = new File(fxmlPath);
        FXMLLoader loader = new FXMLLoader(DetailsPageController.class.getResource(fxmlPath));
        Parent rootNode;

        try {

            rootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.showAndWait();

        return geciciList;


    }

    public static ObservableList<Information> benzerleriBulState(ObservableList<Information> inf, ObservableList<String> arrForStateNameDistinct, int BenzerlikOrani, int threadSayisi) {
        thread = threadSayisi;
        long startTime = System.nanoTime();
        ObservableList<Information> geciciList = FXCollections.observableArrayList();
        ExecutorService executorService = Executors.newFixedThreadPool(threadSayisi);
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                int counter = 0;
                float benzerlik = 0;
                for (int i = 0; i < arrForStateNameDistinct.size(); i++) {
                    String[] gecici1 = (arrForStateNameDistinct.get(i).split(" "));
                    for (int j = 0; j < inf.size(); j++) {
                        String[] gecici2 = (inf.get(j).getState().split(" "));
                        int max = Math.max(gecici1.length, gecici2.length);
                        int min = Math.min(gecici1.length, gecici2.length);
                        if (gecici1[0] != "") {
                            for (int k = 0; k < max; k++) {
                                for (int l = 0; l < min; l++) {
                                    if (gecici1.length >= gecici2.length) {
                                        if (gecici1[k].equals(gecici2[l])) {
                                            counter++;
                                        }
                                    } else if (gecici2[k].equals(gecici1[l])) {

                                        counter++;
                                    }

                                }
                            }
                            if (counter >= max) counter = max;
                            benzerlik = ((float) counter / (float) max) * 100;
                            if (benzerlik >= BenzerlikOrani) {
                                CompareDatas record1 = new CompareDatas(arrForStateNameDistinct.get(i), inf.get(j).getState(), (int) benzerlik);
                                compareData.add(record1);
                            }
                            counter = 0;
                        }
                    }
                }
            }
        });
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        time = (double) duration / 1_000_000_000.0;
        String fxmlPath = "/com/mericaltikardes/bigdatamultisearch/second-page.fxml";
        File file = new File(fxmlPath);
        FXMLLoader loader = new FXMLLoader(DetailsPageController.class.getResource(fxmlPath));
        Parent rootNode;
        try {

            rootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.showAndWait();

        return geciciList;

    }

    public static ObservableList<Information> benzerleriBulZipCode(ObservableList<Information> inf, ObservableList<String> arrForZipCodeNameDistinct, int BenzerlikOrani, int threadSayisi) {
        thread = threadSayisi;
        long startTime = System.nanoTime();
        ObservableList<Information> geciciList = FXCollections.observableArrayList();
        ExecutorService executorService = Executors.newFixedThreadPool(threadSayisi);
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                int counter = 0;
                float benzerlik = 0;
                for (int i = 0; i < arrForZipCodeNameDistinct.size(); i++) {
                    String[] gecici1 = (arrForZipCodeNameDistinct.get(i).split(" "));
                    for (int j = 0; j < inf.size(); j++) {
                        String[] gecici2 = (inf.get(j).getZipCode().split(" "));
                        int max = Math.max(gecici1.length, gecici2.length);
                        int min = Math.min(gecici1.length, gecici2.length);
                        for (int k = 0; k < max; k++) {
                            for (int l = 0; l < min; l++) {
                                if (gecici1.length >= gecici2.length) {
                                    if (gecici1[k].equals(gecici2[l])) {
                                        counter++;
                                    }
                                } else if (gecici2[k].equals(gecici1[l])) {
                                    counter++;
                                }
                            }
                        }
                        if (counter >= max) counter = max;
                        benzerlik = ((float) counter / (float) max) * 100;
                        if (benzerlik >= BenzerlikOrani) {
                            System.out.println(benzerlik + " " + arrForZipCodeNameDistinct.get(i) + " " + inf.get(j).getZipCode());
                            CompareDatas record1 = new CompareDatas(arrForZipCodeNameDistinct.get(i), inf.get(j).getZipCode(), (int) benzerlik);
                            compareData.add(record1);
                        }
                        counter = 0;
                    }
                }
            }
        });
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        time = (double) duration / 1_000_000_000.0;
        String fxmlPath = "/com/mericaltikardes/bigdatamultisearch/second-page.fxml";
        File file = new File(fxmlPath);
        FXMLLoader loader = new FXMLLoader(DetailsPageController.class.getResource(fxmlPath));
        Parent rootNode;
        try {

            rootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.showAndWait();
        return geciciList;
    }

    public static ObservableList<Information> benzerleriBulComplaintId(ObservableList<Information> inf, ObservableList<String> arrForComplaintIdNameDistinct, int BenzerlikOrani, int threadSayisi) {
        thread = threadSayisi;
        long startTime = System.nanoTime();
        ObservableList<Information> geciciList = FXCollections.observableArrayList();
        ExecutorService executorService = Executors.newFixedThreadPool(threadSayisi);
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                int counter = 0;
                float benzerlik = 0;
                for (int i = 0; i < arrForComplaintIdNameDistinct.size(); i++) {
                    String[] gecici1 = (arrForComplaintIdNameDistinct.get(i).split(" "));
                    for (int j = 0; j < inf.size(); j++) {
                        String[] gecici2 = (inf.get(j).getComplaintId().split(" "));
                        int max = Math.max(gecici1.length, gecici2.length);
                        int min = Math.min(gecici1.length, gecici2.length);
                        for (int k = 0; k < max; k++) {
                            for (int l = 0; l < min; l++) {
                                if (gecici1.length >= gecici2.length) {
                                    if (gecici1[k].equals(gecici2[l])) {
                                        counter++;
                                    }
                                } else if (gecici2[k].equals(gecici1[l])) {
                                    counter++;
                                }
                            }
                        }
                        if (counter >= max) counter = max;
                        benzerlik = ((float) counter / (float) max) * 100;
                        if (benzerlik >= BenzerlikOrani) {
                            System.out.println(benzerlik + " " + arrForComplaintIdNameDistinct.get(i) + " " + inf.get(j).getComplaintId());
                            CompareDatas record1 = new CompareDatas(arrForComplaintIdNameDistinct.get(i), inf.get(j).getComplaintId(), (int) (benzerlik));
                            compareData.add(record1);
                        }
                        counter = 0;
                        System.out.println(geciciList);
                    }
                }
            }
        });
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        time = (double) duration / 1_000_000_000.0;
        String fxmlPath = "/com/mericaltikardes/bigdatamultisearch/second-page.fxml";
        File file = new File(fxmlPath);
        FXMLLoader loader = new FXMLLoader(DetailsPageController.class.getResource(fxmlPath));
        Parent rootNode;
        try {

            rootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.showAndWait();

        return geciciList;

    }

    public static void multiSearch(ObservableList<Information> inf, String arananColumnIndeksi, String arananDeger,
                                   int benzerlikOrani, String aramaYapilmasiGrekenKolon, String yazilmasiIstenenKolon, int threadSayisi) {
        thread = threadSayisi;
        long startTime = System.nanoTime();
        ExecutorService executorService = Executors.newFixedThreadPool(threadSayisi);
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                if (arananColumnIndeksi.equals("Product")) {
                    for (int i = 0; i < inf.size(); i++) {
                        if (inf.get(i).getProduct().equals(arananDeger)) {
                            equalsData.add(inf.get(i));
                        }
                    }
                    aramaYapilmasiGereken(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);
                }
                if (arananColumnIndeksi.equals("Issue")) {
                    for (int i = 0; i < inf.size(); i++) {
                        if (inf.get(i).getIssue().equals(arananDeger)) {
                            equalsData.add(inf.get(i));
                        }
                    }
                    aramaYapilmasiGereken(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);
                }
                if (arananColumnIndeksi.equals("Company")) {
                    for (int i = 0; i < inf.size(); i++) {
                        if (inf.get(i).getCompany().equals(arananDeger)) {
                            equalsData.add(inf.get(i));
                        }
                    }
                    aramaYapilmasiGereken(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);
                }
                if (arananColumnIndeksi.equals("State")) {
                    for (int i = 0; i < inf.size(); i++) {
                        if (inf.get(i).getState().equals(arananDeger)) {
                            equalsData.add(inf.get(i));

                        }
                    }
                    aramaYapilmasiGereken(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);
                }
                if (arananColumnIndeksi.equals("ZipCode")) {
                    for (int i = 0; i < inf.size(); i++) {
                        if (inf.get(i).getZipCode().equals(arananDeger)) {
                            equalsData.add(inf.get(i));
                        }
                    }
                    aramaYapilmasiGereken(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);
                }
                if (arananColumnIndeksi.equals("ComplaintId")) {
                    for (int i = 0; i < inf.size(); i++) {
                        if (inf.get(i).getComplaintId().equals(arananDeger)) {
                            equalsData.add(inf.get(i));
                        }
                    }
                    aramaYapilmasiGereken(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);
                }
            }
        });
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        time = (double) duration / 1_000_000_000.0;
        String fxmlPath = "/com/mericaltikardes/bigdatamultisearch/second-page.fxml";
        File file = new File(fxmlPath);
        FXMLLoader loader = new FXMLLoader(DetailsPageController.class.getResource(fxmlPath));
        Parent rootNode;
        try {

            rootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.showAndWait();


    }

    public static void aramaYapilmasiGereken(ObservableList<Information> inf, String arananColumnIndeksi, String arananDeger, int benzerlikOrani, String aramaYapilmasiGrekenKolon, String yazilmasiIstenenKolon) {
        if (aramaYapilmasiGrekenKolon.equals("Product")) {
            HashSet productDistinct = new HashSet();
            productDistinct = distinctinColumnProduct(equalsData);
            ObservableList<String> arrForProductDistinct = FXCollections.observableArrayList(productDistinct);
            System.out.println(arrForProductDistinct);
            yazilmasiIstenen(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);

        } else if (aramaYapilmasiGrekenKolon.equals("Issue")) {
            HashSet issueDistinct = new HashSet();
            issueDistinct = distinctinColumnIssue(equalsData);
            ObservableList<String> arrForIssueDistinct = FXCollections.observableArrayList(issueDistinct);
            System.out.println(arrForIssueDistinct);
            yazilmasiIstenen(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);

        } else if (aramaYapilmasiGrekenKolon.equals("Company")) {
            HashSet companyDistinct = new HashSet();
            companyDistinct = distinctinColumnCompany(equalsData);
            ObservableList<String> arrForCompanyDistinct = FXCollections.observableArrayList(companyDistinct);
            System.out.println(arrForCompanyDistinct);
            yazilmasiIstenen(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);

        } else if (aramaYapilmasiGrekenKolon.equals("State")) {
            HashSet stateDistinct = new HashSet();
            stateDistinct = distinctinColumnState(equalsData);
            ObservableList<String> arrForStateDistinct = FXCollections.observableArrayList(stateDistinct);
            System.out.println(arrForStateDistinct);
            yazilmasiIstenen(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);

        } else if (aramaYapilmasiGrekenKolon.equals("ZipCode")) {
            HashSet zipCodeDistinct = new HashSet();
            zipCodeDistinct = distinctinColumnZipCode(equalsData);
            ObservableList<String> arrForZipCodeDistinct = FXCollections.observableArrayList(zipCodeDistinct);
            System.out.println(arrForZipCodeDistinct);
            yazilmasiIstenen(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);
        } else if (aramaYapilmasiGrekenKolon.equals("ComplaintId")) {
            HashSet complaintIdDistinct = new HashSet();
            complaintIdDistinct = distinctinColumnComplaintId(equalsData);
            ObservableList<String> arrForComplaintIdDistinct = FXCollections.observableArrayList(complaintIdDistinct);
            System.out.println(arrForComplaintIdDistinct);
            yazilmasiIstenen(inf, arananColumnIndeksi, arananDeger, benzerlikOrani, aramaYapilmasiGrekenKolon, yazilmasiIstenenKolon);
        }
    }

    public static void yazilmasiIstenen(ObservableList<Information> inf, String arananColumnIndeksi, String arananDeger, int benzerlikOrani, String aramaYapilmasiGrekenKolon, String yazilmasiIstenenKolon) {
        int size = equalsData.size();
        if (yazilmasiIstenenKolon.equals("Product")) {
            for (int i = 0; i < size; i++) {
                CompareDatas record = new CompareDatas(arananDeger, equalsData.get(i).getProduct(), benzerlikOrani);
                compareData.add(record);
                System.out.println(compareData);
            }
        }
        if (yazilmasiIstenenKolon.equals("Issue")) {
            for (int i = 0; i < size; i++) {
                CompareDatas record = new CompareDatas(arananDeger, equalsData.get(i).getIssue(), benzerlikOrani);
                compareData.add(record);
                System.out.println(compareData);
            }
        }
        if (yazilmasiIstenenKolon.equals("Company")) {
            for (int i = 0; i < size; i++) {
                CompareDatas record = new CompareDatas(arananDeger, equalsData.get(i).getCompany(), benzerlikOrani);
                compareData.add(record);
                System.out.println(compareData);
            }
        }
        if (yazilmasiIstenenKolon.equals("State")) {
            for (int i = 0; i < size; i++) {
                CompareDatas record = new CompareDatas(arananDeger, equalsData.get(i).getState(), benzerlikOrani);
                compareData.add(record);
                System.out.println(compareData);
            }
        }
        if (yazilmasiIstenenKolon.equals("ZipCode")) {
            for (int i = 0; i < size; i++) {
                CompareDatas record = new CompareDatas(arananDeger, equalsData.get(i).getZipCode(), benzerlikOrani);
                compareData.add(record);
                System.out.println(compareData);
            }
        }
        if (yazilmasiIstenenKolon.equals("ComplaintId")) {
            for (int i = 0; i < size; i++) {
                CompareDatas record = new CompareDatas(arananDeger, equalsData.get(i).getComplaintId(), benzerlikOrani);
                compareData.add(record);
                System.out.println(compareData);
            }
        }
    }
}









