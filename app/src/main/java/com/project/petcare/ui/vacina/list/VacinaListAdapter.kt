package com.project.petcare.ui.vacina.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.petcare.R
import com.project.petcare.databinding.VacinaCardviewBinding
import com.project.petcare.model.Vacina

class VacinaListAdapter :
    androidx.recyclerview.widget.ListAdapter<Vacina, VacinaListAdapter.VacinaViewHolder>(
        Diffcallback()
    ) {

    var listenerDetail: (Vacina) -> Unit = {}
    var listenerUpdate: (Vacina) -> Unit = {}
    var listenerDelete: (Vacina) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacinaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VacinaCardviewBinding.inflate(inflater, parent, false)
        return VacinaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacinaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VacinaViewHolder(private val binding: VacinaCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vacina: Vacina) {
            binding.tvIdNumber.text = vacina.id.toString()
            binding.tvNomeVacina.text = vacina.nomeVacina
            binding.tvDataAplicacao.text = vacina.dataAplicacao
            binding.tvProximaDose.text = vacina.proximaAplicacao
            binding.ivMore.setOnClickListener { showMenu(vacina) }
            binding.vacinaCardview.setOnClickListener { listenerDetail(vacina) }
        }

        private fun showMenu(vacina: Vacina) {
            val ivMore = binding.ivMore
            val popUpMenu = PopupMenu(ivMore.context, ivMore)
            popUpMenu.menuInflater.inflate(R.menu.item_menu, popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerUpdate(vacina)
                    R.id.action_delete -> listenerDelete(vacina)
                }
                return@setOnMenuItemClickListener true
            }
            popUpMenu.show()
        }
    }
}

class Diffcallback : DiffUtil.ItemCallback<Vacina>() {
    override fun areItemsTheSame(oldItem: Vacina, newItem: Vacina): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Vacina, newItem: Vacina): Boolean {
        if (oldItem.id != newItem.id) return false
        if (oldItem.nomeVacina != newItem.nomeVacina) return false
        if (oldItem.dataAplicacao != newItem.dataAplicacao) return false
        if (oldItem.proximaAplicacao != newItem.proximaAplicacao) return false
        if (oldItem.petId != newItem.petId) return false

        return true
    }

}
