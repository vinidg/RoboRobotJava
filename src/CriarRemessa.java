import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CriarRemessa {

	public static void main(String[] args) throws IOException, InterruptedException, AWTException,
			IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Acessar acessar = new Acessar();
		acessar.sistemas();
	}

	public static class Acessar {
		public void sistemas() throws IOException, InterruptedException, AWTException, IllegalArgumentException,
				IllegalAccessException, NoSuchFieldException, SecurityException {

			Process process = null;
			Robot celso = new Robot();
			Runtime runTime = Runtime.getRuntime();
			celso.setAutoDelay(500);
			CriarLog criarLog = new CriarLog();

			try {

				System.out.println("==== Abrindo área de trabalho remota ====");
				process = runTime.exec("mstsc /f");

				Thread.sleep(3000);

				celso.keyPress(KeyEvent.VK_ENTER);
				celso.keyRelease(KeyEvent.VK_ENTER);

				loading(celso, new Color(0, 0, 0), "Loading Área de Trabalho");

				System.out.println("\n==== Abrindo sistema financeiro ====");

				try {
					celso.mouseMove(838, 33);
					celso.mousePress(InputEvent.BUTTON1_MASK);
					celso.mouseRelease(InputEvent.BUTTON1_MASK);

					Color corAlvo = new Color(254, 255, 0);
					for (int y = 0; y <= 700; y++) {
						Color cor = celso.getPixelColor(46, y);
						if (cor.getRGB() == corAlvo.getRGB()) {
							celso.mouseMove(46, y);
							celso.mousePress(InputEvent.BUTTON1_MASK);
							celso.mouseRelease(InputEvent.BUTTON1_MASK);
							celso.keyPress(KeyEvent.VK_ENTER);
							celso.keyRelease(KeyEvent.VK_ENTER);

							break;
						} else if (y == 700) {
							System.out.println("\n==== Icone não encontrado ====");
							criarLog.GerarLog("==== Icone não encontrado ====");
							process.destroy();
							return;
						}
					}
				} catch (Exception e) {
					System.out.println("\n==== Erro não encontrado ====");
					return;
				}

				escreverNoTeclado(celso, "vinicius");

				celso.keyPress(KeyEvent.VK_TAB);
				celso.keyRelease(KeyEvent.VK_TAB);

				escreverNoTeclado(celso, "vini123");

				celso.keyPress(KeyEvent.VK_ENTER);
				celso.keyRelease(KeyEvent.VK_ENTER);

				loading(celso, new Color(128, 128, 128), "\nLoading Financeiro");

				celso.mouseMove(190, 40);

				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				celso.mouseMove(244, 91);

				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				celso.mouseMove(502, 91);

				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				celso.mouseMove(764, 91);

				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				celso.mouseMove(366, 100);

				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				System.out.println("\n==== Gerando arquivo de remessa ====");

				celso.mouseMove(20, 153);

				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				Calendar c = Calendar.getInstance();
				c.add( Calendar.MONTH , 1);
				Date date = c.getTime();
				SimpleDateFormat dt1 = new SimpleDateFormat("ddMMyyyy");
				
				escreverNumeros(celso, stringParaIntFloat(dt1.format(date)));

				celso.mouseMove(175, 152);

				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				celso.mouseMove(177, 177);

				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				celso.getPixelColor(141, 256);

				Color corAlvo = new Color(51, 153, 255);

				for (int i = 144; i <= 244; i++) {
					Color cor = celso.getPixelColor(i, 256);
					System.out.println("Cor = " + cor.getRed() + ", " + cor.getGreen() + ", " + cor.getBlue());
					if (cor.getRGB() != corAlvo.getRGB()) {
						System.out.println("Mudança de cor " + i + " " + 256 + "\n");
						System.out.println("==== Possui boleto(s) em aberto ====");
						celso.mouseMove(748, 108);

						celso.mousePress(InputEvent.BUTTON1_MASK);
						celso.mouseRelease(InputEvent.BUTTON1_MASK);

						celso.keyPress(KeyEvent.VK_ENTER);
						celso.keyPress(KeyEvent.VK_ENTER);

						celso.keyPress(KeyEvent.VK_ENTER);
						celso.keyPress(KeyEvent.VK_ENTER);

						break;
					} else if (i == 244) {
						System.out.println("==== Não foi encontrado nenhum boleto, tente mais tarde ! ====");
						Thread.sleep(4000);
						process.destroy();
						criarLog.GerarLog("==== Sem boleto ====");
					}
				}
				criarLog.GerarLog("==== Sucesso ! ====");
			} finally {
				Thread.sleep(4000);
				process.destroy();
				criarLog.GerarLog("==== Finalizado ====");
				criarLog.Salvar();
			}
		}

		public void escreverNoTeclado(Robot bot, String st)
				throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
			String upperCase = st.toUpperCase();
			bot.setAutoDelay(100);
			for (int i = 0; i < upperCase.length(); i++) {
				String letter = Character.toString(upperCase.charAt(i));
				String code = "VK_" + letter;

				Field f = KeyEvent.class.getField(code);
				int keyEvent = f.getInt(null);

				bot.keyPress(keyEvent);
				bot.keyRelease(keyEvent);
			}
			bot.setAutoDelay(500);
		}

		public int[] stringParaIntFloat(String v) {
			int strLength = v.length();
			int[] intArray = new int[strLength];
			int i = 0;
			for (i = 0; i < strLength; i++) {
				if (!Character.isDigit(v.charAt(i))) {
					System.out.println("Contém digito inválido");
					break;
				}
				intArray[i] = Integer.parseInt(String.valueOf(v.charAt(i)));
			}
			return intArray;
		}

		public void escreverNumeros(Robot bot, int[] digitos) {
			for (int digito : digitos) {

				switch (digito) {
				case 0:
					bot.keyPress(KeyEvent.VK_NUMPAD0);
					bot.keyRelease(KeyEvent.VK_NUMPAD0);
					break;
				case 1:
					bot.keyPress(KeyEvent.VK_NUMPAD1);
					bot.keyRelease(KeyEvent.VK_NUMPAD1);
					break;
				case 2:
					bot.keyPress(KeyEvent.VK_NUMPAD2);
					bot.keyRelease(KeyEvent.VK_NUMPAD2);
					break;
				case 3:
					bot.keyPress(KeyEvent.VK_NUMPAD3);
					bot.keyRelease(KeyEvent.VK_NUMPAD3);
					break;
				case 4:
					bot.keyPress(KeyEvent.VK_NUMPAD4);
					bot.keyRelease(KeyEvent.VK_NUMPAD4);
					break;
				case 5:
					bot.keyPress(KeyEvent.VK_NUMPAD5);
					bot.keyRelease(KeyEvent.VK_NUMPAD5);
					break;
				case 6:
					bot.keyPress(KeyEvent.VK_NUMPAD6);
					bot.keyRelease(KeyEvent.VK_NUMPAD6);
					break;
				case 7:
					bot.keyPress(KeyEvent.VK_NUMPAD7);
					bot.keyRelease(KeyEvent.VK_NUMPAD7);
					break;
				case 8:
					bot.keyPress(KeyEvent.VK_NUMPAD8);
					bot.keyRelease(KeyEvent.VK_NUMPAD8);
					break;
				case 9:
					bot.keyPress(KeyEvent.VK_NUMPAD9);
					bot.keyRelease(KeyEvent.VK_NUMPAD9);
					break;
				}

			}
		}

		public void loading(Robot roboto, Color corAlvo, String Mensagem) {
			int[] x = { 200, 350, 200, 350 };
			int[] y = { 150, 250, 150, 250 };

			Color cor = null;
			try {

				System.out.print(Mensagem + " ");
				for (int j = 0; j < 50; j++) {
					for (int valorX : x) {
						for (int valorY : y) {
							cor = roboto.getPixelColor(valorX, valorY);
							roboto.mouseMove(valorX, valorY);

							if (cor.getRGB() == corAlvo.getRGB()) {
								j = 1000;
								return;
							}
							Thread.sleep(300);
							System.out.print(".");
							break;
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				
			}
		}
	}

}