package com.steelypip.powerups;

//package com.mycompany;
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

import org.junit.internal.TextListener;


@RunWith(ClasspathSuite.class)
@ClasspathSuite.IncludeJars(true)
public class RunAllTests {

	//	This is optional - included so I can run from the command line (and debug ant's build.xml).
    public static void main(String args[]) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        junit.run(RunAllTests.class);
    }
	
}