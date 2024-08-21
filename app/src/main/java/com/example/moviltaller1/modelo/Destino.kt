package com.example.moviltaller1.modelo

import java.io.Serializable

class Destino(
    private var id: Int,
    private var nombre: String,
    private var pais: String,
    private var categoria: String,
    private var plan: String,
    private var precio: Int,
    private var latitud: Double,
    private var longitud: Double
) : Serializable{

    constructor(nombre:String): this(
        -1,
        nombre,
        "",
        "",
        "",
        -1,
        -1.0,
        -1.0
    )

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getNombre(): String {
        return nombre
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun getPais(): String {
        return pais
    }

    fun setPais(pais: String) {
        this.pais = pais
    }

    fun getCategoria(): String {
        return categoria
    }

    fun setCategoria(categoria: String) {
        this.categoria = categoria
    }

    fun getPlan(): String {
        return plan
    }

    fun setPlan(plan: String) {
        this.plan = plan
    }

    fun getPrecio(): Int {
        return precio
    }

    fun setPrecio(precio: Int) {
        this.precio = precio
    }
    fun getLatitud(): Double {
        return latitud
    }
    fun setLatitud(latitud: Double) {
        this.latitud = latitud
    }
    fun getLongitud(): Double {
        return longitud
    }
    fun setLongitud(longitud: Double) {
        this.longitud = longitud
    }
}