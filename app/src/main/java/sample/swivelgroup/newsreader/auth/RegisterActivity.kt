package sample.swivelgroup.newsreader.auth

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.content_register.*
import android.widget.Toast
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R
import android.content.Intent
import kotlinx.android.synthetic.main.content_register.view.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(sample.R.layout.activity_register)
        setSupportActionBar(toolbar)
/*
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        appCompatButtonRegister.setOnClickListener {
            val PersonName=textInputEditTextName.text.toString().trim();
            val PersonEmail=textInputEditTextEmail.text.toString().trim();
            val PersonPassword=textInputEditTextPassword.text.toString().trim();

            val preferences = getSharedPreferences("mynewsreader",MODE_PRIVATE);
            val editpreference = preferences.edit();
            editpreference.putString("PersonName",PersonName);
            editpreference.putString("PersonEmail",PersonEmail);
            editpreference.putString("PersonPassword",PersonPassword);
            editpreference.apply();
            Toast.makeText(this, "You Registered", Toast.LENGTH_SHORT).show()
            val myIntent =  Intent(getBaseContext(),LoginActivity::class.java);
            startActivity(myIntent);
        }

        appCompatTextViewLoginLink.setOnClickListener {
            val myIntent =  Intent(getBaseContext(),LoginActivity::class.java);
            startActivity(myIntent);
        }


    }

    private fun validate(): Boolean {
        var temp = true
       // val checkemail = em.getText().toString()
        val pass = textInputEditTextPassword.text.toString().trim();
        val cpass =""
       if (pass != cpass) {
            Toast.makeText(this, "Password Not matching", Toast.LENGTH_SHORT).show()
            temp = false
        }
        return temp
    }

}
