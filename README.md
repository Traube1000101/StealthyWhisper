<!-- English README file of the StealthyWhisper project written in markdown -->
<div align="center">

<img src="./src/main/resources/com/traube/stealthywhispergui/app-icon.png" alt="Wow&#8230; such Empty!" title="StealthyWhisper Icon" width="128">
<h1>StealthyWhisper</h1>

Stealthy Whisper is a ciphering tool that hides a message in another message as invisible characters.

[![CONTAINS TASTY SPAGHETTI CODE](https://forthebadge.com/images/badges/contains-tasty-spaghetti-code.svg)](../../tree/master/src/main/java/com/traube/stealthywhispergui)   
[![MADE WITH JAVA](https://forthebadge.com/images/badges/made-with-java.svg)](https://www.java.com/)&emsp13;[![USES CSS](https://forthebadge.com/images/badges/uses-css.svg)](https://www.w3.org/Style/CSS/)&emsp13;[![USES JAVAFX](./assets/badges/USES_JAVAFX.svg)](https://openjfx.io/)

###### [← Back](../../#readme)&ensp;|&ensp;[Deutsch](./README_DE.md)
</div>

---

## How it works

- If a secr⁡⁤⁡⁤‌⁠⁡⁤‍​⁡⁤‍​⁡⁤‍⁠⁡⁤‍⁣⁡﻿⁢⁡‌﻿⁡‌﻿⁡⁤﻿⁤⁡⁤‌﻿⁡⁤‍‌⁡⁤‍​⁡⁤‍‌⁡‌‍⁡⁤​⁢⁡⁤​‌⁡‌﻿⁡⁤﻿⁠⁡⁤‍‍⁡⁤⁠‍⁡⁤⁣⁢⁡⁤‌⁢⁡⁤‌﻿⁡‍‌⁡⁤⁢⁠⁡⁤​﻿⁡⁤⁠﻿⁡‍⁠et key is provided, the message is encrypted with AES using the key
- It Converts each letter of the message into their octal representation
- The octal numbers are then turned invisible by Substitution with invisible unicode characters
- This invisible message then gets randomly inserted into the visible message
- Prepends a byte to the message (e.g. #1 is a text message) which defines the type of message


## Example

| Visible Message | Hidden Message | Encoded Message | octal segments made visible                |
|:----------------|:---------------|:----------------|:-------------------------------------------|
| Hello           | Secret         | Hel⁡⁤⁡⁤⁢⁣⁡⁤​‌⁡⁤​⁣⁡⁤‍⁢⁡⁤​‌⁡⁤‍​lo           | Hel&#8203;**#1#123#145#143#162#145#164**lo |


## How to use

1. Download the latest [release](../../releases)
2. Install Java 17 or newer
3. Run the JAR and have fun!


## License

#### StealthyWhisper is distributed under the [Eclipse Public License 2.0](./LICENSE)   

Feel free to use StealthyWhisper in your own projects, modify it, or redistribute it.  
However, please make sure to give credit and provide a link to this repository. Thanks! &#128578;

## Made by

<a href="../../graphs/contributors" target="_blank">
  <img src="https://contrib.rocks/image?repo=Traube1000101/StealthyWhisperGUI" />
</a>
