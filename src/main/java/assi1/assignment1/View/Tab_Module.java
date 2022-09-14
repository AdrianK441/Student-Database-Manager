package assi1.assignment1.View;


import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.ObservableList;

import assi1.assignment1.Controller.*;
import assi1.assignment1.Model.*;

import java.util.ArrayList;

public class Tab_Module {

    private static ObservableList<String> O_list;
    private static ObservableList<String> module_list;
    private static ArrayList<Modules> modulesArrayList;
    private static String listSelected;
    private static String moduleSelected;
    private static Student StudentSelected = null;

    static VBox DisplayModuleTab(ListView<String> list, Database database){

        //error label
        Label error_lbl = new Label();

        //right vbox
        ListView<String> rightList = new ListView<>();

        //Module title
        Label moduleTitle_lbl = new Label("Module Registration");
        Separator moduleTitle_sep = new Separator();

        //Module ID ComboBox
        Label moduleID_lbl = new Label("Module ID: ");
        moduleID_lbl.setPrefWidth(100);
        ComboBox<String> moduleID_CB = new ComboBox<String>();
        moduleID_CB.setEditable(true);

        HBox moduleID_HBox = new HBox(30);
        moduleID_HBox.getChildren().addAll(moduleID_lbl,moduleID_CB);

        //Module name TextField
        Label moduleName_lbl = new Label("Module Name: ");
        moduleName_lbl.setPrefWidth(100);
        TextField moduleName_txtF = new TextField();
        moduleName_txtF.setPrefWidth(120);

        HBox moduleName_HBox = new HBox(30);
        moduleName_HBox.getChildren().addAll(moduleName_lbl, moduleName_txtF);

        //Module Grade TextField
        Label moduleGrade_lbl = new Label("Students Grade: ");
        moduleGrade_lbl.setPrefWidth(100);
        TextField moduleGrade_txtF = new TextField();
        moduleGrade_txtF.setPrefWidth(120);

        HBox moduleGrade_HBox = new HBox(30);
        moduleGrade_HBox.getChildren().addAll(moduleGrade_lbl, moduleGrade_txtF);

        modulesArrayList = Controller.populateModuleCB(moduleID_CB);

        //ComboBox Functionality
        moduleID_CB.setOnAction(e ->{
            for (Modules modules : modulesArrayList) {
                if (moduleID_CB.getValue().equals(modules.getModule_id())) {
                    moduleID_CB.setValue(modules.getModule_id());
                    moduleName_txtF.setText(modules.getModule_name());
                }
            }
        });

        //add Module Button
        Button addModule_btn = new Button("Add module");
        addModule_btn.setMinWidth(100);
        addModule_btn.setOnAction(e -> {
            try{
                database.addModuleDB(StudentSelected, moduleID_CB.getValue(), moduleName_txtF.getText(), Integer.parseInt(moduleGrade_txtF.getText()));
                database.refreshModulesDB(rightList, StudentSelected);
                error_lbl.setText("");
                moduleID_CB.setValue("");
                moduleName_txtF.setText("");
                moduleGrade_txtF.setText("");
                modulesArrayList = Controller.populateModuleCB(moduleID_CB);
            }catch(Exception r){
                if(moduleGrade_txtF.getText().equals("") || moduleName_txtF.getText().equals("")){
                    error_lbl.setText("Please fill out the forms!");
                }else{
                    error_lbl.setText("Please select a student");
                }
            }
        });

        //remove button
        Button removeModule_btn = new Button("Remove Module");
        removeModule_btn.setMinWidth(100);
        removeModule_btn.setOnAction(e -> {
            try{
                module_list = rightList.getSelectionModel().getSelectedItems();
                moduleSelected = module_list.get(0);
                System.out.println(moduleSelected);
                database.removeModuleDB(Controller.recoverModuleID(moduleSelected), StudentSelected);
                error_lbl.setText("");
                database.refreshModulesDB(rightList, StudentSelected);
            }catch(Exception r){
                error_lbl.setText("Please select a Module");
            }
        });

        //Top Button HBox
        HBox Top_Button_HBox = new HBox();
        Top_Button_HBox.getChildren().addAll(addModule_btn, removeModule_btn);

        //Region
        Region region = new Region();
        region.setMinHeight(100);

        TextArea right_txtF = new TextArea();
        right_txtF.setEditable(false);
        right_txtF.setMinHeight(42);
        right_txtF.setMaxHeight(42);

        //Select student button
        Button SelectStudent_btn = new Button("Select Student");
        SelectStudent_btn.setMinWidth(100);
        SelectStudent_btn.setOnAction(e -> {
            try{
                O_list = list.getSelectionModel().getSelectedItems();
                listSelected = O_list.get(0);
                StudentSelected = database.getStudentDB(Controller.recoverStudentID(listSelected));
                right_txtF.setText("Student: " + StudentSelected.getName() + "\n" + "ID: " + StudentSelected.getStudent_id());
                error_lbl.setText("");
                database.refreshModulesDB(rightList, StudentSelected);
            }catch(Exception r){
                error_lbl.setText("Error, no Student selected");
            }
        });

        //Bottom Button HBox
        HBox bottom_Button_HBox = new HBox(10);
        bottom_Button_HBox.getChildren().addAll(SelectStudent_btn, error_lbl);

        //left vbox
        VBox leftModule_vbox = new VBox(10);
        leftModule_vbox.getChildren().addAll(moduleID_HBox, moduleName_HBox, moduleGrade_HBox, Top_Button_HBox, bottom_Button_HBox);

        VBox rightModule_vbox = new VBox();
        rightModule_vbox.getChildren().addAll(right_txtF, rightList);
        rightModule_vbox.setMaxWidth(180);
        rightModule_vbox.setMinWidth(180);

        //SplitPane
        SplitPane splitpane = new SplitPane();
        splitpane.getItems().addAll(leftModule_vbox,rightModule_vbox);
        splitpane.setPadding(new Insets(10));
        splitpane.setDividerPositions(0.6);
        splitpane.setMaxHeight(170);

        //Module Vbox
        VBox Module_vbox = new VBox(10);
        Module_vbox.getChildren().addAll(moduleTitle_lbl,moduleTitle_sep,splitpane);
        Module_vbox.setPadding(new Insets(10));

        return Module_vbox;
    }
}
