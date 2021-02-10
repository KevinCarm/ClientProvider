package com.example.clientprovider

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_delete.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_delete : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var button: Button
    private lateinit var listDelete: Spinner
    private lateinit var main: MainActivity
    private var _ID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        main = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_delete, container, false)
        setup(view)
        return view
    }

    private fun setup(root: View): Unit {
        button = root.findViewById(R.id.buttonDelete)
        button.setOnClickListener {
            delete()
        }
        listDelete = root.findViewById(R.id.listSelectdelete) as Spinner
        val adapter =
            context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_dropdown_item,
                    main.getAllEmail()!!
                )
            }
        listDelete.adapter = adapter
        listDelete.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("Recycle")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item: String =
                        parent?.getItemAtPosition(position).toString()
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
                                    _ID = cursor.getString(0)
                                    Toast.makeText(context, _ID, Toast.LENGTH_SHORT).show()
                                    break
                                }
                            }
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
    }

    private fun delete() {
        try {
            main.delete(
                "${MiProveedorContenidoContract.Usuarios._ID}=?",
                arrayOf(_ID)
            )
            Toast.makeText(context, "Eliminado correctamente", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, e.message + " ----", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_delete.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_delete().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}