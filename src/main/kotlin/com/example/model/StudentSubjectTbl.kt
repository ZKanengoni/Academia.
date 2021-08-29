package com.example.model


import javafx.beans.property.Property
import javafx.beans.property.SimpleIntegerProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
//import tornadofx.getValue
//import tornadofx.setValue
import tornadofx.*

fun ResultRow.tostudentSubjectEntry() = studentSubjectEntry(
    this[StudentSubjectTbl.id],
    this[StudentSubjectTbl.studentID],
    this[StudentSubjectTbl.subjectID]
)

object StudentSubjectTbl : Table() {
    val id: Column<Int> = integer("enrolmentID").autoIncrement().primaryKey()
    val studentID: Column<Int> = integer("studentID")
    val subjectID: Column<Int> = integer("subjectID")
}

class studentSubjectEntry(id: Int, studentID: Int, subjectID: Int) {
    val idProperty = SimpleIntegerProperty(id)
    var id: Int by idProperty

    val studentIDProperty = SimpleIntegerProperty(studentID)
    var studentID: Int by studentIDProperty

    val subjectIDProperty = SimpleIntegerProperty(subjectID)
    var subjectID: Int by subjectIDProperty

    override fun toString() : String {
        return "SubjectEntry(id=$id, subjectID=$subjectID, studentID=$studentID)"
    }
}

class StudentSubjectEntrymodel : ItemViewModel<studentSubjectEntry>() {
    val id : Property<Number?> = bind { item?.idProperty }
    val studentID : Property<Number?> = bind { item?.studentIDProperty }
    val subjectID : Property<Number?> = bind { item?.subjectIDProperty }
}