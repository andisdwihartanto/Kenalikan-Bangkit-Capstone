package com.dicoding.kenalikan.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.kenalikan.R
import com.dicoding.kenalikan.data.DescriptionRent
import com.dicoding.kenalikan.data.ListRentAdapter
import com.dicoding.kenalikan.data.Rent

class RentFragment : Fragment() {
    private lateinit var sewaKapal: RecyclerView
    private val list = ArrayList<Rent>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rent, container, false)
        sewaKapal = view.findViewById(R.id.sewaKapal)
        sewaKapal.setHasFixedSize(true)

        list.addAll(getListRent())
        showRecyclerList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun getListRent(): ArrayList<Rent> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listRent = ArrayList<Rent>()
        for (i in dataName.indices) {
            val rent = Rent(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listRent.add(rent)
        }
        dataPhoto.recycle()
        return listRent
    }

    private fun showSelectedRent(rent: Rent) {
        Toast.makeText(requireContext(), "Membuka Deskripsi " + rent.name, Toast.LENGTH_SHORT).show()
    }

    private fun showRecyclerList() {
        sewaKapal.layoutManager = LinearLayoutManager(requireContext())
        val listRentAdapter = ListRentAdapter(list)
        sewaKapal.adapter = listRentAdapter

        listRentAdapter.setOnItemClickCallback(object : ListRentAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Rent) {
                val moveWithDataIntent = Intent(requireContext(), DescriptionRent::class.java)
                moveWithDataIntent.putExtra(DescriptionRent.EXTRA_GAMBAR, data.photo)
                moveWithDataIntent.putExtra(DescriptionRent.EXTRA_NAMARENT, data.name)
                moveWithDataIntent.putExtra(DescriptionRent.EXTRA_ISIDESC, data.description)

                startActivity(moveWithDataIntent)
                showSelectedRent(data)
            }
        })
    }
}