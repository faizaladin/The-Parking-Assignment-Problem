import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import java.awt.*;

public class GenStudents {

    public GenStudents(List<Student> students, int numstudents){

        List<Point> classlocations = new ArrayList<Point>();
        classlocations.add(new Point(49, 48)); //Cummings
        classlocations.add(new Point(62, 52)); //Palmer
        classlocations.add(new Point(79, 53)); //Bill
        classlocations.add(new Point(94, 44)); //Fanning
        classlocations.add(new Point(109, 55)); //NLH
        classlocations.add(new Point(109, 24)); //Olin
        classlocations.add(new Point(124, 25)); //Hale
        classlocations.add(new Point(136, 25)); //Tansil Theater
        classlocations.add(new Point(139, 94)); //Shain
        classlocations.add(new Point(175, 77)); //Crozier Williams
        classlocations.add(new Point(123, 90)); //Blaustein

        List<Point> dorms = new ArrayList<Point>();
        dorms.add(new Point(53,110)); //Freeman
        dorms.add(new Point(63,121)); //Jane Addams
        dorms.add(new Point(81,122)); //Harkness
        dorms.add(new Point(101,120)); //Knowlton
        dorms.add(new Point(124,117)); //Windham
        dorms.add(new Point(155,136)); //Lazrus
        dorms.add(new Point(179,39)); //Larrabee
        dorms.add(new Point(186,53)); //Katherine Blunt
        dorms.add(new Point(202,11)); //360 Apartment
        dorms.add(new Point(202,103)); //Wright
        dorms.add(new Point(212,107)); //Park
        dorms.add(new Point(222,102)); //Johnson
        dorms.add(new Point(203,78)); //Morrison
        dorms.add(new Point(219,75)); //Lambdin
        dorms.add(new Point(227,74)); //Hamilton


        for (int i = 0; i < numstudents; i++){
            Random rand = new Random();
            int grademin = 1;
            int grademax = 4;
            int classmin = 0;
            int classmax = classlocations.size()-1;
            int grade = rand.nextInt((grademax - grademin) + 1) + grademin;
            List<Point> classes = new ArrayList<Point>();
            for (int z = 0; z < 4; z++){
                int x = rand.nextInt((classmax - classmin) + 1) + classmin;
                classes.add(classlocations.get(x));
            }
            int x = rand.nextInt((dorms.size()-1 - classmin) + 1) + classmin;
            students.add(new Student(i, grade, classes, dorms.get(x)));
        }
    }


}