package com.example.view

import com.example.controller.SubjectController
import com.example.model.LecturerEntryModel
import com.example.model.SubjectEntryModel
import javafx.geometry.Pos
import tornadofx.*

class SubjectEditor : View("Subject Admin") {

    private val model = SubjectEntryModel()
    private val controller: SubjectController by inject()

    private var mTableView: TableViewEditModel<SubjectEntryModel> by singleAssign()

    override val root = borderpane {
        left = vbox {

//            alignment = Pos.CENTER

            form {
                fieldset {
                    label("Subject name")
                    field {
                        textfield(model.subjectName) {
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
                    label("Subject code")
                    field {
                        textfield(model.subjectCode) {
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
                    label("Price")
                    field {
                        textfield(model.price) {
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
                    label("Hours per week")
                    field {
                        textfield(model.hours) {
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

                hbox(10) {
                    button("Add subject") {
                        enableWhen(model.valid)

                        action {
                            model.commit {
                                addSubject()
                                model.rollback()
                            }


                        }
                    }

                    button("Delete") {
                        action {
                            val selectedSubject: SubjectEntryModel? = mTableView.tableView.selectedItem
                            controller.delete(selectedSubject!!)
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
            }
        }

        center = vbox {

//            alignment = Pos.CENTER_LEFT

            form {
                fieldset() {
                    tableview<SubjectEntryModel> {
                        items = controller.subjects
                        mTableView = editModel
                        column("ID", SubjectEntryModel::id)
                        column("SubjectName", SubjectEntryModel::subjectName).makeEditable()
                        column("SubjectCode", SubjectEntryModel::subjectCode).makeEditable()
                        column("Price", SubjectEntryModel::price).makeEditable()
                        column("HoursPerWeek", SubjectEntryModel::hours).makeEditable()

                        onEditCommit {
                            controller.update(it)
                        }
                    }
                }
            }
        }

    }

    private fun addSubject() {
        controller.add(model.subjectName.value, model.subjectCode.value, model.price.value.toDouble(), model.hours.value.toDouble())
    }
}
