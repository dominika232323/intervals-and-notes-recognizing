INSERT INTO Users VALUES (1, "guest", "QbRjk7UX8b6eN5j7SWFATZ56zeIIsl9EwVQ2C7opwfMBlvEFj9BtC8HhL28tbDX+");

INSERT INTO Notes VALUES(1, "C2");
INSERT INTO Notes VALUES(2, "C#2/Db2");
INSERT INTO Notes VALUES(3, "D2");
INSERT INTO Notes VALUES(4, "D#2/Eb2");
INSERT INTO Notes VALUES(5, "E2");
INSERT INTO Notes VALUES(6, "F2");
INSERT INTO Notes VALUES(7, "F#2/Gb2");
INSERT INTO Notes VALUES(8, "G2");
INSERT INTO Notes VALUES(9, "G#2/Ab2");
INSERT INTO Notes VALUES(10, "A2");
INSERT INTO Notes VALUES(11, "A#2/Bb2");
INSERT INTO Notes VALUES(12, "B2");
INSERT INTO Notes VALUES(13, "C3");
INSERT INTO Notes VALUES(14, "C#3/Db3");
INSERT INTO Notes VALUES(15, "D3");
INSERT INTO Notes VALUES(16, "D#3/Eb3");
INSERT INTO Notes VALUES(17, "E3");
INSERT INTO Notes VALUES(18, "F3");
INSERT INTO Notes VALUES(19, "F#3/Gb3");
INSERT INTO Notes VALUES(20, "G3");
INSERT INTO Notes VALUES(21, "G#3/Ab3");
INSERT INTO Notes VALUES(22, "A3");
INSERT INTO Notes VALUES(23, "A#3/Bb3");
INSERT INTO Notes VALUES(24, "B3");
INSERT INTO Notes VALUES(25, "C4");
INSERT INTO Notes VALUES(26, "C#4/Db4");
INSERT INTO Notes VALUES(27, "D4");
INSERT INTO Notes VALUES(28, "D#4/Eb4");
INSERT INTO Notes VALUES(29, "E4");
INSERT INTO Notes VALUES(30, "F4");
INSERT INTO Notes VALUES(31, "F#4/Gb4");
INSERT INTO Notes VALUES(32, "G4");
INSERT INTO Notes VALUES(33, "G#4/Ab4");
INSERT INTO Notes VALUES(34, "A4");
INSERT INTO Notes VALUES(35, "A#4/Bb4");
INSERT INTO Notes VALUES(36, "B4");
INSERT INTO Notes VALUES(37, "C5");
INSERT INTO Notes VALUES(38, "C#5/Db5");
INSERT INTO Notes VALUES(39, "D5");
INSERT INTO Notes VALUES(40, "D#5/Eb5");
INSERT INTO Notes VALUES(41, "E5");
INSERT INTO Notes VALUES(42, "F5");
INSERT INTO Notes VALUES(43, "F#5/Gb5");
INSERT INTO Notes VALUES(44, "G5");
INSERT INTO Notes VALUES(45, "G#5/Ab5");
INSERT INTO Notes VALUES(46, "A5");
INSERT INTO Notes VALUES(47, "A#5/Bb5");
INSERT INTO Notes VALUES(48, "B5");
INSERT INTO Notes VALUES(49, "C6");

INSERT INTO Intervals VALUES (1, "Pryma czysta");
INSERT INTO Intervals VALUES (2, "Sekunda mała");
INSERT INTO Intervals VALUES (3, "Sekunda wielka");
INSERT INTO Intervals VALUES (4, "Tercja mała");
INSERT INTO Intervals VALUES (5, "Tercja wielka");
INSERT INTO Intervals VALUES (6, "Kwarta czysta");
INSERT INTO Intervals VALUES (7, "Tryton");
INSERT INTO Intervals VALUES (8, "Kwinta czysta");
INSERT INTO Intervals VALUES (9, "Seksta mała");
INSERT INTO Intervals VALUES (10, "Seksta wielka");
INSERT INTO Intervals VALUES (11, "Septyma mała");
INSERT INTO Intervals VALUES (12, "Septyma wielka");
INSERT INTO Intervals VALUES (13, "Oktawa czysta");

insert into LevelIntervals (userID, name, numberOfRepetitions, up, together, down) values (null, '10 powtórzeń, w górę', 10, 1, 0, 0);
insert into LevelIntervals (userID, name, numberOfRepetitions, up, together, down) values (null, '10 powtórzeń, w dół', 10, 0, 0, 1);
insert into LevelIntervals (userID, name, numberOfRepetitions, up, together, down) values (null, '10 powtórzeń, razem', 10, 0, 1, 0);



-- TESTING VALUES
INSERT INTO LevelNotes VALUES (1, 1, 'name1', 25, 49, 1, 5, 10);
INSERT INTO LevelNotes VALUES (2, 1, 'name2', 1, 24, 5, 10, 5);
INSERT INTO LevelNotes VALUES (3, 1, 'name3', 1, 49, 1, 10, 3);
INSERT INTO LevelNotes VALUES (4, 1, 'name4', 1, 5, 1, 5, 10);
INSERT INTO LevelNotes VALUES (5, 1, 'name5', 20, 24, 1, 5, 10);
INSERT INTO LevelNotes VALUES (6, 1, 'name6', 24, 30, 1, 5, 10);
INSERT INTO LevelNotes VALUES (7, 1, 'name7', 44, 49, 1, 5, 10);
INSERT INTO LevelNotes VALUES (8, 1, 'name8', 44, 45, 1, 5, 10);
INSERT INTO LevelNotes VALUES (9, 1, 'name9', 1, 49, 1, 20, 10);
INSERT INTO LevelNotes VALUES (10, 1, 'name10', 1, 49, 1, 20, 10);
INSERT INTO LevelNotes VALUES (11, 1, 'name11', 1, 49, 1, 20, 10);
INSERT INTO LevelNotes VALUES (12, 1, 'name12', 1, 49, 1, 20, 10);
INSERT INTO LevelNotes VALUES (13, 1, 'name13', 1, 49, 1, 20, 10);
INSERT INTO LevelNotes VALUES (14, 1, 'name14', 1, 49, 1, 20, 10);
INSERT INTO LevelNotes VALUES (15, 1, 'name15', 1, 49, 1, 20, 10);
INSERT INTO LevelNotes VALUES (16, 1, 'name16', 1, 49, 1, 20, 10);
INSERT INTO LevelNotes VALUES (17, 1, 'name17', 1, 49, 1, 20, 10);
INSERT INTO LevelNotes VALUES (18, 1, 'name18', 1, 49, 1, 20, 10);
INSERT INTO LevelNotes VALUES (19, 1, 'name19', 1, 49, 1, 20, 10);

INSERT INTO NotesGames (notesGameID, userID, levelNotesID, datePlayed) VALUES (160, 1, 7, '2024-01-02');
INSERT INTO NotesGames (notesGameID, userID, levelNotesID, datePlayed) VALUES (161, 1, 7, '2024-01-02');
INSERT INTO NotesGames (notesGameID, userID, levelNotesID, datePlayed) VALUES (162, 1, 7, '2023-12-30');
INSERT INTO NotesGames (notesGameID, userID, levelNotesID, datePlayed) VALUES (163, 1, 7, '2023-12-29');
INSERT INTO NotesGames (notesGameID, userID, levelNotesID, datePlayed) VALUES (164, 1, 7, '2023-12-30');

INSERT INTO AnswersNotesGame VALUES (1, 160, 24, 3, 2);
INSERT INTO AnswersNotesGame VALUES (1, 160, 15, 4, 4);
INSERT INTO AnswersNotesGame VALUES (1, 160, 48, 3, 3);
INSERT INTO AnswersNotesGame VALUES (1, 160, 26, 1, 1);
-- TESTING VALUES