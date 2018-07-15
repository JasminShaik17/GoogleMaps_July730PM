package cubex.mahesh.googlemaps

import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.location.places.Place
import android.content.Intent





class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sManager =     supportFragmentManager.findFragmentById(R.id.frag1)
                    as SupportMapFragment
        sManager.getMapAsync({
Toast.makeText(this@MainActivity,
        "Map is Ready",Toast.LENGTH_LONG).show()
        //    it.mapType = GoogleMap.MAP_TYPE_HYBRID

            var lManager = getSystemService(Context.LOCATION_SERVICE)
                    as LocationManager
            lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000.toLong(),1.toFloat(), object : LocationListener {
                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
               }
                override fun onProviderEnabled(p0: String?) {
                }
                override fun onProviderDisabled(p0: String?) {
                }
                override fun onLocationChanged(p0: Location?) {
                        tv1.text = p0!!.latitude.toString()
                        tv2.text = p0!!.longitude.toString()

                    var marker = MarkerOptions()
                    marker.position(LatLng(p0!!.latitude,p0!!.longitude))
                    marker.title("NareshIT-New Block")
                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.bike))
                    it.addMarker(marker)
                    it.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            LatLng(p0!!.latitude,p0!!.longitude),15.toFloat()
                    ))

                        lManager.removeUpdates(this)
                }
            })
        })

        sl.setOnClickListener({
            val PLACE_PICKER_REQUEST = 1
            val builder = PlacePicker.IntentBuilder()
            startActivityForResult(builder.build(this@MainActivity),
                    PLACE_PICKER_REQUEST)
        })

    } // onCreate

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data!!, this)
                val toastMsg = String.format("Place: %s", place.name)
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
            }
        }
    }
}
