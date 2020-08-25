/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.configs;

import java.util.Base64;

/**
 *
 * @author Nauman
 */
public class EncryptFactory {

    public static String encryptData(String data) {
        String encodedString = Base64.getEncoder().encodeToString(data.getBytes());
        return encodedString;
    }

    public static String decryptData(String data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
