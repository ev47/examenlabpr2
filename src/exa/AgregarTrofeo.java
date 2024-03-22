/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exa;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author edgva
 */
public class AgregarTrofeo extends JFrame{
    private PSNUsers psnUsers;
public AgregarTrofeo(PSNUsers psnUsers){
    this.psnUsers= psnUsers;
    
    
    JPanel j=new JPanel();
        j.setLayout(new GridLayout(5, 1));
        JLabel JuegoTxt=new JLabel("Nombre del Juego");
        JTextField Juego = new JTextField(18);
        j.add(JuegoTxt);
        j.add(Juego);
        
        JLabel trofeoTxt = new JLabel("Nombre del Trofeo:");
        JTextField Trofeo = new JTextField(18);
        j.add(trofeoTxt);
        j.add(Trofeo);
        
        JLabel tipoTxT = new JLabel("Tipo de Trofeo:");
        String[] tipos = {"Platino", "Oro", "Plata", "Bronce"};
        JComboBox<String> TipoCBx = new JComboBox<>(tipos);
        j.add(tipoTxT);
        j.add(TipoCBx);
        
        JButton trofeo = new JButton ("Agregar Trofeo");
        trofeo.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
          String username = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");
        if (username != null && !username.isEmpty()) {
            String juego = Juego.getText();
            String trofeo = Trofeo.getText();
            String tipo = String.valueOf(TipoCBx.getSelectedItem());
            if (juego != null && trofeo != null && tipo != null) {
                switch (tipo.toLowerCase()) {
                    case "platino":
                        psnUsers.AddTrophie(username, juego, trofeo, Trophy.PLATINO);
                        break;
                    case "oro":
                        psnUsers.AddTrophie(username, juego, trofeo, Trophy.ORO);
                        break;
                    case "plata":
                        psnUsers.AddTrophie(username, juego, trofeo, Trophy.PLATA);
                        break;
                    case "bronce":
                        psnUsers.AddTrophie(username, juego, trofeo, Trophy.BRONCE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Tipo de trofeo no válido", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }
                JOptionPane.showMessageDialog(null, "Trofeo agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de usuario válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }                
        });
         j.add(trofeo);
         add(j);
         setSize(400,300);
       setVisible(true);
    
}
}
