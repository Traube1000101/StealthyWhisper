package com.traube.stealthywhisper;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Algorithm {
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

    public static String encodeMessage (String message, byte type) {

        // Check if Cipher Key is set and then encrypts the message
        String cipherKey = SettingsManager.getSetting("cipherKey", "");
        // Add type to the beginning of the message
        message = (char)type + message;
        if (!Objects.equals(cipherKey, "")) {
            // If Cipher Key is set, use it to encrypt the message
            message = Cipher.encrypt(message, cipherKey);
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

    public static String[] decodeMessage(String encodedMessage) throws Exception {
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

        //print all segments if DEBUG is true
        if (Application.DEBUG) {
            System.out.println("Segments:");
            segments.forEach(System.out::println);
        }

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
            output.set(Cipher.decrypt(output.get(), cipherKey));
        }

        byte type = (byte)output.get().substring(0, 1).charAt(0);
        if (type == (byte)1) {
            return new String[]{output.get().substring(1), visibleMessage[0] + visibleMessage[1]};
        }
        else {
            Locale locale = new Locale(SettingsManager.getSetting("locale", Locale.getDefault().getCountry()));
            ResourceBundle langBundle = ResourceBundle.getBundle("com.traube.bundles.lang", locale);
            throw new Exception(langBundle.getString("general.error.unsupported_message_type"));
        }
    }
}
