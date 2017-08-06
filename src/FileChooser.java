

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;



public class FileChooser {
	private Model model;
	
	public FileChooser(Model m ){
		this.model = m;
		JFileChooser jFileChooser = new JFileChooser();
		//jFileChooser.setCurrentDirectory(new File("/User/alvinreyes"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		    jFileChooser.setFileFilter(filter);
		
		
		int result = jFileChooser.showOpenDialog(new JFrame());
	
	
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = jFileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}
}
