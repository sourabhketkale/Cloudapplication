package com.cloudapplication.gui;
import org.eclipse.swt.*;

import org.eclipse.swt.widgets.*;

public class Filechooser {

	String filePath;
	
	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String fileselecthandler() {
		
		Filechooser filechooser= new Filechooser();
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setSize(289, 211);
		shell.open ();
		FileDialog dialog = new FileDialog (shell, SWT.SAVE);
		String [] filterNames = new String [] {"Image Files", "All Files (*)"};
		String [] filterExtensions = new String [] {"*.gif;*.png;*.xpm;*.jpg;*.jpeg;*.tiff", "*"};
		String filterPath = "/";		//here u have to 
		String platform = SWT.getPlatform();
		if (platform.equals("win32")) {
			filterNames = new String [] {"Image Files", "All Files (*.*)"};
			filterExtensions = new String [] {"*.gif;*.png;*.bmp;*.jpg;*.jpeg;*.tiff", "*.*"};
			filterPath = "c:\\";
		}
		dialog.setFilterNames (filterNames);
		dialog.setFilterExtensions (filterExtensions);
		dialog.setFilterPath (filterPath);
		dialog.setFileName ("");
		filechooser.setFilePath(dialog.getFilterPath());
		String filePathtosend=dialog.open();
		System.out.println ("Save to: " + dialog.open ());
		
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
		
		return filePathtosend;
		
	}

	
/*	public static void main(String args[]){
		
		Filechooser  filechooser= new Filechooser();
		filechooser.fileselecthandler();
		
	}*/
}