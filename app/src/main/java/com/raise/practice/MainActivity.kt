package com.raise.practice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abupdate.common.Trace
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    var client: Mqtt3AsyncClient by Delegates.notNull<Mqtt3AsyncClient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_1.setOnClickListener {
            client = MqttClient.builder()
                    .useMqttVersion3()
                    .identifier("my-hivemq-client-001")
                    .serverHost("mqtt.p2hp.com")
                    .serverPort(1883)
                    .buildAsync()

            client.connectWith()
                    .send()
                    .whenComplete { connAck: Mqtt3ConnAck?, throwable: Throwable? ->
                        if (throwable != null) {
                            // handle failure
                        } else {
                            // setup subscribes or start publishing
                            Trace.d(TAG, "onCreate() 连接成功")
                            sub()
                        }
                    }
        }

        btn_2.setOnClickListener {
            client.publishWith()
                    .topic("ota/check")
                    .payload("这是我发布的消息 from hivemq.".toByteArray())
                    .send()
                    .whenComplete { publish: Mqtt3Publish?, throwable: Throwable? ->
                        if (throwable != null) {
                            // Handle failure to subscribe
                            Trace.e(TAG, "publishWith() $throwable")
                        } else {
                            // Handle successful subscription, e.g. logging or incrementing a metric
                            Trace.d(TAG, "publishWith() success:$publish")
                        }
                    }
        }
        btn_3.setOnClickListener {
            Trace.d(TAG, "onCreate() client.disconnect()")
            client.disconnect().whenComplete { a, throwable ->
                if (throwable != null) {
                    // Handle failure to subscribe
                    Trace.e(TAG, "disconnect() $throwable")
                } else {
                    // Handle successful subscription, e.g. logging or incrementing a metric
                    Trace.d(TAG, "disconnect() success:$a")
                }
            }
        }
    }

    fun sub() {
        client.subscribeWith()
                .topicFilter("ota/check")
                .callback { publish ->
                    Trace.d(TAG, "sub() publish=$publish::${String(publish.payloadAsBytes)}")
                }
                .send()
                .whenComplete { subAck, throwable ->
                    if (throwable != null) {
                        // Handle failure to subscribe
                        Trace.e(TAG, "sub() $throwable")
                    } else {
                        // Handle successful subscription, e.g. logging or incrementing a metric
                        Trace.d(TAG, "sub() success:$subAck")
                    }
                }
    }

}
