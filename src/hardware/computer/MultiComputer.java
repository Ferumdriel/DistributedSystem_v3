package hardware.computer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Binio on 2017-01-24.
 */
public class MultiComputer extends AbstractComputer {


    //MASTER MASTER
    public MultiComputer(int id){
        this.id = id;
        parent = null;
        sonsList = new ArrayList<>();
        generateProcessors();
    }

    //MASTER'S SONS AND SONS
    public MultiComputer(int id, MultiComputer mc){
        this.id = id;
        parent = mc;
        sonsList = new ArrayList<>();
        generateProcessors();
    }



    public void createSon(int id){
        MultiComputer mc = new MultiComputer(id, this);
        sonsList.add(mc);
            System.out.println("Son: " + id + " of: " + mc.parent.id + " has been created");
    }



    public List<MultiComputer> getSonsList() {
        return sonsList;
    }

    public String toString(){
        return getClass().getSimpleName() + "[" + id + "]\n";
    }

    public void listTree(){
        if(parent==null){
            System.out.println("Master computer");
        }
        System.out.println(this);
        if(!sonsList.isEmpty()){
            listTree(sonsList);
        }
    }

    public void listTree(List<MultiComputer> list){

    }

//    public void run(){
//        if(!sonsList.isEmpty()){
//            for(MultiComputer m: sonsList){
//                m.start();
//            }
//        }
//        tick();
//    }

    @Override
    public synchronized void run() {
        if (!sonsList.isEmpty()) {
            for(MultiComputer m: sonsList){
                m.start();
            }
        }
        while(!jobIsDone()) {
            tick();
        }
        System.out.println("Task finished for computer: " + id);
//        stop();
    }

    public synchronized void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
        start();
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
}
