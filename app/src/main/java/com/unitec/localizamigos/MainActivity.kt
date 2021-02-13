package com.unitec.localizamigos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        // Vamos a poner un codigo gracioso, vamos aponer el vibrador!!!
        var vi = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vi.vibrate(3000)

        /*Empezamos a programar para ver la forma de implementar los eventos de boton en android studio   */

        var botonEmpezar = findViewById<Button>(R.id.empezar)

        //Invocamos el boton localizar por su id, ahora con su plugin de extensiones
        localizar.setOnClickListener{
            startActivity( Intent(this, MapitaActivity::class.java))
        }

        //Manejamos el evento
        botonEmpezar.setOnClickListener {
            //Antes de ka navegacion hacia la activitu registro vamos a invocar a una componente
            //que se llama Toast: estos son mensjaes de corta duracion que se muestran en la pantalla
            Toast.makeText(applicationContext,"Vamos a registrarnos", Toast.LENGTH_LONG).show()
            //El siguiente renglon nos lleva de esta activity a la de registro:

            //La redireccionamos

            startActivity( Intent(this, registroactivity::class.java))


        }


        //Empezamos a programar para ver la forma de implementar los eventos de boton en android studio
        //declaracion de una variable en kotlin
        var x = 4
        var y = "Hola mundo!!"
        //tambien la formal es
        var z: Int = 5
        var w: String = "Otro mensaje"
        //asi se pone cuando no las quieras inicializar
        var t: Int
        var r: String

        //vamos a imprimir en el logcat para ello vamos a usar la base Log

        Log.i("MALO", "Este es mi primer mensjae con etiqueta en info")

        //vamos a concatenar una variable en Kotlin
        var mensajito = " vamos a concatenar"
        Log.i("MALO", "En Kotlin" + mensajito + " mas facil " + "Que Java")
        // La version de concatenado de Kotlin es mucho mejor
        Log.i("MALO", "En Kotlin $mensajito+ mas facil Que Java")
        //Ademas la interpolacion reloaded
        Log.i("MALO", " Vamos a interpolar una expresion ${5 + 3} que puede ser una operacion")
        //En Kotlin las funciones son lo que en Java los metodos y tienen tambien mucho mas poder
        //Por que pueden anidarse y son tratadas como OBJETOS. A esto se le denomina, programacion funcional
        //FUNCIONAL: UNA FUNCION ES TRATADA COMO CUALQUIER TIPO DE VARIABLE (OBJETO)
        //ES DECIR, FUNCION PUEDE MANDAR LLAMAR A OTRA FUNCION

        //Invocamos o mandamos a llamar nuestra funcion
        saludar()

        //Aqui estamos dentro del ambito de el metodo onCreate (es decir su cuerpo) aun asi kotlin permite aqui dentro
        //implementar una funcion, cosa que no es posible en Java


        fun mensajito() {
            Log.i("MALO", "Implementando una funcion dentro de otra")
        }
        //Aqui invocamos la funcion anterior
        mensajito()


        //Funciones con tipo de retorno
        fun sumar(): Int {
            var x = 5 + 4
            return x
        }
        //La invocamos
        Log.i("MALO", "Invocamos la funcion ${sumar()} con el interpolador de expresiones")


        //ota modalidad de una funcion es la siguiente
        fun saludar2(mensaje: String) {
            Log.i("MALO", mensaje)
        }
        //La invocamos
        saludar2("Este mensajito es el argumento de la funcion")


        //Kotlin es un lenguaje orientado a objetos, es funcional y tambien es reactivo


        fun saludar2(nombre: String, edad: Int) {
            Log.i("MALO", "Tu nombre es $nombre y tu edad es $edad")
        }
        //Invocamos la funcion anterior con sus argumentos
        saludar2("cesar", 27)
    }



    //Aqui declaramos una funcion
    fun saludar(){
        Log.i("MALO", "Implementando mi primer funcion en Kotlin")
    }

}