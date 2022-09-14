package assi1.assignment1.View;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import assi1.assignment1.Controller.*;
import assi1.assignment1.Model.*;

public class Tab_view {

    private static ObservableList<String> O_list;
    private static String listSelected;
    private static Student StudentSelected = null;
    private static int selectedIndex = 0;
    private static SortedList<Student> sortedList;

    static VBox DisplayViewTab(ListView<String> list, Database database){

        Label viewTitle_lbl = new Label("Viewer");
        Separator viewTitle_sep = new Separator();

        TextArea viewer_txtA = new TextArea();
        viewer_txtA.setEditable(false);

        Label error_lbl = new Label();

        Button view_btn = new Button("View");
        view_btn.setOnAction(e -> {
            if(list.getSelectionModel().getSelectedItems().isEmpty()){
                error_lbl.setText("Error, no Student selected");
            }else {
                O_list = list.getSelectionModel().getSelectedItems();
                listSelected = O_list.get(0);
                StudentSelected = database.getStudentDB(Controller.recoverStudentID(listSelected));
                viewer_txtA.setText(database.formatForView(StudentSelected, 0));
                error_lbl.setText("");
            }
        });

        Button viewResults_btn = new Button("View Results Over 70%");
        viewResults_btn.setOnAction(e -> {
            if(list.getSelectionModel().getSelectedItems().isEmpty()){
                error_lbl.setText("Error, no Student selected");
            }else {
                O_list = list.getSelectionModel().getSelectedItems();
                listSelected = O_list.get(0);
                StudentSelected = database.getStudentDB(Controller.recoverStudentID(listSelected));
                viewer_txtA.setText(database.formatForView(StudentSelected, 70));
                error_lbl.setText("");
            }
        });

        ComboBox<String> comboBox = new ComboBox<>();

        comboBox.getItems().add("Name");
        comboBox.getItems().add("ID Number");
        comboBox.getItems().add("Date of Birth");

        comboBox.setOnAction(e -> {
            selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
        });

        Button sort_btn = new Button("Sort By");
        sort_btn.setOnAction(e -> {
            if(selectedIndex == comboBox.getItems().indexOf("Name")){
                database.Sorted_RefreshDB(list, 0);
            }else if(selectedIndex == comboBox.getItems().indexOf("ID Number")){
                database.Sorted_RefreshDB(list, 1);
            }else if(selectedIndex == comboBox.getItems().indexOf("Date of Birth")) {
                database.Sorted_RefreshDB(list, 2);
            }
        });


        HBox button_HBox = new HBox(10);
        button_HBox.getChildren().addAll(view_btn, sort_btn, comboBox, viewResults_btn, error_lbl);

        VBox vbox_view = new VBox(10);
        vbox_view.getChildren().addAll(viewTitle_lbl,viewTitle_sep,viewer_txtA,button_HBox);
        vbox_view.setPadding(new Insets(10));
        return vbox_view;
    }
}
