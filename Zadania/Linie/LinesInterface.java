import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LinesInterface {

	/**
	 * Punkt
	 */
	public interface Point {
		/**
		 * Nazwa punktu. różne punkty mają rózne nazwy
		 * 
		 * @return nazwa odcinka
		 */
		public String getName();
	}

	/**
	 * Odcinek. Odcinek nie ma kierunku. równie dobrze prowadzi od krańca 1 do 2 jak
	 * i od 2 do 1.
	 */
	public interface Segment {
		/**
		 * Pierwszy kraniec odcinka
		 * 
		 * @return punkt będący pierwszym krańcem odcinka
		 */
		public Point getEndpoint1();

		/**
		 * Drugi kraniec odcinka
		 * 
		 * @return punkt będący drugim krańcem odcinka
		 */
		public Point getEndpoint2();
	}

	/**
	 * Metoda pozwala na dodanie zbioru punktów.
	 * 
	 * @param points zbiór punktów
	 */
	public void addPoints(Set<Point> points);

	/**
	 * Metoda pozwala na dodanie zbioru odcinków łączących punkty.
	 * 
	 * @param segments zbiór odcinków
	 */
	public void addSegments(Set<Segment> segments);

	/**
	 * Metoda wyszukuje polaczenie pomiedzy para punktow. kazde poprawne polaczenie
	 * podanej pary i bez petli uznane zostanie za poprawne. W przypadku braku
	 * polaczenia metoda zwraca liste o rozmiarze 0.
	 * 
	 * @param start punkt poczatkowy
	 * @param end   punkt koncowy
	 * @return lista kolejnych odcinkow prowadzacych od start do end.
	 */
	public List<Segment> findConnection(Point start, Point end);

	/**
	 * Metoda generuje mape, ktorej kluczem jest punkt. Punkt wskazuje na zbior
	 * odcinkow, ktorych jednym z punktów krancowych jest ten punkt.
	 * 
	 * @return mapa powiazań punktow z odcinkami
	 */
	public Map<Point, Set<Segment>> getMapEndpointToSegments();

	/**
	 * Metoda generuje mape, ktorej kluczem jest punkt. Punkt jest punktem startowym
	 * sciezek. We wskazywanej przez punkt mapie maja znalezc sie punkty, ktore
	 * mozna osiagnac poprzez trase zawierajaca 1, 2, 3 lub 4 odcinki. Jesli trasa o
	 * okreslone dlugosci nie istnieje zbior punktow nia dostepny jest zbiorem
	 * pustym (zbior o rozmiarze 0).
	 * 
	 * @return mapa powiazan punktow z innymi punktami
	 */
	public Map<Point, Map<Integer, Set<Point>>> getReachableEndpoints();

}