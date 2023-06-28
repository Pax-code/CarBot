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
import com.example.carbot.databinding.FragmentWaterPageBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class WaterPage : Fragment() {

    private var _binding: FragmentWaterPageBinding? = null
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
        _binding = FragmentWaterPageBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Room.databaseBuilder(requireContext(),DataBase::class.java,"DataBase").build()
        dao = db.dbdao()

        binding.waterPageMinus1.setOnClickListener{
            val valueWithUnit = binding.editTextText.text.toString()
            val valueString = valueWithUnit.substringBefore(" m³")
            var value = valueString.toIntOrNull() ?: 0
            value--
            if (value >= 0) {
                binding.editTextText.setText(value.toString() + " m³")
            }else{
                Toast.makeText(requireContext(), "Usage can not be lower than 0", Toast.LENGTH_SHORT).show()
            }

        }
        binding.waterPagePlus1.setOnClickListener{
            val valueWithUnit = binding.editTextText.text.toString()
            val valueString = valueWithUnit.substringBefore(" m³")
            var value = valueString.toIntOrNull() ?: 0
            value++
            binding.editTextText.setText(value.toString() + " m³")
        }

        binding.waterSave.setOnClickListener {
            val valueWithUnit = binding.editTextText.text.toString()
            val valueString = valueWithUnit.substringBefore(" m³")
            val value = valueString.toIntOrNull() ?: 0
            saveButton(value.toFloat())
        }

    }

    private fun saveButton(value: Float){


        if (value >= 0) {
            val dbModel = DBModel(value,"Water")
            compositeDisposable.add(
                dao.insert(dbModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )

        }else{
            val dbModel = DBModel(0f,"Water")
            compositeDisposable.add(
                dao.insert(dbModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}