package com.example.ex2

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import org.w3c.dom.Text
import android.widget.Toast

import android.content.DialogInterface
import androidx.core.view.isVisible
import java.lang.Math.ceil
import android.widget.RelativeLayout

import android.view.ViewGroup
import android.util.DisplayMetrics
import android.widget.RelativeLayout.ALIGN_RIGHT


var answer = ""
var display_answer = ""
val example_map = mapOf(
    0 to "Example:123",
    1 to "Example:123.0",
    2 to "Example:123.00",
    3 to "Example:123.000",
    4 to "Example:123.0000",
    5 to "Example:123.00000",
)


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("SSS", "S")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        /* --------------------------------------------------------------------------------
            flag - Correlate with the two text fields to know when they are both not empty
            All the rest of the varuables are made to connect a listener to them.
            The seek bar is for adding an on seek listener to him.
         ------------------------------------------------------------------------------*/

        var flag = 0
        val add_btn = findViewById(R.id.btnAdd) as Button
        val deacrease_btn = findViewById(R.id.btnDec) as Button
        val multiply_btn = findViewById(R.id.BtnMul) as Button
        val divide_btn = findViewById(R.id.btnDiv) as Button
        val clearBtn = findViewById(R.id.clearBtn) as Button
        val firstTxt = findViewById(R.id.firstArg) as TextView
        val sceondTxt = findViewById(R.id.secondArg) as TextView

        //Dynamicly interting the subLayout that we created and position him inside the Frame layout

        val parentLayout = findViewById<View>(R.id.seekerBar) as ViewGroup
        val child: View = layoutInflater.inflate(R.layout.seekbar, parentLayout, false)
        val rlp = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        rlp.setMargins(0, dp2px(10), 0, 0);
        rlp.addRule(RelativeLayout.BELOW, R.id.main);
        parentLayout.addView(child, rlp)
        val exampleTxt = findViewById(R.id.exampleTxt) as TextView
        val sBar = findViewById<SeekBar>(R.id.seekBar)
        toggle_seekBar(false)
// ----------------------------------- TextView's and SeekBar Listeners ------------------------- //

        /* -------------------------------------------------------------------------------------
            This listener is handling the first text that the user need to eneter.
            After the user firstly keystroke then the button will be enable IF the second
            text has been filled already. If not, the flag value will be 1 and the button
            still be disabled.
            If the listener see that the string that he is in now is empty thats mean the user
            just cleared the text field and we want to disable the button from use.

            *** The same goes for the secondTXT.addTextChangedListener **

         --------------------------------------------------------------------------------------*/
        firstTxt.addTextChangedListener(object : TextWatcher { // for the  first operand
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) { // this function will be called when the user start typing

                if (s.toString().length == 1) { // check if its one because we dont want this function to be called every key stroke.
                    flag += 1
                    if (flag == 2) {
                        enableBtn(add_btn)
                        enableBtn(deacrease_btn)
                        enableBtn(multiply_btn)
                    }

                }
                if (s.toString() == "") {
                    flag -= 1
                    disableBtn(add_btn)
                    disableBtn(deacrease_btn)
                    disableBtn(multiply_btn)
                    disableBtn(divide_btn)

                }
            }
        })
        // As mentioned above, the funcionality is the same but there is one more check to do
        // for the divide, if the value is 0 we want the divide button to be disabled.
        sceondTxt.addTextChangedListener(object : TextWatcher { // for the  first operand
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) { // this function will be called when the user start typing

                if (s.toString().length == 1) { // check if its one because we dont want this function to be called every key stroke.
                    flag += 1
                    if (flag == 2) {
                        enableBtn(add_btn)
                        enableBtn(deacrease_btn)
                        enableBtn(multiply_btn)
                    }

                }
                if (s.toString() != "0") {
                    enableBtn(divide_btn)
                }
                if (s.toString() == "") {
                    flag -= 1
                    disableBtn(add_btn)
                    disableBtn(deacrease_btn)
                    disableBtn(multiply_btn)
                    disableBtn(divide_btn)

                }
            }
        })

        /*
            the reuslt text will be change according to the move of the seek bar
            if the uer will increase the seek bar then more numbers will be added to the
            result. if the number doesnt have a  fraction and its an integer then zeros will be added
            if the number does have a fraction then the numbers that will be added are the numbers that
            was before the seekbar was decreased.
           */
        sBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            /*
                 - resultNumber - The final result that will be displayed to the result textView
                 - resul - input of the result after the user  clicked on one of the buttons
                 - contains_dot - boolean flag that  will indicate if the number is integer or float.
                 - count_digits - integer that will hold the number of digits after the dot.
                 - denominator - will hold the denominator side of the number only
                 - resul_in_string - will hold the result of the text input as a string and not as an object( as resul holds)
                 - list - will hold the number by 2 places ( if its float) the first place will indicate the numerator and the second for the denominator
                 - save_numerator - will hold the numerator of the resulted number

             */
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var resultNumber = ""
                val resul = findViewById<android.widget.TextView>(com.example.ex2.R.id.result)
                var contains_dot = false
                var count_digits = 0
                var denominator = ""
                val resul_in_string = resul.text.toString()
                // we have the ruslt number in string here and we would like to split to get the numerator and denominator
                val list = resul_in_string.split(".")
                var save_numerator = list[0]
                contains_dot = list.toString().contains(",")
                if (contains_dot) {
                    count_digits = list[1].length

                    /*----------------------------------------------------------------------------------
                        We have the number of the current progress bar and we would like to add to the
                        denominator the amount of 0's in the end of the number
                        if the current progress is less then the number then we will decrease the last cell
                        else we  will increase by adding zero
                      ----------------------------------------------------------------------------------*/
                    denominator = list[1]

                  // If the user want to decrease the progress bar but he have more number after the dot then the progress
                    if (count_digits > progress) {

                        for (i in count_digits downTo progress + 1) {
                            var temp_char = denominator[denominator.length - 1]

                            // substring the string without the last char
                            denominator = denominator.substring(0, (denominator.length - 1))
                            // checking if the last char that  was (as int) if he is bigger then five
                            // then will need to ceil the number - if the number that we will erase is bigger then five
                            // we would like to make the number before the number that will be earased +1.

                            if (temp_char.digitToInt() >= 5) {
                                if (denominator.length > 0) {
                                    var change_to = (denominator[denominator.length - 1])
                                    change_to = (change_to.toInt() + 1).toChar()
                                    denominator = denominator.dropLast(1) + change_to.toString()
                                } else {
                                    save_numerator = (save_numerator.toInt() + 1).toString()
                                }
                            }
                        }
                    }
                }

                // Will split the number into  2 where the list will contain in 0 cell the numerator

                /* --------------------------------------------------------------------------------
                   If the number of digites is less then the progress then need to add a number to the
                   cell like it  was before the downgrade of the number
                   - Go into the number that has been saved.
                   - Extract the current number after the dot.
                   - Extract the next number after the dot that need to be added
                   - IF the denominator is empty that we need to get back the numerator.

                   -------------------------------------------------------------------------------*/
                if (count_digits < progress) {
                    var uping_flag = false
                    var denominator_res = answer.split(".")// 5.181812
                    uping_flag = denominator_res.toString().contains(",")
                    if (denominator == "") {
                        save_numerator = denominator_res[0]

                    }
                    for (i in count_digits..progress - 1) {
                        if (uping_flag) {


                            // add the second number
                            // try and catch for the  option that we are in the first argument and i=0.
                            try {
                                denominator = denominator.dropLast(1) + (denominator_res[1][i - 1])
                                denominator += denominator_res[1][i]

                            } catch (e: Throwable) {
                                denominator += "0"
                            }
                        } else {

                            denominator += "0"
                        }
                    }
                }
                // we dont want to put the "." in the answer because the number is as integer.
                if (progress == 0) {
                    resultNumber = save_numerator + denominator
                } else
                    resultNumber = save_numerator + "." + denominator
                resul.setText(resultNumber)
                exampleTxt.setText(example_map.get(progress))

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

//-------------------------------- Buttons listeners --------------------------------------------//

        clearBtn.setOnClickListener {
            firstTxt.setText("")
            sceondTxt.setText("")
            val resul = findViewById<android.widget.TextView>(com.example.ex2.R.id.result)
            resul.setText("")
            toggle_seekBar(false)
        }

        add_btn.setOnClickListener {

            val firstArg = returnFirstArg()
            val secondArg = returnSecondArg()
            val resul = findViewById<android.widget.TextView>(com.example.ex2.R.id.result)
            val res = (firstArg + secondArg).toString()
            answer = res

            resul.setText(res)
            toggle_seekBar(true)

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            sikerSet()
        }


        deacrease_btn.setOnClickListener {

            val firstArg = returnFirstArg()
            val secondArg = returnSecondArg()
            val resul = findViewById<TextView>(R.id.result)
            val res = (firstArg - secondArg).toString()
            answer = res
            resul.setText(res)
            toggle_seekBar(true)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            sikerSet()

        }


        multiply_btn.setOnClickListener {

            val firstArg = returnFirstArg()
            val secondArg = returnSecondArg()
            val resul = findViewById<TextView>(R.id.result)
            val res = (firstArg * secondArg).toString()
            answer = res
            resul.setText(res)
            toggle_seekBar(true)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            sikerSet()
        }



        divide_btn.setOnClickListener {

            val secondArg = returnSecondArg()
            val resul = findViewById<TextView>(R.id.result)
            val res = (returnFirstArg().toFloat() / secondArg.toFloat()).toString()

            answer = res
            var tmp = res.split(".")
            var have_dot = tmp.contains(",")
            if (have_dot) {
                display_answer = tmp[1] // only s
            }
            resul.setText(res)
            toggle_seekBar(true)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            sikerSet()
        }


    }

// ------------------------------------- FUNCTIONS ----------------------------------------------//

    fun sikerSet() {
        val sBar = findViewById<SeekBar>(R.id.seekBar)
        val resul = findViewById<android.widget.TextView>(com.example.ex2.R.id.result)
        val resul_in_string = resul.text.toString()
        val exampleTxt = findViewById(R.id.exampleTxt) as TextView

        // we have the ruslt number in string here and we would like to split to get the numerator and denominator
        val list = resul_in_string.split(".")
        var dot_flag = list.toString().contains(",")


        /* ----------------------------------------------------------------------------------------
                    we would like to know the size of the denominator  that is corrently positioned.
                    If the current number is bigger then five we would put the tracker to five
           ---------------------------------------------------------------------------------------*/
        if (dot_flag) {
            val count_digits = list[1].length
            if (count_digits > 5) {

                sBar.progress = 5
                exampleTxt.setText(example_map.get(5))
            } else {

                sBar.progress = count_digits
                exampleTxt.setText(example_map.get(count_digits))

            }
        } else {
            sBar.progress = 0
            exampleTxt.setText(example_map.get(0))
        }


    }


    fun dp2px(dp: Int): Int {
        val metrics = resources.displayMetrics
        val px = dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        return px.toInt()
    }


    fun toggle_seekBar(boolean: Boolean) {
        val sBar = findViewById<SeekBar>(R.id.seekBar)
        sBar.setEnabled(boolean)
    }

    fun enableBtn(button: Button) {
        button?.isEnabled = true
    }

    fun disableBtn(button: Button) {
        button?.isEnabled = false
    }

    // function that will return the first value that has been entered by the user as integer
    fun returnFirstArg(): Int {
        val temp = findViewById<TextView>(R.id.firstArg)
        val msg: String = temp.text.toString()
        if (msg.trim().isEmpty())
            return -1
        return Integer.parseInt(temp.getText().toString());
    }

    fun returnSecondArg(): Int {
        val temp = findViewById<EditText>(R.id.secondArg)
        val msg: String = temp.text.toString()
        if (msg.trim().isEmpty())
            return -1
        return Integer.parseInt(temp.getText().toString());
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (answer != "") {
            val resul = findViewById<TextView>(R.id.result)
            resul.setText(answer)
        }


    }
// -------------------------------------- END OF CLASS -------------------------------------------//
}