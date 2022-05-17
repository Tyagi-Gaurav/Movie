package com.gt.scr.download.resource.domain

import java.util.UUID

data class MovieMetaDataResponse(
    val id: UUID,
    val streamUrl: String
)
