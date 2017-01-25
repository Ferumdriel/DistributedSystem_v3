package hardware;

import math.MathSolver;
import math.Matrix;
import math.MatrixDivider;

import java.util.List;

/**
 * Created by Binio on 2017-01-21.
 */
public abstract class AbstractComputer implements Runnable{
    public static final int PROCESSORS_AMOUNT = 2;


    protected int id;
    protected MathSolver solver = new MathSolver(this);
//    protected MatrixDivider divider = new MatrixDivider();
    protected Processor[] processors = new Processor[PROCESSORS_AMOUNT];
    protected boolean readyToExecute = false;
    protected boolean matricesSent = false;

    protected List<MultiComputer> sonsList;
    protected Matrix m1;
    protected Matrix m2;

    public boolean isReadyToExecute(){
        if(solver.getM1().getColumns()==1 || sonsList.isEmpty()){
            return true;
        }
        else return false;
    }

    public MathSolver getSolver() {
        return solver;
    }

    public void setMatrices(Matrix m1, Matrix m2){
        this.m1 = m1;
        this.m2 = m2;
        solver.addMatrixes(m1,m2);
    }

    private void sendMatrices(){
        int k = 0;
        for(MultiComputer m: sonsList){
            m.setMatrices(solver.getSplitM1().get(k++), solver.getM2());
        }
        matricesSent = true;
    }

    private void getInfoAboutSentMatrices(){
        for(AbstractComputer m: sonsList){
            System.out.println("Son: " + m.id);
            System.out.println("M1: ");
            m.m1.display();
            System.out.println("M2: ");
            m.m2.display();
        }
    }



    public void tick(){
        if(!solver.isMatricesSplit()){
            solver.splitMatrix(sonsList.size());
        }
        if(!matricesSent){
            sendMatrices();
            getInfoAboutSentMatrices();
        }


    }
}
