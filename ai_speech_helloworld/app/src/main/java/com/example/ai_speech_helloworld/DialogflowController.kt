
package com.example.ai_speech_helloworld
/*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bae.dialogflowbot.helpers.SendMessageInBackground
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.*
import com.google.common.collect.Lists
import interfaces.DialogflowAgentReply
import java.io.FileInputStream
import java.util.*


class DialogflowController: AppCompatActivity(), DialogflowAgentReply{

    //dialogFlow
    private var sessionsClient: SessionsClient? = null
    private var sessionName: SessionName? = null
    private val uuid: String = UUID.randomUUID().toString()
    private val TAG = "mainactivity"
    private var projectId: String = ""

    private fun setupDialogflow() {
        try {
            val credentials: GoogleCredentials = GoogleCredentials.fromStream(FileInputStream("/res/raw/credential.json"))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"))
            projectId = (credentials as ServiceAccountCredentials).projectId
            val settingsBuilder = SessionsSettings.newBuilder()
            val sessionsSettings = settingsBuilder.setCredentialsProvider(
                FixedCredentialsProvider.create(credentials)
            ).build()
            sessionsClient = SessionsClient.create(sessionsSettings)
            sessionName = SessionName.of(projectId, uuid)
            Log.d(TAG, "projectId : $projectId")
        } catch (e: Exception) {
            Log.d(TAG, "setUpBot: " + e.message)
        }
    }

    private fun sendMessageToDialogflowAgent(message: String) {

    val response = callDialogFlow(projectId, uuid, "Das ist ein Test")

    }

    override fun callback(returnResponse: DetectIntentResponse?) {
        if (returnResponse != null) {
            val dialogflowAgentReply = returnResponse.queryResult.fulfillmentText
            if (!dialogflowAgentReply.isEmpty()) {
                //messageList.add(Message(botReply, true))
                //chatAdapter.notifyDataSetChanged()
                //Objects.requireNonNull(chatView.getLayoutManager()).scrollToPosition(messageList.size() - 1)
            } else {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "failed to connect!", Toast.LENGTH_SHORT).show()
        }
    }

    @Throws(Exception::class)
    fun callDialogFlow(projectId: String, sessionId: String, message: String): String {
        // Instantiates a client
        SessionsClient.create() { sessionsClient ->
            // Set the session name using the sessionId and projectID
            val session = SessionName.of(projectId, sessionId)

            // Set the text and language code (en-US) for the query
            val textInput = TextInput.newBuilder().setText(message).setLanguageCode("en")

            // Build the query with the TextInput
            val queryInput = QueryInput.newBuilder().setText(textInput).build()

            // Performs the detect intent request
            val response = sessionsClient.detectIntent(session, queryInput)

            // Display the query result
            val queryResult = response.queryResult

            println("====================")
            System.out.format("Query Text: '%s'\n", queryResult.queryText)
            System.out.format("Detected Intent: %s (confidence: %f)\n",
                queryResult.intent.displayName, queryResult.intentDetectionConfidence)
            System.out.format("Fulfillment Text: '%s'\n", queryResult.fulfillmentText)

            return queryResult.fulfillmentText
        }


}

 */