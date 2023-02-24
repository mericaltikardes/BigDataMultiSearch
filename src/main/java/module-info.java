module com.mericaltikardes.bigdatamultisearch {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens com.mericaltikardes.bigdatamultisearch to javafx.fxml;
    exports com.mericaltikardes.bigdatamultisearch;
    exports com.mericaltikardes.bigdatamultisearch.data;
    exports com.mericaltikardes.bigdatamultisearch.controller;
    opens com.mericaltikardes.bigdatamultisearch.controller to javafx.fxml;

}