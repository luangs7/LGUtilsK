package br.com.luan2.lgutilsk.utils

/**
 * Created by luan silva on 19/04/18.
 */


fun Long.toBoolean() = when (this) {
    0L -> false
    else -> true
}

fun Boolean.toLong() = when (this) {
    false -> 0L
    true -> -1L // All bits to 1, supports bitwise not to be false
}

fun Double.toBoolean() = when (this) {
    0.0 -> false
    else -> true
}

fun Boolean.toDouble() = when (this) {
    false -> 0.0
    true -> -1.0
}

fun Byte.toBoolean() = toLong().toBoolean()
fun Boolean.toByte() = toLong().toByte()

fun Short.toBoolean() = toLong().toShort()
fun Boolean.toShort() = toLong().toByte()

fun Int.toBoolean() = toLong().toBoolean()
fun Boolean.toInt() = toLong().toInt()

fun Float.toBoolean() = toDouble().toBoolean()
fun Boolean.toFloat() = toDouble().toFloat()

fun Double.toBits(): Long = java.lang.Double.doubleToRawLongBits(this)
fun Float.toBits(): Int = java.lang.Float.floatToRawIntBits(this)

fun Int.toBinaryString(): String = java.lang.Integer.toBinaryString(this)
fun Long.toBinaryString(): String = java.lang.Long.toBinaryString(this)
fun Double.toBinaryString(): String = toBits().toBinaryString()
fun Float.toBinaryString(): String = toBits().toBinaryString()


inline fun String.toByteOrRun(defaultBlock: (String) -> Byte): Byte = toByteOrNull() ?: run(defaultBlock)
inline fun String.toByteOrThrow(crossinline throwable: String.() -> Throwable): Byte = toByteOrRun { throw throwable() }
fun String.toByteOrThrow(throwable: Throwable): Byte = toByteOrThrow { throwable }
fun String.toByteOrDefault(default: Byte = 0): Byte = toByteOrRun { default }

inline fun String.toShortOrRun(defaultBlock: (String) -> Short): Short = toShortOrNull() ?: run(defaultBlock)
inline fun String.toShortOrThrow(crossinline throwable: String.() -> Throwable): Short = toShortOrRun { throw throwable() }
fun String.toShortOrThrow(throwable: Throwable): Short = toShortOrThrow { throwable }
fun String.toShortOrDefault(default: Short = 0): Short = toShortOrRun { default }

inline fun String.toIntOrRun(defaultBlock: (String) -> Int): Int = toIntOrNull() ?: run(defaultBlock)
inline fun String.toIntOrThrow(crossinline throwable: String.() -> Throwable): Int = toIntOrRun { throw throwable() }
fun String.toIntOrThrow(throwable: Throwable): Int = toIntOrThrow { throwable }
fun String.toIntOrDefault(default: Int = 0): Int = toIntOrRun { default }

inline fun String.toLongOrRun(defaultBlock: (String) -> Long): Long = toLongOrNull() ?: run(defaultBlock)
inline fun String.toLongOrThrow(crossinline throwable: String.() -> Throwable): Long = toLongOrRun { throw throwable() }
fun String.toLongOrThrow(throwable: Throwable): Long = toLongOrThrow { throwable }
fun String.toLongOrDefault(default: Long = 0): Long = toLongOrRun { default }

inline fun String.toFloatOrRun(defaultBlock: (String) -> Float): Float = toFloatOrNull() ?: run(defaultBlock)
inline fun String.toFloatOrThrow(crossinline throwable: String.() -> Throwable): Float = toFloatOrRun { throw throwable() }
fun String.toFloatOrThrow(throwable: Throwable): Float = toFloatOrThrow { throwable }
fun String.toFloatOrDefault(default: Float = 0f): Float = toFloatOrRun { default }

inline fun String.toDoubleOrRun(defaultBlock: (String) -> Double): Double = toDoubleOrNull() ?: run(defaultBlock)
inline fun String.toDoubleOrThrow(crossinline throwable: String.() -> Throwable): Double = toDoubleOrRun { throw throwable() }
fun String.toDoubleOrThrow(throwable: Throwable): Double = toDoubleOrThrow { throwable }
fun String.toDoubleOrDefault(default: Double = 0.0): Double = toDoubleOrRun { default }

fun String.toBooleanOrNull(): Boolean? = if (equals("true", ignoreCase = true)) true else if (equals("false", ignoreCase = true)) false else null
inline fun String.toBooleanOrRun(defaultBlock: (String) -> Boolean): Boolean = toBooleanOrNull() ?: run(defaultBlock)
inline fun String.toBooleanOrThrow(crossinline throwable: String.() -> Throwable): Boolean = toBooleanOrRun { throw throwable() }
fun String.toBooleanOrThrow(throwable: Throwable): Boolean = toBooleanOrThrow { throwable }
fun String.toBooleanOrDefault(default: Boolean = false): Boolean = toBooleanOrRun { default }

fun Any?.toByte(): Byte {
    return when (this) {
        null -> 0
        is Number -> toByte()
        else -> toString().toByteOrDefault()
    }
}

fun Any?.toShort(): Short {
    return when (this) {
        null -> 0
        is Number -> toShort()
        else -> toString().toShortOrDefault()
    }
}

fun Any?.toInt(): Int {
    return when (this) {
        null -> 0
        is Number -> toInt()
        else -> toString().toDoubleOrDefault().toInt()
    }
}

fun Any?.toLong(): Long {
    return when (this) {
        null -> 0L
        is Number -> toLong()
        else -> toString().toLongOrDefault()
    }
}

fun Any?.toFloat(): Float {
    return when (this) {
        null -> 0f
        is Number -> toFloat()
        else -> toString().toFloatOrDefault()
    }
}

fun Any?.toDouble(): Double {
    return when (this) {
        null -> 0.0
        is Number -> toDouble()
        else -> toString().toDoubleOrDefault()
    }
}

fun Any?.toBoolean(): Boolean {
    return when (this) {
        null -> false
        is Boolean -> this
        is Double -> toBoolean()
        is Float -> toBoolean()
        is Number -> toLong().toBoolean()
        else -> toString().toBooleanOrDefault()
    }
}