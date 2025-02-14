module com.sad.stockshark {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sad.stockshark to javafx.fxml;
    exports com.sad.stockshark;
}