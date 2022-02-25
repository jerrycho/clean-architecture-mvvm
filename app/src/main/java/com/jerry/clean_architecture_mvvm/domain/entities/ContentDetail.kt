package com.jerry.clean_architecture_mvvm.domain.entities

import java.io.Serializable


data class ContentDetail (
    val body: String? = null,
) : Content(), Serializable

