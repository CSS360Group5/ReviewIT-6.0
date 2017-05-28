package modelTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.UserController;
import model.UserProfile;

/**
 * This class tests the constructor for the UserController class
 * @author Dongsheng Han
 * @version 5/27/2017
 */
public class UserControllerTest {

	
	private UserController testUserController;
	
	@Before
	public void setUp() {
		testUserController = new UserController();
	}


	/**
	 * Tests that the CurrentUser is set properly.
	 */
    @Test	
	public void testsetCurrentUser() { 
    	String testUserID = "testUserID";
    	String testUserName = "testUserName";
    	String testUserID1 = "testUserID1";
    	String testUserName1 = "testUserName1";
    	testUserController.createNewUserProfile(testUserID, testUserName);
    	testUserController.createNewUserProfile(testUserID1, testUserName1);
    	testUserController.setCurrentUser(testUserID);
    	assertEquals(testUserController.getCurrentUser().getUserID(),testUserID);
    }
    
    /**
	 * Tests that the User profile is add properly.
	 * !!!It does not filter out user with same ID
	 */
    @Test	
	public void testcreateNewUserProfile() { 
    	String testUserID = "testUserID";
    	String testUserName = "testUserName";
    	String testUserID1 = "testUserID1";
    	String testUserName1 = "testUserName1";
    	testUserController.createNewUserProfile(testUserID, testUserName);
    	testUserController.createNewUserProfile(testUserID1, testUserName1);
    	assertEquals(testUserController.getUser(testUserID),new UserProfile(testUserID, testUserName));
    	assertEquals(testUserController.getUser(testUserID1),new UserProfile(testUserID1, testUserName1));
    }
    
    /**
	 * Tests if the method can get User profile that is already added to User Profile.
	 */
    @Test	
	public void testgetUser() { 
    	String testUserID = "testUserID";
    	String testUserName = "testUserName";
    	String testUserID1 = "testUserID1";
    	String testUserName1 = "testUserName1";
    	String testUserID2 = "not added testUserID2";
    	String testUserName2 = "not added testUserName2";
    	testUserController.createNewUserProfile(testUserID, testUserName);
    	testUserController.createNewUserProfile(testUserID1, testUserName1);
    	assertEquals(testUserController.getUser(testUserID),new UserProfile(testUserID, testUserName));
    	assertEquals(testUserController.getUser(testUserID1),new UserProfile(testUserID1, testUserName1));
    	assertNotEquals(testUserController.getUser(testUserID2),new UserProfile(testUserID2, testUserName2));
    }

}
