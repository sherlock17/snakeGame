A snakeGame fordításához és futtatásához java8 -as verzióra van szükség. A kód fordításához a maven wrapper szkriptek használhatók.

Indítás után három parancsot üthet be a felhasználó (play, results, exit). 
play: play után meg kell adni a pálya méretét, majd elindul a játék. A falak 'X' karakterekkel vannak ábrázolva, a kigyó teste 'S' a feje 'H', a gyümölcs pedig 'F' karakter.
results: parancs megjeleníti az eddigi 10 legjobb eredményt (itt minden név 20 karakter hosszúra van normálva, azért, hogy táblázatszerűen kiférjen a képernyőre)
exit: kilép a programból.

Az elkészített megoldásnak három elkülöníthető része van. 
Az engine - ben van megvalósítva a snake játéklogika, a dao a játékeredményekkel kapcsolatos műveletekért felelős, a gui a felhasználói interakciókra szolgál, emelett karakteres felületet biztosít a játéknak.


A játék mindhárom részének előre definiált interfésze van, ezek köré lett építve a megvalósítás. 

A GameController osztály a komponensekkel kommunikálva koordinálja a játékot. A snakeGameRunner a program belépési pontja, a játék menüjét hivatott lekezeleni. 


dao: egy fájba tárolja az elért eredményeket. Az eredmények mindig sorrendezve vannak tárolva, a rekorderedmény és a top 10 eredmény műveletek így gyorsan le tudnak futni. 
Új eredmény beillesztésénél figyelni kell, hogy a megfelelő helyre történjen a beillesztés. A fájlban lévő lista kiolvasódik és felülíródik az új listával. 
Az eredmények a gyökérkönyvtárban található results.txt fájlba íródnak, ez jelenleg be van égetve a kódba. File lockolás nincs megvalósítva, a snake-nek egy szálon kell futnia.
A fájlban történő tárolás interfész módoítás nélkül kicserélhető adatbázisra.


gui: A játék karakteres felülete a lanterna nevű library segítségével lett megvalósítva. 
A játékon kívüli felhasználói interakciók a standard inputon és outputon keresztül történnek. 
Ez az interfész módosítása nélkül lecserélhető egy felhasználói felületre.


engine: A játék állapotát tárolja (Elsőként itt egy newGame hívást kell hívni, lehetne vizsgálni, ha newGame nélkül hívódik valami, de a GameController ezt kezeli)


Tesztek: A tesztek a snake üzleti logika kritériumai alapján lettek elkészítve. A teszteknek be kell állítaniuk a játék állapotát, ehhez egy új teszt interfész is szükséges volt.
A production kódban is kellett emiatt egy külön hívás az állapotbeállítása. 
Alapvetően nem jó, ha a tesztek miatt kerül csak egy funkció a production kódba, de itt most ez volt a legjobb megoldás amit találtam.

