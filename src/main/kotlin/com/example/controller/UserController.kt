package com.example.controller

import com.example.model.*
import com.example.utils.execute
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import tornadofx.Controller
import tornadofx.observable
import tornadofx.singleAssign

class UserController : Controller() {

    private val listOfUsers: ObservableList<UserEntryModel> = execute {
        UserTbl.selectAll().map {
            UserEntryModel().apply {
                item = it.toUserEntry()
            }
        }.observable()
    }

    var users: ObservableList<UserEntryModel> by singleAssign()

    init {
        users = listOfUsers
    }

    fun add(newUsername: String, newPassword: String) : UserEntry {
        val newEntry : InsertStatement<Number> = execute {
            UserTbl.insert {
                it[username] = newUsername
                it[password] = newPassword
            }
        }

        listOfUsers.add(
            UserEntryModel().apply {
                item = UserEntry(newEntry[UserTbl.id], newUsername, newPassword)
            }
        )

        return UserEntry(newEntry[UserTbl.id], newUsername, newPassword)
    }

    fun login(username: String, password: String): Boolean {
        return username == "Admin" && password == "123456789"
    }
}