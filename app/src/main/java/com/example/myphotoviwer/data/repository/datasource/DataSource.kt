package com.example.myphotoviwer.data.repository.datasource

interface DataSource {
    suspend fun getPhotoList()
}