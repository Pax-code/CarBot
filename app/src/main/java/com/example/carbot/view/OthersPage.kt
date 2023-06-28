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
import com.example.carbot.databinding.FragmentOthersPageBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class OthersPage : Fragment() {

    private var _binding: FragmentOthersPageBinding? = null
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
        _binding = FragmentOthersPageBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Room.databaseBuilder(requireContext(),DataBase::class.java,"DataBase").build()
        dao = db.dbdao()

        buttons()
    }


    private fun buttons(){
        binding.othersPageMinusPaper.setOnClickListener{
            buttonsMinusFun(binding.paperText)
        }
        binding.othersPagePlusPaper.setOnClickListener{
            buttonsPlusFun(binding.paperText)
        }



        binding.othersPageMinusPlastic.setOnClickListener{
            buttonsMinusFun(binding.plasticText)
        }
        binding.othersPagePlusPlastic.setOnClickListener{
            buttonsPlusFun(binding.plasticText)
        }



        binding.othersPageMinusMetal.setOnClickListener{
            buttonsMinusFun(binding.metalText)
        }
        binding.othersPagePlusMetal.setOnClickListener{
            buttonsPlusFun(binding.metalText)
        }



        binding.othersPageMinusGlass.setOnClickListener{
            buttonsMinusFun(binding.glassText)

        }
        binding.othersPagePlusGlass.setOnClickListener{
            buttonsPlusFun(binding.glassText)
        }



        binding.othersPageMinusBattery.setOnClickListener{
           buttonsMinusFun(binding.batteryText)

        }
        binding.othersPagePlusBattery.setOnClickListener{
           buttonsPlusFun(binding.batteryText)
        }

        binding.othersSave.setOnClickListener{
            gettingTextForSave(binding.glassText,"Glass")
            gettingTextForSave(binding.plasticText,"Plastic")
            gettingTextForSave(binding.batteryText,"Battery")
            gettingTextForSave(binding.paperText,"Paper")
            gettingTextForSave(binding.metalText,"Metal")

        }

    }

    private fun buttonsMinusFun(editText: EditText){

            val valueWithUnit = editText.text.toString()
            val valueString = valueWithUnit.substringBefore(" Kg")
            var value = valueString.toIntOrNull() ?: 0
            value--
            if (value >= 0) {
                editText.setText(value.toString() + " Kg")
            }else{
                Toast.makeText(requireContext(), "Usage can not be lower than 0", Toast.LENGTH_SHORT).show()
            }
    }

    private fun buttonsPlusFun(editText: EditText){
            val valueWithUnit = editText.text.toString()
            val valueString = valueWithUnit.substringBefore(" Kg")
            var value = valueString.toIntOrNull() ?: 0
            value++
            editText.setText(value.toString() + " Kg")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}