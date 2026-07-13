package com.group.rua.session.program;

import com.group.rua.entities.Module;
import com.group.rua.entities.ProgramSubject;
import com.group.rua.entities.Subject;
import com.group.rua.repositories.ModuleRepo;
import com.group.rua.repositories.ProgramSubjectRepo;
import com.group.rua.repositories.SubjectRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramService {

    private final ProgramSubjectRepo programSubjectRepo;
    private final SubjectRepo subjectRepo;
    private final ModuleRepo moduleRepo;

    public ProgramService(ProgramSubjectRepo programSubjectRepo, SubjectRepo subjectRepo, ModuleRepo moduleRepo) {
        this.programSubjectRepo = programSubjectRepo;
        this.subjectRepo = subjectRepo;
        this.moduleRepo = moduleRepo;
    }

    public List<ProgramInfoDTO> getProgramInfo(Integer programId) {

        List<ProgramSubject> programSubjects = programSubjectRepo.findById_ProgramId(programId);

        // i el usuario es nuevo y no tiene asignaturas, devuelve Ok con una lista vacía
        if (programSubjects.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> subjectIds = programSubjects.stream()
                .map(ps -> ps.id.subjectId)
                .toList();

        List<Subject> subjects = subjectRepo.findAllById(subjectIds);

        List<ProgramInfoDTO> response = new ArrayList<>();
        for (Subject subject : subjects) {
            List<Module> modules = moduleRepo.findBySubjectId(subject.subjectId);

            response.add(new ProgramInfoDTO(
                    subject.subjectId,
                    subject.subjectName,
                    subject.code,
                    modules
            ));
        }

        return response;
    }
}