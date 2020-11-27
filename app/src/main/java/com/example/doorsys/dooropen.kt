package com.example.doorsys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.google.firebase.database.FirebaseDatabase
val secondary = FirebaseDatabase.getInstance("https://bait2123-202010-05.firebaseio.com/")
val relay1 = secondary.getReference("PI_001").child("buzz");
val relay2 = secondary.getReference("PI_001").child("lock");
class dooropen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dooropen)



//        val relay2 = secondary.getReference("PI_03_CONTROL").child("relay2");

        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                relay1.setValue("1")
                relay2.setValue("1")
            }

            override fun onFinish() {
                relay1.setValue("0")
                relay2.setValue("0")
            }
        }.start()


        relay1.setValue("0")
        relay2.setValue("0")
    }

}