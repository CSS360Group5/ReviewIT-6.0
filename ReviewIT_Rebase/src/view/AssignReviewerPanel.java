/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Danielle
 *
 */
public class AssignReviewerPanel extends AutoSizeablePanel {

	/**
	 * Generated serialization
	 */
	private static final long serialVersionUID = -6463495129673836491L;
	
	private final JLabel titleHeaderArea;
	private final JLabel selectReviewerHeaderArea;

	/**
	 * 
	 * @param theXRatio
	 * @param theYRatio
	 * @param theStartingSize
	 */
	public AssignReviewerPanel(double theXRatio, double theYRatio, Dimension theStartingSize) {
		super(theXRatio, theYRatio, theStartingSize);
		titleHeaderArea = new JLabel();
		selectReviewerHeaderArea = new JLabel();
		initialize();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                final JFrame window = new JFrame();
                final JPanel mainPanel = new AssignReviewerPanel(1, 1, new Dimension(800, 600));
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
}
	
	private void initialize() {
		setLayout(new GridBagLayout());
		createTable();
		formatTextAreas();
		initObservers();
	}

	private void initObservers() {
		// TODO Auto-generated method stub
		
	}

	private void formatTextAreas() {
		// TODO Auto-generated method stub
		
	}

	private void createTable() {
		titleHeaderArea.setText("                               Manuscript Title                                ");
		titleHeaderArea.setHorizontalAlignment(JLabel.CENTER);
		selectReviewerHeaderArea.setText("Select\nReviewer");
		
		final GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.01;
		constraints.weighty = 0.01;
		
		constraints.gridwidth = 1500;
		constraints.gridheight = 50;
		constraints.gridx = 0;
		constraints.gridy = 0;
		titleHeaderArea.setSize(2000, 50);
		titleHeaderArea.setBackground(Color.RED);
		add(titleHeaderArea, constraints);
		
		constraints.gridwidth = 200;
		constraints.gridheight = 50;
		constraints.gridx = 1500;
		constraints.gridy = 0;
		selectReviewerHeaderArea.setSize(200, 50);
		add(selectReviewerHeaderArea, constraints);
		
	}

}
