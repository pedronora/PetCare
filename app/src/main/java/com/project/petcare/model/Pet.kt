package com.project.petcare.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Pet (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome : String,
    val dtNasc : String,
    val fileName : String?,
    val tutorId: String
) : Parcelable