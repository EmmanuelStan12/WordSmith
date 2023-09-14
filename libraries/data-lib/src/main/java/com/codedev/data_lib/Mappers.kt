package com.codedev.data_lib

import com.codedev.data_lib.models.Definition
import com.codedev.data_lib.models.History
import com.codedev.data_lib.models.Meaning
import com.codedev.data_lib.models.Phonetic
import com.codedev.data_lib.models.Word
import com.codedev.room_lib.models.DefinitionEntity
import com.codedev.room_lib.models.HistoryEntity
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.PhoneticEntity
import com.codedev.room_lib.models.WordEntity
import java.util.Date

fun MeaningEntity.toMeaning() = Meaning(
    meaning_id, part_of_speech, meaning_antonyms, meaning_synonyms
)

fun WordEntity.toWord() = Word(
    name, phonetic, license, origin, source_urls, is_saved, audio
)

fun Word.toWordEntity() = WordEntity(
    name, phonetic, license, origin, source_urls, is_saved, audio
)

fun DefinitionEntity.toDefinition() = Definition(
    definition_id, definition_example, definition_antonyms, definition_synonyms, definition, meaning_id
)

fun PhoneticEntity.toPhonetic() = Phonetic(
    phonetic_id, text, audio, word, source_url
)

fun History.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(word = word.name, date)
}

fun String.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        word = this,
        date = Date()
    )
}