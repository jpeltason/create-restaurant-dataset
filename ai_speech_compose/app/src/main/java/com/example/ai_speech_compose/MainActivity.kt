package com.example.ai_speech_compose

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.*

class MainActivity : ComponentActivity(){
//    private var tts: TextToSpeech? = null

    private lateinit var ttsFactory: TextToSpeechFactory
    private lateinit var sttFactory: SpeechToTextFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ttsFactory = TextToSpeechFactory()
        ttsFactory.create(this@MainActivity)

        sttFactory = SpeechToTextFactory()
        sttFactory.create(this@MainActivity)

        setContent {
            Column{
                speakSection()
                Spacer(modifier = Modifier.size(30.dp))
                listenSection()
            }

        }
    }

//    @Composable
//    fun Greeting(name: String) {
//        Text(text = "Hello $name!")
//    }

//    @Preview(showBackground = true)
//    @Composable
//    fun DefaultPreview() {
//        AispeechcomposeTheme {
//
//        }
//    }

    @Composable
    fun speakSection () {
        val textInput = remember { mutableStateOf("") }
        Row (modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Blue)){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(4.dp, Color.Red),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier.size(300.dp, 100.dp),
                    value = textInput.value,
                    onValueChange = {
                        textInput.value = it
                    },
                    label = { Text("Bitte Text eingeben") }
                )
                Button(
                    modifier = Modifier
                        .size(300.dp, 60.dp)
                        .padding(0.dp, 5.dp),
                    onClick = { ttsFactory.sendTextToTts(textInput) },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.DarkGray
                    ),
                    enabled = ttsFactory.isTtsEnabled
                ) {
                    Text(
                        "Speak",
                        color = Color.White
                    )
                }
            }
        }
    }

    @Composable
    fun listenSection () {
        val textOutput = remember { mutableStateOf("") }
        Row (modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Blue)){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(4.dp, Color.Red),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.size(300.dp, 100.dp),
                    text = textOutput.value
                )
                Button(
                    modifier = Modifier
                        .size(300.dp, 60.dp)
                        .padding(0.dp, 5.dp),
                    onClick = { sttFactory.startListening()},
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.DarkGray
                    ),
                    //enabled = stt.isSttEnabled
                ) {
                    Text(
                        "Listen",
                        color = Color.White
                    )
                }
            }
        }
    }


    override fun onDestroy() {
        if (ttsFactory.tts != null) ttsFactory.tts!!.shutdown()
        if(sttFactory.stt != null) sttFactory.stt.destroy()
        super.onDestroy()
    }
}

