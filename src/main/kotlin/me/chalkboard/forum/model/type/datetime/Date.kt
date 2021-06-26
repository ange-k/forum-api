package me.chalkboard.forum.model.type.datetime

import java.time.Clock
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * 日付オブジェクト
 */
class Date private constructor(
    private val date: LocalDate
) {

    /**
     * YYYY-MM-DD形式の文字列を返す
     */
    override fun toString():String =
        date.format(DateTimeFormatter.ISO_DATE)

    fun value(): LocalDate = this.date

    companion object {
        /**
         * 現在のオブジェクトを取得
         */
        fun now(clock: Clock): Date {
            return Date(LocalDate.now(clock))
        }

        /**
         * N日前のオブジェクトを取得
         */
        fun daysAgo(day: Long, clock: Clock): Date {
            val now = LocalDate.now(clock)
            return Date(now.minusDays(day))
        }
    }
}