package math.tests;

import hardware.computer.MultiComputer;
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
        mc.getDivider().addMatrices(m,m);
        mc.getSolver().addMatrixes(m,m);
        mc.getDivider().divide();
        mc.getSolver().solveMatrix();

        int expectedValue = 55;
        assertEquals(expectedValue,mc.getFinalM().getMatrix()[1][1]);
    }

}