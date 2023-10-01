package com.jgbravo.logger

interface CustomLogger {

    enum class DatabaseAction(val action: String) {
        INSERT("Insert item"),
        UPDATE("Update item"),
        DELETE("Delete item"),
        DELETE_ALL("Delete all items")
    }

    fun d(message: String)

    fun d(tag: String, message: String)

    fun i(message: String)

    fun i(tag: String, message: String)

    fun w(message: String)

    fun w(throwable: Throwable)

    fun e(message: String)

    fun e(throwable: Throwable)

    fun time(message: String)

    fun db(message: String, dbAction: DatabaseAction)

    fun http(message: String)
}