#Backendowa część aplikacji 

    Napisana w  javie docelowo łacząca się z angularem.
    Wykorzystująca Spring Security. Zapisująca użytkowników w bazie danych (JPA).
    Wykorzystująca do uwierzytelniania JWT i Basic Authentification.
    
    Start projektu grudzień 2019.

##Do celowy funkcjonalność. 

    1 Logowanie, rejstracja, zarzadzanie użytkownikami, zbieranie danych diagnostycznych za pomoca aspektów.
    2 Obsluga wystawiania faktur. Wystawianie (frontend drukowanie do pdf), katalogowanie, przetwarzanie, wyszukiwanie, stałe towary.
    3 Baza klientów do wystawiania faktur dla każdego użytkownika, szukanie danych w bazie gus.
    4 Pokrycie całości testami.
    
###Endpointy 


    GET     /auth       login
Odbiera w nagłowku w sekcji Authentification token z Basic Authentification. W odpowiedzi wysyla token JWT. Dalsza utoryzacja za pomoca JWT.


~~

    GET     /auth/islogin       islogin
Zwraca obecnie zalogowanego użytkownika. 


~~

    POST   /auth     register
 Rejstracja użytkownika z walidacją danych orzaz sprawdzeniem czy login istnieje. Po zarejstrowaniu wysyłany mail z linkiem aktywacyjnym. 
 
 ~~
 


    GET     /auth/activate/{id}/{token}     activateUser
Aktywacja użytkownika. Na podstawie tokenu którry został odebrany mailem.

~~

    POST       /auth/password/      changePassword
Zmiana hasła wysyłany jest mailem link do zmiany hasła.



`~~~~`

##Tylko dla Admina endpointy.
Zabezpieczone przed dostempem z innymi rolami.

    GET     /user       getAllUsers
    GET     /user/{id}      getUserById
    DELETE      /user/{id}      deleteUserById
    PUT     /user/{id}/enabled      setEnabledUser
    PUT     /user/{id}/roles        setRoles
