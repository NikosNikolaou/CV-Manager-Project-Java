package gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultCaret;

import cv.BulletListItem;
import cv.CV;
import cv.Section;
import input.HTMLTemplateReader;
import output.TemplateFileSaver;
import parser.DataValidator;
import parser.HTMLparser;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class CvTemplateEditor extends JDialog {
	
	private String templateType = null;
	private String cvTemplateStr = null;
	private CV cvTemplate = null;
	
	private JEditorPane editorPane;
	private JScrollPane editorPaneScroll;
	private JTextField nameTextField;
	private JTextField addrTextField;
	private JTextField homeTextField;
	private JTextField mobileTextField;
	private JTextField emailTextField;
	private JTextPane careerSumTextPane;
	private JTextPane coreStrengthsTextPane;
	private JTextPane profProfileTextPane;
	private JTextPane educAndTrainingTextPane;
	private JTextPane furtherCoursesTextPane;
	private JTextPane additionalInfoTextPane;
	private JTextPane interestsTextPane;
	private JTextPane skillDetailsTextPane;
	private JTextPane experienceResponsibilitiesTextPane;
	private JTextPane experienceAchivementsTextPane;
	private JComboBox<String> skillsComboBox;
	private JButton skillDetailsSaveBtn;
	private HashMap<String, String> skillDetails;
	private JComboBox<String> experienceComboBox;
	private JButton experienceDetailsSaveBtn;
	private HashMap<String, String> experienceResponsibilities;
	private HashMap<String, String> experienceResponsibilityAchivements;
	private JLabel changesAppliedTag;
	

	public static void main(String[] args) {
		try {
			CvTemplateEditor dialog = new CvTemplateEditor("COMBINED");
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CvTemplateEditor(String templateType) {
		this.templateType = templateType;
		cvTemplateStr = HTMLTemplateReader.readTemplateFile(templateType);
		
		initializeJDialogParameters();
		initializeJEditorPane();
		initializeJLabels();
		initializeJTextFields();
		initializeJButtons();
	}
	
	public CvTemplateEditor(CV cv) {
		this.templateType = cv.getCvType();
		cvTemplateStr = HTMLTemplateReader.readTemplateFile(templateType);
		
		initializeJDialogParameters();
		initializeJEditorPane();
		initializeJLabels();
		initializeJTextFields();
		initializeJButtons();
		
		fillTextFieldsUsingCv(cv);
		updateEditorPaneText();
	}
	
	private void initializeJDialogParameters(){
		setResizable(false);
		setBounds(100, 100, 1250, 1000);
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setType(Type.POPUP);
		setLocationRelativeTo(null);
		setTitle("CV editor - Active template: " + templateType);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we){
		    	if (changesAppliedTag.getText().equals("YES")){
			        String objButtons[] = {"Exit","Cancel"};
			        int exitPromptResult = JOptionPane.showOptionDialog(null, "The template has been edited, but document was not saved.\nAll made changes will be lost.\nAre you sure?", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, objButtons, objButtons[1]);
			        if(exitPromptResult==JOptionPane.YES_OPTION){
			            dispose();
			        }
		    	}else{
		    		dispose();
		    	}
		    }
		});
	}
	
	private void initializeJEditorPane(){
		getContentPane().setLayout(null);
		editorPaneScroll = new JScrollPane();
		editorPaneScroll.setBounds(0, 0, 558, 971);
		getContentPane().add(editorPaneScroll);
		
		editorPane = new JEditorPane("text/html", ""){
			@Override
			public void paint(Graphics g) {
            	Graphics2D g2d = (Graphics2D) g.create();
            	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            	super.paint(g2d);
            	g2d.dispose();
			}
		};
		editorPane.setEditable(false);
		editorPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		editorPaneScroll.setViewportView(editorPane);
		
		DefaultCaret caret = (DefaultCaret) editorPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
		editorPane.setText(cvTemplateStr);
	}
	
	private void initializeJLabels(){
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblName.setBounds(588, 50, 80, 25);
		getContentPane().add(lblName);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAddress.setBounds(588, 87, 80, 25);
		getContentPane().add(lblAddress);
		
		JLabel lblHome = new JLabel("Home:");
		lblHome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHome.setBounds(588, 124, 80, 25);
		getContentPane().add(lblHome);
		
		JLabel lblMobile = new JLabel("Mobile:");
		lblMobile.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMobile.setBounds(588, 161, 80, 25);
		getContentPane().add(lblMobile);
		
		JLabel lblEmai = new JLabel("Email:");
		lblEmai.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEmai.setBounds(588, 196, 80, 25);
		getContentPane().add(lblEmai);
		
		JLabel label = new JLabel("__________________________________________________________________");
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label.setBounds(568, 222, 666, 25);
		getContentPane().add(label);
		
		JLabel lblGeneralInformation = new JLabel("1. GENERAL INFORMATION");
		lblGeneralInformation.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGeneralInformation.setBounds(578, 14, 240, 25);
		getContentPane().add(lblGeneralInformation);
		
		JLabel lblProfessionalProfile = new JLabel("2. PROFESSIONAL PROFILE");
		lblProfessionalProfile.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblProfessionalProfile.setBounds(923, 258, 300, 25);
		getContentPane().add(lblProfessionalProfile);
		
		if (templateType.equals("CHRONOLOGICAL")){
			JLabel lblCoreStrengths = new JLabel("3. CORE STRENGTHS");
			lblCoreStrengths.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblCoreStrengths.setBounds(578, 258, 300, 25);
			getContentPane().add(lblCoreStrengths);
		}else{
			JLabel lblSkillsAndExperience = new JLabel("3. SKILLS AND EXPERIENCE");
			lblSkillsAndExperience.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblSkillsAndExperience.setBounds(578, 258, 300, 25);
			getContentPane().add(lblSkillsAndExperience);
			
			JLabel lblNewLabel = new JLabel("Details:");
			lblNewLabel.setForeground(new Color(0, 0, 255));
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel.setBounds(578, 361, 101, 18);
			getContentPane().add(lblNewLabel);
		}
		
		if (templateType.equals("FUNCTIONAL")){
			JLabel lblCareerSummary = new JLabel("4. CAREER SUMMARY");
			lblCareerSummary.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblCareerSummary.setBounds(578, 547, 300, 25);
			getContentPane().add(lblCareerSummary);
		}else{
			JLabel lblProfessionalExperience = new JLabel("4. PROFESSIONAL EXPERIENCE");
			lblProfessionalExperience.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblProfessionalExperience.setBounds(578, 547, 300, 25);
			getContentPane().add(lblProfessionalExperience);
			
			JLabel lblResponsibilities = new JLabel("Responsibilities:");
			lblResponsibilities.setForeground(Color.BLUE);
			lblResponsibilities.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblResponsibilities.setBounds(578, 652, 101, 18);
			getContentPane().add(lblResponsibilities);
			
			JLabel lblAchievements = new JLabel("Achivements:");
			lblAchievements.setForeground(Color.BLUE);
			lblAchievements.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblAchievements.setBounds(578, 795, 101, 18);
			getContentPane().add(lblAchievements);
		}
		
		JLabel lblEducationAnd = new JLabel("5. EDUCATION AND TRAINING");
		lblEducationAnd.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEducationAnd.setBounds(923, 402, 300, 25);
		getContentPane().add(lblEducationAnd);
		
		JLabel lblFurtherCourses = new JLabel("6. FURTHER COURSES");
		lblFurtherCourses.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFurtherCourses.setBounds(923, 547, 300, 25);
		getContentPane().add(lblFurtherCourses);
		
		JLabel lblSkillsAnd = new JLabel("7. ADDITIONAL INFORMATION");
		lblSkillsAnd.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSkillsAnd.setBounds(923, 695, 300, 25);
		getContentPane().add(lblSkillsAnd);
		
		JLabel lblInterests = new JLabel("8. INTERESTS");
		lblInterests.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblInterests.setBounds(923, 834, 300, 25);
		getContentPane().add(lblInterests);
		
		JLabel lblTemplateEdited = new JLabel("Changes Applied:");
		lblTemplateEdited.setForeground(Color.BLUE);
		lblTemplateEdited.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTemplateEdited.setBounds(994, 14, 158, 25);
		getContentPane().add(lblTemplateEdited);
		
		changesAppliedTag = new JLabel("NO");
		changesAppliedTag.setForeground(new Color(255, 0, 0));
		changesAppliedTag.setHorizontalAlignment(SwingConstants.CENTER);
		changesAppliedTag.setFont(new Font("Tahoma", Font.BOLD, 18));
		changesAppliedTag.setBounds(1154, 13, 80, 25);
		getContentPane().add(changesAppliedTag);
	}
	
	private void initializeJTextFields(){
		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameTextField.setBounds(670, 50, 300, 26);
		getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		addrTextField = new JTextField();
		addrTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addrTextField.setColumns(10);
		addrTextField.setBounds(670, 86, 300, 26);
		getContentPane().add(addrTextField);
		
		homeTextField = new JTextField();
		homeTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		homeTextField.setColumns(10);
		homeTextField.setBounds(670, 123, 300, 26);
		getContentPane().add(homeTextField);
		
		mobileTextField = new JTextField();
		mobileTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		mobileTextField.setColumns(10);
		mobileTextField.setBounds(670, 160, 300, 26);
		getContentPane().add(mobileTextField);
		
		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		emailTextField.setColumns(10);
		emailTextField.setBounds(670, 196, 300, 26);
		getContentPane().add(emailTextField);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(923, 283, 300, 90);
		getContentPane().add(scrollPane_1);
		
		profProfileTextPane = new JTextPane();
		profProfileTextPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane_1.setViewportView(profProfileTextPane);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(924, 427, 299, 90);
		getContentPane().add(scrollPane_2);
		
		educAndTrainingTextPane = new JTextPane();
		scrollPane_2.setViewportView(educAndTrainingTextPane);
		educAndTrainingTextPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(924, 572, 299, 90);
		getContentPane().add(scrollPane_3);
		
		furtherCoursesTextPane = new JTextPane();
		scrollPane_3.setViewportView(furtherCoursesTextPane);
		furtherCoursesTextPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(923, 720, 300, 90);
		getContentPane().add(scrollPane_4);
		
		additionalInfoTextPane = new JTextPane();
		scrollPane_4.setViewportView(additionalInfoTextPane);
		additionalInfoTextPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(923, 859, 300, 90);
		getContentPane().add(scrollPane_5);
		
		interestsTextPane = new JTextPane();
		scrollPane_5.setViewportView(interestsTextPane);
		interestsTextPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		if (templateType.equals("CHRONOLOGICAL")){
			JScrollPane scrollPane_6 = new JScrollPane();
			scrollPane_6.setBounds(579, 283, 299, 234);
			getContentPane().add(scrollPane_6);

			coreStrengthsTextPane = new JTextPane();
			coreStrengthsTextPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
			scrollPane_6.setViewportView(coreStrengthsTextPane);
		}else{
			JScrollPane scrollPane_6 = new JScrollPane();
			scrollPane_6.setBounds(578, 379, 300, 103);
			getContentPane().add(scrollPane_6);
			
			skillDetailsTextPane = new JTextPane();
			skillDetailsTextPane.setBackground(new Color(220, 220, 220));
			skillDetailsTextPane.setEnabled(false);
			skillDetailsTextPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
			scrollPane_6.setViewportView(skillDetailsTextPane);
			skillDetailsTextPane.getDocument().addDocumentListener(new DocumentListener() {
		        @Override
		        public void removeUpdate(DocumentEvent e) {
		        	checkTextMatch(e);
		        }
	
		        @Override
		        public void insertUpdate(DocumentEvent e) {
		        	checkTextMatch(e);
		        }
	
		        @Override
		        public void changedUpdate(DocumentEvent e) {
		        	checkTextMatch(e);
		        }
		        
		        private void checkTextMatch(DocumentEvent e){
		        	if (skillsComboBox.getSelectedItem() != null){
			        	if (skillDetailsTextPane.getText().equals(skillDetails.get(skillsComboBox.getSelectedItem().toString()))){
			        		skillDetailsTextPane.setForeground(new Color(0, 0, 0));
			        		skillDetailsSaveBtn.setEnabled(false);
			        	}else{
			        		skillDetailsTextPane.setForeground(new Color(255, 0, 0));
			        		skillDetailsSaveBtn.setEnabled(true);
			        	}
		        	}
		        }
		    });
		}
			
		if (templateType.equals("FUNCTIONAL")){
			JScrollPane scrollPane_7 = new JScrollPane();
			scrollPane_7.setBounds(579, 572, 300, 377);
			getContentPane().add(scrollPane_7);

			careerSumTextPane = new JTextPane();
			careerSumTextPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
			scrollPane_7.setViewportView(careerSumTextPane);
		}else{
			JScrollPane scrollPane_7 = new JScrollPane();
			scrollPane_7.setBounds(579, 671, 298, 113);
			getContentPane().add(scrollPane_7);
			
			experienceResponsibilitiesTextPane = new JTextPane();
			scrollPane_7.setViewportView(experienceResponsibilitiesTextPane);
			experienceResponsibilitiesTextPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
			experienceResponsibilitiesTextPane.setEnabled(false);
			experienceResponsibilitiesTextPane.setBackground(new Color(220, 220, 220));
			experienceResponsibilitiesTextPane.getDocument().addDocumentListener(new DocumentListener() {
		        @Override
		        public void removeUpdate(DocumentEvent e) {
		        	checkTextMatch(e);
		        }
	
		        @Override
		        public void insertUpdate(DocumentEvent e) {
		        	checkTextMatch(e);
		        }
	
		        @Override
		        public void changedUpdate(DocumentEvent e) {
		        	checkTextMatch(e);
		        }
		        
		        private void checkTextMatch(DocumentEvent e){
		        	if (experienceComboBox.getSelectedItem() != null){
			        	if (experienceResponsibilitiesTextPane.getText().equals(experienceResponsibilities.get(experienceComboBox.getSelectedItem().toString()))){
			        		experienceResponsibilitiesTextPane.setForeground(new Color(0, 0, 0));
			        		experienceDetailsSaveBtn.setEnabled(false);
			        	}else{
			        		experienceResponsibilitiesTextPane.setForeground(new Color(255, 0, 0));
			        		experienceDetailsSaveBtn.setEnabled(true);
			        	}
		        	}
		        }
		    });
			
			JScrollPane scrollPane_8 = new JScrollPane();
			scrollPane_8.setBounds(579, 814, 298, 101);
			getContentPane().add(scrollPane_8);
			
			experienceAchivementsTextPane = new JTextPane();
			scrollPane_8.setViewportView(experienceAchivementsTextPane);
			experienceAchivementsTextPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
			experienceAchivementsTextPane.setEnabled(false);
			experienceAchivementsTextPane.setBackground(new Color(220, 220, 220));
			experienceAchivementsTextPane.getDocument().addDocumentListener(new DocumentListener() {
		        @Override
		        public void removeUpdate(DocumentEvent e) {
		        	checkTextMatch(e);
		        }
	
		        @Override
		        public void insertUpdate(DocumentEvent e) {
		        	checkTextMatch(e);
		        }
	
		        @Override
		        public void changedUpdate(DocumentEvent e) {
		        	checkTextMatch(e);
		        }
		        
		        private void checkTextMatch(DocumentEvent e){
		        	if (experienceComboBox.getSelectedItem() != null){
			        	if (experienceAchivementsTextPane.getText().equals(experienceResponsibilityAchivements.get(experienceComboBox.getSelectedItem().toString()))){
			        		experienceAchivementsTextPane.setForeground(new Color(0, 0, 0));
			        		experienceDetailsSaveBtn.setEnabled(false);
			        	}else{
			        		experienceAchivementsTextPane.setForeground(new Color(255, 0, 0));
			        		experienceDetailsSaveBtn.setEnabled(true);
			        	}
		        	}
		        }
		    });
		}
	}
	
	private void initializeJButtons(){
		JButton applyAllBtn = new JButton("Apply all");
		applyAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				applyButtonPress();
			}
		});
		applyAllBtn.setForeground(Color.BLUE);
		applyAllBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		applyAllBtn.setBounds(994, 49, 240, 50);
		getContentPane().add(applyAllBtn);

		
		JButton resetAllBtn = new JButton("Reset all");
		resetAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetButtonPress();
			}
		});
		resetAllBtn.setForeground(Color.RED);
		resetAllBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		resetAllBtn.setBounds(994, 110, 240, 50);
		getContentPane().add(resetAllBtn);
		
		JButton btnSave = new JButton("Save to file");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveButtonPress();
			}
		});
		btnSave.setForeground(Color.BLACK);
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSave.setBounds(994, 174, 240, 50);
		getContentPane().add(btnSave);
		
		
		
		if (!templateType.equals("CHRONOLOGICAL")){
			JButton skillsAddNewBtn = new JButton("Add new");
			skillsAddNewBtn.setForeground(new Color(0, 102, 0));
			skillsAddNewBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
			skillsAddNewBtn.setBounds(578, 289, 145, 30);
			getContentPane().add(skillsAddNewBtn);
			skillsAddNewBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String newSkill = "";
					while(newSkill!=null && newSkill.equals("")){
						newSkill = JOptionPane.showInputDialog(null, "Skills and experience on:", "Add new", JOptionPane.QUESTION_MESSAGE);
						if (newSkill!=null && newSkill.equals("")){
							JOptionPane.showMessageDialog(null, "Input field can't be empty...", "Input Error", JOptionPane.WARNING_MESSAGE);
						}
					}
					if (newSkill!=null){
						skillsComboBox.addItem(newSkill);
						skillDetails.put(newSkill, "");
						skillsComboBox.setSelectedItem(newSkill);
					}
				}
			});
			
			JButton skillsRemoveSelectedBtn = new JButton("Remove selected");
			skillsRemoveSelectedBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					skillDetails.remove(skillsComboBox.getSelectedItem().toString());
					skillsComboBox.removeItemAt(skillsComboBox.getSelectedIndex());
					skillDetailsSaveBtn.setEnabled(false);
				}
			});
			skillsRemoveSelectedBtn.setEnabled(false);
			skillsRemoveSelectedBtn.setForeground(new Color(255, 51, 0));
			skillsRemoveSelectedBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
			skillsRemoveSelectedBtn.setBounds(733, 289, 145, 30);
			getContentPane().add(skillsRemoveSelectedBtn);
			
			skillsComboBox = new JComboBox<String>();
			skillsComboBox.setBounds(578, 324, 300, 26);
			getContentPane().add(skillsComboBox);
			
			skillsComboBox.addActionListener (new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
			        if (skillsComboBox.getItemCount() > 0){
			        	skillsRemoveSelectedBtn.setEnabled(true);
			        	
			        	skillDetailsTextPane.setEnabled(true);
			        	skillDetailsTextPane.setBackground(new Color(255, 255, 255));
			        	if (skillDetails.size() > 0){
			        		skillDetailsTextPane.setText(skillDetails.get(skillsComboBox.getSelectedItem().toString()));
			        	}else{
			        		skillDetailsTextPane.setText("");
			        	}
			        }else{
			        	skillsRemoveSelectedBtn.setEnabled(false);
			        	
			        	skillDetailsTextPane.setEnabled(false);
			        	skillDetailsTextPane.setBackground(new Color(220, 220, 220));
			        	skillDetailsTextPane.setText("");
			        }
			    }
			});
			
			skillDetails = new HashMap<String, String>();
			
			skillDetailsSaveBtn = new JButton("Save");
			skillDetailsSaveBtn.setEnabled(false);
			skillDetailsSaveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					skillDetails.put(skillsComboBox.getSelectedItem().toString(), skillDetailsTextPane.getText());
					skillDetailsTextPane.setText(skillDetailsTextPane.getText());
					skillDetailsSaveBtn.setEnabled(false);
				}
			});
			skillDetailsSaveBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
			skillDetailsSaveBtn.setBounds(578, 493, 300, 23);
			getContentPane().add(skillDetailsSaveBtn);
		}
		
		
		
		if (!templateType.equals("FUNCTIONAL")){
			JButton experienceAddNewBtn = new JButton("Add new");
			experienceAddNewBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String newExperience = "";
					boolean validation = false;
					while(newExperience!=null && (newExperience.equals("") || !validation)){
						newExperience = JOptionPane.showInputDialog(null, "Professional experience on:", "Add new", JOptionPane.QUESTION_MESSAGE);
						
						if (newExperience!=null && newExperience.equals("")){
							JOptionPane.showMessageDialog(null, "Input field can't be empty...", "Input Error", JOptionPane.WARNING_MESSAGE);
						}else if (newExperience!=null){
							if (newExperience.split(",").length != 3){
								JOptionPane.showMessageDialog(null, "Input must be in the following format:\n" + "Company Name, Job Title, Date", "Input Error", JOptionPane.WARNING_MESSAGE);
							}else{
						        try {
						        	String dateStr = newExperience.split(",")[2].trim();
						        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
									dateFormat.parse(dateStr);
									
						        	if (!DataValidator.checkDate(dateStr)){
						        		JOptionPane.showMessageDialog(null, "Use this format for Date: dd/MM/yyyy\nWhere dd->[01, 31], MM->[01, 12], yyyy->[1970, " + DataValidator.getCurrentYear() + "]", "Input Error", JOptionPane.WARNING_MESSAGE);
						        		continue;
						        	}
								} catch (ParseException e) {
									JOptionPane.showMessageDialog(null, "Use this format for Date: dd/MM/yyyy", "Input Error", JOptionPane.WARNING_MESSAGE);
									continue;
								}
						        
						        validation = true;
							}
						}
					}
					
					if (newExperience!=null){
						experienceComboBox.addItem(newExperience);
						experienceResponsibilities.put(newExperience, "");
						experienceResponsibilityAchivements.put(newExperience, "");
						experienceComboBox.setSelectedItem(newExperience);
					}
				}
			});
			experienceAddNewBtn.setForeground(new Color(0, 102, 0));
			experienceAddNewBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
			experienceAddNewBtn.setBounds(578, 578, 145, 30);
			getContentPane().add(experienceAddNewBtn);
			
			JButton experienceRemoveSelectedBtn = new JButton("Remove selected");
			experienceRemoveSelectedBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					experienceResponsibilities.remove(experienceComboBox.getSelectedItem().toString());
					experienceResponsibilityAchivements.remove(experienceComboBox.getSelectedItem().toString());
					experienceComboBox.removeItemAt(experienceComboBox.getSelectedIndex());
					experienceDetailsSaveBtn.setEnabled(false);
				}
			});
			experienceRemoveSelectedBtn.setForeground(new Color(255, 51, 0));
			experienceRemoveSelectedBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
			experienceRemoveSelectedBtn.setEnabled(false);
			experienceRemoveSelectedBtn.setBounds(733, 578, 145, 30);
			getContentPane().add(experienceRemoveSelectedBtn);
			
			experienceResponsibilities = new HashMap<String, String>();
			experienceResponsibilityAchivements = new HashMap<String, String>();
			
			experienceDetailsSaveBtn = new JButton("Save");
			experienceDetailsSaveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					experienceResponsibilities.put(experienceComboBox.getSelectedItem().toString(), experienceResponsibilitiesTextPane.getText());
					experienceResponsibilitiesTextPane.setText(experienceResponsibilitiesTextPane.getText());
					
					experienceResponsibilityAchivements.put(experienceComboBox.getSelectedItem().toString(), experienceAchivementsTextPane.getText());
					experienceAchivementsTextPane.setText(experienceAchivementsTextPane.getText());
					
					experienceDetailsSaveBtn.setEnabled(false);
				}
			});
			experienceDetailsSaveBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
			experienceDetailsSaveBtn.setEnabled(false);
			experienceDetailsSaveBtn.setBounds(578, 926, 300, 23);
			getContentPane().add(experienceDetailsSaveBtn);
			
			experienceComboBox = new JComboBox<String>();
			experienceComboBox.setBounds(578, 613, 300, 26);
			getContentPane().add(experienceComboBox);
			
			experienceComboBox.addActionListener (new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
			        if (experienceComboBox.getItemCount() > 0){
			        	experienceRemoveSelectedBtn.setEnabled(true);
			        	
			        	experienceResponsibilitiesTextPane.setEnabled(true);
			        	experienceResponsibilitiesTextPane.setBackground(new Color(255, 255, 255));
			        	if (experienceResponsibilities.size() > 0){
			        		experienceResponsibilitiesTextPane.setText(experienceResponsibilities.get(experienceComboBox.getSelectedItem().toString()));
			        	}else{
			        		experienceResponsibilitiesTextPane.setText("");
			        	}
			        	
			        	experienceAchivementsTextPane.setEnabled(true);
			        	experienceAchivementsTextPane.setBackground(new Color(255, 255, 255));
			        	if (experienceResponsibilities.size() > 0){
			        		experienceAchivementsTextPane.setText(experienceResponsibilityAchivements.get(experienceComboBox.getSelectedItem().toString()));
			        	}else{
			        		experienceAchivementsTextPane.setText("");
			        	}
			        }else{
			        	experienceRemoveSelectedBtn.setEnabled(false);
			        	
			        	experienceResponsibilitiesTextPane.setEnabled(false);
			        	experienceResponsibilitiesTextPane.setBackground(new Color(220, 220, 220));
			        	experienceResponsibilitiesTextPane.setText("");
			        	
			        	experienceAchivementsTextPane.setEnabled(false);
			        	experienceAchivementsTextPane.setBackground(new Color(220, 220, 220));
			        	experienceAchivementsTextPane.setText("");
			        }
			    }
			});
		}
	}
	
	private void fillTextFieldsUsingCv(CV cv){
		nameTextField.setText(cv.getCvOwnerName());
		addrTextField.setText(cv.getCvOwnerAddress());
		homeTextField.setText(cv.getCvOwnerHomeTelephone());
		mobileTextField.setText(cv.getCvOwnerMobileTelephone());
		emailTextField.setText(cv.getCvOwnerEmail());
		
		for (Section section : cv.getSectionsArray()){
			switch (section.getTitle()){
				case "2. PROFESSIONAL PROFILE":
					profProfileTextPane.setText(section.getSectionParagraphs());
					break;
				case "3. SKILLS AND EXPERIENCE":
					for (Section subSection : section.getSubSections()){
						skillsComboBox.addItem(subSection.getTitle().substring(subSection.getTitle().indexOf("<")+1, subSection.getTitle().indexOf(">")));
						skillDetails.put(subSection.getTitle().substring(subSection.getTitle().indexOf("<")+1, subSection.getTitle().indexOf(">")), subSection.getSectionBulletListItems());
					}
					skillsComboBox.setSelectedIndex(0);
					break;
				case "3. CORE STRENGTHS":
					coreStrengthsTextPane.setText(section.getSectionParagraphs());
					break;
				case "4. PROFESSIONAL EXPERIENCE":
					for (BulletListItem bListItem : section.getBulletListItemsArray()){
						experienceComboBox.addItem(bListItem.getContentsWithDate());
						
						experienceResponsibilities.put(bListItem.getContentsWithDate(), bListItem.getSubBulletListItems().get(0).getContentsWithDate());
						
						String achievements = "";
						for (BulletListItem achviementBullet : bListItem.getSubBulletListItems().get(0).getSubBulletListItems()){
							achievements += achviementBullet.getContentsWithDate() + System.lineSeparator();
						}
						achievements = achievements.substring(0, achievements.lastIndexOf(System.lineSeparator()));
						
						experienceResponsibilityAchivements.put(bListItem.getContentsWithDate(), achievements);
					}
					experienceComboBox.setSelectedIndex(0);
					break;
				case "4. CAREER SUMMARY":
					careerSumTextPane.setText(section.getSectionBulletListItems());
					break;
				case "5. EDUCATION AND TRAINING":
					educAndTrainingTextPane.setText(section.getSectionBulletListItems());
				case "6. FURTHER COURSES":
					furtherCoursesTextPane.setText(section.getSectionBulletListItems());
					break;
				case "7. ADDITIONAL INFORMATION":
					additionalInfoTextPane.setText(section.getSectionParagraphs());
					break;
				case "8. INTERESTS":
					interestsTextPane.setText(section.getSectionParagraphs());
					break;
			}
		}
	}
	
	private void applyButtonPress(){
		if (!checkForEmptyFields(false)){
			if (validateInput()){
				updateEditorPaneText();
				updateCvObjectData();
				
				changesAppliedTag.setText("YES");
				changesAppliedTag.setForeground(new Color(0, 200, 0));
			}
		}else{
			JOptionPane.showMessageDialog(null, "All fields are empty, nothing to be applied!", "Note", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private boolean checkForEmptyFields(boolean checkAllFields){
		boolean firstPartEmpty = false;
		boolean secondPartEmpty = false;
		boolean thirdPartEmpty = false;
		
		if (checkAllFields){
			if (
				nameTextField.getText().equals("") ||
					addrTextField.getText().equals("") ||
						homeTextField.getText().equals("") ||
							mobileTextField.getText().equals("") ||
								emailTextField.getText().equals("") ||
									profProfileTextPane.getText().equals("") ||
										educAndTrainingTextPane.getText().equals("") ||
											furtherCoursesTextPane.getText().equals("") ||
												additionalInfoTextPane.getText().equals("") ||
													interestsTextPane.getText().equals("")){
				
				firstPartEmpty = true;
			}
		}else{
			if (
					nameTextField.getText().equals("") &&
						addrTextField.getText().equals("") &&
							homeTextField.getText().equals("") &&
								mobileTextField.getText().equals("") &&
									emailTextField.getText().equals("") &&
										profProfileTextPane.getText().equals("") &&
											educAndTrainingTextPane.getText().equals("") &&
												furtherCoursesTextPane.getText().equals("") &&
													additionalInfoTextPane.getText().equals("") &&
														interestsTextPane.getText().equals("")){
					
					firstPartEmpty = true;
				}
		}
		
		if (!templateType.equals("CHRONOLOGICAL")){
			if (skillsComboBox.getItemCount()==0){
				secondPartEmpty = true;
			}
		}else{
			if (coreStrengthsTextPane.getText().equals("")){
				secondPartEmpty = true;
			}
		}
		
		if (!templateType.equals("FUNCTIONAL")){
			if (experienceComboBox.getItemCount()==0){
				thirdPartEmpty = true;
			}
		}else{
			if (careerSumTextPane.getText().equals("")){
				thirdPartEmpty = true;
			}
		}
		
		if (checkAllFields){
			return (firstPartEmpty || secondPartEmpty || thirdPartEmpty);
		}else{
			return (firstPartEmpty && secondPartEmpty && thirdPartEmpty);
		}
	}
	
	private boolean validateInput(){
		if (!DataValidator.checkDataFormat("EDUCATION AND TRAINING", educAndTrainingTextPane.getText(), "Qualification, Establishment, Location, Date", 3)){
			return false;
		}
		if (!DataValidator.checkDataFormat("FURTHER COURSES", furtherCoursesTextPane.getText(), "Course, Establishment, Location, Date", 3)){
			return false;
		}
		if (templateType.equals("FUNCTIONAL")){
			if (!DataValidator.checkDataFormat("CAREER SUMMARY", careerSumTextPane.getText(), "Company Name, Job Title, Date", 2)){
				return false;
			}
		}
		
		return true;
	}
	
	private void updateEditorPaneText(){
		String updatedText;
		
		updatedText = HTMLparser.getUpdatedTemplateText(cvTemplateStr, "NAME", nameTextField.getText());
		updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "ADDRESS", addrTextField.getText());
		updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "HOME", homeTextField.getText());
		updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "MOBILE", mobileTextField.getText());
		updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "EMAIL", emailTextField.getText());
		updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "PROFFESIONAL-PROFILE", profProfileTextPane.getText(), "div", true);
		updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "EDUCATION-AND-TRAINING", educAndTrainingTextPane.getText(), "li", false);
		updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "FURTHER-COURSES", furtherCoursesTextPane.getText(), "li", false);
		updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "ADDITIONAL-INFORMATION", additionalInfoTextPane.getText(), "div", true);
		updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "INTERESTS", interestsTextPane.getText(), "div", true);
		
		if (!templateType.equals("CHRONOLOGICAL")){
			if (skillsComboBox.getItemCount() != 0){
				updatedText = HTMLparser.getUpdatedTemplateText(updatedText, skillsComboBox, skillDetails);
			}
		}else{
			updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "CORE-STRENGTHS", coreStrengthsTextPane.getText(), "div", true);
		}
		
		if (!templateType.equals("FUNCTIONAL")){
			if (experienceComboBox.getItemCount() != 0){
				updatedText = HTMLparser.getUpdatedTemplateText(updatedText, experienceComboBox, experienceResponsibilities, experienceResponsibilityAchivements);
			}
		}else{
			updatedText = HTMLparser.getUpdatedTemplateText(updatedText, "CAREER-SUMMARY", careerSumTextPane.getText(), "li", false);
		}
		
		editorPane.setText(updatedText);
	}
	
	private void updateCvObjectData(){
		cvTemplate = new CV();
		cvTemplate.setCvType(templateType);
		
		int sectionNum = -1;
		
		cvTemplate.setGeneralInformation(nameTextField.getText().trim(), addrTextField.getText().trim(), homeTextField.getText().trim(), mobileTextField.getText().trim(), emailTextField.getText().trim());
		
		sectionNum = cvTemplate.addSection("2. PROFESSIONAL PROFILE");
		cvTemplate.addParagraph(sectionNum, profProfileTextPane.getText().trim());
		
		if (!templateType.equals("CHRONOLOGICAL")){
			sectionNum = cvTemplate.addSection("3. SKILLS AND EXPERIENCE");
			for (int i=0; i<skillsComboBox.getItemCount(); i++){
				String experienceOn = skillsComboBox.getItemAt(i);
				String details[] = skillDetails.get(skillsComboBox.getItemAt(i)).split(System.lineSeparator());
				
				Section subSection = new Section("3." + (i+1) + ". SKILLS AND EXPERIENCE ON <" + experienceOn + ">");
				for (String bulletListContents : details){
					subSection.addBulletListItem(bulletListContents, null);
				}
				
				cvTemplate.addSubSection(sectionNum, subSection);
			}
			
		}else{
			sectionNum = cvTemplate.addSection("3. CORE STRENGTHS");
			cvTemplate.addParagraph(sectionNum, coreStrengthsTextPane.getText().trim());
		}
		
		if (!templateType.equals("FUNCTIONAL")){
			sectionNum = cvTemplate.addSection("4. PROFESSIONAL EXPERIENCE");
			for (int i=0; i<experienceComboBox.getItemCount(); i++){
				String experienceEntry[] = experienceComboBox.getItemAt(i).split(",");
				String responsibilities = experienceResponsibilities.get(experienceComboBox.getItemAt(i)).trim();
				String achievements[] = experienceResponsibilityAchivements.get(experienceComboBox.getItemAt(i)).split(System.lineSeparator());
				
				BulletListItem b1 = new BulletListItem(experienceEntry[0].trim()+", "+experienceEntry[1].trim(), experienceEntry[2].trim());
				BulletListItem b2 = b1.addSubBulletListItem(responsibilities, null);
				for (String achievement : achievements){
					b2.addSubBulletListItem(achievement, null);
				}
				
				cvTemplate.addBulletListItem(sectionNum, b1);
			}
		}
		else{
			sectionNum = cvTemplate.addSection("4. CAREER SUMMARY");
			if (!careerSumTextPane.getText().equals("")){
				String careerEntries[] = careerSumTextPane.getText().split(System.lineSeparator());
				for (int i=0; i<careerEntries.length; i++){
					String careerEntry[] = careerEntries[i].split(",");
					
					cvTemplate.addBulletListItem(sectionNum, careerEntry[0].trim()+", "+careerEntry[1].trim(), careerEntry[2].trim());
				}
			}
		}
		
		sectionNum = cvTemplate.addSection("5. EDUCATION AND TRAINING");
		if (!educAndTrainingTextPane.getText().equals("")){
			for (String line : educAndTrainingTextPane.getText().split(System.lineSeparator())){
				String sentenceData[] = line.split(",");
				cvTemplate.addBulletListItem(sectionNum, sentenceData[0].trim()+", "+sentenceData[1].trim()+", "+sentenceData[2].trim(), sentenceData[3].trim());
			}
		}
		
		sectionNum = cvTemplate.addSection("6. FURTHER COURSES");
		if (!furtherCoursesTextPane.getText().equals("")){
			for (String line : furtherCoursesTextPane.getText().split(System.lineSeparator())){
				String sentenceData[] = line.split(",");
				cvTemplate.addBulletListItem(sectionNum, sentenceData[0].trim()+", "+sentenceData[1].trim()+", "+sentenceData[2].trim(), sentenceData[3].trim());
			}
		}
		
		sectionNum = cvTemplate.addSection("7. ADDITIONAL INFORMATION");
		cvTemplate.addParagraph(sectionNum, additionalInfoTextPane.getText().trim());
		
		sectionNum = cvTemplate.addSection("8. INTERESTS");
		cvTemplate.addParagraph(sectionNum, interestsTextPane.getText().trim());
	}
	
	private void resetButtonPress(){
		int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to reset everything back to default?", "Reset", JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			nameTextField.setText("");
			addrTextField.setText("");
			homeTextField.setText("");
			mobileTextField.setText("");
			emailTextField.setText("");
			profProfileTextPane.setText("");
			educAndTrainingTextPane.setText("");
			furtherCoursesTextPane.setText("");
			additionalInfoTextPane.setText("");
			interestsTextPane.setText("");
			
			if (!templateType.equals("CHRONOLOGICAL")){
				skillDetails = new HashMap<String, String>();
				skillsComboBox.removeAllItems();
			}else{
				coreStrengthsTextPane.setText("");
			}
			
			if (!templateType.equals("FUNCTIONAL")){
				experienceResponsibilities = new HashMap<String, String>();
				experienceResponsibilityAchivements = new HashMap<String, String>();
				experienceComboBox.removeAllItems();
			}else{
				careerSumTextPane.setText("");
			}
			
			editorPane.setText(cvTemplateStr);
			editorPaneScroll.getVerticalScrollBar().setValue(0);
			editorPaneScroll.getHorizontalScrollBar().setValue(0);
			//editorPane.setCaretPosition(0);
			
			changesAppliedTag.setText("NO");
			changesAppliedTag.setForeground(new Color(255, 0, 0));
			
			cvTemplate = null;
		}
	}

	private void saveButtonPress(){
		if (checkForEmptyFields(true) || changesAppliedTag.getText().equals("NO")){
			JOptionPane.showMessageDialog(null, "You must fill in all template fields and apply them in order to save the CV as a document!", "Save Error", JOptionPane.WARNING_MESSAGE);
		}else{
			JFileChooser saveFileFolderChooser = new JFileChooser();
			updateFileChooserUI(saveFileFolderChooser);
			
			//saveFileFolderChooser.setCurrentDirectory(new java.io.File("."));
			saveFileFolderChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
			saveFileFolderChooser.setDialogTitle("Choose a directory...");
			saveFileFolderChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			
			FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("TXT file(*.txt)", "txt");
			FileNameExtensionFilter latexFilter = new FileNameExtensionFilter("LATEX file(*.tex)", "tex");
			saveFileFolderChooser.addChoosableFileFilter(txtFilter);
			saveFileFolderChooser.addChoosableFileFilter(latexFilter);
			saveFileFolderChooser.setFileFilter(txtFilter);
			saveFileFolderChooser.setAcceptAllFileFilterUsed(false);
			
			if(saveFileFolderChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				String selectedFileName = saveFileFolderChooser.getSelectedFile().toString();
				String selectedExtension = ((FileNameExtensionFilter)saveFileFolderChooser.getFileFilter()).getExtensions()[0];
				
				File file = new File(selectedFileName + "." + selectedExtension);
				if (file.exists()) {
				    int response = JOptionPane.showConfirmDialog(null, //
				            "File already exists!\nDo you want to replace the existing file?", //
				            "Confirm", JOptionPane.YES_NO_OPTION, //
				            JOptionPane.QUESTION_MESSAGE);
				    if (response != JOptionPane.YES_OPTION) {
				        return;
				    } 
				}
				
				TemplateFileSaver.saveCVtoFile(cvTemplate, selectedFileName, selectedExtension);
				
				JOptionPane.showMessageDialog(null, "File has been saved successfully!" , "Done", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		}
	}
	
	private void updateFileChooserUI(JFileChooser jFileChooser){
		UIManager.put("FileChooser.lookInLabelText", "Location");
		UIManager.put("FileChooser.openButtonText", "Save");
		
		SwingUtilities.updateComponentTreeUI(jFileChooser);
	}
}