package com.example.praktam_2417051022

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktam_2417051022.model.ReviewSource
import com.example.praktam_2417051022.ui.theme.PRAKTAM_2417051022Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRAKTAM_2417051022Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//
//    val review = ReviewSource.dummyReview[0]
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//
//        Image(
//            painter = painterResource(id = review.imageRes),
//            contentDescription = review.nama,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(text = "Nama: ${review.nama}")
//        Text(text = "Deskripsi: ${review.deskripsi}")
//        Text(text = "Kategori: ${review.kategori}")
//    }
//}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val reviews = ReviewSource.dummyReview

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        reviews.forEach { review ->

            Image(
                painter = painterResource(id = review.imageRes),
                contentDescription = review.nama,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Nama: ${review.nama}")
            Text(text = "Deskripsi: ${review.deskripsi}")
            Text(text = "Kategori: ${review.kategori}")

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PRAKTAM_2417051022Theme {
        Greeting("Android")
    }
}