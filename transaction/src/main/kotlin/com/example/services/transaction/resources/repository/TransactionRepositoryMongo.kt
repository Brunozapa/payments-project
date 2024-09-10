package com.example.services.transaction.resources.repository

import com.example.services.transaction.resources.repository.entity.TransactionEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface TransactionRepositoryMongo : MongoRepository<TransactionEntity, String>
