package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.junit.Before;

import model.Conference;
import model.ConferenceStateManager;
import model.Manuscript;
import model.Review;
import model.UserProfile;
import model.UserProfileStateManager;
import view.AuthorPanel.TitleListener;


/**
 * Subprogram Chair submit a recommendation for a manuscript
 * @author Dongsheng Han
 * @version 5/30/2017
 */
public class RecommendAManuscriptPanel extends AutoSizeablePanel implements ActionListener {
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -1272270960428310414L;
	
	private JButton  fileChooserButton, submitButton;
	private JFileChooser fc;
	private File file;
	private JTextArea log;
	private final static JFrame window = new JFrame();

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                final JPanel mainPanel = new RecommendAManuscriptPanel(1, 1, new Dimension(300, 150));
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
	}
	
	public RecommendAManuscriptPanel(double theXRatio,double theYRatio,Dimension theStartingSize) {
		super(theXRatio, theYRatio, theStartingSize);
		init();
	}

	/**
	 * A private helper method for initializing all the components of this Panel. 
	 */
	private void init() {
		//Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(6,24);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        log.setFont(new Font("Times New Roman", Font.BOLD, 12));
        
        JScrollPane logScrollPane = new JScrollPane(log);
		
        fc = new JFileChooser();
		
		fileChooserButton = new JButton("Choose a File");
		fileChooserButton.addActionListener(this);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		
		add(logScrollPane, BorderLayout.CENTER);
		add(fileChooserButton);
	}

	public void actionPerformed(ActionEvent e) {
		 
		//Handle open button action.
		if (e.getSource() == fileChooserButton) {
			int returnVal = fc.showOpenDialog(RecommendAManuscriptPanel.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                log.append("File:" + file.getName() + "\n" + "Submit?" + "\n");
                fileChooserButton.setText("Change File");
                add(submitButton);	
            }
        } else if (e.getSource() == submitButton) {
//<<<<<<<<<<<<<<<<<<<<<<<<<submit File Here.
        	JOptionPane.showMessageDialog(window,"Submittion finish.","Message", JOptionPane.PLAIN_MESSAGE);
        	window.dispose();
		}
	}
	
}

//	// Fields for Conference
//	private static String conferenceName = "Computer Science";
//	private static ZoneId zoneId = ZoneId.of("UTC+1");
//	/** Year, Month, Day, Hour, Minute, Seconds, nano, zoneID */
//	private static ZonedDateTime conferenceSubmissionDeadline = ZonedDateTime.of(2017, 11, 30, 23, 45, 59, 1234,
//			zoneId);
//	
//	// Fields for Manuscript
//	private static String manuscriptTitle = "Technology";
//	
//	private static String submissionUserID = "John117";
//	private static String submissionUserName = "submission John117";
//	private static UserProfile submissionUser = new UserProfile(submissionUserID, submissionUserName);
//	
//	private static String coAuthorUserName = "Samuel034";
//	private static String anotherUserName = "Kelly087";
//	private static ArrayList<String> coAuthors = new ArrayList<String>();
//	
//	private static String reviewerUserID = "Dongl034";
//	private static String reviewerUserName = "reviewer Kelly087";
//	
//	private static ZonedDateTime submissionDate = ZonedDateTime.now();
//	
//	private static File manuscriptFile = new File("SomePath");
//	
//	private static Conference conference;
//	private static Manuscript manuscript;
//		setLayout(new GridBagLayout());
//		addColumns();
//		List<Manuscript> legalManuscripts = getLegalManuscript();
//		JComboBox<String> legalManuscriptsTitles = new JComboBox<String>();
//		for(){
//			
//		}
//		final GridBagConstraints constraints = new GridBagConstraints();
//		constraints.weightx = 0.9;
//		constraints.weighty = 0.9;
//		constraints.gridwidth = 100;
//		constraints.gridheight = 25;
//		constraints.gridx = 0;
//		constraints.gridy = 50;
//		for(Manuscript m: getLegalManuscript()){
//			JLabel theTitle = new JLabel(m.getTitle());
//			theTitle.addMouseListener(new TitleListener());
//			add(theTitle,constraints);
//			constraints.gridy += 300;
//		}
//	private void addColumns(){
//		JLabel myTitleLabel = new JLabel();
//		myTitleLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
//		myTitleLabel.setText("Title");
//		JLabel myAuthorLabel = new JLabel();
//		myAuthorLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
//		myAuthorLabel.setText("Authors");
//		JLabel myReviewsLabel = new JLabel();
//		myReviewsLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
//		myReviewsLabel.setText("Reviews");
//		JLabel myRecomendationLabel = new JLabel();
//		myRecomendationLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
//		myRecomendationLabel.setText("Recomendation");
//		
//		final GridBagConstraints constraints = new GridBagConstraints();
//		constraints.weightx = 0.9;
//		constraints.weighty = 0.9;
//		
//		//Add title column
//		constraints.gridwidth = 100;
//		constraints.gridheight = 25;
//		constraints.gridx = 0;
//		constraints.gridy = 0;
//		add(myTitleLabel, constraints);
//		
//		//Add CoAuthor column
//		constraints.gridwidth = 100;
//		constraints.gridheight = 25;
//		constraints.gridx = 300;
//		constraints.gridy = 0;
//		add(myAuthorLabel, constraints);
//		
//		//Add SubmissionDate column
//		constraints.gridwidth = 100;
//		constraints.gridheight = 25;
//		constraints.gridx = 600;
//		constraints.gridy = 0;
//		add(myReviewsLabel, constraints);
//		
//		//Add Remove column
//		constraints.gridwidth = 100;
//		constraints.gridheight = 25;
//		constraints.gridx = 900;
//		constraints.gridy = 0;
//		add(myRecomendationLabel, constraints);
//		
//		
//		
//	}
//	/**
//	 * get legal manuscript for recommend by sub program chair
//	 * @return legalManuscripts
//	 */
//	private List<Manuscript> getLegalManuscript() {
//		//setting up a conference
//		coAuthors.add(coAuthorUserName);//--------------------------------------------------------------------------------------------------
//		coAuthors.add(anotherUserName);
//		conference = new Conference(conferenceName, conferenceSubmissionDeadline);
//		for(int i = 0; i < 4; i++){
//			manuscript = new Manuscript(manuscriptTitle + Integer.toString(i), submissionUser, coAuthors, submissionDate, manuscriptFile);
//			conference.submitManuscript(manuscript);
//			for(int j = 0; j < i; j++){
//				if(i!=3){
//					UserProfile reviewer = new UserProfile(reviewerUserID + Integer.toString(j), reviewerUserName);
//					manuscript.addReviewer(reviewer,conference);
//					manuscript.addReview(new Review(reviewer,manuscriptFile));
//				}
//			}
//		}
//		
//		//manuscript with 3 reviews(max number) is legal
//		List<Manuscript> legalManuscripts = new ArrayList<>();
//		for(Manuscript manuscript:conference.getManuscripts()){
//			if(manuscript.getReviews().size() == Manuscript.MAX_REVIEWERS){
//				legalManuscripts.add(manuscript);
//				System.out.println(manuscript.getReviews().size() + manuscript.getTitle());
//			}
//		}
//		return legalManuscripts;
//    }
	
//	/**
//	 * Public inner class that handles what action happens when a user clicks a manuscripts title
//	 * @author Harlan Stewart
//	 *
//	 */
//	public class TitleListener implements MouseListener {
//		@Override
//		public void mouseClicked(MouseEvent theEvent) {
//			Object[] options = {"Submit Manuscript", "Cancel"};
//			int n = JOptionPane.showOptionDialog(theEvent.getComponent(), "What would you like to do with this manuscript?", 
//					"Manuscript Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,null);
//		}
//		@Override
//		public void mouseEntered(MouseEvent arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//		@Override
//		public void mouseExited(MouseEvent arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//		@Override
//		public void mousePressed(MouseEvent arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//		@Override
//		public void mouseReleased(MouseEvent arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//	}