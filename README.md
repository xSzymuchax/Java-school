# Java-school

Zadania z Javy oddawane jako klasy implementujące interfejs podany przez prowadzącego. Testy uruchamiał nauczyciel, więc w każdym folderze jest implementacja razem z tym interfejsem.

Java assignments submitted as classes that implement an interface given by the instructor. The teacher ran the tests, so each folder contains the implementation together with that interface.

## Archivizer

Kompresja katalogu do jednego pliku i rozpakowanie go z powrotem. `compress` pakuje katalog `dir` do pliku `filename` i zwraca rozmiar archiwum w bajtach (albo `-1`, gdy się nie uda). `decompress` wypakowuje archiwum do wskazanego katalogu. W `Ten` leży przykładowy katalog wejściowy.

Compress a directory into a single file and unpack it again. `compress` packs the directory `dir` into `filename` and returns the archive size in bytes, or `-1` on failure. `decompress` extracts the archive into the given directory. `Ten` is a sample input directory.

## Connection

Otwarcie połączenia TCP do hosta i portu podanych w `connect`. Po połączeniu program czyta linie przysyłane przez serwer.

Open a TCP connection to the host and port given to `connect`. After connecting, the program reads lines sent by the server.

## Decoder

Dekoder strumienia cyfr podawanych pojedynczo metodą `input`. Ciąg to cyfry danych, potem `0`, potem cztery cyfry liczby powtórzeń. `output` zwraca dane powtórzone tyle razy. `reset` przywraca pusty stan obiektu. Znak `r` w `main` też resetuje dekoder.

A decoder for a stream of digits fed one by one through `input`. A sequence is the data digits, then `0`, then four digits of the repeat count. `output` returns the data repeated that many times. `reset` restores the empty state of the object. The character `r` in `main` resets the decoder as well.

## Drawing

Rysowanie odcinków na kwadratowym płótnie. `setCanvasGeometry` dostaje rozmiar płótna i punkt startu. `draw` rysuje jeden odcinek: kierunek `1` / `-1` zmienia pierwszy indeks, `2` / `-2` drugi, a za długi odcinek jest skracany do krawędzi. `getPainting` zwraca tablicę pikseli, `clear` zeruje płótno.

Draw line segments on a square canvas. `setCanvasGeometry` receives the canvas size and the starting point. `draw` paints one segment: direction `1` / `-1` changes the first index, `2` / `-2` the second, and a segment that would leave the canvas is shortened to the edge. `getPainting` returns the pixel array, `clear` sets the canvas to zero.

## FastHistogram

Histogram liczony równolegle. `setup` ustala dokładną liczbę wątków i liczbę koszyków. `setVector` nie może zablokować wywołującego: obliczenie startuje w tle. `isReady` mówi, czy wynik jest gotowy. `histogram` zwraca mapę wartość → liczba wystąpień, bez kluczy o liczbie wystąpień równej zero.

A histogram computed in parallel. `setup` sets the exact thread count and the number of bins. `setVector` must not block the caller: the computation starts in the background. `isReady` reports whether the result is ready. `histogram` returns a map of value → number of occurrences, without keys whose count is zero.

## FieldInspector

Inspekcja publicznych pól klasy podanej z nazwy, przez refleksję. Dla każdego pola powstaje `FieldInfo`: typ (`INT`, `LONG`, `FLOAT`, `DOUBLE` albo `OTHER` — prymityw i odpowiadający mu wrapper liczą się tak samo), nazwa oraz numer wersji z adnotacji `@FieldVersion`. Pole bez tej adnotacji ma wersję `-1`.

Inspect the public fields of a class given by name, using reflection. Each field becomes a `FieldInfo`: a type (`INT`, `LONG`, `FLOAT`, `DOUBLE`, or `OTHER` — a primitive and its wrapper count as the same type), a name, and the version number from the `@FieldVersion` annotation. A field without that annotation has version `-1`.

## GUI

Okno Swing rysujące sumę częściową szeregu fali prostokątnej. Suwak `n` ustala liczbę składników, pole `k` zakres argumentu jako wielokrotność π.

A Swing window that draws a partial sum of the square-wave series. The `n` slider sets the number of terms, and the `k` field sets the argument range as a multiple of π.

## Linie

Graf punktów połączonych odcinkami bez kierunku. Można dodać punkty i odcinki, znaleźć dowolną ścieżkę między dwoma punktami, zbudować mapę punkt → odcinki, które się w nim kończą, oraz sprawdzić, które punkty da się osiągnąć trasą o długości 1, 2, 3 albo 4 odcinków.

A graph of points connected by undirected segments. You can add points and segments, find any path between two points, build a map from a point to the segments that end at it, and see which points can be reached by a route of 1, 2, 3, or 4 segments.

## MultipleHistograms

Wiele histogramów naraz. `setup` dostaje liczbę koszyków i odbiorcę wyniku. `addVector` przyjmuje wektor z własnym `vectorID` i musi wrócić od razu: histogram liczy się w osobnym wątku, a `accept` na odbiorcy woła jeszcze inny wątek niż ten, który histogram policzył.

Several histograms at once. `setup` receives the number of bins and a result consumer. `addVector` takes a vector with its own `vectorID` and must return immediately: the histogram is computed on a separate thread, and `accept` on the consumer is called from yet another thread, not the one that computed the histogram.

## Pionki

Pionki na planszy 2D schodzą się do punktu zbornego. `addPawns` podaje pozycje startowe (kolejność listy to kolejność ruchu), `addMeetingPoint` cel. `move` przesuwa pionki, aż żaden nie może podejść bliżej, bo blokują go inne. Każdy pionek zostaje na innym polu i zachowuje swój numer. `getNeighbours` zwraca pionki sąsiadujące z podanym numerem.

Pawns on a 2D board move toward a meeting point. `addPawns` gives the starting positions (list order is move order), and `addMeetingPoint` sets the target. `move` shifts the pawns until none can get closer because the others block it. Each pawn stays on its own square and keeps its number. `getNeighbours` returns the pawns adjacent to the given number.

## SerwisAukcyjny

Prosty serwis aukcyjny. Użytkownik rejestruje się z obiektem powiadomień, przedmioty wchodzą na aukcję z ceną minimalną w groszach. Użytkownik może subskrybować przedmiot i dostaje znać, gdy ktoś przebije jego ofertę. `oferta` składa kwotę, `koniecAukcji` zamyka licytację, `ktoWygrywa` i `najwyższaOferta` podają wynik.

A simple auction service. A user registers with a notification object, and items enter the auction with a minimum price in groszy. A user can subscribe to an item and is told when someone outbids them. `oferta` places an amount, `koniecAukcji` closes the bidding, and `ktoWygrywa` together with `najwyższaOferta` report the result.
