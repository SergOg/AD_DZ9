package ru.gb.dz9

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import ru.gb.dz9.databinding.FragmentWelcomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val calendar = Calendar.getInstance()
        val date = calendar.time

        binding.birthDay.setOnClickListener {
            val constraints = CalendarConstraints.Builder()
                .setOpenAt(calendar.timeInMillis)
                .build()
            val dateDialog = MaterialDatePicker.Builder.datePicker()
//                .setSelection(calendar.timeInMillis)
                .setCalendarConstraints(constraints)
                .setTitleText(resources.getString(R.string.date_of_birth))
                .build()
            dateDialog.addOnPositiveButtonClickListener { timeInMillis ->
                calendar.timeInMillis = timeInMillis
                Snackbar.make(
                    binding.birthDay,
                    dateFormat.format(calendar.timeInMillis),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            dateDialog.show(parentFragmentManager, "Date")
        }

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_quizFragment)
        }

        binding.button.animate().apply {
            duration = 3000
            translationZ(16f)
        }.start()

        val textView = binding.greetingsText
        val textShader = LinearGradient(
            0f, 0f,
            textView.paint.measureText(textView.text.toString()),
            textView.textSize,
            intArrayOf(Color.MAGENTA, Color.MAGENTA, Color.MAGENTA),
            null,
            Shader.TileMode.CLAMP
        )
        textView.paint.shader = textShader
        textView.invalidate()

        ValueAnimator.ofObject(
            GradientArgbEvaluator,
            intArrayOf(Color.MAGENTA, Color.MAGENTA, Color.MAGENTA),
            intArrayOf(Color.MAGENTA, Color.MAGENTA, Color.BLUE),
            intArrayOf(Color.MAGENTA, Color.BLUE, Color.BLACK),
            intArrayOf(Color.BLUE, Color.BLACK, Color.RED),
            intArrayOf(Color.BLACK, Color.RED, Color.GREEN),
            intArrayOf(Color.RED, Color.GREEN, Color.BLUE),
            intArrayOf(Color.GREEN, Color.BLUE, Color.CYAN),
            intArrayOf(Color.BLUE, Color.CYAN, Color.YELLOW),
            intArrayOf(Color.CYAN, Color.YELLOW, Color.MAGENTA),
        ).apply {
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            duration = 3000
            addUpdateListener {
                val shader = LinearGradient(
                    0f, 0f,
                    textView.paint.measureText(textView.text.toString()),
                    textView.textSize,
                    it.animatedValue as IntArray,
                    null,
                    Shader.TileMode.CLAMP
                )
                textView.paint.shader = shader
                textView.invalidate()
            }
            start()
        }
    }

    object GradientArgbEvaluator : TypeEvaluator<IntArray> {
        private val argbEvaluator = ArgbEvaluator()
        override fun evaluate(fraction: Float, startValue: IntArray, endValue: IntArray): IntArray {
            return startValue.mapIndexed { index, item ->
                argbEvaluator.evaluate(fraction, item, endValue[index]) as Int
            }.toIntArray()
        }
    }
}