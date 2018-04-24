package br.com.luan2.lgutilsk.utils

import java.util.*

/**
 * Created by luan silva on 19/04/18.
 */



inline fun <T> Array<T>?.isBlank(): Boolean = this == null || isEmpty()

inline fun <T> Array<T?>.anyNull(): Boolean = any { it == null }

inline fun <T> Array<T?>.allNull(): Boolean = all { it == null }

inline fun <T> Array<Array<T?>>.anyNull(): Boolean = any { it.anyNull() }

inline fun <T> Array<Array<T?>>.allNull(): Boolean = all { it.allNull() }

inline fun <T> Array<Array<T>>.anyInner(predicate: (T) -> Boolean): Boolean = any { it.any(predicate) }

inline fun <T> Array<Array<T>>.allInner(predicate: (T) -> Boolean): Boolean = all { it.all(predicate) }

inline val Array<*>.half: Int get() = size / 2

fun <T> Array<T>.swap(i: Int, j: Int): Array<T> {
    return apply {
        val aux = this[i]
        this[i] = this[j]
        this[j] = aux
    }
}

inline fun <T> Array<T>.getRandom(generator: Random = Random()): T = get(generator.nextInt(size))

inline fun <reified T> Array<T>.shuffle(generator: Random = Random()): Array<T> {
    return apply {
        for (i in size downTo 2)
            swap(i - 1, generator.nextInt(i))
    }
}

inline fun <T> array2dOf(): Array<Array<T>> = arrayOf()

inline fun <T> array2dOf(vararg ts: Array<T>) = Array<Array<T>>(ts.size) { ts[it] }

inline fun <T> array2d(rows: Int, initCol: (Int) -> Array<T>) = Array<Array<T>>(rows, { row -> initCol(row) })


inline fun <reified T> Array<Array<T>>.copy() = Array(size) { get(it).copyOf() }

inline val <T> Array<Array<T>>.rows get() = indices


inline val <T> Array<Array<T>>.lastRowIndex get() = lastIndex


inline val <T> Array<Array<T>>.rowSize get() = size


inline val <T> Array<Array<T>>.totalSize get() = fold(0) { acc, col -> acc + col.size }