

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Calendar;
import java.util.Date;

public class testeCor {

	public static void main(String[] args) throws AWTException, InterruptedException {
		// TODO Auto-generated method stub
		Thread.sleep(5000);
		
		Robot celso = new Robot();
		System.out.println(celso.getPixelColor(322, 281));
		celso.setAutoDelay(500);

		

	}

}
