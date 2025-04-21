# BlackjackGame

## How to Build & Run

### Install Qt Development Packages (if not installed)

Before compiling, ensure you have the necessary Qt development tools installed.

#### **For Ubuntu:**

```sh
sudo apt update
sudo apt install qtbase5-dev qtchooser qt5-qmake qtbase5-dev-tools
```

### Generate the Makefile using qmake

```sh
qmake blackjackgame.pro
```

#### If you don't have qmake installed:

```sh
sudo apt install qtchooser qt5-qmake
```

### Compile the Project

```sh
make
```

### Run the Application

```sh
./blackjackgame
```

## How to Build & Run Tests

### Build Tests Individually

#### Build `test_client`

```sh
g++ -o tests/test_client tests/test_client.cpp Client/client.cpp -IClient -pthread
```

#### Build `test_menu`

```sh
g++ -o tests/test_menu tests/test_menu.cpp Menu/menu.cpp -IMenu -I/usr/include/x86_64-linux-gnu/qt5/QtWidgets -I/usr/include/x86_64-linux-gnu/qt5 -lQt5Widgets -lQt5Gui -lQt5Core -pthread
```

#### Build `test_gamewindow`

```sh
g++ -o tests/test_gamewindow tests/test_gamewindow.cpp Menu/gamewindow.cpp -IMenu -I/usr/include/x86_64-linux-gnu/qt5/QtWidgets -I/usr/include/x86_64-linux-gnu/qt5 -lQt5Widgets -lQt5Gui -lQt5Core -pthread
```

### Run the Tests

Navigate to the `tests` directory and run the test executables:

```sh
./tests/test_client
./tests/test_menu
./tests/test_gamewindow
```
