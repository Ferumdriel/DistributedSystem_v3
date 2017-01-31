package hardware.computer;

import hardware.computer.MultiComputer;
import math.tests.ExampleMatrix;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Binio on 2017-01-31.
 */
public class MultiComputerTest{


    @Test
    public void combineMatrices() throws Exception {
        ExampleMatrix m = new ExampleMatrix();
        ExampleMatrix m1 = new ExampleMatrix(5);
        MultiComputer mc = new MultiComputer(1);

        mc.getSonsResults().add(m.getM());
        mc.getSonsResults().add(m1.getM());

        mc.combineMatrices();
        int expectedValue = 2;
        assertEquals(expectedValue, mc.getFinalM().getMatrix()[5][2]);
    }

    @Test
    public void divide() throws Exception {

    }

    @Test
    public void calculate() throws Exception {

    }

    @Test
    public void sendToParent() throws Exception {
    }

}