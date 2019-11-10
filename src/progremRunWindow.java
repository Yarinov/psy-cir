import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class progremRunWindow {

	protected Shell shell;

	private Display display;

	static ArrayList<Point> pointList;
	static HashMap<Integer, Integer> testResult;
	static int pointsCounter = 0;
	static int testCounter = 0;

	Color pointsColor;

	boolean spaceEffect = true;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	int circleWidthStart = (int) screenSize.getWidth() / 2 - 200;
	int circleHeightStart = (int) screenSize.getHeight() / 2 - 200;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			progremRunWindow window = new progremRunWindow();
			pointList = new ArrayList<Point>();
			testResult = new HashMap<Integer, Integer>();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		progremRunWindow window = new progremRunWindow();
		pointList = new ArrayList<Point>();
		testResult = new HashMap<Integer, Integer>();

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
				
				if(arg0.keyCode == 32) {
					if(spaceEffect) spaceEffect = false;
					else spaceEffect = true;
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

				}else {
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
									e.gc.fillRectangle((int) x + circleWidthStart + 150, (int) y + circleHeightStart + 150,
											mainWin.pointSizeValue, mainWin.pointSizeValue);
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
					testResult.put(testCounter, pointsCounter);
					testCounter++;
					pointsCounter = 0;
					pointList.clear();
					spaceEffect = true;

					try {
						int sleepTime = mainWin.pauseTimeInputValue * 1000;
						Thread.sleep(sleepTime);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					canvas.addPaintListener(new PaintListener() {

						@Override
						public void paintControl(PaintEvent e) {
							e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
							e.gc.fillRectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
						}
					});
					System.out.println(testResult);
				}

				canvas.redraw();
			}
		});

	}
}
