package com.example.model

import javafx.beans.property.Property
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal
import tornadofx.*

fun ResultRow.toLecturerEntry() = LecturerEntry(
    this[LecturerTbl.id],
    this[LecturerTbl.lecturerName],
    this[LecturerTbl.hours].toDouble()
)

object LecturerTbl : Table() {
    val id: Column<Int> = integer("lecturerID").autoIncrement().primaryKey()
    val lecturerName: Column<String> = varchar("name", 50)
    val hours: Column<BigDecimal> = decimal("hours", scale = 2, precision = 9)
}

class LecturerEntry(id: Int, lecturerName: String, hours: Double) {
    val idProperty = SimpleIntegerProperty(id)
    var id: Int by idProperty

    val nameProperty = SimpleStringProperty(lecturerName)
    var lecturerName: String by nameProperty

    val hoursProperty  = SimpleDoubleProperty(hours)
    var hours: Double by hoursProperty

    override fun toString() : String {
        return "LecturerEntry(id=$id, name=$lecturerName, hours=$hours)"
    }

}

class LecturerEntryModel : ItemViewModel<LecturerEntry>() {
    val id : Property<Number?> = bind { item?.idProperty }
    val lecturerName : Property<String> = bind { item?.nameProperty }
    val hours : Property<Number> = bind { item?.hoursProperty }
}