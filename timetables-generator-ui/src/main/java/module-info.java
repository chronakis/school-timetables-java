module net.chronakis.school.timetables.ui {
	requires net.chronakis.school.timetables.core;
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires io;
	requires kernel;
	requires layout;

    opens net.chronakis.school.timetables.ui to javafx.fxml;
    exports net.chronakis.school.timetables.ui;
}
