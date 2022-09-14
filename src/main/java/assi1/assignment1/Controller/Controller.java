package assi1.assignment1.Controller;

import assi1.assignment1.Model.Modules;
import assi1.assignment1.Model.Student;
import javafx.scene.control.ComboBox;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class holds methods that bridge the interactions between the Model and View of the project
 */
public class Controller{
    private static Database database;

    /**
     * this constructor calls and creates a new instance of Database
     */
    public Controller(){
        database= new Database();
        //Loop creating infinite dummy people until out of memory
        //StudentList studentList = new StudentList();
        //while(true){studentList.add_to_list(new Student("Adrian Kowalski", "CR001", LocalDate.now()));}
    }

    /**
     * This method takes in a String of information of a student from an observale list and picks out the student ID
     *
     * @param text The String returned from the observable list containing student id, name and dob
     * @return the student id from the text
     */
    public static String recoverStudentID(String text){
        System.out.println(text);
        String[] Id_lookup = text.split("\\s");
        String recovered_id;

        int id_index = Id_lookup.length - 5;

        recovered_id = Id_lookup[id_index];

        return recovered_id;
    }

    /**
     * This method takes in a string of information of a module from an observable list and picks out the module ID
     *
     * @param text The String returned from the observable list containing module id, name and result
     * @return the module id from the text
     */
    public static String recoverModuleID(String text){
        String[] id_lookup = text.split("\\s");
        String recovered_id;

        recovered_id = id_lookup[1];

        return recovered_id;
    }

    /**
     * This method takes a combobox and populates it with module IDs from the database
     *
     * @param combo an JavaFX ComboBox of Strings
     * @return a list of modules from the database
     */
    public static ArrayList<Modules> populateModuleCB(ComboBox<String> combo){
        combo.getItems().clear();
        ArrayList<Modules> list = database.getAllModulesListDB();
        int i;
        for(i = 0; i < list.size(); i++){
            combo.getItems().add(list.get(i).getModule_id());
        }
        return list;
    }
}
