/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;






public class MainFrame extends JFrame {
    
    
    private GamePanel oyunPaneli;
    private KontrolPaneli kontrolPaneli;
    private JPanel menuPaneli; 

    
    
    
    public MainFrame() {
        
        this.setTitle("Tower Defense");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setLayout(new BorderLayout());
        
   
        menuPaneli = new JPanel(new GridBagLayout()) {
            
            
            
            @Override
            protected void paintComponent(Graphics g) {
                
                super.paintComponent(g);
                
               
                try {
                    Image bg = new ImageIcon(getClass().getResource("/Resources/Map.jpg")).getImage();
                    
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                    
                } catch (Exception e) {
                  
                    g.setColor(Color.DARK_GRAY);
                    
                    g.fillRect(0, 0, getWidth(), getHeight());
                }

              
                g.setColor(new Color(0, 0, 0, 150)); 
                
                g.fillRect(0, 0, getWidth(), getHeight());
                
              
                g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 80));
                
                String baslik = "TOWER DEFENSE";
                
                int len = g.getFontMetrics().stringWidth(baslik);
                
                
              
                g.setColor(Color.BLACK); 
                
                g.drawString(baslik, (getWidth()/2) - (len/2) + 5, 205);
                
            
                g.setColor(new Color(255, 215, 0)); 
                
                g.drawString(baslik, (getWidth()/2) - (len/2), 200);
                
           
                g.setFont(new Font("SansSerif", Font.PLAIN, 15));
                
                g.setColor(Color.LIGHT_GRAY);
                
                String alt = "Kuzey İstilası";
                
                int altLen = g.getFontMetrics().stringWidth(alt);
                
                g.drawString(alt, (getWidth()/2) - (altLen/2), getHeight() - 50);
          }
        };
        
       
        menuPaneli.setPreferredSize(new Dimension(1224, 1024));
        
       
        JButton btnBasla = tasarlananButon("OYUNU BAŞLAT", new Color(34, 139, 34)); 
        
        
        JButton btnCikis = tasarlananButon("ÇIKIŞ", new Color(178, 34, 34)); 
        
     
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(20, 0, 20, 0); 
        
        gbc.gridx = 0; 
        
        gbc.gridy = 0;
        
        menuPaneli.add(btnBasla, gbc);
        
        gbc.gridy = 1;
        
        menuPaneli.add(btnCikis, gbc);
        
        
        this.add(menuPaneli, BorderLayout.CENTER);
        
        
        
        btnBasla.addActionListener(new ActionListener() {
            
            
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                baslatOyunu();
            }
        });
        
        btnCikis.addActionListener(new ActionListener() {
            
            
            
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                
                System.exit(0);
            }
        });
        
       
        this.setResizable(false);
        this.pack(); 
        this.setLocationRelativeTo(null); 
        this.setVisible(true);
    }
  
    private JButton tasarlananButon(String yazi, Color renk) {
        
        
        JButton btn = new JButton(yazi);
        
        btn.setFont(new Font("DialogInput", Font.BOLD, 30));
        
        btn.setBackground(new Color(50, 50, 50));
        
        btn.setForeground(renk);
        
        btn.setFocusPainted(false); 
        
        btn.setBorderPainted(true);
        
        btn.setPreferredSize(new Dimension(300, 70)); 
        
        
        
        return btn;
        
    }
    
    
    
    
    
    private void baslatOyunu() {
       
        this.remove(menuPaneli);
        
       
        oyunPaneli = new GamePanel();
        
        oyunPaneli.setPreferredSize(new Dimension(1024, 1024)); 
        
        this.add(oyunPaneli, BorderLayout.CENTER);
        
       
        kontrolPaneli = new KontrolPaneli();
        
        this.add(kontrolPaneli, BorderLayout.EAST);
        
        
        
        
        this.revalidate();
        this.repaint();
        this.pack(); 
        
       
        new Model.GameEngine(oyunPaneli, kontrolPaneli); 
        
        
        
    }
    
    
    
    
    public static void main(String[] args) {
        new MainFrame();
    }
}