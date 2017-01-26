package math.tests;

import hardware.MultiComputer;
import math.MathSolver;
import math.Matrix;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Binio on 2017-01-26.
 */
public class MathSolverTest {

    @Test
    public void solveMatrix() throws Exception {
        MultiComputer mc = new MultiComputer(1);
        ExampleMatrix em = new ExampleMatrix();
        Matrix m = em.getM();
        mc.setMatrices(m,m);
        MathSolver solver = new MathSolver(mc);
        solver.addMatrixes(m,m);
        solver.solveMatrix();
        int expectedValue = 30;
        assertEquals(expectedValue,mc.getFinalM().getMatrix()[0][0]);
    }

}