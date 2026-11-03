import java.util.*;


public class SerwisAukcyjny implements Aukcja {

	public class Powiadomienie implements Aukcja.Powiadomienie{

		@Override
		public void przebitoTwojąOfertę(Aukcja.PrzedmiotAukcji przedmiot) {
			System.out.println(przedmiot);
		}
		
	}
	
	public class PrzedmiotAukcji implements Aukcja.PrzedmiotAukcji{
		public  int id=0;
		public String nazwa;
		public int cena;
		public int oferta;
		
		@Override
		public int identyfikator() {
			return id;
		}

		@Override
		public String nazwaPrzedmiotu() {
			return nazwa;
		}

		@Override
		public int aktualnaOferta() {
			return oferta;
		}

		@Override
		public int aktualnaCena() {
			return cena;
		}
		

	}
	
	Map<String, Aukcja.Powiadomienie> uzytkownicy = new HashMap<>();
	Map<Integer, Aukcja.PrzedmiotAukcji> przedmioty = new HashMap<>();
	Map<Integer, String> najwyzszeOferty = new HashMap<>();
	Map<Integer, Set<String>> subskrypcje = new HashMap<>();
	Set<Integer> zakonczoneAukcje = new HashSet<>();
	
	@Override
	public void dodajUżytkownika(String username, Aukcja.Powiadomienie kontakt) {
		if (uzytkownicy.get(username) == null)
			uzytkownicy.put(username, kontakt);
	}

	@Override
	public void dodajPrzedmiotAukcji(Aukcja.PrzedmiotAukcji przedmiot) {
		przedmioty.put(przedmiot.identyfikator(), przedmiot);		
	}

	@Override
	public void subskrypcjaPowiadomień(String username, int identyfikatorPrzedmiotuAukcji) {
		if (subskrypcje.get(identyfikatorPrzedmiotuAukcji) == null) {
			Set<String> s = new HashSet<>();
			s.add(username);
			subskrypcje.put(identyfikatorPrzedmiotuAukcji, s);
		}
		else {
			subskrypcje.get(identyfikatorPrzedmiotuAukcji).add(username);
		}
	}

	@Override
	public void rezygnacjaZPowiadomień(String username, int identyfikatorPrzedmiotuAukcji) {
		if (subskrypcje.get(identyfikatorPrzedmiotuAukcji) == null) {
			return;
		}
		else {
			subskrypcje.get(identyfikatorPrzedmiotuAukcji).remove(username);
		}

	}
	
	private void ustalWlasciciela(int identyfikatorPrzedmiotuAukcji, String username) {
		najwyzszeOferty.put(identyfikatorPrzedmiotuAukcji, username);
	}
	
	private void wyslijPowiadomienia(int identyfikatorPrzedmiotuAukcji, String wlasciciel){
		Set<String> subskrybenci = subskrypcje.get(identyfikatorPrzedmiotuAukcji);
		if (subskrybenci == null) return;
		for (String uname : subskrybenci) {
			if (uname == wlasciciel) continue;
			Aukcja.Powiadomienie kontakt = uzytkownicy.get(uname);
			kontakt.przebitoTwojąOfertę(przedmioty.get(identyfikatorPrzedmiotuAukcji));
		}
	}
	
	@Override
	public void oferta(String username, int identyfikatorPrzedmiotuAukcji, int oferowanaKwota) {
		if (zakonczoneAukcje.contains(identyfikatorPrzedmiotuAukcji)) return;
		Aukcja.PrzedmiotAukcji przedmiot = przedmioty.get(identyfikatorPrzedmiotuAukcji);
		
		if (przedmiot.aktualnaCena() < oferowanaKwota) 
		{	
			PrzedmiotAukcji new_przedmiot = new PrzedmiotAukcji();
			new_przedmiot.cena = oferowanaKwota;
			new_przedmiot.id = przedmiot.identyfikator();
			new_przedmiot.nazwa = przedmiot.nazwaPrzedmiotu();
			new_przedmiot.oferta = oferowanaKwota;
			
			//przedmioty.remove(identyfikatorPrzedmiotuAukcji);
			dodajPrzedmiotAukcji(new_przedmiot);
			ustalWlasciciela(identyfikatorPrzedmiotuAukcji, username);
			
			wyslijPowiadomienia(identyfikatorPrzedmiotuAukcji, username);
		}
	}

	

	@Override
	public void koniecAukcji(int identyfikatorPrzedmiotuAukcji) {
		zakonczoneAukcje.add(identyfikatorPrzedmiotuAukcji);
	}

	@Override
	public String ktoWygrywa(int identyfikatorPrzedmiotuAukcji) {
		return najwyzszeOferty.get(identyfikatorPrzedmiotuAukcji);
	}

	@Override
	public int najwyższaOferta(int identyfikatorPrzedmiotuAukcji) {
		Aukcja.PrzedmiotAukcji p = przedmioty.get(identyfikatorPrzedmiotuAukcji);
		return p.aktualnaCena();
	}
/*
	public static void main(String[] args) {
		SerwisAukcyjny serwis = new SerwisAukcyjny();
		
		Powiadomienie pow = serwis.new Powiadomienie();
		serwis.dodajUżytkownika("Adam", pow);
	
		PrzedmiotAukcji p = serwis.new PrzedmiotAukcji();
		p.id = 1;
		p.cena = 100;
		p.nazwa = "Item";
		p.oferta = 100;
		serwis.dodajPrzedmiotAukcji(p);
		
		serwis.oferta("Adam", 1, 110);
		
		Map<Integer, Integer> mapa = new HashMap<>();
		mapa.put(1, 1);
		mapa.put(1, 2);
		
		System.out.println(mapa);
	}*/
}
