package com.codedev.data_lib

import com.codedev.data_lib.models.Definition
import com.codedev.data_lib.models.Meaning
import com.codedev.data_lib.models.Phonetic
import com.codedev.data_lib.models.Word
import com.codedev.room_lib.models.DefinitionEntity
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.PhoneticEntity
import com.codedev.room_lib.models.WordEntity

fun MeaningEntity.toMeaning() = Meaning(
    meaning_id, part_of_speech, meaning_antonyms, meaning_synonyms
)

fun WordEntity.toWord() = Word(
    word_id, name, phonetic, license, origin, source_urls
)

fun DefinitionEntity.toDefinition() = Definition(
    definition_id, definition_example, definition_antonyms, definition_synonyms, definition, meaning_id
)

fun PhoneticEntity.toPhonetic() = Phonetic(
    phonetic_id, text, audio, word_id, source_url
)