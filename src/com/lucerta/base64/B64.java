package com.lucerta.base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class B64
{
    public static String encodeToString(byte[] data, boolean urlSafe)
    {
        data = B64Converter.encode(data, urlSafe);
        return new String(data, StandardCharsets.US_ASCII);
    }

    public static String encodeToString(String text, Charset inputCharset, boolean urlSafe)
    {
        byte[] data = text.getBytes(inputCharset);
        data = B64Converter.encode(data, urlSafe);
        return new String(data, StandardCharsets.US_ASCII);
    }

    public static String encodeToString(String text, boolean urlSafe)
    {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        data = B64Converter.encode(data, urlSafe);
        return new String(data, StandardCharsets.US_ASCII);
    }

    public static byte[] encodeToBytes(byte[] data, boolean urlSafe)
    {
        return B64Converter.encode(data, urlSafe);
    }

    public static byte[] encodeToBytes(String text, Charset inputCharset, boolean urlSafe)
    {
        byte[] data = text.getBytes(inputCharset);
        return B64Converter.encode(data, urlSafe);
    }

    public static byte[] encodeToBytes(String text, boolean urlSafe)
    {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        return B64Converter.encode(data, urlSafe);
    }

    public static byte[] decodeToBytes(String text)
    {
        byte[] data = text.getBytes(StandardCharsets.US_ASCII);
        return B64Converter.decode(data);
    }

    public static byte[] decodeToBytes(byte[] data)
    {
        return B64Converter.decode(data);
    }

    public static String decodeToString(String text, Charset outputCharset)
    {
        byte[] data = text.getBytes(StandardCharsets.US_ASCII);
        data = B64Converter.decode(data);
        return new String(data, outputCharset);
    }

    public static String decodeToString(String text)
    {
        byte[] data = text.getBytes(StandardCharsets.US_ASCII);
        data = B64Converter.decode(data);
        return new String(data, StandardCharsets.UTF_8);
    }

    public static String decodeToString(byte[] data, Charset outputCharset)
    {
        data = B64Converter.decode(data);
        return new String(data, outputCharset);
    }

    public static String decodeToString(byte[] data)
    {
        data = B64Converter.decode(data);
        return new String(data, StandardCharsets.UTF_8);
    }

}