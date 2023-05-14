package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 参考:https://www.jianshu.com/p/93d8a384a8a0
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android", this@MainActivity)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}

private var contentData: String? = "no data"
private var state by mutableStateOf(contentData)

@Composable
fun Greeting(name: String, context: Context?, modifier: Modifier = Modifier) {

    Column() {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(
            onClick = {
                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show()
                test(context)
            },
            modifier = Modifier
                .height(60.dp)
                .absoluteOffset(20.dp, 20.dp)
                .padding(5.dp)
        ) {
            Text(text = "$state")
        }
    }
}

val mainScope = MainScope()

@SuppressLint("UnrememberedMutableState")
fun test(context: Context?) {
    mainScope.launch(Dispatchers.IO) {
        contentData = getResult("我是请求参数对应的")
        withContext(Dispatchers.Main) {
            Toast.makeText(context, contentData, Toast.LENGTH_SHORT).show()
            state = contentData
        }
    }
}

fun getResult(params: String): String {
    Thread.sleep(2000)
    return "$params==>模拟网络数据获取成功"
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android", null)
    }
}