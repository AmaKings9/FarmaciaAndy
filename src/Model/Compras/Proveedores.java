/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Compras;

import Model.ConexionBD.ConexionBD;
import UI.BusquedaProveedor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Proveedores {
    
    Connection con;
    private String nom_prov;
    private String cont_prov;
    private String correo_prov;
    private String direc_prov;
    
    //constructor
    public Proveedores(){
        ConexionBD conex = new ConexionBD();
        
        try{
            con = conex.getConnection();
        }
        catch(Exception ex){
            Logger.getLogger(Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //Métodos
    public int busquedaResponsivaConteoRes(){
        int contador = 0;  //creamos un contador para saber el numero de datos que obtendremos de la tabla datos de sql
        try { //para las consultas sql siempre vamos a ocupar un try catch por su ocurre un error
            Statement st_cont = con.createStatement(); //el statement nos ayuda a procesar una sentencia sql 
            ResultSet rs_cont = st_cont.executeQuery("SELECT COUNT(*) FROM Proveedores"); // asignamos los datos obtenidos de la consulta al result set
            if (rs_cont.next()) {
                contador = rs_cont.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusquedaProveedor.class.getName()).log(Level.SEVERE, null, ex); //si llegara a ocurrir un error ya se  una mala consulta o mala conexion aqui nos lo mostraria
        }
        return contador;
    }
    
    public String[][] busquedaResponsivaInicial(){        
        int contador = busquedaResponsivaConteoRes();
        String[][] M_datos = new String[contador][4]; //definimos el tamaño de la matriz 
        try {
            Statement st = con.createStatement(); //ahora vamos a  hacer lo mismo solo que esta vez no obtendremos el numero de filas en la tabla
            ResultSet rs = st.executeQuery("SELECT * FROM Proveedores"); //aora obtendremos los datos de la tabla para mostrarlos en el jtable

            int cont = 0; //el contador nos ayudara para movernos en las filas de la matriz mientras que los numeros fijos (0,1,2,3) nos moveran por las 4 columnas que seran el id, nombre, etc
            while (rs.next()) { //el while nos ayudara a recorrer los datos obtenidos en la consulta anterior y asignarlos a la matriz  
                M_datos[cont][0] = rs.getString("nom_prov");    //agregamos los datos a la table
                M_datos[cont][1] = rs.getString("cont_prov");
                M_datos[cont][2] = rs.getString("correo_prov");
                M_datos[cont][3] = rs.getString("direc_prov");
                cont = cont + 1; //avanzamos una posicion del contador para que pase a la siguiente fila
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusquedaProveedor.class.getName()).log(Level.SEVERE, null, ex); //si llegara a ocurrir un error ya se  una mala consulta o mala conexion aqui nos lo mostraria
        }

        return M_datos;
    }
    
    public String[][] busquedaResponsiva(String nombre){
        int valor = 0;
        int cont = 0;
        try {
            Statement st_cont = con.createStatement(); //hacemos lo mismo que con el metodo mostrar, buscamos el numero de filas dela tabla
            ResultSet rs = st_cont.executeQuery("SELECT COUNT(*) FROM Proveedores WHERE nom_prov LIKE'" + nombre + "%'");//solo que esta ves usamos like
            if (rs.next()) {// like nos ayudara a buscar nombres que tengan similitudes con lo que estamos escribiendo en el texfield
                valor = rs.getInt(1); //una vez que obtenimos el numero de filas continuamos a sacar  el valor que buscamos
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusquedaProveedor.class.getName()).log(Level.SEVERE, null, ex); //si llegara a ocurrir un error ya se  una mala consulta o mala conexion aqui nos lo mostraria
        }
                
        String [][] M_datos = new String[valor][4];
        try {
            Statement st_cont = con.createStatement(); //hacemos lo mismo que con el metodo mostrar, buscamos el numero de filas dela tabla
            ResultSet rs = st_cont.executeQuery("SELECT * FROM Proveedores WHERE nom_prov LIKE'" + nombre + "%'"); //aqui es donde buscaremos a a la persona en especifico o las personas
            while (rs.next()) {
                M_datos[cont][0] = rs.getString("nom_prov");    //agregamos los datos a la table
                M_datos[cont][1] = rs.getString("cont_prov");
                M_datos[cont][2] = rs.getString("correo_prov");
                M_datos[cont][3] = rs.getString("direc_prov");
                cont = cont + 1;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BusquedaProveedor.class.getName()).log(Level.SEVERE, null, ex); //si llegara a ocurrir un error ya se  una mala consulta o mala conexion aqui nos lo mostraria
        }
        
        return M_datos;
    }
    
    //Getter & Setter
    public String getNom_prov() {
        return nom_prov;
    }

    public void setNom_prov(String nom_prov) {
        this.nom_prov = nom_prov;
    }

    public String getCont_prov() {
        return cont_prov;
    }

    public void setCont_prov(String cont_prov) {
        this.cont_prov = cont_prov;
    }

    public String getCorreo_prov() {
        return correo_prov;
    }

    public void setCorreo_prov(String correo_prov) {
        this.correo_prov = correo_prov;
    }

    public String getDirec_prov() {
        return direc_prov;
    }

    public void setDirec_prov(String direc_prov) {
        this.direc_prov = direc_prov;
    }
    
    
}
