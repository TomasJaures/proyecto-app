package com.group.rua.session.unit.program;

import com.group.rua.entities.Module;
import com.group.rua.entities.ProgramSubject;
import com.group.rua.entities.ProgramSubjectId;
import com.group.rua.entities.Subject;
import com.group.rua.repositories.ModuleRepo;
import com.group.rua.repositories.ProgramSubjectRepo;
import com.group.rua.repositories.SubjectRepo;
import com.group.rua.session.program.ProgramChangeDTO;
import com.group.rua.session.program.ProgramInfoDTO;
import com.group.rua.session.program.ProgramService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceTest {

    @Mock
    private ProgramSubjectRepo programSubjectRepo;

    @Mock
    private SubjectRepo subjectRepo;

    @Mock
    private ModuleRepo moduleRepo;

    @InjectMocks
    private ProgramService programService;

    @Test
    void getProgramInfo_EmptyProgram_ReturnsEmptyList() {
        when(programSubjectRepo.findById_ProgramId(1)).thenReturn(new ArrayList<>());
        List<ProgramInfoDTO> result = programService.getProgramInfo(1);
        assertTrue(result.isEmpty());
    }

    @Test
    void getProgramInfo_WithSubjects_ReturnsInfo() {
        ProgramSubjectId psId = new ProgramSubjectId();
        psId.subjectId = 10;
        ProgramSubject ps = new ProgramSubject();
        ps.id = psId;

        Subject mockSubject = new Subject();
        mockSubject.subjectId = 10;
        mockSubject.subjectName = "Matematicas";

        when(programSubjectRepo.findById_ProgramId(1)).thenReturn(List.of(ps));
        when(subjectRepo.findAllById(List.of(10))).thenReturn(List.of(mockSubject));
        when(moduleRepo.findBySubjectId(10)).thenReturn(new ArrayList<>());

        List<ProgramInfoDTO> result = programService.getProgramInfo(1);

        assertFalse(result.isEmpty());
        assertEquals("Matematicas", result.get(0).subjectName);
    }

    @Test
    void processProgramChanges_AddSubject() {
        ProgramChangeDTO change = new ProgramChangeDTO();
        change.field = "Subject";
        change.action = "Add";
        change.newSubjectName = "Fisica";
        change.code = "FIS101";

        Subject savedSubject = new Subject();
        savedSubject.subjectId = 5;

        when(subjectRepo.save(any(Subject.class))).thenReturn(savedSubject);

        programService.processProgramChanges(1, List.of(change));

        verify(subjectRepo, times(1)).save(any(Subject.class));
        verify(programSubjectRepo, times(1)).save(any(ProgramSubject.class));
    }

    @Test
    void processProgramChanges_EditSubject() {
        ProgramChangeDTO change = new ProgramChangeDTO();
        change.field = "Subject";
        change.action = "Edit";
        change.subjectId = 5;
        change.newName = "Fisica Avanzada";

        Subject existingSubject = new Subject();
        existingSubject.subjectId = 5;

        when(subjectRepo.findById(5)).thenReturn(Optional.of(existingSubject));

        programService.processProgramChanges(1, List.of(change));

        verify(subjectRepo, times(1)).save(existingSubject);
        assertEquals("Fisica Avanzada", existingSubject.subjectName);
    }

    @Test
    void processProgramChanges_RemoveSubject() {
        ProgramChangeDTO change = new ProgramChangeDTO();
        change.field = "Subject";
        change.action = "Remove";
        change.subjectId = 5;

        when(moduleRepo.findBySubjectId(5)).thenReturn(new ArrayList<>());

        programService.processProgramChanges(1, List.of(change));

        verify(programSubjectRepo, times(1)).deleteById(any(ProgramSubjectId.class));
        verify(moduleRepo, times(1)).deleteAll(anyList());
        verify(subjectRepo, times(1)).deleteById(5);
    }

    @Test
    void processProgramChanges_AddModule() {
        ProgramChangeDTO change = new ProgramChangeDTO();
        change.field = "Module";
        change.action = "Add";
        change.subjectId = 5;
        change.num = 1;

        programService.processProgramChanges(1, List.of(change));

        verify(moduleRepo, times(1)).save(any(Module.class));
    }

    @Test
    void processProgramChanges_RemoveModule() {
        ProgramChangeDTO change = new ProgramChangeDTO();
        change.field = "Module";
        change.action = "Remove";
        change.moduleId = 20;

        programService.processProgramChanges(1, List.of(change));

        verify(moduleRepo, times(1)).deleteById(20);
    }
}