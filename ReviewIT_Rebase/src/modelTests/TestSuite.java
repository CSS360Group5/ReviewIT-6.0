package modelTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	ConferenceAuthorSubmissionTests.class,
	ConferenceControllerTest.class,
	ConferenceSubprogramChairAssignReviewerTests.class,
	ConferenceTest.class,
	ManuscriptTest.class
})

/**
 * This class will run all of the unit test cases that are included
 * in the Suite.SuiteClasses annotation above, and is to be called ONLY by
 * the TestRunner class.
 * @author Lorenzo Pacis
 * @version 5.3.2017
 */
public class TestSuite {

}
