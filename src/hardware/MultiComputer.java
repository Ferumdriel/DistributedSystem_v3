package hardware;

import math.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Binio on 2017-01-24.
 */
public class MultiComputer extends AbstractComputer{
    private MultiComputer parent;


    //MASTER MASTER
    public MultiComputer(int id){
        this.id = id;
        parent = null;
        sonsList = new ArrayList<>();
    }

    //MASTER'S SONS AND SONS
    public MultiComputer(int id, MultiComputer mc){
        this.id = id;
        parent = mc;
        sonsList = new ArrayList<>();
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

    @Override
    public void run() {

    }
}
