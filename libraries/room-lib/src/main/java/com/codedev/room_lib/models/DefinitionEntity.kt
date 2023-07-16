package com.codedev.room_lib.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "definition")
data class DefinitionEntity(
    @PrimaryKey(autoGenerate = true)
    val definition_id: Int? = null,
    val definition_example: String,
    val definition_antonyms: String,
    val definition_synonyms: String,
    val definition: String,
    val meaning_id: Int? = null
) {

    companion object {
        fun parseDefinitionCSV(line: String): DefinitionEntity {
            val fields = line.split(",")

            return DefinitionEntity(
                definition_id = fields[0].toIntOrNull(),
                definition_example = fields[1],
                definition_antonyms = fields[2],
                definition_synonyms = fields[3],
                definition = fields[4],
                meaning_id = fields[5].toIntOrNull(),
            )
        }
    }
}
