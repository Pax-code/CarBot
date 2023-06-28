package com.example.carbot.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.example.carbot.database.DBDao
import com.example.carbot.database.DBModel
import com.example.carbot.database.DataBase
import com.example.carbot.databinding.FragmentGasPageBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class GasPage : Fragment() {

    private var _binding: FragmentGasPageBinding? = null
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
        _binding = FragmentGasPageBinding.inflate(layoutInflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Room.databaseBuilder(requireContext(),DataBase::class.java,"DataBase").build()
        dao = db.dbdao()

        binding.gasPageMinus1.setOnClickListener{
            val valueWithUnit = binding.gasText.text.toString()
            val valueString = valueWithUnit.substringBefore(" m³")
            var value = valueString.toIntOrNull() ?: 0
            value--
            if (value >= 0) {
                binding.gasText.setText(value.toString() + " m³")
            }else{
                Toast.makeText(requireContext(), "Usage can not be lower than 0", Toast.LENGTH_SHORT).show()
            }

        }
        binding.gasPagePlus1.setOnClickListener{
            val valueWithUnit = binding.gasText.text.toString()
            val valueString = valueWithUnit.substringBefore(" m³")
            var value = valueString.toIntOrNull() ?: 0
            value++
            binding.gasText.setText(value.toString() + " m³")
        }


        binding.gasPageMinus2.setOnClickListener{
            val valueWithUnit = binding.editTextCoal.text.toString()
            val valueString = valueWithUnit.substringBefore(" Kg")
            var value = valueString.toIntOrNull() ?: 0
            value--
            if (value >= 0) {
                binding.editTextCoal.setText(value.toString() + " Kg")
            }else{
                Toast.makeText(requireContext(), "Usage can not be lower than 0", Toast.LENGTH_SHORT).show()
            }

        }
        binding.gasPagePlus2.setOnClickListener{
            val valueWithUnit = binding.editTextCoal.text.toString()
            val valueString = valueWithUnit.substringBefore(" Kg")
            var value = valueString.toIntOrNull() ?: 0
            value++
            binding.editTextCoal.setText(value.toString() + " Kg")
        }

        binding.gasSave.setOnClickListener {
            gettingTextForSave(binding.editTextCoal,"Coal")
            gettingTextForSaveGas(binding.gasText,"Gas")
        }


    }

    private fun saveButton(value: Float, text: String){

        if (value >= 0) {
            val dbModel = DBModel(value,text)
            compositeDisposable.add(
                dao.insert(dbModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun gettingTextForSave(editText: EditText, text: String){
        val valueWithUnit = editText.text.toString()
        val valueString = valueWithUnit.substringBefore(" Kg")
        val value = valueString.toIntOrNull() ?: 0
        saveButton(value.toFloat(),text)
    }

    private fun gettingTextForSaveGas(editText: EditText, text: String){
        val valueWithUnit = editText.text.toString()
        val valueString = valueWithUnit.substringBefore(" m³")
        val value = valueString.toIntOrNull() ?: 0
        saveButton(value.toFloat(),text)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}