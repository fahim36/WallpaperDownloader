package com.example.myphotoviwer.data.repository

import com.example.myphotoviwer.data.repository.datasource.DataSource
import com.example.myphotoviwer.domain.repository.Repository


class RepositoryImpl(private val dataSource: DataSource) : Repository {

}