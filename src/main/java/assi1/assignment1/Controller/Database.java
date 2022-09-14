package assi1.assignment1.Controller;

import assi1.assignment1.Model.Modules;
import assi1.assignment1.Model.Student;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This Class creates a connection to the database and holds the methods to handle the database
 */
public class Database{
    private static final String URL = "jdbc:derby:C:\\Apache\\db-derby-10.15.2.0-bin\\bin\\StudentSystems;";

    private Connection connection = null;

    private PreparedStatement addStudent = null;
    private PreparedStatement removeStudent = null;
    private PreparedStatement selectStudent = null;
    private PreparedStatement selectAllStudents = null;

    private PreparedStatement addModule = null;
    private PreparedStatement addModuleToStudent = null;
    private PreparedStatement removeStudentModuleByStudentID = null;
    private PreparedStatement addResultsToModule = null;
    private PreparedStatement removeResultsByStudentID = null;
    private PreparedStatement selectAllStudentModulesByStudentID = null;
    private PreparedStatement selectAllModules = null;
    private PreparedStatement selectResultByModuleAndStudentIDs = null;
    private PreparedStatement selectModuleByModuleID = null;
    private PreparedStatement removeSingleModule = null;
    private PreparedStatement removeSingleResult = null;
    private PreparedStatement selectResultsOver = null;

    /**
     * This Constructor created the connection and PreparedStatements to the database
     */
    public Database(){
        try{
            connection = DriverManager.getConnection(URL);

            addStudent = connection.prepareStatement("INSERT INTO Student (student_id, name, dob) VALUES ( ?, ?, ?)");
            removeStudent = connection.prepareStatement("DELETE FROM Student WHERE student_id = ?");
            removeStudentModuleByStudentID = connection.prepareStatement("DELETE FROM StudentModules WHERE student_id = ?");
            removeResultsByStudentID = connection.prepareStatement("DELETE FROM Results WHERE student_id = ?");
            selectAllStudents = connection.prepareStatement("SELECT * FROM Student");
            selectStudent = connection.prepareStatement("SELECT * FROM Student WHERE student_id = ?");
            selectAllStudentModulesByStudentID = connection.prepareStatement("SELECT * FROM StudentModules WHERE student_id = ?");
            selectResultByModuleAndStudentIDs = connection.prepareStatement("SELECT * FROM Results WHERE student_id = ? AND module_id = ?");
            selectModuleByModuleID = connection.prepareStatement("SELECT * FROM Modules WHERE module_id = ?");
            addModule = connection.prepareStatement("INSERT INTO Modules (module_id, name) VALUES ( ?, ?)");
            addModuleToStudent = connection.prepareStatement("INSERT INTO StudentModules (student_id, module_id) VALUES ( ?, ?)");
            addResultsToModule = connection.prepareStatement("INSERT INTO Results (student_id, module_id, results) VALUES ( ?, ?, ?)");
            removeSingleModule = connection.prepareStatement("DELETE FROM StudentModules WHERE module_id = ? AND student_id = ?");
            removeSingleResult = connection.prepareStatement("DELETE FROM Results WHERE module_id = ? AND student_id = ?");
            selectAllModules = connection.prepareStatement("SELECT * FROM Modules");
            selectResultsOver = connection.prepareStatement("SELECT * FROM Results WHERE student_id = ? AND results > ?");

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            System.exit( 1);
        }
    }

    /**
     * This Method takes in a Name, ID and Date of birth and uses the data to add a student to the database
     *
     * @param name String used as a name for a student to be added to the database
     * @param id String used as ID for a student to be added to the database
     * @param dob LocalDate used as a Date of birth for a student to be added to the database
     */
    public void addStudentDB(String name, String id, LocalDate dob){
        try{
            addStudent.setString(1,id);
            addStudent.setString(2,name);
            addStudent.setDate(3, Date.valueOf(dob));

            addStudent.executeUpdate();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
    }

    /**
     * This method takes in an id and removes the student and their module records associated with that id
     *
     * @param id String used as student_id for finding a student and removing them from the database
     */
    public void removeStudentDB(String id){
        try{
            removeResultsByStudentID.setString(1, id);
            removeResultsByStudentID.executeUpdate();

            removeStudentModuleByStudentID.setString(1, id);
            removeStudentModuleByStudentID.executeUpdate();

            removeStudent.setString(1,id);
            removeStudent.executeUpdate();

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
    }

    /**
     * This method takes in an id and returns a student associated with that id
     *
     * @param id String used as student_id for finding a student
     * @return student object associated with id
     */
    public Student getStudentDB(String id){
        //System.out.println("Database.getStudentDB");
        Student student = null;
        ResultSet resultSet;
        try{
            selectStudent.setString(1,id);

            resultSet = selectStudent.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            String student_id = resultSet.getString("student_id");
            LocalDate dob = resultSet.getDate("dob").toLocalDate();

            student = new Student(name, student_id, dob);

            resultSet.close();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        return student;
    }

    /**
     * This method returns a studentList of all students currently in the database
     *
     * @return studentlist of all students in the database
     */
    public StudentList getStudentsListDB(){
        StudentList List = new StudentList();
        ResultSet resultSet = null;

        try{
            resultSet = selectAllStudents.executeQuery();
            while(resultSet.next()){
                List.add_to_list(new Student(resultSet.getString("name"),
                                                resultSet.getString("student_id"),
                                                resultSet.getDate("dob").toLocalDate())
                );
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return List;
    }

    /**
     * This method returns an ArrayList of modules associated with a student
     *
     * @param student Student object used to get a student_id and its modules
     * @return ArrayList of Modules
     */
    public ArrayList<Modules> getModulesListByStudentDB(Student student){
        ArrayList<Modules> List = new ArrayList<>();
        ResultSet resultSet = null;
        try{
            selectAllStudentModulesByStudentID.setString(1, student.getStudent_id());
            resultSet = selectAllStudentModulesByStudentID.executeQuery();
            while(resultSet.next()){
                selectModuleByModuleID.setString(1, resultSet.getString("module_id"));
                ResultSet resultSet1 = selectModuleByModuleID.executeQuery();
                resultSet1.next();

                selectResultByModuleAndStudentIDs.setString(1, student.getStudent_id());
                selectResultByModuleAndStudentIDs.setString(2, resultSet.getString("module_id"));
                ResultSet resultSet2 = selectResultByModuleAndStudentIDs.executeQuery();
                resultSet2.next();

                List.add(new Modules(resultSet1.getString("module_id"),resultSet1.getString("name"),resultSet2.getInt("results")));
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return List;
    }

    /**
     * This method returns an ArrayList of modules where the module result must be above min
     *
     * @param student Student object used to get student_id and get its associated modules
     * @param min Int used as the minimum result that the ArrayList will accept
     * @return ArrayList of modules
     */
    public ArrayList<Modules> getModulesListByResultDB(Student student, int min){
        ArrayList<Modules> List = new ArrayList<>();
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        try{
            selectResultsOver.setString(1, student.getStudent_id());
            selectResultsOver.setInt(2, (min-1));
            resultSet = selectResultsOver.executeQuery();
            while(resultSet.next()){
                selectModuleByModuleID.setString(1, resultSet.getString("module_id"));
                resultSet1 = selectModuleByModuleID.executeQuery();
                resultSet1.next();
                List.add(new Modules(resultSet.getString("module_id"),resultSet1.getString("name"), resultSet.getInt("results")));
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        finally {
            try{
                resultSet.close();
                resultSet1.close();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return List;
    }

    /**
     * This method returns an Arraylist of all modules in the database
     *
     * @return ArrayList of modules
     */
    public ArrayList<Modules> getAllModulesListDB(){
        ArrayList<Modules> List = new ArrayList<>();
        ResultSet resultSet = null;
        try{
            resultSet = selectAllModules.executeQuery();
            while(resultSet.next()){
                List.add(new Modules(resultSet.getString("module_id"),resultSet.getString("name"), 0));
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return List;
    }

    /**
     * This method takes in a Student, module_id, name and results and uses this data to add a module to the Student
     *
     * @param student Student object used to get the student_id to associate the module with
     * @param module_id String used as a module_id for the module
     * @param name String used as a name for the module
     * @param results Int used as the results for the module
     */
    public void addModuleDB(Student student, String module_id, String name, int results){
        try{
            if(VerifyModule(module_id) == 0){
                addModule.setString(1,module_id);
                addModule.setString(2, name);
                addModule.executeUpdate();
            }
            addModuleToStudent.setString(1, student.getStudent_id());
            addModuleToStudent.setString(2, module_id);

            addResultsToModule.setString(1, student.getStudent_id());
            addResultsToModule.setString(2, module_id);
            addResultsToModule.setInt(3, results);

            addModuleToStudent.executeUpdate();
            addResultsToModule.executeUpdate();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
    }

    /**
     * This method takes in an module id and Student and removes a module record from a Student
     *
     * @param id String used as the module_id to fint the module in the database
     * @param student Student object used to get the student_id
     */
    public void removeModuleDB(String id, Student student){
        try{
            removeSingleResult.setString(1, id);
            removeSingleResult.setString(2, student.getStudent_id());

            removeSingleModule.setString(1, id);
            removeSingleModule.setString(2, student.getStudent_id());

            removeSingleResult.executeUpdate();
            removeSingleModule.executeUpdate();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
    }

    /**
     * This Method takes in a ListView, clears it and re-adds all students from the database by calling the getStudentsListDB() Method
     *
     * @param list ListView holds a list of students
     */
    public void refresh(ListView<String> list){
        list.getItems().clear();
        StudentList studentlist = getStudentsListDB();
        for(int i=0; i<studentlist.getSize(); i++){
            list.getItems().add(studentlist.getStudent(i).formatToString());
        }
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * This Method takes in a ListView, clears it and then calls getStudentsListDB() Method into an Arraylist, then sorts the array
     * with a designated method and re-adds the students to the ListView
     *
     * @param list ListView gets its content updated with data from the database and sorted
     * @param method Int used to tell the Method how to sort the list
     */
    public void Sorted_RefreshDB(ListView<String> list, int method){
        list.getItems().clear();
        StudentList studentList = getStudentsListDB();
        ArrayList<Student> array = studentList.getStudent_array();
        if(method == 0){
            array.sort(Comparator.comparing(Student::getName));
        }else if(method == 1){
            array.sort(Comparator.comparing(Student::getStudent_id));
        }else if(method == 2){
            array.sort(Comparator.comparing(Student::getDOB));
        }
        for(int i=0; i<studentList.getSize(); i++){
            list.getItems().add(studentList.getStudent(i).formatToString());
        }
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * This Method takes in a ListView, clears it and re-adds all modules fromt the database associated with student by calling
     * getModulesListByStudentDB()
     *
     * @param list ListView holds a list of modules associated with student
     * @param student Student object used to get student_id to associate the modules with
     */
    public void refreshModulesDB(ListView<String> list, Student student){
        list.getItems().clear();
        ArrayList<Modules> moduleList = getModulesListByStudentDB(student);
        for (Modules modules : moduleList) {
            list.getItems().add(modules.FormatToString());
        }
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * This method takes in a student and a minimum value, it creates a string to display the information of the student
     * and their modules which results are above the min value
     *
     * @param student Student Object used to get information to display and student_id to associate modules with
     * @param min Int used as the minimum result that the method will display
     * @return String containing information of the student and their modules
     */
    public String formatForView(Student student, int min){
        ArrayList<Modules> module_array = null;
        StringBuilder student_description = new StringBuilder((  "Student Name: " + student.getName() + "\n" +
                "Student ID: " + student.getStudent_id() + "\n" +
                "Student date of birth: " + student.getDOB().toString() + "\n\n" +
                "Student Modules:\n"));
        if(min <= 0){
            module_array = getModulesListByStudentDB(student);
        }else{
            module_array = getModulesListByResultDB(student, min);
        }
        for(int i=0; i < module_array.size(); i++){
            student_description.append("(").append(i + 1).append("). Name: ").append(module_array.get(i).getModule_name()).append(" | Result: ").append(module_array.get(i).getModule_result()).append("\n");
        }
        return student_description.toString();
    }

    /**
     * This method verifies if a module already exists in the database, returns 1 if it does and 0 if it doesnt
     *
     * @param module_id String used as a module_id to select from the database
     * @return int 0 if module doesnt exist in the database, 1 if it does exist in the database
     */
    public int VerifyModule(String module_id){
        int verify = 0;
        try{
            selectModuleByModuleID.setString(1, module_id);
            ResultSet resultSet = selectModuleByModuleID.executeQuery();
            if(resultSet.next()){
                String id = resultSet.getString("module_id");
                if(module_id.equals(id)){
                    verify = 1;
                }
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            close();
        }
        return verify;
    }

    /**
     * This method closes the connection to the database
     */
    public void close(){
        try{
            connection.close();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
