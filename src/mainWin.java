import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFileChooser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

public class mainWin {

	protected Shell shell;

	private static progremRunWindow progremRunWindow;
	private static mainWin window;

	private static Dimension screenSize;

	private Text text;

	// Instruction Part
	int circleWidthStart, circleHeightStart;
	private Label instructionLabel;

	// Setting Part
	private Text pauseTimeInput, pointSizeInput;

	static int pauseTimeInputValue, pointSizeValue;

	static boolean firstSpaceAddButtonFlag, firstSpaceMinButtonFlag, numOfButtons1ButtonFlag, numOfButtons2ButtonFlag,
			pointColorGreenButtonFlag, pointColorBlueButtonFlag, pointColorRedButtonFlag, pointShapeRecButtonFlag,
			pointShapeCirButtonFlag;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			// Setup Default Settings
			firstSpaceAddButtonFlag = true;

			window = new mainWin();
			progremRunWindow = new progremRunWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents(display);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Display display) {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		shell = new Shell(display);
		shell.setText("Snippet 120");
		shell.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(1150, 60, 129, 24);
		lblNewLabel.setText("הגדר ספריית תמונות");

		Button selectFolderButton = new Button(shell, SWT.NONE);
		selectFolderButton.setBounds(1094, 60, 44, 24);

		Button settingButton = new Button(shell, SWT.NONE);
		settingButton.setBounds(1186, 10, 93, 30);
		settingButton.setText("Setting");

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setText("מספר");
		lblNewLabel_1.setBounds(1204, 101, 75, 17);

		text = new Text(shell, SWT.BORDER);
		text.setBounds(1011, 101, 180, 23);

		Button openInstructions = new Button(shell, SWT.NONE);
		openInstructions.setBounds(1186, 153, 93, 30);
		openInstructions.setText("Start");

		openInstructions.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				new instructionWindow(display);
			}
		});

		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Main Shell handling Close event, about to dipose the main Display");
				display.dispose();
			}
		});

		// Select Folder Section

		selectFolderButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				selectFolder();
			}

			private void selectFolder() {

				JFileChooser chooser;
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select a folder");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				// disable the "All files" options
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(new Component() {
				}) == JFileChooser.APPROVE_OPTION) {

					Folder imgSrc = new Folder(chooser.getSelectedFile().toString(),
							chooser.getSelectedFile().listFiles());
					System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
					System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
				} else {
					System.out.println("No Selection ");
				}
			}

		});

		settingButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				new settingWin(display);
			}

		});

	}

	private class instructionWindow {
		public instructionWindow(Display display) {
			System.out.println("Creating new child Shell");

			// =========================================
			// Create a Shell (window) from the Display
			// =========================================
			final Shell shell = new Shell(display, SWT.CLOSE);

			// =====================
			// Set the Window Title
			// =====================
			shell.setText("הוראות");

			shell.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
			shell.open();

			circleWidthStart = (int) screenSize.getWidth() / 2 - 200;
			circleHeightStart = (int) screenSize.getHeight() / 2 - 200;

			instructionLabel = new Label(shell, SWT.NONE);
			instructionLabel.setImage(new Image(display, "/home/yarin/Pictures/1.png"));
			instructionLabel.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());

			// =============================================================
			// Register a listener for the Close event on the child Shell.
			// This disposes the child Shell
			// =============================================================
			shell.addListener(SWT.Close, new Listener() {
				@Override
				public void handleEvent(Event event) {
					System.out.println("Child Shell handling Close event, about to dispose this Shell");
					shell.dispose();
				}
			});

			shell.addKeyListener(new KeyListener() {

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyPressed(KeyEvent arg0) {
					shell.close();
					progremRunWindow.open();

				}
			});
		}
	}

	private class settingWin {

		Shell shell;

		public settingWin(Display display) {
			System.out.println("Creating setting Shell");

			// =========================================
			// Create a Shell (window) from the Display
			// =========================================
			shell = new Shell(display, SWT.CLOSE);

			// =====================
			// Set the Window Title
			// =====================
			shell.setText("הגדרות");
			shell.setSize(411, 511);
			shell.open();

			// =============================================================
			// Register a listener for the Close event on the child Shell.
			// This disposes the child Shell
			// =============================================================
			shell.addListener(SWT.Close, new Listener() {
				@Override
				public void handleEvent(Event event) {
					System.out.println("Child Shell handling Close event, about to dispose this Shell");
					shell.dispose();
				}
			});

			Label pointSize = new Label(shell, SWT.NONE);
			pointSize.setBounds(269, 10, 122, 23);
			pointSize.setText("גודל הנקודות");
			pointSize.setFont(new Font(null, "Ariel", 16, 0));

			Label equalPoints = new Label(shell, SWT.RIGHT);
			equalPoints.setBounds(261, 68, 130, 23);
			equalPoints.setText("הנקודות אחידות או לא");
			equalPoints.setFont(new Font(null, "Ariel", 16, 0));

			Label pointShape = new Label(shell, SWT.NONE);
			pointShape.setFont(SWTResourceManager.getFont("Sans", 16, SWT.NORMAL));
			pointShape.setBounds(261, 173, 130, 23);
			pointShape.setText("צורת הנקודות");

			Label pointColor = new Label(shell, SWT.NONE);
			pointColor.setFont(SWTResourceManager.getFont("Sans", 16, SWT.NORMAL));
			pointColor.setBounds(269, 120, 122, 23);
			pointColor.setText("צבע הנקודות");

			Label pauseTime = new Label(shell, SWT.NONE);
			pauseTime.setFont(SWTResourceManager.getFont("Sans", 16, SWT.NORMAL));
			pauseTime.setBounds(257, 233, 134, 23);
			pauseTime.setText("אורך ההפסקה");

			Label numOfButtons = new Label(shell, SWT.NONE);
			numOfButtons.setFont(SWTResourceManager.getFont("Sans", 16, SWT.NORMAL));
			numOfButtons.setBounds(211, 287, 173, 23);
			numOfButtons.setText("מקש אחד או שניים");

			Button equalPointsButton = new Button(shell, SWT.CHECK);
			equalPointsButton.setBounds(237, 73, 18, 18);
			equalPointsButton.getSelection();

			pointSizeInput = new Text(shell, SWT.BORDER);
			pointSizeInput.setBounds(177, 16, 70, 17);

			Label lblNewLabel = new Label(shell, SWT.NONE);
			lblNewLabel.setBounds(141, 16, 30, 17);
			lblNewLabel.setText("(1-5)");

			pointShapeSection();

			pointColorSection();

			pauseTimeInput = new Text(shell, SWT.BORDER);
			pauseTimeInput.setBounds(172, 233, 79, 23);

			numberOfButtonsSection();

			firstSpaceSection();

			Button saveSettingButton = new Button(shell, SWT.NONE);
			saveSettingButton.setBounds(141, 418, 93, 30);
			saveSettingButton.setText("אישור");

			saveSettingButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {

					try {
						pauseTimeInputValue = Integer.parseInt(pauseTimeInput.getText());
					} catch (Exception e) {
						pauseTimeInputValue = 1;
					}

					try {
						pointSizeValue = 8 + Integer.parseInt(pointSizeInput.getText());
					} catch (Exception e) {
						pointSizeValue = 8;
					}

					shell.close();

				}
			});

		}

		// Done
		private void pointShapeSection() {
			Button pointShapeRecButton = new Button(shell, SWT.CHECK);
			pointShapeRecButton.setBounds(185, 178, 70, 18);
			pointShapeRecButton.setText("ריבוע");
			pointShapeRecButtonFlag = false;

			Button pointShapeCirButton = new Button(shell, SWT.CHECK);
			pointShapeCirButton.setBounds(123, 178, 56, 18);
			pointShapeCirButton.setText("עיגול");
			pointShapeCirButtonFlag = false;

			pointShapeRecButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if (!pointShapeRecButtonFlag) {
						pointShapeCirButton.setEnabled(false);
						pointShapeRecButtonFlag = true;
					} else {
						pointShapeCirButton.setEnabled(true);
						pointShapeRecButtonFlag = false;
					}

				}
			});

			pointShapeCirButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if (!pointShapeCirButtonFlag) {
						pointShapeRecButton.setEnabled(false);
						pointShapeCirButtonFlag = true;
					} else {
						pointShapeRecButton.setEnabled(true);
						pointShapeCirButtonFlag = false;
					}

				}
			});

		}

		// Done
		private void pointColorSection() {
			Button pointColorGreenButton = new Button(shell, SWT.CHECK);
			pointColorGreenButton.setBounds(61, 125, 56, 18);
			pointColorGreenButton.setText("ירוק");
			pointColorGreenButtonFlag = false;

			Button pointColorBlueButton = new Button(shell, SWT.CHECK);
			pointColorBlueButton.setBounds(123, 125, 56, 18);
			pointColorBlueButton.setText("כחול");
			pointColorBlueButtonFlag = false;

			Button pointColorRedButton = new Button(shell, SWT.CHECK);
			pointColorRedButton.setBounds(185, 125, 70, 18);
			pointColorRedButton.setText("אדום");
			pointColorRedButtonFlag = false;

			// Disable multi choose

			pointColorGreenButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if (!pointColorGreenButtonFlag) {
						pointColorRedButton.setEnabled(false);
						pointColorBlueButton.setEnabled(false);
						pointColorGreenButtonFlag = true;
					} else {
						pointColorRedButton.setEnabled(true);
						pointColorBlueButton.setEnabled(true);
						pointColorGreenButtonFlag = false;
					}

				}
			});

			pointColorBlueButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if (!pointColorBlueButtonFlag) {
						pointColorRedButton.setEnabled(false);
						pointColorGreenButton.setEnabled(false);
						pointColorBlueButtonFlag = true;
					} else {
						pointColorRedButton.setEnabled(true);
						pointColorGreenButton.setEnabled(true);
						pointColorBlueButtonFlag = false;
					}

				}
			});

			pointColorRedButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if (!pointColorRedButtonFlag) {
						pointColorBlueButton.setEnabled(false);
						pointColorGreenButton.setEnabled(false);
						pointColorRedButtonFlag = true;
					} else {
						pointColorBlueButton.setEnabled(true);
						pointColorGreenButton.setEnabled(true);
						pointColorRedButtonFlag = false;
					}

				}
			});
		}

		private void firstSpaceSection() {

			Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			label.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label.setBounds(10, 49, 381, 2);

			Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_1.setBounds(10, 106, 381, 5);

			Label label_2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_2.setBounds(10, 160, 381, 5);

			Label label_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_3.setBounds(10, 216, 381, 2);

			Label label_4 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_4.setBounds(10, 268, 381, 2);

			Label label_5 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			label_5.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_5.setBounds(10, 326, 381, 2);

		}

		private void numberOfButtonsSection() {
			Button numOfButtons1Button = new Button(shell, SWT.CHECK);
			numOfButtons1Button.setBounds(110, 292, 89, 18);
			numOfButtons1Button.setText("מקש אחד");
			numOfButtons1ButtonFlag = false;

			Button numOfButtons2Button = new Button(shell, SWT.CHECK);
			numOfButtons2Button.setBounds(15, 292, 93, 18);
			numOfButtons2Button.setText("שני מקשים");
			numOfButtons1ButtonFlag = false;

			numOfButtons1Button.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if (!numOfButtons1ButtonFlag) {
						numOfButtons2Button.setEnabled(false);
						numOfButtons1ButtonFlag = true;
					} else {
						numOfButtons2Button.setEnabled(true);
						numOfButtons1ButtonFlag = false;
					}

				}
			});

			numOfButtons2Button.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if (!numOfButtons2ButtonFlag) {
						numOfButtons1Button.setEnabled(false);
						numOfButtons2ButtonFlag = true;
					} else {
						numOfButtons1Button.setEnabled(true);
						numOfButtons2ButtonFlag = false;
					}

				}
			});

		}

	}

}
