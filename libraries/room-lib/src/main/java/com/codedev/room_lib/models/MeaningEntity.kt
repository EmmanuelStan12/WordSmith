package com.codedev.room_lib.models

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "meaning")
data class MeaningEntity(
    @PrimaryKey(autoGenerate = true)
    val meaning_id: Int? = null,
    val part_of_speech: String,
    val meaning_antonyms: String,
    val meaning_synonyms: String,
    val word_id: Int? = null
) {

    companion object {
        fun parseMeaningCSV(line: String): MeaningEntity {
            val fields = line.split(",")

            return MeaningEntity(
                meaning_id = fields[0].toIntOrNull(),
                part_of_speech = fields[1],
                meaning_antonyms = fields[2],
                meaning_synonyms = fields[3],
                word_id = fields[4].toIntOrNull(),
            )
        }
    }

}
