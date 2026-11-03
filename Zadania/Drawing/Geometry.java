
public interface Geometry {
	/**
	 * Rozmiar plotna (dwuwymiarowej tablicy). Dla uproszczenia zakladamy, ze
	 * tablica jest kwadratowa.
	 * 
	 * @return rozmiar boku plotna
	 */
	public int getSize();

	/**
	 * Pierwsza wspĂłĹrzÄdna (indeks) pozycji, od ktĂłrej rysowany bÄdzie pierwszy
	 * odcinek. Pierwszy odcinek zaczyna siÄ na pozycji:
	 * <tt>canvas[ getInitialFirstCoordinate() ][ getInitialSecondCoordinate() ]</tt>
	 * 
	 * @return pierwsza wspĂłĹrzÄdna punktu, od ktĂłrego rozpocznie siÄ rysowanie
	 */
	public int getInitialFirstCoordinate();

	/**
	 * Druga wspĂłĹrzÄdna (indeks) pozycji, od ktĂłrej rysowany bÄdzie pierwszy
	 * odcinek. Pierwszy odcinek zaczyna siÄ na pozycji:
	 * <tt>canvas[ getInitialFirstCoordinate() ][ getInitialSecondCoordinate() ]</tt>
	 * 
	 * @return druga wspĂłĹrzÄdna punktu, od ktĂłrego rozpocznie siÄ rysowanie
	 */
	public int getInitialSecondCoordinate();
}