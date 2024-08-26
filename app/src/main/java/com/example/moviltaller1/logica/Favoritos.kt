package com.example.moviltaller1.logica

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviltaller1.R

class Favoritos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        inicializar()
    }

    private fun inicializar(){
        val favoritosLista: ListView = findViewById(R.id.favoritosList)

        inicializarLista(favoritosLista)
    }

    private fun inicializarLista(favoritosLista: ListView){
        val nombresDestino = MainActivity.getFavoritos().map { destino -> destino.getNombre() }
        favoritosLista.adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            nombresDestino)
    }
}