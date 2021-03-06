/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yensen
 */
public class Servidor extends javax.swing.JFrame {

    /**
     * Creates new form Servidor
     */
    public Servidor() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Activar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jButton1)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jButton1)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jButton1.getAccessibleContext().setAccessibleName("Activar");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private String obtenerNombreUsuario(){
        String nombreuser = System.getProperty("user.name");
        //System.out.println("Nmbre del Usuario: " + nombreuser);
        return "Nombre de Usuario: "+nombreuser;
    }
   
    private String obtenerNombreMaquina(){
       
        InetAddress address=null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Nombre de la Maquina: " + address);
        return "Nombre de Maquina: " + address;
    }
    
    private String obtenerFechayHora(){
        return " Fecha y hora: "+(new Date()).toString();
    }
    
    private String obtenerUnidades(){
        StringBuilder cad = new StringBuilder();
        File[] roots = File.listRoots();
        for (int i=0; i<roots.length; i++) {
            cad.append(" ");
            cad.append(roots[i]);
            cad.append(" ");
        }
        String a="Unidades: "+cad.toString();
        return a;
    }
    
    private String obtenerMac() throws SocketException, UnknownHostException {
        //Direccion Mac
        NetworkInterface a = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        //Obtenemos su MAC Address, pero nos devuelve un array de bytes
        //Por lo que hay que convertirlos a Hexadecimal
        byte[] b = a.getHardwareAddress();
        String[] macAddres = new String[6];
        for (int i = 0; i < b.length; i++) {
            //Tratamos los valores que devuelven < 0 normalmente son el "3 y 5 par"
            if (b[i] < 0) {
                //Convertimos el byte a Hexadecimal con la clase Integer
                String tmp = Integer.toHexString(b[i]);
                //Los numeros que son menores a cero al momento de convertirlo a string nos devuelven una cadena de este tipo ffffffAA por lo que unicamente tomamos los ultimos 2 caracteres que son lo que buscamos. y obtenemos esos ultimos caracteres con substring
                macAddres [i]= (tmp.substring(tmp.length() - 2).toUpperCase());
                continue;
            }else{
                String aux = Integer.toHexString(b[i]);
                if(aux.length() < 2){
                    macAddres [i]= ("0"+Integer.toHexString(b[i]));
                }else{
                    macAddres [i]= (Integer.toHexString(b[i]));
                }
            }
        }
        StringBuilder cad = new StringBuilder();

        for(int x = 0;x < macAddres.length ;x++){

            if(x<macAddres.length-1){
                cad.append(macAddres [x] + "-");
            }else{
                cad.append(macAddres [x]);
            }
        }

        
        return "MAC: "+cad.toString();
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        iniciarServidor();
        
    }//GEN-LAST:event_jButton1ActionPerformed
    private void iniciarServidor(){
        try {
        int puerto = 5000;
        System.out.printf("Servidor iniciado en el puerto " + puerto + "... \n");
        System.out.println("iniciar servidor...\n");
        

        ServerSocket s = new ServerSocket(puerto);
        //String comandoSalir = "Exit";
        String entrada = "";
        
        while(true){
                Socket s1 = s.accept();        
                System.out.println("Aceptando conexion...\n");
                PrintWriter out = new PrintWriter(s1.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s1.getInputStream()));
                
                outerloop:
                while ((entrada = in.readLine()) != null) {					
                    System.out.println("entrada"+entrada);
                    
                    switch(entrada){
                        case "0":
                                out.println(obtenerNombreMaquina());
                                break;
                        case "1":
                                out.println(obtenerMac());
                                break;
                        case "2":
                                out.println(obtenerUnidades());
                                break;
                        case "3":
                                out.println(obtenerFechayHora());
                                break;
                        case "4":
                                out.println(obtenerNombreUsuario());
                                break;    
                        case "5":
                                break outerloop;
                                
                        default:
                                
                                break;        
                    }
                    
                    
                    
                } 
                s1.close();

        }

        } catch (IOException e) {
            e.printStackTrace();
        //System.exit(-1);
        }
    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
