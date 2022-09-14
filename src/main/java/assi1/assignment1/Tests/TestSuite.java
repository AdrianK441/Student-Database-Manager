package assi1.assignment1.Tests;

import assi1.assignment1.Controller.Controller;
import assi1.assignment1.Model.*;
import org.junit.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestSuite {

    @Test
    public void TestRecoverStudentID(){
        String testS1 = "Student Name:  Adrian  Kowalski\n" +
                        "Student ID:    R001\n" +
                        "Date of Birth: 2022-05-20";
        String testS2 = "Student Name:  Bob  Jones\n" +
                        "Student ID:    R025\n" +
                        "Date of Birth: 2021-07-10";
        String testS3 =  "Student Name:  Josh  Walker\n" +
                        "Student ID:    R089\n" +
                        "Date of Birth: 2020-01-05";

        assertEquals("R001", Controller.recoverStudentID(testS1));
        assertEquals("R025", Controller.recoverStudentID(testS2));
        assertEquals("R064", Controller.recoverStudentID(testS3));
    }

    @Test
    public void TestRecoverModuleID(){
        String test1 =  "ID: CR6\n" +
                        "Module Name: C Programming\n" +
                        "Grade: 87";
        String test2 =  "ID: CR4\n" +
                        "Module Name: NoSQL\n" +
                        "Grade: 71";
        String test3 =  "ID: CR1\n" +
                        "Module Name: OOP\n" +
                        "Grade: 34";

        assertEquals("CR6",Controller.recoverModuleID(test1));
        assertEquals("CR4",Controller.recoverModuleID(test2));
        assertEquals("CR1",Controller.recoverModuleID(test3));
    }

    @Test
    public void TestGetName(){
        Student student = new Student("Adrian kowalski","R002", LocalDate.now());
        Student student2 = new Student("Bob Builder","R075", LocalDate.now());

        assertEquals("Adrian Kowalski", student.getName());
        assertEquals("James Cook", student2.getName());
    }
}
