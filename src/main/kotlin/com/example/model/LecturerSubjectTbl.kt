package com.example.model

import javafx.beans.property.Property
import javafx.beans.property.SimpleIntegerProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import tornadofx.*

fun ResultRow.toLecturerSubject() = LecturerSubjectEntry(
    this[LecturerSubjectTbl.id],
    this[LecturerSubjectTbl.lecturerID],
    this[LecturerSubjectTbl.subjectID]
)

object LecturerSubjectTbl : Table() {
    val id : Column<Int> = integer("assignmentID").autoIncrement().primaryKey()
    val lecturerID: Column<Int> = integer("lecturerID")
    val subjectID: Column<Int> = integer("subjectID")
}

class LecturerSubjectEntry(id: Int, lecturerID: Int, subjectID: Int) {
    val idProperty = SimpleIntegerProperty(id)
    var id: Int by idProperty

    val lecturerIDProperty = SimpleIntegerProperty(lecturerID)
    var lecturerID: Int by lecturerIDProperty

    val subjectIDProperty = SimpleIntegerProperty(subjectID)
    var subjectID: Int by subjectIDProperty
}

class LecturerSubjectsModel : ItemViewModel<LecturerSubjectEntry>() {
    val id : Property<Number?> = bind { item?.idProperty }
    val lecturerID : Property<Number?> = bind { item?.lecturerIDProperty }
    val subjectID : Property<Number?> = bind { item?.subjectIDProperty }
}