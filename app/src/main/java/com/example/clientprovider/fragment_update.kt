package com.example.clientprovider

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_update.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_update : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var spinner: Spinner
    private lateinit var nombre: EditText
    private lateinit var telefono: EditText
    private lateinit var correo: EditText
    private lateinit var contra: EditText
    private lateinit var button: Button
    private lateinit var main: MainActivity
    private var ID: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        setup(view)
        return view
    }

    private fun setup(root: View): Unit {
        spinner = root.findViewById(R.id.listSelectUpdate)
        nombre = root.findViewById(R.id.txtNombreUpdate)
        telefono = root.findViewById(R.id.txtNumeroUpdate)
        correo = root.findViewById(R.id.txtCorreoUpdate)
        contra = root.findViewById(R.id.txtContraUpdate)
        button = root.findViewById(R.id.buttonUpdate)
        val adapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item,
                    main.getAllEmail()!!
                )
            }
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val item = parent.getItemAtPosition(position).toString()
                fillFields(item)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        button.setOnClickListener {
            try {
                update()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fillFields(item: String) {
        val content: ContentResolver? =
            main.contentResolver
        if (content != null) {
            val cursor =
                content.query(
                    MiProveedorContenidoContract.Usuarios.CONTENT_URI,
                    null, null, null, null
                )
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    if (cursor.getString(2).equals(item)) {
                        ID = cursor.getInt(0)
                        correo.setText(cursor.getString(2) + ID)
                        nombre.setText(cursor.getString(1))
                        telefono.setText(cursor.getString(3))
                        contra.setText(cursor.getString(4))
                        break
                    }
                }
            }
            cursor?.close()
        }
    }

    private fun update(): Unit {
        if (nombre.text.isNotEmpty() && telefono.text.isNotEmpty()
            && correo.text.isNotEmpty() && contra.text.isNotEmpty()
        ) {
            val cv = ContentValues()
            cv.apply {
                put(MiProveedorContenidoContract.Usuarios.NOMBRE, nombre.text.toString())
                put(MiProveedorContenidoContract.Usuarios.TELEFONO, telefono.text.toString())
                put(MiProveedorContenidoContract.Usuarios.EMAIL, correo.text.toString())
                put(MiProveedorContenidoContract.Usuarios.PASS, contra.text.toString())
            }
            try {
                main.update(
                    cv,
                    "${MiProveedorContenidoContract.Usuarios._ID}=?",
                    arrayOf(ID.toString())
                )
                Toast.makeText(context, "Actualización correcta", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {

            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_update.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_update().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}