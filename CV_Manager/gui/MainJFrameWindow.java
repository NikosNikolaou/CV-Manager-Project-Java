package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import cv.CV;
import cv.CVmerger;
import input.LoadCVFromFile;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MainJFrameWindow extends JFrame {

	private static MainJFrameWindow mainFrame;
	private JPanel contentPane;
	private JButton createNewCVBtn;
	private JButton openExistingCVBtn;
	private JButton compareCVsBtn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame = new MainJFrameWindow();
					mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainJFrameWindow() {
		initializeMainFrameParameters();
		initializeContentPane();
		initializeJButtons();
	}

	private void initializeMainFrameParameters(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 277, 205);
		setTitle("CV Manager");
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	private void initializeContentPane(){
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
	}
	
	private void initializeJButtons(){
		createNewCVBtn = new JButton("Create new");
		createNewCVBtn.setForeground(Color.RED);
		createNewCVBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		createNewCVBtn.setBounds(10, 11, 250, 44);
		contentPane.add(createNewCVBtn);
		createNewCVButtonActionListenerHandler();
		
		openExistingCVBtn = new JButton("Open existing");
		openExistingCVBtn.setEnabled(true);
		openExistingCVBtn.setForeground(Color.BLUE);
		openExistingCVBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		openExistingCVBtn.setBounds(10, 66, 250, 44);
		contentPane.add(openExistingCVBtn);
		createOpenExistingCVButtonActionListenerHandler();
		
		compareCVsBtn = new JButton("Compare CV's");
		compareCVsBtn.setEnabled(true);
		compareCVsBtn.setForeground(Color.BLACK);
		compareCVsBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		compareCVsBtn.setBounds(10, 121, 250, 44);
		contentPane.add(compareCVsBtn);
		createCompareCVsButtonActionListenerHandler();
	}
	
	private void createNewCVButtonActionListenerHandler(){
		createNewCVBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateNewCvDialog createNewCVJDialog = new CreateNewCvDialog();
				createNewCVJDialog.setVisible(true);
			}
		});
	}
	
	private void createOpenExistingCVButtonActionListenerHandler(){
		openExistingCVBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedFile = openJFileChooser();
				if (selectedFile != null){
					CV cvObject = LoadCVFromFile.load(selectedFile);
					if (cvObject == null){
						JOptionPane.showMessageDialog(null, "Couldn't open selected file as a CV.\nFile format is unsupported and doesn't match any of the application\nsupported templates: [FUNCTIONAL | CHRONOLOGICAL | COMBINED]" , "Format Error", JOptionPane.ERROR_MESSAGE);
					}else{
						CvTemplateEditor cvTemplateEditorWindow = new CvTemplateEditor(cvObject);
						cvTemplateEditorWindow.setVisible(true);
					}
				}
			}
		});
	}
			
	private void createCompareCVsButtonActionListenerHandler(){
		compareCVsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Please select the 1st cv file..." , "CV Compare", JOptionPane.INFORMATION_MESSAGE);
				String firstCvFile = openJFileChooser();
				if (firstCvFile != null){
					CV firstCvObject = LoadCVFromFile.load(firstCvFile);
					if (firstCvObject == null){
						JOptionPane.showMessageDialog(null, "Couldn't open selected file as a CV.\nFile format is unsupported and doesn't match any of the application\nsupported templates: [FUNCTIONAL | CHRONOLOGICAL | COMBINED]" , "Format Error", JOptionPane.ERROR_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "1st cv file selection: Done!\nNow select the 2nd cv file..." , "CV Compare", JOptionPane.INFORMATION_MESSAGE);
						String secondCvFile = openJFileChooser();
						if (secondCvFile != null){
							CV secondCvObject = LoadCVFromFile.load(secondCvFile);
							if (secondCvObject == null){
								JOptionPane.showMessageDialog(null, "Couldn't open selected file as a CV.\nFile format is unsupported and doesn't match any of the application\nsupported templates: [FUNCTIONAL | CHRONOLOGICAL | COMBINED]" , "Format Error", JOptionPane.ERROR_MESSAGE);
							}else{
								if (firstCvFile.equals(secondCvFile)){
									JOptionPane.showMessageDialog(null, "First CV: "+firstCvFile+"\nSecond CV: "+secondCvFile+"\n\n" + "Selected CV files can't be the same!" , "CV Compare", JOptionPane.ERROR_MESSAGE);
								}else{
									CV mergedCV = CVmerger.merge(firstCvObject, secondCvObject);
									if (mergedCV != null){
										JOptionPane.showMessageDialog(null, "First CV: "+firstCvFile+"\nSecond CV: "+secondCvFile+"\n\n" + "Given CV's succssfully merged into 1 CV!\nPress OK to open the new-merged cv in editor..." , "CV Compare", JOptionPane.INFORMATION_MESSAGE);
										CvTemplateEditor cvTemplateEditorWindow = new CvTemplateEditor(mergedCV);
										cvTemplateEditorWindow.setVisible(true);
									}
								}
							}
						}
					}
				}
			}
		});
	}
	
	private String openJFileChooser(){
		JFileChooser saveFileFolderChooser = new JFileChooser();
		
		//saveFileFolderChooser.setCurrentDirectory(new java.io.File("."));
		saveFileFolderChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
		saveFileFolderChooser.setDialogTitle("Select a file...");
		saveFileFolderChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("TXT file(*.txt)", "txt");
		FileNameExtensionFilter latexFilter = new FileNameExtensionFilter("LATEX file(*.tex)", "tex");
		saveFileFolderChooser.addChoosableFileFilter(txtFilter);
		saveFileFolderChooser.addChoosableFileFilter(latexFilter);
		saveFileFolderChooser.setFileFilter(txtFilter);
		saveFileFolderChooser.setAcceptAllFileFilterUsed(false);
		
		
		if(saveFileFolderChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String selectedFile = saveFileFolderChooser.getSelectedFile().toString();
			
			File file = new File(selectedFile);
			if (file.exists()) {
				return selectedFile;
			}else{
				JOptionPane.showMessageDialog(null, "The file that was selected doesn't exist!" , "File not found", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return null;
	}
}
	