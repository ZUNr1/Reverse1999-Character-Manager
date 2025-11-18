module com.ZUNr1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;

    // 关键修复：导出 ui 包给 javafx.graphics
    exports com.ZUNr1.ui to javafx.graphics;

    // 为 FXML 和控制器打开包
    opens com.ZUNr1.ui to javafx.fxml;

    // 导出其他包
    exports com.ZUNr1.model;
    exports com.ZUNr1.enums;

    // 为 Jackson 序列化打开包
    opens com.ZUNr1.model to com.fasterxml.jackson.databind;
    opens com.ZUNr1.enums to com.fasterxml.jackson.databind;
    exports com.ZUNr1.ui.controller to javafx.graphics;
    opens com.ZUNr1.ui.controller to javafx.fxml;
}