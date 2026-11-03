
public interface SimpleDrawing {
	/**
	 * Metoda umozliwia przekazanie obiektu Geometry.
	 * 
	 * @param input obiekt Geometry
	 */
	public void setCanvasGeometry(Geometry input);

	/**
	 * Metoda zleca narysowanie pojedynczego odcinka. Poczatke pierwszego odcinka
	 * wskazuje obiekt Geometry. Kolejny odcinek zaczyna siÄe tam, gdzie poprzedni
	 * sie zakonczyl. Pierwszy punkt nowego odcinka nadpisuje ostatni punkt
	 * poprzedniego. 
	 * UWAGA: obszar plotna jest ograniczony. Jesli dlugosc zleconego do narysowania
	 * odcinka jest zbyt duza i spowodowaĹlaby wyjscie poza obszar plottna, nalezy
	 * odpowiednio dĹlugosc zmniejszyÄc, tak, aby ostatni "piksel" odcinka wypadĹ na
	 * brzegu plotna.
	 * 
	 * @param segment - segment do narysowania
	 */
	public void draw(Segment segment);

	/**
	 * Metoda zwraca dwuwymiatowa tablice liczb calkowitych reprezentujaca aktualny
	 * stan obrazka. Przed ustawieniem obiektu Geometry metoda zwraca null.
	 * 
	 * @return tablica zawierajaca obrazek
	 */
	public int[][] getPainting();

	/**
	 * Metoda czysci plotno (ustawia wszystkie pozycje-"pixele" na 0).
	 */
	public void clear();
}