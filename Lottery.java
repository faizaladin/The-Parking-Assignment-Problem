import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;


public class Lottery{

    public ArrayList<Student> students;
    public ArrayList<Spot> spots;
    

    public double findDist(Student student, Spot spot){
        List<Point> classes = student.getClasses();
        double dist = 0;
        Point spotloc = spot.getLoc();
        for(int i = 0; i < classes.size(); i++){
            Point point1 = classes.get(i);
            dist += point1.distance(spotloc);
        }
        dist += student.getDorm().distance(spotloc);
        return dist;
    }


    public Lottery(ArrayList<Student> students, ArrayList<Spot> spots){
        this.students = students;
        this.spots = spots;

        Comparator<Student> studentComparator = new Comparator<Student>() {
            @Override
            public int compare(Student person1, Student person2) {
                return Integer.compare(person2.getGrade(), person1.getGrade());
            }
        };

        Collections.sort(students, studentComparator);

        Student beststu = null;
        Student worststu = null;
        Student beststu1 = null;
        Student worststu1 = null;
        Student beststu2 = null;
        Student worststu2 = null;
        Student beststu3 = null;
        Student worststu3 = null;
        Student beststu4 = null;
        Student worststu4 = null;
        double grade1dist = 0;
        double grade1num = 0;
        double grade2dist = 0;
        double grade2num = 0;
        double grade3dist = 0;
        double grade3num = 0;
        double grade4dist = 0;
        double grade4num = 0;
    
        for(int i = 0; i < students.size(); i++){
            Student student = students.get(i);
            for(int z = 0; z < spots.size(); z++){
                Spot spot = spots.get(z);
                if(spot.getState() != true){
                    double distance = findDist(student, spot);
                    if(distance < student.getCurrDist()){
                        if(student.getSpot() != null){
                            student.getSpot().setState(false);
                        }
                        student.setSpot(spot);
                        student.setCurrDist(distance);
                        spot.setState(true);
                    }
                }
            }
            if (beststu == null){
                beststu = student;
            }
            if (worststu == null){
                worststu = student;
            }
            else{
                if (beststu.getCurrDist() > student.getCurrDist()){
                    beststu = student;
                }
                if (worststu.getCurrDist() < student.getCurrDist()){
                    worststu = student;
                }
            }

            if(student.getGrade() == 1){
                grade1dist += student.getCurrDist();
                grade1num += 1;
                if (beststu1 == null){
                beststu1 = student;
                }
                if (worststu1 == null){
                worststu1 = student;
                }
                else{
                    if (beststu1.getCurrDist() > student.getCurrDist()){
                        beststu1 = student;
                    }
                    if (worststu1.getCurrDist() < student.getCurrDist()){
                    worststu1 = student;
                    }
                }
            }
            if(student.getGrade() == 2){
                grade2dist += student.getCurrDist();
                grade2num += 1;
                if (beststu2 == null){
                beststu2 = student;
                }
                if (worststu2 == null){
                worststu2 = student;
                }
                else{
                    if (beststu2.getCurrDist() > student.getCurrDist()){
                        beststu2 = student;
                    }
                    if (worststu2.getCurrDist() < student.getCurrDist()){
                    worststu2 = student;
                    }
                }
            }
            if(student.getGrade() == 3){
                grade3dist += student.getCurrDist();
                grade3num += 1;
                if (beststu3 == null){
                beststu3 = student;
                }
                if (worststu3 == null){
                worststu3 = student;
                }
                else{
                    if (beststu3.getCurrDist() > student.getCurrDist()){
                        beststu3 = student;
                    }
                    if (worststu3.getCurrDist() < student.getCurrDist()){
                    worststu3 = student;
                    }
                }
            }
            if(student.getGrade() == 4){
                grade4dist += student.getCurrDist();
                grade4num += 1;
                if (beststu4 == null){
                beststu4 = student;
                }
                if (worststu4 == null){
                worststu4 = student;
                }
                else{
                    if (beststu4.getCurrDist() > student.getCurrDist()){
                        beststu4 = student;
                    }
                    if (worststu4.getCurrDist() < student.getCurrDist()){
                    worststu4 = student;
                    }
                }
            }

        }
        double avggrade1 = grade1dist/grade1num;
        double avggrade2 = grade2dist/grade2num;
        double avggrade3 = grade3dist/grade3num;
        double avggrade4 = grade4dist/grade4num;

        System.out.println("Lottery");
        System.out.println(" ");
        System.out.println("Average Walking Distance of Student: " + ((grade1dist + grade2dist + grade3dist + grade4dist)/students.size()));
        System.out.println("Average Walking Distance for Freshman: " + avggrade1);
        System.out.println("Average Walking Distance for Sophomores: " + avggrade2);
        System.out.println("Average Walking Distance for Juniors: " + avggrade3);
        System.out.println("Average Walking Distance for Seniors: " + avggrade4);

        System.out.println(" ");

        System.out.println("Grade of student with best parking spot: " + beststu.getGrade());
        System.out.println("Walking Distance of student with best spot: " + beststu.getCurrDist());
        System.out.println("Grade of student with worst parking spot: " + worststu.getGrade());
        System.out.println("Walking Distance of student with worst spot: " + worststu.getCurrDist());
        System.out.println(" ");
        System.out.println("Smallest walking distance of Freshman: " + beststu1.getCurrDist());
        System.out.println("Largest walking distance of Freshman: " + worststu1.getCurrDist());
        System.out.println(" ");
        System.out.println("Smallest walking distance of Sophomores: " + beststu2.getCurrDist());
        System.out.println("Largest walking distance of Sophomores: " + worststu2.getCurrDist());
        System.out.println(" ");
        System.out.println("Smallest walking distance of Juniors: " + beststu3.getCurrDist());
        System.out.println("Largest walking distance of Juniors: " + worststu3.getCurrDist());
        System.out.println(" ");
        System.out.println("Smallest walking distance of Seniors: " + beststu4.getCurrDist());
        System.out.println("Largest walking distance of Seniors: " + worststu4.getCurrDist());

        System.out.println(" ");

        String filename = "lottery.csv";

        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (Student student : students) {
                fileWriter.append(String.valueOf(student.getID()));
                fileWriter.append(",");
                fileWriter.append(String.valueOf(student.getGrade()));
                fileWriter.append(",");
                fileWriter.append(String.valueOf(student.getCurrDist()));
                fileWriter.append("\n");
                
            }
        } 
        catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
        
    }

}