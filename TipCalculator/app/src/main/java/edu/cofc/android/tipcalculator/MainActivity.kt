package edu.cofc.android.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import edu.cofc.android.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.billAmount.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        binding.billAmount.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                setTipPercent();
                return@setOnEditorActionListener true;
            }
                return@setOnEditorActionListener false;
        }
        binding.tenPercent.setOnClickListener {
            setTipPercent()
        }
        binding.fifteenPercent.setOnClickListener {
            setTipPercent()
        }
        binding.eighteenPercent.setOnClickListener {
            setTipPercent()
        }
        binding.twentyPercent.setOnClickListener {
            setTipPercent()
        }
    }

    private fun setTipPercent() {
        var tipPercent = 0.0
        val getInput = binding.billAmount.text.toString()
        val bill = getInput.toDouble()
        val tipSelected = binding.tipOptions.checkedRadioButtonId
        if (tipSelected == binding.tenPercent.id){
            tipPercent = 0.10
        }
        else if(tipSelected == binding.fifteenPercent.id){
            tipPercent = 0.15
        }
        else if(tipSelected == binding.eighteenPercent.id){
            tipPercent = 0.18
        }
        else if(tipSelected == binding.twentyPercent.id){
            tipPercent = 0.20
        }
        val tip = tipPercent * bill
        val total = tip + bill
        val newTip = NumberFormat.getCurrencyInstance().format(tip)
        val newTotal = NumberFormat.getCurrencyInstance().format(total)
        binding.computedTip.text = newTip.toString()
        binding.computedTotal.text = newTotal.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("billAmount", binding.billAmount.text.toString())
        outState.putInt("tipPercentage", binding.tipOptions.checkedRadioButtonId)
        outState.putString("tipAmount", binding.computedTip.text.toString())
        outState.putString("totalAmount", binding.computedTotal.text.toString())
    }

    override fun onRestoreInstanceState(inState: Bundle) {
        super.onRestoreInstanceState(inState)
        binding.billAmount.setText(inState.getString("billAmount"))
        binding.tipOptions.check(inState.getInt("tipPercentage"))
        binding.computedTip.text = inState.getString("tipAmount")
        binding.computedTotal.text = inState.getString("totalAmount")
    }

}