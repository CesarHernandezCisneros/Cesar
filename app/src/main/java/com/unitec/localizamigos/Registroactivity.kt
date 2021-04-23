package com.unitec.localizamigos

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LruCache
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.unitec.localizamigos.modelo.Usuario
import kotlinx.android.synthetic.main.activity_registroactivity.*
import org.json.JSONObject

class Registroactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registroactivity)

        //Manejamos el evento del boton para pedir los valores de las componentes de
        //nuestro fomulario

        //Punto 2:
        registrar.setOnClickListener{
            var usuario=Usuario()
            //Este usuario lo llenamos con los valores de los campos de texto del formulario.


            usuario.email = txtEmail.text.toString()
            usuario.nickname = txtNickname.text.toString()
            usuario.nombre = txtNombre.text.toString()

            //El siguiente paso es este objeto que acabamos de crear(Usuario) lo tenemos que enviar
            //a un servidor externo para que pueda ser guardado y registrado, asi como cualquier red social
            //Para este paso necesitamos enviar esta informacion a un servidor y el mecanismo de envio es una
            //arquitectura muy particular que se denomina arquitectura estilo REST
            //En android existe una tecnologia muy particular que nos va ayudar a poder enviar nuestro objeto
            //de registro al back-end. Esta tecnologia se conoce como RETROFIT

            //Punto 3: Generar el objeto Json de los datos del punto 2
            var usuariojson = JSONObject()
            usuariojson.put("email", usuario.email)
            usuariojson.put("nickname", usuario.nickname)
            usuariojson.put("nombre", usuario.nombre)


            //punto 4: Generar el objeto de tipo request donde se envia al back-end nuestro usuario
            var url = "https://benesuela.herokuapp.com/api/usuario"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, usuariojson,
                    Response.Listener { response ->
                        //Ponemos la notificacion del back-end en un objeto de tipo toast

                       //AQUI VAMOS A GUARDAR EL OBJETO EN SAHREDPREFERENCES
                        val preferencias = applicationContext?.getSharedPreferences("AMIGOS", Context.MODE_PRIVATE)?:return@Listener
                        //Con notacion funcional con lambdas mas moderno y mas seguro al nullpointer exception
                        with(preferencias.edit()){
                            putString("nombre", usuario.nombre).commit()
                            putString("email", usuario.email).commit()
                        }

                        //El equivalente de arriba pero orientado a objetos
                        //preferencias?.edit()?.putString("nombre", usuario.nombre)?.commit()

                        //En este cuarto parametro del objeto JsonObjectRequest se recibe la notificacion de la clase estatus
                        Toast.makeText(this, response.get("mensaje").toString(), Toast.LENGTH_LONG).show()
                    },
                    Response.ErrorListener { error ->
                        // TODO: Handle error
                        Toast.makeText(this, "Hubo un error, ${error}", Toast.LENGTH_LONG).show()
                    }
            )

            // Acceso al request por medio de una clase Singleton
            MiSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


        }

    }
}

class MiSingleton constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: MiSingleton? = null
        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: MiSingleton(context).also {
                        INSTANCE = it
                    }
                }
    }

    //Para el caso d cargar un objeto como una imagen.
    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,
                object : ImageLoader.ImageCache {
                    private val cache = LruCache<String, Bitmap>(20)
                    override fun getBitmap(url: String): Bitmap {
                        return cache.get(url)
                    }
                    override fun putBitmap(url: String, bitmap: Bitmap) {
                        cache.put(url, bitmap)
                    }
                })
    }
    val requestQueue: RequestQueue by lazy {
        // applicationContext es para evitar fuga de mmoria

        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}