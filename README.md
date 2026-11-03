# Java-school

Zadania z Javy oddawane jako klasy implementujące interfejs podany przez prowadzącego. Testy uruchamiał nauczyciel, więc w każdym folderze jest implementacja razem z tym interfejsem.

## Archivizer

Kompresja katalogu do jednego pliku i rozpakowanie go z powrotem. `compress` pakuje katalog `dir` do pliku `filename` i zwraca rozmiar archiwum w bajtach (albo `-1`, gdy się nie uda). `decompress` wypakowuje archiwum do wskazanego katalogu. W `Ten` leży przykładowy katalog wejściowy.

## Connection

Otwarcie połączenia TCP do hosta i portu podanych w `connect`. Po połączeniu program czyta linie przysyłane przez serwer.

## Decoder

Dekoder strumienia cyfr podawanych pojedynczo metodą `input`. Ciąg to cyfry danych, potem `0`, potem cztery cyfry liczby powtórzeń. `output` zwraca dane powtórzone tyle razy. `reset` przywraca pusty stan obiektu. Znak `r` w `main` też resetuje dekoder.

## Drawing

Rysowanie odcinków na kwadratowym płótnie. `setCanvasGeometry` dostaje rozmiar płótna i punkt startu. `draw` rysuje jeden odcinek: kierunek `1` / `-1` zmienia pierwszy indeks, `2` / `-2` drugi, a za długi odcinek jest skracany do krawędzi. `getPainting` zwraca tablicę pikseli, `clear` zeruje płótno.

## FastHistogram

Histogram liczony równolegle. `setup` ustala dokładną liczbę wątków i liczbę koszyków. `setVector` nie może zablokować wywołującego: obliczenie startuje w tle. `isReady` mówi, czy wynik jest gotowy. `histogram` zwraca mapę wartość → liczba wystąpień, bez kluczy o liczbie wystąpień równej zero.

## FieldInspector

Inspekcja publicznych pól klasy podanej z nazwy, przez refleksję. Dla każdego pola powstaje `FieldInfo`: typ (`INT`, `LONG`, `FLOAT`, `DOUBLE` albo `OTHER` — prymityw i odpowiadający mu wrapper liczą się tak samo), nazwa oraz numer wersji z adnotacji `@FieldVersion`. Pole bez tej adnotacji ma wersję `-1`.

## GUI

Okno Swing rysujące sumę częściową szeregu fali prostokątnej. Suwak `n` ustala liczbę składników, pole `k` zakres argumentu jako wielokrotność π.

## Linie

Graf punktów połączonych odcinkami bez kierunku. Można dodać punkty i odcinki, znaleźć dowolną ścieżkę między dwoma punktami, zbudować mapę punkt → odcinki, które się w nim kończą, oraz sprawdzić, które punkty da się osiągnąć trasą o długości 1, 2, 3 albo 4 odcinków.

## MultipleHistograms

Wiele histogramów naraz. `setup` dostaje liczbę koszyków i odbiorcę wyniku. `addVector` przyjmuje wektor z własnym `vectorID` i musi wrócić od razu: histogram liczy się w osobnym wątku, a `accept` na odbiorcy woła jeszcze inny wątek niż ten, który histogram policzył.

## Pionki

Pionki na planszy 2D schodzą się do punktu zbornego. `addPawns` podaje pozycje startowe (kolejność listy to kolejność ruchu), `addMeetingPoint` cel. `move` przesuwa pionki, aż żaden nie może podejść bliżej, bo blokują go inne. Każdy pionek zostaje na innym polu i zachowuje swój numer. `getNeighbours` zwraca pionki sąsiadujące z podanym numerem.

## SerwisAukcyjny

Prosty serwis aukcyjny. Użytkownik rejestruje się z obiektem powiadomień, przedmioty wchodzą na aukcję z ceną minimalną w groszach. Użytkownik może subskrybować przedmiot i dostaje znać, gdy ktoś przebije jego ofertę. `oferta` składa kwotę, `koniecAukcji` zamyka licytację, `ktoWygrywa` i `najwyższaOferta` podają wynik.
