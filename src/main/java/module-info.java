module assi1.assignment1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.junit.jupiter.params;

    opens assi1.assignment1.Model to javafx.fxml;
    opens assi1.assignment1.View to javafx.fxml;
    opens assi1.assignment1.Controller to javafx.fxml;

    //opens assi1.assignment1 to javafx.fxml;

    exports assi1.assignment1.View;
}