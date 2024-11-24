/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Articulos;

import Model.ConexionBD.ConexionBD;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 * 
 * insert into Almacen (nom_producto, precio_producto, cat_producto, nom_prov, id_almacen) values (?, ?, ?, ?, ?)
 */
public abstract class Articulos {
    
    //atributos
    private int cod_art;
    private String nom_art;
    private float precio_art;

    //métodos
    public int getCod_art() {
        return cod_art;
    }

    public void setCod_art(int cod_art) {
        this.cod_art = cod_art;
    }

    public String getNom_art() {
        return nom_art;
    }

    public void setNom_art(String nom_art) {
        this.nom_art = nom_art;
    }

    public float getPrecio_art() {
        return precio_art;
    }

    public void setPrecio_art(float precio_art) {
        this.precio_art = precio_art;
    }
    
    //métodos abstractos
    public abstract boolean altaArt();
    public abstract boolean modificarArt();
    public abstract String[][] consultarArt();
    public abstract String[][] busquedaResponsivaArt(String nombre);
    //metodos almacén
    public abstract boolean altaAlmacen(String fecha, String cad, int exist);
    public abstract boolean modificarAlmacen();
    public abstract String[][] consultarAlmacen(int id);
    //public abstract String eliminarAlmacen();
}
