package com.example.rickandmorty.data

import com.apollographql.apollo3.ApolloClient
import com.example.CharactersQuery
import com.example.rickandmorty.domain.CharacterClient
import com.example.rickandmorty.domain.SimpleCharacter
import javax.inject.Inject

class ApolloCharacterClient @Inject constructor(
    private val apolloClient: ApolloClient
) : CharacterClient {

    override suspend fun getCharacters(): List<SimpleCharacter>? =
        apolloClient
            .query(CharactersQuery())
            .execute()
            .data?.characters?.results?.map { it!!.toSimpleCharacter() }
}

fun CharactersQuery.Result.toSimpleCharacter(): SimpleCharacter {
    return SimpleCharacter(
        name = name,
        status = status,
        gender = gender,
        image = image,
        created = created
    )
}