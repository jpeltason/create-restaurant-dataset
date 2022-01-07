package com.example.ai_speech_compose

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.MutableState
import java.util.*

class TextToSpeechFactory: TextToSpeech.OnInitListener {
    var isTtsEnabled:Boolean = false
    var tts:TextToSpeech? = null


    fun create(context: Context) {
        tts = TextToSpeech(context, this)
    }

    fun sendTextToTts(text: MutableState<String>) {
        var inputText = text.value
        tts!!.speak(inputText, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set GERMAN as language for tts
            val result = tts!!.setLanguage(Locale.GERMAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                isTtsEnabled = true
            }
        } else {
            Log.e("TTS", "Initilization Failed! Status: "+ status)
        }
    }

}