package mundo;

public class Archivo {

	private String rutaImg;
	private int tamano;
	private String ruta;
	public final static String RUTA_MP4="./data/mp4.png";
	public final static String RUTA_TXT="./data/txt.png";
	public final static String RUTA_PDF="./data/pdf.png";
	
	public Archivo(String pRuta, int pTamano)
	{
		ruta=pRuta;
		if(ruta.endsWith("mp4"))
		{
			rutaImg=RUTA_MP4;
		}
		else if(ruta.endsWith("txt"))
		{
			rutaImg=RUTA_TXT;
		}
		else if(ruta.endsWith("pdf"))
		{
			rutaImg=RUTA_PDF;
		}
		else
		{
			rutaImg=RUTA_PDF;
		}
		tamano=pTamano;
	}
	
	public String darRutaImg() {
		return rutaImg;
	}
	
	public String darRuta() {
		return ruta;
	}

	public Integer darTamano() {
		return tamano;
	}

	public String darNombre() {
		String[]partes= ruta.split("\\.");
		System.out.println("r "+ruta);
		return partes[0];
	}
}