import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;
import java.util.*;

public class HungGrade{

    public HungGrade(ArrayList<Student> students, ArrayList<Spot> spots){
        
        Comparator<Student> studentComparator = new Comparator<Student>() {
            @Override
            public int compare(Student person1, Student person2) {
                return Integer.compare(person2.getGrade(), person1.getGrade());
            }
        };

        Collections.sort(students, studentComparator);

        ArrayList<Student> grade4 = new ArrayList<Student>();
        ArrayList<Student> grade3 = new ArrayList<Student>();
        ArrayList<Student> grade2 = new ArrayList<Student>();
        ArrayList<Student> grade1 = new ArrayList<Student>();
        int i = 0;
        while(students.get(i).getGrade() == 4){
            grade4.add(students.get(i));
            students.remove(i);
        }
        System.out.println("Seniors");
        Hungarian hbm4 = new Hungarian(grade4, spots);
        int[] result4 = hbm4.execute();
        hbm4.printResult(result4, grade4, spots);

        int x = 0;

        while(x < spots.size()){
            if (spots.get(x).getState() == true){
                spots.remove(x);
            }
            else{
                x += 1;
            }
        }
        while(students.get(i).getGrade() == 3){
            grade3.add(students.get(i));
            students.remove(i);
        }
        System.out.println(" ");
        System.out.println("Juniors");
        Hungarian hbm3 = new Hungarian(grade3, spots);
        int[] result3 = hbm3.execute();
        hbm3.printResult(result3, grade3, spots);
        x = 0;

        while(x < spots.size()){
            if (spots.get(x).getState() == true){
                spots.remove(x);
            }
            else{
                x += 1;
            }
        }
        
        while(students.get(i).getGrade() == 2){
            grade2.add(students.get(i));
            students.remove(i);
        }
        System.out.println(" ");
        System.out.println("Sophomores");
        Hungarian hbm2 = new Hungarian(grade2, spots);
        int[] result2 = hbm2.execute();
        hbm2.printResult(result2, grade2, spots);

        x = 0;

        while(x < spots.size()){
            if (spots.get(x).getState() == true){
                spots.remove(x);
            }
            else{
                x += 1;
            }
        }

        while(students.size() > 0){
            grade1.add(students.get(i));
            students.remove(i);
        }

        System.out.println(" ");
        System.out.println("Freshmen");
        Hungarian hbm1 = new Hungarian(grade1, spots);
        int[] result1 = hbm1.execute();
        hbm1.printResult(result1, grade1, spots);

    }

}