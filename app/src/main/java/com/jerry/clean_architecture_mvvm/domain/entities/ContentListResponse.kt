package com.jerry.clean_architecture_mvvm.domain.entities

import java.io.Serializable

open class ContentListResponse(
    val items: List<Content>? = null,
) : Serializable