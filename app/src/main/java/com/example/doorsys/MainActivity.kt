package com.example.doorsys

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //accessing UI element
        val proceedButton: Button = findViewById(R.id.buttonReg)
        val codeOTP: TextView = findViewById(R.id.code)
        val door:TextView = findViewById(R.id.door)
        //initialize code variable
        var code:Int = 0 ;
        var pcode:String ="";


        //button is pressed, generate a code
        proceedButton.setOnClickListener {
            //allow user to press once only
            proceedButton.isEnabled = false;

            //random function fo 6 digit pin
            code = (Math.random()*1000000).toInt()
            pcode = String.format("%06d", code);
            codeOTP.text = "Your room pin is $pcode"

            //the code is then saved to the firebase
            val database = FirebaseDatabase.getInstance()
            val codePin = database.getReference("DoorPIN")
            codePin.setValue(code.toString())

            //changing message in the button
            proceedButton.text = "SUCCESSFULLY BOOKED"
        }

        door.setOnClickListener {
            startActivity(Intent(this, logoin::class.java))
        }

    }
}