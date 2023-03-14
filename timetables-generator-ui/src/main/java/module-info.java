module net.chronakis.school.timetables.ui {
	requires net.chronakis.school.timetables.core;
	requires net.chronakis.school.timetables.pdf;

	requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;

    opens net.chronakis.school.timetables.ui to javafx.fxml;
    exports net.chronakis.school.timetables.ui;
}
