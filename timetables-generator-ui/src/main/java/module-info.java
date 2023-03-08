module net.chronakis.school.timetables.ui {
    requires javafx.controls;
    requires javafx.fxml;
	requires net.chronakis.adrian.ttgen;
	requires javafx.graphics;
	requires javafx.base;
	requires io;

    opens net.chronakis.school.timetables.ui to javafx.fxml;
    exports net.chronakis.school.timetables.ui;
}
