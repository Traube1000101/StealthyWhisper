package com.traube.stealthywhispergui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class StealthyWhisperAlgorithm {
    public static char[] invisibleCharacters = {
            '\u2060', // Word Joiner
            '\u2064', // Invisible Plus
            '\u2062', // Invisible Times
            '\u2063', // Invisible Separator
            '\u200B', // Zero Width Space (ZWSP)
            '\u200C', // Zero Width Non-Joiner (ZWNJ)
            '\u200D', // Zero Width Joiner (ZWJ)
            '\uFEFF', // Zero Width No-Break Space (ZWNBnSP)
    };

    public static char invisibleDelimiter = '\u2061'; // Function Application

    public static String insertMessage(String visibleMessage, String encodedMessage) {
        // Split visibleMessage randomly into 2 parts
        int splitIndex = (int) (Math.random() * (visibleMessage.length() - 2) + 1);
        String firstPart = visibleMessage.substring(0, splitIndex);
        String secondPart = visibleMessage.substring(splitIndex);

        // Insert encodedMessage between the 2 parts
        return firstPart + encodedMessage + secondPart;
    }

    public static String encodeMessage (String message) {
        // Check if Cipher Key is set
        String cipherKey = SettingsManager.getSetting("cipherKey", "");
        if (!Objects.equals(cipherKey, "")) {
            // If Cipher Key is set, use it to encrypt the message
            message = StealthyWhisperCipher.encrypt(message, cipherKey);
        }

        // Convert input string to octal string
        StringBuilder octal = new StringBuilder();
        for (int i = 0; i < Objects.requireNonNull(message).length(); i++) {
            char c = message.charAt(i);
            octal.append(String.format(invisibleDelimiter + "%o", (int) c));
        }

        // Replace every number from 0 to 7 in a string called test with index 0 to 7 of invisibleCharacters
        for (int i = 0; i < 8; i++) {
            octal = new StringBuilder(octal.toString().replace(String.valueOf(i), String.valueOf(invisibleCharacters[i])));
        }
        return octal.toString();
    }

    public static String[] decodeMessage(String encodedMessage) {
        //
        AtomicReference<String> output = new AtomicReference<>("");

        ArrayList<String> segments = new ArrayList<>(Arrays.asList(encodedMessage.split(String.valueOf(invisibleDelimiter))));
        int lastIndex = segments.size() - 1;
        int lastDigitIndex = 0;
        for (int i = 0, currentLastDigitIndex; i < 7; i++) {
            currentLastDigitIndex = segments.get(lastIndex).lastIndexOf(String.valueOf(invisibleCharacters[i]));
            if (currentLastDigitIndex > lastDigitIndex)
                lastDigitIndex = currentLastDigitIndex;
        }
        String[] visibleMessage = new String[2];

        visibleMessage[1] = segments.get(lastIndex).substring(lastDigitIndex + 1);
        segments.set(lastIndex, segments.get(lastIndex).substring(0, lastDigitIndex + 1));

        visibleMessage[0] = segments.remove(0);

        segments.forEach(segment ->{
            for (int i = 0; i < 8; i++) {
                segment = segment.replace(String.valueOf(invisibleCharacters[i]), String.valueOf(i));
            }

            String finalSegment = segment;
            output.updateAndGet(v -> v + (char) Integer.parseInt(finalSegment, 8));
        });
        // Check if Cipher Key is set
        String cipherKey = SettingsManager.getSetting("cipherKey", "");
        if (!Objects.equals(cipherKey, "")) {
            // If Cipher Key is set, use it to decrypt the message
            output.set(StealthyWhisperCipher.decrypt(output.get(), cipherKey));
        }

        return new String[]{output.get(), visibleMessage[0] + visibleMessage[1]};
    }
}
