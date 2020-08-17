package com.project.hotel.service

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class FileStorageService {
    val  rootLocation = Paths.get("upload")
    fun store(file:FilePart):String{
        val resultFileName = UUID.randomUUID().toString() + "." + file.filename()
        file.transferTo(File(rootLocation.resolve(resultFileName).toString()))
        return resultFileName
    }
    fun loadFile(filename: String): Resource {
        val file = rootLocation.resolve(filename)
        val resource = UrlResource(file.toUri())

        if(resource.exists() || resource.isReadable) {
            return resource
        }else{
            throw RuntimeException("FAIL!")
        }
    }
}