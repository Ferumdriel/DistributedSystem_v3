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
    public void splitMatrix() throws Exception {

    }

    public void solveMatrix() throws Exception {
        MultiComputer mc = new MultiComputer(1);
        ExampleMatrix em = new ExampleMatrix();
        Matrix m = em.getM();
        MathSolver solver = new MathSolver(mc);
        solver.solveMatrix();
        int expectedValue = 55;
        assertEquals(expectedValue,mc.getFinalM().getMatrix()[1][2]);
    }

}