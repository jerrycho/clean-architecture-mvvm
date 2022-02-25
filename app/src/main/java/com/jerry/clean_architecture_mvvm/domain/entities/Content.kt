package com.jerry.clean_architecture_mvvm.domain.entities

import java.io.Serializable

open class Content(
    val id: Int? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val date: String? = null,

) : Serializable