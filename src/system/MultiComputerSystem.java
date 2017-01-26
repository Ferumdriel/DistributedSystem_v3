package system;


import hardware.AbstractComputer;
import hardware.MultiComputer;
import math.Matrix;

import java.util.List;

/**
 * Created by Binio on 2017-01-24.
 */
public class MultiComputerSystem implements Runnable{

    public static final int MASTER_SONS = 2;
    public static final int MASTER_SONS_SONS = 3;
    public static final int MASTER_SONS_SONS_SONS = 4;

    MultiComputer master;
    List<MultiComputer> allConnected;
    private boolean systemGenerated = false;
    private boolean running = false;
    private Thread thread;
    private boolean taskCompleted = false;

    Matrix m1;
    Matrix m2;

    private void sendMatricesToMaster(){
        master.setMatrices(m1,m2);
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
        //sons of master's sons
        for(MultiComputer m: master.getSonsList()){
            for(int i = 0; i < MASTER_SONS_SONS; i++){
                m.createSon(tmpID++);
            }
        }
        //sons of sons of master's sons
        for(MultiComputer m: master.getSonsList()) {
            for (MultiComputer k : m.getSonsList()) {
                for (int i = 0; i < MASTER_SONS_SONS_SONS; i++) {
                    k.createSon(tmpID++);
                }
            }
        }
    }

    private void generateMatrices(){
        int a = 10, b = 5;

        int[][] test = new int[a][b];
        for(int i = 0; i < a; i++){
            for(int j  = 0; j < b; j++){
                test[i][j] = i + j;
            }
        }

        int[][] test2 = new int[b][a];
        for(int i = 0; i < b; i++){
            for(int j  = 0; j < a; j++){
                test2[i][j] = i + j;
            }
        }

        Matrix tmpM1 = new Matrix(test);
        System.out.println("M1: ");
        tmpM1.display();
        Matrix tmpM2 = new Matrix(test2);
        System.out.println("M2: ");
        tmpM2.display();
        this.m1 = tmpM1;
        this.m2 = tmpM2;
    }

    @Override
    public void run() {
        generateSystem();

        while(!taskCompleted){
            master.start();
        }

        //in case it didn't stop earlier
        stop();
    }

    public void start(){
        if(running){
            return;
        }
            running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop(){
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
}
