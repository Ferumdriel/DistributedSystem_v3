package hardware;

import math.MathSolver;
import math.MatrixDivider;

import java.util.List;

/**
 * Created by Binio on 2017-01-21.
 */
public abstract class AbstractComputer {
    public static final int PROCESSORS_AMOUNT = 2;


    protected int id;
    protected MathSolver solver = new MathSolver(this);
//    protected MatrixDivider divider = new MatrixDivider();
    protected Processor[] processors = new Processor[PROCESSORS_AMOUNT];
    protected boolean readyToExecute = false;
    protected List<MultiComputer> sonsList;

    public boolean isReadyToExecute(){
        if(solver.getM1().getColumns()==1 || sonsList.isEmpty()){
            return true;
        }
        else return false;
    }

    public MathSolver getSolver() {
        return solver;
    }
}
