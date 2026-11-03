/**
 * Interfejs systemu akukcyjnego
 *
 */
public interface Aukcja {

	/**
	 * Interfejs reprezentujący przedmiot aukcji.
	 */
	public interface PrzedmiotAukcji {
		/**
		 * Unikalny, liczbowy identyfikator przedmiotu. Każdy przedmiot aukcji będzie
		 * posiadać inny identyfikator.
		 * 
		 * @return identyfikator liczbowy
		 */
		public int identyfikator();

		/**
		 * Nazwa przedmiotu aukcji
		 * 
		 * @return nazwa przedmiotu
		 */
		public String nazwaPrzedmiotu();

		/**
		 * Nowa oferta za przedmiot. Dla Uproszczenia, aby uniknąć ułamków, oferta
		 * podawana jest w groszach. W przypadku użycia w powiadomieniach metoda
		 * przekazuje ofertę, która przebiła kwotę zaoferowaną przez użytkownika serwisu
		 * aukcyjnego. Niekoniecznie aktualna oferta będzie większa od aktualnej ceny.
		 * 
		 * @return aktualna oferta w groszach
		 */
		public int aktualnaOferta();

		/**
		 * Aktualna cena za przedmiot. Dla proszczenia, aby uniknąć ułamków, cena
		 * podawana jest w groszach. W momencie dodawania przedmiotu do aukcji pozwala
		 * na przekazanie ceny minimalnej. W przypadku użycia w powiadomieniach metoda
		 * przekazuje ofertę, która aktualnie jest najwyższa.
		 * 
		 * @return aktualna cena w groszach
		 */
		public int aktualnaCena();

	}

	/**
	 * Powiadomienie użytkownika o przebiciu jego oferty.
	 */
	public interface Powiadomienie {
		/**
		 * Przekazanie informacji o przebiciu oferty na obserwowany przedmiot aukcji.
		 * 
		 * @param przedmiot obiekt reprezentujÄcy przedmiot aukcji z ustawionÄ aktualnÄ
		 *                  ofertÄ.
		 */
		public void przebitoTwojąOfertę(PrzedmiotAukcji przedmiot);
	}

	/**
	 * Metoda do dodawania uĹźytkowika do systemu aukcyjnego. UĹźytkownicy rozrĂłĹźniani
	 * sÄ za pomocÄ ich unikalnego username.
	 * 
	 * @param username unikalne nazwa uĹźytkownika
	 * @param kontakt  obiekt za pomocÄ naleĹźy powiadamiaÄ tego uĹźytkownika, gdy
	 *                 ktoĹ inny przebije ofertÄ na przedmiot, ktĂłrym uĹźytkownik
	 *                 jest zainteresowany.
	 */
	public void dodajUżytkownika(String username, Powiadomienie kontakt);

	/**
	 * Metoda pozwala na dodanie przedmiotu do serwisu aukcyjnego.
	 * 
	 * @param przedmiot dodawany do serwisu przedmiot
	 */
	public void dodajPrzedmiotAukcji(PrzedmiotAukcji przedmiot);

	/**
	 * UĹźytkownik o podanym username zgĹasza zainteresowanie przedmiotem aktucji o
	 * podanym identyfikatorze. Od chwili wykonania tej metody uĹźytkownik jest
	 * powiadamiany kaĹźdorazowo, gdy jego oferta zostanie przebita.
	 * 
	 * @param username                      nazwa uĹźytkownika serwisu
	 * @param identyfikatorPrzedmiotuAukcji identyfikator przedmiotu aukcji
	 */
	public void subskrypcjaPowiadomień(String username, int identyfikatorPrzedmiotuAukcji);

	/**
	 * Metoda koĹczy obserwacjÄ danego przedmiotu przez uĹźytkownika o podanym
	 * username. Rezygnacja z powiadomieĹ oznacza zaprzestanie wysyĹania
	 * powiadomieĹ.
	 * 
	 * @param username                      nazwa uĹźytkownika serwisu
	 * @param identyfikatorPrzedmiotuAukcji identyfikator przedmiotu aukcji
	 */
	public void rezygnacjaZPowiadomień(String username, int identyfikatorPrzedmiotuAukcji);

	/**
	 * UĹźytkownik przekazuje ofertÄ zakupu przedmiotu za podanÄ kwotÄ. Wszystkie
	 * osoby obserwujÄce ten sam przedmiot, a oferujÄce niĹźszÄ kwotÄ powinny zostaÄ
	 * automatycznie powiadomione o przebiciu ich oferty.
	 * 
	 * @param username                      nazwa uĹźytkownika serwisu
	 * @param identyfikatorPrzedmiotuAukcji identyfikator przedmiotu aukcji
	 * @param oferowanaKwota                zaoferowana kwota w groszach
	 */
	public void oferta(String username, int identyfikatorPrzedmiotuAukcji, int oferowanaKwota);

	/**
	 * Za pomocÄ tej metody zamykana jest aukcja. Najlepsza oferta wygrywa. Nowe
	 * oferty sÄ ignorowane.
	 * 
	 * @param identyfikatorPrzedmiotuAukcji identyfikator przedmiotu aukcji
	 */
	public void koniecAukcji(int identyfikatorPrzedmiotuAukcji);

	/**
	 * Metoda pozwala poznaÄ nazwÄ uĹźytkownika, ktĂłry zaoferowaĹ nawyĹźszÄ kwotÄ za
	 * przedmiot aukcji. JeĹli aukcja zostaĹa zakoĹczona, metoda pozwala poznaÄ dane
	 * osoby, ktĂłra aukcjÄ na dany przedmiot wygraĹa.
	 * 
	 * @param identyfikatorPrzedmiotuAukcji identyfikator przedmiotu aukcji
	 * @return nazwa uĹźytkownika wygrywajÄcego aukcjÄ
	 */
	public String ktoWygrywa(int identyfikatorPrzedmiotuAukcji);

	/**
	 * Metoda pozwala poznaÄ najlepszÄ ofertÄ za dany przedmiot. Kwota podawna jest
	 * w groszach.
	 * 
	 * @param identyfikatorPrzedmiotuAukcji identyfikator przedmiotu aukcji
	 * @return najwyĹźsza ofera za przedmiot
	 */
	public int najwyższaOferta(int identyfikatorPrzedmiotuAukcji);

}