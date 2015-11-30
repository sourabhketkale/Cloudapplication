package com.cloudapplication.gui;
import connectionMongoDB.ConnectionClass;
import com.cloudapplication.pojo.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.cloudapplication.gui.*;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.swing.JList;
import javax.swing.JSpinner;
public class Applicationmainpage {

	private JFrame frmCloudApplication;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Applicationmainpage window = new Applicationmainpage();
					window.getFrmCloudApplication().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Applicationmainpage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrmCloudApplication(new JFrame());
		getFrmCloudApplication().setTitle("Cloud Application");
		getFrmCloudApplication().setBounds(100, 100, 750, 471);
		getFrmCloudApplication().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmCloudApplication().getContentPane().setLayout(null);
		Filechooser filechooser = new Filechooser();
		
		JButton DownloadButton = new JButton("Download");
		DownloadButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DownloadButton.setBounds(44, 59, 137, 58);
		getFrmCloudApplication().getContentPane().add(DownloadButton);
		
		JButton UploadButton_1 = new JButton("Upload");
		
		UploadButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UploadButton_1.setBounds(44, 179, 137, 58);
		getFrmCloudApplication().getContentPane().add(UploadButton_1);
		
		
		JList fileListcomboBox = new JList();
		fileListcomboBox.setBounds(361, 59, 271, 22);
		getFrmCloudApplication().getContentPane().add(fileListcomboBox);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(55, 335, 613, 22);
		getFrmCloudApplication().getContentPane().add(progressBar);
		
		UploadButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				filechooser.fileselecthandler();
			}
		});
	
	}

	public JFrame getFrmCloudApplication() {
		return frmCloudApplication;
	}

	public void setFrmCloudApplication(JFrame frmCloudApplication) {
		this.frmCloudApplication = frmCloudApplication;
	}
	
	public void fillflielistcombo(String collectionName){
		
		
		ConnectionClass connectionClass= new ConnectionClass();
		DB db= connectionClass.connection("CloudApplication");
		DBCollection coll= db.getCollection(collectionName);
		/*DBCursor dbCursor= */
		BasicDBObjectBuilder dbObjectBuilder = new BasicDBObjectBuilder();
		/*dbObjectBuilder.
		Set<String> collections = coll.get
		
		while(){
			
			
		}
		*/
		
		
		
		
	}
}
