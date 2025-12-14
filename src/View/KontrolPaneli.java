/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;





//Bu kod ekranin sag tarafdaki menusudur
public class KontrolPaneli extends JPanel {
    
    public JButton btnArbalet;
    public JButton btnMancinik;
    public JButton btnBuyucu;
    
    public KontrolPaneli() {
        
        this.setPreferredSize(new Dimension(200, 1024)); 
        
        this.setBackground(Color.DARK_GRAY);
        
        this.setLayout(new GridLayout(10, 1, 10, 10)); 
        
        this.setBorder(new EmptyBorder(10, 10, 10, 10)); 
        
        
        
        btnArbalet = new JButton("Okçu (50 Gold)");
        
        btnMancinik = new JButton("Topçu (75 Gold)");
        
        btnBuyucu = new JButton("Buz Kulesi (70 Gold)");
        
        
        
        JLabel lblBaslik = new JLabel("KULE İNŞA ET ");
        
        lblBaslik.setForeground(Color.WHITE);
        
        lblBaslik.setHorizontalAlignment(JLabel.CENTER);
        
        
        
        
        
        this.add(lblBaslik);
        this.add(btnArbalet);
        this.add(btnMancinik);
        this.add(btnBuyucu);
    }
    
}