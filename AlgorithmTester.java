import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import java.awt.*;


public class AlgorithmTester extends JPanel{

    public static void genSouthLot(ArrayList<Spot> spots, int counter){
        int x = 13;
        boolean isswitch = true;
        for (int y = 0; y < 8; y++){
            for (int i = 0; i < 39; i++){
                spots.add(new Spot(counter, new Point(i, x)));
                counter++;
            }
            if (x == 12){
                x += 5;
                isswitch = false;
            }
            else if (isswitch == true){
                x += 4;
                isswitch = false;
            }
            else{
                x += 1;
                isswitch = true;
            }
        }
    }

    public static void genWestLot(ArrayList<Spot> spots, int counter){
        int x = 140;
        for (int y = 0; y < 12; y++){
            for (int i = 175; i < 191; i++){
                spots.add(new Spot(counter, new Point(i, x)));
                counter++;
            }
            x-=1;
        }
    }

    public static void genNorthLot(ArrayList<Spot> spots, int counter){
        int x = 75;
        boolean isswitch = true;
        for (int y = 0; y < 10; y++){
            for (int i = 237; i < 252; i++){
                spots.add(new Spot(counter, new Point(i, x)));
                counter++;
            }
            if (isswitch == true){
                x += 4;
                isswitch = false;
            }
            else{
                x += 1;
                isswitch = true;
            }
        }
    }

    public static void genCro(ArrayList<Spot> spots, int counter){
        int x = 66;
        for (int i = 118; i < 212; i++){
            spots.add(new Spot(counter, new Point(i, x)));
            counter++;
        }
    }

    public static void genFoH(ArrayList<Spot> spots, int counter){
        int x = 194;
        for (int i  = 77; i < 95; i++){
            spots.add(new Spot(counter, new Point(x, i)));
            counter++;
        }
        x = 192;
        for (int z = 74; z < 94; z++){
            spots.add(new Spot(counter, new Point(x, z)));
            counter++;
        }
    }

    public static void genFoJ(ArrayList<Spot> spots, int counter){
        int x = 120;
        for (int i = 196; i < 222; i++){
            spots.add(new Spot(counter, new Point(i, x)));
            counter++;
        }
        x = 118;
        for (int z = 196; z < 207; z++){
            spots.add(new Spot(counter, new Point(z, x)));
            counter++;
        }
    }

    public static void genMor(ArrayList<Spot> spots, int counter){
        int x = 197;
        for (int i = 70; i < 73; i++){
            spots.add(new Spot(counter, new Point(x, i)));
            counter++;
        }
        x = 70;
        for (int z = 199; z < 208; z++){
            spots.add(new Spot(counter, new Point(z, x)));
            counter++;
        }
        x = 65;
        for (int j = 212; j < 224; j++){
            spots.add(new Spot(counter, new Point(j, x)));
            counter++;
        }
        x = 63;
        for (int y = 212; y < 224; y++){
            spots.add(new Spot(counter, new Point(y, x)));
            counter++;
        }
        x = 61;
        for (int f = 212; f < 228; f++){
            spots.add(new Spot(counter, new Point(f, x)));
            counter++;
        }
        x = 69;
        for (int l = 224; l < 232; l++){
            spots.add(new Spot(counter, new Point(l, x)));
            counter++;
            x += 1;
        }
    }

    public static void genBeck(ArrayList<Spot> spots, int counter){
        int x = 172;
        for (int i = 21; i < 26; i++){
            spots.add(new Spot(counter, new Point(x, i)));
            counter++;
        }
        x = 176;
        for (int z = 19; z < 26; z++){
            spots.add(new Spot(counter, new Point(x, z)));
            counter++;
        }
        x = 180;
        for (int y = 16; y < 25; y++){
            spots.add(new Spot(counter, new Point(x, y)));
            counter++;
        }
    }

    public static void genHale(ArrayList<Spot> spots, int counter){
        int x = 35;
        for (int i = 124; i < 133; i++){
            spots.add(new Spot(counter, new Point(i, x)));
            counter++;
        }
    }

    public static void genBill(ArrayList<Spot> spots, int counter){
        int x = 38;
        for (int i = 54; i < 96; i++){
            if (i != 8 && i != 9 && i != 10 && i != 11){
                spots.add(new Spot(counter, new Point(i, x)));
                counter++;
            }
        }
        x = 43;
        int y = 62;

        for (int z = 62; z < 80; z++){
            spots.add(new Spot(counter, new Point(y, x)));
            y = y + 2;
            counter++;
        }
    }

    public static void genSpots(ArrayList<Spot> spots, int numspots){
        int counter = 1;
        genSouthLot(spots, counter);
        counter = spots.size() + 1;
        genWestLot(spots, counter); 
        counter = spots.size() + 1;
        genNorthLot(spots, counter);
        counter = spots.size() + 1;
        genCro(spots, counter);
        counter = spots.size() + 1;
        genFoH(spots, counter);
        counter = spots.size() + 1;
        genFoJ(spots, counter);
        counter = spots.size() + 1;
        genMor(spots, counter); 
        counter = spots.size() + 1;
        genBeck(spots, counter);
        counter = spots.size() + 1;
        genHale(spots, counter);
        counter = spots.size() + 1;
        genBill(spots, counter);
    }

    public static void genStudents(ArrayList<Student> students, int numstudents){

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
            ArrayList<Point> classes = new ArrayList<Point>();
            for (int z = 0; z < 4; z++){
                int c = rand.nextInt((classmax - classmin) + 1) + classmin;
                classes.add(classlocations.get(c));
            }
            int d = rand.nextInt((dorms.size()-1 - classmin) + 1) + classmin;
            students.add(new Student(i, grade, classes, dorms.get(d)));
        }
    }

    public static void main(String[] args){
        ArrayList<Spot> spots = new ArrayList<Spot>();
        ArrayList<Student> students = new ArrayList<Student>();
        genSpots(spots, 0);
        genStudents(students, 800);

        //Algorithms
        //
        HungGrade hunggrade = new HungGrade(students, spots);
        //Lottery lottery = new Lottery(students, spots);4
        //Centroid centroid = new Centroid(students, spots);
        //HungAssign hungarian = new HungAssign(students, spots);
        //Randassign rand = new Randassign(students, spots);
        //3LocalSearch local = new LocalSearch(students, spots);

        //Imaging
        //
        // JFrame frame = new JFrame("Point Plotter");
        // PointPlotter pointPlotter = new PointPlotter(spots);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().add(pointPlotter);
        // frame.setSize(400, 400);
        // frame.setVisible(true);

    }




}