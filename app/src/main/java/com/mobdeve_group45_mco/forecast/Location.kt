package com.mobdeve_group45_mco.forecast

data class Location(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val elevation: Double,
    val timezone: String,
    val feature_code: String,
    val country_code: String,
    val country: String,
    val country_id: Int,
    val population: Int,
    val postcodes: List<String>,
    val admin1: String?,
    val admin2: String?,
    val admin3: String?,
    val admin4: String?,
    val admin1_id: Int?,
    val admin2_id: Int?,
    val admin3_id: Int?,
    val admin4_id: Int?
)
