package com.example.carbot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.room.Room
import com.example.carbot.database.DBDao
import com.example.carbot.database.DBModel
import com.example.carbot.database.DataBase
import com.example.carbot.databinding.FragmentElectricPageBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class ElectricPage : Fragment() {

    private var _binding: FragmentElectricPageBinding?  = null
    private val binding get() = _binding!!

    private lateinit var db : DataBase
    private lateinit var dao : DBDao

    private val compositeDisposable= CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentElectricPageBinding.inflate(layoutInflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        db = Room.databaseBuilder(requireContext(),DataBase::class.java,"DataBase").build()
        dao = db.dbdao()

        binding.electricPageMinus1.setOnClickListener{

            val valueWithUnit = binding.editTextText.text.toString()
            val valueString = valueWithUnit.substringBefore(" kW/h")
            var value = valueString.toIntOrNull() ?: 0
            value--
            if (value >= 0) {
                binding.editTextText.setText(value.toString() + " kW/h")
            }else{
                Toast.makeText(requireContext(), "Usage can not be lower than 0", Toast.LENGTH_SHORT).show()
            }

        }
        binding.electricPagePlus1.setOnClickListener{
            val valueWithUnit = binding.editTextText.text.toString()
            val valueString = valueWithUnit.substringBefore(" kW/h")
            var value = valueString.toIntOrNull() ?: 0
            value++
            binding.editTextText.setText(value.toString() + " kW/h")
        }

        binding.electricSave.setOnClickListener{
            val valueWithUnit = binding.editTextText.text.toString()
            val valueString = valueWithUnit.substringBefore(" kW/h")
            val value = valueString.toIntOrNull() ?: 0
            saveButton(value.toFloat())
        }



    }

    private fun saveButton(value: Float){


        if (value >= 0) {
            val dbModel = DBModel(value,"Electric")
            compositeDisposable.add(
                dao.insert(dbModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )

        }else{

        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        compositeDisposable.clear()

    }

}