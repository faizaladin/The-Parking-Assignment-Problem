import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class Student{

    private int ID;
    private int grade;
    private List<Point> classes;
    private double currDist = 100000000;
    private Spot currSpot;
    private Point dorm;

    public Student(int ID, int grade, List<Point> classes, Point dorm){
        this.ID = ID;
        this.grade = grade;
        this.classes = classes;
        this.dorm = dorm;
    }

    public int getID(){
        return ID;
    }

    public int getGrade(){
        return grade;
    }

    public double getCurrDist(){
        return currDist;
    }

    public Spot getSpot(){
        return currSpot;
    }

    public List<Point> getClasses(){
        return classes;
    }

    public Point getDorm(){
        return dorm;
    }

    public void setCurrDist(double newDist){
        currDist = newDist;
    }

    public void setSpot(Spot newSpot){
        currSpot = newSpot;
    }
    

}