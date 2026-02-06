import java.awt.Point;

public class Spot{

    private int ID;
    private boolean taken = false;
    private Point location;
    private Student student;

    public Spot(int ID, Point location){
        this.ID = ID;
        this.location = location;
    }

    public int getID(){
        return ID;
    }

    public Point getLoc(){
        return location;
    }

    public boolean getState(){
        return taken;
    }

    public Student getStudent(){
        return student;
    }

    public void setState(boolean state){
        taken = state;
    }

    public void setStudent(Student newStudent){
        student = newStudent;
    }


}