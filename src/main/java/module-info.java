module com.example.vulkanoevakuacija {
    requires javafx.controls;
    requires javafx.fxml;




    exports com.example.vulkanoevakuacija.strategy;
    opens com.example.vulkanoevakuacija.strategy to javafx.fxml;
    exports com.example.vulkanoevakuacija.Game;
    opens com.example.vulkanoevakuacija.Game to javafx.fxml;
}