# INFORMACJE OGÓLNE
 - W każdym zapytaniu ( oprócz zapytania rejestrującego użytkownika oraz zapytania sprawdzającego token) powinien być zamieszczony w nagłówku zapytania token autoryzacyjny użytkownika, który wykonuje zapytanie.
 - Przykładowo: 
```sh
key: token    value: 8787dfsdfsd7f6dsfsd5f7s6df576sdf567sdf
```
## REJESTRACJA UŻYTKOWNIKA

Zapytanie:
```sh
https://sporttrace.herokuapp.com/api/users
```


Wymagane parametry:
 - token - token użytkownika facebooka
 - uid - id użytkownika facebooka
 - expiredDate - data wygaśnięcia tokenu
 - regId - identyfikator aplikacji GCM

Format przesyłanych parametrów: **JSON**

Metoda: **POST**

Przykład:
```sh
{
    "token": "987dfds8fgds7g6f7g6fd7g678fd6g7fd6g7df6g7dfg",
    "uid" : 879898, 
    "expiredDate": "1568460250030",
    "regId" : "87fsdf7d8f79d8fs78"
}
```


Zwracane statusy:

- 404 - użytkownik o podanym tokenie nie istnieje w serwisie facebook
- 406 - brak wszystkich wymaganych parametrów
- 409 - użytkownik o podanym tokenie już istnieje w serwisie
- 201 - użytkownik został utworzony (zwraca JSON z utworzonym użytkownikiem)



## AUTORYZACJA UŻYTKOWNIKA

Zapytanie: 
```sh
https://sporttrace.herokuapp.com/api/users/authenticate
```
Wymagane parametry:
 - token w nagłówku

Format przesyłanych parametrów: **BRAK**

Metoda: **GET**

Zwracane statusy:

 - 200 - użytkownik istnieje w systemie (zwraca JSON z użytkownikiem)
 - 401 - wygasła data ważnosci tokena, ale użytkownik istnieje w systemie 
 - 404 - użytkownik nie istnieje w systemie
 - 406 - brak tokena w nagłówku zapytania




## SPRAWDZENIE CZY TOKEN NALEŻY DO UŻYTKOWNIKA FACEBOOKA

Zapytanie: 
```sh
https://sporttrace.herokuapp.com/api/users/checkToken/5HJKNKJKJKJ98990NB
```
Wymagane parametry:
 - token - token użytkownika facebooka

Format przesyłanych parametrów: **W zapytaniu**

Metoda: **GET**

Zwracane statusy:

 - 200 - użytkownik istnieje w serwisie facebook
 - 404 - użytkownik nie istnieje w serwisie facebook
 - 406 - brak wymaganych parametrów



## USTAWIENIE KATEGORII SPORTÓW UŻYTKOWNIKA

(Domyślnie użytkownik posiada wybrane wszystkie kategorie)

Zapytanie:
```sh
https://sporttrace.herokuapp.com/api/users/preferences
```


Wymagane parametry:
 - name - nazwa kategorii sportowej


Format przesyłanych parametrów: **JSON**

Metoda: **PUT**

Przykład:
```sh
[
  {
    "name": "BASKETBALL"
  },
  {
    "name": "VOLLEYBALL"
  },
  {
    "name": "RUGBY"
  }
]
```


Zwracane statusy:

- 406 - brak wszystkich wymaganych parametrów
- 401 - nieudana autoryzacja
- 200 - kategorie sportowe użytkownika zostały zaktualizowane (zwraca JSON z użytkownikiem)



## DODAWANIE OBSZARU WYSZUKIWANIA DLA UŻYTKOWNIKA

Zapytanie:
```sh
https://sporttrace.herokuapp.com/api/users/areas
```


Wymagane parametry:
 - name - unikalna nazwa obszaru
 - longitude - długość geograficzna
 - latitude - szerokość geograficzna
 - radius - promień obszaru

Format przesyłanych parametrów: **JSON**

Metoda: **POST**

Przykład:
```sh
{
    "name": "Obszar1",
    "longitude": 54.3234,
    "latitude": 16.9986,
    "radius": 2.0
}
```


Zwracane statusy:

- 406 - brak wszystkich wymaganych parametrów bądź brak tokena w nagłówku
- 409 - obszar o podanej nazwie już istnieje
- 200 - obszar wyszukiwania został stworzony poprawnie





## AKTUALIZOWANIE OBSZARU WYSZUKIWANIA DLA UŻYTKOWNIKA

Zapytanie:
```sh
https://sporttrace.herokuapp.com/api/users/areas
```


Wymagane parametry:
 - name - unikalna nazwa obszaru
 - longitude - długość geograficzna
 - latitude - szerokość geograficzna
 - radius - promień obszaru

Format przesyłanych parametrów: **JSON**

Metoda: **PUT**

Przykład:
```sh
{
    "name": "Obszar1",
    "longitude": 54.3234,
    "latitude": 16.9986,
    "radius": 3.0
}
```


Zwracane statusy:

- 404 - nie istnieje obszar wyszukiwania o podanej nazwie
- 406 - brak wszystkich wymaganych parametrów bądź brak tokena w nagłówku
- 200 - obszar wyszukiwania został zaktualizowany poprawnie


 
## POBRANIE WYDARZEŃ STWORZONYCH PRZEZ UŻYTKOWNIKA O PODANYM ID

Zapytanie: 
```sh
https://sporttrace.herokuapp.com/api/users/1/events
```
Wymagane parametry:
 - id - id użytkownika, którego wydarzenia maja zostać zwrócone

Format przesyłanych parametrów: **W zapytaniu**

Metoda: **GET**

Zwracane statusy:

 - 200 - wydarzenia zostają zwrócone poprawnie
 - 404 - użytkownik o podanym id nie istnieje w serwisie
 - 401 - nieudana autoryzacja
 

## POBRANIE WSZYSTKICH UŻYTKOWNIKÓW

Zapytanie: 
```sh
https://sporttrace.herokuapp.com/api/users
```
Wymagane parametry:
 - brak

Format przesyłanych parametrów: **brak**

Metoda: **GET**

Zwracane statusy:
 - 200 - użytkownicy zostają zwróceni poprawnie
 - 401 - nieudana autoryzacja 


## POBRANIE WSZYSTKICH WYDARZEŃ

Zapytanie: 
```sh
https://sporttrace.herokuapp.com/api/events
```
Wymagane parametry:
 - brak

Format przesyłanych parametrów: **brak**

Metoda: **GET**

Zwracane statusy:
 - 200 - wydarzenia zostają zwróceni poprawnie
 - 401 - nieudana autoryzacja 




## POBRANIE WYDARZEŃ DOSTOSOWANYCH DO UŻYTKOWNIKA

Pobiera wydarzenia na podstawie ustawionych kategorii sportowych oraz obszaru wyszukiwania uzytkownika.
Jeżeli kategorie nie były ustawiane a obszar był ustawiony zwraca wszystkie wydarzenia w wybranym obszarze.
Jeśli ani kategorie ani obszar nie zostały ustawione to zwraca wszystkie wydarzenia.

Zapytanie: 
```sh
https://sporttrace.herokuapp.com/api/events/adjust
```
Wymagane parametry:
 - brak

Format przesyłanych parametrów: **brak**

Metoda: **GET**

Zwracane statusy:
 - 200 - wydarzenia zostają zwróceni poprawnie
 - 401 - nieudana autoryzacja  




## DODANIE WYDARZENIA PRZEZ ORGANIZATORA

Zapytanie:
```sh
https://sporttrace.herokuapp.com/api/events
```


Wymagane parametry:
 - category_id - id kategorii do jakiej ma zostać przypisane wydarzenie (patrz Kategorie Sportowe)
 - date - data wydarzenia
 - latitude - szerokość geograficzna
 - longitude - długość geograficzna
 - name - nazwa wydarzenia
 - participantsNumber - docelowa liczba osób potrzebnych do wydarzenia
 - special - czy wydarzenie ma być wyróżnione
 - location - adres wydarzenia

Format przesyłanych parametrów: **JSON**

Metoda: **POST**

Przykład:
```sh
{
    category_id: 7,
    date: "2016-06-16 13:10:50.042",
    latitude: 15.4324,
    longitude: 54.7677,
    name: "Meczyk",
    participantsNumber: 11,
    special: "false",
    location: "Poznan"
}
```

WAŻNE INFO: Listy **participants** oraz **apliers** zawierają id użytkowników odpowiednio biorących udział w wydarzeniu i użytkowników ubiegających się o wzięcie udziału w wydarzeniu. Organizator zawsze jest przypisany jako biorący udział w wydarzeniu.

Zwracane statusy:

- 404 - błąd w składni zapytania lub zapytanie nie istnieje
- 406 - brak wszystkich wymaganych parametrów
- 401 - nieudana autoryzacja
- 409 - wydarzenie o podanej nazwie już istnieje w serwisie
- 201 - wydarzenie zostało utworzone (zwraca JSON z utworzonym wydarzeniem)





## USUNIĘCIE WYDARZENIA O PODANYM ID

Zapytanie: 
```sh
https://sporttrace.herokuapp.com/api/events/1
```
Wymagane parametry:
 - id - identyfikator wydarzenia

Format przesyłanych parametrów: **W zapytaniu**

Metoda: **DELETE**

Zwracane statusy:
 - 204 - wydarzenia zostało poprawnie usunięte
 - 401 - nieudana autoryzacja
 - 404 - wydarzenie o podanym id nie istnieje w serwisie


## POBRANIE WYDARZENIA O PODANYM ID

Zapytanie: 
```sh
https://sporttrace.herokuapp.com/api/events/1
```
Wymagane parametry:
 - id - identyfikator wydarzenia

Format przesyłanych parametrów: **W zapytaniu**

Metoda: **GET**

Zwracane statusy:
 - 200 - wydarzenia zostało poprawnie zwrócone
 - 401 - nieudana autoryzacja
 - 404 - wydarzenie o podanym id nie istnieje w serwisie


## POBRANIE WSZYSTKICH KATEGORII SPORTOWYCH

Zapytanie: 
```sh
https://sporttrace.herokuapp.com/api/sportCategories
```
Wymagane parametry:
 - brak

Format przesyłanych parametrów: **brak**

Metoda: **GET**

Zwracane statusy:
 - 200 - kategorie sportowe zostają zwrócone poprawnie
 - 401 - nieudana autoryzacja
 - 404 - błąd w składni lub zapytanie nie istnieje




 ## APLIKOWANIE DO WZIĘCIA UDZIAŁU W WYDARZENIU

 Zapisuje użytkownika do listy **appliers** wydarzenia, która zawiera osoby ubiegające się o wzięcie udziału w wydarzeniu i oczekujące na akceptację przez organizatora

Zapytanie:
```sh
https://sporttrace.herokuapp.com/api/events/apply/7
```


Wymagane parametry:
 - id - id wydarzenia, w którym użytkownik chce wziąść udział


Format przesyłanych parametrów: **W zapytaniu**

Metoda: **PUT**


Zwracane statusy:

- 404 - wydarzenie o podanym id nie istnieje
- 409 - użytkownik już jest przypisany do wydarzenia lub jest a liście oczekujących na akceptację
- 200 - użytkownik został zapisany do listy oczekujących na akceptację



 ## REZYGNAZJA Z WZIĘCIA UDZIAŁU W WYDARZENIU

 Usuwa zarówno z listy **participants** oraz **appliers** wydarzenia (w zależności od tego czy użytkownik już bierze udział w wydarzeniu czy dopiero się ubiega)

Zapytanie:
```sh
http://localhost:8080/SportTrace-Rest/api/events/resign/48
```


Wymagane parametry:
 - id - id wydarzenia z którego chce zrezygnować użytkownik


Format przesyłanych parametrów: **W zapytaniu**

Metoda: **POST**


Zwracane statusy:

- 404 - wydarzenie o podanym id nie istnieje
- 403 - użytkownik jest właścicielem wydarzenia i nie może zrezygnować z brania w nim udziału
- 200 - użytkownik został usunięty z wydarzenia ( zwraca JSON z zaktualizowanym wydarzeniem)


## AKCEPTACJA UŻYTKOWNIKÓW, KTÓRZY UBIEGAJĄ SIĘ O WZIĘCIE UDZIAŁU W WYDARZENIU

Zapytanie:
```sh
https://sporttrace.herokuapp.com/api/events/appliers/accept/$id"
```


Wymagane parametry:
 - id (w zapytaniu) - id wydarzenia
 - appliers (JSON) - tablica id użytkowników. Użytkownicy muszą znajdować się na liscie appliers wydarzenia

Format przesyłanych parametrów: **JSON + w zapytaniu**

Metoda: **POST**

Przykład:
```sh
{
    "appliers" : [41, 77, 11]
}
```

Zwracane statusy:

- 404 - wydarzenie o podanym id nie istnieje LUB lista appliers wydarzenia nie ma wszystkich id wymienionych uzytkowników LUB nie wszyscy wymienieni uzytkownicy istnieją w systemie 
- 401 - brak autoryzacji LUB użytkownik nie jest właścicielem wydarzenia
- 406 - brak tokena autoryzacyjnego LUB brak wszystkich wymaganych parametrów
- 200 - użytkownicy zostali przypisani do użytkowników biorących udział w wydarzeniu (zwraca JSON z zaktualizowanym wydarzeniem)