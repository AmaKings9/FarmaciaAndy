/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Empleados;

import Model.ConexionBD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Empleados {
    
    //atributos
    Connection con;
    private int num_empl;
    private String nom_empl;
    private String app_empl;
    private String correo_empl;
    private String contr_empl;
    private String cat_empl;
    
    //constructor
    public Empleados(){
        ConexionBD conex = new ConexionBD();
        
        try{
            con = conex.getConnection();
        }
        catch(Exception ex){
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //Métodos
    public boolean inicioSesion(String correo, String contraseña){
        PreparedStatement ps;
        ResultSet rs;
        
        try{
            
            ps = con.prepareStatement("select * from Empleados where correo_empl=? and contr_empl=?;");
            ps.setString(1, correo);
            ps.setString(2, contraseña);
            System.out.println("Datos a buscar: " + correo +", "+ contraseña);
            rs = ps.executeQuery();
            
            if(rs.next()){
                
                setNum_empl(rs.getInt("num_empl"));
                setNom_empl(rs.getString("nom_empl"));
                setApp_empl(rs.getString("app_empl"));
                setCorreo_empl(rs.getString("correo_empl"));
                setContr_empl(rs.getString("contr_empl"));
                setCat_empl(rs.getString("cat_empl"));
                
                return true;
                                
            }else{
                return false;
            }
            
        }
        catch (SQLException e){
            
            System.out.println(e.toString());
            
            return false;
            
        }
    }
    
    //Get & Set
    public int getNum_empl() {
        return num_empl;
    }

    public void setNum_empl(int num_empl) {
        this.num_empl = num_empl;
    }

    public String getNom_empl() {
        return nom_empl;
    }

    public void setNom_empl(String nom_empl) {
        this.nom_empl = nom_empl;
    }

    public String getApp_empl() {
        return app_empl;
    }

    public void setApp_empl(String app_empl) {
        this.app_empl = app_empl;
    }

    public String getCorreo_empl() {
        return correo_empl;
    }

    public void setCorreo_empl(String correo_empl) {
        this.correo_empl = correo_empl;
    }

    public String getContr_empl() {
        return contr_empl;
    }

    public void setContr_empl(String contr_empl) {
        this.contr_empl = contr_empl;
    }

    public String getCat_empl() {
        return cat_empl;
    }

    public void setCat_empl(String cat_empl) {
        this.cat_empl = cat_empl;
    }
    
    
}
