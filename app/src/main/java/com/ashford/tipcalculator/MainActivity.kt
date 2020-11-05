@file:Suppress("SpellCheckingInspection")

package com.ashford.tipcalculator

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.ashford.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity(){
    //This line declares a top-level variable in the class for the binding object.
    //lateinit is saying that the code will initialize the variable before using it.
    private lateinit var binding: ActivityMainBinding

    /*Create our onCreate function to be used as our main.
    * Binding is initialized which will access views in our activity_main.xml
    * binding.root is the root of the hierarchy. It connects all of our views
    * */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener{ calculateTip() }

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }

    }

    private fun calculateTip(){
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if(cost == null || cost == 0.0){
            displayTip(0.0)
            return
        }

        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.twenty_percent -> .20
            R.id.fifteen_percent -> .15
            else -> .10
        }

        var tip = tipPercentage * cost
        if(binding.roundUpSwitch.isChecked){
            tip = ceil(tip)
        }

        displayTip(tip)
    }

    private fun displayTip(tip: Double){
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.totalCostText.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}