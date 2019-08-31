/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // tests
    
    /**
     * test the constructor of the GraphPoet class
     * 
     * @throws IOException cannot read from given file
     */
    @Test
    public void testConstructor() throws IOException {
    	GraphPoet graphPoet = new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
		assertEquals("this==>is\n" + 
				"is==>a\n" + 
				"a==>test\n" + 
				"test==>of\n" + 
				"of==>the\n" + 
				"the==>mugar\n" + 
				"mugar==>omni\n" + 
				"omni==>theater\n" + 
				"theater==>sound\n" + 
				"sound==>system.", graphPoet.toString());
    }
    
    /**
     * test the poem method
     * 
     * @throws IOException cannot read from file
     */
    @Test
    public void testPoem() throws IOException {
    	GraphPoet graphPoet = new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
    	assertEquals("This is a test", graphPoet.poem("This a test"));
    }
    
}
