import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class progremRunWindow {

	protected Shell shell;

	private Display display;

	static ArrayList<Point> pointList;
	static HashMap<String, Integer> testResult;
	static int pointsCounter = 0;
	static int testCounter = 0;
	static boolean testEnd = false;
	
	Color pointsColor;

	Timer timer;

	boolean spaceEffect = true;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	int circleWidthStart = (int) screenSize.getWidth() / 2 - 200;
	int circleHeightStart = (int) screenSize.getHeight() / 2 - 200;

	static File testResultFile;
	static List<File> testFiles;
	static int fileNumber = 0;

	static picWin picWin;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			progremRunWindow window = new progremRunWindow();
			pointList = new ArrayList<Point>();
			testResult = new HashMap<String, Integer>();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		testResultFile = new File("result.txt");
		try {
			if (testResultFile.createNewFile()) {
				System.out.println("file.txt File Created in Project root directory");
			} else
				System.out.println("File file.txt already exists in the project root directory");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		progremRunWindow window = new progremRunWindow();
		pointList = new ArrayList<Point>();
		testResult = new HashMap<String, Integer>();

		testFiles = mainWin.imageForTest;

		if (mainWin.pointColorGreenButtonFlag) {
			pointsColor = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
		} else if (mainWin.pointColorBlueButtonFlag) {
			pointsColor = Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
		} else if (mainWin.pointColorRedButtonFlag) {
			pointsColor = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		} else {
			pointsColor = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
		}

		display = Display.getDefault();
		createContents();
		shell.open();
		picWin = new picWin(display);
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

		shell = new Shell(Display.getCurrent());
		shell.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
		shell.setText("App");

		shell.setLayout(new FillLayout());
		final Canvas canvas = new Canvas(shell, SWT.NONE);

		// Print the first circle
		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {

				canvas.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				// Draw the circle
				e.gc.setLineWidth(1);
				e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
				e.gc.drawOval(circleWidthStart, circleHeightStart, 300, 300);

			}
		});

		// Print the points inside
		shell.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {

				if (arg0.keyCode == 32) {
					if (spaceEffect)
						spaceEffect = false;
					else
						spaceEffect = true;
				}

				canvas.addPaintListener(new PaintListener() {

					@Override
					public void paintControl(PaintEvent e) {
						// Draw the circle
						e.gc.setLineWidth(1);
						e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
						e.gc.drawOval(circleWidthStart, circleHeightStart, 300, 300);

						e.gc.setLineWidth(1);
						e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
						e.gc.drawOval(circleWidthStart, circleHeightStart, 300, 300);

					}
				});
			}

			@Override
			public void keyPressed(KeyEvent arg0) {

				double a = Math.random() * 2 * Math.PI;
				double r = 150 * Math.sqrt(Math.random());

				double x = r * Math.cos(a);
				double y = r * Math.sin(a);

				if (mainWin.numOfButtons1ButtonFlag) {
					if (arg0.keyCode == 32 && spaceEffect) {
						pointList.add(new Point((int) x, (int) y));
						pointsCounter++;
						System.out.println("Point C" + pointsCounter);

						canvas.addPaintListener(new PaintListener() {

							@Override
							public void paintControl(PaintEvent e) {

								if (!mainWin.pointShapeRecButtonFlag) {
									e.gc.setForeground(pointsColor);
									e.gc.setBackground(pointsColor);
									e.gc.fillOval((int) x + circleWidthStart + 150, (int) y + circleHeightStart + 150,
											mainWin.pointSizeValue, mainWin.pointSizeValue);
								} else {
									e.gc.setForeground(pointsColor);
									e.gc.setBackground(pointsColor);
									e.gc.fillRectangle((int) x + circleWidthStart + 150,
											(int) y + circleHeightStart + 150, mainWin.pointSizeValue,
											mainWin.pointSizeValue);
								}

							}

						});// END OF PAINT LIS
					} else if (arg0.keyCode == 32 && !spaceEffect && pointsCounter > 0) {
						int tempX = pointList.get(0).getX();
						int tempY = pointList.get(0).getY();

						pointList.remove(0);

						pointsCounter--;

						canvas.addPaintListener(new PaintListener() {

							@Override
							public void paintControl(PaintEvent e) {
								if (!mainWin.pointShapeRecButtonFlag) {
									e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
									e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
									e.gc.fillOval(tempX + circleWidthStart + 147, tempY + circleHeightStart + 147,
											mainWin.pointSizeValue + 4, mainWin.pointSizeValue + 4);
								} else {
									e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
									e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
									e.gc.fillRectangle(tempX + circleWidthStart + 147, tempY + circleHeightStart + 147,
											mainWin.pointSizeValue + 4, mainWin.pointSizeValue + 4);
								}

							}
						});
					}

				} else {
					if (arg0.keyCode == 0x1000004) {// Press the right
						pointList.add(new Point((int) x, (int) y));
						pointsCounter++;
						System.out.println("Point C" + pointsCounter);

						canvas.addPaintListener(new PaintListener() {

							@Override
							public void paintControl(PaintEvent e) {

								if (!mainWin.pointShapeRecButtonFlag) {
									e.gc.setForeground(pointsColor);
									e.gc.setBackground(pointsColor);
									e.gc.fillOval((int) x + circleWidthStart + 150, (int) y + circleHeightStart + 150,
											mainWin.pointSizeValue, mainWin.pointSizeValue);
								} else {
									e.gc.setForeground(pointsColor);
									e.gc.setBackground(pointsColor);
									e.gc.fillRectangle((int) x + circleWidthStart + 150,
											(int) y + circleHeightStart + 150, mainWin.pointSizeValue,
											mainWin.pointSizeValue);
								}

							}

						});// END OF PAINT LIS

					} else if (arg0.keyCode == 0x1000003 && pointsCounter > 0) {
						int tempX = pointList.get(0).getX();
						int tempY = pointList.get(0).getY();

						pointList.remove(0);

						pointsCounter--;

						canvas.addPaintListener(new PaintListener() {

							@Override
							public void paintControl(PaintEvent e) {
								if (!mainWin.pointShapeRecButtonFlag) {
									e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
									e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
									e.gc.fillOval(tempX + circleWidthStart + 147, tempY + circleHeightStart + 147,
											mainWin.pointSizeValue + 4, mainWin.pointSizeValue + 4);
								} else {
									e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
									e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
									e.gc.fillRectangle(tempX + circleWidthStart + 147, tempY + circleHeightStart + 147,
											mainWin.pointSizeValue + 4, mainWin.pointSizeValue + 4);
								}

							}
						});
					}
				}

				if (arg0.keyCode == 0xd) {
					testResult.put(testFiles.get(fileNumber).toString(), pointsCounter);
					testCounter++;
					fileNumber++;
					pointsCounter = 0;
					pointList.clear();
					spaceEffect = true;

					picWin.shell.dispose();

					if (fileNumber == testFiles.size()) {
						canvas.addPaintListener(new PaintListener() {

							@Override
							public void paintControl(PaintEvent e) {
								e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
								e.gc.fillRectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
							}
						});

						FileWriter fstream;
						BufferedWriter out = null;

						// create your filewriter and bufferedreader
						try {
							fstream = new FileWriter("result.txt");
							out = new BufferedWriter(fstream);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						// initialize the record count
						int count = 0;

						// create your iterator for your map
						Iterator<Entry<String, Integer>> it = testResult.entrySet().iterator();

						 try {
								out.write(mainWin.nameInputText + "\n\n");
								out.write("Image Name\t|\tPoints" + "\n");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
						 while (it.hasNext() && count < testResult.size()) {

						        // the key/value pair is stored here in pairs
						        Entry<String, Integer> pairs = it.next();
						        System.out.println("Value is " + pairs.getValue());

						        // since you only want the value, we only care about pairs.getValue(), which is written to out
						        try {
									out.write(pairs.getKey() + "\t| " + pairs.getValue() + "\n");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

						        // increment the record count once we have printed to the file
						        count++;
						    }
						    // lastly, close the file and end
						    try {
								out.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						    
						   
						    new sumWin(display);
						    
					} else {
						picWin = new picWin(display);
					}

					canvas.addPaintListener(new PaintListener() {

						@Override
						public void paintControl(PaintEvent e) {
							e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
							e.gc.fillRectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
						}
					});

					// Print the circle
					canvas.addPaintListener(new PaintListener() {
						@Override
						public void paintControl(PaintEvent e) {

							canvas.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
							// Draw the circle
							e.gc.setLineWidth(1);
							e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
							e.gc.drawOval(circleWidthStart, circleHeightStart, 300, 300);

						}
					});
					System.out.println(testResult);
				}

				canvas.redraw();
			}
		});

	}

	private class picWin {

		Shell shell;

		public picWin(Display display) {
			System.out.println("Creating picWin Shell");

			// =========================================
			// Create a Shell (window) from the Display
			// =========================================
			shell = new Shell(display, SWT.CLOSE);

			// =====================
			// Set the Window Title
			// =====================
			shell.setText("Ex");
			shell.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
			shell.open();

			Label picLabel = new Label(shell, SWT.NONE);
			picLabel.setImage(new Image(display, testFiles.get(fileNumber).toString()));
			picLabel.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());

			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(mainWin.pauseTimeInputValue * 1000); // time after which pop up will be
																			// disappeared.
						shell.dispose();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				};
			}.start();
		}

	}

	private class sumWin {

		Shell shell;

		public sumWin(Display display) {
			System.out.println("Creating sumWin Shell");

			// =========================================
			// Create a Shell (window) from the Display
			// =========================================
			shell = new Shell(display, SWT.CLOSE);

			// =====================
			// Set the Window Title
			// =====================
			shell.setText("סיום");
			shell.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
			shell.open();

			Label picLabel = new Label(shell, SWT.NONE);
			picLabel.setImage(new Image(display, mainWin.endPhoto.toString()));
			picLabel.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
			

		}

	}

}
