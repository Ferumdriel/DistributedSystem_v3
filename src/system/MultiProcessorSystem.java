package system;

import hardware.computer.MultiComputer;
import math.Matrix;

import java.util.List;

/**
 * Created by Binio on 2017-02-02.
 */
public class MultiProcessorSystem implements Runnable{

    public static final int MASTER_SONS = Math.max(SystemRequirements.MASTER_SONS,1)*Math.max(SystemRequirements.MASTER_SONS_SONS,1)* Math.max(SystemRequirements.MASTER_SONS_SONS_SONS,1);


    MultiComputer master;
    List<MultiComputer> allConnected;
    private boolean systemGenerated = false;
    private boolean running = false;
    private Thread thread;
    private boolean taskCompleted = false;
    private long timer;
    private double executeTimer;

    Matrix m1;
    Matrix m2;

    private void sendMatricesToMaster(){
        master.setMatrices(m1,m2);
        master.getDivider().addMatrices(m1,m2);
    }

    private void generateSystem(){
        generateComputers();
        generateMatrices();
        sendMatricesToMaster();
        systemGenerated = true;
    }

    private void generateComputers(){
        int tmpID = 1;
        MultiComputer master = new MultiComputer(tmpID);
        this.master = master;
        tmpID++;
        //master's sons
        for(int i = 0; i < MASTER_SONS; i++){
            master.createSon(tmpID++);
        }
    }

//    private void sendMatricesToMaster(){
//        master.setMatrices(m1,m2);
//    }

    private void generateMatrices(){
        int a = SystemRequirements.a, b = SystemRequirements.b;

        int[][] test = new int[a][b];
        for(int i = 0; i < a; i++){
            for(int j  = 0; j < b; j++){
                test[i][j] = i + j;
            }
        }
    //
        int[][] test2 = new int[b][a];
        for(int i = 0; i < b; i++){
            for(int j  = 0; j < a; j++){
                test2[i][j] = i + j;
            }
        }

        Matrix tmpM1 = new Matrix(test);
//        System.out.println("M1: ");
//        tmpM1.display();
        Matrix tmpM2 = new Matrix(test2);
//        System.out.println("M2: ");
//        tmpM2.display();
        this.m1 = tmpM1;
        this.m2 = tmpM2;
    }

    @Override
    public synchronized void run() {
        System.out.println("-------------------------------SYSTEM WIELOPROCESOROWY--------------------------\n\n\n\n\n\n");
        generateSystem();

        timer = System.nanoTime();
        while(!taskCompleted){
            if(systemGenerated){
                master.start();
                if(master.hasFinalResult()){
                    taskCompleted = true;
                    System.out.println("Koniec obliczen dla wieloprocesorow");
//                    master.getFinalM().display();
                }
            }
        }
        executeTimer = ((System.nanoTime() - timer)/Math.pow(10,9));
        System.out.printf("%40s%10fs\n","Czas na wykonanie zadania dla systemu wieloprocesorow:",executeTimer);

        //in case it didn't stop earlier
//        stop();
    }

    public synchronized void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running) {
            return;
        }
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public MultiComputer getMaster() {
        return master;
    }

    public void displayTimeSpentOnExecution(){
        System.out.printf("%40s%10fs\n","Czas na wykonanie zadania dla systemu wieloprocesorow:",executeTimer);
    }

    public void displayFinalMatrix(){
        master.getFinalM().display();
    }
}
