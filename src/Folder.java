import java.io.File;

public class Folder {

	private String folderName;
	private File[] filesInFolder;
	
	public Folder(String folderName, File[] files) {
		this.folderName = folderName;
		this.filesInFolder = files;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public File[] getFilesInFolder() {
		return filesInFolder;
	}

	public void setFilesInFolder(File[] filesInFolder) {
		this.filesInFolder = filesInFolder;
	}
	
	
}
