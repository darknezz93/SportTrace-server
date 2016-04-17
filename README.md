-----------------------------------REJESTRACJA UZYTKOWNIKA-----------------------------------

Zapytanie: sporttrace.herokuapp.com/api/user/create

Wymagane parametry:

token - token użytkownika facebooka
uid - id użytkownika facebooka
expiredDate - data wygaśnięcia tokenu

Przykład: 
curl -H "Content-Type: application/json" -X POST -d '{"token":"877576fhsdgfh334","uid" : 109861136289, "expiredDate": "2016-06-16 13:10:50.042"}' http://localhost:8080/SportTrace-Rest/api/user/create

Zwracane statusy:

404 - użytkownik o podanym tokenie nie istnieje w serwisie facebook
406 - brak wszystkich wymaganych parametrów
409 - użytkownik o podanym tokenie już istnieje w serwisie
201 - użytkownik został utworzony (zwraca JSON z utworzonym użytkownikiem)



-------------SPRAWDZENIE CZY UZYTKOWNIK O PODANYM TOKENIE ISTNIEJE W SERWISIE FACEBOOK-------

Zapytanie: sporttrace.herokuapp.com/api/user/checkToken

Wymagane parametry:

token - token użytkownika facebooka

Przykład:
curl -H "Content-Type: application/json" -X POST -d '{"token":"877576fhsdgfh334"}' http://localhost:8080/SportTrace-Rest/api/user/checkToken

Zwracane statusy:

200 - użytkownik istnieje w serwisie facebook
404 - użytkownik nie istnieje w serwisie facebook
406 - brak wymaganych parametrów
