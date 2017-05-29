package modelTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Role;
import model.UserProfileStateManager;
import model.UserProfile;

/**
 * This class tests the constructor for the UserController class
 * @author Dongsheng Han
 * @version 5/27/2017
 */
public class UserControllerTest {

	
	private UserProfileStateManager testUserController;
	String testUserID,testUserName,testUserID1,testUserName1,testUserID2,testUserName2;
	
	@Before
	public void setUp() {
		UserProfileStateManager.destroy();
		testUserController = UserProfileStateManager.getInstance();
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
	public void setCurrentUser_CurrentSetTotestUserID_SettingSuccess() { 
    	testUserController.setCurrentUser(testUserID);
    	assertEquals(testUserController.getCurrentUser().getUserID(),testUserID);
    }
    
    /**
	 * Tests that the User profile is add properly.
	 * @TODO It does not filter out user with same ID
	 */
    @Test	
	public void createNewUserProfile_AddTwoNewUser_FilterOutUserWithSameID() { 
    	if(testUserController.containsUserProfile(testUserID)){
    		assertEquals(testUserController.getUser(testUserID),new UserProfile(testUserID, testUserName));
    		assertNotEquals(testUserController.getUser(testUserID1),new UserProfile(testUserID, testUserName1));
    	}
    	if(testUserController.containsUserProfile(testUserID1)){
    		assertEquals(testUserController.getUser(testUserID1),new UserProfile(testUserID1, testUserName1));
    		assertNotEquals(testUserController.getUser(testUserID1),new UserProfile(testUserID1, testUserName));
    	}
    	
    }
    
    /**
	 * Tests if the method can get User profile that is already added to User Profile.
	 */
    @Test	
	public void getUser_getUserWithUniqueID_RetuenExpectedID() { 
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
	public void containsUserProfile_GivenID_RetuenTrueWhenContain() { 
    	assertTrue(testUserController.containsUserProfile(testUserID));
    	assertTrue(testUserController.containsUserProfile(testUserID1));
    	assertFalse(testUserController.containsUserProfile(testUserID2));
    }
    
    /**
	 * Tests if the method return all the user's ID is already added to User Profile.
	 */
    @Test	
	public void getAllUserIDs_RequestAllID_AllIDInAListOfString() { 
    	List<String> testallUserIDs = new ArrayList<>();
    	testallUserIDs.add(testUserID);
    	testallUserIDs.add(testUserID1);
    	assertEquals(testUserController.getAllUserIDs(),testallUserIDs);
    }
    
    /**
   	 * Tests if the method return all the user's name is already added to User Profile.
   	 */
    @Test	
   	public void getAllUserNames_RequestAllName_AllNameInAListOfString() { 
       	List<String> testallUserNames = new ArrayList<>();
       	testallUserNames.add(testUserName);
       	testallUserNames.add(testUserName1);
       	assertEquals(testUserController.getAllUserNames(), testallUserNames);
    }

    /**
   	 * Tests if the method return all the user's with same role.
   	 */
    @Test	
   	public void getUsersByRole_RequestAllUserWithSpecificRole_AListOfUserProfileWithSameRole() { 
       	List<UserProfile> testusersWithTheRoles = new ArrayList<>();
       	testUserController.getUser(testUserID).assignRole("theConferenceID", Role.AUTHOR);
       	testUserController.getUser(testUserID1).assignRole("theConferenceID", Role.AUTHOR);
       	testusersWithTheRoles.add(testUserController.getUser(testUserID));
       	testusersWithTheRoles.add(testUserController.getUser(testUserID1));
       	assertEquals(testUserController.getUsersByRole(Role.AUTHOR, "theConferenceID"), testusersWithTheRoles);
    }


}
