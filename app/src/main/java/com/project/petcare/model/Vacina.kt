package com.project.petcare.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Vacina(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nomeVacina: String,
    val dataAplicacao: String,
    val proximaAplicacao: String,
    val petId: Int
) : Parcelable
