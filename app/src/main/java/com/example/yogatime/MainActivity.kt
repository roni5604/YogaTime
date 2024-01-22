package com.example.yogatime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.yogatime.ui.theme.YogaTimeTheme


class MainActivity : ComponentActivity() {
    private val manager = Manager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YogaTimeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ManagerUI(manager)
                }
            }
        }
    }
}

@Composable
fun DisplayTrain(train: Train) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Train Name: ${train.name}")
            Text(text = "Train Date: ${train.date}")
            Text(text = "Train Capacity: ${train.capacity}")
        }
    }
}

@Composable
fun ManagerUI(manager: Manager) {
    var trainName by remember { mutableStateOf("") }
    var trainDate by remember { mutableStateOf("") }
    var trainCapacity by remember { mutableStateOf("") }
    var trains by remember { mutableStateOf(listOf<Train>()) }

    Column {
        OutlinedTextField(
            value = trainName,
            onValueChange = { trainName = it },
            label = { Text("Train Name") }
        )
        OutlinedTextField(
            value = trainDate,
            onValueChange = { trainDate = it },
            label = { Text("Train Date") }
        )
        OutlinedTextField(
            value = trainCapacity,
            onValueChange = { trainCapacity = it },
            label = { Text("Train Capacity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(onClick = {
            val train = manager.createTrain(trainName, trainDate, trainCapacity.toInt())
            trains = trains + train
        }) {
            Text("Add Train")
        }

        //option to scroll through trains - loads only visible trains
        LazyColumn {
            items(trains) { train ->
                DisplayTrain(train)
            }
        }
    }
}

//        //option to scroll through trains - loads all trains at once (not matter if visible or not)
//        Column(Modifier.verticalScroll(rememberScrollState())) {
//            for (train in trains) {
//                DisplayTrain(train)
//            }
//        }

/* Option without the list - what out of the screen is never visible*/
//@Composable
//fun ManagerUI(manager: Manager) {
//    var trainName by remember { mutableStateOf("") }
//    var trainDate by remember { mutableStateOf("") }
//    var trainCapacity by remember { mutableStateOf("") }
//    var train by remember { mutableStateOf<Train?>(null) }
//
//    Column {
//        OutlinedTextField(
//            value = trainName,
//            onValueChange = { trainName = it },
//            label = { Text("Train Name") }
//        )
//        OutlinedTextField(
//            value = trainDate,
//            onValueChange = { trainDate = it },
//            label = { Text("Train Date") }
//        )
//        OutlinedTextField(
//            value = trainCapacity,
//            onValueChange = { trainCapacity = it },
//            label = { Text("Train Capacity") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//        )
//        Button(onClick = {
//            train = manager.createTrain(trainName, trainDate, trainCapacity.toInt())
//        }) {
//            Text("Add Train")
//        }
//        train?.let {
//            DisplayTrain(it)
//        }
//    }
//}