package com.example.myapplication

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.eclipse.paho.client.mqttv3.MqttCallback

class MqttManager(
    private val serverUri: String,
    private val topic: String
) {
    private val clientId = MqttClient.generateClientId()
    private val client = MqttClient(serverUri, clientId, MemoryPersistence())

    fun connect(onMessage: (String) -> Unit) {
        val options = MqttConnectOptions().apply {
            isAutomaticReconnect = true
            isCleanSession = true
        }

        client.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {}

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                onMessage(message.toString())
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {}
        })

        client.connect(options)
        client.subscribe(topic)
    }

    fun publish(message: String) {
        client.publish(topic, MqttMessage(message.toByteArray()))
    }

    fun disconnect() {
        if (client.isConnected) client.disconnect()
    }
}