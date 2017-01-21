package hardware;

import math.MathSolver;

/**
 * Created by Binio on 2017-01-21.
 */
public abstract class AbstractComputer {
    public static int processorsAmount = 8;


    protected int id;
    protected MathSolver solver = new MathSolver(this);
    protected Processor[] processors = new Processor[processorsAmount];

}
