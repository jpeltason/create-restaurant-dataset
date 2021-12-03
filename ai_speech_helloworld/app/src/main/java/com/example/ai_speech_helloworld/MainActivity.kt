package com.example.ai_speech_helloworld
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*


class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener, RecognitionListener {

    private val permission = 100
    private lateinit var speechRecognizer: SpeechRecognizer
    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var buttonListen: Button? = null
    private var editText: EditText? = null
    private var textViewRecognizedText : TextView? = null
    private lateinit var recognizerIntent: Intent
    private var recognitionListener: RecognitionListener? = null //Must have this
    //var singleResult = true
    private val MY_PERMISSIONS_RECORD_AUDIO = 1
    private val MY_PERMISSIONS_INTERNET = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSpeak = findViewById(R.id.button_speak)
        buttonListen = findViewById(R.id.button_listen)
        editText = findViewById(R.id.edittext_input)
        textViewRecognizedText = findViewById((R.id.textView_recognizedText))

        tts = TextToSpeech(this, this)

        buttonSpeak!!.isEnabled = false
        buttonSpeak!!.setOnClickListener { sendTextToTts() }
        buttonListen!!.setOnClickListener { startListening() }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(this)
        Log.e("", "isRecognitionAvailable: " +SpeechRecognizer.isRecognitionAvailable(this))
        //buttonListen!!.isEnabled = SpeechRecognizer.isRecognitionAvailable(this)

        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        val languagePref = "de-DE" //or, whatever iso code...
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePref);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languagePref);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, languagePref)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)

        requestAudioPermissions()
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.GERMAN)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }



    }
    private fun startListening() {
        Log.e("", "startListening")
        speechRecognizer.startListening(recognizerIntent)
    }

    private fun sendTextToTts() {
        val text = editText!!.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    override fun onStop() {
        if (tts != null) {
            tts!!.stop()
        }
        super.onStop()
    }

    private fun requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECORD_AUDIO) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET)
            ) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG)
                    .show()

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.RECORD_AUDIO),
                    MY_PERMISSIONS_RECORD_AUDIO
                )
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.INTERNET),
                    MY_PERMISSIONS_INTERNET
                )
                Log.i("", "request permission")
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.RECORD_AUDIO),
                    MY_PERMISSIONS_RECORD_AUDIO
                )
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.INTERNET),
                    MY_PERMISSIONS_INTERNET
                )
                Log.i("", "show user dialogue")
            }
        } else if ((ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)
        )
        {

            Log.i("", "go ahead with recording audio")
            //Go ahead with recording audio now

        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permission -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager
                    .PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Permission granted!",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Permission Denied!",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    //SPEECH RECOGNITION CALLBACKS
    override fun onReadyForSpeech(params: Bundle?) {
        Log.i("", "readyForSpeech")
    }

    override fun onBeginningOfSpeech() {
        Log.i("", "onBeginningOfSpeech")
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.i("", "onRmsChanged")
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
    }

    override fun onResults(results: Bundle?) {
        //if (singleResult) {
            Log.i("", "onResults - singleResult=true")
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
                    textViewRecognizedText?.text = text
                }
            }
            Log.i("", "onResults - singleResult=false")
            // next result will be ignored
            //singleResult = false
            speechRecognizer.stopListening()
        //}
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val data = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val unstableData = partialResults?.getStringArrayList("android.speech.extra.UNSTABLE_TEXT")
        var mResult = data!![0] + unstableData!![0]
        Log.i("logTag", "onPartialResults" + mResult)
        textViewRecognizedText?.text = mResult
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.i("", "onEvent")
    }

    override fun onDestroy() {
        if (tts != null) {
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}