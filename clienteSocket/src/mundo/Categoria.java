package mundo;

public class Categoria {

	private String rutaImg;
	private String nombre;

	public Categoria(String pNombre, String pRutaImg)
	{
		rutaImg=pRutaImg;
		nombre=pNombre;
	}

	public String darRutaImg() {
		return rutaImg;
	}

	public String darNombre() {
		return nombre;
	}

}
