import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.example.carbot.database.DBDao
import com.example.carbot.database.DataBase
import com.example.carbot.databinding.FragmentStatisticsPageBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class StatisticsPage : Fragment() {
    private var _binding: FragmentStatisticsPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: DataBase
    private lateinit var dao: DBDao

    private val compositeDisposable = CompositeDisposable()

    private var list = ArrayList<PieEntry>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Room.databaseBuilder(requireContext(), DataBase::class.java, "DataBase").build()
        dao = db.dbdao()
        gettingData()
        pieChart()

    }

    private fun pieChart() {




        val piDataSet = PieDataSet(list, "")

        val customColors = intArrayOf(
            Color.parseColor("#E26B6B"),
            Color.parseColor("#EF9F4E"),
            Color.parseColor("#C186AA"),
            Color.parseColor("#82CBB2"),
            Color.parseColor("#9FB0C1"),
            Color.parseColor("#A7D69E"),
            Color.parseColor("#D6CF82"),
            Color.parseColor("#D3A4B7"),
            Color.parseColor("#A6A9C4")
        )

        piDataSet.colors = customColors.toList()

        piDataSet.valueTextSize = 15f
        piDataSet.valueTextColor = Color.BLACK

        val chart = binding.pieChart
        val pieData = PieData(piDataSet)

        chart.data = pieData

        chart.description.text = "CO2e Chart"
        chart.description.textSize = 15f
        chart.description.textColor = Color.WHITE

        chart.centerText = "CO2 Release(Kg)"
        chart.animateY(500)

        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)

        chart.holeRadius = 58f
        chart.transparentCircleRadius = 61f

        chart.legend.textColor = Color.WHITE
        chart.setEntryLabelColor(Color.TRANSPARENT)

        piDataSet.valueLineColor = Color.TRANSPARENT

        chart.legend.orientation = Legend.LegendOrientation.VERTICAL
        chart.setExtraOffsets(5F, 0f, 5F, 30f)
    }


    private fun handleResponseWater(value: Float) {

        val waterValue = value * 0.3f

            list.add(PieEntry(waterValue, "Water"))
            val chart = binding.pieChart
            chart.notifyDataSetChanged()
            chart.invalidate()
    }

    private fun handleResponseElectric(value: Float) {
        val electricValue = value * 0.5f

            list.add(PieEntry(electricValue, "Electric"))
                val chart = binding.pieChart
                chart.notifyDataSetChanged()
                chart.invalidate()

    }

    private fun handleResponsePaper(value: Float) {

        val paperValue = value * 1.7f

            list.add(PieEntry(paperValue, "Paper"))
            val chart = binding.pieChart
            chart.notifyDataSetChanged()
            chart.invalidate()

    }

    private fun handleResponsePlastic(value: Float) {

        val plasticValue = value * 4.1f

            list.add(PieEntry(plasticValue, "Plastic"))
            val chart = binding.pieChart
            chart.notifyDataSetChanged()
            chart.invalidate()

    }

    private fun handleResponseMetal(value: Float) {

        val metalValue = value * 7.5f
            list.add(PieEntry(metalValue, "Metal"))
            val chart = binding.pieChart
            chart.notifyDataSetChanged()

    }

    private fun handleResponseGlass(value: Float) {

        val glassValue = value * 1.5f

            list.add(PieEntry(glassValue, "Glass"))
            val chart = binding.pieChart
            chart.notifyDataSetChanged()
            chart.invalidate()

    }

    private fun handleResponseBattery(value: Float) {

        val batteryValue = value * 1.5f

            list.add(PieEntry(batteryValue, "Battery"))
            val chart = binding.pieChart
            chart.notifyDataSetChanged()
            chart.invalidate()

    }

    private fun handleResponseGas(value: Float) {

        val gasValue = value * 1.7f
            list.add(PieEntry(gasValue, "Gas"))
            val chart = binding.pieChart
            chart.notifyDataSetChanged()
            chart.invalidate()


    }


    private fun handleResponseCoal(value: Float) {

        val coalValue = value * 2.7f
            list.add(PieEntry(coalValue, "Coal"))
            val chart = binding.pieChart
            chart.notifyDataSetChanged()
            chart.invalidate()

    }

    private fun gettingData(){

        compositeDisposable.add(
            dao.getElectricTotalValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseElectric)
        )

        compositeDisposable.add(
            dao.getWaterTotalValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseWater)
        )

        compositeDisposable.add(
            dao.getGlassTotalValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseGlass)
        )

        compositeDisposable.add(
            dao.getMetalTotalValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseMetal)
        )

        compositeDisposable.add(
            dao.getPaperTotalValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponsePaper)
        )

        compositeDisposable.add(
            dao.getBatteryTotalValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseBattery)
        )

        compositeDisposable.add(
            dao.getPlasticTotalValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponsePlastic)
        )

        compositeDisposable.add(
            dao.getGassTotalValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseGas)
        )

        compositeDisposable.add(
            dao.getCoalTotalValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseCoal)
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        compositeDisposable.clear()
    }
}
