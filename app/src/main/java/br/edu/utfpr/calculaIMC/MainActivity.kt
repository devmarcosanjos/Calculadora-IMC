package br.edu.utfpr.calculaIMC

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var etPeso: EditText
    private lateinit var etAltura: EditText
    private lateinit var tvResultado: TextView
    private lateinit var tvClassification: TextView
    private lateinit var btnCalcular: Button
    private lateinit var btnLimpar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val mainView = findViewById<android.view.View>(R.id.main)
        val initialPaddingLeft = mainView.paddingLeft
        val initialPaddingTop = mainView.paddingTop
        val initialPaddingRight = mainView.paddingRight
        val initialPaddingBottom = mainView.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                initialPaddingLeft + systemBars.left,
                initialPaddingTop + systemBars.top,
                initialPaddingRight + systemBars.right,
                initialPaddingBottom + systemBars.bottom
            )
            insets
        }

        init()

        btnCalcular.setOnClickListener {
            calcularOnClick()
        }

        btnLimpar.setOnClickListener {
            limparOnClick()
        }
    }

    private fun init() {
        etPeso = findViewById(R.id.etPeso)
        etAltura = findViewById(R.id.etAltura)
        tvResultado = findViewById(R.id.tvResultado)
        tvClassification = findViewById(R.id.tvClassification)
        btnCalcular = findViewById(R.id.btnCalcular)
        btnLimpar = findViewById(R.id.btnLimpar)
    }

    private fun limparOnClick() {
        etPeso.text.clear()
        etAltura.text.clear()
        tvResultado.text = "0.0"
        tvClassification.text = ""
        etPeso.error = null
        etAltura.error = null
        etPeso.requestFocus()

        // Alterna a visibilidade dos botões
        btnLimpar.visibility = View.GONE
        btnCalcular.visibility = View.VISIBLE
    }

    private fun calcularOnClick() {
        val peso = etPeso.text.toString().toDoubleOrNull()
        val alturaCm = etAltura.text.toString().toDoubleOrNull()

        // --- Validation ---
        if (peso == null || peso <= 0) {
            etPeso.error = "Peso inválido"
            etPeso.requestFocus()
            return
        }

        if (alturaCm == null || alturaCm <= 0) {
            etAltura.error = "Altura inválida"
            etAltura.requestFocus()
            return
        }

        // --- Calculation ---
        val alturaM = alturaCm / 100.0
        val imc = calcularImc(peso, alturaM)
        tvResultado.text = String.format("%.2f", imc)

        // --- Classification ---
        val classification: String
        val color: Int
        when {
            imc < 18.5 -> {
                classification = "Abaixo do peso"
                color = Color.parseColor("#FF9800") // Orange
            }
            imc < 24.9 -> {
                classification = "Peso ideal"
                color = Color.parseColor("#4CAF50") // Green
            }
            imc < 29.9 -> {
                classification = "Levemente acima do peso"
                color = Color.parseColor("#FFC107") // Amber
            }
            imc < 34.9 -> {
                classification = "Obesidade Grau I"
                color = Color.parseColor("#F44336") // Red
            }
            imc < 39.9 -> {
                classification = "Obesidade Grau II (Severa)"
                color = Color.parseColor("#D32F2F") // Dark Red
            }
            else -> {
                classification = "Obesidade Grau III (Mórbida)"
                color = Color.parseColor("#B71C1C") // Darker Red
            }
        }
        tvClassification.text = classification
        tvClassification.setTextColor(color)

        // --- UI Update ---
        btnCalcular.visibility = View.GONE
        btnLimpar.visibility = View.VISIBLE
    }

    private fun calcularImc(peso: Double, altura: Double): Double {
        return peso / altura.pow(2.0)
    }
}
