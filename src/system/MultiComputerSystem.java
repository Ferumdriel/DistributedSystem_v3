package system;

import hardware.MultiComputer;

import java.util.List;

/**
 * Created by Binio on 2017-01-24.
 */
public class MultiComputerSystem {

    List<MultiComputer> allConnected;









    public void generateSystem(int masterSons, int sonsSons, int sonsSonsSons){
        int tmpID = 1;
        MultiComputer master = new MultiComputer(tmpID);
        tmpID++;
        //master's sons
        for(int i = 0; i < masterSons; i++){
            master.createSon(tmpID++);
        }
        //sons of master's sons
        for(MultiComputer m: master.getSonsList()){
            for(int i = 0; i < sonsSons; i++){
                m.createSon(tmpID++);
            }
        }
        //sons of sons of master's sons
        for(MultiComputer m: master.getSonsList()) {
            for (MultiComputer k : m.getSonsList()) {
                for (int i = 0; i < sonsSonsSons; i++) {
                    k.createSon(tmpID++);
                }
            }
        }
    }
}
