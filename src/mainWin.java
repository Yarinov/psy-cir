package cc;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class mainWin {

	protected Shell shell;

//	private static progremRunWindow progremRunWindow;
	private static mainWin window;
	private static runProgramWin runProgramWin;

	private static Dimension screenSize;

	private Text nameInputField;
	static String nameInputText;

	static List<File> imageForTest;

	// Instruction Part
	int circleWidthStart, circleHeightStart;
	private Label instructionLabel;

	// Setting Part
	private Text pauseTimeInput, pointSizeInput;

	static int pauseTimeInputValue = 3, pointSizeValue;

	static boolean firstSpaceAddButtonFlag, firstSpaceMinButtonFlag, numOfButtons1ButtonFlag, numOfButtons2ButtonFlag,
			pointColorGreenButtonFlag, pointColorBlueButtonFlag, pointColorRedButtonFlag, pointColorBlackFlag,
			pointShapeRecButtonFlag, pointShapeCirButtonFlag;

	static String selectedDir, instructionPhoto, endPhoto;
	static File currentDir;

	Label settingsLoadedLabel;

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
		shell.setText("מבחן עיגולים - תוכנה");
		shell.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
		shell.setMaximized(true);

		Label instructionPhotoIsLoadedText = new Label(shell, SWT.NONE);
		instructionPhotoIsLoadedText.setVisible(false);
		instructionPhotoIsLoadedText.setText("הוראות טעונות");
		instructionPhotoIsLoadedText.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		instructionPhotoIsLoadedText.setFont(SWTResourceManager.getFont("Sans", 14, SWT.NORMAL));
		instructionPhotoIsLoadedText.setBounds(969, 99, 129, 28);

		Label endPhotoIsLoadedText = new Label(shell, SWT.NONE);
		endPhotoIsLoadedText.setVisible(false);
		endPhotoIsLoadedText.setText("טקסט סיום ניסוי טעון");
		endPhotoIsLoadedText.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		endPhotoIsLoadedText.setFont(SWTResourceManager.getFont("Sans", 14, SWT.NORMAL));
		endPhotoIsLoadedText.setBounds(900, 133, 180, 28);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Arial", 9, SWT.BOLD));
		lblNewLabel.setBounds(1150, 60, 129, 24);
		lblNewLabel.setText("הגדר ספריית תמונות");

		Button selectFolderButton = new Button(shell, SWT.NONE);
		selectFolderButton.setBounds(1094, 60, 44, 24);

		Button settingButton = new Button(shell, SWT.NONE);
		settingButton.setFont(SWTResourceManager.getFont("Arial", 9, SWT.BOLD));
		settingButton.setBounds(1186, 10, 93, 30);
		settingButton.setText("הגדרות");

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Arial", 9, SWT.BOLD));
		lblNewLabel_1.setText("שם");
		lblNewLabel_1.setBounds(1235, 239, 75, 17);

		nameInputField = new Text(shell, SWT.BORDER);
		nameInputField.setBounds(1037, 233, 180, 23);

		Button openInstructions = new Button(shell, SWT.NONE);
		openInstructions.setFont(SWTResourceManager.getFont("Arial", 9, SWT.BOLD));
		openInstructions.setBounds(1186, 269, 93, 30);
		openInstructions.setText("התחל ניסוי");

		Label imageLoadText = new Label(shell, SWT.NONE);
		imageLoadText.setFont(SWTResourceManager.getFont("Sans", 14, SWT.NORMAL));
		imageLoadText.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		imageLoadText.setBounds(959, 60, 129, 28);
		imageLoadText.setText("תמונות טעונות");
		imageLoadText.setVisible(false);

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Arial", 9, SWT.BOLD));
		lblNewLabel_2.setBounds(1165, 100, 129, 17);
		lblNewLabel_2.setText("הגדר הוראות ניסוי");

		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Arial", 9, SWT.BOLD));
		lblNewLabel_3.setBounds(1139, 135, 140, 17);
		lblNewLabel_3.setText("הגדר טקסט סיום ניסוי");

		Button loadinstructionButton = new Button(shell, SWT.NONE);
		loadinstructionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fd = new FileDialog(shell, SWT.OPEN);

				fd.setText("בחר קובץ הוראות");
				fd.setFilterPath(".");
				String[] filterExt = { "*.*", "*.jpg", "*.png", ".jpeg" };
				fd.setFilterExtensions(filterExt);
				instructionPhoto = fd.open();

				if (instructionPhoto != null)
					instructionPhotoIsLoadedText.setVisible(true);

			}
		});
		loadinstructionButton.setBounds(1115, 99, 44, 24);

		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fd = new FileDialog(shell, SWT.OPEN);

				fd.setText("בחר קובץ סיום ניסוי");
				fd.setFilterPath(".");
				String[] filterExt = { "*.*", "*.jpg", "*.png", ".jpeg" };
				fd.setFilterExtensions(filterExt);
				endPhoto = fd.open();

				if (endPhoto != null)
					endPhotoIsLoadedText.setVisible(true);

			}
		});
		btnNewButton_1.setBounds(1086, 133, 44, 19);

		settingsLoadedLabel = new Label(shell, SWT.NONE);
		settingsLoadedLabel.setVisible(false);
		settingsLoadedLabel.setText("הגדרות טעונות");
		settingsLoadedLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		settingsLoadedLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.NORMAL));
		settingsLoadedLabel.setBounds(979, 12, 180, 28);

		openInstructions.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {

				nameInputText = nameInputField.getText();

				if (!settingsLoadedLabel.isVisible() || imageForTest == null || nameInputText.isEmpty()
						|| endPhoto == null || instructionPhoto == null) {
					MessageBox box = new MessageBox(shell, SWT.CANCEL | SWT.OK);
					box.setText("Error");
					box.setMessage("השלם את כל הסעיפים לפני התחלת הניסוי");

					box.open();
				} else
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

				DirectoryDialog fd = new DirectoryDialog(shell);
				fd.setText("בחר תיקיית תמונות לניסוי");
				fd.setFilterPath(".");

				imageForTest = new ArrayList<File>();

				selectedDir = fd.open();

				if (selectedDir != null) {

					currentDir = new File(selectedDir);

					imageLoadText.setVisible(true);
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
			shell.setLayout(new FillLayout());
			shell.setMaximized(true);
			shell.open();

			circleWidthStart = (int) screenSize.getWidth() / 2 - 200;
			circleHeightStart = (int) screenSize.getHeight() / 2 - 200;

			final Image image = new Image(display, instructionPhoto);

			// get Image width and height
			ImageData imgData = image.getImageData();
			imgData = imgData.scaledTo((int) (screenSize.getWidth()), (int) (screenSize.getHeight()));

			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { imgData };

			imageLoader.save(instructionPhoto, SWT.IMAGE_COPY);

			instructionLabel = new Label(shell, SWT.NONE);
			instructionLabel.setBounds(0, 0, imgData.width, imgData.height);
			instructionLabel.setImage(SWTResourceManager.getImage(instructionPhoto));

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

					if (!pointColorBlueButtonFlag && !pointColorGreenButtonFlag && !pointColorRedButtonFlag)
						pointColorBlackFlag = true;

					runProgramWin = new runProgramWin(Display.getCurrent());

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

			shell = new Shell();
			shell.setText("הגדרות");
			shell.setSize(411, 379);
			shell.open();

			Label pointSize = new Label(shell, SWT.NONE);
			pointSize.setBounds(269, 27, 122, 23);
			pointSize.setText("גודל הנקודות");
			pointSize.setFont(new Font(null, "Ariel", 16, 0));

			Label pointShape = new Label(shell, SWT.NONE);
			pointShape.setFont(SWTResourceManager.getFont("Sans", 16, SWT.NORMAL));
			pointShape.setBounds(261, 124, 130, 23);
			pointShape.setText("צורת הנקודות");

			Label pointColor = new Label(shell, SWT.NONE);
			pointColor.setFont(SWTResourceManager.getFont("Sans", 16, SWT.NORMAL));
			pointColor.setBounds(269, 75, 122, 23);
			pointColor.setText("צבע הנקודות");

			Label pauseTime = new Label(shell, SWT.NONE);
			pauseTime.setFont(SWTResourceManager.getFont("Sans", 16, SWT.NORMAL));
			pauseTime.setBounds(261, 174, 134, 23);
			pauseTime.setText("אורך ההפסקה");

			Label numOfButtons = new Label(shell, SWT.NONE);
			numOfButtons.setFont(SWTResourceManager.getFont("Sans", 16, SWT.NORMAL));
			numOfButtons.setBounds(218, 224, 173, 23);
			numOfButtons.setText("מקש אחד או שניים");

			pointSizeInput = new Text(shell, SWT.BORDER);
			pointSizeInput.setBounds(177, 31, 70, 17);

			Label lblNewLabel = new Label(shell, SWT.NONE);
			lblNewLabel.setBounds(135, 33, 30, 17);
			lblNewLabel.setText("(1-5)");

			pauseTimeInput = new Text(shell, SWT.BORDER);
			pauseTimeInput.setBounds(154, 178, 79, 23);

			Button saveSettingButton = new Button(shell, SWT.NONE);
			saveSettingButton.setBounds(154, 295, 93, 30);
			saveSettingButton.setText("אישור");

			numberOfButtonsSection();
			pointShapeSection();
			pointColorSection();
			firstSpaceSection();

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

					settingsLoadedLabel.setVisible(true);

				}
			});

		}

		private void pointShapeSection() {
			Button pointShapeRecButton = new Button(shell, SWT.CHECK);
			pointShapeRecButton.setBounds(177, 129, 70, 18);
			pointShapeRecButton.setText("ריבוע");
			pointShapeRecButtonFlag = false;

			Button pointShapeCirButton = new Button(shell, SWT.CHECK);
			pointShapeCirButton.setBounds(107, 129, 56, 18);
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

		private void pointColorSection() {
			Button pointColorGreenButton = new Button(shell, SWT.CHECK);
			pointColorGreenButton.setBounds(57, 80, 56, 18);
			pointColorGreenButton.setText("ירוק");
			pointColorGreenButtonFlag = false;

			Button pointColorBlueButton = new Button(shell, SWT.CHECK);
			pointColorBlueButton.setBounds(135, 80, 56, 18);
			pointColorBlueButton.setText("כחול");
			pointColorBlueButtonFlag = false;

			Button pointColorRedButton = new Button(shell, SWT.CHECK);
			pointColorRedButton.setBounds(197, 80, 70, 18);
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
			label.setBounds(10, 67, 381, 2);

			Label label_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_3.setBounds(10, 216, 381, 2);

			Label label_4 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_4.setBounds(10, 268, 381, 2);

			Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_1.setBounds(10, 116, 381, 2);

			Label label_2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label_2.setBounds(10, 166, 381, 2);

		}

		private void numberOfButtonsSection() {
			Button numOfButtons1Button = new Button(shell, SWT.CHECK);
			numOfButtons1Button.setBounds(123, 229, 89, 18);
			numOfButtons1Button.setText("מקש אחד");
			numOfButtons1ButtonFlag = false;

			Button numOfButtons2Button = new Button(shell, SWT.CHECK);
			numOfButtons2Button.setBounds(20, 229, 93, 18);
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
