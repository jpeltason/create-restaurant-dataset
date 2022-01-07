package com.example.ai_speech_compose

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log

class SpeechToTextFactory(): RecognitionListener {
    var isSttEnabled: Boolean = false
    lateinit var stt: SpeechRecognizer
    private lateinit var recognizerIntent: Intent

    fun create(context: Context) {

        stt = SpeechRecognizer.createSpeechRecognizer(context)
        stt.setRecognitionListener(this)
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        setRecognizerIntentLanguage()
        if(::stt.isInitialized) Log.i("", "STT initialized") else Log.i("", "STT not initialized")
    }

    private fun setRecognizerIntentLanguage() {
        val languagePref = "de-DE" //or, whatever iso code...
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePref);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languagePref);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, languagePref)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
    }

    fun startListening() {
        Log.i("", "startListening")
        stt?.startListening(recognizerIntent)

    }

    override fun onReadyForSpeech(params: Bundle?) {
        Log.i("", "readyForSpeech")
    }

    override fun onBeginningOfSpeech() {
        Log.i("", "BeginningOfSpeech")
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.i("", "rmsChanged")
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.i("", "onBufferReceived")
    }

    override fun onEndOfSpeech() {
        Log.i("", "onEndOfSpeech")
    }

    override fun onError(error: Int) {
        var message = ""
        message = when (error) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Didn't understand, please try again."
        }
        Log.e("error", message)
    }

    override fun onResults(results: Bundle?) {
        //if (singleResult) {
        Log.i("", "onResults")
        results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).let {
            results?.toString()?.let { it1 -> Log.i("", "result: " +it1) }
            val matches = results!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            var text = ""
            if (matches != null) {
                for (result in matches)
                {
                    text = """
                                    $result
                                    """.trimIndent()
                }
                //textViewRecognizedText?.text = text //TODO
            }
        }
        // next result will be ignored
        //singleResult = false
        stt.stopListening()
        //}
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val data = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val unstableData = partialResults?.getStringArrayList("android.speech.extra.UNSTABLE_TEXT")
        var mResult = data!![0] + unstableData!![0]
        Log.i("logTag", "onPartialResults" + mResult)
        //textViewRecognizedText?.text = mResult //TODO
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.i("", "onEvent")
    }
}