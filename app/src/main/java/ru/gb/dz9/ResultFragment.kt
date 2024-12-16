package ru.gb.dz9

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.gb.dz9.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private val args: ResultFragmentArgs by navArgs()
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.result.text = args.number.toString() + "/3"

        ObjectAnimator.ofArgb(
            binding.resultTitle, "textColor",
            Color.BLUE,
            Color.RED
        ).apply {
            duration = 2000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        (AnimatorInflater.loadAnimator(this.context, R.animator.custom_animation) as AnimatorSet).apply {
            setTarget(binding.result)
        }.start()

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_quizFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}