package com.codedev.room_lib.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "word")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val word_id: Int? = null,
    val name: String? = null,
    val phonetic: String,
    val license: String,
    val origin: String,
    val source_urls: String
) {

    companion object {
        fun parseWordCSV(line: String): WordEntity {
            val fields = line.split(",")

            return WordEntity(
                word_id = fields[0].toIntOrNull(),
                phonetic = fields[1],
                origin = fields[2],
                source_urls = fields[3],
                license = fields[4],
                name = fields[5]
            )
        }
    }
}
