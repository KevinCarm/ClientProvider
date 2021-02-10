package com.example.clientprovider

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [insert_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class insert_fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var button: Button
    private lateinit var nombre: EditText
    private lateinit var telefono: EditText
    private lateinit var correo: EditText
    private lateinit var contrasenia: EditText
    private lateinit var main: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = context as MainActivity
    }

    private fun setup(root: View): Unit {
        button = root.findViewById(R.id.buttonInsert) as Button
        nombre = root.findViewById(R.id.txtNombreInsert) as EditText
        telefono = root.findViewById(R.id.txtNumeroInsert) as EditText
        correo = root.findViewById(R.id.txtCorreoInsert) as EditText
        contrasenia = root.findViewById(R.id.txtContraInsert)

        button.setOnClickListener {
            insertar()
        }
    }

    private fun insertar() {
        val cv = ContentValues()
        cv.put(MiProveedorContenidoContract.Usuarios.NOMBRE, nombre.text.toString())
        cv.put(MiProveedorContenidoContract.Usuarios.TELEFONO, telefono.text.toString())
        cv.put(MiProveedorContenidoContract.Usuarios.EMAIL, correo.text.toString())
        cv.put(MiProveedorContenidoContract.Usuarios.PASS, contrasenia.text.toString())
        main.insert(cv)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_insert, container, false)
        setup(view)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment insert_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                insert_fragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}