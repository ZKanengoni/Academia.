package com.example

import com.example.controller.StudentController
import com.example.utils.createTables
import com.example.utils.enableConsoleLogger
import com.example.view.CashPoolView
import com.example.view.LecturerEditor
import com.example.view.StudentEditor
import com.example.view.SubjectEditor
import javafx.scene.control.TabPane
import org.jetbrains.exposed.sql.Database
import tornadofx.*

class AdminUserWorkspace : Workspace("Admin Workspace", NavigationMode.Tabs) {

    init {
    //        initialize db...
        enableConsoleLogger()
        Database.connect("jdbc:sqlite:./app-academia.db", "org.sqlite.JDBC")
        createTables()

    //        controller/s
        StudentController()


    //        doc views
        dock<StudentEditor>()
        dock<LecturerEditor>()
        dock<SubjectEditor>()
        dock<CashPoolView>()

        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }
}
