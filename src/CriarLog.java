import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CriarLog {

	Calendar c = Calendar.getInstance();
	Date date = c.getTime();
	SimpleDateFormat dt1 = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
	
	String data = dt1.format(date);
	
	FileWriter arq = null;
	
	public void GerarLog(String mensagem) throws IOException{

	arq = new FileWriter("C:/Remessador/log/Log_" + data);
	
	PrintWriter gravarArq = new PrintWriter(arq);
 
    gravarArq.printf("%n"+mensagem);
 
    
	}
	
	public void Salvar() throws IOException{
		arq.close();
		 
	    System.out.printf("\nLog salvo em:  \"C:/Remessador/log/"+arq+"\".\n");
	}
    
}
