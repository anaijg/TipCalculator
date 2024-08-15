package org.hyperskill.calculator.tip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // retrieval of all the views in layout
        val display = findViewById<EditText>(R.id.edit_text)
        val billValueTextView = findViewById<TextView>(R.id.bill_value_tv)
        val tipPercentTextView = findViewById<TextView>(R.id.tip_percent_tv)
        val seekBar = findViewById<SeekBar>(R.id.seek_bar)
        val tipAmountTextView = findViewById<TextView>(R.id.tip_amount_tv)

        // variables we need for calculations, which are updated in different listeners
        var billAmount: Double = 0.0
        var percentage: Double = seekBar.progress.toDouble()

        display.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputText = s.toString()
               // If the value is greater than zero display the value on bill_value_tv with the following format: Bill Value: $[amount], where [amount] is a placeholder for a number always displayed with two decimal places;
                val billValue = inputText.toDoubleOrNull()
                if (billValue != null && billValue > 0) {
                    val formattedBillValue = String.format("Bill Value: $%.2f", billValue)
                    billValueTextView.setText(formattedBillValue)
                    // Also if the value is greater than1 zero display the current tip percent on tip_percent_tv with the following format: Tip: [tipPercent]%, where [tipPercent] is a placeholder for an integer number. The tip percent value should be equal to the progress attribute on seek_bar
                    val tipPercent = seekBar.progress
                    val formattedTipPercent = String.format("Tip: %d%%", tipPercent)
                    tipPercentTextView.setText(formattedTipPercent)
                    // Display the tipAmount result on tip_amount_tv with the following format Tip Amount: $[amount] where [amount] is a placeholder for a number always displayed with two decimal places
                    billAmount = billValue.toDouble()
                    val tipAmount = calculateTipAmount(billAmount, percentage)
                    // Display the tipAmount result on tip_amount_tv with the following format Tip Amount: $[amount] where [amount] is a placeholder for a number always displayed with two decimal places;
                    val formattedTipAmount = String.format("Tip Amount: $%.2f", tipAmount)
                    tipAmountTextView.setText(formattedTipAmount)
                } else {
                    tipPercentTextView.setText("")
                    billValueTextView.setText("")
                    // If the value on edit_text is either zero or empty then keep tip_amount_tv empty;
                    tipAmountTextView.setText("")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed after text changes
            }
        })


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // When the value of progress attribute changes update the value displayed at tip_percent_tv using the same format described above;
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val inputText = display.text.toString()
                if (inputText.isNotEmpty() && inputText.toDoubleOrNull() != null && inputText.toDouble() > 0) {
                    val formattedTipPercent = String.format("Tip: %d%%", progress)
                    tipPercentTextView.text = formattedTipPercent
                    percentage = progress.toDouble()
                    // Make sure that the value on tip_amount_tv is updated with changes on either edit_text or seek_bar;
                    val tipAmount = calculateTipAmount(billAmount, percentage)
                    val formattedTipAmount = String.format("Tip Amount: $%.2f", tipAmount)
                    tipAmountTextView.setText(formattedTipAmount)
                } else {
                    tipPercentTextView.text = ""
                    tipAmountTextView.setText("")
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No action needed when tracking starts
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // No action needed when tracking stops
            }
        })
    }

    // Calculate the tipAmount, which will be the result of multiplying the value on edit_text with the value on seek_bar and dividing by 100
    fun calculateTipAmount(billAmount: Double, percentage: Double): Double {
        return billAmount * percentage / 100.0
    }


}