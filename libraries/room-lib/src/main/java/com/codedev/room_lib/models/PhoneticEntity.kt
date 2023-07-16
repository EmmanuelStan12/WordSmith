package com.codedev.room_lib.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phonetic")
data class PhoneticEntity(
    @PrimaryKey(autoGenerate = true)
    val phonetic_id: Int? = null,
    val text: String,
    val audio: String,
    val word_id: Int? = null,
    val source_url: String
) {

    companion object {
        fun parsePhoneticCSV(line: String): PhoneticEntity {
            val fields = line.split(",")

            return PhoneticEntity(
                phonetic_id = fields[0].toIntOrNull(),
                text = fields[1],
                audio = fields[2],
                source_url = fields[3],
                word_id = fields[4].toIntOrNull(),
            )
        }
    }

}
