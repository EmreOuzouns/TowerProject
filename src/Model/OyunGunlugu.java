/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;







//Bu class oyunda bas veren hareketlenmenin mesela dusmanin olmesini text olarak yamagimizi saglar
public class OyunGunlugu {
    
    private static final String DOSYA_ADI = "savunma_gunlugu.txt";
    

    public static void logYaz(String mesaj) {
        try {
            
            
            FileWriter fw = new FileWriter(DOSYA_ADI, true);
            PrintWriter pw = new PrintWriter(fw);
            
            LocalDateTime simdi = LocalDateTime.now();
            
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            String zaman = simdi.format(format);
            
            pw.println("[" + zaman + "] " + mesaj);
            
            System.out.println("[" + zaman + "] " + mesaj);
            
            pw.close();
            
        } catch (IOException e) {
            
            System.err.println("Log yazma hatası: " + e.getMessage());
        }
    }
    
    public static void temizle() {
        
        try {
            new PrintWriter(DOSYA_ADI).close();
        } catch (IOException e) {
            e.printStackTrace();
       
        
  }
 }
}