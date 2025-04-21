QT += widgets

CONFIG += c++23

SOURCES += main.cpp \
           Menu/menu.cpp \
           Menu/gamewindow.cpp \
           Menu/tutorial.cpp \
           Menu/buychips.cpp \
           Menu/leaderboard.cpp \
           Game/Game.cpp \  
           Game/Card.cpp \
           Game/Dealer.cpp \
           Game/Deck.cpp \
           Game/Player.cpp 

HEADERS += Menu/menu.h \
           Menu/gamewindow.h \
           Menu/tutorial.h \
           Menu/buychips.h \
           Menu/leaderboard.h \
           Game/Game.h \
           Game/Card.h \
           Game/Dealer.h \
           Game/Deck.h \
           Game/Player.h 

RESOURCES += resources.qrc

INCLUDEPATH += /usr/include/x86_64-linux-gnu/qt5 \
               /usr/include/x86_64-linux-gnu/qt5/QtWidgets \
               /usr/include/x86_64-linux-gnu/qt5/QtGui

LIBS += -L/usr/lib/x86_64-linux-gnu -lQt5Widgets -lQt5Gui -lQt5Core