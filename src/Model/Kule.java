/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;

public abstract class Kule {
    
    protected String id;
    protected int hasar;
    protected double menzil;
    protected double atesHizi;
    protected double sonAtesZamani; 
    protected int fiyat;
    protected double x;
    protected double y;
    
    
    
  
    public Dusman sonHedef = null; 

    public Kule(String id, int hasar, double menzil, double atesHizi, int fiyat) {
      
        this.id = id;
        this.hasar = hasar;
        this.menzil = menzil;
        this.atesHizi = atesHizi;
        this.fiyat = fiyat;
    }


    public double hasarHesapla(Dusman hedef) {
        
        double zirh = hedef.getZirh();
        double azaltma = zirh / (zirh + 100.0);
        return this.hasar * (1.0 - azaltma);
    }
    
    public int getHasarMiktari() { return hasar; } 

    // Bu kod hangi dusmana ates edecegini belirler usse en yakin olani hedef alir
    public Dusman hedefBul(List<Dusman> dusmanlar) {
        
        Dusman enIyiHedef = null;
        
        int enBuyukIlerleme = -1; 
        

        for (Dusman e : dusmanlar) {
            if (e.olduMu()) continue;
            
            if (this instanceof Topcu && e instanceof Dragon) continue; 

            if (mesafeHesapla(e) <= this.menzil) {
                
                if (e.hedefNoktaIndeksi > enBuyukIlerleme) {
                    
                    enBuyukIlerleme = e.hedefNoktaIndeksi;
                    enIyiHedef = e;
                }
            }
        }
        return enIyiHedef;
    }
    
    // Bu kod dusmanin kulenin menziline girip girmedigini hesaplar
    private double mesafeHesapla(Dusman e) {
        return Math.sqrt(Math.pow(e.getX() - this.x, 2) + Math.pow(e.getY() - this.y, 2));
    }
    
    
    public boolean saldir(List<Dusman> dusmanlar, double suAnkiZaman) {
        
        if (suAnkiZaman - sonAtesZamani < atesHizi) return false; 

        Dusman hedef = hedefBul(dusmanlar);

        if (hedef != null) {
            
            sonHedef = hedef;
            sonAtesZamani = suAnkiZaman;
            return true; 
        }
        return false; 
    }

    public void setKonum(double x, double y) { this.x = x; this.y = y; }
    public int getFiyat() { return fiyat; }
    public String getId() { return id; }
    public double getMenzil() { return menzil; }
    public double getX() { return x; } 
    public double getY() { return y; } 
}