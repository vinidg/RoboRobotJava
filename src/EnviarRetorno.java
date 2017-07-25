import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EnviarRetorno {
	private static String dataRemessa = "";

	AcessarSistema sistema = new AcessarSistema();
	Calendar c = Calendar.getInstance();
	Date data = c.getTime();
	DateFormat f = DateFormat.getDateInstance(DateFormat.SHORT);

	TimerTask task = new TimerTask() {
		public void run() {
			if (dataRemessa.equals("")) {
				System.out.println("...@...");
				dataRemessa = f.format(data);
				try {
					sistema.sistemas(dataRemessa);
				} catch (InterruptedException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException
						| SecurityException | IOException | AWTException | ParseException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	public void getInputData() throws Exception {
		Timer timer = new Timer();
		timer.schedule(task, 10 * 1000);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Digite a data de vencimento ou aperte ENTER para seguir com a data atual: ");
		dataRemessa = in.readLine();

		if (!dataRemessa.isEmpty()) {
			try {
				@SuppressWarnings("unused")
				Date dataValida = sdf.parse(dataRemessa);
				try {
					sistema.sistemas(dataRemessa);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (ParseException e) {
				System.err.println("Data inválida");
				return;
			}
		}

		timer.cancel();
	}

	public static void main(String[] args) throws IOException, InterruptedException, AWTException,
			IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		CriarLog criarLog = new CriarLog();
		try {
			(new EnviarRetorno()).getInputData();
		} catch (Exception e) {
			System.out.println(e);
			criarLog.GerarLog("==== Sem boleto ====");
		}
	}

	public static class AcessarSistema {
		public void sistemas(String dataRemessa) throws Exception {

			CriarLog criarLog = new CriarLog();
			Process process = null;
			Robot celso = new Robot();
			Runtime runTime = Runtime.getRuntime();
			celso.setAutoDelay(500);

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
					Color corParar = new Color(9, 26, 70);
					for (int y = 0; y <= 700; y++) {
						Color cor = celso.getPixelColor(46, y);
						if (cor.getRGB() == corAlvo.getRGB()) {
							celso.mouseMove(46, y);
							celso.mousePress(InputEvent.BUTTON1_MASK);
							celso.mouseRelease(InputEvent.BUTTON1_MASK);
							celso.keyPress(KeyEvent.VK_ENTER);
							celso.keyRelease(KeyEvent.VK_ENTER);
							break;
						} else if (cor.getRGB() == corParar.getRGB()) {
							break;
						}
						if (y == 700) {
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

				celso.mouseMove(764, 114);
				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				celso.mouseMove(73, 105);
				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);
				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				// ITAU
				celso.mouseMove(63, 105);
				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				celso.mouseMove(63, 130);
				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				File file = new File("C:/Users/suport/Desktop/Remessas/Remessa itau/Retorno/");
				File[] files = file.listFiles();
				File lastModified = files[0];
				System.out.println("\n==== Identificando ultima remessa ====");
				for (int i = 0; i < files.length; i++) {
					if (lastModified.lastModified() < files[i].lastModified()) {
						lastModified = files[i];
					}
				}

				escreverNoTeclado(celso,
						"//tsclient/C/Users/suport/Desktop/Remessas/Remessa itau/Retorno/" + lastModified.getName());

				celso.mouseMove(686, 143);
				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				Thread.sleep(3000);
				celso.mouseMove(1236, 598);
				celso.mousePress(InputEvent.BUTTON1_MASK);
				celso.mouseRelease(InputEvent.BUTTON1_MASK);

				Color entrada = new Color(192, 220, 192);
				Color liquido = new Color(166, 202, 240);
				// TODO Aprimorar fazendo com que o cursor vá da esquerda pra direita, de cima para baixo
				int j = 25;
				for (int i = 187; i <= 280; i++) {
					Color cor = celso.getPixelColor(j, i);
					System.out.println("Cor = " + cor.getRed() + ", " + cor.getGreen() + ", " + cor.getBlue());
					celso.mouseMove(j, i);
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
					j++;
					}
				criarLog.GerarLog("==== Gerado ! ====");

			} finally {
				Thread.sleep(4000);
				criarLog.Salvar();
				process.destroy();
			}
		}

		public static boolean isUpperCase(String string) {
			return string.toUpperCase().equals(string);
		}

		public void escreverNoTeclado(Robot bot, String st) throws Exception {
			String upperCase = st.toUpperCase();
			bot.setAutoDelay(80);
			for (int i = 0; i < upperCase.length(); i++) {
				String letter = Character.toString(upperCase.charAt(i));
				if (letter.contains(":")) {
					bot.keyPress(KeyEvent.VK_SHIFT);
					bot.keyPress(KeyEvent.VK_SEMICOLON);
					bot.keyRelease(KeyEvent.VK_SEMICOLON);
					bot.keyRelease(KeyEvent.VK_SHIFT);
					letter = "";
				} else if (letter.contains("/")) {
					alt(bot, KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD9, KeyEvent.VK_NUMPAD2);
					letter = "";
				} else if (letter.contains(" ")) {
					bot.keyPress(KeyEvent.VK_SPACE);
					bot.keyRelease(KeyEvent.VK_SPACE);
					letter = "";
				} else if (letter.contains(".")) {
					bot.keyPress(KeyEvent.VK_PERIOD);
					bot.keyRelease(KeyEvent.VK_PERIOD);
					letter = "";
				}
				String code = "VK_" + letter;
				if (!letter.isEmpty()) {
					Field f = KeyEvent.class.getField(code);
					int keyEvent = f.getInt(null);
					bot.keyPress(keyEvent);
					bot.keyRelease(keyEvent);
				}
			}
			bot.setAutoDelay(400);
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

		public static void alt(Robot bot, int event1, int event2, int event3, int event4) throws Exception {

			bot.keyPress(KeyEvent.VK_ALT);

			bot.keyPress(event1);
			bot.keyRelease(event1);

			bot.keyPress(event2);
			bot.keyRelease(event2);

			bot.keyPress(event3);
			bot.keyRelease(event3);

			bot.keyPress(event4);
			bot.keyRelease(event4);

			bot.keyRelease(KeyEvent.VK_ALT);

		}

	}

}