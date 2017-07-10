

import java.awt.AWTException;
import java.awt.Robot;

public class testeCor {

	public static void main(String[] args) throws AWTException, InterruptedException {
		Thread.sleep(5000);
		int x = 759;
		int y = 360;
		Robot celso = new Robot();
		celso.mouseMove(x, y);
		System.out.println(celso.getPixelColor(x, y));
		celso.setAutoDelay(500);

		

	}

}
