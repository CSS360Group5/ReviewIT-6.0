package modelTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import model.Conference;
import model.ConferenceController;

/**
 * @author Daniel
 * @version 2.0
 */
public class ConferenceControllerTest {
	private ZonedDateTime timeNow = ZonedDateTime.now();
	private ZonedDateTime timeOther;
	private ZonedDateTime time3;
	private Conference conference1 = new Conference("The first Conference", timeNow);
	private Conference conference2;
	private Conference conference3;
	private ConferenceController controller;



	/**
	 * Constructor that sets up the class.
	 */
	public ConferenceControllerTest() {
		timeOther = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
		time3 = ZonedDateTime.now(ZoneId.of("Europe/London"));
		conference2 = new Conference("The second Conference", timeOther);
		conference3 = new Conference("The third Conference", time3);
	}

	// [MethodName_StateUnderTest_ExpectedBehavior]

	/**
	 * Testing attempt to getCurrentConference() when no current Conference is set.
	 */
	@Test
	public void getCurrentConference_CurrentConferenceNotInitiated_ReturnsNull() {
		controller = new ConferenceController();
		assertNull("Conference Controller not initiated", controller.getCurrentConference());
	}

	/**
	 * Testing attempt to getCurrentConference() when no current Conference is set.
	 */
	@Test
	public void setCurrentConference_SettingAConference_ReturnsConference() {
		controller = new ConferenceController();
		controller.setCurrentConference(conference1);
		assertEquals("Didnt return correct Conference.", controller.getCurrentConference(), conference1);
	}

	/**
	 * Testing attempt to getCurrentConference() when no current Conference is set.
	 */
	@Test
	public void setCurrentConference_ChangeCurrentConference_ReturnsNewConference() {
		controller = new ConferenceController();
		controller.addConference(conference2);
		controller.setCurrentConference(conference1);
		assertEquals("Didnt return correct Conference.", controller.getCurrentConference(), conference1);
		controller.setCurrentConference(conference2);
		assertEquals("Didnt return correct Conference.", controller.getCurrentConference(), conference2);
	}
	/**
	 * Testing attempt to getCurrentConference() when no current Conference is set.
	 * @throws java.lang.IllegalArgumentException - throws exception
	 */
	@Test (expected = IllegalArgumentException.class)
	public void setCurrentConference_passInNullConference_ThrowsException() throws IllegalArgumentException {
		controller = new ConferenceController();
		controller.setCurrentConference(null);
	}

	/**
	 * Testing the setCurrentConference() method with Integer Parameter.
	 */
	@Test
	public void setCurrentConference_IntegerRepresentationOfConference_ReturnsSpecifiedConferenceInArrayList() {
		controller = new ConferenceController();
		controller.addConference(conference1);
		controller.setCurrentConference(0);
		assertEquals("Didnt return correct Conference.", controller.getCurrentConference(), conference1);
	}

	/**
	 * Testing attempt to setCurrentConference() with an out of bounds int parameter.
	 * @throws java.lang.IllegalArgumentException - throws exception
	 */
	@Test (expected = IndexOutOfBoundsException.class)
	public void setCurrentConference_passInOutOfBoundsInt_ThrowsException() throws IndexOutOfBoundsException {
		controller = new ConferenceController();
		controller.setCurrentConference(0);
		controller.setCurrentConference(100);
	}

	/**
	 * Testing attempt to getConference() with out of bounds int.
	 * @throws java.lang.IllegalArgumentException - throws exception
	 */
	@Test (expected = IndexOutOfBoundsException.class)
	public void getConference_passTooSmallInt_ThrowsException() throws IndexOutOfBoundsException {
		controller = new ConferenceController();
		controller.addConference(conference1);
		controller.getConference(-1);
	}

	/**
	 * Testing attempt to getConference() with out of bounds int.
	 * @throws java.lang.IllegalArgumentException - throws exception
	 */
	@Test (expected = IndexOutOfBoundsException.class)
	public void getConference_passInTooLargeInt_ThrowsException() throws IndexOutOfBoundsException {
		controller = new ConferenceController();
		controller.addConference(conference1);
		controller.getConference(100);
	}

	/**
	 * Testing attempt to getCurrentConference() when no current Conference is set.
	 * @throws java.lang.IllegalArgumentException - throws exception
	 */
	@Test
	public void getConference_passInInt_GetConferenceAtRequestedLocation() {
		controller = new ConferenceController();
		controller.addConference(conference1);
		controller.addConference(conference2);
		assertEquals("Returned Wrong Conference",controller.getConference(0), conference1);
		assertEquals("Returned Wrong Conference",controller.getConference(1), conference2);
	}

	/**
	 * Testing attempt to create a new conference without a proper name.
	 * @throws IllegalArgumentException when
	 */
	@Test (expected = IllegalArgumentException.class)
	public void createNewConference_StringNameIsEmpty_ThrowsException() throws IllegalArgumentException {
		controller = new ConferenceController();
		controller.createNewConference("", timeNow);
	}

	/**
	 * Testing attempt to create a new conference without a proper name.
	 * @throws IllegalArgumentException when conference name is a null
	 */
	@Test (expected = NullPointerException.class)
	public void createNewConference_StringNameIsNull_ThrowsException() throws NullPointerException {
		controller = new ConferenceController();
		controller.createNewConference(null, timeNow);
	}

	/**
	 * Testing attempt to create a new conference.
	 */
	@Test
	public void createNewConference_ProperNameAndTime_ConferenceCreatedProperly() {
		controller = new ConferenceController();
		controller.createNewConference("The New Conference", timeNow);
		assertEquals("Conference not created Properly.", "The New Conference", controller.getConference(0).getMyConferenceName());
	}

	/**
	 * Testing attempt to create a add conference that is null.
	 * @throws IllegalArgumentException when conference is null
	 */
	@Test (expected = NullPointerException.class)
	public void addConference_PassInNullConference_ThrowsException() throws NullPointerException {
		controller = new ConferenceController();
		controller.addConference(null);
	}

	/**
	 * Testing attempt to create a add conference that is null.
	 * @throws IllegalArgumentException when conference is null
	 */
	@Test
	public void addConference_LegitConfernce_CreatedSuccessfully() {
		controller = new ConferenceController();
		controller.addConference(conference1);
		assertEquals("Conference was not added properly", controller.getConference(0), conference1);
	}

	/**
	 * Testing attempt to get number of conferences.
	 */
	@Test
	public void getNumberOfConferences() {
		controller = new ConferenceController();
		controller.addConference(conference1);
		controller.addConference(conference2);
		assertEquals("Didnt return right number of conferences", controller.getNumberOfConferences(), 2);
	}

	/**
	 * Testing attempt to search for a conference.
	 */
	@Test
	public void containsConference() {
		controller = new ConferenceController();
		controller.addConference(conference1);
		assertTrue("Couldnt find the conference", controller.containsConference("The first Conference"));
		assertFalse("Found non-existing conference", controller.containsConference("No Conference Here"));
	}


	@Test
	public void searchForConference() throws Exception {
	}

	@Test
	public void setRoleView() throws Exception {
	}

	@Test
	public void getRoleView() throws Exception {
	}

	@Test
	public void removeAllConferences() throws Exception {
	}

	@Test
	public void isConferenceSet() throws Exception {
	}

	@Test
	public void isRoleViewSet() {
	}

	@Test
	public void isConferenceListEmpty() throws Exception {
	}
}