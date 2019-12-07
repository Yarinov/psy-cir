package cc;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class runProgramWin {

	/**
	 * Launch the application.
	 * @param args
	 */
	
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	
	 @SuppressWarnings("unused")
	    private void initUI(Display display) {

	        Shell shell = new Shell(Display.getCurrent());
			shell.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
			shell.setMaximized(true);
			shell.setLayout(new FillLayout());
			shell.setText("App");
	        Board board = new Board(shell);        

	        
	        
	        shell.open();

	        while (!shell.isDisposed()) {
	          if (!display.readAndDispatch()) {
	            display.sleep();
	          }
	        }
	    }
	 
	 public runProgramWin(Display display) {
	        
	        initUI(display);
	    }
	 
	 public void open() {
		 Display display = new Display();
			runProgramWin ex = new runProgramWin(display);
	        display.dispose();
	 }
	 
	public static void main(String[] args) {

			Display display = new Display();
			runProgramWin ex = new runProgramWin(display);
	        display.dispose();
	}

}
