import org.junit.*;
import static org.junit.Assert.*;

import java.io.PrintStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

public class TestM {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /*
     * This satisfies node coverage requirements.
     * The function is called with i == 0, which covers nodes [0,1,2].
     * Then, separate calls pass a string of lengths 0, 1, and 2
     * which covers nodes [3,4,5,6].
     * Additionally, it is called with strings of length > 0
     * and of length <= 0, covering nodes [7,8,9,10].
     */
    @Test
    public void testNodeCoverage() {
        M obj = new M();

        obj.m("", 0);
        obj.m("a", 0);
        obj.m("ab", 0);

        assertEquals(outContent.toString(), "zero\na\nb\n");
    }

    /*
     * This satisfies edge coverage requirements.
     * It adds on to the tests for node coverage with a call to
     * the function with a string length 3 and i == 1.
     * This allows the additional edges [0,2] and [2,6] to
     * be covered which were not covered in just the node coverage
     * test.
     */
    @Test
    public void testEdgeCoverage() {
        M obj = new M();

        obj.m("", 0);
        obj.m("a", 0);
        obj.m("ab", 0);
        obj.m("abc", 1);

        assertEquals(outContent.toString(), "zero\na\nb\nb\n");
    }

    /*
     * In this case, it is impossible to satisfy
     * coverage #3. A test requirement set that satisfies
     * Edge Pair Coverage for this CFG would also satisfy
     * Prime Path Coverage. There is no test set requirement
     * that satisfies EPC but not PPC. You can prove this
     * by working backwards from the PPC TR below - removing
     * any of the test paths would make the TR fail to satisfy
     * both PPC and EPC.
     */
    @Test
    public void testEdgePairCoverage() {
    
    }
    
    /*
     * This satisfies prime path coverage.
     * There are 8 prime paths identified in Part B
     * (see the submission PDF) - each call to the method
     * executes one of those prime paths.
     */
    @Test
    public void testPrimePathCoverage() {
        M obj = new M();

        obj.m("" , 0);
        obj.m("a" , 0);
        obj.m("ab" , 0);
        obj.m("abc" , 0);
        obj.m("" , 1);
        obj.m("a" , 1);
        obj.m("ab" , 1);
        obj.m("abc" , 1);

        assertEquals(outContent.toString(), "zero\na\nb\nb\nzero\na\nb\nb\n");
    }
}

class M {
	public static void main(String [] argv){
		M obj = new M();
		if (argv.length > 0)
			obj.m(argv[0], argv.length);
	}
	
	public void m(String arg, int i) {
		int q = 1;
		A o = null;
		Impossible nothing = new Impossible();
		if (i == 0)
			q = 4;
		q++;
		switch (arg.length()) {
			case 0: q /= 2; break;
			case 1: o = new A(); new B(); q = 25; break;
			case 2: o = new A(); q = q * 100;
			default: o = new B(); break; 
		}
		if (arg.length() > 0) {
			o.m();
		} else {
			System.out.println("zero");
		}
		nothing.happened();
	}
}

class A {
	public void m() { 
		System.out.println("a");
	}
}

class B extends A {
	public void m() { 
		System.out.println("b");
	}
}

class Impossible{
	public void happened() {
		// "2b||!2b?", whatever the answer nothing happens here
	}
}
