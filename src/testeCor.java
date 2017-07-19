

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class testeCor {

	public static void main(String[] args) throws AWTException, InterruptedException {
		Thread.sleep(5000);
		int x = 256;
		int y = 207;
		Robot celso = new Robot();
		celso.mouseMove(x, y);
		System.out.println(celso.getPixelColor(x, y));
		celso.setAutoDelay(500);

		Color entrada = new Color(192, 220, 192);
		Color liquido = new Color(166, 202, 240);

		for (int i = 187; i <= 280; i++) {
			Color cor = celso.getPixelColor(256 ,i);
			System.out.println("Cor = " + cor.getRed() + ", " + cor.getGreen() + ", " + cor.getBlue());
			celso.mouseMove(256, i);
			if (cor.getRGB() == entrada.getRGB() || cor.getRGB() == liquido.getRGB()) {
				System.out.println("==== Possui boleto(s) ====");
				celso.mouseMove(752, 110);

				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				celso.keyPress(KeyEvent.VK_ENTER);
				celso.keyRelease(KeyEvent.VK_ENTER);
				celso.keyPress(KeyEvent.VK_ENTER);
				celso.keyRelease(KeyEvent.VK_ENTER);

				break;
			} else if (i == 280) {
				System.out.println("==== Não foi encontrado nenhum boleto, tente mais tarde ! ====");
				Thread.sleep(4000);
			}
		}

	}

}
