/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exa;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author edgva
 */
public class Main extends JFrame{
    private PSNUsers psnUsers;
    public Main (){
        try{
          psnUsers=new PSNUsers();
        }catch(IOException e){
          e.printStackTrace();
          JOptionPane.showMessageDialog(null,"Error al tratar de inicializar","Error",JOptionPane.ERROR_MESSAGE);
          System.exit(1);
        }
        JPanel j=new JPanel();
        j.setLayout(new GridLayout(5, 1));
        setLocationRelativeTo(null); 
        
        JButton agregar=new JButton("Add User");
        agregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Ingrese el nombre del usuario: ");
                if (username!= null && !username.isEmpty()) {
                    try {
                        psnUsers.addUser(username);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al agregar usuario", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de usuario valido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton desactivar=new JButton("Desactivar Cuenta");
        desactivar.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               String username= JOptionPane.showInputDialog("Ingrese el Nombre del Usuario: ");
               if (username!=null && username.isEmpty()){
                   try {
                       psnUsers.deactivateUser(username);
                   } catch (IOException ex){
                       ex.printStackTrace();
                     JOptionPane.showMessageDialog(null, "Error al desactivar el usuario", "Error", JOptionPane.ERROR_MESSAGE);  
                   }
               } else {
                  JOptionPane.showMessageDialog(null, "Porfavor Ingrese un nombre de Usuario Valido", "Error", JOptionPane.ERROR_MESSAGE);
               }
           }
        });
        JButton trofeo = new JButton ("Agregar Trofeo");
        trofeo.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
            AgregarTrofeo l=new AgregarTrofeo(psnUsers);
            l.setVisible(true);
           }               
        });
        
        JButton info= new JButton ("Player Info");
        info.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
        String username = JOptionPane.showInputDialog("Ingrese el Nombre del Usuario");
        if (username != null && !username.isEmpty()) { 
            try {
                String informacion = psnUsers.playerInfo(username);
                if (informacion != null && !informacion.isEmpty()) { 
                    JOptionPane.showMessageDialog(null, informacion, "Informacion del Jugador: ", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario no tiene información de trofeos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al tratar de obtener la informacion del Jugador", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe Ingresar un nombre Valido Porfavor ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        });
        
        JButton salir = new JButton ("Salir");
          salir.addActionListener(new ActionListener(){
              public void actionPerformed(ActionEvent e){
               System.exit(0);     
              }
          });
        
       j.add(agregar); 
       j.add(desactivar); 
       j.add(trofeo);
       j.add(info);
       j.add(salir);
       
        
       add(j);
       setSize(400,300);
       setVisible(true);
    }
    public static void main(String[] args) {
     SwingUtilities.invokeLater(new Runnable(){
         public void run(){
             new Main();
         }
     });
    }
    
}
