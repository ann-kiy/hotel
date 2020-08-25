package com.project.hotel.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class FileStorageService {
    @Value("\${upload.path}")
    lateinit var rootLocation: String
    fun uploadFile(file: FilePart): String {
        val resultFileName = rootLocation + UUID.randomUUID().toString() + "." + file.filename()
        file.transferTo(File(resultFileName))
        return resultFileName
    }

    fun getFile(filename: String): Resource {
        val file = rootLocation + filename
        val resource = UrlResource(file)

        if (resource.exists() || resource.isReadable) {
            return resource
        } else {
            throw RuntimeException("FAIL!")
        }
    }
}