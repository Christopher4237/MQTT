package com.example.myapplication


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue


@Composable
fun MqttScreen(viewModel: MqttViewModel = viewModel()) {
    val message by viewModel.messages.collectAsState()

    Column {
        Text("Received: $message")

        Button(onClick = { viewModel.send("Hello from Compose!") }) {
            Text("Send")
        }
    }
}