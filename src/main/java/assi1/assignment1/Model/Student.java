package assi1.assignment1.Model;

import java.time.LocalDate;

/**
 * This class creates a Student Object and holds methods related to it
 */
public class Student{

    private Name name;
    private String student_id;
    private LocalDate DOB;

    /**
     * This constructor creates a new Student Object with the inserted parameters
     * @param name String used as Students name
     * @param student_id String used as Students id
     * @param DOB LocalDate use as Students date of birth
     */
    public Student(String name, String student_id, LocalDate DOB){
        //System.out.println("Student.Constructed");
        this.name = new Name(name);
        this.student_id = student_id;
        this.DOB = DOB;
    }

    /**
     * This method returns the name of the Student Object
     * @return String name
     */
    public String getName(){
        //System.out.println("Student.getName");
        return name.getFullName();
    }

    /**
     * This method returns the student_id of the Student Object
     * @return String student_id
     */
    public String getStudent_id(){
        //System.out.println("Student.getStudent_id");
        return student_id;
    }

    /**
     * This method returns the Date of Birth of the student Object
     * @return LocalDate DOB
     */
    public LocalDate getDOB() {
        //System.out.println("Student.getDOB");
        return DOB;
    }

    /**
     * This method sets the name of the Student Object to the inserted parameter
     * @param name String name
     */
    public void setName(String name){
        this.name = new Name(name);
    }

    /**
     * This method sets the student_id of the Student Object to the inserted parameter
     * @param id String student id
     */
    public void setStudent_id(String id){
        this.student_id = id;
    }

    /**
     * This method sets the Date Of Birth of the Student Object to the inserted parameter
     * @param dob LocalDate date of birth
     */
    public void setDOB(LocalDate dob){
        this.DOB = dob;
    }

    /**
     * This method displays the information of the Student Object in a String
     * @return String with information of the Student
     */
    public String formatToString(){
        //System.out.println("Student.formatToString");
        return  "Student Name:  "+name.getFullName()+"\n" +
                "Student ID:    "+student_id+"\n" +
                "Date of Birth: "+DOB.toString()+"\n";
    }

}
