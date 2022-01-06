# WHITE-COLLAR
## Botiga de Quadres

### Objectius
* Protocol HTTP / REST
* JPA
* MySQL
* jUnit & Unit Testing

### Descripció
> Dissenyar i construir una API REST utilitzant bases de dades com a persistència

### Nivell 1
Tenim una franquicia de botiga de quadres il·legals que fa veure que ven collarets blancs, per això es diu “white collar”.   

Aquesta franquicia té varies botigues, cadascuna amb un nom i una capacitat màxima de quadres (o collars^^). Hi ha quadres que tenen un nom d’autor i d’altres que són anònims. Això sí, tots tenen un nom, un preu i una data d’entrada (és la data del moment en el que entra a la botiga).

El client ens demana implementar una API amb Spring amb les següents funcionalitats:  

* **Crear botiga**: li direm el nom i la capacitat 
```[java]
(POST /shops/)
```
* **Llistar botigues**: retorna la llista de botigues amb el seu nom i la capacitat 
```[java]
(GET /shops/)
```
* **Afegir quadre**: li donarem el nom del quadre i el del autor 
```[java]
(POST /shops/{ID}/pictures) 
```
* **Llistar els quadres de la botiga**
```[java]
(GET /shops/{ID}/pictures)
```
* **Incendiar quadres**: per si ve la policia, es poden eliminar tots els quadres de la botiga sense deixar rastre 
```[java]
(DELETE /shops/{ID}/pictures)
```

### NOTES 

Has de tindre en compte els següents detalls de construcció: 

* Inclou en un directori de el projecte la col·lecció Postman per a les invocacions http 
* Utilitza el projecte SpringBootInitialDemo com a exemple d'estructura 
* Has de persistir les dades en una base de dades en memòria h2 (inclosa en Spring boot)

### Nivell 2
Utilitza mysql per persistir les dades, en lloc de h2.

### Nivell 3
Afegeix tests unitaris, de components i d'integració al projecte. Pots utilitzar jUnit, AssertJ, Hamcrest o Mockito, per exemple.
