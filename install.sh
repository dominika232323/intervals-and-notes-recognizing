#!/bin/bash

if [ "$1" == '--install' ]
then
  # Instalacja Java (jesli nie jest zainstalowana)
  sudo apt-get update
  sudo apt-get install openjdk-17-jdk openjdk-17-jre

  # Instalacja Maven (jeśli nie jest zainstalowane)
  sudo apt-get install maven

  # Instalacja MySQL (jesli nie jest zainstalowana)
  sudo apt-get install mysql-server
fi;

# Set JAVA_HOME to your JDK path
export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which javac))))
export PATH=$JAVA_HOME/bin:$PATH

# Uruchomienie MySQL
sudo service mysql start

# Konfiguracja bazy danych MySQL
echo "CREATE DATABASE IF NOT EXISTS db;" | mysql -u root
echo "CREATE USER IF NOT EXISTS 'user'@'localhost' IDENTIFIED BY 'password';" | mysql -u root
echo "GRANT ALL PRIVILEGES ON db.* TO 'user'@'localhost';" | mysql -u root
echo "FLUSH PRIVILEGES;" | mysql -u root

# Import danych do bazy (jesli potrzebne)
sudo mysql -u user --password=password db -v < database/createDatabase.sql
sudo mysql -u user --password=password db -v < database/insertIntoDatabase.sql

# Uruchomienie projektu przy uzyciu Maven
mvn clean javafx:run



