package com.example.rickandmorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.ApolloCharacterClient
import com.example.rickandmorty.domain.SimpleCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharactersUiState(
    val characters: List<SimpleCharacter> = emptyList()
)

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val apolloCharacterClient: ApolloCharacterClient
) : ViewModel() {
    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            val response = apolloCharacterClient.getCharacters()

            if (response != null) {
                _uiState.update {
                    it.copy(characters = response)
                }
            }
        }
    }
}