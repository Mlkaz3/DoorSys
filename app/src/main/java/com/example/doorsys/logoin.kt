package com.example.doorsys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

lateinit var code:String
class logoin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logoin)

        val psw: EditText = findViewById(R.id.password)
        val buttonProceed: Button = findViewById(R.id.buttonProceed)
        val textView:TextView = findViewById(R.id.textView)

        //retrieving the pass code from firebase
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("DoorPIN")

        val secondary = FirebaseDatabase.getInstance("https://bait2123-202010-05.firebaseio.com/")
        val relay1 = secondary.getReference("PI_001").child("buzz");
        val relay2 = secondary.getReference("PI_001").child("lock");
//        val relay2 = secondary.getReference("PI_001").child("relay2");

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //storing the correct password into code variable
                code = dataSnapshot.value.toString();
            }

            override fun onCancelled(error: DatabaseError) {
                textView.text = "FAILED"
            }
        })


        buttonProceed.setOnClickListener {
            relay1.setValue("0")
            relay1.setValue("0")
            //assigning user input to a variable
            val password:String = psw.text.toString().trim();
            if(password.isEmpty()){
                psw.error = "Password is required!";
                psw.requestFocus();
            }

            else if(password.length < 6){
                psw.error = "Password must not less than 6 digit!";
                psw.requestFocus();
            }
            else{
                //do password checking
                if(password == code){
                    //door open
                    //chg sir firebase ;)
                    relay1.setValue("1")
                    relay2.setValue("1")
                    startActivity(Intent(this, dooropen::class.java))
                }
                else{
                    psw.error = "Password does not match!";
                    psw.requestFocus();
                }

            }}

    }
}