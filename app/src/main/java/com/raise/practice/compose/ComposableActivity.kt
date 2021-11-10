package com.raise.practice.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.raise.practice.compose.theme.PracticeAndroidTheme

class ComposableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeAndroidTheme {
                Greeting()
            }
        }
    }
}


@Composable
fun Greeting(){
    Text("Hello Compose!")
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    PracticeAndroidTheme {
        Greeting()
    }
}