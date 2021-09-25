package com.vspk.bookrest.rest

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class PhotoController {

    @GetMapping(value = ["/get-image-with-media-type"], produces = [MediaType.IMAGE_JPEG_VALUE])
    fun getImageWithMediaType(imageId: String): ByteArray? {
        val photo = javaClass
            .getResourceAsStream("/com/baeldung/produceimage/image.jpg")//path to image pass id
        return photo?.readAllBytes()//IOUtils.toByteArray(photo)
    }


}