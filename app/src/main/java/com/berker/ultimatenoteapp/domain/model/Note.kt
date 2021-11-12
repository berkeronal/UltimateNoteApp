package com.berker.ultimatenoteapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         12.11.2021          | |
//| |_____________________________| |
//|_________________________________|
@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val description: String,
    val imageUrl: String,
    val createdDate: Date,
    val editHistory: List<Date>,
    val color: Int,
)