package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class handles loading and storing
 * of ConferenceControllers that are serialized objects.
 * @author Lorenzo Pacis
 * @version 5.7.2017
 *
 */
public class DataHandler {
	
	private String mySaveFile;

	
	private ConferenceController myController;
	
	/**
	 * The constructor takes a file location and a conference controller to be
	 * Serialized and saved in the file location passed into the constructor.
	 * The controller object and the file location passed in cannot be null.
	 * @author Lorenzo pacis
	 * @param theSaveFile
	 * @param theController
	 */
	public DataHandler(String theSaveFile, ConferenceController theController) {
		mySaveFile = theSaveFile;

		myController = theController;
	}
	
	
	/**
	 * Trys to load an object output stream to save
	 * the serailized object to a .ser file.
	 * @author Lorenzo Pacis
	 */
	public void saveData() {
		
		try(ObjectOutputStream oos = 
				new ObjectOutputStream(new FileOutputStream(mySaveFile))) {
			oos.writeObject(myController);
			
		} catch (Exception ex) {
			
		}		
	}
	/**
	 * Trys to load an object input stream to load
	 * the serailized object from a .ser file.
	 * @author Lorenzo Pacis
	 */
	public ConferenceController loadData() {
		ConferenceController conferenceController = null;
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try {
			fin = new FileInputStream(mySaveFile);
			ois = new ObjectInputStream(fin);
			conferenceController = (ConferenceController) ois.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					
				}
			}
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					
				}
			}
		}
		return conferenceController;
	}

}
