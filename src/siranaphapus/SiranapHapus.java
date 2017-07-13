/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siranaphapus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.HttpClient;

/**
 *
 * @author INSTI
 */
public class SiranapHapus {

    /**
     * @param args the command line arguments
     */
    //Web Service header
    private static final String xrsid = "KodeRSKemenkes";
    private static final String xpass = "PasswordSIRSOnline";
    private static final String strURLSiranap = "http://sirs.yankes.kemkes.go.id/sirsservice/sisrute/hapusdata/";

    // Generate MD5
    private static String generateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        String kode_tipe_pasien = "0011";
        String kode_kelas_ruang = "0004";
        try {
            // Declare a header
            PostMethod post = new PostMethod(strURLSiranap + xrsid + "/" + kode_tipe_pasien + "/" + kode_kelas_ruang);
            post.setRequestHeader("content-type", "application/xml; charset=ISO-8859-1");
            post.setRequestHeader("X-rs-id", xrsid);
            post.setRequestHeader("X-pass", generateMD5(xpass));
            HttpClient httpClient = new HttpClient();

            //Post a data
            int result = httpClient.executeMethod(post);
            System.out.println("Response status code: " + result);
            System.out.println("Response body: ");
            System.out.println(post.getResponseBodyAsString());
            post.releaseConnection();
        } catch (IOException ex) {
            Logger.getLogger(SiranapHapus.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
