package assi1.assignment1.Controller;

import java.util.ArrayList;
import assi1.assignment1.Model.*;

/**
 * This class holds methods for handling the StudentList(ArrayList<Student>)
 */
public class StudentList{

    private ArrayList<Student> student_list;

    /**
     * This constructor creates a new ArrayList of students
     */
    public StudentList(){
        //System.out.println("StudentList.constructed");
        student_list = new ArrayList<>();
    }

    /**
     * This method adds a student to the studentList
     * @param student Student object
     */
    public void add_to_list(Student student){
        //System.out.println("StudentList.add_to_list");
        student_list.add(student);
    }

    /**
     * This method gets the size of the studentList
     * @return Int representing the number of Students in the list
     */
    public int getSize(){
        //System.out.println("StudentList.getSize");
        return student_list.size();
    }

    /**
     * This method returns a student based on the index given
     * @param i Int used to get a student from the StudenList at the Index i
     * @return Student Object
     */
    public Student getStudent(int i){
        //System.out.println("StudentList.getStudent");
        return student_list.get(i);
    }

    /**
     * This method returns the ArrayList
     *
     * @return Arraylist of Student Objects
     */
    public ArrayList getStudent_array(){
        return student_list;
    }
}
