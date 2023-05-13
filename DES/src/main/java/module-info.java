module com.example.des {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.des to javafx.fxml;
    exports com.example.des;
}