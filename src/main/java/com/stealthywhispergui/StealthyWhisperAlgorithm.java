package com.stealthywhispergui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class StealthyWhisperAlgorithm {
    static char[] invisibleCharacters = {
            '\u2060', // Word Joiner
            '\u2064', // Invisible Plus
            '\u2062', // Invisible Times
            '\u2063', // Invisible Separator
            '\u200B', // Zero Width Space (ZWSP)
            '\u200C', // Zero Width Non-Joiner (ZWNJ)
            '\u200D', // Zero Width Joiner (ZWJ)
            '\uFEFF', // Zero Width No-Break Space (ZWNBnSP)
    };

    static char invisibleDelimiter = '\u2061'; // Function Application

    static String insertMessage(String visibleMessage, String secretMessage) {
        // Split visibleMessage randomly into 2 parts

        int splitIndex = (int) (Math.random() * (visibleMessage.length() - 2) + 1);
        String firstPart = visibleMessage.substring(0, splitIndex);
        String secondPart = visibleMessage.substring(splitIndex);
        return firstPart + encodeMessage(secretMessage) + secondPart;
    }

    static String encodeMessage (String message) {
        //convert input string to octal string
        String octal = "";
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            octal += String.format(invisibleDelimiter + "%o", (int) c);
        }

        //replace every number from 0 to 7 in a string called test with index 0 to 7 of invisibleCharacters
        for (int i = 0; i < 8; i++) {
            octal = octal.replace(String.valueOf(i), "" + invisibleCharacters[i]);
        }
        return octal;
    }

    static String[] decodeMessage(String encodedMessage) {
        AtomicReference<String> output = new AtomicReference<>("");

        ArrayList<String> segments = new ArrayList<String>();
        segments.addAll(Arrays.asList(encodedMessage.split(String.valueOf(invisibleDelimiter))));
        int lastIndex = segments.size() - 1;
        int lastDigitIndex = 0;
        for (int i = 0, currentLastDigitIndex = 0; i < 7; i++) {
            currentLastDigitIndex = segments.get(lastIndex).lastIndexOf(String.valueOf(invisibleCharacters[i]));
            if (currentLastDigitIndex > lastDigitIndex)
                lastDigitIndex = currentLastDigitIndex;
        }
        String visibleMessage[] = new String[2];

        visibleMessage[1] = segments.get(lastIndex).substring(lastDigitIndex + 1);
        segments.set(lastIndex, segments.get(lastIndex).substring(0, lastDigitIndex + 1));

        visibleMessage[0] = segments.remove(0);

        segments.forEach(segment ->{
            for (int i = 0; i < 8; i++) {
                segment = segment.replace( "" + invisibleCharacters[i], String.valueOf(i));
            }

            String finalSegment = segment;
            output.updateAndGet(v -> v + (char) Integer.parseInt(finalSegment, 8));
        });
        String messages[] = {output.get(), visibleMessage[0] + visibleMessage[1]};

        return messages;
    }
}
