package com.rare.datepicker

import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rare.datepicker.databinding.ActivityMainBinding
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var getImageContract = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            Toast.makeText(this,"Permission granted", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Permission not granted", Toast.LENGTH_LONG).show()
        }
    }

    var pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        //uri - universal resource identifier
        System.out.println("in uri $it")
        binding.ivIDproof.setImageURI(it)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnUploadImage.setOnClickListener {
          if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
              pickImage.launch("image/*")
          }else{
              getImageContract.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
          }
        }
        binding.btnDatepicker.setOnClickListener {
            showDatePickerDialog()
        }

        }
    fun showDatePickerDialog() {
        var selectedCalenderDate = Calendar.getInstance()
        var d = selectedCalenderDate.get(Calendar.DAY_OF_MONTH)
        var m = selectedCalenderDate.get(Calendar.MONTH)
        var y = selectedCalenderDate.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(this@MainActivity, { _, year, month, day ->
            selectedCalenderDate.set(year, month, day)
        //MMM-Mar, Jan, Feb
//            MM - Month
//            mm-minutes
        //hh-12hour format
        //aa -AM/PM
        //HH-24hour format
        //ss-seconds

        val simpledateformat = SimpleDateFormat("dd/MM/yyyy")
        val formatedDate = simpledateformat.format(selectedCalenderDate.time)
        binding.tvDatepicker.setText(formatedDate)
        }, y, m, d)
        datePickerDialog.show()
    }
}

