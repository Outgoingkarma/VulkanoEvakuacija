module com.example.vulkanoevakuacija {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.vulkanoevakuacija to javafx.fxml;
    exports com.example.vulkanoevakuacija;
    exports com.example.vulkanoevakuacija.strategy;
    opens com.example.vulkanoevakuacija.strategy to javafx.fxml;
}