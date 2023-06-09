package com.example.rickandmorty.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rickandmorty.domain.SimpleCharacter
import com.example.rickandmorty.presentation.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.presentation.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainUi()
                }
            }
        }
    }
}

@Composable
fun MainUi(viewModel: CharacterViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn(modifier = Modifier.background(Color.LightGray)) {
        items(uiState.characters) { character ->
            CharacterRow(character = character)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterRow(character: SimpleCharacter) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = character.image,
            contentDescription = null
        )
        Spacer(modifier = Modifier.padding(start = 12.dp))
        Column {
            character.name?.let { Text(text = it, fontSize = 20.sp, fontWeight = FontWeight.Bold) }
            character.gender?.let { Text(text = it) }
        }
    }
}