package com.example.mvvmmovieapp.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("belongs_to_collection") val belongs_to_collection: String,
    @SerializedName("budget") val budget: Int,
    @SerializedName("genres") val genres: List<Genres>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("imdb_id") val imdb_id: String,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("production_companies") val production_companies: List<Production_companies>,
    @SerializedName("production_countries") val production_countries: List<Production_countries>,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("spoken_languages") val spoken_languages: List<Spoken_languages>,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Int
)

data class Genres (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)

data class Production_companies (

    @SerializedName("id") val id : Int,
    @SerializedName("logo_path") val logo_path : String,
    @SerializedName("name") val name : String,
    @SerializedName("origin_country") val origin_country : String
)

data class Production_countries (

    @SerializedName("iso_3166_1") val iso_3166_1 : String,
    @SerializedName("name") val name : String
)

data class Spoken_languages (

    @SerializedName("english_name") val english_name : String,
    @SerializedName("iso_639_1") val iso_639_1 : String,
    @SerializedName("name") val name : String
)






