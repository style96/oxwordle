package com.kodlamayabasla.wordle_oxford.backend.repository

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.kodlamayabasla.wordle_oxford.WordleOxfordGame
import java.io.InputStream
import java.io.OutputStream

object GameSerializer : Serializer<WordleOxfordGame> {
    override val defaultValue: WordleOxfordGame = WordleOxfordGame.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): WordleOxfordGame {
        try {
            return WordleOxfordGame.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }
    override suspend fun writeTo(t: WordleOxfordGame, output: OutputStream)  = t.writeTo(output)
}