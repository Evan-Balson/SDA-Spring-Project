module com.sad.stockshark {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.sad.stockshark to javafx.fxml;
    exports com.sad.stockshark;
    exports com.sad.stockshark.Controllers;
    opens com.sad.stockshark.Controllers to javafx.fxml;
}