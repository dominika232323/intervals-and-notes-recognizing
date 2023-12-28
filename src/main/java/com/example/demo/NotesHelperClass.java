package com.example.demo;

import java.util.List;

enum NoteType {
    SHARP, FLAT, NONE
}

enum NoteLetter {
    C, D, E, F, G, A, B
}

public class NotesHelperClass {
    public NotesHelperClass(String noteString){

    }
    private int octave = 0;
    private NoteType noteType = NoteType.FLAT;
    private NoteLetter noteLetter = NoteLetter.C;
    static final NoteLetter[] noteLettersArray = {NoteLetter.C, NoteLetter.D, NoteLetter.E,
            NoteLetter.F, NoteLetter.G, NoteLetter.A, NoteLetter.B};

    public static void main(String[] args){
        System.out.println(NoteLetter.C);
        NoteLetter nt = NoteLetter.valueOf("E");
        System.out.println(nt);
    }

}
