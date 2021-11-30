package com.prem.cakeshop.presentation.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.prem.cakeshop.R
import com.prem.cakeshop.domain.model.CakeInfo
import com.prem.cakeshop.presentation.adapters.CakeListAdapter
import com.prem.cakeshop.presentation.viewmodels.CakeInfoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class CakesListActivity : AppCompatActivity(), CakeListAdapter.OnItemClickListener {

    private lateinit var alertDialog: AlertDialog
    private lateinit var descriptionDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cakes_list)

        val cakesListViewModel: CakeInfoListViewModel by viewModels()
        val recyclerview = findViewById<RecyclerView>(R.id.cakesListRecyclerView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val adapter = CakeListAdapter(this)
        alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.error_dialog_title)
            .setCancelable(false)
            .setPositiveButton(R.string.error_dialog_retry) { _, _ ->
                cakesListViewModel.retryFetchingCakeList()
            }
            .create()

        descriptionDialog = AlertDialog.Builder(this)
            .setTitle(R.string.description_dialog_title)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .create()


        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

        cakesListViewModel.cakes.observe(this, {
            if (it!!.isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
            adapter.setData(it.cakesList)
            adapter.notifyDataSetChanged()
        })

        lifecycleScope.launchWhenStarted {
            cakesListViewModel.uiEvent.collectLatest {
                when (it) {
                    is CakeInfoListViewModel.UIEvent.ShowErrorDialog -> {
                        alertDialog.setMessage(it.message)
                        alertDialog.show()
                    }
                }
            }
        }

        floatingActionButton.setOnClickListener { cakesListViewModel.refreshCakeList() }
    }

    /**
     * onPause or onDestroy we have to dismiss the dialog if showing.
     * some time we might get window leaks for this reason
     * TODO: Need to polish this behaviour. More elegant way to handle dialogs.Would consider creating one custom dialog and use it.
     */
    override fun onPause() {
        super.onPause()
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        }
        if (descriptionDialog.isShowing) {
            descriptionDialog.dismiss()
        }
    }

    override fun onItemClick(item: CakeInfo) {
        if (descriptionDialog.isShowing) {
            descriptionDialog.dismiss()
        }
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        }
        descriptionDialog.setMessage(item.desc)
        descriptionDialog.show()
    }
}