package com.example.controller

import com.example.model.StudentEntry
import com.example.model.StudentEntryModel
import com.example.model.StudentTbl
import com.example.model.toStudentEntry
import com.example.utils.execute
import com.example.utils.toDates
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.math.BigDecimal
import java.time.LocalDate


class StudentController : Controller() {

    var studentModel = StudentEntryModel()


    //  Get all students
    private val listOfStudents: ObservableList<StudentEntryModel> = execute {
        StudentTbl.selectAll().map {
            StudentEntryModel().apply {
                item = it.toStudentEntry()
            }
        }.observable()
    }

    var students: ObservableList<StudentEntryModel> by singleAssign()

    init {
        students = listOfStudents
    }


    fun add(newDateOfBirth: LocalDate, newStudentName: String, newFees: Double, newCourseType: String) : StudentEntry {
        val newEntry : InsertStatement<Number> = execute {
            com.example.model.StudentTbl.insert {
                it[dateOfBirth] = newDateOfBirth.toDates()
                it[studentName] = newStudentName
                it[courseType] = newCourseType
                it[fees] = BigDecimal.valueOf(newFees)
            }
        }

        listOfStudents.add(
            StudentEntryModel().apply {
                item = StudentEntry(newEntry[StudentTbl.id], newDateOfBirth, newStudentName, newCourseType, newFees)
            }
        )

        return StudentEntry(newEntry[StudentTbl.id], newDateOfBirth, newStudentName, newCourseType, newFees)
    }

    fun update(updatedStudent: StudentEntryModel): Int{
        return execute {
            StudentTbl.update({ StudentTbl.id eq(updatedStudent.id.value!!.toInt()) }) {
                it[dateOfBirth] = updatedStudent.dateOfBirth.value!!.toDates()
                it[studentName] = updatedStudent.studentName.value.toString()
                it[courseType] = updatedStudent.courseType.value.toString()
                it[fees] = BigDecimal.valueOf(updatedStudent.fees.value!!.toDouble())
            }
        }
    }

    fun delete(model: StudentEntryModel) {
        execute {
            StudentTbl.deleteWhere {
                    StudentTbl.id eq (model.id.value!!.toInt())
            }
        }
        listOfStudents.remove(model)
    }

}


