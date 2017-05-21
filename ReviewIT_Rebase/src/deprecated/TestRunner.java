package deprecated;

import junit.framework.TestSuite;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This class will run the TestSuite and print the string message of the failure tests, this class is
 * not to be modified to include any tests or console print outs than what it contains as of
 * 5.3.2017.
 * @author Lorenzo Pacis
 * @version 5.3.2017
 */
public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(TestSuite.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
   }
}  