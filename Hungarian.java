import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;
import java.util.*;

public class Hungarian{
    
    private final double[][] costMatrix;
    private final int        rows, cols, dim;
    private final double[]   labelByWorker, labelByJob;
    private final int[]      minSlackWorkerByJob;
    private final double[]   minSlackValueByJob;
    private final int[]      matchJobByWorker, matchWorkerByJob;
    private final int[]      parentWorkerByCommittedJob;
    private final boolean[]  committedWorkers;
    public ArrayList<Student> students;
    public ArrayList<Spot> spots;

    public static double findDist(Student student, Spot spot){
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

    public static double[][] genMatrix(ArrayList<Student> students, ArrayList<Spot> spots){
        double[][] matrix = new double[students.size()][spots.size()];
        for (int i = 0; i < students.size(); i++){
            for (int y = 0; y < spots.size(); y++){
                matrix[i][y] = findDist(students.get(i), spots.get(y));
            }
        }
        // for (double[] row : matrix){
        //     System.out.println(Arrays.toString(row));
        // }

        return matrix;
    }

    public Hungarian(ArrayList<Student> students, ArrayList<Spot> spots)
    {
        this.students = students;
        this.spots = spots;
        double[][] costMatrix = genMatrix(students, spots);
        this.dim = Math.max(costMatrix.length, costMatrix[0].length);
        this.rows = costMatrix.length;
        this.cols = costMatrix[0].length;
        this.costMatrix = new double[this.dim][this.dim];
        for (int w = 0; w < this.dim; w++)
        {
            if (w < costMatrix.length)
            {
                if (costMatrix[w].length != this.cols)
                {
                    throw new IllegalArgumentException("Irregular cost matrix");
                }
                this.costMatrix[w] = Arrays.copyOf(costMatrix[w], this.dim);
            }
            else
            {
                this.costMatrix[w] = new double[this.dim];
            }
        }
        labelByWorker = new double[this.dim];
        labelByJob = new double[this.dim];
        minSlackWorkerByJob = new int[this.dim];
        minSlackValueByJob = new double[this.dim];
        committedWorkers = new boolean[this.dim];
        parentWorkerByCommittedJob = new int[this.dim];
        matchJobByWorker = new int[this.dim];
        Arrays.fill(matchJobByWorker, -1);
        matchWorkerByJob = new int[this.dim];
        Arrays.fill(matchWorkerByJob, -1);
    }
 
    protected void computeInitialFeasibleSolution()
    {
        for (int j = 0; j < dim; j++)
        {
            labelByJob[j] = Double.POSITIVE_INFINITY;
        }
        for (int w = 0; w < dim; w++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (costMatrix[w][j] < labelByJob[j])
                {
                    labelByJob[j] = costMatrix[w][j];
                }
            }
        }
    }
 
    public int[] execute()
    {
        /*
         * Heuristics to improve performance: Reduce rows and columns by their
         * smallest element, compute an initial non-zero dual feasible solution
         * and
         * create a greedy matching from workers to jobs of the cost matrix.
         */
        reduce();
        computeInitialFeasibleSolution();
        greedyMatch();
        int w = fetchUnmatchedWorker();
        while (w < dim)
        {
            initializePhase(w);
            executePhase();
            w = fetchUnmatchedWorker();
        }
        int[] result = Arrays.copyOf(matchJobByWorker, rows);
        for (w = 0; w < result.length; w++)
        {
            if (result[w] >= cols)
            {
                result[w] = -1;
            }
        }
        return result;
    }
 
    protected void executePhase()
    {
        while (true)
        {
            int minSlackWorker = -1, minSlackJob = -1;
            double minSlackValue = Double.POSITIVE_INFINITY;
            for (int j = 0; j < dim; j++)
            {
                if (parentWorkerByCommittedJob[j] == -1)
                {
                    if (minSlackValueByJob[j] < minSlackValue)
                    {
                        minSlackValue = minSlackValueByJob[j];
                        minSlackWorker = minSlackWorkerByJob[j];
                        minSlackJob = j;
                    }
                }
            }
            if (minSlackValue > 0)
            {
                updateLabeling(minSlackValue);
            }
            parentWorkerByCommittedJob[minSlackJob] = minSlackWorker;
            if (matchWorkerByJob[minSlackJob] == -1)
            {
                /*
                 * An augmenting path has been found.
                 */
                int committedJob = minSlackJob;
                int parentWorker = parentWorkerByCommittedJob[committedJob];
                while (true)
                {
                    int temp = matchJobByWorker[parentWorker];
                    match(parentWorker, committedJob);
                    committedJob = temp;
                    if (committedJob == -1)
                    {
                        break;
                    }
                    parentWorker = parentWorkerByCommittedJob[committedJob];
                }
                return;
            }
            else
            {
                /*
                 * Update slack values since we increased the size of the
                 * committed
                 * workers set.
                 */
                int worker = matchWorkerByJob[minSlackJob];
                committedWorkers[worker] = true;
                for (int j = 0; j < dim; j++)
                {
                    if (parentWorkerByCommittedJob[j] == -1)
                    {
                        double slack = costMatrix[worker][j]
                                - labelByWorker[worker] - labelByJob[j];
                        if (minSlackValueByJob[j] > slack)
                        {
                            minSlackValueByJob[j] = slack;
                            minSlackWorkerByJob[j] = worker;
                        }
                    }
                }
            }
        }
    }
 
    protected int fetchUnmatchedWorker()
    {
        int w;
        for (w = 0; w < dim; w++)
        {
            if (matchJobByWorker[w] == -1)
            {
                break;
            }
        }
        return w;
    }
 
    protected void greedyMatch()
    {
        for (int w = 0; w < dim; w++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (matchJobByWorker[w] == -1
                        && matchWorkerByJob[j] == -1
                        && costMatrix[w][j] - labelByWorker[w] - labelByJob[j] == 0)
                {
                    match(w, j);
                }
            }
        }
    }
 
    protected void initializePhase(int w)
    {
        Arrays.fill(committedWorkers, false);
        Arrays.fill(parentWorkerByCommittedJob, -1);
        committedWorkers[w] = true;
        for (int j = 0; j < dim; j++)
        {
            minSlackValueByJob[j] = costMatrix[w][j] - labelByWorker[w]
                    - labelByJob[j];
            minSlackWorkerByJob[j] = w;
        }
    }
 
    protected void match(int w, int j)
    {
        matchJobByWorker[w] = j;
        matchWorkerByJob[j] = w;
    }
 
    protected void reduce()
    {
        for (int w = 0; w < dim; w++)
        {
            double min = Double.POSITIVE_INFINITY;
            for (int j = 0; j < dim; j++)
            {
                if (costMatrix[w][j] < min)
                {
                    min = costMatrix[w][j];
                }
            }
            for (int j = 0; j < dim; j++)
            {
                costMatrix[w][j] -= min;
            }
        }
        double[] min = new double[dim];
        for (int j = 0; j < dim; j++)
        {
            min[j] = Double.POSITIVE_INFINITY;
        }
        for (int w = 0; w < dim; w++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (costMatrix[w][j] < min[j])
                {
                    min[j] = costMatrix[w][j];
                }
            }
        }
        for (int w = 0; w < dim; w++)
        {
            for (int j = 0; j < dim; j++)
            {
                costMatrix[w][j] -= min[j];
            }
        }
    }
 
    protected void updateLabeling(double slack)
    {
        for (int w = 0; w < dim; w++)
        {
            if (committedWorkers[w])
            {
                labelByWorker[w] += slack;
            }
        }
        for (int j = 0; j < dim; j++)
        {
            if (parentWorkerByCommittedJob[j] != -1)
            {
                labelByJob[j] -= slack;
            }
            else
            {
                minSlackValueByJob[j] -= slack;
            }
        }
    }

    public static void printResult(int[] result, ArrayList<Student> students, ArrayList<Spot> spots){
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
    
        for (int i = 0; i < result.length; i++){
            Student student = students.get(i);
            Spot spot = spots.get(result[i]);
            double distance = findDist(student, spot);
            student.setSpot(spot);
            student.setCurrDist(distance);
            spot.setState(true);
        }

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

        // double avggrade1 = grade1dist/grade1num;
        // double avggrade2 = grade2dist/grade2num;
        // double avggrade3 = grade3dist/grade3num;
        // double avggrade4 = grade4dist/grade4num;

        System.out.println("Hungarian Algorithm");
        System.out.println(" ");

        System.out.println("Average Walking Distance of Student: " + ((grade1dist + grade2dist + grade3dist + grade4dist)/students.size()));

        // System.out.println("Average Walking Distance for Freshman: " + avggrade1);
        // System.out.println("Average Walking Distance for Sophomores: " + avggrade2);
        // System.out.println("Average Walking Distance for Juniors: " + avggrade3);
        // System.out.println("Average Walking Distance for Seniors: " + avggrade4);

        System.out.println(" ");

        //System.out.println("Grade of student with best parking spot: " + beststu.getGrade());
        //System.out.println("Walking Distance of student with best spot: " + beststu.getCurrDist());
        //System.out.println("Grade of student with worst parking spot: " + worststu.getGrade());
        //System.out.println("Walking Distance of student with worst spot: " + worststu.getCurrDist());
        // System.out.println(" ");
        // System.out.println("Smallest walking distance of Freshman: " + beststu1.getCurrDist());
        // System.out.println("Largest walking distance of Freshman: " + worststu1.getCurrDist());
        // System.out.println(" ");
        // System.out.println("Smallest walking distance of Sophomores: " + beststu2.getCurrDist());
        // System.out.println("Largest walking distance of Sophomores: " + worststu2.getCurrDist());
        // System.out.println(" ");
        // System.out.println("Smallest walking distance of Juniors: " + beststu3.getCurrDist());
        // System.out.println("Largest walking distance of Juniors: " + worststu3.getCurrDist());
        // System.out.println(" ");
        // System.out.println("Smallest walking distance of Seniors: " + beststu4.getCurrDist());
        // System.out.println("Largest walking distance of Seniors: " + worststu4.getCurrDist());
        // System.out.println(" ");

        // String filename = "hungarian.csv";

        // try (FileWriter fileWriter = new FileWriter(filename)) {
        //     for (Student student : students) {
        //         fileWriter.append(String.valueOf(student.getID()));
        //         fileWriter.append(",");
        //         fileWriter.append(String.valueOf(student.getGrade()));
        //         fileWriter.append(",");
        //         fileWriter.append(String.valueOf(student.getCurrDist()));
        //         fileWriter.append("\n");
                
        //     }
        // } 
        // catch (IOException e) {
        //     System.out.println("An error occurred while writing to the file: " + e.getMessage());
        // }

    }
}