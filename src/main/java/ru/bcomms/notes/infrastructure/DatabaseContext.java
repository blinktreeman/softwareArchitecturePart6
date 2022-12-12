package ru.bcomms.notes.infrastructure;

import ru.bcomms.databases.NotesDatabase;
import ru.bcomms.databases.NotesRecord;
import ru.bcomms.notes.core.application.interfaces.NotesDatabaseContext;
import ru.bcomms.notes.core.domain.Note;

import java.util.ArrayList;
import java.util.Collection;

public class DatabaseContext extends DbContext implements NotesDatabaseContext {
    public DatabaseContext(Database database) {
        super(database);
    }

    @Override
    protected void onModelCreating(ModelBuilder builder) {
        builder.applyConfiguration(new NoteConfiguration());
    }


    @Override
    public Collection<Note> getAll() {
        Collection<Note> notesList = new ArrayList<>();
        //TODO: Этого кастинга быть не должно, тут должен работать внутренний механизм фреймворка
        for (NotesRecord record : ((NotesDatabase)database).getNotesTable().getRecords()){
            notesList.add(new Note(
                    record.getId(),
                    record.getUserId(),
                    record.getTitle(),
                    record.getDetails(),
                    record.getCreationDate()
            ));
        }
        return notesList;
    }
}
