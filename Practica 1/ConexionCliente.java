/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author yensen
 */
public class ConexionCliente {
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in ;
    

    public ConexionCliente() {
    }
    
    public void crearConexcion(){
        String servidor = "127.0.0.1";
        int puerto = 5000;
        try{
          socket= new Socket (servidor,puerto);
          in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
          out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
          
          
        } catch (IOException e){
                  System.out.println("Error en conexión!!!");
        }
    }
    public void peticion(String peticion) {
        
        out.println(peticion);
        
    }
    public String respuesta(){
        StringBuilder cadena = new StringBuilder();
        String respuesta = "";
        try{
            while  ((respuesta = in.readLine()) != null){
                cadena.append(respuesta);
                break;
            }
            socket.close();
            
        } catch (IOException e){
            System.out.println("Error en conexión!!!");
        }
        return cadena.toString();
               
        
    }
    
}
