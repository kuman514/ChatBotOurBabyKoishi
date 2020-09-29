package com.example.chatbotourbabykoishi

import android.content.Context
import android.util.Log
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.*
import com.google.protobuf.Struct
import com.google.protobuf.Value
import java.io.InputStream

object KoishiChatBot {
    private var client: SessionsClient? = null
    private var session: SessionName? = null
    private const val LANGUAGE_CODE = "en-US"

    private var stream: InputStream? = null
    private var credentials: GoogleCredentials? = null
    private var projectId: String? = null

    private var settingsBuilder: SessionsSettings.Builder? = null
    private var sessionsSettings: SessionsSettings? = null

    fun initAssistant(context: Context) {
        try {
            stream = context.resources.openRawResource(R.raw.ourbabykoishi)
            credentials = GoogleCredentials.fromStream(stream)
            projectId = (credentials as ServiceAccountCredentials).projectId

            settingsBuilder = SessionsSettings.newBuilder()
            sessionsSettings = settingsBuilder!!.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build()

            //Log.d(MainActivity.TAG + "Dialogflow", settingsBuilder.toString())
            //Log.d(MainActivity.TAG + "Dialogflow", sessionsSettings.toString())

            client = SessionsClient.create(sessionsSettings)

            //Log.d(MainActivity.TAG + "Dialogflow", "Initialization complete.")
        } catch (e: Exception) {
            Log.e(MainActivity.TAG + "Dialogflow", "Dialogflow system failed.")
        }
    }

    fun talkToKoishi(somePayload: Int, sessionText: String): QueryResult {
        session = SessionName.of(projectId, sessionText)
        return detectIntentText(somePayload, sessionText)
    }

    @Throws(Exception::class)
    fun detectIntentText(somePayload: Int, text: String): QueryResult {
        if(client == null || session == null){
            throw Exception("Error: no Dialogflow client")
        }

        // Set the text (input) and language code (en) for the query
        val textInput = TextInput.newBuilder().setText(text).setLanguageCode(LANGUAGE_CODE)

        // Build the query with the TextInput
        val queryInput = QueryInput.newBuilder().setText(textInput).build()

        // Save received payload into a protobuf Struct
        val payLoad = Value.newBuilder().setNumberValue(somePayload.toDouble()).build()
        val struct = Struct.newBuilder()
        struct.putFields("somePayload", payLoad)

        // Set queryParameters
        val queryParameters = QueryParameters.newBuilder().setPayload(struct).build()

        // Build the request
        val request = DetectIntentRequest.newBuilder()
            .setSession(session.toString())
            .setQueryInput(queryInput)
            .setQueryParams(queryParameters)
            .build()

        // Performs the detect intent request
        val response = client!!.detectIntent(request)

        // Display the query result
        return response.queryResult
    }
}