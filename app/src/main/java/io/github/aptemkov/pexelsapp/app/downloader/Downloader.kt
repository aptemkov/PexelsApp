package io.github.aptemkov.pexelsapp.app.downloader

interface Downloader {
    fun downloadFile(url: String, fileName: String): Long
}