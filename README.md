# Classical Music Scores (Webstore)
Déri Antal Jakab vizsgaremeke

## Bemutatás
A vizsgaremekem egy klasszikus zenei kottákat áruló online boltnak
a backendjét ellátó alkalmazás. Ez a három rétegű REST API arra szolgál, hogy kottákat lehessen a boltból lekérdezni (később megvásárolni), frissíteni, újat hozzáadni, illetve törölni. Mindezeknek megfelelő HTTP metódusokat készítettem. A háttérben működő adatbázist a Postgres szolgáltatja.

## Az API-ban szereplő entity-k:

### Score (kotta)
- Long id
- String title (cím)
- Composer composer (zeneszerző)
- Integer yearOfCreation (a mű keletkezésének ideje évszámban kifejezve)
- Publisher publisher (a zenemű kiadója)
- Double price (a kotta ára)
- Boolean isAvailableInStock (van-e a kotta készleten)

### Composer (zeneszerző)
- Long id
- String name
- Integer yearOfBirth (születési év)
- Integer yearOfDeath (halálozási év)
- List<Score> scores (a zeneszerző által készített zeneművek/kották listája)

### Publisher (kottakiadó)
- Long id
- String name
- List<Score> scores (a kiadó tulajdonában levő kották)
#
  
Három entitás tehát, a Score-nak, mint child entity-nek két parent entity-je van: Composer és Publisher. Ugyanis nem csak a zeneszerző, de a kottakiadó vállalat is rendelkezik a kotta tulajdonjogaival.

## Repository réteg (com.codecool.classical_music_scores.repository)
- ScoreRepository.java
- ComposerRepository.java
- PublisherRepository.java

## Service réteg (com.codecool.classical_music_scores.service)
- ScoreService.java
- ComposerService.java
- PublisherService.java

## Controller réteg (com.codecool.classical_music_scores.controller)
- ScoreController.java
- ComposerController.java
- PublisherController.java

### A ScoreController-ben szereplő HTTP kérések

#### GET
- /scores : visszatér egy listában az összes kottával
- /scores/{id} : visszatér egy adott kottapéldánnyal, melyet ID alapján kér le. Ha nem talál ilyet, 404-es Error üzenettel tér vissza.
- /scores/{id}/composer : visszatér egy ID alapján lekért kottának a zeneszerzőjével. Ha nem, akkor 404.
- /scores/{id}/publisher : visszatér egy ID alapján lekért kottának a kiadójával. Ha nem, akkor 404.

#### POST
- /scores : hozzáad egy új Score entity-t az adatbázishoz

#### PUT
- /scores/{id} : frissíti egy ID alapján lekért kottának a tulajdonságait 

#### DELETE
- /scores/{id} : töröl egy ID alapján lekért kottát az adatbázisból


### A ComposerController-ben szereplő HTTP kérések

#### GET
- /composers : visszatér egy listában az összes zeneszerzővel
- /composers/{id} : visszatér egy adott zeneszerzővel, melyet ID alapján kér le. Ha nem talál ilyet, 404-es Error üzenettel tér vissza.
- /composers/{id}/scores : visszatér egy ID alapján lekért zeneszerzőnek a műveivel (List<Score>). Ha nem, 404.

#### POST
- /composers : hozzáad egy új Composer entity-t az adatbázishoz

#### PUT
- /composers/{id} : frissíti egy ID alapján lekért zeneszerzőnek a tulajdonságait 

#### DELETE
- /composers/{id} : töröl egy ID alapján lekért zeneszerzőt az adatbázisból


### A PublisherController-ben szereplő HTTP kérések

#### GET
- /publishers : visszatér egy listában az összes kiadóval
- /publishers/{id} : visszatér egy adott kiadóval, melyet ID alapján kér le. Ha nem talál ilyet, 404-es Error üzenettel tér vissza.
- /publishers/{id}/scores : visszatér egy ID alapján lekért kiadónak a műveivel (List<Score>). Ha nem, 404.
- /publishers/{id}/composers : visszatér egy ID alapján lekért kiadónak a zeneszerzőivel (List<Composer>). Ha nem, 404.

#### POST
- /publishers : hozzáad egy új Publisher entity-t az adatbázishoz

#### PUT
- /publishers/{id} : frissíti egy ID alapján lekért kiadónak a tulajdonságait 

#### DELETE
- /publishers/{id} : töröl egy ID alapján lekért kiadót az adatbázisból

## Migrációs sémák
- src/main/resources/db/migration/V1__.sql
- src/main/resources/db/migration/V2__.sql

## Dockerfile, futtatási parancsok
- docker_build.bat - lebuildel egy image-t
- docker_build.sh
- docker_run.bat - létrehoz egy container-t az image-ből (ha nem foglalt a port)
- docker_run.sh
- Dockerfile
- tesztek futtatásához: run_tests.cmd

## Problémák a kóddal
- A ScoreController PUT metódusa valamiért nem működik
- A Unit tesztek szerint az összes Service osztály Update metódusa nem működik
- ezért az ezzel kapcsolatos tesztekre rátettem a @Disabled annotációt
