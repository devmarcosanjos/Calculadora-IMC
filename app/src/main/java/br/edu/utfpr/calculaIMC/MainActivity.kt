package br.edu.utfpr.calculaIMC

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var etPeso: EditText
    private lateinit var etAltura: EditText
    private lateinit var tvResultado: TextView
    private lateinit var btnCalcular: Button
    private lateinit var btnLimpar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        btnCalcular.setOnClickListener {
            calcularOnClick()
        }

        btnLimpar.setOnLongClickListener {
            Toast.makeText(this, "Campos limpos", Toast.LENGTH_SHORT).show()
            limparOnClick()
            true
        }
    }

    private fun init() {
        etPeso = findViewById(R.id.etPeso)
        etAltura = findViewById(R.id.etAltura)
        tvResultado = findViewById(R.id.tvResultado)
        btnCalcular = findViewById(R.id.btnCalcular)
        btnLimpar = findViewById(R.id.btnLimpar)
    }

    private fun limparOnClick() {
        etPeso.text.clear()
        etAltura.text.clear()
        tvResultado.text = ""
        etPeso.error = null
        etAltura.error = null
        etPeso.requestFocus()
    }

    private fun calcularOnClick() {
        val peso = etPeso.text.toString().toDoubleOrNull()
        val altura = etAltura.text.toString().toDoubleOrNull()

        // --- Validation ---
        if (peso == null || peso <= 0) {
            etPeso.error = "Peso inválido"
            etPeso.requestFocus()
            return
        }

        if (altura == null || altura <= 0) {
            etAltura.error = "Altura inválida"
            etAltura.requestFocus()
            return
        }

        // --- Calculation and UI Update ---
        val imc = calcularImc(peso, altura)
        tvResultado.text = String.format("%.2f", imc)
    }

    private fun calcularImc(peso: Double, altura: Double): Double {
        return peso / (altura * altura)
    }
}
