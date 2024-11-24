/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Articulos;

/**
 *
 * @author Usuario
 */
public class Almacen {
    
    //atributos
    private int id_almacen;
    private String fecha_modif_alm;
    private String cad_almacen;
    private int exist_almacen;
    
    //constructor
    public Almacen(String fecha, String cad, int exist){
        this.fecha_modif_alm = fecha;
        this.cad_almacen = cad;
        this.exist_almacen = exist;
    }
    
    //métodos getter & setter
    protected int getId_almacen() {
        return id_almacen;
    }

    protected void setId_almacen(int id_almacen) {
        this.id_almacen = id_almacen;
    }

    protected String getFecha_modif_alm() {
        return fecha_modif_alm;
    }

    protected void setFecha_modif_alm(String fecha_modif_alm) {
        this.fecha_modif_alm = fecha_modif_alm;
    }

    protected String getCad_almacen() {
        return cad_almacen;
    }

    protected void setCad_almacen(String cad_almacen) {
        this.cad_almacen = cad_almacen;
    }

    protected int getExist_almacen() {
        return exist_almacen;
    }

    protected void setExist_almacen(int exist_almacen) {
        this.exist_almacen = exist_almacen;
    }
    
}
