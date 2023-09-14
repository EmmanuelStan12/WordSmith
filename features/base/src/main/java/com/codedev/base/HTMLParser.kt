package com.codedev.base

import com.codedev.data_lib.models.Definition
import com.codedev.data_lib.models.Meaning
import com.codedev.data_lib.models.Phonetic
import com.codedev.data_lib.models.Word
import java.util.Locale

object HTMLParser {

    private fun createDefinition(
        definition: Definition,
        word: String
    ): String {
        val content = """
                ${"<p>\t●  ${definition.definition}</p>"}
                ${
                    if (definition.definition_synonyms.isNotBlank()) {
                        "\t<p>Synonyms: ${definition.definition_synonyms}</p>"
                    } else ""
                }
                ${
                    if (definition.definition_antonyms.isNotBlank()) {
                        "\t<p>Antonyms: ${definition.definition_antonyms}</p>"
                    } else ""
                }
                ${
                    if (definition.definition_example.isNotBlank()) {
                        "\t${
                            definition.definition_example.split(",").joinToString("") {
                                createExample(it, word)
                            }
                        }"
                    } else ""
                }
            </li>
        """.trimMargin()

        return content
    }

    fun createWord(word: Word): String {
        val content = """
            <h1>${word.name}</h1>
            <p><em>${word.phonetic}</em></p>
            ${createPronunciation(word.phonetics ?: emptyList())}
            ${word.meanings?.map { 
                createMeaning(it, word.name)
            }}
        """.trimIndent()

        return content
    }

    private fun createExample(example: String, word: String): String {
        val content = """
            ${"<p>\t\t${example.replace(word, "<b>${word}</b>")}</p>"}
        """.trimIndent()

        return content
    }

    private fun createMeaning(
        meaning: Meaning,
        word: String
    ): String {
        val content = """
            <h3>${meaning.part_of_speech.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }}</h3>
            <p><em>${word}</em></p>
            ${
                meaning.definitions?.joinToString("") {
                    createDefinition(it, word)
                } ?: ""
            }
            ${
                if (meaning.meaning_synonyms.isNotBlank()) {
                    "\t<p>Synonyms: ${meaning.meaning_synonyms}</p>"
                } else ""
            }
            ${
                if (meaning.meaning_antonyms.isNotBlank()) {
                    "\t<p>Antonyms: ${meaning.meaning_antonyms}</p>"
                } else ""
            }
        """.trimIndent()

        return content
    }

    fun createPronunciation(phonetics: List<Phonetic>): String {
        val phoneticContent = phonetics.joinToString("") {
            "\t●  ${it.text}"
        }

        return """
            <h6>Phonetics</h6>
                $phoneticContent
        """.trimIndent()
    }

    fun createAboutMe(): String {
        return """
                <br>
                <h1>Developer Spotlight: Emmanuel Amadiegwu</h1>

                <p>Meet Emmanuel Amadiegwu, a talented and passionate software developer who has recently completed an impressive dictionary app. With a profound love for technology and a commitment to creating user-friendly applications, Emmanuel is making his mark in the world of mobile app development.</p>

                <h2>The Journey Begins</h2>

                <p>Emmanuel's journey in the world of software development started with a fascination for solving real-world problems through technology. He was drawn to the endless possibilities offered by mobile app development, particularly on the Android platform. Armed with a strong foundation in programming and a thirst for knowledge, he embarked on his mission to create a dictionary app that would redefine the way people explore language.</p>

                <h2>The Dictionary App</h2>

                <p>Emmanuel's dictionary app is a testament to his dedication and technical prowess. This app offers users a powerful tool to explore the vast world of words, meanings, and translations. Whether you're a student, a writer, or simply someone who loves language, this app is designed to enrich your vocabulary and enhance your communication skills.</p>

                <h3>Key Features</h3>

                <div>
                    <p><strong>Comprehensive Database:</strong> The app houses an extensive database of words and their meanings, ensuring that users have access to a wide array of vocabulary.</p>
                    <p><strong>User-Friendly Interface:</strong> The app's intuitive and user-friendly interface makes it easy for anyone to look up words, discover their definitions, and explore synonyms and antonyms effortlessly.</p>
                    <p><strong>Offline Access:</strong> To cater to users without constant internet access, the app allows for offline use, ensuring that language knowledge is available anytime, anywhere.</p>
                    <p><strong>Random Word:</strong> Emmanuel understands the importance of continuous learning. That's why he incorporated a "Random Word" feature, allowing users to expand their vocabulary one word at a time.</p>
                </div>

                <h2>Emmanuel's Vision</h2>

                <p>Emmanuel Amadiegwu envisions a future where his dictionary app becomes an indispensable tool for language enthusiasts and learners worldwide. He is committed to regularly updating and improving the app to provide the best user experience possible.</p>

                <h2>Join the Journey</h2>

                <p>Emmanuel's dictionary app is more than just a digital lexicon; it's a testament to the power of innovation and determination. Join Emmanuel on his journey to redefine language learning and communication.</p>
        """.trimIndent()
    }
}