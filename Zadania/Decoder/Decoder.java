import java.util.Scanner;

/**
 * Klasa dekodera.
 * @field values[] pole przechowujace dane z wejscia
 * @next pozycja do wpisania kolejnej danej
 * @counting okresla czy zaczelo sie wpisywanie cyfr powtorzen
 * @c liczy cyfry powtorzen
 */
public class Decoder {
	public byte[] values = new byte[1024];
	public int next = 0;
	public boolean counting = false;
	public int c = 0;
	/**
	 * Metoda pozwalajÄca na wprowadzanie danych od tablicy @values.
	 * @param value dostarczona wartoĹÄ
	 */
	public void input( byte value ) 
	{
		if (value < 0 || value > 9) return;
		values[next] = value;
		next++;
	}
	
	/**
	 * Metoda pozwalajÄca na pobranie wyniku dekodowania danych.
	 * @return wynik dziaĹania
	 */
	public String output() 
	{
		String data="";
		String repeats="";
		String result="";
		
		int i=0;
		boolean cnt = false;
		int c_cnt = 0;
		
		while(i<next)
		{
			if (cnt) //okreslenie poczatku liczby powtorzen
			{
				repeats += values[i];
				c_cnt++;
			}
			else if (!cnt && (values[i] == 0) ) 
			{
				cnt=true;
			}
			else data += values[i]; //dodanie cyfry do danych
			
			if (c_cnt==4) //po zczytaniu liczby  powtorzen - wypisywanie
			{
				c_cnt=0;
				cnt=false;
				
				for (int j=0;j<Integer.parseInt(repeats);j++) 
					result += data;
				
				data="";
				repeats="";
			}			
			i++;
		}	
		return result;
	}
	
	/**
	 * PrzywrĂłcenie poczÄtkowego stanu obiektu.
	 */
	public void reset() 
	{
		next = 0;
		counting = false;
		c = 0;
		for (int i=0;i<1024;i++)
		{
			if (values[i]!= -1) values[i] = -1;
			else if (values[i] == -1) break;
		}
	}
	
	/**
	 * Metoda glowna programu, pobierajaca po cyfrze lub znaku resetu "r" dane
	 * od uzytkownika programu a nastepnie wypisujaca je wedlug okreslonego wzorca.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Decoder dec = new Decoder();
		Scanner in = new Scanner(System.in);
		byte num=0;
		
		String input_data;
		do
		{
			input_data = in.nextLine();
			System.out.println("TAKIE INFO WPADLO: " + input_data);
			//System.out.println();
			
			if ( (input_data.compareTo("r")) == 0)
			{
				dec.reset();
				continue;
			}
			
			if ( (input_data.compareTo("0") == 0) && !dec.counting )
			{
				dec.counting = true;
				num = Byte.parseByte(input_data);
				dec.input(num);
			}
			else 
			{
				if (dec.counting) dec.c++;
				num = Byte.parseByte(input_data);
				dec.input(num);
			}
			
			if (dec.c==4)
			{
				dec.c=0;
				dec.counting = false;
				System.out.println("WYNIK: " + dec.output());
			}
			
		}
		while(true);
	}
}