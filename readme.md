Aplikacja backendowa w javie docelowo łacząca się z angularem.</br>
Wykorzystująca Spring Security.
 Zabezpieczenie basicauthorization w pierwszej wersji.</br>
 </br>
 endpointy:</br>
 ~~~~
 POST   /auth     register
 Rejstracja użytkownika z walidacją danych orzaz sprawdzeniem czy login istnieje. Po zarejstrowaniu wysyłany mail z linkiem aktywacyjnym. 
 
 
GET     /auth       login
Zwraca obecnie zalogowanego użytkownika.


PUT     /auth   update
Przyjmuje dane do aktualizacji uzytkownika.


GET     /auth/activate/{id}/{token}     activateUser
Aktywacja użytkownika.

POST       /auth/password/      changePassword
Zmiana hasła wysyłany jest mailem link do zmiany hasła.

PUT     /auth/password/     updatePasswordMail
Zmiana hasła po dostaniu maila.

`~~~~`

Tylko dla Admina.

GET     /user       getAllUsers
GET     /user/{id}      getUserById
DELETE      /user/{id}      deleteUserById
PUT     /user/{id}/enabled      setEnabledUser
PUT     /user/{id}/roles        setRoles
