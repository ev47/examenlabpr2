/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exa;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author edgva
 */
public class PSNUsers {
    RandomAccessFile usuarios;
    HashTable users;
   public PSNUsers() throws IOException{
        usuarios = new RandomAccessFile("usuarios.data","rw");
        reloadHashTable();
    }
    private void reloadHashTable() throws IOException{
        users = new HashTable();
        if(usuarios.length()!=0){
            usuarios.seek(0);
            while(usuarios.getFilePointer()<usuarios.length()){
                long posicion = usuarios.getFilePointer();
                String username = usuarios.readUTF();
                usuarios.skipBytes(8);
                if(usuarios.readBoolean()==true)
                    users.add(posicion, username);
            }
        }
    }
    public void addUser(String username)throws IOException{
       long posicion=0;
       if (usuarios.length()!=0){
          usuarios.seek(usuarios.length());
          posicion=usuarios.getFilePointer();
       }
       if (users.search(username)==-1){
           usuarios.writeUTF(username);
           usuarios.writeInt(0);
           usuarios.writeInt(0);
           usuarios.writeBoolean(true);
           users.add(posicion, username);
           JOptionPane.showMessageDialog(null, "Se ha creado el usuario exitosamente", "Usuario creado", JOptionPane.INFORMATION_MESSAGE);
       } else {
           JOptionPane.showMessageDialog(null,"El Usuario ya esta creado","Error",JOptionPane.ERROR_MESSAGE);
       }
          
    }
    public void deactivateUser(String username)throws IOException{
      if (users.search(username) != -1) {
        long userPosition = usuarios.getFilePointer() - 4 * 2 - 1; 
        usuarios.readUTF(); 
        usuarios.skipBytes(8); 
        usuarios.writeBoolean(false); 
        users.remove(username);
        usuarios.seek(userPosition);
        usuarios.writeUTF(username);
        usuarios.writeInt(0); 
        usuarios.writeInt(0);
        usuarios.writeBoolean(false);
    } else {
        System.out.println("[desactivateUser] Usuario no encontrado.");
    }
    }
    
    public boolean AddTrophie(String Username,String trophyGame, String trophyName, Trophy type){
        if (users.search(Username)== -1){
           return false; 
        }     
        try {
        usuarios.seek(0);
        while (usuarios.getFilePointer() < usuarios.length()) {
            long currentPosition = usuarios.getFilePointer();
            String currentUser = usuarios.readUTF();
            if (!currentUser.equals(Username)) {
                usuarios.skipBytes(4 * 2 + 1);
                continue;
            }
            int trophyPoints = usuarios.readInt();
            int trophyCount = usuarios.readInt();
            boolean registered = usuarios.readBoolean();

            if (!registered) {
                return false;
            }
            trophyPoints += type.points;
            trophyCount++;
            usuarios.seek(currentPosition);
            usuarios.writeUTF(currentUser);
            usuarios.writeInt(trophyPoints);
            usuarios.writeInt(trophyCount);
            usuarios.writeBoolean(true);
            RandomAccessFile trophies = new RandomAccessFile("trofeos.data", "rw");
            trophies.seek(trophies.length());
            trophies.writeUTF(Username);
            trophies.writeUTF(type.name());
            trophies.writeUTF(trophyGame);
            trophies.writeUTF(trophyName);
            trophies.writeLong(System.currentTimeMillis());
            trophies.close();
            return true;
        }
        return false;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
    }
    public String playerInfo(String username) throws IOException{
 StringBuilder userInfoBuilder = new StringBuilder();

    if (users.search(username) == -1) {
        userInfoBuilder.append("Usuario no encontrado.");
        return userInfoBuilder.toString();
    }

    try (RandomAccessFile trofeos = new RandomAccessFile("psn.data", "rw")) {
        usuarios.seek(0);
        while (usuarios.getFilePointer() < usuarios.length()) {
            String currentUsername = usuarios.readUTF();
            int trophyPoints = usuarios.readInt();
            int trophyCount = usuarios.readInt();
            boolean registered = usuarios.readBoolean();
            if (currentUsername.equals(username) && registered) {
                userInfoBuilder.append("USERS INFO: ").append(username).append("\n----------------\n");
                userInfoBuilder.append("\nTrofeos obtenidos: ").append(trophyCount).append("\n");
                userInfoBuilder.append("Puntos: ").append(trophyPoints).append("\n");
                userInfoBuilder.append("Información de trofeos:\n");
                trofeos.seek(0);
                while (trofeos.getFilePointer() < trofeos.length()) {
                    String currentUser = trofeos.readUTF();
                    String trophyType = trofeos.readUTF();
                    String trophyGame = trofeos.readUTF();
                    String trophyName = trofeos.readUTF();
                    long date = trofeos.readLong();
                    if (currentUser.equals(username)) {
                        userInfoBuilder.append("\n\tTipo de trofeo: ").append(trophyType);
                        userInfoBuilder.append("\nNombre del trofeo: ").append(trophyName);
                        userInfoBuilder.append("\nNombre del juego: ").append(trophyGame);
                        userInfoBuilder.append("\nFecha: ").append(new Date(date)).append("\n");
                    }
                }
                userInfoBuilder.append("*************************************************************\n");
            }
        }
    } catch (Exception e) {
        userInfoBuilder.append("Error al obtener la información del usuario: ").append(e.getMessage());
    }
    return userInfoBuilder.toString();   
}
}
