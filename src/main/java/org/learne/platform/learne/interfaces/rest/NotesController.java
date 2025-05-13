package org.learne.platform.learne.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.learne.platform.learne.domain.model.queries.Notes.GetAllNotesQuery;
import org.learne.platform.learne.domain.model.queries.Notes.GetNotesByStudentIdQuery;
import org.learne.platform.learne.domain.services.Exam.ExamCommandService;
import org.learne.platform.learne.domain.services.notes.NotesCommandService;
import org.learne.platform.learne.domain.services.notes.NotesQueryServices;
import org.learne.platform.learne.interfaces.rest.resources.Notes.CreateNotesResource;
import org.learne.platform.learne.interfaces.rest.resources.Notes.NoteResource;
import org.learne.platform.learne.interfaces.rest.transform.Notes.CreateNotesCommandFromResourceAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Notes.NotesResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/v1/notes",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Notes", description = "Notes API")
public class NotesController {

   private final NotesCommandService notesCommandService;
   private final NotesQueryServices notesQueryServices;


    public NotesController(NotesCommandService notesCommandService, NotesQueryServices notesQueryServices) {
        this.notesCommandService = notesCommandService;
        this.notesQueryServices = notesQueryServices;
    }
    @PostMapping
    @Operation(summary = "Create notes",description = "Create notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Note created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<NoteResource> createNotes(@RequestBody CreateNotesResource resource) {
        var createNoteCommand = CreateNotesCommandFromResourceAssembler.toCommandFromResource(resource);
        var noteId = notesCommandService.handle(createNoteCommand);

        if(noteId==null || noteId==0L) {
            return ResponseEntity.badRequest().build();
        }

        var getNotesByStudentIdQuery = new GetNotesByStudentIdQuery(noteId);
        var note = notesQueryServices.handle(getNotesByStudentIdQuery);;

        if(note.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var noteEntity = note.get();
        var noteResource= NotesResourceFromEntityAssembler.ToResourceFromEntity(noteEntity);

        return new ResponseEntity<>(noteResource, HttpStatus.CREATED);
    }

    @GetMapping("/{noteId}")
    @Operation(summary = "Get note by student_id", description = "Get note by student_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note found"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<NoteResource> getNoteById(@PathVariable Long noteId) {
        var getNotesByStudentIdQuery = new GetNotesByStudentIdQuery(noteId);
        var note=notesQueryServices.handle(getNotesByStudentIdQuery);
        if(note.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var noteResource= NotesResourceFromEntityAssembler.ToResourceFromEntity(note.get());
        var noteEntity = note.get();
        return ResponseEntity.ok(noteResource);

    }

    @GetMapping
    @Operation(summary = "Get all notes", description = "Get all notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notes found"),
            @ApiResponse(responseCode = "404", description = "Notes not found")
    })

    public ResponseEntity<List<NoteResource>> getAllNotes() {
        var notes = notesQueryServices.handle(new GetAllNotesQuery());
        if (notes.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var noteResource =notes.stream()
                .map(NotesResourceFromEntityAssembler::ToResourceFromEntity)
                .toList();
        return ResponseEntity.ok(noteResource);
    }
}
