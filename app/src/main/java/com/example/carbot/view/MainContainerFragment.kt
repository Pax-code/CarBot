package com.example.carbot.view

import StatisticsPage
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.carbot.databinding.FragmentMainContainerBinding
import com.example.carbot.R

class MainContainerFragment : Fragment() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),
        R.anim.rotate_open_animation
    )}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),
        R.anim.rotate_close_animation
    )}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),
        R.anim.from_bottom_animation
    )}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),
        R.anim.to_bottom_animation
    )}
    private var clicked = false


    private var _binding: FragmentMainContainerBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainContainerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        replaceFragment(MainScreen())

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false
        buttons()
    }


    private fun buttons(){

        binding.addFab.setOnClickListener{

            setVisibility(clicked)
            setAnimation(clicked)
            setClickable(clicked)
            clicked = !clicked
        }


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Home -> {
                    replaceFragment(MainScreen())
                    true
                }
                R.id.LeaderBoard -> {
                    replaceFragment(StatisticsPage())
                    true
                }

                else -> {
                    replaceFragment(MainScreen())
                    true
                }
            }
        }
        binding.waterFab.setOnClickListener{
            replaceFragment(WaterPage())
            closeMenu()
        }

        binding.gasFab.setOnClickListener{
            replaceFragment(GasPage())
            closeMenu()

        }

        binding.electiricFab.setOnClickListener {
            replaceFragment(ElectricPage())
            closeMenu()

        }
        binding.othersFab.setOnClickListener {
            replaceFragment(OthersPage())
            closeMenu()

        }

    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            binding.waterFab.startAnimation(fromBottom)
            binding.othersFab.startAnimation(fromBottom)
            binding.gasFab.startAnimation(fromBottom)
            binding.electiricFab.startAnimation(fromBottom)
            binding.addFab.startAnimation(rotateOpen)


        }else{
            binding.waterFab.startAnimation(toBottom)
            binding.othersFab.startAnimation(toBottom)
            binding.gasFab.startAnimation(toBottom)
            binding.electiricFab.startAnimation(toBottom)
            binding.addFab.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {

        if (!clicked){
            binding.waterFab.visibility = View.VISIBLE
            binding.electiricFab.visibility = View.VISIBLE
            binding.othersFab.visibility = View.VISIBLE
            binding.gasFab.visibility = View.VISIBLE
        }else{
            binding.waterFab.visibility = View.GONE
            binding.electiricFab.visibility = View.GONE
            binding.othersFab.visibility = View.GONE
            binding.gasFab.visibility = View.GONE
        }

    }

    private fun setClickable(clicked: Boolean){
        if (!clicked){
            binding.waterFab.isClickable = true
            binding.electiricFab.isClickable = true
            binding.othersFab.isClickable = true
            binding.gasFab.isClickable = true
        }else{
            binding.waterFab.isClickable = false
            binding.electiricFab.isClickable = false
            binding.othersFab.isClickable = false
            binding.gasFab.isClickable = false
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_main,fragment)
        fragmentTransaction.commit()
    }

    private fun closeMenu() {
        setAnimation(true)
        setClickable(true)
        setVisibility(true)
        clicked = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}