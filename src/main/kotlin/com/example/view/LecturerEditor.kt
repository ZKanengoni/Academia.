package com.example.view

import com.example.controller.LecturerController
import com.example.controller.StudentController
import com.example.model.LecturerEntryModel
import com.example.model.StudentEntryModel
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import tornadofx.*

class LecturerEditor : View("Lecturer Admin") {

    private val model = LecturerEntryModel()
    private val modules = listOf("IDV100","IDV200","IDV300").asObservable()
    private val controller: LecturerController by inject()

    private var mTableView: TableViewEditModel<LecturerEntryModel> by singleAssign()

    override val root = borderpane {
        left = vbox {
            form {
                fieldset {
                    label("Lecturer's name")
                    field {
                        maxWidth = 200.0
                        textfield(model.lecturerName) {
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
                    label("Work hours")
                    field {
                        maxWidth = 200.0
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

                fieldset {
                    label("Select modules to teach")
                    field {
                        maxWidth = 200.0

                        listview(modules) {
                            selectionModel.selectionMode = SelectionMode.MULTIPLE
                        }
                    }
                }

                hbox(10) {
                    button("Add lecturer") {
                        enableWhen(model.valid)

                        action {
                            model.commit {
                                addLecturer()
                                model.rollback()
                            }


                        }
                    }

                    button("Delete") {
                        action {
                            val selectedLecturer: LecturerEntryModel? = mTableView.tableView.selectedItem
                            controller.delete(selectedLecturer!!)
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
                        label("Search for lecturer:")
                        field {
                            maxWidth = 200.0
                            textfield(model.lecturerName) {
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
                }
            }
        }

        center = vbox {

            alignment = Pos.CENTER_LEFT

            form {
                fieldset() {
                    tableview<LecturerEntryModel> {
                        items = controller.lecturers
                        mTableView = editModel
                        column("ID", LecturerEntryModel::id)
                        column("LecturerName", LecturerEntryModel::lecturerName).makeEditable()
//                        column("Modules",)
                        column("Hours", LecturerEntryModel::hours).makeEditable()

                        onEditCommit {
                            controller.update(it)
                        }
                    }
                }
            }

            style {
                paddingBottom = 85
            }
        }

    }

    private fun addLecturer() {
        controller.add(model.lecturerName.value, model.hours.value as Double)
    }
}
