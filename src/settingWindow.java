import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class settingWindow {

	protected Shell shell;
	private Text pauseTimeInput;

	private boolean firstSpaceAddButtonFlag, firstSpaceMinButtonFlag, numOfButtons1ButtonFlag, numOfButtons2ButtonFlag,
			pointColorGreenButtonFlag, pointColorBlueButtonFlag, pointColorRedButtonFlag, pointShapeRecButtonFlag,
			pointShapeCirButtonFlag;
	private Text pointSizeInput;


	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			settingWindow window = new settingWindow();
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
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(411, 511);
		shell.setText("הגדרות");

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
		numOfButtons.setBounds(218, 345, 173, 23);
		numOfButtons.setText("מקש אחד או שניים");

		Label firstSpace = new Label(shell, SWT.NONE);
		firstSpace.setFont(SWTResourceManager.getFont("Sans", 16, SWT.NORMAL));
		firstSpace.setBounds(211, 287, 180, 23);
		firstSpace.setText("לחיצת רווח ראשונה");

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

	}// end here

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
		// Define buttons and flag.
		// While one of them is check the other one is disabled.
		Button firstSpaceAddButton = new Button(shell, SWT.CHECK);
		firstSpaceAddButton.setBounds(135, 292, 70, 18);
		firstSpaceAddButton.setText("מעלה");
		firstSpaceAddButtonFlag = false;

		Button firstSpaceMinButton = new Button(shell, SWT.CHECK);
		firstSpaceMinButton.setBounds(59, 292, 70, 18);
		firstSpaceMinButton.setText("מפחית");
		
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
		
		Button saveSettingButton = new Button(shell, SWT.NONE);
		saveSettingButton.setBounds(141, 418, 93, 30);
		saveSettingButton.setText("אישור");

		firstSpaceAddButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (!firstSpaceAddButtonFlag) {
					firstSpaceMinButton.setEnabled(false);
					firstSpaceAddButtonFlag = true;
				} else {
					firstSpaceMinButton.setEnabled(true);
					firstSpaceAddButtonFlag = false;
				}

			}
		});

		firstSpaceMinButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (!firstSpaceMinButtonFlag) {
					firstSpaceAddButton.setEnabled(false);
					firstSpaceMinButtonFlag = true;
				} else {
					firstSpaceAddButton.setEnabled(true);
					firstSpaceMinButtonFlag = false;
				}

			}
		});

	}

	private void numberOfButtonsSection() {
		Button numOfButtons1Button = new Button(shell, SWT.CHECK);
		numOfButtons1Button.setBounds(123, 350, 89, 18);
		numOfButtons1Button.setText("מקש אחד");
		numOfButtons1ButtonFlag = false;

		Button numOfButtons2Button = new Button(shell, SWT.CHECK);
		numOfButtons2Button.setBounds(26, 350, 93, 18);
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
