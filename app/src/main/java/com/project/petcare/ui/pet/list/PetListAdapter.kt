package com.project.petcare.ui.pet.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.petcare.R
import com.project.petcare.databinding.PetCardviewBinding
import com.project.petcare.model.Pet

class PetListAdapter : ListAdapter<Pet, PetListAdapter.PetViewHolder>(DiffCallback()) {

    var listenerVaccine: (Pet) -> Unit = {}
    var listenerEdit: (Pet) -> Unit = {}
    var listenerDelete: (Pet) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PetCardviewBinding.inflate(inflater, parent, false)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PetViewHolder(private val binding: PetCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pet: Pet) {
            binding.ivProfileImage.setImageURI(pet.fileName?.toUri())
            binding.tvIdNumber.text = pet.id.toString()
            binding.tvNome.text = pet.nome
            binding.tvNasc.text = pet.dtNasc
            binding.ivMenuMore.setOnClickListener { showMenu(pet) }
            binding.petCardview.setOnClickListener { listenerVaccine(pet) }
        }


        private fun showMenu(pet: Pet) {
            val ivMenuMore = binding.ivMenuMore
            val popupMenu = PopupMenu(ivMenuMore.context, ivMenuMore)
            popupMenu.menuInflater.inflate(R.menu.item_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerEdit(pet)
                    R.id.action_delete -> listenerDelete(pet)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Pet>() {
    override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        if (oldItem.id != newItem.id) return false
        if (oldItem.nome != newItem.nome) return false
        if (oldItem.dtNasc != newItem.dtNasc) return false

        return true
    }
}
