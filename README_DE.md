<!-- Deutsche README Datei des StealthyWhisper Projekts in markdown geschrieben -->
<div align="center">
    <h1><img src="./src/main/resources/com/traube/stealthywhisper/app-icon.png" alt="Wow&#8230; such Empty!" title="StealthyWhisper Icon" width="7%" align="bottom">StealthyWhisper</h1>
    Stealthy Whisper ist ein Chiffrierungsprogramm welches eine Nachricht in einer anderen Nachricht als unsichtbare Zeichen versteckt.
    <br/><br/>

[![MADE WITH JAVA](./assets/readme/made-with-java.svg)](https://www.java.com/)&emsp13;<img src="https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white" alt="IntelliJ IDEA" title="IntelliJ IDEA" height="35">&emsp13;[![USES CSS](./assets/readme/uses-css.svg)](https://www.w3.org/Style/CSS/)&emsp13;[![USES JAVAFX](./assets/readme/uses-javafx.svg)](https://openjfx.io/)  
<img src="https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black" alt="Linux" title="Linux" height="35">&emsp13;<img src="https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white" alt="Windows" title="Windows" height="35">&nbsp;[![CONTAINS TASTY SPAGHETTI CODE](./assets/readme/contains-tasty-spaghetti-code.svg)](../../tree/master/src/main/java/com/traube/stealthywhisper)<br/>
    
###### [← Back](./#readme)&ensp;|&ensp;[English](./README.md)
</div>

---

## Funktionsweise

- Sobald ein Schl⁡⁤⁡⁤‌⁠⁡⁤‍​⁡⁤‍​⁡⁤‍⁠⁡⁤‍⁣⁡﻿⁢⁡‌﻿⁡‌﻿⁡⁤﻿⁤⁡⁤‌﻿⁡⁤‍‌⁡⁤‍​⁡⁤‍‌⁡‌‍⁡⁤​⁢⁡⁤​‌⁡‌﻿⁡⁤﻿⁠⁡⁤‍‍⁡⁤⁠‍⁡⁤⁣⁢⁡⁤‌⁢⁡⁤‌﻿⁡‍‌⁡⁤⁢⁠⁡⁤​﻿⁡⁤⁠﻿⁡‍⁠üssel eingegeben wurde, wird die Nachricht mit AES verschlüsselt
- Es konvertiert jeden Buchstaben der Nachricht in seine oktale Darstellung
- Die oktalen Zahlen werden dann durch Substitution mit unsichtbaren Unicode-Zeichen unsichtbar gemacht
- Diese unsichtbare Nachricht wird dann zufällig in die sichtbare Nachricht eingefügt
- Fügt einen Byte vor der Nachricht ein (z.B. ist #1 eine Textnachricht) welcher den Nachrichtentyp definiert


## Beispiel

| Sichtbare Nachricht | Versteckte Nachricht | Kodierte Nachricht              | Sichtbare Oktal Segmente                   |
|:--------------------|:---------------------|:--------------------------------|:-------------------------------------------|
| Hello               | Secret               | Hel⁡⁤⁡⁤⁢⁣⁡⁤​‌⁡⁤​⁣⁡⁤‍⁢⁡⁤​‌⁡⁤‍​lo | Hel&#8203;**#1#123#145#143#162#145#164**lo |


## Wie benutze ich es

1. Lade das neueste [Release](../../releases/latest) herunter
2. Es wird Java 17 oder neuer benötigt
4. Führe die JAR aus und hab Spaß!


## Lizenz

#### StealthyWhisper wird unter der [Eclipse Public License 2.0](./LICENSE) vertrieben

Du kannst StealthyWhisper gerne in deinen eigenen Projekten verwenden, modifizieren oder weiterverteilen.  
Allerdings wäre es super, wenn du mir Credits gibst und auf dieses Repository verlinkst. Danke! &#128578;

## Mitwirkende Personen

<a href="../../graphs/contributors" target="_blank">
  <img src="https://contrib.rocks/image?repo=Traube1000101/StealthyWhisperGUI" />
</a>
