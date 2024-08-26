package com.example.moviltaller1.logica

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviltaller1.R
import com.example.moviltaller1.modelo.Destino

class Recomendaciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendaciones)

        val destino = getRecomendacion()
        inicializar(destino)

    }

    private fun inicializar(destino:Destino){
        val nombreTxt: TextView = findViewById(R.id.nombreTxt)
        val paisTxt: TextView = findViewById(R.id.paisTxt)
        val categoriaTxt: TextView = findViewById(R.id.categoriaTxt)
        val planTxt: TextView = findViewById(R.id.planTxt)
        val precioTxt: TextView = findViewById(R.id.precioTxt)

        nombreTxt.text = destino.getNombre()
        paisTxt.text = destino.getPais()
        categoriaTxt.text = destino.getCategoria()
        planTxt.text = destino.getPlan()
        precioTxt.text = if (destino.getPrecio()!=-1) "USD " + destino.getPrecio().toString() else ""
    }

    private fun getRecomendacion(): Destino {
        val favoritos = MainActivity.getFavoritos()
        if (favoritos.isEmpty()){
            return Destino("NA")
        }
        val categoria = getCategoriaMasComun(favoritos)
        return getDestinoRecomendado(categoria, favoritos)
    }

    private fun getCategoriaMasComun(favoritos:List<Destino>):String{
        val categoriasNum = favoritos.groupingBy { destino -> destino.getCategoria() }.eachCount()
        return categoriasNum.maxBy {categoria -> categoria.value}.key
    }

    private fun getDestinoRecomendado(categoria:String, favoritos:List<Destino>):Destino{
        val destinosByCategoria = favoritos.filter { destino -> destino.getCategoria() == categoria }
        return destinosByCategoria.random()
    }
}