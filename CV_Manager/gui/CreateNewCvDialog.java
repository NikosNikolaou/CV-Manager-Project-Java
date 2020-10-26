package gui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CreateNewCvDialog extends JDialog {

	private ButtonGroup jRadioBtnGroup = new ButtonGroup();
	private JRadioButton functionalJRadioButton;
	private JRadioButton chronologicalJRadioButton;
	private JRadioButton combinedJRadioButton;
	private JButton createButton;

	public CreateNewCvDialog() {
		initializeJDialogParameters();
		initializeSelectLabel();
		initializeFunctionalJRadioButton();
		initializeChronologicalJRadioButton();
		initializeCombinedJRabioButton();
		initializeCreateButton();
	}
	
	private void initializeJDialogParameters(){
		setResizable(false);
		setBounds(100, 100, 306, 262);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setType(Type.POPUP);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Creating a new CV...");
	}
	
	private void initializeSelectLabel(){
		JLabel selectLabel = new JLabel("Select one of the above Templates:");
		selectLabel.setBounds(29, 2, 242, 37);
		selectLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(selectLabel);
	}
	
	private void initializeFunctionalJRadioButton(){
		functionalJRadioButton = new JRadioButton("Functional CV");
		functionalJRadioButton.setBounds(29, 51, 123, 29);
		functionalJRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(functionalJRadioButton);
		
		jRadioBtnGroup.add(functionalJRadioButton);
	}
	
	private void initializeChronologicalJRadioButton(){
		chronologicalJRadioButton = new JRadioButton("Chronological CV");
		chronologicalJRadioButton.setBounds(29, 90, 147, 29);
		chronologicalJRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(chronologicalJRadioButton);
		
		jRadioBtnGroup.add(chronologicalJRadioButton);
	}
	
	private void initializeCombinedJRabioButton(){
		combinedJRadioButton = new JRadioButton("Combined CV");
		combinedJRadioButton.setBounds(29, 129, 123, 29);
		combinedJRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(combinedJRadioButton);
		
		jRadioBtnGroup.add(combinedJRadioButton);
	}
	
	private void initializeCreateButton(){
		createButton = new JButton("Create");
		createButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		createButton.setBounds(29, 186, 242, 36);
		getContentPane().add(createButton);
		
		createButtonActionListenerHandler();
	}
	
	private void createButtonActionListenerHandler(){
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (functionalJRadioButton.isSelected()){
					dispose();
					CvTemplateEditor functionalCVTemplateWindow = new CvTemplateEditor("FUNCTIONAL");
					functionalCVTemplateWindow.setVisible(true);
				}
				else if (chronologicalJRadioButton.isSelected()){
					dispose();
					CvTemplateEditor chronologicalCVTemplateWindow = new CvTemplateEditor("CHRONOLOGICAL");
					chronologicalCVTemplateWindow.setVisible(true);
				}
				else if (combinedJRadioButton.isSelected()){
					dispose();
					CvTemplateEditor combinedCVTemplateWindow = new CvTemplateEditor("COMBINED");
					combinedCVTemplateWindow.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(null, "You have to select one CV template!", "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
}