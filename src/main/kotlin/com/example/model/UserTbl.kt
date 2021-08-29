package com.example.model

import javafx.beans.property.Property
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import tornadofx.*



fun ResultRow.toUserEntry() = UserEntry(
    this[UserTbl.id],
    this[UserTbl.username],
    this[UserTbl.password]
)

object UserTbl : Table() {
    val id: Column<Int> = integer("UserID").autoIncrement().primaryKey()
    val username: Column<String> = varchar("username", 50)
    val password: Column<String> = varchar("password", 50)
}

class UserEntry(id: Int, username: String, password: String) {
    val idProperty = SimpleIntegerProperty(id)
    var id: Int by idProperty

    val usernameProperty = SimpleStringProperty(username)
    var username: String by usernameProperty

    val passwordProperty = SimpleStringProperty(password)
    var password: String by passwordProperty

    override fun toString() : String {
        return "UserEntry(id=$id, username=$username, password=$password)"
    }
}


class UserEntryModel : ItemViewModel<UserEntry>() {
    val id : Property<Number?> = bind { item?.idProperty }
    val username : Property<String> = bind { item?.usernameProperty }
    val password : Property<String> = bind { item?.passwordProperty }
}