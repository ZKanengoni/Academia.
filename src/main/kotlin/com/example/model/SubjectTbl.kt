package com.example.model


import javafx.beans.property.Property
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
//import tornadofx.getValue
//import tornadofx.setValue
import java.math.BigDecimal
import tornadofx.*

fun ResultRow.toSubjectEntry() = SubjectEntry(
    this[SubjectTbl.id],
    this[SubjectTbl.subjectName],
    this[SubjectTbl.subjectCode],
    this[SubjectTbl.price].toDouble(),
    this[SubjectTbl.hours].toDouble()
)

object SubjectTbl : Table() {
    val id : Column<Int> = integer("subjectID").autoIncrement().primaryKey()
    val subjectName: Column<String> = varchar("subjectName", 255)
    val subjectCode: Column<String> = varchar("code", 80)
    val price:Column<BigDecimal> = decimal("price", scale = 2, precision = 9)
    val hours: Column<BigDecimal> = decimal("hours", scale = 2, precision = 9)
}

class SubjectEntry(id: Int, subjectName: String, subjectCode: String, price: Double, hours: Double) {
    val idProperty = SimpleIntegerProperty(id)
    var id: Int by idProperty

    val subjectNameProperty = SimpleStringProperty(subjectName)
    var subjectName: String by subjectNameProperty

    val subjectCodeProperty = SimpleStringProperty(subjectCode)
    var subjectCode: String by subjectCodeProperty

    val priceProperty = SimpleDoubleProperty(price)
    var price: Double by priceProperty

    val hoursProperty = SimpleDoubleProperty(hours)
    var hours: Double by hoursProperty

    override fun toString() : String {
        return "SubjectEntry(id=$id, subjecName=$subjectName, subjectCode=$subjectCode, price=$price, hours=$hours)"

    }
}

class SubjectEntryModel : ItemViewModel<SubjectEntry>() {
    val id : Property<Number?> = bind { item?.idProperty }
    val subjectName : Property<String> = bind { item?.subjectNameProperty }
    val subjectCode : Property<String> = bind { item?.subjectCodeProperty }
    val price : Property<Number> = bind { item?.priceProperty }
    val hours : Property<Number> = bind { item?.hoursProperty }
}