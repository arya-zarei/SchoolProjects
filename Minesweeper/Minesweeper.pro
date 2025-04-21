# Minesweeper.pro
QT       += core gui widgets

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = Minesweeper
TEMPLATE = app

SOURCES += src/main.cpp \
           src/GameUI/GameBoard.cpp \
           src/Logic/GameLogic.cpp \
           src/Commands/HandleClicks.cpp \
           src/GameUI/Reveal.cpp \
           src/GameUI/GameOutcome.cpp

HEADERS  += src/GameUI/GameBoard.h \
            src/Logic/GameLogic.h \
            src/Commands/HandleClicks.h \
            src/GameUI/Reveal.h \
            src/GameUI/GameOutcome.h


# Add any additional resources or icons here
RESOURCES += resources.qrc