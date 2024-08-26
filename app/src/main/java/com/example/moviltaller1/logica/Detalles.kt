package com.example.moviltaller1.logica

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviltaller1.R
import com.example.moviltaller1.modelo.Destino
import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Detalles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)
        val destino = intent.getSerializableExtra("destino") as Destino
        inicializar(destino)
    }

    private fun inicializar(destino: Destino){
        val nombreTxt: TextView = findViewById(R.id.nombreTxt)
        val paisTxt: TextView = findViewById(R.id.paisTxt)
        val categoriaTxt: TextView = findViewById(R.id.categoriaTxt)
        val planTxt: TextView = findViewById(R.id.planTxt)
        val precioTxt: TextView = findViewById(R.id.precioTxt)
        val favBtn: Button = findViewById(R.id.agregarFavoritoBtn)
        val climaTxt: TextView = findViewById(R.id.climaTxt)

        inicializarBotonFavorito(favBtn, destino)

        CoroutineScope(Dispatchers.Main).launch {
            val clima = obtenerClima(destino.getLatitud(), destino.getLongitud())
            climaTxt.text = clima

            nombreTxt.text = destino.getNombre()
            paisTxt.text = destino.getPais()
            categoriaTxt.text = destino.getCategoria()
            planTxt.text = destino.getPlan()
            precioTxt.text = if (destino.getPrecio()!=-1) "USD " + destino.getPrecio().toString() else ""
        }
    }

    private fun inicializarBotonFavorito(favBtn: Button, destino: Destino){
        if (MainActivity.containsDestino(destino.getId())){
            deshabilitarBoton(favBtn)
        }else{
            favBtn.setOnClickListener {
                MainActivity.agregarDestino(destino)
                Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_LONG).show()
                deshabilitarBoton(favBtn)
            }
        }
    }

    private fun deshabilitarBoton(boton: Button){
        boton.isEnabled = false
        boton.isClickable = false
    }

    private suspend fun obtenerClima(latitud:Double, longitud:Double):String{
        val error = "Sin clima disponible"
        return withContext(Dispatchers.IO) {
            try {
                val url = "https://api.open-meteo.com/v1/forecast?latitude=${latitud}&longitude=${longitud}&current=temperature_2m"
                val respuesta = Fuel.get(url).responseString().third.get()
                val responseJson = JSONObject(respuesta)
                val currentWeather = responseJson.getJSONObject("current")
                currentWeather.getDouble("temperature_2m").toString()+ "°C"
            }catch (e:Exception){
                e.printStackTrace()
                error
            }
        }.toString()
    }
}