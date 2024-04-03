package com.jk.common_data

import java.security.MessageDigest

fun String.sha256(): String {
    return this.hash("SHA-256")
}

private fun String.hash(algName:String): String {
    val bytes = this.toByteArray()
    val md = MessageDigest.getInstance(algName)
    val digest = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}