package assi1.assignment1.View;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import assi1.assignment1.Controller.*;

public class Tab_Student_Main
        extends Application {
    public Tab_Student_Main(){
    }

    public static void main(String[] args) {
        launch();
    }
    private String selected;
    private ListView<String> list;
    private ObservableList<String> O_list;
    private final Controller student_controller = new Controller();
    private final  Database database = new Database();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Assignment 2 -- Student Records but now with tabs");

        VBox student_vbox = new VBox();
        Label studentTitle_lbl = new Label("Student Registration");
        Separator studentTitle_sep = new Separator();

        //Name TextField
        TextField name_txtF = new TextField();
        Label name_lbl = new Label("Name:");
        name_lbl.setPrefWidth(100);
        HBox name_HBox = new HBox(30);
        name_HBox.getChildren().addAll(name_lbl, name_txtF);

        //ID TextField
        TextField id_txtF = new TextField();
        Label id_lbl = new Label("Student ID:");
        id_lbl.setPrefWidth(100);
        HBox id_HBox = new HBox(30);
        id_HBox.getChildren().addAll(id_lbl, id_txtF);

        //DOB DatePicker
        DatePicker dob_txtF = new DatePicker();
        dob_txtF.setEditable(false);
        Label dob_lbl = new Label("Date of Birth:");
        dob_lbl.setPrefWidth(100);
        HBox dob_HBox = new HBox(30);
        dob_HBox.getChildren().addAll(dob_lbl, dob_txtF);

        //List
        list = new ListView<>();

        //buttons HBox
        HBox btn_HBox = new HBox(10);

        Label error_lbl = new Label();

        //add button
        Button add_btn = new Button("Add");
        add_btn.setOnAction(e -> {
            try {
                //Controller.add_student(name_txtF.getText(), id_txtF.getText(), dob_txtF.getValue());
                database.addStudentDB(name_txtF.getText(), id_txtF.getText(), dob_txtF.getValue());
                name_txtF.setText("");
                id_txtF.setText("");
                dob_txtF.setValue(null);
                //Controller.refresh(list);
                database.refresh(list);
                error_lbl.setText("");
            }catch(Exception r){
                if(name_txtF.getText().equals("") || id_txtF.getText().equals("") || dob_txtF.getValue().toString().equals("")){
                    error_lbl.setText("Please fill out the forms!");
                }else{
                    error_lbl.setText("Unidentified Error!");
                }
            }
        });

        //remove button
        Button remove_btn = new Button("Remove");
        remove_btn.setOnAction(actionEvent -> {
                O_list = list.getSelectionModel().getSelectedItems();
                selected = O_list.get(0);
                database.removeStudentDB(Controller.recoverStudentID(selected));
                database.refresh(list);
                error_lbl.setText("");
        });

        //edit button
        Button edit_btn = new Button("Edit");
        edit_btn.setOnAction(e -> {
            if(list.getSelectionModel().getSelectedItems().isEmpty()){
                error_lbl.setText("Error, no Student selected");
            }else {
                O_list = list.getSelectionModel().getSelectedItems();
                selected = O_list.get(0);
                Window_Edit.DisplayEditMenu(database.getStudentDB(Controller.recoverStudentID(selected)), database);
                error_lbl.setText("");
                database.refresh(list);
            }
        });

        //region
        Region region = new Region();
        region.setMinHeight(20);

        //button HBox
        btn_HBox.getChildren().addAll(add_btn, remove_btn, edit_btn, error_lbl);

        //Load Button
        Button load_btn = new Button("load");
        load_btn.setOnAction(e -> {
            //Controller.DeserializedLoad(list);
            database.refresh(list);
        });

        //exit button
        Button exit_btn = new Button("Exit");
        exit_btn.setOnAction(e -> Platform.exit());

        //Bottom HBox
        HBox save_Load_HBox = new HBox(10);
        save_Load_HBox.setPadding(new Insets(15));
        save_Load_HBox.getChildren().addAll(load_btn, exit_btn);

        //fields vbox
        VBox vbox_fields = new VBox(10);
        vbox_fields.getChildren().addAll(studentTitle_lbl,studentTitle_sep ,name_HBox, id_HBox, dob_HBox, region, btn_HBox);
        vbox_fields.setPadding(new Insets(10));
        vbox_fields.setMinHeight(230);

        Separator separator_base = new Separator();
        student_vbox.getChildren().addAll(separator_base, list, save_Load_HBox);

        //----
        //Tabs
        //----
        TabPane main_tabPane = new TabPane();

        Tab student_tab = new Tab("Student");
        student_tab.setClosable(false);
        student_tab.setOnSelectionChanged(e -> student_tab.setContent(vbox_fields));

        Tab module_tab = new Tab("Module");
        module_tab.setClosable(false);
        module_tab.setOnSelectionChanged(e -> module_tab.setContent(Tab_Module.DisplayModuleTab(list, database)));

        Tab view_tab = new Tab("View");
        view_tab.setClosable(false);
        view_tab.setOnSelectionChanged(e -> view_tab.setContent(Tab_view.DisplayViewTab(list, database)));

        main_tabPane.getTabs().addAll(student_tab, module_tab, view_tab);
        main_tabPane.setPadding(new Insets(5));

        //main vbox
        VBox main_vbox = new VBox();
        main_vbox.getChildren().addAll(main_tabPane, student_vbox);

        //scene
        Scene student_scene = new Scene(main_vbox, 500, 700);
        primaryStage.setScene(student_scene);
        primaryStage.setResizable(false);
        primaryStage.setMaxHeight(800);
        primaryStage.show();
    }
}