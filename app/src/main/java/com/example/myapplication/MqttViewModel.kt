package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MqttViewModel : ViewModel() {

    private val mqtt = MqttManager(
        serverUri = "tcp://10.172.10.233:1883",
        topic = "test/refresh/notify"
    )

    private val _messages = MutableStateFlow("")
    val messages = _messages.asStateFlow()

    init {
        mqtt.connect { msg ->
            _messages.value = msg
        }
    }

    fun send(msg: String) {
        mqtt.publish(msg)
    }

    override fun onCleared() {
        mqtt.disconnect()
        super.onCleared()
    }
}