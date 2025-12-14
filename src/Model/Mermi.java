/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

import java.util.List;

public class Mermi {
    private double x, y;
    private Dusman hedef;
    private Kule atanKule;
    private double hiz;
    private String tip;
    private boolean aktif = true;
    private List<Dusman> dusmanListesi;
    
    
    

    //Bu kod mermilerin hizini ve hangi dusmana ates edecegini belirler
    public Mermi(double baslangicX, double baslangicY, Dusman hedef, String tip, Kule atanKule, List<Dusman> dusmanListesi) {
        
        this.x = baslangicX;
        this.y = baslangicY;
        this.hedef = hedef;
        this.tip = tip;
        this.atanKule = atanKule; 
        this.dusmanListesi = dusmanListesi;
        
        if (tip.equals("ok")) this.hiz =8;
        
        else if (tip.equals("gulle")) this.hiz = 10;
        else this.hiz = 8; 
    }

    //Bu kod merminin hedefe ilerlemesini kontrol eder
    public void hareketEt() {
        
        if (hedef == null || hedef.olduMu()) {
            aktif = false;
            return;
        }

        double dx = hedef.getX() - x;
        double dy = hedef.getY() - y;
        double mesafe = Math.sqrt(dx * dx + dy * dy);

        if (mesafe < hiz) {
            
            vurusYap();
            aktif = false;
            
        } else {
            x += (dx / mesafe) * hiz;
            y += (dy / mesafe) * hiz;
        }
    }

    //Bu kod mermilerin dusmana verdigi hasari hesaplar
    private void vurusYap() {
        
        double hasar = atanKule.hasarHesapla(hedef);
        
        hedef.hasarAl(hasar);
        
        try {
            String logMesaji = String.format(
                    
                "SALDIRI: %s -> %s vurdu. [Zırh: %.0f] -> [Net Hasar: %.1f] [Kalan Can: %.1f]",
                    
                    
                atanKule.getId(),
                hedef.getId(),
                hedef.getZirh(),
                hasar,
                hedef.getCan()
            );
            OyunGunlugu.logYaz(logMesaji);
            
        } catch (Exception e) {}

        if (atanKule instanceof BuzKulesi) {
            
            hedef.yavaslat(3.0);
        }

        if (atanKule instanceof Topcu) {
            
            for (Dusman d : dusmanListesi) {
                
                if (d == hedef || d instanceof Dragon || d.olduMu()) continue;
                
                double mesafe = Math.sqrt(Math.pow(d.getX() - hedef.getX(), 2) + Math.pow(d.getY() - hedef.getY(), 2));
                
                
                if (mesafe <= 75) {
                    
                    double alanHasari = atanKule.hasarHesapla(d);
                    d.hasarAl(alanHasari);
                    
                    
                    try {
                        OyunGunlugu.logYaz("ALAN HASARI: " + atanKule.getId() + " -> " + d.getId() + " (Ekstra " + String.format("%.1f", alanHasari) + " hasar)");
                    } catch(Exception ex){}
                }
            }
        }
        
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public String getTip() { return tip; }
    public boolean isAktif() { return aktif; }
    public Dusman getHedef() { return hedef; }
}