package com.bae.dialogflowbot.helpers
/*
import android.os.AsyncTask
import android.util.Log
import com.google.cloud.dialogflow.v2.*
import interfaces.DialogflowAgentReply

class SendMessageInBackground(
    dialogflowAgentReply: DialogflowAgentReply, session: SessionName, sessionsClient: SessionsClient,
    queryInput: QueryInput
) : AsyncTask<Void?, Void?, DetectIntentResponse?>() {
    private val session: SessionName
    private val sessionsClient: SessionsClient
    private val queryInput: QueryInput
    private val TAG = "async"
    private val dialogflowAgendReply: DialogflowAgentReply
    protected override fun doInBackground(vararg params: Void?): DetectIntentResponse? {
        try {
            val detectIntentRequest = DetectIntentRequest.newBuilder()
                .setSession(session.toString())
                .setQueryInput(queryInput)
                .build()
            return sessionsClient.detectIntent(detectIntentRequest)
        } catch (e: Exception) {
            Log.d(TAG, "doInBackground: " + e.message)
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(response: DetectIntentResponse?) {
        //handle return response here
        dialogflowAgendReply.callback(response)
    }

    init {
        this.dialogflowAgendReply = dialogflowAgentReply
        this.session = session
        this.sessionsClient = sessionsClient
        this.queryInput = queryInput
    }
}

 */