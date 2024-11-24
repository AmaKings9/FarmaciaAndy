/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Articulos;

import Model.Compras.Proveedores;
import Model.ConexionBD.ConexionBD;
import UI.RegisVenta;
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
public class Medicamentos extends Articulos{
    
    //atributos
    Connection con;
    Proveedores prov;
    Almacen almacen;
    
    private String tipo_med;
    private String presen_med;
    private String gram_med;
    private boolean receta_med;
    
    //constructor
    public Medicamentos(Proveedores prov){
        ConexionBD conex = new ConexionBD();
        
        try{
            con = conex.getConnection();
        }
        catch(Exception ex){
            Logger.getLogger(Articulos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.prov = prov;
    }

    //Métodos abstractos
    @Override
    public boolean altaArt() {
        PreparedStatement ps;
        Statement s_cod;
        ResultSet rs;
        try{
            
            ps = con.prepareStatement("insert into Medicamentos (nom_med, tipo_med, presen_med, gram_med, receta_med, precio_med, nom_prov, id_almacen) values (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, getNom_art());
            ps.setString(2, getTipo_med());
            ps.setString(3, getPresen_med());
            ps.setString(4, getGram_med());
            ps.setBoolean(5, getReceta_med());
            ps.setString(6, String.valueOf(getPrecio_art()));
            ps.setString(7, prov.getNom_prov());
            ps.setString(8, String.valueOf(almacen.getId_almacen()));

            ps.execute();
            
            s_cod = con.createStatement();
            rs = s_cod.executeQuery("select cod_med from Medicamentos order by cod_med desc limit 1;");
            
            if(rs.next()){
                
                setCod_art(rs.getInt("cod_med"));
                System.out.println(getCod_art());
                
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

    @Override
    public boolean modificarArt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[][] consultarArt() {
        Statement ps, st_cont;
        ResultSet rs, rs_cont;
        int valor = 0, cont = 1;
        
        try {
            st_cont = con.createStatement(); //hacemos lo mismo que con el metodo mostrar, buscamos el numero de filas dela tabla
            rs_cont = st_cont.executeQuery("SELECT COUNT(*) FROM Medicamentos");//solo que esta ves usamos like
            if (rs_cont.next()) {// like nos ayudara a buscar nombres que tengan similitudes con lo que estamos escribiendo en el texfield
                valor = rs_cont.getInt(1); //una vez que obtenimos el numero de filas continuamos a sacar  el valor que buscamos
            }
        
            String[][] lista = new String[valor+1][12];
            lista[0][0] = String.valueOf(valor);
            
            ps = con.createStatement();
            rs = ps.executeQuery("select * from Medicamentos;");
            
            while(rs.next()){
                
                lista[cont][0] = String.valueOf(rs.getInt("cod_med"));
                lista[cont][1] = rs.getString("nom_med");
                lista[cont][2] = rs.getString("tipo_med");
                lista[cont][3] = rs.getString("presen_med");
                lista[cont][4] = rs.getString("gram_med");
                lista[cont][5] = String.valueOf(rs.getBoolean("receta_med"));
                lista[cont][6] = String.valueOf(rs.getFloat("precio_med"));
                lista[cont][7] = rs.getString("nom_prov");
                lista[cont][8] = String.valueOf(rs.getInt("id_almacen"));
                System.out.println(lista[cont][8]);
                
                String[][] list_alm = consultarAlmacen(rs.getInt("id_almacen"));
                
                lista[cont][9] = list_alm[0][0];
                lista[cont][10] = list_alm[0][1];
                lista[cont][11] = list_alm[0][2];
                
                /*System.out.println(list_alm[cont][2]);
                System.out.println(list_alm[cont][1]);
                System.out.println(list_alm[cont][0]);*/
                
                cont = cont + 1; 
            }
            
            return lista;
        } catch (SQLException e){
            System.out.println(e.toString());
            
            return null;
        }
    }

    @Override
    public boolean altaAlmacen(String fecha, String cad, int exist) {
        PreparedStatement ps;
        Statement s_id;
        ResultSet rs;
        almacen = new Almacen(fecha, cad, exist);        
        try{
            
            ps = con.prepareStatement("insert into Almacen (fecha_modif_almacen, cad_almacen, exist_almacen) values (curdate(), ?, ?)");
            ps.setString(1, almacen.getCad_almacen());
            ps.setString(2, String.valueOf(almacen.getExist_almacen()));

            ps.execute();
            
            s_id = con.createStatement();
            rs = s_id.executeQuery("select id_almacen from Almacen order by id_almacen desc limit 1;");
            
            if(rs.next()){
                
                almacen.setId_almacen(rs.getInt("id_almacen"));
                System.out.println(almacen.getId_almacen());
                
                return true;
                                
            }else{
                return false;
            }
        } catch (SQLException e){
            System.out.println(e.toString());
            
            return false;
        }
    }

    @Override
    public boolean modificarAlmacen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[][] consultarAlmacen(int id) {
        PreparedStatement ps_cont, ps_alm;
        ResultSet rs_cont, rs_alm;
        int valor = 0, cont = 0;
        
        try {
            ps_cont = con.prepareStatement("SELECT COUNT(*) FROM Almacen WHERE id_almacen=?");
            ps_cont.setInt(1, id);
            rs_cont = ps_cont.executeQuery();
            if (rs_cont.next()) {
                valor = rs_cont.getInt(1);
            }
        
            String[][] lista = new String[valor+1][3];
            
            ps_alm = con.prepareStatement("SELECT fecha_modif_almacen, cad_almacen, exist_almacen FROM Almacen WHERE id_almacen=?");
            ps_alm.setInt(1, id);
            rs_alm = ps_alm.executeQuery();
            
            
            while(rs_alm.next()){
                
                lista[cont][0] = String.valueOf(rs_alm.getDate("fecha_modif_almacen"));
                lista[cont][1] = String.valueOf(rs_alm.getDate("cad_almacen"));
                lista[cont][2] = String.valueOf(rs_alm.getInt("exist_almacen"));
                
                cont = cont + 1; 
            }
            
            return lista;
        } catch (SQLException e){
            System.out.println(e.toString());
            
            return null;
        }
    }
     
    @Override
    public String[][] busquedaResponsivaArt(String nombre){
        int valor = 0;
        int cont = 1;
        try {
            Statement st_cont = con.createStatement(); //hacemos lo mismo que con el metodo mostrar, buscamos el numero de filas dela tabla
            ResultSet rs = st_cont.executeQuery("SELECT COUNT(*) FROM Medicamentos WHERE nom_med LIKE'" + nombre + "%'");//solo que esta ves usamos like
            if (rs.next()) {// like nos ayudara a buscar nombres que tengan similitudes con lo que estamos escribiendo en el texfield
                valor = rs.getInt(1); //una vez que obtenimos el numero de filas continuamos a sacar  el valor que buscamos
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisVenta.class.getName()).log(Level.SEVERE, null, ex); //si llegara a ocurrir un error ya se  una mala consulta o mala conexion aqui nos lo mostraria
        }
                
        String [][] M_datos = new String[valor+1][6];
        M_datos[0][0] = String.valueOf(valor);
        
        try {
            Statement st_cont = con.createStatement(); //hacemos lo mismo que con el metodo mostrar, buscamos el numero de filas dela tabla
            ResultSet rs = st_cont.executeQuery("SELECT id_almacen, nom_med, precio_med, nom_prov, receta_med FROM Medicamentos WHERE nom_med LIKE'" + nombre + "%'"); //aqui es donde buscaremos a a la persona en especifico o las personas
            while (rs.next()) {
                M_datos[cont][0] = rs.getString("id_almacen");
                M_datos[cont][1] = rs.getString("nom_med");    //agregamos los datos a la table
                M_datos[cont][2] = rs.getString("precio_med");
                M_datos[cont][3] = rs.getString("nom_prov");
                M_datos[cont][4] = "Medicamento";
                if(String.valueOf(rs.getBoolean("receta_med")).equals("false")){
                    M_datos[cont][5] = "NO";
                } else{
                    M_datos[cont][5] = "SI";
                    System.out.println(String.valueOf(rs.getBoolean("receta_med")));
                }
                cont = cont + 1;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RegisVenta.class.getName()).log(Level.SEVERE, null, ex); //si llegara a ocurrir un error ya se  una mala consulta o mala conexion aqui nos lo mostraria
        }
        
        return M_datos;
    }
    
    //Métodos
    public String[][] busquedaResponsivaInicial(){ 
        String[][] allContentTabla = consultarArt();
        int contador = Integer.parseInt(allContentTabla[0][0]);
        String[][] M_datos = new String[contador+1][6]; //definimos el tamaño de la matriz 
        M_datos[0][0] = allContentTabla[0][0];
        
        for(int i=1; i <=contador; i++){
            M_datos[i][0] = allContentTabla[i][8];
            M_datos[i][1] = allContentTabla[i][1];
            M_datos[i][2] = allContentTabla[i][6];
            M_datos[i][3] = allContentTabla[i][7];
            M_datos[i][4] = "Medicamento";
            if(allContentTabla[i][5].equals("false")){
                M_datos[i][5] = "NO";
            } else{
                M_datos[i][5] = "SI";
                System.out.println(allContentTabla[i][5]);
            }
        }

        return M_datos;
    }
    
    //Getter & Setter

    public String getTipo_med() {
        return tipo_med;
    }

    public void setTipo_med(String tipo_med) {
        this.tipo_med = tipo_med;
    }

    public String getPresen_med() {
        return presen_med;
    }

    public void setPresen_med(String presen_med) {
        this.presen_med = presen_med;
    }

    public String getGram_med() {
        return gram_med;
    }

    public void setGram_med(String gram_med) {
        this.gram_med = gram_med;
    }

    public boolean getReceta_med() {
        return receta_med;
    }

    public void setReceta_med(boolean receta_med) {
        this.receta_med = receta_med;
    }
    
}
