package com.example.test.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.data.BookModel
import com.example.test.observe
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.Interval
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private val s1 = "Test"
    private val s2 = "Test"
    private var startDate1: Date? = null
    private var endDate1: Date? = null
    private var startDate2: Date? = null
    private var endDate2: Date? = null

    private var numbers: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstance()
        subscribe()
        setNumbers()
    }

    private fun subscribe() {
        mainViewModel.apply {
            observe(books, ::updateResult4)
        }
    }

    private fun initInstance() {
        btn1.setOnClickListener { getAnswer1() }
        btn2.setOnClickListener { getAnswer2() }
        btn3.setOnClickListener { getAnswer3() }
        btn4.setOnClickListener { getAnswer4() }
        tvStartDate1.apply {
            setOnClickListener {
                setDateText(this, KEY_START_DATE_1)
            }
        }
        tvEndDate1.apply {
            setOnClickListener {
                setDateText(this, KEY_END_DATE_1)
            }
        }
        tvStartDate2.apply {
            setOnClickListener {
                setDateText(this, KEY_START_DATE_2)
            }
        }
        tvEndDate2.apply {
            setOnClickListener {
                setDateText(this, KEY_END_DATE_2)
            }
        }
    }

    private fun setDateText(textView: TextView, type: String) {
        showDatePicker { date ->
            when (type) {
                KEY_START_DATE_1 -> startDate1 = date
                KEY_END_DATE_1 -> endDate1 = date
                KEY_START_DATE_2 -> startDate2 = date
                KEY_END_DATE_2 -> endDate2 = date
            }
            textView.text = date.format("dd/MM/YYYY")
        }
    }

    private fun getAnswer1() {
        var r1 = ""
        var r2 = ""
        if (s1 == s2) {
//            Log.d("answer1", "Same")
            r1 = "Same"
        }
        if (s1.equals(s2)) {
//            Log.d("answer1", "Equals")
            r2 = "Equals"
        }
        tvResult1.text = "$r1$r2"
    }

    private fun getAnswer2() {
        if (startDate1 != null && endDate1 != null && startDate2 != null && endDate2 != null) {
            val interval1 = Interval(startDate1!!.time, endDate1!!.time)
            val interval2 = Interval(startDate2!!.time, endDate2!!.time)
            tvResult2.text = interval1.overlaps(interval2).toString()
        }
    }

    private fun showDatePicker(f: (date: Date) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this, { _, year, month, dayOfMonth ->
                calendar.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                f.invoke(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            show()
        }
    }

    private fun setNumbers() {
        numbers.add(1)
        numbers.add(8)
        numbers.add(2)
        numbers.add(6)
        numbers.add(5)
        numbers.add(3)
        numbers.add(5)
        numbers.add(0)
    }

    private fun getAnswer3() {
        var maxA = Integer.MIN_VALUE
        var maxB = Integer.MIN_VALUE
        numbers.forEach {
            if (it > maxA) {
                maxB = maxA
                maxA = it
            } else if (it > maxB) {
                maxB = it
            }
        }
        tvResult3.text = "$maxA, $maxB"
    }

    private fun getAnswer4() {
        val userId = etUserId.text.toString()
        if(userId.isNotEmpty()){
            mainViewModel.requestBookService(etUserId.text.toString())
        }
    }

    private fun updateResult4(books: MutableList<BookModel>?) {

    }

    fun Date.format(format: String): String? {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return try {
            sdf.format(this)
        } catch (e: Exception) {
            return null
        }
    }

    companion object {
        val KEY_START_DATE_1 = "start_date_1"
        val KEY_END_DATE_1 = "end_date_1"
        val KEY_START_DATE_2 = "start_date_2"
        val KEY_END_DATE_2 = "end_date_2"
    }
}