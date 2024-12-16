package ru.gb.dz9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.gb.dz9.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.alpha = 0f
        binding.title.animate().apply {
            duration = 2000
            alpha(1f)
            translationZ(8f)
        }.start()

        binding.buttonSend.setOnClickListener {
            val number = getAnswersByUser()
            val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(number)
            findNavController().navigate(action)
        }

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_welcomeFragment)
        }
    }

    private fun getAnswersByUser(): Int {
        var correctAnswersCount = 0
        if (binding.question1.checkedRadioButtonId == binding.answer12.id) correctAnswersCount++
        if (binding.question2.checkedRadioButtonId == binding.answer21.id) correctAnswersCount++
        if (binding.question3.checkedRadioButtonId == binding.answer31.id) correctAnswersCount++
        return correctAnswersCount
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}