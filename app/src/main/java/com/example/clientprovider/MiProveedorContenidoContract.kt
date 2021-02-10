package com.example.clientprovider

import android.net.Uri
import android.provider.BaseColumns

class MiProveedorContenidoContract {
    internal class Usuarios : BaseColumns{
        companion object{
            val CONTENT_ITEM_TYPE =
                    "vnd.android.cursor.item/vnd.com.example.servicioprovider.provider.usuarios"
            val CONTENT_TYPE =
                    "vnd.android.cursor.dir/vnd.com.example.servicioprovider.provider.usuarios"
            val CONTENT_URI = Uri.parse("content://com.example.servicioprovider.provider/usuarios")

            val _ID = "_id"
            val EMAIL = "email"
            val NOMBRE = "nombre"
            val TELEFONO = "tel"
            val PASS = "password"
        }
    }
}