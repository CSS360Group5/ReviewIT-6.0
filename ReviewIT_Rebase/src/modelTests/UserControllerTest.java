package modelTests;

import static org.junit.Assert.*;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Manuscript;
import model.Role;
import model.UserController;
import model.UserProfile;

/**
 * This class tests the constructor for the UserController class
 * @author Dongsheng Han
 * @version 5/27/2017
 */
public class UserControllerTest {

	
	private UserController testUserController;
	String testUserID,testUserName,testUserID1,testUserName1,testUserID2,testUserName2;
	
	@Before
	public void setUp() {
		testUserController = new UserController();
		testUserID = "testUserID";
    	testUserName = "testUserName";
    	testUserID1 = "testUserID1";
    	testUserName1 = "testUserName1";
    	testUserID2 = "not added testUserID2";
    	testUserName2 = "not added testUserName2";
    	testUserController.createNewUserProfile(testUserID, testUserName);
    	testUserController.createNewUserProfile(testUserID1, testUserName1);
	}


	/**
	 * Tests that the CurrentUser is set properly.
	 */
    @Test	
	public void testsetCurrentUser() { 
    	testUserController.setCurrentUser(testUserID);
    	assertEquals(testUserController.getCurrentUser().getUserID(),testUserID);
    }
    
    /**
	 * Tests that the User profile is add properly.
	 * !!!It does not filter out user with same ID
	 */
    @Test	
	public void testcreateNewUserProfile() { 
    	if(testUserController.containsUserProfile(testUserID)){
    		assertEquals(testUserController.getUser(testUserID),new UserProfile(testUserID, testUserName));
    	}
    	if(testUserController.containsUserProfile(testUserID1)){
    		assertEquals(testUserController.getUser(testUserID1),new UserProfile(testUserID1, testUserName1));
    	}
    	
    }
    
    /**
	 * Tests if the method can get User profile that is already added to User Profile.
	 */
    @Test	
	public void testgetUser() { 
    	if(testUserController.containsUserProfile(testUserID)){
    		assertEquals(testUserController.getUser(testUserID),new UserProfile(testUserID, testUserName));
    	}
    	if(testUserController.containsUserProfile(testUserID1)){
    		assertEquals(testUserController.getUser(testUserID1),new UserProfile(testUserID1, testUserName1));
    	}
    	assertNotEquals(testUserController.getUser(testUserID2),new UserProfile(testUserID2, testUserName2));
    }
    
    /**
	 * Tests if the method can find if the user is already added to User Profile.
	 */
    @Test	
	public void testcontainsUserProfile() { 
    	assertTrue(testUserController.containsUserProfile(testUserID));
    	assertTrue(testUserController.containsUserProfile(testUserID1));
    	assertFalse(testUserController.containsUserProfile(testUserID2));
    }
    
    /**
	 * Tests if the method return all the user's ID is already added to User Profile.
	 */
    @Test	
	public void testgetAllUserIDs() { 
    	List<String> testallUserIDs = new ArrayList<>();
    	testallUserIDs.add(testUserID);
    	testallUserIDs.add(testUserID1);
    	assertEquals(testUserController.getAllUserIDs(),testallUserIDs);
    }
    
    /**
   	 * Tests if the method return all the user's name is already added to User Profile.
   	 */
    @Test	
   	public void testgetAllUserNames() { 
       	List<String> testallUserNames = new ArrayList<>();
       	testallUserNames.add(testUserName);
       	testallUserNames.add(testUserName1);
       	assertEquals(testUserController.getAllUserNames(), testallUserNames);
    }

    /**
   	 * Tests if the method return all the user's with same role.
   	 */
    @Test	
   	public void testgetUsersByRole() { 
       	List<UserProfile> testusersWithTheRoles = new ArrayList<>();
       	testUserController.getUser(testUserID).assignRole("theConferenceID", Role.AUTHOR);
       	testUserController.getUser(testUserID1).assignRole("theConferenceID", Role.AUTHOR);
       	testusersWithTheRoles.add(testUserController.getUser(testUserID));
       	testusersWithTheRoles.add(testUserController.getUser(testUserID1));
       	assertEquals(testUserController.getUsersByRole(Role.AUTHOR, "theConferenceID"), testusersWithTheRoles);
    }
    
    /**
   	 * Tests if the method return all the user's name is already added to User Profile.
   	 */
    @Test	
   	public void testgetEligibleReviewers() { 
    	testUserController.createNewUserProfile(testUserID2, testUserName2);
       	testUserController.getUser(testUserID2).assignRole("theConferenceID", Role.REVIEWER);
    	testUserController.getUser(testUserID).assignRole("theConferenceID", Role.REVIEWER);
       	testUserController.getUser(testUserID1).assignRole("theConferenceID", Role.REVIEWER);
       	List<String> testAuthors = new ArrayList<> ();
       	testAuthors.add(testUserName1);
       	ZonedDateTime testSubmissionDate = ZonedDateTime.now();
       	File testFile = new File("SomePath");
       	Manuscript testManuscript = new Manuscript("Title", testUserName, testAuthors, testSubmissionDate, testFile);
       	List<UserProfile> eligibleReviewers = new ArrayList<> ();
       	eligibleReviewers.add(testUserController.getUser(testUserID2));
       	List<UserProfile> testeligibleReviewers = testUserController.getEligibleReviewers("theConferenceID", testManuscript);
       	assertEquals(testeligibleReviewers, eligibleReviewers);
    }


}

