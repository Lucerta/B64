package com.lucerta.base64;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class B64Converter
{
    public static byte[] encode(byte[] data, boolean urlSafe)
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        for (int i = 0; i < data.length; i += 3)
        {
            int length = Math.min(3, data.length - i);
            byte[] array = Arrays.copyOfRange(data, i, i + length);
            array = convert3to4(array, urlSafe);
            buffer.write(array, 0, array.length);
        }

        return buffer.toByteArray();
    }

    public static byte[] decode(byte[] data)
    {
        data = filterEncodedData(data);
        
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        for (int i = 0; i < data.length; i += 4)
        {
            int length = Math.min(4, data.length - i);
            byte[] array = Arrays.copyOfRange(data, i, i + length);
            array = convert4to3(array);
            buffer.write(array, 0, array.length);
        }

        return buffer.toByteArray();
    }

    private static byte[] convert3to4(byte[] array, boolean urlSafe)
    {
        char[] table = urlSafe ? B64_CHARS_URLSAFE : B64_CHARS_DEFAULT;

        int iVal = 0;
        for (int i = 0; i < array.length; i++)
        {
            int b = array[i] & 0xFF;
            iVal += b << ((2 - i) * 8);
        }

        array = new byte[array.length + 1];

        for (int i = 0; i < array.length; i++)
        {
            int index = (iVal >> ((3 - i) * 6)) & 63;
            array[i] = (byte)table[index];
        }

        boolean padding = !urlSafe && array.length < 4;
        if (padding) array = padRight(array, 4 - array.length, (byte)'=');

        return array;
    }

    private static byte[] convert4to3(byte[] array)
    {
        int iVal = 0;
        for (int i = 0; i < array.length; i++)
        {
            int b = array[i] & 63;
            iVal += b << ((3 - i) * 6);
        }

        array = new byte[array.length - 1];

        for (int i = 0; i < array.length; i++)
        {
            array[i] = (byte)((iVal >> ((2 - i) * 8)) & 0xFF);
        }

        return array;
    }
    
    private static byte[] filterEncodedData(byte[] data)
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        boolean ending = false;
        for (int i = 0; i < data.length; i++)
        {
            char c = (char)data[i];
            if (c == '=')
            {
                ending = true;
                continue;
            }
            int b = REVERSE_LOOKUP[c];
            if (b == -2) continue; // b = 9 (tab), 10 (lf), 13 (cr) or 32 (spc)
            if (ending || b == -1) throw new IllegalArgumentException("Invalid char val=" + (int)c);
            buffer.write(b);
        }
        return buffer.toByteArray();
    }

    private static byte[] padRight(byte[] array, int padLength, byte padChar)
    {
        if (padLength == 0) return array;
        int newLength = array.length + padLength;
        byte[] result = Arrays.copyOf(array, newLength);
        for (int i = array.length; i < newLength; i++) result[i] = padChar;
        return result;
    }

    private final static char[] B64_CHARS_DEFAULT = {
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
        'Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
        'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',
        'w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/' };
    private final static char[] B64_CHARS_URLSAFE = {
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
        'Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
        'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',
        'w','x','y','z','0','1','2','3','4','5','6','7','8','9','-','_' };
    private final static int[] REVERSE_LOOKUP = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -1, -1, -2, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63,
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
        -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63,
        -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

}
