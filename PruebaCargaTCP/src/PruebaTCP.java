import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class PruebaTCP {

	public static void main(String[] args) throws Exception {

		try(Socket socket=new Socket("35.173.252.14",9000); InputStream in= socket.getInputStream(); PrintWriter out = new PrintWriter(socket.getOutputStream(), true))
		{
			out.println("Holiwi");
			out.flush();

			byte[] buffer = new byte[6]; // or 4096, or more
			BufferedReader bf= new BufferedReader(new InputStreamReader(in));		
			String text=bf.readLine();
			char[] chars=text.toCharArray();
			if(!text.equals("Holiwi"))
			{
				throw new Exception("No se recibió lo que se esperaba");
			}

			out.println("GRANDE");
			text=bf.readLine();
			text=text.trim();
			String[] archivoStr= text.split(";");
			for (int i = 0; i < archivoStr.length; i++) 
			{
				String[] datos= archivoStr[i].trim().split(":::");
				Integer.parseInt(datos[1]);
			}

			out.println("DESCARGA:::GRANDE:::arch1.mp4");
			buffer = new byte[1024]; // or 4096, or more
			int cantBytes=0;
			int count;
			FileOutputStream outP = new FileOutputStream("./data/"+Math.random()+"arch1.mp4");
			int tamArchivo=1400000;
			while ((count = in.read(buffer)) > 0)
			{
				outP.write(buffer, 0, count);
				for (int i = 0; i < buffer.length; i++) {
					cantBytes++;
				}				
				int porcentaje= cantBytes*100/tamArchivo;
				if(cantBytes>=tamArchivo)
				{
					break;
				}
			}
			out.println("Adios");
			out.flush();
			out.close();
			in.close();
			socket.close();
		}
		catch(Exception e)
		{
			throw new Exception();
		}
	}
}
