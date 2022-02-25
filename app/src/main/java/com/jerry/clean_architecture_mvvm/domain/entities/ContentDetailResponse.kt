package com.jerry.clean_architecture_mvvm.domain.entities

import java.io.Serializable

open class ContentDetailResponse(
    val item: ContentDetail? = null,
) : Serializable