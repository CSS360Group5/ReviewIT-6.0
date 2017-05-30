package model;
import java.io.File;
import java.io.Serializable;

/**
 * This class handles the reviews for each manuscript.
 * @author lorenzo pacis
 * @version 4.28.2017
 */
public class Recommendation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1003250823600981704L;

	/**
	 * The subprogram chair's UserProfile
	 */
	private final UserProfile myRecommender;
	
	/**
	 * The recommend/not recommend
	 */
	private final boolean myDecision;
	
	/**
	 * The recommend file.
	 */
	private final File myFile;
	
	public Recommendation(
			final UserProfile theRecommender,
			final boolean theDecision,
			final File theRecommendationFile
			) {
		myRecommender = theRecommender;
		myDecision = theDecision;
		myFile = theRecommendationFile;
	}
	
	public UserProfile getRecommender (){
		return myRecommender;
	}
	
	public File getRecommendationFile() {
		return myFile;
	}
	
	public boolean getRecommendDecision(){
		return myDecision;
	}
}
