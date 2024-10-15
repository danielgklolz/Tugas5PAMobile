import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var recyclerView: RecyclerView
    private val countriesList = mutableListOf<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadCountries()

        val btnAddCountry: Button = findViewById(R.id.btnAddCountry)
        btnAddCountry.setOnClickListener {
            // Menambahkan negara baru (contoh)
            val newCountry = "Country ${countriesList.size + 1}"
            dbHelper.addCountry(newCountry)
            loadCountries() // refresh data
        }
    }

    private fun loadCountries() {
        countriesList.clear()
        countriesList.addAll(dbHelper.getAllCountries())
        countryAdapter = CountryAdapter(countriesList)
        recyclerView.adapter = countryAdapter
    }
}