package com.example.model

import com.example.utils.toJavaLocalDate
import javafx.beans.property.*
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.math.BigDecimal
import tornadofx.*

fun ResultRow.toStudentEntry() = StudentEntry(
    this[StudentTbl.id],
    this[StudentTbl.dateOfBirth].toJavaLocalDate(),
    this[StudentTbl.studentName],
    this[StudentTbl.courseType],
    this[StudentTbl.fees].toDouble()
)

object StudentTbl : Table() {
    val id: Column<Int> = integer("studentID").autoIncrement().primaryKey()
    val dateOfBirth: Column<DateTime> = date("dob")
    val studentName: Column<String> = varchar("name", 50)
    val courseType: Column<String> = varchar("course_type", 50)
    val fees: Column<BigDecimal> = decimal("fees", scale = 2, precision = 9)
}

class StudentEntry(id: Int, dateOfBirth: java.time.LocalDate, studentName: String, courseType: String, fees: Double) {
    val idProperty = SimpleIntegerProperty(id)
    var id: Int by idProperty

    val dobProperty = SimpleObjectProperty<java.time.LocalDate>(dateOfBirth)
    var dateOfBirth: java.time.LocalDate by dobProperty

    val studentNameProperty = SimpleStringProperty(studentName)
    var studentName: String by studentNameProperty

    val courseTypeProperty = SimpleStringProperty(courseType)
    var courseType: String by courseTypeProperty

    val feesProperty = SimpleDoubleProperty(fees)
    var fees: Double by feesProperty

    override fun toString() : String {
        return "StudentEntry(id=$id, dob=$dateOfBirth, name=$studentName, course=$courseType, fees=$fees)"
    }

}

class StudentEntryModel : ItemViewModel<StudentEntry>() {
    val id : Property<Number?> = bind { item?.idProperty }
    val dateOfBirth : Property<java.time.LocalDate> = bind { item?.dobProperty }
    val studentName : Property<String> = bind { item?.studentNameProperty }
    val courseType : Property<String> = bind { item?.courseTypeProperty }
    val fees : Property<Number> = bind { item?.feesProperty }
}
