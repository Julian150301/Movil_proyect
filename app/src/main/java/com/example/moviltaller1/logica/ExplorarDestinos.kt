package com.example.moviltaller1.logica

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviltaller1.R
import com.example.moviltaller1.modelo.Destino

class ExplorarDestinos : AppCompatActivity() {
    private lateinit var destinosLista: List<Destino>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorar_destinos)

        inicializar()

    }

    private fun inicializar(){
        val destinosView: ListView = findViewById(R.id.destinosList)

        //recibir listaDestinos de main activity
        destinosLista = intent.getSerializableExtra("destinos") as List<Destino>
        inicializarLista(destinosView)
    }

    private fun inicializarLista(destinosView: ListView){
        filtrarLista()
        val nombresDestino = destinosLista.map { destino -> destino.getNombre() }
        destinosView.adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            nombresDestino)

        destinosView.setOnItemClickListener { parent, view, position, id ->
            val destinoSeleccionado = destinosLista[position]
            val intent = Intent(this, Detalles::class.java)
            intent.putExtra("destino", destinoSeleccionado)
            startActivity(intent)
        }
    }

    private fun filtrarLista(){
        val filtro = intent.getStringExtra("filtro")
        if (filtro != "Todos"){
            destinosLista = destinosLista.filter { destino ->  destino.getCategoria()==filtro}
        }
    }
}