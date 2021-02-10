package com.example.clientprovider

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.insert_fragment,
                R.id.fragment_getAll,
                R.id.fragment_update,
                R.id.fragment_update
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun getAllEmail(): ArrayList<String>? {
        val list: ArrayList<String> = ArrayList()
        return try {
            val cursor: Cursor? =
                contentResolver.query(
                    MiProveedorContenidoContract.Usuarios.CONTENT_URI,
                    null, null, null, null
                )
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    list.add(
                        cursor.getString(2)
                    )
                }
            }
            cursor?.close()
            list
        } catch (e: Exception) {
            null
        }
    }

    fun insert(values: ContentValues) {
        val cr: ContentResolver = contentResolver
        val uri: Uri? = cr.insert(MiProveedorContenidoContract.Usuarios.CONTENT_URI, values)
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show()
    }

    fun update(values: ContentValues, selection: String, arguments: Array<out String>?) {
        val content = contentResolver
        val res: Int = content.update(
            MiProveedorContenidoContract.Usuarios.CONTENT_URI,
            values,
            selection,
            arguments
        )
    }

    fun delete(selection: String, arguments: Array<out String>?) {
        val content = contentResolver
        content.delete(
            MiProveedorContenidoContract.Usuarios.CONTENT_URI,
            selection,
            arguments
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}