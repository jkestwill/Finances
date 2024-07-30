package com.jk.common_data

fun ULong.toByteArray(): ByteArray {
    val result = ByteArray(ULong.SIZE_BYTES)
    (0 until ULong.SIZE_BYTES).forEach {
        result[it] = this.shr(Byte.SIZE_BITS * it).toByte()
    }
    return result
}

fun ByteArray.ULong(): ULong =
        foldIndexed(0UL) { index, acc, byte ->
            acc + (byte.toULong() shl index * 8)
        }

fun main() {
    val long = 712211313UL
    val byte = long.toByteArray()
    println(byte.ULong())
}