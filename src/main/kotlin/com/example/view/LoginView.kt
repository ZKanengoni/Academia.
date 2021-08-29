package com.example.view

import com.example.AdminUserWorkspace
import com.example.controller.UserController
import com.example.model.UserEntryModel
import com.example.utils.createTables
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import org.jetbrains.exposed.sql.Database
import tornadofx.*

class LoginView : View("Academia.") {


    private val model = UserEntryModel()
    private val controller: UserController by inject()

    init {
        Database.connect("jdbc:sqlite:./app-academia.db", "org.sqlite.JDBC")
    }

    private val bgColour = c("#FFFFFF")
    private val buttonColour = c("#53616C")
    private val inputColour = c("#F3F3F4")
    private val mainLabelColour = c("#53616C")

    override val root = borderpane {
        left {
            hbox {
                alignment = Pos.CENTER_LEFT
                paddingLeft = 40.0

                imageview("/img/sammy.png") {
                    fitHeight = 400.0
                    isPreserveRatio = true
                }
            }
        }

        center {
            form {
                alignment = Pos.CENTER
                paddingLeft = 100.0

                hbox {
                    label("Academia.") {
                        style {
                            setTextFill(mainLabelColour)
                            fontSize = 22.pt
                            paddingBottom = 15.0
                            fontWeight = FontWeight.BOLD
                            alignment = Pos.CENTER

                        }
                    }
                }


                fieldset {
                    label("Username") {
                        style {
                            setTextFill(mainLabelColour)
                            fontSize = 12.pt
                            fontWeight = FontWeight.SEMI_BOLD
                            paddingTop = 10.0
                            paddingBottom = 5.0
                        }
                    }

                    field {
                        textfield (model.username) {
                            style {
                                setPrefHeight(36.0)
                                setMaxWidth(280.0)
                                backgroundColor += inputColour
                            }
                        }
                    }

                    label("Password") {
                        style {
                            setTextFill(mainLabelColour)
                            fontSize = 12.pt
                            fontWeight = FontWeight.SEMI_BOLD
                            paddingTop = 10.0
                            paddingBottom = 5.0
                        }
                    }

                    field {
                        passwordfield(model.password) {
                            style {
                                setPrefHeight(36.0)
                                setMaxWidth(280.0)
                                backgroundColor += inputColour
                            }
                        }
                    }

                }

                hbox {
                    button("Sign In") {

                        action {
                            loginUser()
//                            addUser()
                        }

                        style {
                            backgroundColor += buttonColour
                            setPrefSize(170.0, 35.0)
                            setTextFill(Color.WHITE)
                            fontWeight = FontWeight.BOLD
                            fontSize = 10.pt
                        }

                    }
                }
            }
        }

        style {
            backgroundColor += bgColour
        }
    }

    private fun loginUser() {

        model.commit {
            if(  controller.login(
                    model.username.value,
                    model.password.value
                ) ) {
                replaceWith<AdminUserWorkspace>()
                print("Clicked")
            }

        }


    }

}
