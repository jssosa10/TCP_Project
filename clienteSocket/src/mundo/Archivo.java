package mundo;

public class Archivo {

	private String rutaImg;
	private int tamano;
	private String ruta;
	public final static String RUTA_MP4="./data/mp4.png";
	public final static String RUTA_TXT="./data/txt.png";
	public final static String RUTA_PDF="./data/pdf.png";
	public final static String RUTA_MD="./data/md.png";
	public final static String RUTA_LANGUAGE="./data/language.png";
	
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
		else if(ruta.endsWith("md"))
		{
			rutaImg=RUTA_MD;
		}
		else if(ruta.endsWith("py") || ruta.endsWith("cpp"))
		{
			rutaImg=RUTA_LANGUAGE;
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
		System.out.println(ruta);
		return ruta.substring(0,ruta.lastIndexOf("."));
	}
}