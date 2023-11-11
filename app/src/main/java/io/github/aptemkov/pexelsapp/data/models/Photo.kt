package io.github.aptemkov.pexelsapp.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain
import io.github.aptemkov.pexelsapp.domain.models.PhotoSrcDomain
import java.io.Serializable

@Entity("favourite_photos")
data class Photo(
    @PrimaryKey
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String?,
    val photographerUrl: String?,
    val photographerId: Int?,
    val avgColor: String?,
    @Embedded(prefix = "src_")
    val src: PhotoSrc,
    val liked: Boolean,
    val alt: String
) : Serializable

data class PhotoSrc(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

fun Photo.asDomain(): PhotoDomain {
    return PhotoDomain(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.url,
        photographer = this.photographer,
        photographerUrl = this.photographerUrl,
        photographerId = this.photographerId,
        avgColor = this.avgColor,
        src = PhotoSrcDomain(
            original = this.src.original,
            large2x = this.src.large2x,
            large = this.src.large,
            medium = this.src.medium,
            small = this.src.small,
            portrait = this.src.portrait,
            landscape = this.src.landscape,
            tiny = this.src.tiny,
        ),
        liked = this.liked,
        alt = this.alt
    )
}

fun PhotoDomain.asPhoto(): Photo {
    return Photo(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.url,
        photographer = this.photographer,
        photographerUrl = this.photographerUrl,
        photographerId = this.photographerId,
        avgColor = this.avgColor,
        src = PhotoSrc(
            original = this.src.original,
            large2x = this.src.original,
            large = this.src.original,
            medium = this.src.original,
            small = this.src.original,
            portrait = this.src.original,
            landscape = this.src.original,
            tiny = this.src.original,
        ),
        liked = this.liked,
        alt = this.alt,
    )
}