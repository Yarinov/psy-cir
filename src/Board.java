package cc;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class Board extends Canvas {
	
	static final String[] EXTENSIONS = new String[] { "png", "bmp", "jpg", "jpeg", "JPG", "PNG", "BMP", "JPEG" };

	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};

	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private final int WIDTH = (int) screenSize.getWidth();
	private final int HEIGHT = (int) screenSize.getHeight();

	int circleWidthStart = (int) screenSize.getWidth() / 2 - 200;
	int circleHeightStart = (int) screenSize.getHeight() / 2 - 200;

	static ArrayList<Point> pointList;
	static HashMap<String, Integer> testResult;
	static int pointsCounter = 0;

	private boolean inGame = true;
	boolean firstTry = true;
	boolean spaceEffect = true; //True - Add | False - Delete
	
	static int testCounter = 0;
	static int fileNumber = 0;

	static File[] testFiles;

	private Display display;
	private Shell shell;
	private Runnable runnable;

	static Color pointsColor;

	// 255, 0, 0 - Red | 0, 255, 0 - Green | 0, 0, 255 - Blue

	public Board(Shell shell) {
		super(shell, SWT.DOUBLE_BUFFERED);

		this.shell = shell;
		pointList = new ArrayList<Point>();
		testResult = new HashMap<String, Integer>();

		testFiles = mainWin.currentDir.listFiles(IMAGE_FILTER);
		
		if(mainWin.pointColorBlueButtonFlag) {
			pointsColor = new Color(display, 0, 0, 255);
		}else if(mainWin.pointColorGreenButtonFlag) {
			pointsColor = new Color(display, 0, 255, 0);
		}else if(mainWin.pointColorRedButtonFlag) {
			pointsColor = new Color(display, 255, 0, 0);
		}else if(mainWin.pointColorBlackFlag) {
			pointsColor = new Color(display, 0, 0, 0);
		}


		initBoard();
	}

	private void initBoard() {

		display = shell.getDisplay();

		addListener(SWT.Paint, event -> doPainting(event));
		addListener(SWT.KeyDown, event -> onKeyDown(event));
		addListener(SWT.KeyUp, event -> onKeyUp(event));

		//Set background color
		Color col = new Color(shell.getDisplay(), 255, 255, 255);

		setBackground(col);
		col.dispose();


		initGame();
	}



	private void onKeyUp(Event event) {
		
		int key = event.keyCode;
		
		if (spaceEffect && key == 32)
			spaceEffect = false;
		else if(!spaceEffect && key == 32) {
			spaceEffect = true;
		}
			
	}

	private void initGame() {

		runnable = new Runnable() {
			@Override
			public void run() {

				display.timerExec(140, this);
				redraw();
			}
		};

		display.timerExec(140, runnable);
	};

	private void doPainting(Event e) {

		GC gc = e.gc;

		Color col = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		gc.setBackground(col);
		col.dispose();


		gc.setAntialias(SWT.ON);

		if (inGame) {
			drawObjects(e);

		} else {
			gameOver(e);
		}
	}

	private void drawObjects(Event e) {

		GC gc = e.gc;

		gc.setLineWidth(3);
		gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		gc.drawOval(circleWidthStart, circleHeightStart, 300, 300);

		gc.setForeground(pointsColor);
		gc.setBackground(pointsColor);
		for(Point point : pointList) {

			if(mainWin.pointShapeRecButtonFlag) {
				gc.fillRectangle(point.x + circleWidthStart + 150, point.y + circleHeightStart + 150, mainWin.pointSizeValue, mainWin.pointSizeValue);
			}else {
				gc.fillOval(point.x + circleWidthStart + 150, point.y + circleHeightStart + 150, mainWin.pointSizeValue, mainWin.pointSizeValue);
			}
		}
	}

	private void gameOver(Event e) {

		GC gc = e.gc;
		
		final Image image = new Image(display,  mainWin.endPhoto);

		//get Image width and height
		ImageData imgData = image.getImageData();
		imgData=imgData.scaledTo((int) (screenSize.getWidth()),(int) (screenSize.getHeight()));

		ImageLoader imageLoader = new ImageLoader();
		imageLoader.data = new ImageData[] {imgData};

		imageLoader.save( mainWin.endPhoto, SWT.IMAGE_COPY);


		gc.drawImage(image, 0, 0);


		display.timerExec(-1, runnable);
	}



	private void onKeyDown(Event e) {

		int key = e.keyCode;


		if(mainWin.numOfButtons1ButtonFlag) {
			if ((key == 32)) {
				
				if(spaceEffect) {
					double a = Math.random() * 2 * Math.PI;
					double r = 150 * Math.sqrt(Math.random());

					double x = r * Math.cos(a);
					double y = r * Math.sin(a);
					pointList.add(new Point((int) x, (int) y));
					pointsCounter++;
				}else if(!spaceEffect && pointsCounter>0) {
					pointList.remove(0);

					pointsCounter--;
				}
			}
		}else {
		
			if ((key == SWT.ARROW_LEFT) && pointsCounter > 0) {

				pointList.remove(0);

				pointsCounter--;
			}

			if ((key == SWT.ARROW_RIGHT)) {

				double a = Math.random() * 2 * Math.PI;
				double r = 150 * Math.sqrt(Math.random());

				double x = r * Math.cos(a);
				double y = r * Math.sin(a);
				pointList.add(new Point((int) x, (int) y));
				pointsCounter++;


			}
		}

		if(key == 13) {

			if(firstTry) {
				firstTry = false;
				pointsCounter = 0;
				pointList.clear();
				spaceEffect = true;
				
				new picWin(display);
			}else {
			
				testResult.put(testFiles[fileNumber].toString(), pointsCounter);

				testCounter++;
				fileNumber++;
				pointsCounter = 0;
				pointList.clear();
				spaceEffect = true;
				
				if(fileNumber == testFiles.length) {
					writeResultFile();
					inGame = false;
				}else {
					new picWin(display);
				}
			}
			
			
		}





	}
	
	public void writeResultFile() {
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
	}
	
	private class picWin {

		Shell shell;

		private int counter = 0;
		private int maxSeconds = mainWin.pauseTimeInputValue; 

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
			shell.setMaximized(true);
			shell.open();

			final Image image = new Image(display,  testFiles[fileNumber].toString());

			//get Image width and height
			ImageData imgData = image.getImageData();
			imgData=imgData.scaledTo((int) (screenSize.getWidth()),(int) (screenSize.getHeight()));

			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] {imgData};

			imageLoader.save( testFiles[fileNumber].toString(), SWT.IMAGE_COPY);


			Label picLabel = new Label(shell, SWT.NONE);
			picLabel.setImage(SWTResourceManager.getImage(testFiles[fileNumber].toString()));
			picLabel.setBounds( 0,0,imgData.width,imgData.height);

			final Runnable timer = new Runnable()
			{
				public void run()
				{
					if(counter <= maxSeconds) {
						counter++;
						display.timerExec(1000, this);
					}
					else
						shell.dispose();

				}
			};

			display.timerExec(0, timer);
		}

	}

}
