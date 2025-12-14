/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package View;

import Model.Dusman;
import Model.Kule;
import Model.Mermi;
import Model.Viking;
import Model.King;
import Model.Dragon;
import Model.Okcu;
import Model.Topcu;
import Model.BuzKulesi;




import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Graphics2D; 
import java.awt.geom.AffineTransform; 
import java.awt.AlphaComposite;       
import java.awt.image.BufferedImage;    
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;






public class GamePanel extends JPanel {
    
    private Image haritaResmi;
    private Image vikingResmi, kingResmi, dragonResmi;
    private Image okcuResmi, mancinikResmi, buyucuResmi;
    private Image coinResmi, kalpResmi;
    private Image winResmi, gameOverResmi;
    private Image okResmi, gulleResmi, buzTopuResmi;
    private Image pauseResmi; 
    
    
    
    
    
    private List<Dusman> dusmanlar = new ArrayList<>();
    private List<Kule> kuleler = new ArrayList<>();
    private List<Mermi> mermiler = new ArrayList<>();

    
    
    
    
    private int guncelPara = 200;
    private int guncelCan = 100;
    private int guncelDalga = 1;
    private int oyunDurumu = 0; 
    private boolean oyunDuraklatildi = false; 
    
    public GamePanel() {
        
        this.setBackground(new Color(34, 139, 34)); 
        
        resimleriYukle();
    }
    
    private void resimleriYukle() {
        try {
            haritaResmi = new ImageIcon(getClass().getResource("/Resources/Map.jpg")).getImage();
            
            vikingResmi = new ImageIcon(getClass().getResource("/Resources/viking.png")).getImage();
            
            kingResmi = new ImageIcon(getClass().getResource("/Resources/zirhli.png")).getImage(); 
            
            dragonResmi = new ImageIcon(getClass().getResource("/Resources/dragon.png")).getImage();
            
            okcuResmi = new ImageIcon(getClass().getResource("/Resources/okcu.png")).getImage();
            
            mancinikResmi = new ImageIcon(getClass().getResource("/Resources/topcu.png")).getImage();
            
            buyucuResmi = new ImageIcon(getClass().getResource("/Resources/buzkulesi.png")).getImage();
            
            coinResmi = new ImageIcon(getClass().getResource("/Resources/coin.png")).getImage();
            
            kalpResmi = new ImageIcon(getClass().getResource("/Resources/kalp.png")).getImage();
            
            winResmi = new ImageIcon(getClass().getResource("/Resources/win.png")).getImage();
            
            gameOverResmi = new ImageIcon(getClass().getResource("/Resources/gameover.png")).getImage();
            
            okResmi = new ImageIcon(getClass().getResource("/Resources/ok.png")).getImage();
            
            gulleResmi = new ImageIcon(getClass().getResource("/Resources/gulle.png")).getImage();
            
            buzTopuResmi = new ImageIcon(getClass().getResource("/Resources/buztopu.png")).getImage();
            
            try {
                
                pauseResmi = new ImageIcon(getClass().getResource("/Resources/pause.png")).getImage();
            } catch (Exception ex) {
            }
            
        } catch (Exception e) {
            
            System.err.println("HATA: Resim dosyaları eksik!");
        }
    }
    
    public void setOyunDuraklatildi(boolean duraklatildi) {
        
        this.oyunDuraklatildi = duraklatildi;
        repaint();
    }

    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (haritaResmi != null) g.drawImage(haritaResmi, 0, 0, getWidth(), getHeight(), this);
        
        // Kule icin daire konumlari
        java.awt.Point[] yuvalar = {
            new java.awt.Point(324, 130), new java.awt.Point(406, 350),
            new java.awt.Point(360, 580), new java.awt.Point(560, 545),
            new java.awt.Point(570, 815)
        };
        int yuvaCapi = 40; 
        for (java.awt.Point p : yuvalar) {
            
            g.setColor(new Color(0, 191, 255, 70)); 
            
            g.fillOval(p.x - (yuvaCapi/2), p.y - (yuvaCapi/2), yuvaCapi, yuvaCapi);
            
            g.setColor(Color.WHITE);
            
            g.drawOval(p.x - (yuvaCapi/2), p.y - (yuvaCapi/2), yuvaCapi, yuvaCapi);
        }

        // Kuleleri cizmek ve boyutlari belirleriz
        for (Kule k : kuleler) {
            
            Image resim = null;
            if (k instanceof Okcu) resim = okcuResmi;
            
            else if (k instanceof Topcu) resim = mancinikResmi;
            
            else if (k instanceof BuzKulesi) resim = buyucuResmi;
            
            int kuleBoyut = 160; 
            
            if (resim != null) {
                int dikeyAyar = -20;
                
                if (k instanceof BuzKulesi) dikeyAyar = -15;
                
                int cizimX = (int)k.getX() - (kuleBoyut / 2 + 40);
                int cizimY = (int)k.getY() - (kuleBoyut / 2) + dikeyAyar; 
                
                
                g.drawImage(resim, cizimX, cizimY, kuleBoyut, kuleBoyut, null);
            } 
        }

       
        for (Dusman d : dusmanlar) {
            
            if(d.olduMu()) continue;
            
            
            // Hasar sayacını her çizimde azalt
            d.hasarEfektiniGuncelle();
            
            Image resim = null;
            int w = 50, h = 50;
            if (d instanceof Viking) { resim = vikingResmi; w = 70; h = 70; } 
            
            else if (d instanceof King) { resim = kingResmi; w = 100; h = 100; } 
            
            else if (d instanceof Dragon) { resim = dragonResmi; w = 200; h = 125; }
            
            int cizimX = (int)d.getX() - (w / 2);
            int cizimY = (int)d.getY() - (h / 2);
            
            if (resim != null) {
            
                if (d.isHasarli()) {
                    
                    Image kirmiziResim = kirmiziEfektliResimOlustur(resim, w, h);
                    g.drawImage(kirmiziResim, cizimX, cizimY, w, h, null);
                } else {
                    
              
                    g.drawImage(resim, cizimX, cizimY, w, h, null);
                }
            } else { 
                
    
                g.setColor(d.isHasarli() ? new Color(255, 0, 0, 150) : Color.RED); 
                g.fillRect(cizimX, cizimY, w, h); 
            }
            
            // Can barlari
            g.setColor(Color.RED); g.fillRect(cizimX, cizimY - 10, w, 5);
            g.setColor(Color.GREEN);
            int bar = (int)((d.getCan() / d.getBaslangicCani()) * w);
            g.fillRect(cizimX, cizimY - 10, bar, 5);
        }
        
        //Mermileri cizerek dusmana dogru hareket etmesini saglar
        for (Mermi m : mermiler) {
            Image mermiResmi = null;
            
            int mW = 20; 
            int mH = 20;

            if (m.getTip().equals("ok")) { 
                
                mermiResmi = okResmi; 
                mW = 50; mH = 100;
            }
            else if (m.getTip().equals("gulle")) { 
                
                mermiResmi = gulleResmi; 
                mW = 20; mH = 20; 
            }
            else if (m.getTip().equals("buz")) { 
                
                mermiResmi = buzTopuResmi; 
                mW = 20; mH = 20; 
            }

            if (mermiResmi != null) {
                if (m.getTip().equals("ok") && m.getHedef() != null) {
                    
                    Graphics2D g2d = (Graphics2D) g; 
                    AffineTransform old = g2d.getTransform(); 

                    double dx = m.getHedef().getX() - m.getX();
                    double dy = m.getHedef().getY() - m.getY();
                    
                    double angle = Math.atan2(dy, dx);

                    g2d.translate(m.getX(), m.getY());
                    g2d.rotate(angle);

                    g2d.drawImage(mermiResmi, -mW/2, -mH/2, mW, mH, null);
                    
                    g2d.setTransform(old);
                } else {
                    
                    g.drawImage(mermiResmi, (int)m.getX() - mW/2, (int)m.getY() - mH/2, mW, mH, null);
                }
            }
        }
        
        // Bilgi Paneli
        g.setColor(new Color(0, 0, 0, 150));
        
        g.fillRoundRect(10, 10, 380, 60, 20, 20);
        
        g.setFont(new Font("Arial", Font.BOLD, 24));
        
        if (kalpResmi != null) g.drawImage(kalpResmi, 20, 20, 40, 40, null);
        
        g.setColor(Color.WHITE); g.drawString("" + guncelCan, 70, 50);
        
        if (coinResmi != null) g.drawImage(coinResmi, 140, 20, 40, 40, null);
        
        g.setColor(Color.YELLOW); g.drawString("" + guncelPara, 190, 50);
        
        g.setColor(Color.CYAN); g.drawString("Dalga: " + guncelDalga, 270, 50);
        
        
        
        
        int btnX = getWidth() - 90;
        int btnY = 10;
        int btnSize = 50;
        
        if (pauseResmi != null) {
            g.drawImage(pauseResmi, btnX, btnY, btnSize, btnSize, null);
        } else {
         
            g.setColor(new Color(0, 0, 0, 150));
            
            g.fillRoundRect(btnX, btnY, 80, btnSize, 10, 10);
            
            g.setColor(Color.WHITE);
            
            g.setFont(new Font("Arial", Font.BOLD, 12));
            
            g.drawString("DURAKLAT", btnX + 10, btnY + 30);
        }
        
        if (oyunDuraklatildi) {
             g.setColor(new Color(0, 0, 0, 150));
             
             g.fillRect(0, 0, getWidth(), getHeight());
             
             g.setColor(Color.WHITE);
             
             g.setFont(new Font("Arial", Font.BOLD, 60));
             
             g.drawString("DURAKLATILDI", getWidth()/2 - 220, getHeight()/2);
        }

        if (oyunDurumu == 1) { 
            
            g.setColor(new Color(0, 0, 0, 150)); g.fillRect(0, 0, getWidth(), getHeight());
            
            if (winResmi != null) g.drawImage(winResmi, (getWidth()/2)-300, (getHeight()/2)-225, 600, 450, null);
            
            else { g.setColor(Color.YELLOW); g.drawString("VICTORY!", 350, 500); }
        } 
        else if (oyunDurumu == 2) { 
            
            g.setColor(new Color(0, 0, 0, 200)); g.fillRect(0, 0, getWidth(), getHeight());
            
            if (gameOverResmi != null) g.drawImage(gameOverResmi, (getWidth()/2)-300, (getHeight()/2)-225, 600, 450, null);
            
            else { g.setColor(Color.RED); g.drawString("GAME OVER", 320, 500); }
   }
    }
    
    
    
    
    public void listeleriGuncelle(List<Dusman> d, List<Kule> k, List<Mermi> m, int para, int can, int dalga, int durum) {
        
        this.dusmanlar = d; this.kuleler = k; this.mermiler = m;
        this.guncelPara = para; this.guncelCan = can; this.guncelDalga = dalga;
        this.oyunDurumu = durum;
        
        
        repaint();
    }
    

    
    
    
    private Image kirmiziEfektliResimOlustur(Image orjinalResim, int w, int h) {

        BufferedImage boyaliResim = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = boyaliResim.createGraphics();

    
        g2d.drawImage(orjinalResim, 0, 0, w, h, null);

    
        g2d.setComposite(AlphaComposite.SrcAtop);

 
        g2d.setColor(new Color(255, 0, 0, 150));
        g2d.fillRect(0, 0, w, h);

 
        g2d.dispose();
        
        
        
        return boyaliResim;
    }
}