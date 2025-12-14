/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public abstract class Dusman {
    
    protected String id;
    protected double can;         
    protected double baslangicCani; 
    protected double hiz;         
    protected double zirh;         
    protected int odul;            
    protected int usHasari;
    protected boolean donmusMu; 
    protected double donmaBitisZamani; 
    protected double x;
    protected double y;
    protected boolean sagaBakiyor = true; 
    
   
    protected int hasarEfektSayaci = 0; 
    
    public int hedefNoktaIndeksi = 1; 

    public Dusman(String id, double can, double hiz, double zirh, int odul, int usHasari) {
        this.id = id;
        this.can = can;
        this.baslangicCani = can;
        this.hiz = hiz;
        this.zirh = zirh;
        this.odul = odul;
        this.usHasari = usHasari;
        this.donmusMu = false;
        this.donmaBitisZamani = 0; 
    }

   
    public void yavaslat(double saniye) {
        this.donmusMu = true;
        // Su anki zaman + (saniye * 1000) diyerek bitis zamanini milisaniye cinsinden hesapliyoruz
        this.donmaBitisZamani = System.currentTimeMillis() + (saniye * 1000);
    }


    public void hasarAl(double miktar) {
        
        this.can -= miktar;
        
        this.hasarEfektSayaci = 10; 
        
        if (this.can < 0) {
            
            this.can = 0;
      }
    }

 
    public boolean isHasarli() {
        return hasarEfektSayaci > 0;
    }

    public void hasarEfektiniGuncelle() {
        if (hasarEfektSayaci > 0) {
            
            hasarEfektSayaci--;
            }
    }

    
    public boolean olduMu() {
        return this.can <= 0;
    }

    public void yonGuncelle(double eskiX, double yeniX) {
        
        if (yeniX > eskiX) {
            
            sagaBakiyor = true; 
        }else if (yeniX < eskiX) {
            
            sagaBakiyor = false; 
      }
    }

 
    
    public boolean isSagaBakiyor() { return sagaBakiyor; } 
    
    public String getId() { return id; }
    
    public double getCan() { return can; }
    
    public double getBaslangicCani() { return baslangicCani; }
    
    
    
    
    public double getHiz() { return donmusMu ? hiz * 0.5 : hiz; }
    
    public double getZirh() { return zirh; }
    
    public int getOdul() { return odul; }
    
    public int getUsHasari() { return usHasari; }
    
    public double getX() { return x; }
    
    public double getY() { return y; }
    
    
    public void setKonum(double x, double y) {
        
        this.x = x;
        this.y = y;
    }
    
    public void setDonmus(boolean donmus) {
        
        this.donmusMu = donmus;
    }
    
    public boolean isDonmus() { 
        
        return donmusMu; 
    }

    public double getDonmaBitisZamani() { 
        return donmaBitisZamani; 
    }

    public void setDonmaBitisZamani(double zaman) { 
        
        this.donmaBitisZamani = zaman; 
    }
}