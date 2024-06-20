package com.dicoding.kenalikan.ui.fragment

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
import com.dicoding.kenalikan.ui.rentfeat.DescActivity
import com.dicoding.kenalikan.ui.rentfeat.RentAdapter
import com.dicoding.kenalikan.response.RentResponse

class RentFragment : Fragment() {
    private lateinit var sewaKapal: RecyclerView
    private val list = ArrayList<RentResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rent, container, false)
        sewaKapal = view.findViewById(R.id.listRent)
        sewaKapal.setHasFixedSize(true)

        list.addAll(getListRent())
        showRecyclerList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun getListRent(): ArrayList<RentResponse> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listRent = ArrayList<RentResponse>()
        for (i in dataName.indices) {
            val rent = RentResponse(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listRent.add(rent)
        }
        dataPhoto.recycle()
        return listRent
    }

    private fun showSelectedRent(rent: RentResponse) {
        Toast.makeText(requireContext(), "Membuka Deskripsi " + rent.name, Toast.LENGTH_SHORT).show()
    }

    private fun showRecyclerList() {
        sewaKapal.layoutManager = LinearLayoutManager(requireContext())
        val listRentAdapter = RentAdapter(list)
        sewaKapal.adapter = listRentAdapter

        listRentAdapter.setOnItemClickCallback(object : RentAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RentResponse) {
                val moveWithDataIntent = Intent(requireContext(), DescActivity::class.java)
                moveWithDataIntent.putExtra(DescActivity.EXTRA_IMAGE, data.photo)
                moveWithDataIntent.putExtra(DescActivity.EXTRA_PLACE, data.name)
                moveWithDataIntent.putExtra(DescActivity.EXTRA_DESC, data.description)

                startActivity(moveWithDataIntent)
                showSelectedRent(data)
            }
        })
    }
}