package com.example.controller

import com.example.model.*
import com.example.utils.execute
import com.example.utils.toDates
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.update
import tornadofx.Controller
import tornadofx.observable
import tornadofx.singleAssign
import java.math.BigDecimal
import java.time.LocalDate

class LecturerController : Controller() {

    var lecturerModel = LecturerEntryModel()


    //  Get all students
    private val listOfLecturers: ObservableList<LecturerEntryModel> = execute {
        LecturerTbl.selectAll().map {
            LecturerEntryModel().apply {
                item = it.toLecturerEntry()
            }
        }.observable()
    }

    var lecturers: ObservableList<LecturerEntryModel> by singleAssign()

    init {
        lecturers = listOfLecturers
    }

    fun add(newLecturerName: String, newHours: Double) : LecturerEntry {
        val newEntry : InsertStatement<Number> = execute {
            com.example.model.LecturerTbl.insert {
                it[lecturerName] = newLecturerName
                it[hours] = BigDecimal.valueOf(newHours)
            }
        }

        listOfLecturers.add(
            LecturerEntryModel().apply {
                item = LecturerEntry(newEntry[LecturerTbl.id], newLecturerName, newHours)
            }
        )

        return LecturerEntry(newEntry[LecturerTbl.id], newLecturerName, newHours)
    }

    fun update(updatedLecturer: LecturerEntryModel): Int{
        return execute {
            LecturerTbl.update({ LecturerTbl.id eq(updatedLecturer.id.value!!.toInt()) }) {
                it[lecturerName] = updatedLecturer.lecturerName.value.toString()
                it[hours] = BigDecimal.valueOf(updatedLecturer.hours.value!!.toDouble())
            }
        }
    }

    fun delete(model: LecturerEntryModel) {
        execute {
            LecturerTbl.deleteWhere {
                LecturerTbl.id eq (model.id.value!!.toInt())
            }
        }
        listOfLecturers.remove(model)
    }


}