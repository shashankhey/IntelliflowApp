package com.example.intelliflowapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var btnAuth: Button
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fgt_pswd = findViewById<TextView>(R.id.fgt_pswd)
        fgt_pswd.setOnClickListener {
            Intent(this, forgot_password::class.java).also {
                startActivity(it)
            }
        }

        btnAuth = findViewById(R.id.btnAuth)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(
            this@MainActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@MainActivity, "Error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
//                tvAuthStatus.text="Succesfully Auth"
                    Toast.makeText(
                        this@MainActivity,
                        "Authentication Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
//                tvAuthStatus.text="Authentication Failed"
                    Toast.makeText(this@MainActivity, "Authentication Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("Cancel")
            .build()
        btnAuth.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

        val language = findViewById<Spinner>(R.id.language)
        language.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@MainActivity,
                    "Langauge selected: ${adapterView?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }
}
