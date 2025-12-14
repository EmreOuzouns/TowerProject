/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import View.GamePanel;
import View.KontrolPaneli;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.swing.Timer;
import javax.swing.JOptionPane;






public class GameEngine implements ActionListener {

    private GamePanel panel;
    private KontrolPaneli kontrolPaneli;
    
    private int oyuncuParasi = 200;
    private int oyuncuCani = 50;
    private String secilenKuleTipi = null;
    
    private List<Dusman> dusmanlar = new ArrayList<>();
    private List<Kule> kuleler = new ArrayList<>();
    private List<Mermi> mermiler = new ArrayList<>();
    
    private Timer oyunZamanlayicisi;
    private Point[] yolNoktalari;
    
    private boolean oyunDuraklatildi = false; //  Duraklatma kontrol degiskeni
    
    
    
    

    // Kulelerin koyulacagi yerler
    private Point[] kuleYuvalari = {
        new Point(324, 130), new Point(406, 350),
        new Point(360, 580), new Point(560, 545),
        new Point(570, 815)
    };

    private int dalgaSayisi = 0;
    private int uretilenDusmanSayisi = 0;
    private long sonUretimZamani = 0;
    private int dalga1Limiti = 5;  
    private int dalga2Limiti = 10; 
    private int oyunDurumu = 0; 

    //Bu kod oyunu olusturan ilk calisan koddur
    public GameEngine(GamePanel panel, KontrolPaneli kontrolPaneli) {
        this.panel = panel;
        this.kontrolPaneli = kontrolPaneli;
        
        try {
            
             OyunGunlugu.temizle();
             
             OyunGunlugu.logYaz("SİMÜLASYON BAŞLADI. Harita Yüklendi. Can: " + oyuncuCani + ", Para: " + oyuncuParasi);
             
        } catch(Exception e) {

            System.out.println("Loglama hatası: " + e.getMessage());
        }
        
        yolNoktalari = new Point[] {
            
            new Point(455, 0),
            new Point(455, 235),
            new Point(300, 235),
            new Point(300, 455),
            new Point(455, 455),
            new Point(455, 680),
            new Point(680, 680),
            new Point(680, 1050)
        };

        butonlariAktifEt();
        
        mouseDinleyicisiEkle();

        oyunZamanlayicisi = new Timer(16, this);
        
        
        dalgaBaslat();
        
        oyunZamanlayicisi.start();
    }

    private void butonlariAktifEt() {
        
        kontrolPaneli.btnArbalet.addActionListener(e -> secilenKuleTipi = "Okcu");
        
        kontrolPaneli.btnMancinik.addActionListener(e -> secilenKuleTipi = "Topcu");
        
        kontrolPaneli.btnBuyucu.addActionListener(e -> secilenKuleTipi = "BuzKulesi");
    }

    //Bu kod ekranan tiklayarak kuleleri insa etmek icin
    private void mouseDinleyicisiEkle() {
        
        panel.addMouseListener(new MouseAdapter() {
            
            
            
            
            @Override
            public void mouseClicked(MouseEvent e) {
                
                
                if (e.getX() >= panel.getWidth() - 60 && e.getY() <= 60) {
                    
                    
                    oyunDuraklatildi = !oyunDuraklatildi; // Durumu tersine cevir
                    panel.setOyunDuraklatildi(oyunDuraklatildi); // Panele bildir
                    
                    if(oyunDuraklatildi) OyunGunlugu.logYaz("OYUN DURAKLATILDI.");
                    else OyunGunlugu.logYaz("OYUN DEVAM EDİYOR.");
                    
                    return; // Kule insa etmeye calismasin
                }
                
                // Eger oyun duraklatildiysa kule insa etme
                if (!oyunDuraklatildi) {
                    
                    kuleInsaEt(e.getX(), e.getY());
      }
         }
        });
    }

    private void kuleInsaEt(int tiklananX, int tiklananY) {
        
        try {
            if (oyunDurumu != 0) return; 
            if (secilenKuleTipi == null) return; 
            

            Point secilenYuva = null;
            for (Point p : kuleYuvalari) {
                
                if (p.distance(tiklananX, tiklananY) < 50) {
                    
                    secilenYuva = p; break;
                }
            }
            if (secilenYuva == null) return; 
            
           

            for (Kule k : kuleler) {
                
                if ((int)k.getX() == secilenYuva.x && (int)k.getY() == secilenYuva.y) {
                    
                    System.out.println("UYARI: Burasi dolu!"); return;
                }
            }

            Kule yeniKule = null;
            if (secilenKuleTipi.equals("Okcu")) yeniKule = new Okcu("Kule-" + kuleler.size());
            
            else if (secilenKuleTipi.equals("Topcu")) yeniKule = new Topcu("Kule-" + kuleler.size());
            
            
            else if (secilenKuleTipi.equals("BuzKulesi")) yeniKule = new BuzKulesi("Kule-" + kuleler.size());

            if (yeniKule == null) return;

            if (oyuncuParasi < yeniKule.getFiyat()) {
                
                 System.out.println("UYARI: Paran Yetmiyor!");
                 
                 OyunGunlugu.logYaz("İNSAAT HATASI: " + secilenKuleTipi + " icin para yetersiz. Mevcut: " + oyuncuParasi);
                 return;
            }
            
            yeniKule.setKonum(secilenYuva.x, secilenYuva.y);
            
            kuleler.add(yeniKule);
            
            oyuncuParasi -= yeniKule.getFiyat();
            
            OyunGunlugu.logYaz("İNSAAT: " + secilenKuleTipi + " kuruldu. Konum: (" + secilenYuva.x + "," + secilenYuva.y + "). Kalan Para: " + oyuncuParasi);
            
            
            secilenKuleTipi = null; 
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void dalgaBaslat() {
        dalgaSayisi++;
        uretilenDusmanSayisi = 0;
        OyunGunlugu.logYaz("DALGA " + dalgaSayisi + " BASLADI.");
    }

    //Bu kod verileri ve gorseli gunceller
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
      
        if (oyunDuraklatildi) {
            
            panel.repaint(); // Sadece ekrani tazele (pause yazisi icin)
            return;
        }

        oyunuGuncelle();
        panel.listeleriGuncelle(dusmanlar, kuleler, mermiler, oyuncuParasi, oyuncuCani, dalgaSayisi, oyunDurumu);
        
        kontrolPaneli.btnArbalet.setEnabled(oyuncuParasi >= 50);
        
        kontrolPaneli.btnMancinik.setEnabled(oyuncuParasi >= 75);
        
        kontrolPaneli.btnBuyucu.setEnabled(oyuncuParasi >= 70);
    }

    private void oyunuGuncelle() {
        
        if (oyunDurumu != 0) return;

        long suAnkiZaman = System.currentTimeMillis();
        
        double suAnkiZamanSaniye = suAnkiZaman / 1000.0; 

        //Bu kod dalgayla ilgi her seyi kontrol eder
        if (oyunDurumu == 0) {
            
            if (dalgaSayisi == 1) {
                
                
                if (uretilenDusmanSayisi < dalga1Limiti) {
                    
                    if (suAnkiZaman - sonUretimZamani > 2000) { dusmanUret(1); sonUretimZamani = suAnkiZaman; }
                } else if (dusmanlar.isEmpty()) { dalgaSayisi = 2; uretilenDusmanSayisi = 0; OyunGunlugu.logYaz("Dalga 1 Bitti. Dalga 2 Geliyor..."); }
                
            } else if (dalgaSayisi == 2) {
                
                if (uretilenDusmanSayisi < dalga2Limiti) {
                    
                    if (suAnkiZaman - sonUretimZamani > 1500) { dusmanUret(2); sonUretimZamani = suAnkiZaman; }
                }else if (dusmanlar.isEmpty()) { 
                    oyunDurumu = 1; 
                    OyunGunlugu.logYaz("OYUN SONU: TEBRİKLER! KAZANDINIZ. Kalan Can: " + oyuncuCani);
                }
                }
        }

        //Bu kod dusmanlara ait her seyi kontrol eder olub olmedigini kontrol eder
        for (int i = 0; i < dusmanlar.size(); i++) {
            Dusman d = dusmanlar.get(i);
        
            if (d.isDonmus()) {
                if (suAnkiZaman >= d.getDonmaBitisZamani()) {
                    
                    d.setDonmus(false);
                }
            }
     

            dusmanHareket(d);
            
            if (d.hedefNoktaIndeksi >= yolNoktalari.length) { 
                 oyuncuCani -= d.getUsHasari();
                 
                 OyunGunlugu.logYaz("HASAR: " + d.getId() + " üsse girdi! -" + d.getUsHasari() + " Can. Kalan Can: " + oyuncuCani);
                 
                 
                 if(oyuncuCani <= 0) { 
                     
                     oyuncuCani = 0; 
                     oyunDurumu = 2; 
                     OyunGunlugu.logYaz("OYUN SONU: KAYBETTİNİZ! Üs düştü.");
                 }
                 dusmanlar.remove(i); i--;
                 
            } 
            else if(d.olduMu()) {
                
                oyuncuParasi += d.getOdul();
                
                OyunGunlugu.logYaz("ZAFER: " + d.getClass().getSimpleName() + " öldürüldü. +" + d.getOdul() + " Gold. Toplam: " + oyuncuParasi);
                
                dusmanlar.remove(i); i--;
            }
        }
        
        //Bu kod kulelerin dusmana hasar verir ve mermileri ekranda gosterir
        for(Kule k : kuleler) {
            
            if(k.saldir(dusmanlar, suAnkiZamanSaniye)) {
                
                String tip = "";
                
                if(k instanceof Okcu) tip = "ok";
                
                else if(k instanceof Topcu) tip = "gulle";
                
                else if(k instanceof BuzKulesi) tip = "buz";

                if(k.sonHedef != null) {
                    
                    mermiler.add(new Mermi(k.getX(), k.getY(), k.sonHedef, tip, k, dusmanlar));
               
              }
        }
        }
        
        //Bu kod merminin dusmana carpmasini saglar
        Iterator<Mermi> it = mermiler.iterator();
        while(it.hasNext()){
            Mermi m = it.next();
            m.hareketEt();
            if(!m.isAktif()){
                it.remove();
         }
        }
    }

    //Bu kod kacinci dalgada oldugumuzu belirler ve dusman uretir
    private void dusmanUret(int dalgaNo) {
        
        String id = "D-" + dalgaNo + "-" + uretilenDusmanSayisi;
        
        Dusman yeni = null;
        if (dalgaNo == 1) {
            
            if (uretilenDusmanSayisi < 2) yeni = new Viking(id);
            
            else if (uretilenDusmanSayisi == 2) yeni = new King(id);
            
            else if (uretilenDusmanSayisi == 3) yeni = new Dragon(id);
            
            else yeni = new Viking(id);
        } else {
            if (uretilenDusmanSayisi % 3 == 0) yeni = new Viking(id);
            
            else if (uretilenDusmanSayisi % 3 == 1) yeni = new King(id);
            
            else yeni = new Dragon(id);
        }
        yeni.setKonum(yolNoktalari[0].x, yolNoktalari[0].y);
        
        yeni.hedefNoktaIndeksi = 1; 
        
        dusmanlar.add(yeni);
        
        uretilenDusmanSayisi++;
        
        OyunGunlugu.logYaz("SPAWN: " + yeni.getClass().getSimpleName() + " sahaya girdi.");
    }

    //Bu kod dusmanin yoldan gitmesini sagalr
    
    private void dusmanHareket(Dusman d) {
        
        int h = d.hedefNoktaIndeksi;
        
        if(h >= yolNoktalari.length) return;
        
        Point hedef = yolNoktalari[h];
        
        double eskiX = d.getX();
        double dx = hedef.x - d.getX();
        double dy = hedef.y - d.getY();
        double dist = Math.sqrt(dx*dx + dy*dy);
        double hiz = d.getHiz() * 0.05; 
        
        
        if(dist < hiz + 3) d.hedefNoktaIndeksi++;
        
        else {
            d.setKonum(d.getX() + (dx/dist)*hiz, d.getY() + (dy/dist)*hiz);
            d.yonGuncelle(eskiX, d.getX());
        }
    }
}