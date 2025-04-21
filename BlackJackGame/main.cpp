#include "Menu/menu.h"
#include "Menu/gamewindow.h"
#include <QApplication>
#include <QObject>
#include <QMessageBox>

// Entry point of the application
int main(int argc, char *argv[])
{
    // Create the QApplication object
    // This is required for any Qt application to manage GUI event handling
    QApplication app(argc, argv);

    try
    {
        // Create the menu window
        Menu menu;

        // Connect the usernameEntered signal from the Menu to a lambda function
        QObject::connect(&menu, &Menu::usernameEntered, [&](const QString &username)
                         {
            // Show a success message with the entered Player ID
            QMessageBox::information(&menu, "Success", "Your Player ID: " + username);
            menu.close(); // Close the menu window

            // Create and show the GameWindow with the entered username
            GameWindow *gameWindow = new GameWindow(username);
            gameWindow->show(); });

        // Connect the destroyed signal of the Menu to the quit slot of the QApplication
        // This ensures the application exits when the Menu window is closed
        QObject::connect(&menu, &QWidget::destroyed, &app, &QApplication::quit);

        // Show the menu window
        menu.show();

        // Execute the application event loop
        // This keeps the application running and processes user interactions
        return app.exec();
    }
    catch (const std::exception &e)
    {
        // Show an error message if an exception is caught
        QMessageBox::critical(nullptr, "Exception", e.what());
    }

    // Return 0 if the application exits successfully
    return 0;
}
