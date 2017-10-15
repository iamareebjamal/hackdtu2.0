package er.health.help

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import er.health.help.data.HealthService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import javax.inject.Inject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import android.R.array
import android.R.attr.bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import java.nio.ByteBuffer


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var service: HealthService

    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 42)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            42 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    dispatchTakePictureIntent()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data.extras
            val imageBitmap = extras!!.get("data") as Bitmap

            val size = imageBitmap.rowBytes * imageBitmap.height
            val byteBuffer = ByteBuffer.allocate(size)
            imageBitmap.copyPixelsToBuffer(byteBuffer)
            val byteArray = byteBuffer.array()
            val requestFile = RequestBody.create(MediaType.parse("image/jpg"), byteArray)

            service.predictFood(requestFile)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ resource ->
                        Toast.makeText(this, resource.toString(), Toast.LENGTH_LONG).show()
                    }, { error ->
                        error.printStackTrace()
                    })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val healthApp: HealthApplication =  application as HealthApplication
        healthApp.component.inject(this)

        fab.setOnClickListener {
            askPermission()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
