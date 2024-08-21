package com.example.moviltaller1.logica

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.moviltaller1.R
import com.example.moviltaller1.datos.DestinosWrapper
import com.example.moviltaller1.modelo.Destino
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var filtroSeleccionado:String = "Todos"
    private lateinit var filtrosArray: Array<String>
    private lateinit var destinosLista: List<Destino>

    companion object{
        private val listaFavoritos: MutableList<Destino> = mutableListOf()

        fun agregarDestino(destino: Destino){
            listaFavoritos.add(destino)
        }

        fun getFavoritos():List<Destino>{
            return listaFavoritos
        }

        fun containsDestino(id:Int):Boolean{
            return listaFavoritos.any { it.getId()==id }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cargarDestinos()
        inicializar()


    }

    private fun cargarDestinos(){
        val jsonStr = loadJSONFromAsset()
        val destinosWrapper = Gson().fromJson(jsonStr, DestinosWrapper::class.java)
        destinosLista = destinosWrapper.destinos

        val filtrosLista = ArrayList<String>()
        filtrosLista.add("Todos")
        destinosLista.forEach { destino ->
            if (!filtrosLista.contains(destino.getCategoria())){
                filtrosLista.add(destino.getCategoria())
            }
        }
        filtrosArray = filtrosLista.toTypedArray()

    }

    private fun inicializar(){
        val destinosBtn: Button = findViewById(R.id.explorarBtn)
        val favoritosBtn: Button = findViewById(R.id.favoritosBtn)
        val recomendacionesBtn: Button = findViewById(R.id.recomendacionesBtn)

        val filtrosSpinner: Spinner = findViewById(R.id.filtros)
        inicializarFiltros(filtrosSpinner)

        destinosBtn.setOnClickListener {
            val intent = Intent(this, ExplorarDestinos::class.java)
            //pasar destinosLista a pantalla de explorar
            intent.putExtra("destinos", ArrayList(destinosLista))
            intent.putExtra("filtro", filtroSeleccionado)
            startActivity(intent)
        }
        favoritosBtn.setOnClickListener { startActivity(Intent(this, Favoritos::class.java))}
        recomendacionesBtn.setOnClickListener { startActivity(Intent(this, Recomendaciones::class.java))}
    }

    private fun inicializarFiltros(filtrosSpinner: Spinner){
        filtrosSpinner.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            filtrosArray).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        filtrosSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        filtroSeleccionado = filtrosArray[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val isStream: InputStream = assets.open("destinos.json")
            val size:Int = isStream.available()
            val buffer = ByteArray(size)
            isStream.read(buffer)
            isStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}