package com.globits.mita.utils

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.*
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.globits.mita.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import me.dm7.barcodescanner.zbar.ZBarScannerView
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun getSpannedText(text: String): Spanned? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        TODO("VERSION.SDK_INT < N")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Date.format(format: String? = null): String {
    val ld = toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return ld.format(DateTimeFormatter.ofPattern(format ?: "dd/MM/yyyy"))
}

fun AppCompatActivity.addFragment(
    frameId: Int,
    fragment: Fragment,
    allowStateLoss: Boolean = false
) {
    supportFragmentManager.commitTransaction(allowStateLoss) { add(frameId, fragment) }
}

inline fun androidx.fragment.app.FragmentManager.commitTransaction(
    allowStateLoss: Boolean = false,
    func: FragmentTransaction.() -> FragmentTransaction
) {
    val transaction = beginTransaction().func()
    if (allowStateLoss) {
        transaction.commitAllowingStateLoss()
    } else {
        transaction.commit()
    }
}

fun <T : Fragment> AppCompatActivity.addFragmentToBackstack(
    frameId: Int,
    fragmentClass: Class<T>,
    tag: String? = null,
    allowStateLoss: Boolean = false,
    option: ((FragmentTransaction) -> Unit)? = null
) {
    supportFragmentManager.commitTransaction(allowStateLoss) {
        option?.invoke(this)
        replace(frameId, fragmentClass, null, tag).addToBackStack(tag)
    }
}
fun <T : Fragment> AppCompatActivity.addFragment(
    frameId: Int,
    fragmentClass: Class<T>,
    tag: String? = null,
    allowStateLoss: Boolean = false,
    option: ((FragmentTransaction) -> Unit)? = null
) {
    supportFragmentManager.commitTransaction(allowStateLoss) {
        option?.invoke(this)
        add(frameId, fragmentClass, null, tag)
    }
}

fun TextInputEditText.transformIntoDatePicker(
    context: Context,
    currentDate: Date? = null,
    maxDate: Date? = null,
    minDate: Date? = null
) {
//            isFocusableInTouchMode = false
    isClickable = true
    // isFocusable = false


    val myCalendar = Calendar.getInstance()
    currentDate?.also { myCalendar.time = it }
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            setText(sdf.format(myCalendar.time))
        }


    setOnTouchListener(object : View.OnTouchListener {
        override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
            hideKeyboard()
            val drawableRight = 2
            if (p1!!.action == MotionEvent.ACTION_UP) {
                if (p1.rawX >= right - compoundDrawables[drawableRight].bounds.width()) {
                    DatePickerDialog(
                        context, datePickerOnDataSetListener, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                    ).run {
                        maxDate?.time?.also { datePicker.maxDate = it }
                        minDate?.time?.also { datePicker.minDate = it }
                        show()
                    }
                    return true
                }
            }
            return false
        }
    })
}

fun View.hideKeyboard(activity: Activity? = null) {
    val imm = context?.getSystemService<InputMethodManager>()
    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    imm?.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun ExposedDropdownMenu.hideKeyboardDrop(activity: Activity? = null) {
    inputType = InputType.TYPE_NULL
    imeOptions = EditorInfo.IME_ACTION_DONE
    showSoftInputOnFocus = false
    onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
        hideKeyboard(activity)
    }
    onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
        hideKeyboard(activity)
    }
}

fun Fragment.formatdate(date: TextInputEditText, dateTil: TextInputLayout) {
    date.addTextChangedListener(object : TextWatcher {
        private var current = ""
        private val ddmmyyyy = "DDMMYYYY"
        private val cal: Calendar = Calendar.getInstance()
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            dateTil.error = ""
            try {
                if (s.toString() != current) {
                    var clean = s.toString().replace("[^\\d.]".toRegex(), "")
                    val cleanC = current.replace("[^\\d.]".toRegex(), "")
                    val cl = clean.length
                    var sel = cl
                    var i = 2
                    while (i <= cl && i < 6) {
                        sel++
                        i += 2
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean == cleanC) sel--
                    if (clean.length < 8) {
                        clean += ddmmyyyy.substring(clean.length)
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        var day = clean.substring(0, 2).toInt()
                        var mon = clean.substring(2, 4).toInt()
                        var year = clean.substring(4, 8).toInt()
                        if (mon > 12) mon = 12
                        cal.set(Calendar.MONTH, mon - 1)
                        year = if (year < 1900) 1900 else if (year > 2100) 2100 else year
                        cal.set(Calendar.YEAR, year)
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012
                        day =
                            if (day > cal.getActualMaximum(Calendar.DATE)) cal.getActualMaximum(
                                Calendar.DATE
                            ) else day
                        clean = String.format("%02d%02d%02d", day, mon, year)
                    }
                    clean = String.format(
                        "%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8)
                    )
                    sel = if (sel < 0) 0 else sel
                    current = clean
                    date.setText(current)
                    date.setSelection(if (sel < current.length) sel else current.length)
                }
            } catch (e: Exception) {
                dateTil.error = getString(R.string.date_invalid)
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {
        }
    })
}

fun Activity.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar =
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction(getString(R.string.retry)) {
            it()
        }
    }
    snackbar.show()
}

fun EditText.removeError(layout: TextInputLayout) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
            layout.error = ""
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            TODO("Not yet implemented")
        }

        override fun afterTextChanged(s: Editable?) {
            TODO("Not yet implemented")
        }

    })
}

fun EditText.parseDate(): Date? {
    if (text.trim().isEmpty()) {
        return null
    }

    val s = text.trim().toString()
    val arr = s.split("/")
    if (arr.size == 3) {
        var ds = ""

        ds += when (arr[0].length) {
            0 -> ""
            1 -> "0" + arr[0] + "/"
            2 -> arr[0] + "/"
            else -> ""
        }

        ds += when (arr[1].length) {
            0 -> ""
            1 -> "0" + arr[1] + "/"
            2 -> if (arr[1].toInt() <= 12) (arr[1] + "/") else ""
            else -> ""
        }

        ds += if (arr[2].length == 4) arr[2] else ""

        if (ds.length != 10) return null

        // check month end
        val midMonth = LocalDate.of(ds.substring(6).toInt(), ds.substring(3, 5).toInt(), 15)
        val endOfMonth = midMonth.lengthOfMonth()
        if (ds.substring(0, 2).toInt() > endOfMonth) {
            ds = endOfMonth.toString() + ds.substring(2)
        }

        val date = LocalDate.parse(ds, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val instant: Instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant()
        return Date.from(instant)
    }

    return null
}

fun createDialogScanner(
    context: Context,
    activity: Activity?,
    eventClick: (String) -> Unit
) {
    val dialog = Dialog(context)
    dialog.setContentView(R.layout._notifi_dialog_scanner)
    dialog.window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
    var btnClose = dialog.findViewById<Button>(R.id.btn_close)
    var txtcontent = dialog.findViewById<FrameLayout>(R.id.container)
    var scannerView = ZBarScannerView(context)
    scannerView.setResultHandler {
        val data = it.contents
        if (!data.contains("TS")) {
            Toast.makeText(
                context,
                context.getString(R.string.code_invalid),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            eventClick(data)
            dialog.dismiss()
        }

    }
    scannerView.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
    scannerView.setBorderColor(ContextCompat.getColor(context, R.color.black))
    scannerView.setLaserColor(ContextCompat.getColor(context, R.color.purple_700))
    scannerView.setBorderStrokeWidth(10)
    scannerView.setSquareViewFinder(true)
    scannerView.setupScanner()
    scannerView.setAutoFocus(true)
    scannerView.startCamera()
    txtcontent.addView(scannerView)
    btnClose.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_DENIED
    ) {
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.CAMERA),
            123
        )
    }
}

fun formatNumber(number: Int): String {
    return String.format("%,d", number)
}