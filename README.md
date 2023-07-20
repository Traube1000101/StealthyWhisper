<!-- README file of the StealthyWhisper project written in markdown -->

# <img src="./src/main/resources/com/traube/stealthywhispergui/app-icon.png" alt="Wow&#8230; such Empty!" title="StealthyWhisper Icon" width="64"> StealthyWhisper
Stealthy Whisper is a ciphering tool that hides a message in another message as invisible characters.


## How it works

- If a secr⁡⁤⁡⁤‌⁠⁡⁤‍​⁡⁤‍​⁡⁤‍⁠⁡⁤‍⁣⁡﻿⁢⁡‌﻿⁡‌﻿⁡⁤﻿⁤⁡⁤‌﻿⁡⁤‍‌⁡⁤‍​⁡⁤‍‌⁡‌‍⁡⁤​⁢⁡⁤​‌⁡‌﻿⁡⁤﻿⁠⁡⁤‍‍⁡⁤⁠‍⁡⁤⁣⁢⁡⁤‌⁢⁡⁤‌﻿⁡‍‌⁡⁤⁢⁠⁡⁤​﻿⁡⁤⁠﻿⁡‍⁠et key is provided, the message is encrypted with AES using the key
- It Converts each letter of the message into their octal representation
- The octal numbers are then turned invisible by Substitution with invisible unicode characters
- This invisible message then gets randomly inserted into the visible message


## Example

| Visible Message | Hidden Message | Encoded Message                 | Invisible characters replaced with visible ones |
|:----------------|:---------------|:--------------------------------|:------------------------------------------------|
| Hello           | Secret         | Hel⁡⁤⁡⁤⁢⁣⁡⁤​‌⁡⁤​⁣⁡⁤‍⁢⁡⁤​‌⁡⁤‍​lo | Hel&#8203;***#1#123#145#143#162#145#164***lo    |


## How to use

1. Download the newest [release](../../releases)
2. Install Java 17 or newer
3. Run the JAR and have fun!


## License

StealthyWhisper is distributed under the [Eclipse Public License 2.0](./LICENSE)   
##### You are free to use StealthyWhisper in your own projects or redistribute it, but please give credit and link to this repo, thanks! &#128578;
