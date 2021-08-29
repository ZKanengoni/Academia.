package com.example.view

import com.example.controller.StudentController
import com.example.model.StudentEntryModel
import javafx.collections.FXCollections
import javafx.scene.input.KeyCode
import tornadofx.*
import java.util.ArrayList

import javafx.collections.ObservableList




class StudentEditor : View("Student Admin") {

    private val model = StudentEntryModel()
    private val courseOptions = FXCollections.observableArrayList("Certificate", "Degree")
    private val coursePrices =FXCollections.observableArrayList(3000.00, 1500.00)
    private val controller: StudentController by inject()

    private var mTableView: TableViewEditModel<StudentEntryModel> by singleAssign()

    override val root = borderpane {
        center = vbox {
            form {
                fieldset {
                    label("Select student's date of birth:")
                    field {
                        maxWidth = 250.0

                        datepicker(model.dateOfBirth) {
                            this.required()

                            validator {
                                when {
                                    it?.dayOfMonth.toString().isEmpty() || it?.dayOfMonth.toString().isNullOrEmpty() || it?.dayOfYear.toString().isEmpty() -> error("The date cannot be blank")
                                    else -> null
                                }
                            }
                        }
                    }
                }

                fieldset {
                    label("Enter student's name:")
                    field {
                        maxWidth = 180.0
                       textfield(model.studentName) {
                           this.required()

                           validator {
                               when {
                                   it.isNullOrEmpty() -> error("Field cannot be empty")
                                   it!!.length < 3 -> error("Too short")
                                   else -> null
                               }
                           }
                       }
                    }
                }

                fieldset {
                    label("Enter amount due:")
                    field {
                        maxWidth = 180.0
                        textfield(model.fees) {
                            this.required()

                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("Field cannot be empty")
                                    else -> null
                                }
                            }
                        }
                    }
                }

                fieldset {
                    label("Select course type:")
                    field {
                        maxWidth = 220.0

                        combobox<String>(model.courseType) {
                            items = courseOptions

                            this.required()

                            validator {
                                when(it) {
                                    null -> error("Please select an option")
                                    else -> null
                                }
                            }

                            setOnKeyPressed {
                                if (it.code == KeyCode.ENTER) {
                                    model.commit {
                                        addStudent()
                                        model.rollback()
                                    }
                                }
                            }
                        }

                    }
                }

                hbox(10) {
                    button("Add student") {
                        enableWhen(model.valid)

                        action {
                            model.commit {
                                addStudent()
                                model.rollback()
                            }


                        }
                    }

                    button("Delete") {
                        action {
                            val selectedStudent: StudentEntryModel? = mTableView.tableView.selectedItem
                            controller.delete(selectedStudent!!)
                        }
                    }

                    button("Reset") {
                        enableWhen(model.valid)

                        action {
                            model.commit{
                                model.rollback()
                            }
                        }
                    }

                    style {
                        paddingBottom = 20
                    }
                }

                hbox {
                    fieldset {
                        label("Search for student:")
                        field {
                            maxWidth = 180.0
                            textfield(model.studentName) {
                                this.required()

                                validator {
                                    when {
                                        it.isNullOrEmpty() -> error("Field cannot be empty")
                                        it!!.length < 3 -> error("Too short")
                                        else -> null
                                    }
                                }
                            }
                        }
                    }
                }

                fieldset() {
                    tableview<StudentEntryModel> {
                        items = controller.students
                        mTableView = editModel
                        column("ID", StudentEntryModel::id)
                        column("StudentName", StudentEntryModel::studentName).makeEditable()
                        column("DateOfBirth", StudentEntryModel::dateOfBirth).makeEditable()
                        column("CourseType", StudentEntryModel::courseType).makeEditable()
                        column("Fees", StudentEntryModel::fees).makeEditable()
//                        column("Major")

                        onEditCommit {
                            controller.update(it)
                        }
                    }
                }
            }
        }

    }

    private fun addStudent() {
        controller.add(model.dateOfBirth.value, model.studentName.value, model.fees.value as Double, model.courseType.value)
    }

}
