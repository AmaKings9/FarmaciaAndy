/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Ventas;

import Model.ConexionBD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Venta {
    
    Connection con;
    int folio_venta;
    String fecha_venta;
    float total_venta;
    String hora_venta;
    String cedula_venta;
    
    public Venta(){
        ConexionBD conex = new ConexionBD();
        
        try{
            con = conex.getConnection();
        }
        catch(Exception ex){
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Métodos
    public boolean altaVenta(int num_empl, String[][] art, int leng){
        PreparedStatement ps;
        Statement s_cod;
        ResultSet rs;
        try{
            
            //Alta en tabla Venta
            ps = con.prepareStatement("insert into Venta (fecha_venta, total_venta, hora_venta, cedula_venta, num_empl) values (curdate(), ?, curtime(), ?, ?)");
            ps.setString(1, String.valueOf(getTotal_venta()));
            ps.setString(2, getCedula_venta());
            ps.setInt(3, num_empl);

            ps.execute();
            
            s_cod = con.createStatement();
            rs = s_cod.executeQuery("select folio_venta, fecha_venta, hora_venta from Venta order by folio_venta desc limit 1;");
            
            if(rs.next()){
                
                setFolio_venta(rs.getInt("folio_venta"));
                setFecha_venta(String.valueOf(rs.getDate("fecha_venta")));
                setHora_venta(String.valueOf(rs.getTime("hora_venta")));
                System.out.println(getFolio_venta());
                               
            }else{    
                return false;
            }
            
            //Alta en tabla débil Registra
            boolean flagAlta = altaVenta_porArt(art, leng);
            if(flagAlta == true){
                return true;
            } else{
                return false;
            }
        }
        catch (SQLException e){
            System.out.println(e.toString());
            
            return false;
        }
    }
    
    private boolean altaVenta_porArt(String[][] art, int leng){
        PreparedStatement ps;
        try{
            
            for(int i = 0; i<leng; i++){
                ps = con.prepareStatement("insert into Registra (folio_venta, id_almacen, cantidad_va, subtotal_va) values (?, ?, ?, ?)");
                ps.setString(1, String.valueOf(getFolio_venta()));
                ps.setString(2, art[i][0]);
                ps.setString(3, art[i][1]);
                ps.setString(4, art[i][2]);

                ps.execute();
            }
            
            return true;
        }
        catch (SQLException e){
            System.out.println(e.toString());
            
            return false;
        }
    }
    
    //Getter & Setter
    public int getFolio_venta() {
        return folio_venta;
    }

    public void setFolio_venta(int folio_venta) {
        this.folio_venta = folio_venta;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public float getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(float total_venta) {
        this.total_venta = total_venta;
    }

    public String getHora_venta() {
        return hora_venta;
    }

    public void setHora_venta(String hora_venta) {
        this.hora_venta = hora_venta;
    }

    public String getCedula_venta() {
        return cedula_venta;
    }

    public void setCedula_venta(String cedula_venta) {
        this.cedula_venta = cedula_venta;
    }
    
    
    
}
