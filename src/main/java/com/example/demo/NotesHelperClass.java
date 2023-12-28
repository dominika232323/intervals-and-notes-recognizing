package com.example.demo;

import java.util.List;




public class NotesHelperClass {
    public enum NoteType {
        SHARP, FLAT, NONE
    }

    public enum NoteLetter {
        C(1), D(2), E(3), F(4), G(5), A(6), B(7);

        private final int scaleDegree;

        NoteLetter(int scaleDegree) {
            this.scaleDegree = scaleDegree;
        }

        public int getScaleDegree() {
            return this.scaleDegree;
        }
    }
    private int octave = 0;
    private NoteType noteType = NoteType.NONE;
    private NoteLetter noteLetter = NoteLetter.C;

    public NotesHelperClass(String noteString) {
        try {
            // Validate the noteString format
            if (!noteString.matches("[CDEFGAB]([#b])?\\d")) {
                throw new IllegalArgumentException("Invalid note format: " + noteString);
            }

            // Extract the letter part
            char letter = noteString.charAt(0);
            noteLetter = NoteLetter.valueOf(String.valueOf(letter));

            // Check for sharp or flat
            if (noteString.contains("#")) {
                noteType = NoteType.SHARP;
            } else if (noteString.contains("b")) {
                noteType = NoteType.FLAT;
            } else {
                noteType = NoteType.NONE;
            }

            // Extract the octave
            int startIndex = noteString.contains("#") || noteString.contains("b") ? 2 : 1;
            octave = Integer.parseInt(noteString.substring(startIndex));

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            // Set default values or handle the error as needed
        }
    }

    public int getOctave() {
        return octave;
    }

    public int getScaleDegree(){
        return noteLetter.getScaleDegree();
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public NoteLetter getNoteLetter() {
        return noteLetter;
    }

    public static void main(String[] args) {
        NotesHelperClass nhc = new NotesHelperClass("A#4");
    }
}

