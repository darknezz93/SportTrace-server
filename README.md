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

Format przesyłanych parametrów: **JSON**

Metoda: **POST**

Przykład:
```sh
{
    "token": "987dfds8fgds7g6f7g6fd7g678fd6g7fd6g7df6g7dfg",
    "uid" : 879898, 
    "expiredDate": "2016-07-16 13:10:50.042"
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
https://lling-grails.herokuapp.com/api/users/authenticate
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