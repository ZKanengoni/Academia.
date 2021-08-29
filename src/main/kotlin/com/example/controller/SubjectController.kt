package com.example.controller
import com.example.model.*
import com.example.utils.execute
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.math.BigDecimal


class SubjectController : Controller() {

    var subjectModel = SubjectEntryModel()

    //  Get all subjects
    private val listOfSubjects: ObservableList<SubjectEntryModel> = execute {
        SubjectTbl.selectAll().map {
            SubjectEntryModel().apply {
                item = it.toSubjectEntry()
            }
        }.observable()
    }

    var subjects: ObservableList<SubjectEntryModel> by singleAssign()

    init {
        subjects = listOfSubjects
    }

    fun add( newSubjectName: String, newSubjectCode: String, newPrice: Double, newHours: Double) : SubjectEntry {
        val newEntry : InsertStatement<Number> = execute {
            com.example.model.SubjectTbl.insert {
                it[subjectName] = newSubjectName
                it[subjectCode] = newSubjectCode
                it[price] = BigDecimal.valueOf(newPrice)
                it[hours] = BigDecimal.valueOf(newHours)
            }
        }

        listOfSubjects.add(
            SubjectEntryModel().apply {
                item = SubjectEntry(newEntry[SubjectTbl.id], newSubjectName, newSubjectCode, newPrice, newHours)
            }
        )

        return SubjectEntry(newEntry[SubjectTbl.id], newSubjectName, newSubjectCode, newPrice, newHours)
    }

    fun update(updatedSubject: SubjectEntryModel): Int{
        return execute {
            SubjectTbl.update({ SubjectTbl.id eq(updatedSubject.id.value!!.toInt()) }) {
                it[subjectName] = updatedSubject.subjectName.value.toString()
                it[subjectCode] = updatedSubject.subjectCode.value.toString()
                it[price] = BigDecimal.valueOf(updatedSubject.price.value!!.toDouble())
                it[hours] = BigDecimal.valueOf(updatedSubject.hours.value!!.toDouble())
            }
        }
    }

    fun delete(model: SubjectEntryModel) {
        execute {
            SubjectTbl.deleteWhere {
                SubjectTbl.id eq (model.id.value!!.toInt())
            }
        }
        listOfSubjects.remove(model)
    }
}