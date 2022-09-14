package assi1.assignment1.View;

import assi1.assignment1.Controller.Database;
import assi1.assignment1.Model.Student;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Window_Edit{
    public static void DisplayEditMenu(Student student, Database database){
        Stage window = new Stage();

        //Name TextField
        TextField name_txtF = new TextField();
        name_txtF.setText(student.getName());
        Label name_lbl = new Label("Name:");
        name_lbl.setPrefWidth(100);
        HBox name_HBox = new HBox(30);
        name_HBox.getChildren().addAll(name_lbl, name_txtF);

        //ID TextField
        TextField id_txtF = new TextField();
        id_txtF.setText(student.getStudent_id());
        Label id_lbl = new Label("Student ID:");
        id_lbl.setPrefWidth(100);
        HBox id_HBox = new HBox(30);
        id_HBox.getChildren().addAll(id_lbl, id_txtF);

        //DOB DatePicker
        DatePicker dob_txtF = new DatePicker();
        dob_txtF.setValue(student.getDOB());
        dob_txtF.setEditable(false);
        Label dob_lbl = new Label("Date of Birth:");
        dob_lbl.setPrefWidth(100);
        HBox dob_HBox = new HBox(30);
        dob_HBox.getChildren().addAll(dob_lbl, dob_txtF);

        //Done Button
        Button done_btn = new Button("Done");
        done_btn.setOnAction(e -> {
            database.removeStudentDB(student.getStudent_id());

            student.setName(name_txtF.getText());
            student.setDOB(dob_txtF.getValue());
            student.setStudent_id(id_txtF.getText());

            database.addStudentDB(student.getName(),student.getStudent_id(),student.getDOB());
            window.close();
        });

        //Cancel Button
        Button cancel_btn = new Button("Cancel");
        cancel_btn.setOnAction(e -> window.close());

        HBox btnBox = new HBox();
        btnBox.getChildren().addAll(done_btn, cancel_btn);

        VBox vbox_edit = new VBox(10);
        vbox_edit.getChildren().addAll(name_HBox, id_HBox, dob_HBox, btnBox);
        Scene scene = new Scene(vbox_edit);
        window.setScene(scene);
        window.showAndWait();
    }
}
